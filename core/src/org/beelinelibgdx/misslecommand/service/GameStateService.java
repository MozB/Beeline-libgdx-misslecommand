package org.beelinelibgdx.misslecommand.service;

import org.beelinelibgdx.misslecommand.MissleCommandGame;
import org.beelinelibgdx.misslecommand.gamestate.GameState;
import org.beelinelibgdx.misslecommand.gamestate.Missle;
import org.beelinelibgdx.misslecommand.gamestate.PlayerBase;

import java.util.Random;

public class GameStateService {

    private Random random = new Random();

    public void eachFrame(GameState gameState) {
        if (random.nextInt(60 * 3) == 0) {
            generateNewComputerMissle(gameState);
        }
        for (Missle missle : gameState.computerMissles) {
            eachFrameMissle(missle);
            eachFrameComputerMissle(gameState, missle);
        }
        for (Missle missle : gameState.playerMissles) {
            eachFrameMissle(missle);
            eachFramePlayerMissle(gameState, missle);
        }
    }

    private void eachFrameComputerMissle(GameState gameState, Missle missle) {
        if (!missle.stopped && getDistance(missle.x, missle.y, missle.xTarget, missle.yTarget) <= missle.speed) {
            // stop the player missle to make sure this only runs once
            missle.stopped = true;
            missle.missleEventListeners.stream().forEach(i -> i.onMissleReachedTarget(missle));
        }
    }

    private void eachFramePlayerMissle(GameState gameState, Missle missle) {
        if (!missle.stopped && getDistance(missle.x, missle.y, missle.xTarget, missle.yTarget) <= missle.speed) {
            // stop computer missles near our player missle
            for (Missle computerMissle : gameState.computerMissles) {
                if (!computerMissle.stopped && getDistance(missle.x, missle.y, computerMissle.x, computerMissle.y) <= 100) {
                    computerMissle.missleEventListeners.stream().forEach(i -> i.onMissleDestroyed(computerMissle));
                    computerMissle.stopped = true;
                }
            }
            // stop the player missle to make sure this only runs once
            missle.stopped = true;
            missle.missleEventListeners.stream().forEach(i -> i.onMissleReachedTarget(missle));
        }
    }

    public static float getDistance(float x1, float y1, float x2, float y2) {
        return (float) Math.sqrt(Math.pow(x2-x1,2) + Math.pow(y2-y1,2));
    }

    private void eachFrameMissle(Missle missle) {
        if (!missle.stopped) {
            float angleToTarget = getAngle(missle.x, missle.y, missle.xTarget, missle.yTarget);

            // We have hypotenuse and angle, we can calculate the x, y delta from this
            float adjacent = (float) (missle.speed * Math.cos(Math.toRadians(angleToTarget)));
            float opposite = (float) (missle.speed * Math.sin(Math.toRadians(angleToTarget)));

            // move our missle a small amount in the direction of target
            missle.x += adjacent;
            missle.y += opposite;
        }
    }

    public static float getAngle(float x1, float y1, float x2, float y2) {
        float angle = (float) Math.toDegrees(Math.atan2(y2 - y1, x2 - x1));

        if(angle < 0){
            angle += 360;
        }

        return angle;
    }

    public void generateNewPlayerMissle(GameState gameState, float targetX, float targetY) {
        float speed = 6;

        // start our missle at the bottom middle of the screen
        float startX = MissleCommandGame.getWidth() / 2;
        float startY = 0;

        Missle missle = new Missle(speed, startX, startY, targetX, targetY);
        gameState.playerMissles.add(missle);
    }

    private void generateNewComputerMissle(GameState gameState) {
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
