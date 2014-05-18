package edu.bim444.gh14.soma.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import edu.bim444.gh14.entity.UIText;
import edu.bim444.gh14.screen.Screen;

import java.util.Timer;
import java.util.TimerTask;

public class TimerText extends UIText {

    private Timer timer;
    private int time;
    private boolean paused;

    public TimerText(BitmapFont bitmapFont, Screen screen) {
        super(bitmapFont, "0:00", screen);

        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                updateTimer();
            }
        }, 1000, 1000);
    }

    public void updateTimer() {
        if(paused)
            return;

        time++;
        String s = "" + time / 60;
        s += ":";
        int seconds = time % 60;
        s += (seconds < 10) ? "0" + seconds : seconds;

        setText(s);
        Gdx.graphics.requestRendering();
    }

    public int getTime() {
        return time;
    }

    public void resume() {
        paused = false;
    }

    public void pause() {
        paused = true;
    }

    @Override
    public void dispose() {
        timer.cancel();
    }

}
