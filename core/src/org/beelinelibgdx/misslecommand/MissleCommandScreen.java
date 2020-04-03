package org.beelinelibgdx.misslecommand;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.Viewport;

import org.beelinelibgdx.actors.BeelineActor;
import org.beelinelibgdx.actors.BeelineAssetManager;
import org.beelinelibgdx.actors.BeelineNinePatch;
import org.beelinelibgdx.actors.ModelAndActorVisibilityContract;
import org.beelinelibgdx.actors.NinePatchStyle;
import org.beelinelibgdx.actors.NinePatchStyleBuilder;
import org.beelinelibgdx.misslecommand.gamestate.GameState;
import org.beelinelibgdx.misslecommand.gamestate.Missle;
import org.beelinelibgdx.misslecommand.gamestate.PlayerBase;
import org.beelinelibgdx.misslecommand.service.GameStateService;
import org.beelinelibgdx.screens.BeelineScreen;

import static com.google.common.collect.Lists.newArrayList;

public class MissleCommandScreen extends BeelineScreen {
    private final GameState gameState;
    private final ModelAndActorVisibilityContract<PlayerBase, PlayerBaseActor> playerBasesContract;
    private final ModelAndActorVisibilityContract<Missle, MissleActor> computerMisslesContract;
    private final GameStateService gameStateService;

    public MissleCommandScreen(BeelineAssetManager assets, Viewport v, GameState gameState) {
        super(v);
        this.gameStateService = new GameStateService();
        this.gameState = gameState;

        NinePatchStyle style = new NinePatchStyleBuilder().withTexture(() -> "square").build();
        BeelineNinePatch title = new BeelineNinePatch(assets.createNinePatchStyle(style), 630, 50,
                "Missle Command with Beeline-libgdx");
        title.getLabel().setFontScale(0.6f);
        title.getLabel().setColor(Color.BLACK);
        title.setPosition(getWidth() / 2, getHeight() - 10, Align.top);
        addActor(title);

        playerBasesContract = new ModelAndActorVisibilityContract<PlayerBase, PlayerBaseActor>(this.getRoot(), gameState.playerBases, newArrayList()) {
            @Override
            public void forEachFrame(PlayerBase model, PlayerBaseActor actor) {
                // nothing for now
            }

            @Override
            public PlayerBaseActor createActor(PlayerBase model) {
                PlayerBaseActor playerBaseActor = new PlayerBaseActor(assets, model);
                playerBaseActor.setPosition(model.x, model.y, Align.bottom);
                return playerBaseActor;
            }
        };

        computerMisslesContract = new ModelAndActorVisibilityContract<Missle, MissleActor>(this.getRoot()   , gameState.computerMissles, newArrayList()) {
            @Override
            public void forEachFrame(Missle model, MissleActor actor) {
                actor.setPosition(model.x, model.y, Align.center);
            }
            @Override
            public MissleActor createActor(Missle model) {
                MissleActor actor = new MissleActor(assets, model);
                return actor;
            }
        };
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        gameStateService.eachFrame(gameState);
        playerBasesContract.refresh();
        computerMisslesContract.refresh();

    }
}
