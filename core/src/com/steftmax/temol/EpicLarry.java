package com.steftmax.temol;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.steftmax.temol.screen.GameScreen;

public class EpicLarry extends Game {

	public static boolean DEBUG = false;
	
	/* (non-Javadoc)
	 * @see com.badlogic.gdx.ApplicationListener#create()
	 */
	@SuppressWarnings("static-access")
	@Override
	public void create() {
		

		if (DEBUG)
			Gdx.app.setLogLevel(Gdx.app.LOG_DEBUG);
		
		Gdx.gl.glEnable(Gdx.gl.GL_BLEND); // enable blending for the whole game
		Gdx.gl.glBlendFunc(Gdx.gl.GL_ONE, Gdx.gl.GL_ONE_MINUS_SRC_ALPHA);
		
		setScreen(new GameScreen(this));
	}

}
