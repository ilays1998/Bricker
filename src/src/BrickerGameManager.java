package src;

import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.components.CoordinateSpace;
import danogl.gui.*;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;
import src.brick_strategies.BrickStrategyFactory;
import src.brick_strategies.RestartCameraView;
import src.gameobjects.*;

/**
 * the Bricker main class that run the game and responsible for
 * the parameters of the game
 * @author ilay soffer
 */
public class BrickerGameManager extends GameManager{

    private static final float BORDER_WIDTH = 10;
    private static final int DISTANCE_NUMERIC_LIVES = 17;
    private static final int MIN_DISTANCE_FROM_SCREEN_EDGE = 3;
    private static final Vector2 PADDLE_PLACE = Vector2.ZERO;
    private static final Vector2 PADDLE_DIMENSIONS = new Vector2(100, 15);
    private static final Vector2 BALL_PLACE = Vector2.ZERO;
    private static final Vector2 BALL_DIMENSIONS = new Vector2(20, 20);
    private static final float BALL_SPEED = 100;
    private static final Vector2 WINDOW_DIMENSIONS = new Vector2(700, 500);
    private static final float BRICK_HEIGHT = 15;
    private static final float DISTANSE_BETWEEN_BRICK_TO_WALL = 5;
    private static final float DISTANSE_BETWEEN_BRICK_TO_BRICK = 1;
    private static final int NUM_OF_BRICK_IN_ROW = 8;
    private static final int NUM_OF_BRICK_IN_COL = 5;
    private static final Vector2 HEART_SIZE = new Vector2(20, 20);
    private static final int NUM_OF_LIVES = 3;
    private static final int DISTANCE_FROM_SIDE = 15;
    private static final int DISTANCE_FLOOR = 30;
    private static final int LIFE_SPEED = 100;
    private Counter livesCounter;
    private Counter numOfBricks;
    private Ball ball;
    private Vector2 windowDimensions;
    private WindowController windowController;
    private UserInputListener inputListener;
    private BrickStrategyFactory brickStrategyFactory;
    private RestartCameraView restartCameraView;

    /**
     * Bricker constructor
     * @param windowTitle the name of the open window
     * @param windowDimensions game dimension
     */
    public BrickerGameManager(String windowTitle, Vector2 windowDimensions) {
        super(windowTitle, windowDimensions);
    }

    //private method to generate new brick
    private void newBrick(Vector2 place, Vector2 size, Renderable brickImage,
                          Counter numOfBricks) {
        gameObjects().addGameObject(new Brick(place, size, brickImage,
                brickStrategyFactory.getStrategy(false, false),
                numOfBricks), Layer.STATIC_OBJECTS);
    }

    //private method to generate new walls for the game
    private void newWall(Vector2 place, Vector2 size, Renderable wallImage)
    {
        gameObjects().addGameObject(new GameObject(
                //anchored at top-left corner of the screen
                place,
                //height of border is the height of the screen
                size,
                //this game object is invisible; it doesn’t have a Renderable
                wallImage));
    }

    //getter for window controller
    public WindowController getWindowController() {
        return windowController;
    }

    //getter for inputListener
    public UserInputListener getInputListener() {
        return inputListener;
    }

    //getter for lives Counter
    public Counter getLivesCounter() {
        return livesCounter;
    }

    /**
     * override of the inheritance class that responsible
     * for generate the objects of the game and to give each
     * brick behavior
     * @param imageReader reader image for make Renderable
     * @param soundReader reader for sound of object (in collision)
     * @param inputListener reader for player input
     * @param windowController for the sizes of the windows
     */
    @Override
    public void initializeGame(ImageReader imageReader, SoundReader soundReader,
                               UserInputListener inputListener,
                               WindowController windowController) {
        super.initializeGame(imageReader, soundReader, inputListener, windowController);

        this.inputListener = inputListener;
        this.livesCounter = new Counter(NUM_OF_LIVES);
        this.windowController = windowController;
        this.numOfBricks = new Counter(NUM_OF_BRICK_IN_COL
                * NUM_OF_BRICK_IN_ROW);

        //creating ball
        Renderable ballImage =
                imageReader.readImage("assets/ball.png", true);

        Sound collisionSound = soundReader.readSound("assets/blop_cut_silenced.wav");

        creatingBall(windowController, ballImage, collisionSound);


        //create paddle
        Renderable paddleImage = imageReader.readImage(
                "assets/paddle.png", true);
        creatingPaddle(inputListener, paddleImage);


        //creating the RestartCameraView class to control the change of the camera
        this.restartCameraView = new RestartCameraView(this, ball);

        //creating walls

        newWall(Vector2.ZERO, new Vector2(BORDER_WIDTH, windowDimensions.y()), null);
        newWall(Vector2.ZERO, new Vector2(windowDimensions.x(), BORDER_WIDTH), null);
        newWall(new Vector2(windowDimensions.x() - BORDER_WIDTH, 0),
                new Vector2(BORDER_WIDTH, windowDimensions.y()),null);

        //creating background
        GameObject background = new GameObject(
                Vector2.ZERO,
                windowController.getWindowDimensions(),
                imageReader.readImage("assets/DARK_BG2_small.jpeg", false));

        background.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);

        gameObjects().addGameObject(background, Layer.BACKGROUND);

        //creating random start of ball
        ball.randomDirection(BALL_SPEED);

        //creating hearts
        Renderable heartImage =
                imageReader.readImage("assets/heart.png", true);

        creatingHearts(heartImage);

        //creating life counter
        GameObject numLifeCounter = new NumericLifeCounter(livesCounter,
                Vector2.ZERO, HEART_SIZE, gameObjects());
        numLifeCounter.setCenter(new Vector2(DISTANCE_FROM_SIDE +
                HEART_SIZE.x() * NUM_OF_LIVES + DISTANCE_FROM_SIDE,
                windowDimensions.y() - DISTANCE_NUMERIC_LIVES));
        gameObjects().addGameObject(numLifeCounter, Layer.BACKGROUND);

        //creating the strategy factory
        Renderable paddleTemporaryImage =
                imageReader.readImage("assets/botGood.png", true);
        Renderable ballMockImage =
                imageReader.readImage("assets/mockBall.png", true);

        Renderable[] images = new Renderable[]
                {ballMockImage, paddleTemporaryImage, heartImage};
        Vector2[] dimensions = new Vector2[]{BALL_DIMENSIONS.add(new Vector2(5, 5)),
                PADDLE_DIMENSIONS,
                HEART_SIZE,
                windowDimensions};
        this.brickStrategyFactory = new BrickStrategyFactory(gameObjects(),images,
                dimensions,
                collisionSound,
                this,
                MIN_DISTANCE_FROM_SCREEN_EDGE,
                BALL_SPEED,
                 ball,
                 LIFE_SPEED, DISTANCE_FROM_SIDE);


        //creating brick
        Renderable brickImage =
                imageReader.readImage("assets/brick.png", false);

        creatingBricks(brickImage);


    }

    //responsible to put bricks in the amount that asked and fit
    //the bricks size according to this
    private void creatingBricks(Renderable brickImage) {
        float brickWidth = (windowDimensions.x() - (BORDER_WIDTH * 2) -
                (DISTANSE_BETWEEN_BRICK_TO_WALL * 2) -
                (DISTANSE_BETWEEN_BRICK_TO_BRICK * (NUM_OF_BRICK_IN_ROW - 1)))
                / NUM_OF_BRICK_IN_ROW;

        float brickRow =  BORDER_WIDTH + DISTANSE_BETWEEN_BRICK_TO_WALL;
        float brickCol = BORDER_WIDTH + DISTANSE_BETWEEN_BRICK_TO_WALL;
        //generate columns and rows of bricks
        for (int row = 0; row < NUM_OF_BRICK_IN_ROW; row++) {
            for (int col = 0; col < NUM_OF_BRICK_IN_COL; col++) {
                newBrick(new Vector2(brickRow, brickCol),
                        new Vector2(brickWidth, BRICK_HEIGHT),
                        brickImage,  numOfBricks);
                brickCol += DISTANSE_BETWEEN_BRICK_TO_BRICK + BRICK_HEIGHT;
            }
            brickCol = BORDER_WIDTH + DISTANSE_BETWEEN_BRICK_TO_WALL;
            brickRow += DISTANSE_BETWEEN_BRICK_TO_BRICK + brickWidth;
        }
    }

    //method for generate the graphic hearts in the screen
    private void creatingHearts(Renderable heartImage) {
        for (int i = 0; i < NUM_OF_LIVES; i++) {
            GameObject heart = new GraphicLifeCounter(Vector2.DOWN, HEART_SIZE, livesCounter,
                    heartImage, gameObjects(), i, DISTANCE_FROM_SIDE, windowDimensions);
            gameObjects().addGameObject(heart, Layer.BACKGROUND);
        }
    }

    //creating the paddle of the game
    private void creatingPaddle(UserInputListener inputListener, Renderable paddleImage) {
        GameObject paddle = new Paddle(PADDLE_PLACE, PADDLE_DIMENSIONS, paddleImage,
                inputListener, windowDimensions, MIN_DISTANCE_FROM_SCREEN_EDGE);

        paddle.setCenter(new Vector2(windowDimensions.x()/2,
                windowDimensions.y()- DISTANCE_FLOOR));

        gameObjects().addGameObject(paddle);
    }

    //creating the main ball of the game
    private void creatingBall(WindowController windowController, Renderable ballImage, Sound collisionSound) {
        ball = new Ball(BALL_PLACE, BALL_DIMENSIONS, ballImage,
                collisionSound);
        ball.setVelocity(Vector2.DOWN.mult(BALL_SPEED));

        windowDimensions = windowController.getWindowDimensions();
        ball.setCenter(windowDimensions.mult(0.5f));

        this.gameObjects().addGameObject(ball);
    }

    /**
     * override the update game by frame check if the game ended
     * @param deltaTime see father class
     * @see danogl.GameObject
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        checkForGameEnd();
        this.restartCameraView.checkForRestartCamera();
    }

    //check if we won or loss
    private void checkForGameEnd() {
        float ballHeight = ball.getCenter().y();
        String prompt = "";
        //lost one life
        if (ballHeight > windowDimensions.y()) {
            //we lost
            prompt = "You Lost!";
            livesCounter.decrement();
        }
        //win the game
        if (numOfBricks.value() == 0 ||
                inputListener.isKeyPressed('w') ||
                inputListener.isKeyPressed('W')) {
            prompt = "You Won! Play again?";
            newGame(prompt);
        } else if (livesCounter.value() == 0 && !prompt.isEmpty()) {
            prompt += " Play again?";
            newGame(prompt);
        } else if (livesCounter.value() != 0 && !prompt.isEmpty()) {
            ball.setCenter(windowDimensions.mult(0.5f));
        }
    }

    //method for new game after win or lose
    private void newGame(String prompt) {
        if(windowController.openYesNoDialog(prompt))
            windowController.resetGame();
        else
            windowController.closeWindow();
    }

    public static void main(String[] args) {
        new BrickerGameManager("Bricker", WINDOW_DIMENSIONS).run();
    }
}
