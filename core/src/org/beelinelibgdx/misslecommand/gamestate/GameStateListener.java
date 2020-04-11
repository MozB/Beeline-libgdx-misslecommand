package org.beelinelibgdx.misslecommand.gamestate;

public interface GameStateListener {

    void onGameOver();
    void onScoreChanged(int scoreDelta);
    void onPlayerMissleFired(Missle missle);

}
