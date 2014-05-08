package edu.bim444.gh14.entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import edu.bim444.gh14.screen.Screen;

public abstract class Entity {

    private Screen screen;
    private Vector3 position;
    private Vector3 anchor;
    private Rectangle bounds;
    private boolean hidden;
    private boolean touchable;

    protected ShapeRenderer shapeRenderer;
    protected SpriteBatch spriteBatch;

    public Entity(float positionX, float positionY, float width, float height, float anchorX, float anchorY, Screen screen) {
        this.screen = screen;
        position = new Vector3(positionX, positionY, 0);
        anchor = new Vector3(anchorX, anchorY, 0);
        bounds = new Rectangle(0, 0, width, height);
        updateBounds();

        shapeRenderer = screen.getShapeRenderer();
        spriteBatch = screen.getSpriteBatch();
    }

    public Entity(float positionX, float positionY, float width, float height, Screen screen) {
        this(positionX, positionY, width, height, width / 2, height / 2, screen);
    }

    public Entity(float width, float height, Screen screen) {
        this(0, 0, width, height, screen);
    }

    public Entity(Screen screen) {
        this(0, 0, screen);
    }

    public final void updateEntity() {
        update();
    }

    public final void drawEntity(float alpha) {
        updateBounds();

        draw(alpha);
    }

    public void updateBounds() {
        bounds.x = position.x - anchor.x;
        bounds.y = position.y - anchor.y;
    }

    public void setWidth(float width) {
        bounds.width = width;
    }

    public void setHeight(float height) {
        bounds.height = height;
    }

    public boolean contains(float x, float y) {
        return bounds.contains(x, y);
    }

    public boolean contains(Vector2 point) {
        return contains(point.x, point.y);
    }

    public void moveBy(float x, float y) {
        position.add(x, y, 0);

        updateBounds();
    }

    public void moveBy(Vector2 delta) {
        moveBy(delta.x, delta.y);
    }

    public void moveTo(float x, float y) {
        position.set(x, y, 0);

        updateBounds();
    }

    public void moveTo(Vector2 point) {
        moveTo(point.x, point.y);
    }

    public void moveBy(float x, float y, float z) {
        position.add(x, y, z);

        updateBounds();
    }

    public void moveBy(Vector3 delta) {
        moveBy(delta.x, delta.y, delta.z);
    }

    public void moveTo(float x, float y, float z) {
        position.set(x, y, z);

        updateBounds();
    }

    public void moveTo(Vector3 point) {
        moveTo(point.x, point.y, point.z);
    }

    public float getX() {
        return position.x;
    }

    public float getY() {
        return position.y;
    }

    public float getZ() {
        return position.z;
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

    public final float getWidth() {
        return bounds.getWidth();
    }

    public final float getHeight() {
        return bounds.getHeight();
    }

    public void dispose() {

    }

    public abstract void update();

    public abstract void draw(float alpha);

    public boolean touchDown(float x, float y, int pointer) {
        return false;
    }

    public boolean touchUp(float x, float y, int pointer) {
        return false;
    }

    public boolean touchDragged(float x, float y, int pointer) {
        return false;
    }

    public void setTouchable(boolean touchable) {
        this.touchable = touchable;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    public boolean isTouchable() {
        return touchable;
    }

    public boolean isHidden() {
        return hidden;
    }

    public Screen getScreen() {
        return screen;
    }

    public void setAnchor(float x, float y) {
        anchor.set(x, y, 0);
    }

    public void setAnchor(float x, float y, float z) {
        anchor.set(x, y, z);
    }

}
