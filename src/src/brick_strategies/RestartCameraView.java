package src.brick_strategies;

import danogl.GameManager;
import src.gameobjects.Ball;

public class RestartCameraView{
    private GameManager gameManager;
    private Ball ball;
    private boolean flagChangeCamera;
    private int numOfCollision;

    public RestartCameraView(GameManager gameManager, Ball ball) {
        this.gameManager = gameManager;
        this.ball = ball;
        this.flagChangeCamera = false;
        this.numOfCollision = 0;
    }

    public void checkForRestartCamera() {
        if (this.flagChangeCamera)
        {
            if (ball.getCollisionCount() - this.numOfCollision >= 4){
                gameManager.setCamera(null);
                this.flagChangeCamera = false;
            }
        }
        else if (gameManager.getCamera() != null)
        {
            this.flagChangeCamera = true;
            this.numOfCollision = ball.getCollisionCount();
        }
    }
}
