package edu.bim444.gh14.soma.screen;

import edu.bim444.gh14.GdxGame;
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
        
        setWorld(new CubeWorld(this, joystickLeft, joystickRight));
    }

    @Override
    public void update() {

    }

    @Override
    public void draw(float alpha) {

    }

}
