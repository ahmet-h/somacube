package edu.bim444.gh14.entity;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import edu.bim444.gh14.screen.Screen;

public class UINinePatchButton extends UITextButton {

    private NinePatch ninePatch;

    public UINinePatchButton(NinePatch ninePatch, BitmapFont bitmapFont, String text, float hPadding, float vPadding, Screen screen) {
        super(null, bitmapFont, text, hPadding, vPadding, screen);

        this.ninePatch = ninePatch;

        updateAnchor();
    }

    public UINinePatchButton(NinePatch ninePatch, BitmapFont bitmapFont, String text, Screen screen) {
        this(ninePatch, bitmapFont, text, 24, 20, screen);
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
