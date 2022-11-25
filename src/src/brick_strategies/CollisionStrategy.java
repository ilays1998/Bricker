package src.brick_strategies;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.util.Counter;

public interface CollisionStrategy {
//    private GameObjectCollection gameObjects;
//    public CollisionStrategy(GameObjectCollection gameObjects)
//    {
//        this.gameObjects = gameObjects;
//    }
    public void onCollision(GameObject thisObj, GameObject otherObj, Counter numOfBricks);
}
