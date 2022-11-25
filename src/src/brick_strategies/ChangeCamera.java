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

public class ChangeCamera extends RemoveBrickStrategy {
    private BrickerGameManager brickerGameManager;
    private Ball ball;
    private WindowController windowController;
    private boolean isDoubleBehavior;

    public ChangeCamera(GameObjectCollection gameObjects,
                        BrickerGameManager brickerGameManager,
                        Ball ball, WindowController windowController, boolean isDoubleBehavior) {
        super(gameObjects);
        this.brickerGameManager = brickerGameManager;
        this.ball = ball;
        this.windowController = windowController;
        this.isDoubleBehavior = isDoubleBehavior;
    }

    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj, Counter numOfBricks) {
        super.onCollision(thisObj, otherObj, numOfBricks);
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
