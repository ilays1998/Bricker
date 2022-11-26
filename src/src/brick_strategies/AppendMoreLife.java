package src.brick_strategies;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;
import src.gameobjects.Brick;
import src.gameobjects.statusdefiners.MoreLife;

/**
 * represent strategy of falling heart
 */
public class AppendMoreLife extends RemoveBrickStrategy {
    private GameObjectCollection gameObjects;
    private Vector2 dimension;
    private Renderable lifeImage;
    private float speed;
    private Vector2 windowDimension;
    private Counter livesCounter;
    private int distanceFromSide;
    private boolean isDoubleBehavior;

    /**
     * construct the strategy
     * @param gameObjects append and remove object
     * @param dimension the dimension of the heart
     * @param lifeImage the image of the heart
     * @param speed the speed of the heart
     * @param windowDimension the game dimension
     * @param livesCounter counter of player's lives
     * @param distanceFromSide distance from heart to heart
     * @param isDoubleBehavior indicates if the brick have more strategy
     */
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

    /**
     * append more falling heart
     * @param thisObj the brick
     * @param otherObj the ball
     * @param numOfBricks the number of bricks that exist
     */
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
