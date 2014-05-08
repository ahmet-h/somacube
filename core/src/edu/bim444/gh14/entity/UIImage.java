package edu.bim444.gh14.entity;

import com.badlogic.gdx.graphics.Texture;
import edu.bim444.gh14.screen.Screen;

public class UIImage extends Entity {

    private Texture texture;

    public UIImage(Texture texture, float scale, Screen screen) {
        super(texture.getWidth() * scale, texture.getHeight() * scale, screen);

        this.texture = texture;
    }

    @Override
    public void update() {

    }

    @Override
    public void draw(float alpha) {
        spriteBatch.draw(texture, getLeft(), getBottom(), getWidth(), getHeight());
    }

}
