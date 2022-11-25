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

public class AppendPaddleTemporary extends RemoveBrickStrategy{
    private Vector2 paddleDimensions;
    private Renderable paddleImage;
    private UserInputListener inputListener;
    private Vector2 windowDimensions;
    private GameObjectCollection gameObjects;
    private int minDistanceFromScreenEdge;
    private boolean isDoubleBehavior;
    private GameObject paddle;

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
