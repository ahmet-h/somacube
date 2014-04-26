package edu.bim444.gh14.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import edu.bim444.gh14.entity.Entity;

public abstract class World {

    private Screen screen;
    private Vector3 touch;
    private Array<Entity> entities;
    private Camera cam;

    protected ShapeRenderer shapeRenderer;
    protected SpriteBatch spriteBatch;

    public World(Screen screen) {
        this.screen = screen;
        touch = new Vector3();
        entities = new Array<>();

        shapeRenderer = screen.getShapeRenderer();
        spriteBatch = screen.getSpriteBatch();
    }

    public void updateWorld() {
        for(Entity entity : entities) {
            entity.updateEntity();
        }

        update();
    }

    public void drawWorld(float alpha) {
        cam.update();
        shapeRenderer.setProjectionMatrix(cam.combined);
        spriteBatch.setProjectionMatrix(cam.combined);

        Rectangle viewport = screen.getGame().getViewport();
        Gdx.gl.glViewport((int) viewport.x, (int) viewport.y, (int) viewport.width, (int) viewport.height);

        for(Entity entity : entities) {
            if(!entity.isHidden())
                entity.drawEntity(alpha);
        }

        draw(alpha);
    }

    public final void addEntity(Entity entity) {
        entities.add(entity);
    }

    public final void removeEntity(Entity entity) {
        entities.removeValue(entity, true);
    }

    public final int getSize() {
        return entities.size;
    }

    public final Entity getEntity(int index) {
        return entities.get(index);
    }

    private void unproject(float deviceX, float deviceY, Vector3 touch) {
        Rectangle viewport = screen.getGame().getViewport();
        cam.unproject(touch.set(deviceX, deviceY, 0), viewport.x, viewport.y, viewport.width, viewport.height);
    }

    public boolean touchWorldDown(float deviceX, float deviceY, int pointer) {
        unproject(deviceX, deviceY, touch);
        boolean result = false;
        for(Entity entity : entities) {
            if(!entity.isHidden() && entity.isTouchable() && entity.touchDown(touch.x, touch.y, pointer))
                result = true;
        }
        return result || touchDown(touch.x, touch.y, pointer);
    }

    public boolean touchWorldUp(float deviceX, float deviceY, int pointer) {
        unproject(deviceX, deviceY, touch);
        boolean result = false;
        for(Entity entity : entities) {
            if(!entity.isHidden() && entity.isTouchable() && entity.touchUp(touch.x, touch.y, pointer))
                result = true;
        }
        return result || touchUp(touch.x, touch.y, pointer);
    }

    public boolean touchWorldDragged(float deviceX, float deviceY, int pointer) {
        unproject(deviceX, deviceY, touch);
        boolean result = false;
        for(Entity entity : entities) {
            if(!entity.isHidden() && entity.isTouchable() && entity.touchDragged(touch.x, touch.y, pointer))
                result = true;
        }
        return result || touchDragged(touch.x, touch.y, pointer);
    }

    public abstract void update();

    public abstract void draw(float alpha);

    public void dispose() {
        for(Entity entity : entities) {
            entity.dispose();
        }
    }

    public boolean touchDown(float worldX, float worldY, int pointer) {
        return false;
    }

    public boolean touchUp(float worldX, float worldY, int pointer) {
        return false;
    }

    public boolean touchDragged(float worldX, float worldY, int pointer) {
        return false;
    }

    public boolean isRenderingRequested() {
        return false;
    }

    protected void setCamera(Camera cam) {
        this.cam = cam;
    }

    public Camera getCamera() {
        return cam;
    }

    public Screen getScreen() {
        return screen;
    }
}
