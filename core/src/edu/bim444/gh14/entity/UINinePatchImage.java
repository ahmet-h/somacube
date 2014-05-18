package edu.bim444.gh14.entity;

import com.badlogic.gdx.graphics.g2d.NinePatch;
import edu.bim444.gh14.screen.Screen;

public class UINinePatchImage extends Entity {

    private NinePatch ninePatch;

    public UINinePatchImage(NinePatch ninePatch, float width, float height, Screen screen) {
        super(width, height, screen);

        this.ninePatch = ninePatch;
    }

    @Override
    public void update() {

    }

    @Override
    public void draw(float alpha) {
        ninePatch.draw(spriteBatch, getLeft(), getBottom(), getWidth(), getHeight());
    }

}
