package src.gameobjects;

import danogl.GameObject;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import java.awt.event.KeyEvent;

public class Paddle extends GameObject {

    public static final float MOVE_SPEED = 200f;
    private final int minDistFromEdge;
    private UserInputListener inputListener;
    private Vector2 windowDimensions;

    /**
     * Construct a new GameObject instance.
     *  @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param renderable    The renderable representing the object. Can be null, in which case
     * @param inputListener
     */
    public Paddle(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                  UserInputListener inputListener, Vector2 windowDimensions,
                  int minDistFromEdge) {
        super(topLeftCorner, dimensions, renderable);
        this.minDistFromEdge = minDistFromEdge;
        this.inputListener = inputListener;
        this.windowDimensions = windowDimensions;
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        if (inputListener.isKeyPressed(KeyEvent.VK_LEFT))
        {
            setVelocity(Vector2.LEFT.mult(MOVE_SPEED));
        }
        if (inputListener.isKeyPressed(KeyEvent.VK_RIGHT))
        {
            setVelocity(Vector2.RIGHT.mult(MOVE_SPEED));
        }
        if (!inputListener.isKeyPressed(KeyEvent.VK_LEFT) &&
                !inputListener.isKeyPressed(KeyEvent.VK_RIGHT))
        {
            setVelocity(Vector2.ZERO);
        }
        if (getTopLeftCorner().x() < minDistFromEdge)
        {
            setVelocity(Vector2.ZERO);
            transform().setTopLeftCornerX(getTopLeftCorner().x() + minDistFromEdge);
        }
        if(windowDimensions.x() - minDistFromEdge - getDimensions().x() <
                getTopLeftCorner().x())
        {
            setVelocity(Vector2.ZERO);
            transform().setTopLeftCornerX(getTopLeftCorner().x() - minDistFromEdge);

        }
    }
}
