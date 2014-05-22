package edu.bim444.gh14.soma.screen;

import com.badlogic.gdx.graphics.Color;
import edu.bim444.gh14.GdxGame;
import edu.bim444.gh14.entity.UIButtonListener;
import edu.bim444.gh14.entity.UIImage;
import edu.bim444.gh14.entity.UINinePatchImage;
import edu.bim444.gh14.entity.UITextButton;
import edu.bim444.gh14.screen.Screen;
import edu.bim444.gh14.soma.Assets;
import edu.bim444.gh14.soma.entity.ChallengesGrid;
import edu.bim444.gh14.soma.entity.SomaChallenges;

public class ChallengesScreen extends Screen {

    private final float ACTION_BAR_HEIGHT = 96;

    private ChallengesGrid grid;

    @Override
    public void init(GdxGame game) {
        super.init(game);

        SomaChallenges challenges = Assets.challenges;

        grid = new ChallengesGrid(this, challenges);
        grid.moveTo(getCenterX(), getCenterY() - 40);
        addEntity(grid);

        float pad = Assets.redPanelNinePatch.getPadLeft();
        UINinePatchImage actionBar = new UINinePatchImage(Assets.redPanelNinePatch, getWidth() + pad * 2, ACTION_BAR_HEIGHT + pad, this);
        actionBar.moveTo(getCenterX(), getTop() - actionBar.getHeight() / 2 + pad);
        actionBar.setTouchable(true);
        addEntity(actionBar);

        UITextButton actionBarButton = new UITextButton(Assets.textButtonBG, Assets.robotoNormal, "   Challenges", 32, -1, this);
        actionBarButton.setHeight(ACTION_BAR_HEIGHT);
        actionBarButton.updateAnchor();
        actionBarButton.updateTextAnchor();
        actionBarButton.moveTo(actionBar.getLeft() + actionBarButton.getWidth() / 2, actionBar.getY() - pad / 2);
        addEntity(actionBarButton);

        actionBarButton.setUIButtonListener(new UIButtonListener() {
            @Override
            public void onClick() {
                getGame().setScreenTransition(null, GdxGame.SCREEN_CHANGE_POP, Color.BLACK);
            }
        });

        UIImage navBackIcon = new UIImage(Assets.navBack, 0.8f, this);
        navBackIcon.moveTo(actionBarButton.getLeft() + 36, actionBarButton.getY());
        addEntity(navBackIcon);
    }

    @Override
    public void update() {

    }

    @Override
    public void draw(float alpha) {

    }

    @Override
    public boolean isRenderingRequested() {
        return grid.isRenderingRequested() || super.isRenderingRequested();
    }

}
