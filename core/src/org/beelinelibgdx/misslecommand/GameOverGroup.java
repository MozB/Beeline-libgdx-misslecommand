package org.beelinelibgdx.misslecommand;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.Align;

import org.beelinelibgdx.actors.BeelineActor;
import org.beelinelibgdx.actors.BeelineAssetManager;
import org.beelinelibgdx.actors.BeelineGroup;
import org.beelinelibgdx.actors.BeelineLabel;
import org.beelinelibgdx.misslecommand.gamestate.GameState;

public class GameOverGroup extends BeelineGroup {
    public GameOverGroup(BeelineAssetManager assets, GameState gameState) {
        BeelineActor back = new BeelineActor(assets.createSprite(() -> "square"), 1000, 800);
        addActor(back);

        setWidth(back.getWidth());
        setHeight(back.getHeight());

        BeelineLabel title = new BeelineLabel("GAME OVER!", assets.getSkin());
        title.setColor(Color.BLACK);
        title.setFontScale(1.5f);
        title.setPositionAndResize(getWidth()/2, getHeight() - 50, Align.top);
        addActor(title);
    }
}
