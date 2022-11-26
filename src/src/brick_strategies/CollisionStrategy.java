package src.brick_strategies;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.util.Counter;

/**
 * interface for classes that represent strategy of brick
 */
public interface CollisionStrategy {
    public void onCollision(GameObject thisObj, GameObject otherObj, Counter numOfBricks);
}
