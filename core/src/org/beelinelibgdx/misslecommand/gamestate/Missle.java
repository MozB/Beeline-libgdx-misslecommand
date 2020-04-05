package org.beelinelibgdx.misslecommand.gamestate;

import org.beelinelibgdx.actors.VisibleModel;

public class Missle implements VisibleModel {

    public final float speed;
    public final float xTarget;
    public final float yTarget;

    public float x;
    public float y;
    public boolean stopped;


    public Missle(float speed, float x, float y, float xTarget, float yTarget) {
        this.speed = speed;
        this.x = x;
        this.y = y;
        this.xTarget = xTarget;
        this.yTarget = yTarget;
    }

    @Override
    public boolean shouldRemoveFromScreen() {
        return false;
    }
}
