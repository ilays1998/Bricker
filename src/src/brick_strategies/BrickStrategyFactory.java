package src.brick_strategies;

import danogl.collisions.GameObjectCollection;
import danogl.gui.Sound;
import danogl.gui.UserInputListener;
import danogl.gui.WindowController;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;
import src.BrickerGameManager;
import src.gameobjects.Ball;

import java.util.Random;

/**
 * this class represent to give brick strategy to demand
 */
public class BrickStrategyFactory {

    private final int numOfStrategy;
    private GameObjectCollection gameObjects;
    private Renderable ballMockImage;
    private Renderable paddleTemporaryImage;
    private Sound collisionSound;
    private Vector2 ballDimensions;
    private Vector2 paddleDimensions;
    private float ballSpeed;
    private Vector2 windowDimensions;
    private UserInputListener inputListener;
    private int minDistanceFromScreenEdge;
    private BrickerGameManager brickerGameManager;
    private Ball ball;
    private WindowController windowController;
    private Vector2 lifeDimension;
    private Renderable lifeImage;
    private float lifeSpeed;
    private Counter livesCounter;
    private int distanceFromSide;

    /**
     * construct new strategy factory
     * @param gameObjects add and remove objects
     * @param images the images for the strategy
     * @param dimensions the dimension for the strategy
     * @param collisionSound sound of collision
     * @param brickerGameManager to get information about the game
     * @param minDistanceFromScreenEdge the distance from wall
     * @param ballSpeed ball speed
     * @param ball the object original ball
     * @param lifeSpeed the speed of the moving down life
     * @param distanceFromSide distance from heart to heart
     */
    public BrickStrategyFactory(GameObjectCollection gameObjects,
                                Renderable[] images,
                                Vector2[] dimensions,
                                Sound collisionSound,
                                BrickerGameManager brickerGameManager,
                                int minDistanceFromScreenEdge,
                                float ballSpeed,
                                Ball ball,
                                float lifeSpeed,
                                int distanceFromSide)
    {
        this.ballMockImage = images[0];
        this.paddleTemporaryImage = images[1];
        this.lifeImage = images[2];
        this.collisionSound = collisionSound;
        this.ballDimensions = dimensions[0];
        this.paddleDimensions = dimensions[1];
        this.lifeDimension = dimensions[2];
        this.windowDimensions = dimensions[3];
        this.ballSpeed = ballSpeed;
        this.inputListener = brickerGameManager.getInputListener();
        this.minDistanceFromScreenEdge = minDistanceFromScreenEdge;
        this.brickerGameManager = brickerGameManager;
        this.ball = ball;
        this.windowController = brickerGameManager.getWindowController();
        this.lifeSpeed = lifeSpeed;
        this.livesCounter = brickerGameManager.getLivesCounter();
        this.distanceFromSide = distanceFromSide;
        this.numOfStrategy = BrickStrategy.values().length;
        this.gameObjects = gameObjects;
    }

    /**
     * return new strategy for the brick
     * @param isDoubleBehavior true if the brick have more special behaviors
     * @param maxNumOfBehaviors true if the brick have 3 special behaviors
     * @return new strategy for the brick
     */
    public CollisionStrategy getStrategy(boolean isDoubleBehavior, boolean maxNumOfBehaviors) {
        //choose randomly between the possible brick strategies
        Random random = new Random();
        BrickStrategy choose;
        if (maxNumOfBehaviors)
            return new RemoveBrickStrategy(gameObjects);
        else if (isDoubleBehavior)
            choose = BrickStrategy.values()[1 + random.nextInt(numOfStrategy - 1)];
        else
            choose = BrickStrategy.values()[random.nextInt(numOfStrategy)];
        switch (choose)
        {
            case REMOVE_BRICK_STRATEGY:
                return new RemoveBrickStrategy(gameObjects);
            case ADD_PUCK:
                return new AddPuck(gameObjects, ballMockImage, collisionSound,
                        ballDimensions, ballSpeed, windowDimensions, isDoubleBehavior);
            case APPEND_PADDLE_TEMPORARY:
                return new AppendPaddleTemporary(paddleDimensions,
                        paddleTemporaryImage, inputListener, windowDimensions,
                        gameObjects, minDistanceFromScreenEdge, isDoubleBehavior);
            case CHANGE_CAMERA:
                return new ChangeCamera(gameObjects, brickerGameManager, ball,
                        windowController, isDoubleBehavior);
            case MORE_LIFE:
                return new AppendMoreLife(gameObjects,
                        lifeDimension, lifeImage, lifeSpeed, windowDimensions,
                        livesCounter, distanceFromSide, isDoubleBehavior);
            case DOUBLE_BEHAVIOR:
                return new DoubleBehavior(gameObjects,
                        this, isDoubleBehavior);
            default:
                return null;
        }
    }
}
