package edu.bim444.gh14.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import edu.bim444.gh14.screen.Screen;

public class UITextButton extends UIButton {

    private Texture background;
    protected UIText uiText;
    private float hPadding;
    private float vPadding;

    public UITextButton(Texture background, BitmapFont bitmapFont, String text, float hPadding, float vPadding, Screen screen) {
        super(0, 0, screen);

        this.background = background;
        uiText = new UIText(bitmapFont, text, screen);
        this.hPadding = hPadding;
        this.vPadding = vPadding;

        updateAnchor();
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
        if(isTouched())
            spriteBatch.draw(background, getLeft(), getBottom(), getWidth(), getHeight());
        uiText.draw(alpha);
    }

}
