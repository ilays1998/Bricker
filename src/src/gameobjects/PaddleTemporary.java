package src.gameobjects;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.collisions.GameObjectCollection;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

/**
 * class that inheritance from paddle and represent temporary
 * paddle that disappear after number of collision with balls
 */
public class PaddleTemporary extends Paddle{
    public static boolean paddleTemporaryExist;
    private int numOfCollision;
    private GameObjectCollection gameObjectCollection;

    /**
     * Construct a new temporary paddle instance.
     *  @param topLeftCorner    Position of the object, in window coordinates (pixels).
     *                         Note that (0,0) is the top-left corner of the window.
     * @param dimensions       Width and height in window coordinates.
     * @param renderable       The renderable representing the object. Can be null, in which case
     * @param inputListener     The user input (<- and ->)
     * @param windowDimensions the game dimension
     * @param minDistanceFromScreenEdge the minimum distance of the paddle from wall
     */
    public PaddleTemporary(Vector2 topLeftCorner, Vector2 dimensions,
                           Renderable renderable, UserInputListener inputListener,
                           Vector2 windowDimensions,
                           GameObjectCollection gameObjectCollection,
                           int minDistanceFromScreenEdge) {
        super(topLeftCorner, dimensions, renderable, inputListener,
                windowDimensions, minDistanceFromScreenEdge);
        PaddleTemporary.paddleTemporaryExist = true;
        this.gameObjectCollection = gameObjectCollection;
        this.numOfCollision = 0;
    }

    /**
     * remove the paddle after 3 collision with balls
     * @see Paddle
     * @param other the object of collision
     * @param collision see GameObject
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        if (other instanceof Ball)
            this.numOfCollision += 1;
        if (numOfCollision == 3) {
            gameObjectCollection.removeGameObject(this);
            PaddleTemporary.paddleTemporaryExist = false;
        }
    }

}
