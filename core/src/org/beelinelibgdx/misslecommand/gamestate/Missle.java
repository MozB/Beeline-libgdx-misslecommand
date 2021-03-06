package org.beelinelibgdx.misslecommand.gamestate;

import org.beelinelibgdx.actors.VisibleModel;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

public class Missle implements VisibleModel {

    public final float speed;
    public final float xTarget;
    public final float yTarget;
    public final float xStart;
    public final float yStart;

    public float x;
    public float y;
    public boolean stopped;

    public List<MissleEventListener> missleEventListeners = newArrayList();


    public Missle(float speed, float x, float y, float xTarget, float yTarget) {
        this.speed = speed;
        this.xStart = x;
        this.yStart = y;
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
