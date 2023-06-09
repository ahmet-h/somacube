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

    public final int getSize() {
        return entities.size;
    }

    public final Entity getEntity(int index) {
        return entities.get(index);
    }

    public final Array<Entity> getEntities() {
        return entities;
    }

    private void unproject(float deviceX, float deviceY, Vector3 touch) {
        Rectangle viewport = screen.getGame().getViewport();
        cam.unproject(touch.set(deviceX, deviceY, 0), viewport.x, viewport.y, viewport.width, viewport.height);
    }

    public boolean touchWorldDown(float deviceX, float deviceY, int pointer) {
        unproject(deviceX, deviceY, touch);
        for(int i = entities.size - 1; i >= 0; i--) {
            if(!entities.get(i).isHidden() && entities.get(i).isTouchable() && entities.get(i).touchDown(touch.x, touch.y, pointer))
                return true;
        }
        return touchDown(touch.x, touch.y, pointer);
    }

    public boolean touchWorldUp(float deviceX, float deviceY, int pointer) {
        unproject(deviceX, deviceY, touch);
        for(int i = entities.size - 1; i >= 0; i--) {
            if(!entities.get(i).isHidden() && entities.get(i).isTouchable() && entities.get(i).touchUp(touch.x, touch.y, pointer))
                return true;
        }
        return touchUp(touch.x, touch.y, pointer);
    }

    public boolean touchWorldDragged(float deviceX, float deviceY, int pointer) {
        unproject(deviceX, deviceY, touch);
        for(int i = entities.size - 1; i >= 0; i--) {
            if(!entities.get(i).isHidden() && entities.get(i).isTouchable() && entities.get(i).touchDragged(touch.x, touch.y, pointer))
                return true;
        }
        return touchDragged(touch.x, touch.y, pointer);
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

    public void moveCameraBy(float deltaX, float deltaY, float deltaZ) {
        cam.translate(deltaX, deltaY, deltaZ);
    }

    public void moveCameraBy(Vector3 delta) {
        cam.translate(delta.x, delta.y, delta.z);
    }

    public void moveCameraTo(float x, float y, float z) {
        cam.position.set(x, y, z);
    }

    public void moveCameraTo(Vector3 point) {
        cam.position.set(point.x, point.y, point.z);
    }

}
