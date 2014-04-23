package edu.bim444.gh14.entity;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import edu.bim444.gh14.screen.Screen;

public class UITextButton extends UIButton {

    private String text;
    private BitmapFont bitmapFont;

    public UITextButton(float width, float height, Screen screen) {
        super(width, height, screen);
    }

}
