package org.beelinelibgdx.misslecommand;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.Viewport;

import org.beelinelibgdx.actors.BeelineActor;
import org.beelinelibgdx.actors.BeelineAssetManager;
import org.beelinelibgdx.actors.BeelineLabel;
import org.beelinelibgdx.actors.BeelineNinePatch;
import org.beelinelibgdx.actors.ModelAndActorVisibilityContract;
import org.beelinelibgdx.actors.NinePatchStyle;
import org.beelinelibgdx.actors.NinePatchStyleBuilder;
import org.beelinelibgdx.misslecommand.gamestate.GameState;
import org.beelinelibgdx.misslecommand.gamestate.GameStateListener;
import org.beelinelibgdx.misslecommand.gamestate.Missle;
import org.beelinelibgdx.misslecommand.gamestate.PlayerBase;
import org.beelinelibgdx.misslecommand.service.GameStateService;
import org.beelinelibgdx.screens.BeelineScreen;

import static com.google.common.collect.Lists.newArrayList;

public class MissleCommandScreen extends BeelineScreen implements GameStateListener {
    private final GameState gameState;
    private final ModelAndActorVisibilityContract<PlayerBase, PlayerBaseActor> playerBasesContract;
    private final ModelAndActorVisibilityContract<Missle, MissleActor> computerMisslesContract;
    private final ModelAndActorVisibilityContract<Missle, MissleActor> playerMisslesContract;
    private final GameStateService gameStateService;
    private final BeelineAssetManager assets;
    private final BeelineLabel scoreLabel;
    private final BeelineActor cannon;

    public MissleCommandScreen(BeelineAssetManager assets, Viewport v, GameState gameState) {
        super(v);
        this.assets = assets;
        this.gameStateService = new GameStateService();
        this.gameState = gameState;
        this.gameState.gameStateListeners.add(this);

        BeelineActor background = new BeelineActor(assets.createSprite(() -> "square"),
                MissleCommandGame.getWidth(), MissleCommandGame.getHeight());
        background.setColor(Color.BLACK);
        background.clearListeners();
        background.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                gameStateService.generateNewPlayerMissle(gameState, x, y);
                return false;
            }
        });
        addActor(background);

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
                model.playerBaseListeners.add(playerBaseActor);
                return playerBaseActor;
            }
        };

        computerMisslesContract = new ModelAndActorVisibilityContract<Missle, MissleActor>(this.getRoot(), gameState.computerMissles, newArrayList()) {
            @Override
            public void forEachFrame(Missle model, MissleActor actor) {
                actor.setPosition(model.x, model.y, Align.center);
                actor.refreshLine();
            }
            @Override
            public MissleActor createActor(Missle model) {
                MissleActor actor = new MissleActor(assets, model, Color.RED);
                model.missleEventListeners.add(actor);
                return actor;
            }
        };

        playerMisslesContract = new ModelAndActorVisibilityContract<Missle, MissleActor>(this.getRoot(), gameState.playerMissles, newArrayList()) {
            @Override
            public void forEachFrame(Missle model, MissleActor actor) {
                actor.setPosition(model.x, model.y, Align.center);
                actor.refreshLine();
            }
            @Override
            public MissleActor createActor(Missle model) {
                MissleActor actor = new MissleActor(assets, model, Color.YELLOW);
                model.missleEventListeners.add(actor);
                return actor;
            }
        };

        scoreLabel = new BeelineLabel("", assets.getSkin());
        scoreLabel.setFontScale(0.6f);
        addActor(scoreLabel);
        onScoreChanged(0);

        cannon = new BeelineActor(assets.createSprite(() -> "square"), 25, 200);
        cannon.setOrigin(cannon.getWidth()/2, 100);
        cannon.setColor(Color.YELLOW);
        cannon.setPosition(getWidth()/2, -100, Align.bottom);
        addActor(cannon);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        if (!gameState.gameOver) {
            gameStateService.eachFrame(gameState);
            playerBasesContract.refresh();
            computerMisslesContract.refresh();
            playerMisslesContract.refresh();
        }
    }

    @Override
    public void onGameOver() {
        GameOverGroup gameOverGroup = new GameOverGroup(assets, gameState);
        gameOverGroup.setPosition(getWidth()/2, getHeight()/2, Align.center);
        gameOverGroup.setTransform(true);
        gameOverGroup.setVisible(false);
        gameOverGroup.addAction(Actions.sequence(
                Actions.delay(1),
                Actions.fadeOut(0),
                Actions.visible(true),
                Actions.fadeIn(1)
        ));
        addActor(gameOverGroup);
    }

    @Override
    public void onScoreChanged(int scoreDelta) {
        scoreLabel.setText("Score: " + gameState.score);
        scoreLabel.setPositionAndResize(10, getHeight()-10, Align.topLeft);
    }

    @Override
    public void onPlayerMissleFired(Missle missle) {
        float lineAngle = GameStateService.getAngle(missle.xStart, missle.yStart, missle.xTarget, missle.yTarget);
        cannon.setRotation(lineAngle - 90);
        cannon.addAction(
                Actions.sequence(
                        Actions.sizeTo(cannon.getWidth(), 100, 0.2f),
                        Actions.sizeTo(cannon.getWidth(), 200, 0.8f, Interpolation.sineOut)
                )
        );
    }
}
