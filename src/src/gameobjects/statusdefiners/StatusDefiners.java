package src.gameobjects.statusdefiners;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.collisions.GameObjectCollection;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import src.gameobjects.Paddle;
import src.gameobjects.PaddleTemporary;

/**
 * abstract class that represent a object that fall after
 * brick collision and have special behavior
 */
public abstract class StatusDefiners extends GameObject {

    private static final float SPEED = 100;
    private Vector2 dimension;
    private GameObjectCollection gameObjectCollection;
    private Vector2 windowDimensions;

    /**
     * constructor that the user decide the spedd of falling down of
     * the object
     * @param topLeftCorner place of the object
     * @param dimension dimension of the object
     * @param renderable the image of the object
     * @param center the new place for the object
     * @param speed speed falling down
     * @param gameObjectCollection for append and remove the object
     * @param windowDimensions the game dimension
     */
    public StatusDefiners(Vector2 topLeftCorner, Vector2 dimension,
                          Renderable renderable,
                          Vector2 center,
                          float speed, GameObjectCollection gameObjectCollection,
                          Vector2 windowDimensions) {
        super(topLeftCorner, dimension, renderable);
        this.dimension = dimension;
        this.gameObjectCollection = gameObjectCollection;
        this.windowDimensions = windowDimensions;
        this.setCenter(center);
        this.setVelocity(Vector2.DOWN.mult(speed));
    }

    /**
     * alternative constructor with default speed falling down
     * @param topLeftCorner place of the object
     * @param dimension dimension of the object
     * @param renderable the image of the object
     * @param center the new place for the object
     * @param gameObjectCollection for append and remove the object
     * @param windowDimensions the game dimension
     */
    public StatusDefiners(Vector2 topLeftCorner, Vector2 dimension,
                          Renderable renderable,
                          Vector2 center, GameObjectCollection gameObjectCollection,
                          Vector2 windowDimensions) {
        super(topLeftCorner, dimension, renderable);
        this.dimension = dimension;
        this.gameObjectCollection = gameObjectCollection;
        this.windowDimensions = windowDimensions;
        this.setCenter(center);
        this.setVelocity(Vector2.DOWN.mult(SPEED));
    }

    /**
     * the object should to collide with
     * @param other the original paddle the collide with the object
     * @return true if other is original paddle
     */
    @Override
    public boolean shouldCollideWith(GameObject other) {
        return (other instanceof Paddle && !(other instanceof PaddleTemporary));
    }

    /**
     * the behavior in collide
     * @param other paddle
     * @param collision see GameObject
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        collisionBehavior(other);
        gameObjectCollection.removeGameObject(this);
    }

    /**
     * the son need to implement the special behavior
     * @param other the paddle
     */
    protected abstract void collisionBehavior(GameObject other);

    /**
     * check if the object pass the board
     * @param deltaTime see GameObject
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        if (this.getDimensions().y() > windowDimensions.y())
            gameObjectCollection.removeGameObject(this);
    }
}
