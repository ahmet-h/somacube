package edu.bim444.gh14.soma.screen;

import edu.bim444.gh14.GdxGame;
import edu.bim444.gh14.entity.UIImageButton;
import edu.bim444.gh14.entity.UIJoystick;
import edu.bim444.gh14.screen.Screen;
import edu.bim444.gh14.soma.Assets;

public class GameScreen extends Screen {

    @Override
    public void init(GdxGame game) {
        super.init(game);

        UIJoystick joystickLeft = new UIJoystick(Assets.joystickBase, Assets.joystickHead, this);
        joystickLeft.moveTo(getLeft() + joystickLeft.getWidth()/2 + 60, getBottom() + joystickLeft.getHeight()/2 + 40);
        addEntity(joystickLeft);

        UIJoystick joystickRight = new UIJoystick(Assets.joystickBase, Assets.joystickHead, this);
        joystickRight.moveTo(getRight() - joystickLeft.getWidth()/2 - 60, getBottom() + joystickLeft.getHeight()/2 + 40);
        addEntity(joystickRight);

        UIImageButton upButton = new UIImageButton(Assets.arrow_up, 1.2f, this);
        upButton.moveTo(joystickLeft.getRight() + upButton.getWidth(), joystickLeft.getY() + upButton.getHeight()/2 + 5);
        addEntity(upButton);

        UIImageButton downButton = new UIImageButton(Assets.arrow_down, 1.2f, this);
        downButton.moveTo(joystickLeft.getRight() + upButton.getWidth(), joystickLeft.getY() - upButton.getHeight()/2 - 5);
        addEntity(downButton);

        setWorld(new CubeWorld(this, joystickLeft, joystickRight, upButton, downButton));
    }

    @Override
    public void update() {

    }

    @Override
    public void draw(float alpha) {

    }

}
