package src.brick_strategies;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.util.Counter;
import src.gameobjects.Brick;

public class RemoveBrickStrategy implements CollisionStrategy{
    protected boolean brickRemoved;
    private GameObjectCollection gameObjects;
    public RemoveBrickStrategy(GameObjectCollection gameObjects)
    {
        this.gameObjects = gameObjects;
        this.brickRemoved = false;
    }
    public void onCollision(GameObject thisObj, GameObject otherObj, Counter numOfBricks) {
        this.brickRemoved = !gameObjects.removeGameObject(thisObj, Layer.STATIC_OBJECTS);
        if (!this.brickRemoved)
            numOfBricks.decrement();
    }
}
