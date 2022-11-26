package src.gameobjects;

import danogl.GameObject;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import java.awt.event.KeyEvent;

/**
 *  represent paddle in the game that the user control
 */
public class Paddle extends GameObject {

    public static final float MOVE_SPEED = 200f;
    private final int minDistFromEdge;
    private UserInputListener inputListener;
    private Vector2 windowDimensions;

    /**
     * Construct a new Paddle instance.
     * @param topLeftCorner the place of the paddle
     * @param dimensions the dimension of the paddle
     * @param renderable the image of the paddle
     * @param inputListener the user input
     * @param windowDimensions the game dimension
     * @param minDistFromEdge the minimum distance of the paddle from wall
     */
    public Paddle(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                  UserInputListener inputListener, Vector2 windowDimensions,
                  int minDistFromEdge) {
        super(topLeftCorner, dimensions, renderable);
        this.minDistFromEdge = minDistFromEdge;
        this.inputListener = inputListener;
        this.windowDimensions = windowDimensions;
    }

    /**
     * move the paddle according user input (<- and ->)
     * @param deltaTime see GameObject
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        if (inputListener.isKeyPressed(KeyEvent.VK_LEFT)) {
            setVelocity(Vector2.LEFT.mult(MOVE_SPEED));
        }
        if (inputListener.isKeyPressed(KeyEvent.VK_RIGHT)) {
            setVelocity(Vector2.RIGHT.mult(MOVE_SPEED));
        }
        if (!inputListener.isKeyPressed(KeyEvent.VK_LEFT) &&
                !inputListener.isKeyPressed(KeyEvent.VK_RIGHT)) {
            setVelocity(Vector2.ZERO);
        }
        if (getTopLeftCorner().x() < minDistFromEdge) {
            setVelocity(Vector2.ZERO);
            transform().setTopLeftCornerX(getTopLeftCorner().x() + minDistFromEdge);
        }
        if(windowDimensions.x() - minDistFromEdge - getDimensions().x() <
                getTopLeftCorner().x()) {
            setVelocity(Vector2.ZERO);
            transform().setTopLeftCornerX(getTopLeftCorner().x() - minDistFromEdge);

        }
    }
}
