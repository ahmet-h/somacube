package edu.bim444.gh14.soma.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import edu.bim444.gh14.GdxGame;
import edu.bim444.gh14.screen.Screen;

public class SplashScreen extends Screen {

    @Override
    public void init(GdxGame game) {
        super.init(game);

        Gdx.gl20.glClearColor(0, 0, 0, 1);
    }

    @Override
    public void update() {
        getGame().setScreenTransition(new TitleScreen(), GdxGame.SCREEN_CHANGE_SET, Color.BLACK);
    }

    @Override
    public void draw(float alpha) {

    }

}
