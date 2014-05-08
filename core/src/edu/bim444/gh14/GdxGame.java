package edu.bim444.gh14;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import edu.bim444.gh14.screen.Screen;
import edu.bim444.gh14.screen.ScreenTransition;

import java.util.Stack;

public class GdxGame implements ApplicationListener {

    public static final int SCREEN_CHANGE_NONE = 0;
    public static final int SCREEN_CHANGE_SET = 1;
    public static final int SCREEN_CHANGE_PUSH = 2;
    public static final int SCREEN_CHANGE_POP = 3;

    public final float VIRTUAL_WIDTH, VIRTUAL_HEIGHT;
    public final float ASPECT_RATIO;
    public final int UPDATES_PER_SECOND = 60;
    public final float DELTA = 1f / UPDATES_PER_SECOND;
    public final int MAX_UPDATE = 10;
    public final float MAX_DELTA = MAX_UPDATE * DELTA;
    public final int MIN_RENDER = 6;

    private float accumulator;
    private Stack<Screen> screens;
    private Rectangle viewport;
    private float scale;
    private ShapeRenderer shapeRenderer;
    private SpriteBatch spriteBatch;
    private Screen initialScreen;
    private Screen nextScreen;
    private int screenChangeType;
    private ScreenTransition screenTransition;
    private int renderCount;
    private boolean resized;

    private FPSLogger fpsLogger;

    public GdxGame(float virtualWidth, float virtualHeight) {
        VIRTUAL_WIDTH = virtualWidth;
        VIRTUAL_HEIGHT = virtualHeight;
        ASPECT_RATIO = VIRTUAL_WIDTH / VIRTUAL_HEIGHT;
        fpsLogger = new FPSLogger();
    }

    @Override
    public void create() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.input.setInputProcessor(new InputHandler());
        Gdx.graphics.setVSync(true);

        screens = new Stack<>();
        viewport = new Rectangle();

        shapeRenderer = new ShapeRenderer();
        spriteBatch = new SpriteBatch();

        screenTransition = new ScreenTransition(this, null, 0, Color.BLACK, 40);

        requestRendering();
    }

    @Override
    public void render() {
        accumulator += Gdx.graphics.getRawDeltaTime();

        if(accumulator > MAX_DELTA)
            accumulator = 0;

        while(accumulator >= DELTA) {
            updateScreen();
            accumulator -= DELTA;
        }

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        float alpha = accumulator / DELTA;
        drawScreen(alpha);

        checkRenderingRequest();
        checkScreenChange();

        if(renderCount > 0) {
            Gdx.graphics.requestRendering();
            renderCount--;
        }

        //fpsLogger.log();
    }

    public final void requestRendering() {
        renderCount = MIN_RENDER;
    }

    private void checkRenderingRequest() {
        if(screens.peek().isRenderingRequested() || !screenTransition.isPaused())
            requestRendering();
    }

    private void updateScreen() {
        screens.peek().updateScreen();
        if(!screenTransition.isPaused())
            screenTransition.update();
    }

    private void drawScreen(float alpha) {
        screens.peek().drawScreen(alpha);
        if(!screenTransition.isPaused())
            screenTransition.draw(alpha);
    }

    public void setScreen(Screen screen) {
        nextScreen = screen;
        screenChangeType = SCREEN_CHANGE_SET;

        if(screens.isEmpty()) {
            screens.push(nextScreen);
            nextScreen.init(this);
            screenChangeType = SCREEN_CHANGE_NONE;
        }
    }

    public void pushScreen(Screen screen) {
        nextScreen = screen;
        screenChangeType = SCREEN_CHANGE_PUSH;

        if(screens.isEmpty()) {
            screens.push(nextScreen);
            nextScreen.init(this);
            screenChangeType = SCREEN_CHANGE_NONE;
        }
    }

    public void popScreen() {
        screenChangeType = SCREEN_CHANGE_POP;
    }

    public void checkScreenChange() {
        if(screenChangeType == SCREEN_CHANGE_NONE)
            return;

        if(screenChangeType == SCREEN_CHANGE_SET) {
            screens.peek().dispose();
            screens.setElementAt(nextScreen, screens.size() - 1);
            nextScreen.init(this);
            accumulator = MAX_DELTA; // To reset accumulator in the next frame
        } else if(screenChangeType == SCREEN_CHANGE_PUSH) {
            screens.push(nextScreen);
            nextScreen.init(this);
            accumulator = MAX_DELTA; // To reset accumulator in the next frame
        } else if(screenChangeType == SCREEN_CHANGE_POP) {
            if(screens.size() <= 1)
                throw new RuntimeException("Cannot pop the screen since there is only one!");

            screens.pop().dispose();
        }

        screenChangeType = SCREEN_CHANGE_NONE;
    }

    public void setScreenTransition(Screen to, int screenChangeType, Color fadeColor) {
        if(screenTransition.isPaused()) {
            screenTransition.set(to, screenChangeType, fadeColor);
            screenTransition.start();
        }
    }

    public final void start(Screen initialScreen) {
        this.initialScreen = initialScreen;
        resume();
    }

    @Override
    public void resize(int width, int height) {
        float aspectRatio = (float) width / height;
        Vector2 crop = new Vector2(0, 0);

        // Cropping
        if(aspectRatio > ASPECT_RATIO) {
            scale = (float) width / VIRTUAL_WIDTH;
            crop.y = (height - VIRTUAL_HEIGHT * scale) / 2f;
        } else if(aspectRatio < ASPECT_RATIO) {
            scale = (float) height / VIRTUAL_HEIGHT;
            crop.x = (width - VIRTUAL_WIDTH * scale) / 2f;
        } else {
            scale = (float) width / VIRTUAL_WIDTH;
        }

        float w = VIRTUAL_WIDTH * scale;
        float h = VIRTUAL_HEIGHT * scale;
        viewport = new Rectangle(crop.x, crop.y, w, h);

        if(!resized) {
            setScreen(initialScreen);
            resized = true;
        }

        screens.peek().resize();
    }

    @Override
    public void pause() {
        screens.peek().pause();
    }

    @Override
    public void resume() {
        if(!screens.isEmpty())
            screens.peek().resume();
        requestRendering();
    }

    @Override
    public void dispose() {
        shapeRenderer.dispose();
        spriteBatch.dispose();

        screens.peek().dispose();
    }

    private class InputHandler extends InputAdapter {
        @Override
        public boolean touchDown(int deviceX, int deviceY, int pointer, int button) {
            requestRendering();
            return screens.peek().touchScreenDown(deviceX, deviceY, pointer);
        }

        @Override
        public boolean touchUp(int deviceX, int deviceY, int pointer, int button) {
            requestRendering();
            return screens.peek().touchScreenUp(deviceX, deviceY, pointer);
        }

        @Override
        public boolean touchDragged(int deviceX, int deviceY, int pointer) {
            requestRendering();
            return screens.peek().touchScreenDragged(deviceX, deviceY, pointer);
        }
    }

    public Rectangle getViewport() {
        return viewport;
    }

    public float getScale() {
        return scale;
    }

    public ShapeRenderer getShapeRenderer() {
        return shapeRenderer;
    }

    public SpriteBatch getSpriteBatch() {
        return spriteBatch;
    }

}
