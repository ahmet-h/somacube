package edu.bim444.gh14.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import edu.bim444.gh14.GdxGame;
import edu.bim444.gh14.entity.Animator;
import edu.bim444.gh14.entity.AnimatorListener;
import edu.bim444.gh14.entity.Interpolator;

public class ScreenTransition {

    private GdxGame game;
    private Screen nextScreen;
    private int screenChangeType;
    private Color fadeColor;
    private Animator fadeInAnimator;
    private Animator fadeOutAnimator;
    private boolean paused;

    public ScreenTransition(GdxGame game, Screen to, int screenChangeType, Color fadeColor, int duration) {
        this.game = game;
        this.nextScreen = to;
        this.screenChangeType = screenChangeType;
        this.fadeColor = fadeColor;
        fadeOutAnimator = new Animator(0, 1, duration/2, Interpolator.DECELERATE);
        fadeOutAnimator.setAnimatorListener(new AnimatorListener() {
            @Override
            public void onAnimationEnd() {
                if(ScreenTransition.this.screenChangeType == GdxGame.SCREEN_CHANGE_SET)
                    ScreenTransition.this.game.setScreen(nextScreen);
                else if(ScreenTransition.this.screenChangeType == GdxGame.SCREEN_CHANGE_PUSH)
                    ScreenTransition.this.game.pushScreen(nextScreen);
                else if(ScreenTransition.this.screenChangeType == GdxGame.SCREEN_CHANGE_POP)
                    ScreenTransition.this.game.popScreen();

                fadeInAnimator.start();
            }
        });

        fadeInAnimator = new Animator(1, 0, duration/2, Interpolator.DECELERATE);
        fadeInAnimator.setAnimatorListener(new AnimatorListener() {
            @Override
            public void onAnimationEnd() {
                paused = true;
            }
        });

        paused = true;
    }

    public void set(Screen to, int screenChangeType, Color fadeColor) {
        this.nextScreen = to;
        this.screenChangeType = screenChangeType;
        this.fadeColor.set(fadeColor);

        fadeOutAnimator.stop();
        fadeInAnimator.stop();
    }

    public void start() {
        if(isPaused()) {
            fadeOutAnimator.start();
            paused = false;
        }
    }

    public void update() {
        boolean fadeOutPaused = fadeOutAnimator.isPaused();
        boolean fadeInPaused = fadeInAnimator.isPaused();

        fadeOutAnimator.update();
        fadeInAnimator.update();

        if(!fadeOutPaused)
            fadeColor.a = fadeOutAnimator.getCurrentValue();
        else if(!fadeInPaused)
            fadeColor.a = fadeInAnimator.getCurrentValue();
        else
            fadeColor.a = 0;
    }

    public void draw(float alpha) {
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        game.getShapeRenderer().setColor(fadeColor);
        game.getShapeRenderer().begin(ShapeRenderer.ShapeType.Filled);
        game.getShapeRenderer().rect(0, 0, game.VIRTUAL_WIDTH, game.VIRTUAL_HEIGHT);
        game.getShapeRenderer().end();
        Gdx.gl.glDisable(GL20.GL_BLEND);
    }

    public boolean isPaused() {
        return paused;
    }

}
