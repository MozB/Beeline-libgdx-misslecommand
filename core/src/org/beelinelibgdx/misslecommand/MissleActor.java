package org.beelinelibgdx.misslecommand;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import com.badlogic.gdx.utils.Align;

import org.beelinelibgdx.actors.BeelineActor;
import org.beelinelibgdx.actors.BeelineAssetManager;
import org.beelinelibgdx.actors.BeelineGroup;
import org.beelinelibgdx.actors.VisibleActor;
import org.beelinelibgdx.misslecommand.gamestate.Missle;
import org.beelinelibgdx.misslecommand.gamestate.MissleEventListener;

import java.util.List;

public class MissleActor extends BeelineGroup implements VisibleActor<Missle>, MissleEventListener {

    private final BeelineActor base;
    private BeelineAssetManager assets;
    private Missle missle;

    public MissleActor(BeelineAssetManager assets, Missle missle) {
        this.assets = assets;
        this.missle = missle;
        base = new BeelineActor(assets.createSprite(() -> "square"), 10, 10);
        addActor(base);

        setWidth(base.getWidth());
        setHeight(base.getHeight());
    }

    @Override
    public void onMissleReachedTarget(Missle missle) {
        BeelineActor explosion = new BeelineActor(assets.createSprite(() -> "circle"), 1, 1);
        explosion.setOrigin(explosion.getWidth()/2, explosion.getHeight()/2);
        explosion.setTouchable(Touchable.disabled);
        explosion.setColor(Color.RED);
        explosion.setPosition(5, 5, Align.center);
        addActor(explosion);

        explosion.addAction(Actions.sequence(
                Actions.scaleTo(200, 200, 0.1f),
                Actions.color(Color.YELLOW, 0.1f),
                Actions.color(Color.RED, 0.1f),
                Actions.color(Color.BLACK, 0.1f),
                Actions.removeActor()
        ));

        onMissleDestroyed(missle);
    }

    @Override
    public void onMissleDestroyed(Missle missle) {
        base.remove();
    }

    @Override
    public Missle getModel() {
        return missle;
    }

}