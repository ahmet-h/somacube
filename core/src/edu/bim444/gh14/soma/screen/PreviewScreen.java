package edu.bim444.gh14.soma.screen;

import edu.bim444.gh14.GdxGame;
import edu.bim444.gh14.entity.UIButtonListener;
import edu.bim444.gh14.entity.UINinePatchButton;
import edu.bim444.gh14.entity.UIText;
import edu.bim444.gh14.screen.Screen;
import edu.bim444.gh14.soma.Assets;

public class PreviewScreen extends Screen {

    private CubeWorld mainWorld;

    public PreviewScreen(CubeWorld cubeWorld) {
        mainWorld = cubeWorld;
    }

    @Override
    public void init(GdxGame game) {
        super.init(game);

        setWorld(new CubeWorld(mainWorld.getModelBatch(), this, mainWorld.getTargetGroup()));

        UIText challengeText = new UIText(Assets.robotoNormal, mainWorld.getChallenge().getTitle(), this);
        challengeText.moveTo(getCenterX(), getTop() - challengeText.getHeight() / 2 - 40);
        addEntity(challengeText);

        UINinePatchButton backButton = new UINinePatchButton(Assets.redButtonNinePatch, Assets.robotoNormal, "Go Back", 40, 24, this);
        backButton.moveBy(getCenterX(), getBottom() + backButton.getHeight() / 2 + 40);
        backButton.setUIButtonListener(new UIButtonListener() {
            @Override
            public void onClick() {
                getGame().setScreenTransition(null, GdxGame.SCREEN_CHANGE_POP);
            }
        });
        addEntity(backButton);
    }

    @Override
    public void update() {

    }

    @Override
    public void draw(float alpha) {

    }

}
