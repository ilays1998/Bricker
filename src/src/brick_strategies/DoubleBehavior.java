package src.brick_strategies;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.util.Counter;
import src.gameobjects.Brick;

/**
 * represent strategy the choose 2 new strategy for the brick
 */
public class DoubleBehavior extends RemoveBrickStrategy{

    private final BrickStrategyFactory brickStrategyFactory;
    private boolean isDoubleBehavior;

    /**
     * construct the double strategy
     * @param gameObjects for remove and append objects
     * @param brickStrategyFactory the factory that give new startegy
     * @param isDoubleBehavior indicates if the brick have more strategy
     */
    public DoubleBehavior(GameObjectCollection gameObjects,
                          BrickStrategyFactory brickStrategyFactory, boolean isDoubleBehavior) {
        super(gameObjects);
        this.brickStrategyFactory = brickStrategyFactory;
        this.isDoubleBehavior = isDoubleBehavior;
    }

    /**
     * the append of the behaviors in collision
     * @param thisObj the brick
     * @param otherObj the paddle
     * @param numOfBricks the number of bricks that exist
     */
    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj, Counter numOfBricks) {
        super.onCollision(thisObj, otherObj, numOfBricks);
        if (brickRemoved && !isDoubleBehavior)
            return;
        //if the brick have 3 special behaviors, need to return and not append strategy
        if (((Brick)thisObj).getNumOfBehaviors() == 3) {
            brickStrategyFactory.getStrategy(true, true).onCollision(
                    thisObj, otherObj, numOfBricks);
        }
        else {
            brickStrategyFactory.getStrategy(true, false).onCollision(
                    thisObj, otherObj, numOfBricks);
            brickStrategyFactory.getStrategy(true, false).onCollision(
                    thisObj, otherObj, numOfBricks);
        }
    }
}
