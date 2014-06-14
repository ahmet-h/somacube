package edu.bim444.gh14.soma.screen;

import com.badlogic.gdx.Gdx;
import edu.bim444.gh14.GdxGame;
import edu.bim444.gh14.entity.UIButtonListener;
import edu.bim444.gh14.entity.UIImageButton;
import edu.bim444.gh14.entity.UIJoystick;
import edu.bim444.gh14.screen.Screen;
import edu.bim444.gh14.soma.Assets;
import edu.bim444.gh14.soma.entity.PauseMenu;
import edu.bim444.gh14.soma.entity.SomaChallenge;
import edu.bim444.gh14.soma.entity.TimerText;

public class CubeScreen extends Screen {

    private PauseMenu pauseMenu;
    private TimerText timerText;
    private SomaChallenge challenge;

    public CubeScreen(SomaChallenge challenge) {
        this.challenge = challenge;
    }

    @Override
    public void init(GdxGame game) {
        super.init(game);

        UIJoystick joystickLeft = new UIJoystick(Assets.joystickBase, Assets.joystickHead, this);
        joystickLeft.moveTo(getLeft() + joystickLeft.getWidth()/2 + 60, getBottom() + joystickLeft.getHeight()/2 + 40);
        addEntity(joystickLeft);

        UIJoystick joystickRight = new UIJoystick(Assets.joystickBase, Assets.joystickHead, this);
        joystickRight.moveTo(getRight() - joystickLeft.getWidth()/2 - 60, getBottom() + joystickLeft.getHeight()/2 + 40);
        addEntity(joystickRight);

        UIImageButton upButton = new UIImageButton(Assets.arrowUp, 1.2f, this);
        upButton.moveTo(joystickLeft.getRight() + upButton.getWidth(), joystickLeft.getY() + upButton.getHeight()/2 + 5);
        addEntity(upButton);

        UIImageButton downButton = new UIImageButton(Assets.arrowDown, 1.2f, this);
        downButton.moveTo(joystickLeft.getRight() + upButton.getWidth(), joystickLeft.getY() - upButton.getHeight()/2 - 5);
        addEntity(downButton);

        final UIImageButton pauseButton = new UIImageButton(Assets.pauseButton, 1.5f, this);
        pauseButton.moveTo(getLeft() + pauseButton.getWidth()/2 + 60, getTop() - pauseButton.getHeight()/2 - 20);
        addEntity(pauseButton);

        UIImageButton helpButton = new UIImageButton(Assets.helpButton, 1.5f, this);
        helpButton.moveTo(getRight() - helpButton.getWidth()/2 - 60, pauseButton.getY());
        helpButton.setUIButtonListener(new UIButtonListener() {
            @Override
            public void onClick() {
                timerText.pause();
                getGame().setScreenTransition(new PreviewScreen((CubeWorld)getWorld()), GdxGame.SCREEN_CHANGE_PUSH);
            }
        });
        addEntity(helpButton);

        timerText = new TimerText(Assets.robotoNormal, this);
        timerText.moveTo(getCenterX(), pauseButton.getY());
        addEntity(timerText);

        pauseMenu = new PauseMenu(600, 420, this);
        addEntity(pauseMenu);

        pauseButton.setUIButtonListener(new UIButtonListener() {
            @Override
            public void onClick() {
                pauseMenu.setHidden(false);
            }
        });

        setWorld(new CubeWorld(this, challenge, joystickLeft, joystickRight, upButton, downButton));

        Gdx.gl.glClearColor(0.8f, 0.8f, 0.8f, 1);
    }

    @Override
    public void update() {
        if(pauseMenu.isHidden())
            timerText.resume();
        else
            timerText.pause();
    }

    @Override
    public void draw(float alpha) {

    }

    @Override
    public void dispose() {
        super.dispose();
        timerText.dispose();
    }

    @Override
    public void pause() {
        pauseMenu.setHidden(false);
        timerText.pause();
    }

}
