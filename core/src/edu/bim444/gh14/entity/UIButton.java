package edu.bim444.gh14.entity;

import edu.bim444.gh14.screen.Screen;

public abstract class UIButton extends TouchEntity {

    private UIButtonListener listener;

    public UIButton(float width, float height, Screen screen) {
        super(width, height, screen);
    }

    public void setUIButtonListener(UIButtonListener listener) {
        this.listener = listener;
    }

    @Override
    public boolean touchUp(float x, float y, int pointer) {
        if(isTouched() && listener != null)
            listener.onClick();

        return super.touchUp(x, y, pointer);
    }
}
