package edu.bim444.gh14.soma.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import edu.bim444.gh14.GdxGame;
import edu.bim444.gh14.entity.UIButtonListener;
import edu.bim444.gh14.entity.UIImage;
import edu.bim444.gh14.entity.UINinePatchButton;
import edu.bim444.gh14.screen.Screen;
import edu.bim444.gh14.soma.Assets;

public class TitleScreen extends Screen {

    private static final float BUTTON_WIDTH = 400;
    private static final float BUTTON_MARGIN = 24;

    @Override
    public void init(GdxGame game) {
        super.init(game);

        UIImage titleImage = new UIImage(Assets.titleImage, 1, this);
        float yy = getTop() - titleImage.getHeight()/2 - 24;
        titleImage.moveTo(getCenterX(), yy);
        addEntity(titleImage);

        UINinePatchButton instructionsButton = new UINinePatchButton(Assets.redButtonNinePatch, Assets.robotoNormal, "Instructions", -1, 32, this);
        yy = getBottom() + instructionsButton.getHeight()/2 + 48;
        instructionsButton.moveTo(getCenterX(), yy);
        instructionsButton.setWidth(BUTTON_WIDTH);
        instructionsButton.updateAnchor();
        instructionsButton.updateTextAnchor();
        addEntity(instructionsButton);
        yy += instructionsButton.getHeight() + BUTTON_MARGIN;

        UINinePatchButton challengesButton = new UINinePatchButton(Assets.redButtonNinePatch, Assets.robotoNormal, "Challenges", -1, 32, this);
        challengesButton.moveTo(getCenterX(), yy);
        challengesButton.setWidth(BUTTON_WIDTH);
        challengesButton.updateAnchor();
        challengesButton.updateTextAnchor();
        addEntity(challengesButton);

        challengesButton.setUIButtonListener(new UIButtonListener() {
            @Override
            public void onClick() {
                //getGame().setScreenTransition(new CubeScreen(Assets.challenges.getChallengeByIndex(0)), GdxGame.SCREEN_CHANGE_PUSH, Color.BLACK);
                getGame().setScreenTransition(new ChallengesScreen(), GdxGame.SCREEN_CHANGE_PUSH, Color.BLACK);
            }
        });

        //Gdx.gl.glClearColor(0.8f, 0.6f, 0.4f, 1);
        Gdx.gl.glClearColor(0.8f, 0.8f, 0.8f, 1);
    }

    @Override
    public void update() {

    }

    @Override
    public void draw(float alpha) {

    }

}
