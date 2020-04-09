package org.beelinelibgdx.misslecommand;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.Align;

import org.beelinelibgdx.actors.BeelineActor;
import org.beelinelibgdx.actors.BeelineAssetManager;
import org.beelinelibgdx.actors.BeelineGroup;
import org.beelinelibgdx.actors.VisibleActor;
import org.beelinelibgdx.misslecommand.gamestate.Missle;
import org.beelinelibgdx.misslecommand.gamestate.MissleEventListener;
import org.beelinelibgdx.misslecommand.service.GameStateService;

import java.util.Random;

public class MissleActor extends BeelineGroup implements VisibleActor<Missle>, MissleEventListener {

    private final BeelineActor base;
    private Color color;
    private BeelineAssetManager assets;
    private BeelineActor line;
    private Missle missle;

    public MissleActor(BeelineAssetManager assets, Missle missle, Color color) {
        this.assets = assets;
        this.missle = missle;
        base = new BeelineActor(assets.createSprite(() -> "square"), 10, 10);
        this.color = color;
        base.setColor(color);
        addActor(base);
        line = new BeelineActor(assets.createSprite(() -> "square"), 4, 4);
        line.setColor(color);
        addActor(line);

        setWidth(base.getWidth());
        setHeight(base.getHeight());
    }

    public void refreshLine() {
        float lineDistance = GameStateService.getDistance(missle.x, missle.y, missle.xStart, missle.yStart);
        float lineAngle = GameStateService.getAngle(missle.x, missle.y, missle.xStart, missle.yStart);

        line.setHeight(lineDistance);
        line.setPosition(getWidth()/2, getHeight()/2, Align.bottom);
        line.setOrigin(line.getWidth()/2, 0);
        line.setRotation(lineAngle - 90);
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

        line.addAction(Actions.sequence(
                Actions.fadeOut(4),
                Actions.removeActor()
        ));

        Random random = new Random();
        for (int i = 0; i < 20; i++) {
            BeelineActor particle = new BeelineActor(assets.createSprite(() -> "square"), 4, 4);
            particle.setColor(color);
            particle.setPosition(getWidth()/2, getHeight()/2);
            particle.setOrigin(particle.getWidth()/2, particle.getHeight()/2);
            addActor(particle);

            int duration = 2;
            particle.addAction(Actions.sequence(
                    Actions.parallel(
                            Actions.moveBy(random.nextInt(400)-200, random.nextInt(400)-200, duration),
                            Actions.moveBy(0, -100, duration, Interpolation.circleIn),
                            Actions.rotateBy(random.nextInt(1000), duration),
                            Actions.fadeOut(duration)
                    ), Actions.removeActor()
            ));
        }
    }

    @Override
    public Missle getModel() {
        return missle;
    }

}