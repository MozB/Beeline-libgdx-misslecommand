package org.beelinelibgdx.misslecommand.service;

import com.badlogic.gdx.math.MathUtils;

import org.beelinelibgdx.misslecommand.MissleCommandGame;
import org.beelinelibgdx.misslecommand.gamestate.GameState;
import org.beelinelibgdx.misslecommand.gamestate.Missle;
import org.beelinelibgdx.misslecommand.gamestate.PlayerBase;

import java.util.Random;

public class GameStateService {

    private Random random = new Random();

    public void eachFrame(GameState gameState) {
        if (random.nextInt(60 * 3) == 0) {
            generateNewMissle(gameState);
        }
        for (Missle missle : gameState.computerMissles) {
            eachFrameMissle(missle);
        }
    }

    private void eachFrameMissle(Missle missle) {
        float angleToTarget = getAngle(missle.x, missle.y, missle.xTarget, missle.yTarget);

        // We have hypotenuse and angle, we can calculate the x, y delta from this
        float adjacent = (float) (missle.speed * Math.cos(Math.toRadians(angleToTarget)));
        float opposite = (float) (missle.speed * Math.sin(Math.toRadians(angleToTarget)));

        // move our missle a small amount in the direction of target
        missle.x += adjacent;
        missle.y += opposite;
    }

    private float getAngle(float x1, float y1, float x2, float y2) {
        float angle = (float) Math.toDegrees(Math.atan2(y2 - y1, x2 - x1));

        if(angle < 0){
            angle += 360;
        }

        return angle;
    }

    private void generateNewMissle(GameState gameState) {
        float speed = random.nextFloat()*2 + 1;

        // start our missle at a random location at the top of the screen
        float startX = MissleCommandGame.getWidth() * random.nextFloat();
        float startY = MissleCommandGame.getHeight();

        // target our missle at a random player base
        PlayerBase randomPlayerBase = gameState.playerBases.get(random.nextInt(gameState.playerBases.size()));
        float targetX = randomPlayerBase.x;
        float targetY = randomPlayerBase.y;

        Missle missle = new Missle(speed, startX, startY, targetX, targetY);
        gameState.computerMissles.add(missle);
    }

}
