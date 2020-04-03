package org.beelinelibgdx.misslecommand;

import org.beelinelibgdx.actors.BeelineActor;
import org.beelinelibgdx.actors.BeelineAssetManager;
import org.beelinelibgdx.actors.BeelineGroup;
import org.beelinelibgdx.actors.VisibleActor;
import org.beelinelibgdx.misslecommand.gamestate.Missle;

public class MissleActor extends BeelineGroup implements VisibleActor<Missle> {

    private Missle missle;

    public MissleActor(BeelineAssetManager assets, Missle missle) {
        this.missle = missle;
        BeelineActor base = new BeelineActor(assets.createSprite(() -> "square"), 10, 10);
        addActor(base);

        setWidth(base.getWidth());
        setHeight(base.getHeight());
    }

    @Override
    public Missle getModel() {
        return missle;
    }
}