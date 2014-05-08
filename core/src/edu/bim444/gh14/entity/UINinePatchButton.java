package edu.bim444.gh14.entity;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import edu.bim444.gh14.screen.Screen;

public class UINinePatchButton extends UIButton {

    private UIText uiText;
    private NinePatch ninePatch;
    private float hPadding;
    private float vPadding;

    public UINinePatchButton(NinePatch ninePatch, BitmapFont bitmapFont, String text, float hPadding, float vPadding, Screen screen) {
        super(0, 0, screen);

        uiText = new UIText(bitmapFont, text, screen);
        this.ninePatch = ninePatch;
        this.hPadding = hPadding;
        this.vPadding = vPadding;

        updateAnchor();
    }

    public UINinePatchButton(NinePatch ninePatch, BitmapFont bitmapFont, String text, Screen screen) {
        this(ninePatch, bitmapFont, text, 24, 20, screen);
    }

    public void updateAnchor() {
        BitmapFont.TextBounds textBounds = uiText.getBitmapFont().getBounds(uiText.getText());

        if(hPadding >= 0)
            setWidth(textBounds.width + hPadding * 2);
        if(vPadding >= 0)
            setHeight(textBounds.height + vPadding * 2);

        setAnchor(getWidth() / 2, getHeight() / 2);
    }

    public void updateTextAnchor() {
        uiText.updateAnchor();
    }

    public void setText(String text) {
        uiText.setText(text);
        updateAnchor();
        uiText.updateAnchor();
    }

    public String getText() {
        return uiText.getText();
    }

    @Override
    public void moveBy(float x, float y) {
        super.moveBy(x, y);
        uiText.moveBy(x, y);
    }

    @Override
    public void moveTo(float x, float y) {
        super.moveTo(x, y);
        uiText.moveTo(x, y);
    }

    @Override
    public void update() {

    }

    @Override
    public void draw(float alpha) {
        Color c = spriteBatch.getColor();
        if(isTouched())
            spriteBatch.setColor(c.r, c.g, c.b, 0.6f);
        else
            spriteBatch.setColor(c.r, c.g, c.b, 1f);

        ninePatch.draw(spriteBatch, getLeft(), getBottom(), getWidth(), getHeight());
        uiText.draw(alpha);

        spriteBatch.setColor(c.r, c.g, c.b, 1);
    }

}
