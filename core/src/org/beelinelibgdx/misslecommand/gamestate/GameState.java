package org.beelinelibgdx.misslecommand.gamestate;

import org.beelinelibgdx.misslecommand.MissleCommandGame;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

public class GameState {

    public final List<PlayerBase> playerBases = newArrayList();
    public final List<Missle> computerMissles = newArrayList();
    public final List<Missle> playerMissles = newArrayList();
    public final List<GameStateListener> gameStateListeners = newArrayList();

    public boolean gameOver;
    public int score;
    public int reloadFramesRemaining;

    public GameState() {
        playerBases.add(new PlayerBase((MissleCommandGame.getWidth()/6) * 1, 0, 200, 50));
        playerBases.add(new PlayerBase((MissleCommandGame.getWidth()/6) * 2, 0, 200, 50));

        playerBases.add(new PlayerBase((MissleCommandGame.getWidth()/6) * 4, 0, 200, 50));
        playerBases.add(new PlayerBase((MissleCommandGame.getWidth()/6) * 5, 0, 200, 50));
    }

}
