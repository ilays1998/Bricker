package src.brick_strategies;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;
import src.gameobjects.Brick;
import src.gameobjects.Paddle;
import src.gameobjects.PaddleTemporary;

import java.util.Random;

/**
 * represent appending paddle temporary in the screen
 */
public class AppendPaddleTemporary extends RemoveBrickStrategy{
    private Vector2 paddleDimensions;
    private Renderable paddleImage;
    private UserInputListener inputListener;
    private Vector2 windowDimensions;
    private GameObjectCollection gameObjects;
    private int minDistanceFromScreenEdge;
    private boolean isDoubleBehavior;

    /**
     * construct the strategy og appending of the temporary paddle
     * @param paddleDimensions dimension of the new paddle
     * @param paddleImage image of new paddle
     * @param inputListener the user input
     * @param windowDimensions the game dimension
     * @param gameObjects append and remove objects
     * @param minDistanceFromScreenEdge distance from walls
     * @param isDoubleBehavior indicates if the brick have more strategy
     */
    public AppendPaddleTemporary(Vector2 paddleDimensions, Renderable paddleImage,
                                 UserInputListener inputListener,
                                 Vector2 windowDimensions, GameObjectCollection gameObjects,
                                 int minDistanceFromScreenEdge, boolean isDoubleBehavior) {
        super(gameObjects);
        this.paddleDimensions = paddleDimensions;
        this.paddleImage = paddleImage;
        this.inputListener = inputListener;
        this.windowDimensions = windowDimensions;
        this.gameObjects = gameObjects;
        this.minDistanceFromScreenEdge = minDistanceFromScreenEdge;
        this.isDoubleBehavior = isDoubleBehavior;
    }

    /**
     * append the temporary paddle
     * @param thisObj the brick
     * @param otherObj the ball
     * @param numOfBricks the number of the bricks exist in current time
     */
    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj, Counter numOfBricks) {
        super.onCollision(thisObj, otherObj, numOfBricks);
        if ((brickRemoved && !isDoubleBehavior)|| PaddleTemporary.paddleTemporaryExist)
            return;
        ((Brick) thisObj).increaseNumOfBehaviors();
        Paddle paddleTemporary =
                new PaddleTemporary(
                        new Vector2((windowDimensions.x() / 2) - (paddleDimensions.x() / 2),
                                windowDimensions.y() / 2),
                        paddleDimensions, paddleImage, inputListener, windowDimensions,
                        gameObjects, minDistanceFromScreenEdge);
        gameObjects.addGameObject(paddleTemporary);

    }
}
