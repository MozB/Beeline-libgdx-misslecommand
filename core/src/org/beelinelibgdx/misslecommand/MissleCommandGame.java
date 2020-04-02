package org.beelinelibgdx.misslecommand;

import org.beelinelibgdx.BeelineGame;
import org.beelinelibgdx.misslecommand.gamestate.GameState;

public class MissleCommandGame extends BeelineGame {

	public MissleCommandGame() {
		super(1920, 1080);
	}

	@Override
	public void create() {
		super.create();
		GameState gameState = new GameState();
		MissleCommandScreen screen = new MissleCommandScreen(getAssetManager(), getViewport(), gameState);
		setScreen(screen);
	}
}
