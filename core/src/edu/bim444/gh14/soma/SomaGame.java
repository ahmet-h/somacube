package edu.bim444.gh14.soma;

import com.badlogic.gdx.Gdx;
import edu.bim444.gh14.GdxGame;
import edu.bim444.gh14.soma.screen.SplashScreen;

public class SomaGame extends GdxGame {

    public SomaGame() {
        super(1280, 768);
    }

    @Override
	public void create () {
        super.create();

        Assets.load();

        Gdx.gl.glClearColor(0.8f, 0.6f, 0.4f, 1);
        Gdx.graphics.setContinuousRendering(false);
        requestRendering();
        start(new SplashScreen());
	}

    @Override
    public void dispose() {
        super.dispose();

        Assets.dispose();
    }
}
