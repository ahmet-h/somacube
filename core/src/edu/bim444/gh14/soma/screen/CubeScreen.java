package edu.bim444.gh14.soma.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import edu.bim444.gh14.GdxGame;
import edu.bim444.gh14.entity.UIButtonListener;
import edu.bim444.gh14.entity.UIImageButton;
import edu.bim444.gh14.entity.UIJoystick;
import edu.bim444.gh14.screen.Screen;
import edu.bim444.gh14.soma.Assets;

public class CubeScreen extends Screen {

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

        UIImageButton pauseButton = new UIImageButton(Assets.pauseButton, 1.5f, this);
        pauseButton.moveTo(getLeft() + pauseButton.getWidth()/2 + 60, getTop() - pauseButton.getHeight()/2 - 20);
        addEntity(pauseButton);

        pauseButton.setUIButtonListener(new UIButtonListener() {
            @Override
            public void onClick() {
                getGame().setScreenTransition(null, GdxGame.SCREEN_CHANGE_POP, Color.BLACK);
            }
        });

        setWorld(new CubeWorld(this, joystickLeft, joystickRight, upButton, downButton));

        Gdx.gl.glClearColor(0.8f, 0.8f, 0.8f, 1);
    }

    @Override
    public void update() {

    }

    @Override
    public void draw(float alpha) {

    }

}
