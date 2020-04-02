package org.beelinelibgdx.misslecommand;

import org.beelinelibgdx.BeelineGame;

public class MissleCommandGame extends BeelineGame {

	public MissleCommandGame() {
		super(1920, 1080);
	}

	@Override
	public void create() {
		super.create();
		MissleCommandScreen screen = new MissleCommandScreen(getAssetManager(), getViewport());
		setScreen(screen);
	}
}
