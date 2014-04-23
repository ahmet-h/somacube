package edu.bim444.gh14.soma.screen;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.math.Vector3;
import edu.bim444.gh14.screen.CameraTouchController;
import edu.bim444.gh14.screen.Screen;
import edu.bim444.gh14.screen.World3D;
import edu.bim444.gh14.soma.entity.CubeGroup;

public class CubeWorld extends World3D {

    public static final float CUBE_WIDTH = 5;

    private CubeGroup group;

    public CubeWorld(Screen screen) {
        super(screen, 20, 12);

        Vector3 dir = new Vector3(-1, -0.75f, -1);
        Color color = new Color(0.3f, 0.3f, 0.3f, 1);
        getEnvironment().add(new DirectionalLight().set(color, dir));
        getEnvironment().add(new DirectionalLight().set(color, dir.rotate(Vector3.Y, 120)));
        getEnvironment().add(new DirectionalLight().set(color, dir.rotate(Vector3.Y, 120)));
        getEnvironment().add(new DirectionalLight().set(color, 0, 1, 0));



        group = new CubeGroup(new int[]{0, 0, 0,
                                        1, 0, 0,
                                        -1, 0, 0,
                                        0, 0, -1}, screen, this);
        addEntity(group);

        setCameraTouchController(new CameraTouchController(getCamera()));
    }

    @Override
    public void draw(float alpha) {

    }
}
