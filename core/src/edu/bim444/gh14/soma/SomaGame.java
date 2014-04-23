package edu.bim444.gh14.soma;

import com.badlogic.gdx.Gdx;
import edu.bim444.gh14.GdxGame;
import edu.bim444.gh14.soma.screen.GameScreen;

public class SomaGame extends GdxGame {

    public SomaGame() {
        super(1280, 768);
    }

    @Override
	public void create () {
        super.create();

        Assets.load();

        Gdx.gl.glClearColor(0.8f, 0.8f, 0.8f, 1);
        Gdx.graphics.setContinuousRendering(false);
        requestRendering();
        start(new GameScreen());
	}

}
