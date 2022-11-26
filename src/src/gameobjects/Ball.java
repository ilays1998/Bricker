package src.gameobjects;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.Sound;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import java.util.Random;

/**
 *  represent ball game object of the bricker game
 */
public class Ball extends GameObject {

    private Sound collisionSound;
    private int collisionCount;

    /**
     * constructor for the ball
     * @param topLeftCorner the ball place
     * @param dimensions the ball dimension
     * @param renderable the ball image
     * @param collisionSound the collision sound
     */
    public Ball(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                Sound collisionSound) {
        super(topLeftCorner, dimensions, renderable);
        this.collisionSound = collisionSound;
        this.collisionCount = 0;
    }

    /**
     * the method help for classes that uses ball and want to
     * generate it with random axis start
     * @param ballSpeed the ball speed
     */
    public void randomDirection(float ballSpeed) {
        float ballVelX = ballSpeed;
        float ballVelY = ballSpeed;
        Random rand = new Random();
        if (rand.nextBoolean()) {
            ballVelX *= -1;
        }
        if (rand.nextBoolean()) {
            ballVelY *= -1;
        }
        this.setVelocity(new Vector2(ballVelX, ballVelY));
    }

    /**
     * the behavior of the ball in collision - to change direction
     * according to physical behavior
     * @param other the object that the ball collision with
     * @param collision see GameObject
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);

        setVelocity(getVelocity().flipped(collision.getNormal()));
        collisionSound.play();
        this.collisionCount += 1;

    }

    /**
     * the method count the number of collision of the ball with some object
     * @return the number of collision of the ball until now
     */
    public int getCollisionCount() {
        return this.collisionCount;
    }
}
