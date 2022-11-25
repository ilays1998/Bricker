package src.gameobjects;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.collisions.GameObjectCollection;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

public class PaddleTemporary extends Paddle{
    public static boolean paddleTemporaryExist;
    private int numOfCollision;
    private GameObjectCollection gameObjectCollection;

    /**
     * Construct a new GameObject instance.
     *  @param topLeftCorner    Position of the object, in window coordinates (pixels).
     *                         Note that (0,0) is the top-left corner of the window.
     * @param dimensions       Width and height in window coordinates.
     * @param renderable       The renderable representing the object. Can be null, in which case
     * @param inputListener
     * @param windowDimensions
     * @param minDistanceFromScreenEdge
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

    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        if (other instanceof Ball)
            this.numOfCollision += 1;
        if (numOfCollision == 3)
        {
            gameObjectCollection.removeGameObject(this);
            PaddleTemporary.paddleTemporaryExist = false;
        }
    }

}
