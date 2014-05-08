package edu.bim444.gh14.entity;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import edu.bim444.gh14.screen.Screen;

public class UIText extends Entity {

    private BitmapFont bitmapFont;
    private String text;

    public UIText(BitmapFont bitmapFont, String text, Screen screen) {
        super(screen);

        this.bitmapFont = bitmapFont;
        this.text = text;

        updateAnchor();
    }

    public void setText(String text) {
        this.text = text;
        updateAnchor();
    }

    public String getText() {
        return text;
    }

    public void updateAnchor() {
        BitmapFont.TextBounds textBounds = bitmapFont.getBounds(text);
        setWidth(textBounds.width);
        setHeight(textBounds.height);
        setAnchor(textBounds.width / 2, textBounds.height / 2);
    }

    public BitmapFont getBitmapFont() {
        return bitmapFont;
    }

    @Override
    public void update() {

    }

    @Override
    public void draw(float alpha) {
        bitmapFont.draw(spriteBatch, text, getLeft(), getTop());
    }

}
