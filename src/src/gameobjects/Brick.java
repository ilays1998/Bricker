package src.gameobjects;

import src.brick_strategies.CollisionStrategy;
import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;

public class Brick extends GameObject {

    private int numOfBehaviors;
    private CollisionStrategy collisionStrategy;
    private Counter numOfBricks;

    /**
     * Construct a new GameObject instance.
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param renderable    The renderable representing the object. Can be null, in which case
     * @param numOfBricks
     */
    public Brick(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                 CollisionStrategy collisionStrategy,
                 Counter numOfBricks) {
        super(topLeftCorner, dimensions, renderable);
        this.collisionStrategy = collisionStrategy;
        this.numOfBehaviors = 0;
        this.numOfBricks = numOfBricks;
    }

    public int getNumOfBehaviors()
    {
        return this.numOfBehaviors;
    }

    public void increaseNumOfBehaviors()
    {
        this.numOfBehaviors += 1;
    }

    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        collisionStrategy.onCollision(this, other,
                this.numOfBricks);
    }
}
