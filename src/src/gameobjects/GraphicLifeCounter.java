package src.gameobjects;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.gui.WindowController;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;

public class GraphicLifeCounter extends GameObject {
    private Counter livesCounter;
    private GameObjectCollection gameObjectsCollection;
    private int numOfLives;

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

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        if (this.livesCounter.value() == this.numOfLives)
        {
            gameObjectsCollection.removeGameObject(this, Layer.BACKGROUND);
        }
    }
}
