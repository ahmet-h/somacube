package edu.bim444.gh14.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import edu.bim444.gh14.GdxGame;
import edu.bim444.gh14.entity.Entity;

public abstract class Screen {

    private GdxGame game;
    private OrthographicCamera cam;
    private Vector3 touch;
    private Array<Entity> entities;
    private Rectangle bounds;
    private World world;

    protected ShapeRenderer shapeRenderer;
    protected SpriteBatch spriteBatch;

    public void init(GdxGame game) {
        this.game = game;
        cam = new OrthographicCamera();
        cam.setToOrtho(false, game.VIRTUAL_WIDTH, game.VIRTUAL_HEIGHT);
        touch = new Vector3();
        entities = new Array<>();
        bounds = new Rectangle();

        shapeRenderer = game.getShapeRenderer();
        spriteBatch = game.getSpriteBatch();

        resize();
    }

    public final void updateScreen() {
        if(world != null) {
            world.updateWorld();
        }

        for(Entity entity : entities) {
            entity.updateEntity();
        }

        update();
    }

    public final void drawScreen(float alpha) {
        if(world != null) {
            world.drawWorld(alpha);
        }

        cam.update();
        shapeRenderer.setProjectionMatrix(cam.combined);
        spriteBatch.setProjectionMatrix(cam.combined);

        Rectangle viewport = game.getViewport();
        Gdx.gl.glViewport((int) viewport.x, (int) viewport.y, (int) viewport.width, (int) viewport.height);

        spriteBatch.begin();
        for(Entity entity : entities) {
            if(!entity.isHidden())
                entity.drawEntity(alpha);
        }

        draw(alpha);
        spriteBatch.end();
    }

    public final void addEntity(Entity entity) {
        entities.add(entity);
    }

    public final void removeEntity(Entity entity) {
        entities.removeValue(entity, true);
    }

    public final void setScreen(Screen screen) {
        game.setScreen(screen);
    }

    public final void pushScreen(Screen screen) {
        game.pushScreen(screen);
    }

    public final Screen popScreen() {
        return game.popScreen();
    }

    private void unproject(float deviceX, float deviceY, Vector3 touch) {
        Rectangle viewport = game.getViewport();
        cam.unproject(touch.set(deviceX, deviceY, 0), viewport.x, viewport.y, viewport.width, viewport.height);
    }

    public boolean touchScreenDown(float deviceX, float deviceY, int pointer) {
        unproject(deviceX, deviceY, touch);
        boolean result = false;
        for(Entity entity : entities) {
            if(!entity.isHidden() && entity.isTouchable() && entity.touchDown(touch.x, touch.y, pointer))
                result = true;
        }
        return result || touchDown(touch.x, touch.y, pointer) ||
                (world != null && world.touchWorldDown(deviceX, deviceY, pointer));
    }

    public boolean touchScreenUp(float deviceX, float deviceY, int pointer) {
        unproject(deviceX, deviceY, touch);
        boolean result = false;
        for(Entity entity : entities) {
            if(!entity.isHidden() && entity.isTouchable() && entity.touchUp(touch.x, touch.y, pointer))
                result = true;
        }
        return result || touchUp(touch.x, touch.y, pointer) ||
                (world != null && world.touchWorldUp(deviceX, deviceY, pointer));
    }

    public boolean touchScreenDragged(float deviceX, float deviceY, int pointer) {
        unproject(deviceX, deviceY, touch);
        boolean result = false;
        for(Entity entity : entities) {
            if(!entity.isHidden() && entity.isTouchable() && entity.touchDragged(touch.x, touch.y, pointer))
                result = true;
        }
        return result || touchDragged(touch.x, touch.y, pointer) ||
                (world != null && world.touchWorldDragged(deviceX, deviceY, pointer));
    }

    public final float getLeft() {
        return bounds.x;
    }

    public final float getTop() {
        return bounds.y + bounds.height;
    }

    public final float getRight() {
        return bounds.x + bounds.width;
    }

    public final float getBottom() {
        return bounds.y;
    }

    public final float getCenterX() {
        return bounds.x + bounds.width / 2;
    }

    public final float getCenterY() {
        return bounds.y + bounds.height / 2;
    }

    public final void resize() {
        if(game.getViewport() == null)
            return;

        Rectangle viewport = game.getViewport();
        float scale = game.getScale();
        bounds.x = -viewport.x / scale;
        bounds.y = -viewport.y / scale;
        bounds.width = (viewport.width + viewport.x * 2) / scale;
        bounds.height = (viewport.height + viewport.y * 2) / scale;
    }

    public abstract void update();

    public abstract void draw(float alpha);

    public void pause() {

    }

    public void resume() {

    }

    public void dispose() {
        if(world != null)
            world.dispose();

        for(Entity entity : entities) {
            entity.dispose();
        }
    }

    public boolean touchDown(float screenX, float screenY, int pointer) {
        return false;
    }

    public boolean touchUp(float screenX, float screenY, int pointer) {
        return false;
    }

    public boolean touchDragged(float screenX, float screenY, int pointer) {
        return false;
    }

    public boolean isRenderingRequested() {
        return world != null && world.isRenderingRequested();
    }

    public GdxGame getGame() {
        return game;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public World getWorld() {
        return world;
    }

    public ShapeRenderer getShapeRenderer() {
        return shapeRenderer;
    }

    public SpriteBatch getSpriteBatch() {
        return spriteBatch;
    }

}
