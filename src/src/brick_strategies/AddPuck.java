package src.brick_strategies;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.gui.Sound;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;
import src.gameobjects.Ball;
import src.gameobjects.Brick;

import java.util.Random;

public class AddPuck extends RemoveBrickStrategy{
    private GameObjectCollection gameObjects;
    private Renderable ballMockImage;
    private Sound collisionSound;
    private Vector2 ballDimensions;
    private float ballSpeed;
    private Vector2 windowDimensions;
    private boolean isDoubleBehavior;

    public AddPuck(GameObjectCollection gameObjects,
                   Renderable ballMockImage, Sound collisionSound,
                   Vector2 ballDimensions, float ballSpeed, Vector2 windowDimensions,
                   boolean isDoubleBehavior) {
        super(gameObjects);
        this.gameObjects = gameObjects;
        this.ballMockImage = ballMockImage;
        this.collisionSound = collisionSound;
        this.ballDimensions = ballDimensions;
        this.ballSpeed = ballSpeed;
        this.windowDimensions = windowDimensions;
        this.isDoubleBehavior = isDoubleBehavior;
    }

    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj,
                            Counter numOfBricks) {
        Vector2 center = thisObj.getCenter();
        super.onCollision(thisObj, otherObj, numOfBricks);
        if (this.brickRemoved && !isDoubleBehavior)
            return;
        ((Brick) thisObj).increaseNumOfBehaviors();

        for (int i = 0; i < 3; i++) {
            Ball ballMock = new Ball(Vector2.ZERO, ballDimensions, ballMockImage,
                    collisionSound);
            ballMock.setCenter(center.add(new Vector2(i, i)));
            ballMock.setVelocity(Vector2.DOWN.mult(ballSpeed));

            //creating random start of ball
            ballMock.randomDirection(ballSpeed);
            gameObjects.addGameObject(ballMock);
        }
    }


}
