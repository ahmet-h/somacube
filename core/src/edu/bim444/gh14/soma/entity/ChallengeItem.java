package edu.bim444.gh14.soma.entity;

import com.badlogic.gdx.graphics.Color;
import edu.bim444.gh14.entity.UIButton;
import edu.bim444.gh14.entity.UIImage;
import edu.bim444.gh14.entity.UINinePatchImage;
import edu.bim444.gh14.entity.UIText;
import edu.bim444.gh14.screen.Screen;
import edu.bim444.gh14.soma.Assets;

public class ChallengeItem extends UIButton {

    public final float TITLE_HEIGHT = 60;
    public final float PANEL_OVERLAP = 8;
    public final float BEST_TIME_HEIGHT = 48;

    private UINinePatchImage titlePanel;
    private UINinePatchImage bottomPanel;
    private UINinePatchImage greyPanel;
    private UIText titleText;
    private UIText bestTimeText;
    private UIImage image;

    private boolean completed;
    private int bestTime;

    public ChallengeItem(SomaChallenge challenge, float width, float height, Screen screen) {
        super(width, height, screen);

        titlePanel = new UINinePatchImage(Assets.redPanelNinePatch, width, TITLE_HEIGHT + PANEL_OVERLAP, screen);
        titlePanel.moveTo(getX(), getTop() - titlePanel.getHeight() / 2);
        bottomPanel = new UINinePatchImage(Assets.redPanelNinePatch, width, BEST_TIME_HEIGHT + PANEL_OVERLAP, screen);
        bottomPanel.moveTo(getX(), getBottom() + bottomPanel.getHeight() / 2);
        greyPanel = new UINinePatchImage(Assets.greyPanelNinePatch, width, height - TITLE_HEIGHT - BEST_TIME_HEIGHT, screen);
        greyPanel.moveTo(getX(), getY() - TITLE_HEIGHT / 2 + BEST_TIME_HEIGHT / 2);
        titleText = new UIText(Assets.robotoSmall, challenge.getTitle(), screen);
        titleText.moveTo(titlePanel.getX(), titlePanel.getY() + PANEL_OVERLAP / 2);
        bestTimeText = new UIText(Assets.robotoTiny, "Best Time: " + ((challenge.getBestTime() == 0) ? "-" : challenge.getBestTime()), screen);
        bestTimeText.moveTo(bottomPanel.getX(), bottomPanel.getY() - PANEL_OVERLAP / 2);

        float imageAspectRatio = (float) challenge.getImage().getWidth() / challenge.getImage().getHeight();
        float panelAspectRatio = greyPanel.getWidth() / (greyPanel.getHeight() - PANEL_OVERLAP * 2);
        float scale;
        if(panelAspectRatio > imageAspectRatio)
            scale = (greyPanel.getHeight() - PANEL_OVERLAP * 2) / challenge.getImage().getHeight();
        else
            scale = greyPanel.getWidth() / challenge.getImage().getWidth();
        image = new UIImage(challenge.getImage(), scale, screen);
        image.moveTo(greyPanel.getX(), greyPanel.getY());
    }

    @Override
    public void moveTo(float x, float y) {
        float xx = getX();
        float yy = getY();
        super.moveTo(x, y);
        titlePanel.moveBy(x - xx, y - yy);
        bottomPanel.moveBy(x - xx, y - yy);
        greyPanel.moveBy(x - xx, y - yy);
        titleText.moveBy(x - xx, y - yy);
        bestTimeText.moveBy(x - xx, y - yy);
        image.moveBy(x - xx, y - yy);
    }

    @Override
    public void moveBy(float x, float y) {
        super.moveBy(x, y);
        titlePanel.moveBy(x, y);
        bottomPanel.moveBy(x, y);
        greyPanel.moveBy(x, y);
        titleText.moveBy(x, y);
        bestTimeText.moveBy(x, y);
        image.moveBy(x, y);
    }

    @Override
    public void draw(float alpha) {
        Color c = spriteBatch.getColor();
        if(isTouched())
            spriteBatch.setColor(c.r, c.g, c.b, 0.8f);
        else
            spriteBatch.setColor(c.r, c.g, c.b, 1f);

        titlePanel.drawEntity(alpha);
        bottomPanel.drawEntity(alpha);
        titleText.drawEntity(alpha);
        bestTimeText.drawEntity(alpha);

        spriteBatch.setColor(c.r, c.g, c.b, 1);
        greyPanel.drawEntity(alpha);
        image.drawEntity(alpha);
    }

}
