package src.brick_strategies;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;
import src.gameobjects.Brick;
import src.gameobjects.statusdefiners.MoreLife;

public class AppendMoreLife extends RemoveBrickStrategy {
    private GameObjectCollection gameObjects;
    private Vector2 dimension;
    private Renderable lifeImage;
    private float speed;
    private Vector2 windowDimension;
    private Counter livesCounter;
    private int distanceFromSide;
    private boolean isDoubleBehavior;

    public AppendMoreLife(GameObjectCollection gameObjects,
                          Vector2 dimension, Renderable lifeImage,
                          float speed,
                          Vector2 windowDimension,
                          Counter livesCounter, int distanceFromSide, boolean isDoubleBehavior) {
        super(gameObjects);
        this.gameObjects = gameObjects;
        this.dimension = dimension;
        this.lifeImage = lifeImage;
        this.speed = speed;
        this.windowDimension = windowDimension;
        this.livesCounter = livesCounter;
        this.distanceFromSide = distanceFromSide;
        this.isDoubleBehavior = isDoubleBehavior;
    }

    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj, Counter numOfBricks) {
        Vector2 center = thisObj.getCenter();
        super.onCollision(thisObj, otherObj, numOfBricks);
        if (brickRemoved && !isDoubleBehavior)
            return;
        ((Brick) thisObj).increaseNumOfBehaviors();
        GameObject moreLife = new MoreLife(Vector2.ZERO, dimension, lifeImage,
                center, speed, gameObjects, windowDimension,
                livesCounter, distanceFromSide);
        gameObjects.addGameObject(moreLife);
    }
}
