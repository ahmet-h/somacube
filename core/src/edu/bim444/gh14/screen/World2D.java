package edu.bim444.gh14.screen;

import com.badlogic.gdx.graphics.OrthographicCamera;

public class World2D extends World {

    public World2D(Screen screen, float worldWidth, float worldHeight) {
        super(screen);

        OrthographicCamera orthoCam = new OrthographicCamera();
        orthoCam.setToOrtho(false, worldWidth, worldHeight);
        orthoCam.position.set(0, 0, 0);
        setCamera(orthoCam);
    }

    @Override
    public void update() {

    }

    @Override
    public void draw(float alpha) {

    }

}
