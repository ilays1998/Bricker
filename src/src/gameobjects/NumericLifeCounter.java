package src.gameobjects;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.gui.rendering.TextRenderable;
import danogl.util.Counter;
import danogl.util.Vector2;

import java.awt.*;

public class NumericLifeCounter extends GameObject {

    private static final int TWO_LIVES = 2;
    private static final int ONE_LIVES = 1;
    private static final double THREE_LIVES = 3;
    private static final int FORE_LIVES = 4;
    private Counter livesCounter;
    private GameObjectCollection gameObjectCollection;

    public NumericLifeCounter(Counter livesCounter, Vector2 topLeftCorner,
                              Vector2 dimensions,
                              GameObjectCollection gameObjectCollection)
    {
        super(topLeftCorner, dimensions, null);
        this.livesCounter = livesCounter;
        this.gameObjectCollection = gameObjectCollection;
        changeText(Color.GREEN);
    }

    private void changeText(Color color) {
        TextRenderable text = new TextRenderable(
                Integer.toString(livesCounter.value()));
        text.setColor(color);
        this.renderer().setRenderable(text);
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        if (livesCounter.value() == FORE_LIVES)
        {
            changeText(Color.GREEN);
        }
        if (livesCounter.value() == THREE_LIVES)
        {
            changeText(Color.GREEN);
        }
        if (livesCounter.value() == TWO_LIVES)
        {
            changeText(Color.YELLOW);
        }
        else if (livesCounter.value() == ONE_LIVES)
        {
            changeText(Color.RED);
        }
        else if (livesCounter.value() == 0)
        {
            gameObjectCollection.removeGameObject(this, Layer.BACKGROUND);
        }
    }
}
