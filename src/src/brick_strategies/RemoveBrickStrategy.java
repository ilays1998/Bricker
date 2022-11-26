package src.brick_strategies;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.util.Counter;
import src.gameobjects.Brick;

/**
 * represent the strategy od remove the brick and do nothing
 */
public class RemoveBrickStrategy implements CollisionStrategy{
    protected boolean brickRemoved;
    private GameObjectCollection gameObjects;

    /**
     * construct the brick strategy
     * @param gameObjects to append and remove object
     */
    public RemoveBrickStrategy(GameObjectCollection gameObjects)
    {
        this.gameObjects = gameObjects;
        this.brickRemoved = false;
    }

    /**
     * the remove of the brick in collision
     * @param thisObj the brick
     * @param otherObj the ball
     * @param numOfBricks the number of bricks that exist
     */
    public void onCollision(GameObject thisObj, GameObject otherObj, Counter numOfBricks) {
        this.brickRemoved = !gameObjects.removeGameObject(thisObj, Layer.STATIC_OBJECTS);
        //the number of the existing bricks decrease by 1 if this if the first removed
        //(avoid double counting remove)
        if (!this.brickRemoved)
            numOfBricks.decrement();
    }
}
