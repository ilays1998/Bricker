package src.gameobjects;

import src.brick_strategies.CollisionStrategy;
import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;

/**
 * represent brick - game object - in the game
 */
public class Brick extends GameObject {

    private int numOfBehaviors;
    private CollisionStrategy collisionStrategy;
    private Counter numOfBricks;

    /**
     * constructor for the brick
     * @param topLeftCorner the brick place
     * @param dimensions the brick dimension
     * @param renderable the brick image
     * @param collisionStrategy the strategy that the brick have in collision of ball
     * @param numOfBricks the bricks that in the game (not removed)
     */
    public Brick(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                 CollisionStrategy collisionStrategy,
                 Counter numOfBricks) {
        super(topLeftCorner, dimensions, renderable);
        this.collisionStrategy = collisionStrategy;
        this.numOfBehaviors = 0;
        this.numOfBricks = numOfBricks;
    }

    /**
     * getter for num of special behaviors that the brick commit
     * @return numbers of the special behaviors that commit
     */
    public int getNumOfBehaviors() {
        return this.numOfBehaviors;
    }

    /**
     * increase the number of special behaviors by 1
     */
    public void increaseNumOfBehaviors() {
        this.numOfBehaviors += 1;
    }

    /**
     * behavior in collision
     * @param other the ball
     * @param collision see GameObject
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        collisionStrategy.onCollision(this, other,
                this.numOfBricks);
    }
}
