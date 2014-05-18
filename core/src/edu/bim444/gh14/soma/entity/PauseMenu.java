package edu.bim444.gh14.soma.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import edu.bim444.gh14.GdxGame;
import edu.bim444.gh14.entity.*;
import edu.bim444.gh14.screen.Screen;
import edu.bim444.gh14.soma.Assets;

public class PauseMenu extends Entity {

    public final float TITLE_HEIGHT = 120;
    public final float PANEL_OVERLAP = 8;
    public final float BUTTON_MARGIN = 100;

    private UINinePatchImage redPanel;
    private UINinePatchImage greyPanel;
    private UIText title;
    private final Color bgColor = new Color(0, 0, 0, 0.4f);
    private UINinePatchButton resumeButton;
    private UINinePatchButton quitButton;

    public PauseMenu(float width, float height, final Screen screen) {
        super(screen.getCenterX(), screen.getCenterY(), width, height, screen);

        redPanel = new UINinePatchImage(Assets.redPanelNinePatch, width, TITLE_HEIGHT + PANEL_OVERLAP, screen);
        redPanel.moveTo(getX(), getTop() - TITLE_HEIGHT / 2 - PANEL_OVERLAP / 2);
        greyPanel = new UINinePatchImage(Assets.greyPanelNinePatch, width, height - redPanel.getHeight(), screen);
        greyPanel.moveTo(getX(), getY() - TITLE_HEIGHT / 2 + PANEL_OVERLAP / 2);
        title = new UIText(Assets.robotoBig, "Game Paused", screen);
        title.moveTo(redPanel.getX(), redPanel.getY());

        resumeButton = new UINinePatchButton(Assets.redButtonNinePatch, Assets.robotoNormal, "Resume", -1, 32, screen);
        resumeButton.moveTo(greyPanel.getX(), greyPanel.getY() + resumeButton.getHeight() / 2 + 10);
        resumeButton.setWidth(getWidth() - BUTTON_MARGIN * 2);
        resumeButton.updateAnchor();
        resumeButton.updateTextAnchor();
        resumeButton.setUIButtonListener(new UIButtonListener() {
            @Override
            public void onClick() {
                setHidden(true);
            }
        });

        quitButton = new UINinePatchButton(Assets.redButtonNinePatch, Assets.robotoNormal, "Quit to Menu", -1, 32, screen);
        quitButton.moveTo(greyPanel.getX(), greyPanel.getY() - quitButton.getHeight() / 2 - 10);
        quitButton.setWidth(getWidth() - BUTTON_MARGIN * 2);
        quitButton.updateAnchor();
        quitButton.updateTextAnchor();
        quitButton.setUIButtonListener(new UIButtonListener() {
            @Override
            public void onClick() {
                screen.getGame().setScreenTransition(null, GdxGame.SCREEN_CHANGE_POP, Color.BLACK);
            }
        });

        setTouchable(true);
        setHidden(true);
    }

    @Override
    public boolean touchDown(float x, float y, int pointer) {
        return resumeButton.touchDown(x, y, pointer) || quitButton.touchDown(x, y, pointer) || true;
    }

    @Override
    public boolean touchDragged(float x, float y, int pointer) {
        return resumeButton.touchDragged(x, y, pointer) || quitButton.touchDragged(x, y, pointer) || true;
    }

    @Override
    public boolean touchUp(float x, float y, int pointer) {
        return resumeButton.touchUp(x, y, pointer) || quitButton.touchUp(x, y, pointer) || true;
    }

    @Override
    public void update() {

    }

    @Override
    public void draw(float alpha) {
        spriteBatch.end();
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(bgColor);
        shapeRenderer.rect(0, 0, getScreen().getGame().VIRTUAL_WIDTH, getScreen().getGame().VIRTUAL_HEIGHT);
        shapeRenderer.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);
        spriteBatch.begin();

        redPanel.drawEntity(alpha);
        greyPanel.drawEntity(alpha);
        title.drawEntity(alpha);

        resumeButton.drawEntity(alpha);
        quitButton.drawEntity(alpha);
    }

}
