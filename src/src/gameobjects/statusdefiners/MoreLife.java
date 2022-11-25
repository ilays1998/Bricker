package src.gameobjects.statusdefiners;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;
import src.gameobjects.GraphicLifeCounter;

/**
 * the more life class represent a object that fall
 * from brick after collision, and give the player if
 * he catch it with the paddle more life (until 4 life)
 */
public class MoreLife extends StatusDefiners{
    private Vector2 dimension;
    private Renderable lifeImage;
    private GameObjectCollection gameObjectCollection;
    private Vector2 windowDimensions;
    private Counter livesCounter;
    private int distanceFromSide;

    /**
     * class constructor
     * @param topLeftCorner the object place
     * @param dimension the object dimension
     * @param lifeImage the heart image
     * @param center the object new place
     * @param speed the speed of the falling down
     * @param gameObjectCollection to append object to game
     * @param windowDimensions the game dimension
     * @param livesCounter live of the player
     * @param distanceFromSide for place the new graphic heart
     */
    public MoreLife(Vector2 topLeftCorner, Vector2 dimension,
                    Renderable lifeImage, Vector2 center, float speed,
                    GameObjectCollection gameObjectCollection,
                    Vector2 windowDimensions, Counter livesCounter,
                    int distanceFromSide) {
        super(topLeftCorner, dimension,
                lifeImage, center, speed, gameObjectCollection, windowDimensions);
        this.dimension = dimension;
        this.lifeImage = lifeImage;
        this.gameObjectCollection = gameObjectCollection;
        this.windowDimensions = windowDimensions;
        this.livesCounter = livesCounter;
        this.distanceFromSide = distanceFromSide;
    }

    /**
     * in collision with the paddle (only the original paddle)
     * append heart to the player
     * @param other the paddle
     */
    @Override
    protected void collisionBehavior(GameObject other) {
        if (livesCounter.value() > 3)
            return;
        livesCounter.increment();
        GameObject graphicLife = new GraphicLifeCounter(Vector2.ZERO, dimension,
                livesCounter, lifeImage, gameObjectCollection, livesCounter.value() - 1,
                distanceFromSide, windowDimensions);

        gameObjectCollection.addGameObject(graphicLife, Layer.BACKGROUND);
    }
}
