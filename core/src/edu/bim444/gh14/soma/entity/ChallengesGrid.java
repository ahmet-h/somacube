package edu.bim444.gh14.soma.entity;

import com.badlogic.gdx.utils.Array;
import edu.bim444.gh14.entity.Entity;
import edu.bim444.gh14.screen.Screen;

public class ChallengesGrid extends Entity {

    private final int ROWS = 2;
    private final int COLS = 3;
    private final float ITEM_MARGIN_H = 20;
    private final float ITEM_MARGIN_V = 20;
    private final float ITEM_WIDTH = 260;
    private final float ITEM_HEIGHT = 240;

    private Array<ChallengeItem> items;
    private int pageIndex;

    public ChallengesGrid(Screen screen, SomaChallenges challenges) {
        super(screen.getWidth(), screen.getHeight(), screen);

        items = new Array<>();

        int pageCount = (int) Math.ceil(challenges.getSize() / (ROWS * COLS));
        setWidth(screen.getWidth() * pageCount);
        moveTo(screen.getCenterX(), screen.getCenterY());

        float firstX = getX() - ((ITEM_WIDTH + ITEM_MARGIN_H * 2) * COLS) / 2 + ITEM_MARGIN_H + ITEM_WIDTH / 2;
        float firstY = getY() + ((ITEM_HEIGHT + ITEM_MARGIN_V * 2) * ROWS) / 2 - ITEM_MARGIN_V - ITEM_HEIGHT / 2;
        for(int i = 0; i < challenges.getSize(); i++) {
            ChallengeItem item = new ChallengeItem(challenges.getChallengeByIndex(i), ITEM_WIDTH, ITEM_HEIGHT, screen);
            item.moveTo(firstX + (ITEM_WIDTH + ITEM_MARGIN_H * 2) * (i % COLS), firstY - (ITEM_HEIGHT + ITEM_MARGIN_V * 2) * (i / COLS));
            items.add(item);
        }

        setTouchable(true);
    }

    @Override
    public void update() {

    }

    @Override
    public void draw(float alpha) {
        for(int i = 0; i < items.size; i++) {
            items.get(i).drawEntity(alpha);
        }
    }

    @Override
    public boolean touchDown(float x, float y, int pointer) {
        for(int i = 0; i < items.size; i++) {
            if(items.get(i).touchDown(x, y, pointer))
                return true;
        }
        return super.touchDown(x, y, pointer);
    }

    @Override
    public boolean touchUp(float x, float y, int pointer) {
        for(int i = 0; i < items.size; i++) {
            if(items.get(i).touchUp(x, y, pointer))
                return true;
        }
        return super.touchUp(x, y, pointer);
    }

    @Override
    public boolean touchDragged(float x, float y, int pointer) {
        for(int i = 0; i < items.size; i++) {
            if(items.get(i).touchDragged(x, y, pointer))
                return true;
        }
        return super.touchDragged(x, y, pointer);
    }
}
