package src.brick_strategies;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.util.Counter;
import src.gameobjects.Brick;

public class DoubleBehavior extends RemoveBrickStrategy{

    private final BrickStrategyFactory brickStrategyFactory;
    private boolean isDoubleBehavior;

    public DoubleBehavior(GameObjectCollection gameObjects,
                          BrickStrategyFactory brickStrategyFactory, boolean isDoubleBehavior) {
        super(gameObjects);
        this.brickStrategyFactory = brickStrategyFactory;
        this.isDoubleBehavior = isDoubleBehavior;
    }

    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj, Counter numOfBricks) {
        super.onCollision(thisObj, otherObj, numOfBricks);
        if (brickRemoved && !isDoubleBehavior)
            return;
        if (((Brick)thisObj).getNumOfBehaviors() == 3)
            brickStrategyFactory.getStrategy(true, true).onCollision(
                    thisObj, otherObj, numOfBricks);
        brickStrategyFactory.getStrategy(true, false).onCollision(
                thisObj, otherObj, numOfBricks);
        brickStrategyFactory.getStrategy(true, false).onCollision(
                thisObj, otherObj, numOfBricks);
    }
}
