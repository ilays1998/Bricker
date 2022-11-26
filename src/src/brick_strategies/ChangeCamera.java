package src.brick_strategies;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.gui.WindowController;
import danogl.gui.rendering.Camera;
import danogl.util.Counter;
import danogl.util.Vector2;
import src.BrickerGameManager;
import src.gameobjects.Ball;
import src.gameobjects.Brick;

/**
 * represent strategy of change the point of view of the game
 */
public class ChangeCamera extends RemoveBrickStrategy {
    private BrickerGameManager brickerGameManager;
    private Ball ball;
    private WindowController windowController;
    private boolean isDoubleBehavior;

    /**
     * construct the strategy
     * @param gameObjects append and remove objects
     * @param brickerGameManager for get information on the game
     * @param ball the original ball
     * @param windowController the game dimension
     * @param isDoubleBehavior indicates if the brick have more strategy
     */
    public ChangeCamera(GameObjectCollection gameObjects,
                        BrickerGameManager brickerGameManager,
                        Ball ball, WindowController windowController, boolean isDoubleBehavior) {
        super(gameObjects);
        this.brickerGameManager = brickerGameManager;
        this.ball = ball;
        this.windowController = windowController;
        this.isDoubleBehavior = isDoubleBehavior;
    }

    /**
     * the change of the camera in collision
     * @param thisObj the brick
     * @param otherObj the ball
     * @param numOfBricks the number of bricks that exist
     */
    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj, Counter numOfBricks) {
        super.onCollision(thisObj, otherObj, numOfBricks);
        //also check that camera is in the original state
        if ((brickRemoved && !isDoubleBehavior) || brickerGameManager.getCamera() != null ||
            !otherObj.equals(ball))
            return;
        ((Brick) thisObj).increaseNumOfBehaviors();
        brickerGameManager.setCamera(
                new Camera(
                        ball, //object to follow
                        Vector2.ZERO, //follow the center of the object
                        windowController.getWindowDimensions().mult(1.2f), //widen the frame a bit
                        windowController.getWindowDimensions() //share the window dimensions
                )
        );

    }
}
