package edu.bim444.gh14.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import edu.bim444.gh14.screen.Screen;

public class UIJoystick extends Entity {

    public static final float SCALE = 1.25f;

    private Texture base;
    private JoystickHead head;

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
        spriteBatch.begin();
        spriteBatch.draw(base, getLeft(), getBottom(), getWidth(), getHeight());
        spriteBatch.end();

        head.draw(alpha);
    }

    @Override
    public boolean touchDown(float x, float y, int pointer) {
        return head.touchDown(x, y, pointer);
    }

    @Override
    public boolean touchUp(float x, float y, int pointer) {
        return head.touchUp(x, y, pointer);
    }

    @Override
    public boolean touchDragged(float x, float y, int pointer) {
        return head.touchDragged(x, y, pointer);
    }

    @Override
    public void moveTo(float x, float y) {
        super.moveTo(x, y);
        head.moveTo(getLeft() + getWidth()/2, getBottom() + getHeight()/2);
        head.updateCenter();
    }

    private class JoystickHead extends TouchEntity {

        public final Vector2 data;

        private Texture head;
        private float radius;
        private float radius2;
        private Vector2 center;
        private Vector2 delta;
        private Vector2 tmpV;

        public JoystickHead(Texture head, float radius, Screen screen) {
            super(head.getWidth() * SCALE, head.getHeight() * SCALE, screen);

            this.head = head;
            this.radius = radius;
            this.radius2 = this.radius * this.radius;
            center = new Vector2(getLeft() + getWidth()/2, getBottom() + getHeight()/2);
            delta = new Vector2();
            tmpV = new Vector2();
            data = new Vector2();
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
            spriteBatch.begin();
            spriteBatch.draw(head, getLeft(), getBottom(), getWidth(), getHeight());
            spriteBatch.end();
        }

        @Override
        public boolean touchDown(float x, float y, int pointer) {
            boolean result = super.touchDown(x, y, pointer);
            if(isTouched())
                delta.set(x, y);
            updateData();
            return result;
        }

        @Override
        public boolean touchUp(float x, float y, int pointer) {
            delta.set(0, 0);
            moveTo(center);
            updateData();
            return super.touchUp(x, y, pointer);
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
                    if(!contains(x, y))
                        delta.set(tmpV);
                }

                updateData();
            }
            return isTouched();
        }

    }

}
