package edu.bim444.gh14.soma.screen;

import edu.bim444.gh14.GdxGame;
import edu.bim444.gh14.screen.Screen;

public class PreviewScreen extends Screen {

    private CubeWorld mainWorld;

    public PreviewScreen(CubeWorld cubeWorld) {
        mainWorld = cubeWorld;
    }

    @Override
    public void init(GdxGame game) {
        super.init(game);

        setWorld(new CubeWorld(mainWorld.getModelBatch(), this, mainWorld.getTargetGroup()));
    }

    @Override
    public void update() {

    }

    @Override
    public void draw(float alpha) {

    }

}
