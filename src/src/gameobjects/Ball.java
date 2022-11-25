package src.gameobjects;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.Sound;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import java.util.Random;

/**
 *
 */
public class Ball extends GameObject {

    private Sound collisionSound;
    private int collisionCount;

    /**
     *
     * @param topLeftCorner
     * @param dimensions
     * @param renderable
     * @param collisionSound
     */
    public Ball(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                Sound collisionSound) {
        super(topLeftCorner, dimensions, renderable);
        this.collisionSound = collisionSound;
        this.collisionCount = 0;
    }

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

    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);

        setVelocity(getVelocity().flipped(collision.getNormal()));
        collisionSound.play();
        this.collisionCount += 1;

    }

    public int getCollisionCount() {
        return this.collisionCount;
    }
}
