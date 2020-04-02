package org.beelinelibgdx.misslecommand;

import org.beelinelibgdx.actors.BeelineActor;
import org.beelinelibgdx.actors.BeelineAssetManager;
import org.beelinelibgdx.actors.BeelineGroup;
import org.beelinelibgdx.actors.VisibleActor;
import org.beelinelibgdx.misslecommand.gamestate.PlayerBase;

public class PlayerBaseActor extends BeelineGroup implements VisibleActor<PlayerBase>{

    private PlayerBase playerBase;

    public PlayerBaseActor(BeelineAssetManager assets, PlayerBase playerBase) {
        this.playerBase = playerBase;
        BeelineActor base = new BeelineActor(assets.createSprite(() -> "square"), playerBase.width, playerBase.height);
        addActor(base);

        setWidth(base.getWidth());
        setHeight(base.getHeight());
    }

    @Override
    public PlayerBase getModel() {
        return playerBase;
    }
}
