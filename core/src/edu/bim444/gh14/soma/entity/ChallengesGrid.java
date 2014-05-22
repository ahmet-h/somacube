package edu.bim444.gh14.soma.entity;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import edu.bim444.gh14.GdxGame;
import edu.bim444.gh14.entity.*;
import edu.bim444.gh14.screen.Screen;
import edu.bim444.gh14.soma.Assets;
import edu.bim444.gh14.soma.screen.CubeScreen;

public class ChallengesGrid extends Entity {

    private static final int ROWS = 2;
    private static final int COLS = 3;
    private static final float ITEM_MARGIN_H = 20;
    private static final float ITEM_MARGIN_V = 12;
    private static final float ITEM_WIDTH = 260;
    private static final float ITEM_HEIGHT = 250;
    private static final float DOT_2R = 12;
    private static final float DOT_2R_DIFF = 12;
    private static final float DOT_MARGIN = 12;
    private static final float MIN_DRAG_DELTA = 20;
    private static final float MAX_DELTA = 20;
    private static final float MIN_DELTA = 10;
    private static final int MAX_DURATION = 20;
    private static final int LIMIT_TOLERANCE = 160;

    private Array<ChallengeItem> items;
    private int pageCount;
    private boolean dragged;
    private boolean canDrag;
    private Vector2 firstTouch = new Vector2();
    private Vector2 delta = new Vector2();
    private float[] deltaArray = new float[4];
    private int deltaIndex;
    private Animator deltaAnimator;
    private int pageIndex;

    public ChallengesGrid(final Screen screen, final SomaChallenges challenges) {
        super(screen.getWidth(), screen.getHeight(), screen);

        items = new Array<>();
        deltaAnimator = new Animator(0, 0, 20, Interpolator.DECELERATE);

        pageCount = (int) Math.ceil((float) challenges.getSize() / (ROWS * COLS));
        setWidth(screen.getWidth() * pageCount);
        setHeight((ITEM_HEIGHT + ITEM_MARGIN_V * 2) * ROWS - ITEM_MARGIN_V * 2);
        setAnchor(screen.getWidth() / 2, getHeight() / 2);

        float firstX = getX() - ((ITEM_WIDTH + ITEM_MARGIN_H * 2) * COLS) / 2 + ITEM_MARGIN_H + ITEM_WIDTH / 2;
        float firstY = getY() + ((ITEM_HEIGHT + ITEM_MARGIN_V * 2) * ROWS) / 2 - ITEM_MARGIN_V - ITEM_HEIGHT / 2;
        for(int i = 0; i < challenges.getSize(); i++) {
            final SomaChallenge challenge = challenges.getChallengeByIndex(i);
            ChallengeItem item = new ChallengeItem(challenge, ITEM_WIDTH, ITEM_HEIGHT, screen);
            item.moveTo(firstX + (ITEM_WIDTH + ITEM_MARGIN_H * 2) * (i % COLS), firstY - (ITEM_HEIGHT + ITEM_MARGIN_V * 2) * ((i % (ROWS * COLS)) / COLS));
            item.setUIButtonListener(new UIButtonListener() {
                @Override
                public void onClick() {
                    screen.getGame().setScreenTransition(new CubeScreen(challenge), GdxGame.SCREEN_CHANGE_PUSH, Color.BLACK);
                }
            });
            items.add(item);

            if(((i + 1) % (ROWS * COLS)) == 0)
                firstX += screen.getWidth();
        }

        setTouchable(true);
    }

    @Override
    public void moveTo(float x, float y) {
        float xx = getX();
        float yy = getY();
        super.moveTo(x, y);
        for(int i = 0; i < items.size; i++) {
            items.get(i).moveBy(x - xx, y - yy);
        }
    }

    @Override
    public void moveBy(float x, float y) {
        super.moveBy(x, y);
        for(int i = 0; i < items.size; i++) {
            items.get(i).moveBy(x, y);
        }
    }

    @Override
    public void update() {
        boolean p = deltaAnimator.isPaused();

        deltaAnimator.update();

        if(!p && !dragged)
            moveTo(deltaAnimator.getCurrentValue(), getY());
    }

    @Override
    public void draw(float alpha) {
        for(int i = 0; i < items.size; i++) {
            items.get(i).drawEntity(alpha);
        }

        float xx = getScreen().getCenterX() - ((DOT_2R + DOT_MARGIN * 2) * (pageCount - 1)) / 2;
        float yy = getScreen().getBottom() + (getBottom() - getScreen().getBottom()) / 2;
        float maxwh = 0;
        for(int i = 0; i < pageCount; i++) {
            float wh = DOT_2R;
            float a;
            if((a = Math.abs(getScreen().getCenterX() - getX() - getScreen().getWidth() * i)) <= getScreen().getWidth()) {
                wh += (1 - a / getScreen().getWidth()) * DOT_2R_DIFF;
                if(maxwh < wh) {
                    maxwh = wh;
                    pageIndex = i;
                }
            }
            spriteBatch.draw(Assets.circle, xx + i * (DOT_2R + DOT_MARGIN * 2) - wh / 2, yy - wh / 2, wh, wh);
        }
    }

    public void resetDelta() {
        delta.set(0, 0);
        for(int i = 0; i < deltaArray.length; i++) {
            deltaArray[i] = 0;
        }
        deltaIndex = 0;
    }

    public void queueDelta(float deltaX) {
        deltaArray[deltaIndex++] = (Math.abs(deltaX) > MAX_DELTA) ? MAX_DELTA * Math.signum(deltaX) : deltaX;
        if(deltaIndex >= deltaArray.length)
            deltaIndex = 0;
    }

    @Override
    public boolean touchDown(float x, float y, int pointer) {
        updateBounds();
        canDrag = contains(x, y);
        if(!canDrag)
            return false;

        boolean stopped = !deltaAnimator.isPaused();
        if(stopped) {
            deltaAnimator.stop();
        }

        resetDelta();
        firstTouch.set(delta.set(x, y));

        for(int i = 0; i < items.size; i++) {
            if(stopped)
                items.get(i).setTouched(false);
            else if(items.get(i).touchDown(x, y, pointer))
                return true;
        }
        return super.touchDown(x, y, pointer);
    }

    @Override
    public boolean touchUp(float x, float y, int pointer) {
        canDrag = false;
        dragged = false;

        float d = getAverage(deltaArray);
        int duration = MAX_DURATION;
        if(Math.abs(d) >= MIN_DELTA) {
            if(d > 0)
                pageIndex = (pageIndex == 0) ? 0 : pageIndex - 1;
            else
                pageIndex = (pageIndex == pageCount - 1) ? pageCount - 1 : pageIndex + 1;
        }
        deltaAnimator.set(getX(), getScreen().getCenterX() - pageIndex * getScreen().getWidth(), duration);
        deltaAnimator.start();

        for(int i = 0; i < items.size; i++) {
            if(items.get(i).touchUp(x, y, pointer))
                return true;
        }
        return super.touchUp(x, y, pointer);
    }

    @Override
    public boolean touchDragged(float x, float y, int pointer) {
        if(!canDrag)
            return false;

        if(Math.abs(x - firstTouch.x) >= MIN_DRAG_DELTA)
            dragged = true;

        delta.sub(x, y).scl(-1);
        queueDelta(delta.x);
        if(dragged) {
            float friction = 0;
            if(getLeft() > 0)
                friction = getLeft() / LIMIT_TOLERANCE;
            else if(getRight() < getScreen().getWidth())
                friction = (getScreen().getWidth() - getRight()) / LIMIT_TOLERANCE;
            friction = Math.min(friction, 1);
            moveBy(delta.scl(1 - friction).x, 0);
        }
        delta.set(x, y);

        for(int i = 0; i < items.size; i++) {
            if(dragged)
                items.get(i).setTouched(false);
            else if(items.get(i).touchDragged(x, y, pointer))
                return true;
        }
        return dragged || super.touchDragged(x, y, pointer);
    }

    public float getAverage(float[] deltaArray) {
        float delta = 0;
        for(int i = 0; i < deltaArray.length; i++) {
            delta += deltaArray[i];
        }
        delta /= deltaArray.length;
        return delta;
    }

    public boolean isRenderingRequested() {
        return !deltaAnimator.isPaused();
    }

}
