package src.brick_strategies;

import danogl.GameManager;
import src.gameobjects.Ball;

/**
 * class that responsible to restart the camera of the game to the original state
 * after number of collision of the original ball
 */
public class RestartCameraView{
    private GameManager gameManager;
    private Ball ball;
    private boolean flagChangeCamera;
    private int numOfCollision;

    /**
     * construct the restart class
     * @param gameManager the control of the camera
     * @param ball the original ball
     */
    public RestartCameraView(GameManager gameManager, Ball ball) {
        this.gameManager = gameManager;
        this.ball = ball;
        this.flagChangeCamera = false;
        this.numOfCollision = 0;
    }

    /**
     * checking if need to restart and restart if needed
     */
    public void checkForRestartCamera() {
        if (this.flagChangeCamera) {
            if (ball.getCollisionCount() - this.numOfCollision >= 4) {
                gameManager.setCamera(null);
                this.flagChangeCamera = false;
            }
        }
        else if (gameManager.getCamera() != null) {
            this.flagChangeCamera = true;
            this.numOfCollision = ball.getCollisionCount();
        }
    }
}
