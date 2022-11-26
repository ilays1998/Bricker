package src.gameobjects;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.gui.WindowController;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;

/**
 * represent the Hearts in the side of the game that represent
 * the player's lives
 */
public class GraphicLifeCounter extends GameObject {
    private Counter livesCounter;
    private GameObjectCollection gameObjectsCollection;
    private int numOfLives;

    /**
     * constructor for the Heart
     * @param widgetTopLeftCorner the place of the heart
     * @param widgetDimensions the dimension of the heart
     * @param livesCounter the lives of the player
     * @param widgetRenderable image for the heart
     * @param gameObjectsCollection remove and append object
     * @param numOfLives the num od lives at current time
     * @param distanceFromSide distance heart from heart
     * @param windowDimension the game dimension
     */
    public GraphicLifeCounter(Vector2 widgetTopLeftCorner, Vector2 widgetDimensions,
                              Counter livesCounter, Renderable widgetRenderable,
                              GameObjectCollection gameObjectsCollection, int numOfLives,
                              int distanceFromSide, Vector2 windowDimension)
    {
        super(widgetTopLeftCorner, widgetDimensions, widgetRenderable);
        this.setCenter(new Vector2(distanceFromSide + widgetDimensions.x() * numOfLives,
                windowDimension.y() - distanceFromSide));
        this.livesCounter = livesCounter;
        this.gameObjectsCollection = gameObjectsCollection;
        this.numOfLives = numOfLives;
    }

    /**
     * update the game by each frame and check if need to remove the heart
     * because the player loss life
     * @param deltaTime
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        if (this.livesCounter.value() == this.numOfLives) {
            gameObjectsCollection.removeGameObject(this, Layer.BACKGROUND);
        }
    }
}
