package edu.bim444.gh14.entity;

import edu.bim444.gh14.screen.Screen;

public class TouchEntity extends Entity {

    private boolean touched;

    public TouchEntity(float width, float height, Screen screen) {
        super(width, height, screen);
        setTouchable(true);
    }

    @Override
    public void update() {

    }

    @Override
    public void draw(float alpha) {

    }

    @Override
    public boolean touchDown(float x, float y, int pointer) {
        touched = contains(x, y);
        return touched;
    }

    @Override
    public boolean touchUp(float x, float y, int pointer) {
        boolean b = touched;
        touched = false;
        return b;
    }

    @Override
    public boolean touchDragged(float x, float y, int pointer) {
        if(!contains(x, y))
            touched = false;
        return touched;
    }

    public boolean isTouched() {
        return touched;
    }
}
