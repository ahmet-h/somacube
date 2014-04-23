package edu.bim444.gh14.entity;

import edu.bim444.gh14.screen.Screen;

public class UIButton extends TouchEntity {

    private UIButtonListener listener;

    public UIButton(float width, float height, Screen screen) {
        super(width, height, screen);
    }

}
