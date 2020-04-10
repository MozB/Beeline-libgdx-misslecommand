package org.beelinelibgdx.misslecommand;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

import org.beelinelibgdx.actors.BeelineActor;
import org.beelinelibgdx.actors.BeelineAssetManager;
import org.beelinelibgdx.actors.BeelineGroup;
import org.beelinelibgdx.actors.VisibleActor;
import org.beelinelibgdx.misslecommand.gamestate.PlayerBase;
import org.beelinelibgdx.misslecommand.gamestate.PlayerBaseListener;

public class PlayerBaseActor extends BeelineGroup implements VisibleActor<PlayerBase>, PlayerBaseListener {

    private final BeelineActor base;
    private PlayerBase playerBase;

    public PlayerBaseActor(BeelineAssetManager assets, PlayerBase playerBase) {
        this.playerBase = playerBase;
        base = new BeelineActor(assets.createSprite(() -> "square"), playerBase.width, playerBase.height);
        base.setColor(Color.YELLOW);
        addActor(base);

        setWidth(base.getWidth());
        setHeight(base.getHeight());
    }

    @Override
    public PlayerBase getModel() {
        return playerBase;
    }

    @Override
    public void onPlayerBaseDestroyed() {
        float duration = 0.1f;
        base.addAction(Actions.sequence(
                Actions.fadeOut(duration),
                Actions.fadeIn(duration),
                Actions.fadeOut(duration),
                Actions.fadeIn(duration),
                Actions.fadeOut(duration * 2),
                Actions.removeActor()
        ));
    }
}
