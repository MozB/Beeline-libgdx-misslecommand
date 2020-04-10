package org.beelinelibgdx.misslecommand.gamestate;

import org.beelinelibgdx.actors.VisibleModel;

import java.awt.Menu;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

public class PlayerBase implements VisibleModel {
    public final int x;
    public final int y;
    public final int width;
    public final int height;
    public final List<PlayerBaseListener> playerBaseListeners = newArrayList();

    public int health = 1;

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
