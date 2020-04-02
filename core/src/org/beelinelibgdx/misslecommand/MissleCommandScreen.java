package org.beelinelibgdx.misslecommand;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.Viewport;

import org.beelinelibgdx.actors.BeelineActor;
import org.beelinelibgdx.actors.BeelineAssetManager;
import org.beelinelibgdx.actors.BeelineNinePatch;
import org.beelinelibgdx.actors.NinePatchStyle;
import org.beelinelibgdx.actors.NinePatchStyleBuilder;
import org.beelinelibgdx.screens.BeelineScreen;

public class MissleCommandScreen extends BeelineScreen {
    public MissleCommandScreen(BeelineAssetManager assets, Viewport v) {
        super(v);

        NinePatchStyle style = new NinePatchStyleBuilder().withTexture(() -> "square").build();
        BeelineNinePatch title = new BeelineNinePatch(assets.createNinePatchStyle(style), 630, 50,
                "Missle Command with Beeline-libgdx");
        title.getLabel().setFontScale(0.6f);
        title.getLabel().setColor(Color.BLACK);
        title.setPosition(getWidth() / 2, getHeight() - 10, Align.top);
        addActor(title);
    }
}
