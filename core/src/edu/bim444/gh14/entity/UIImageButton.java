package edu.bim444.gh14.entity;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import edu.bim444.gh14.screen.Screen;

public class UIImageButton extends UIButton {

    private Texture texture;

    public UIImageButton(Texture texture, float scale, Screen screen) {
        super(texture.getWidth() * scale, texture.getHeight() * scale, screen);

        this.texture = texture;
    }

    @Override
    public void draw(float alpha) {
        Color c = spriteBatch.getColor();
        if(isTouched())
            spriteBatch.setColor(c.r, c.g, c.b, 0.6f);
        else
            spriteBatch.setColor(c.r, c.g, c.b, 0.8f);

        spriteBatch.draw(texture, getLeft(), getBottom(), getWidth(), getHeight());

        spriteBatch.setColor(c.r, c.g, c.b, 1);
    }

    @Override
    public void update() {

    }

}
