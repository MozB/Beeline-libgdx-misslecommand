package org.beelinelibgdx.misslecommand.gamestate;

import org.beelinelibgdx.actors.VisibleModel;

public class PlayerBase implements VisibleModel {
    public final int x;
    public final int y;
    public final int width;
    public final int height;

    private int health = 1;

    public PlayerBase(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    @Override
    public boolean shouldRemoveFromScreen() {
        return false;
    }
}
