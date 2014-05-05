package edu.bim444.gh14.entity;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import edu.bim444.gh14.screen.Screen;

public class UIJoystick extends TouchEntity {

    public static final float SCALE = 1.25f;
    public static final float ALPHA = 0.8f;

    private Texture base;
    private JoystickHead head;
    private boolean directed;

    private UIJoystickListener listener;

    public UIJoystick(Texture base, Texture head, Screen screen) {
        super(base.getWidth() * SCALE, base.getHeight() * SCALE, screen);
        setTouchable(true);

        this.base = base;
        this.head = new JoystickHead(head, base.getWidth()/3f, getScreen());
    }

    @Override
    public void update() {
        head.update();
    }

    @Override
    public void draw(float alpha) {
        Color c = spriteBatch.getColor();
        spriteBatch.setColor(c.r, c.g, c.b, ALPHA);

        spriteBatch.draw(base, getLeft(), getBottom(), getWidth(), getHeight());

        spriteBatch.setColor(c.r, c.g, c.b, 1);

        head.draw(alpha);
    }

    @Override
    public boolean touchDown(float x, float y, int pointer) {
        return head.touchDown(x, y, pointer) || super.touchDown(x, y, pointer);
    }

    @Override
    public boolean touchUp(float x, float y, int pointer) {
        return head.touchUp(x, y, pointer) || super.touchUp(x, y, pointer);
    }

    @Override
    public boolean touchDragged(float x, float y, int pointer) {
        return head.touchDragged(x, y, pointer) || super.touchDragged(x, y, pointer);
    }

    @Override
    public void moveTo(float x, float y) {
        super.moveTo(x, y);
        head.moveTo(getLeft() + getWidth()/2, getBottom() + getHeight()/2);
        head.updateCenter();
    }

    public void setUIJoystickListener(UIJoystickListener listener) {
        this.listener = listener;
    }

    private class JoystickHead extends TouchEntity {

        public final Vector2 data;

        private final float TOUCH_RADIUS2;

        private Texture head;
        private float radius;
        private float radius2;
        private Vector2 center;
        private Vector2 delta;
        private Vector2 tmpV;
        private Vector2 tmpV2;
        private Vector2 offset;

        public JoystickHead(Texture head, float radius, Screen screen) {
            super(head.getWidth() * SCALE, head.getHeight() * SCALE, screen);

            TOUCH_RADIUS2 = (getWidth() / 2 + 24) * (getWidth() / 2 + 24);

            this.head = head;
            this.radius = radius;
            this.radius2 = this.radius * this.radius;
            center = new Vector2(getLeft() + getWidth()/2, getBottom() + getHeight()/2);
            delta = new Vector2();
            tmpV = new Vector2();
            tmpV2 = new Vector2();
            data = new Vector2();
            offset = new Vector2();
        }

        @Override
        public boolean contains(float x, float y) {
            return tmpV2.set(getLeft() + getWidth()/2, getBottom() + getHeight()/2).dst2(x, y) < TOUCH_RADIUS2;
        }

        public void updateCenter() {
            center.set(getLeft() + getWidth()/2, getBottom() + getHeight()/2);
        }

        public void updateData() {
            float cx = getLeft() + getWidth()/2;
            float cy = getBottom() + getHeight()/2;

            data.set(cx, cy).sub(center);
        }

        @Override
        public void update() {
            super.update();
        }

        @Override
        public void draw(float alpha) {
            spriteBatch.draw(head, getLeft(), getBottom(), getWidth(), getHeight());
        }

        @Override
        public boolean touchDown(float x, float y, int pointer) {
            boolean result = super.touchDown(x, y, pointer);
            if(isTouched()) {
                delta.set(x, y);
                offset.set(x, y).sub(center);
            }
            updateData();
            return result;
        }

        @Override
        public boolean touchUp(float x, float y, int pointer) {
            delta.set(0, 0);
            moveTo(center);
            updateData();
            directed = false;
            boolean b = isTouched();
            super.touchUp(x, y, pointer);
            return b;
        }

        @Override
        public boolean touchDragged(float x, float y, int pointer) {
            if(isTouched()) {
                delta.sub(x, y).scl(-1);
                moveBy(delta);
                delta.set(x, y);

                float cx = getLeft() + getWidth()/2;
                float cy = getBottom() + getHeight()/2;
                if(tmpV.set(cx, cy).sub(center).len2() > radius2) {
                    tmpV.nor().scl(radius).add(center);
                    moveTo(tmpV);
                    if(!contains(x, y)) {
                        delta.set(tmpV.add(offset));
                    }

                    // Listener Event
                    int dir;
                    updateData();
                    if(Math.abs(data.x) > Math.abs(data.y))
                        dir = (data.x < 0) ? UIJoystickListener.LEFT : UIJoystickListener.RIGHT;
                    else
                        dir = (data.y < 0) ? UIJoystickListener.DOWN : UIJoystickListener.UP;
                    if(listener != null && !directed)
                        listener.onDirection(dir);
                    directed = true;
                }

                offset.set(x, y).sub(cx, cy);

                updateData();
            }
            return isTouched();
        }

    }

}
