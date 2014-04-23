package edu.bim444.gh14.soma.entity;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import edu.bim444.gh14.entity.CubeEntity;
import edu.bim444.gh14.entity.Entity3D;
import edu.bim444.gh14.screen.Screen;
import edu.bim444.gh14.screen.World3D;
import edu.bim444.gh14.soma.Assets;
import edu.bim444.gh14.soma.screen.CubeWorld;

public class CubeGroup extends Entity3D {

    private int anchor;
    private Array<CubeEntity> cubes;
    private boolean selected;

    public CubeGroup(int[] positions, Screen screen, World3D world3D) {
        super(screen, world3D, null);

        cubes = new Array<>();
        for(int i = 0; i < positions.length / 3; i++) {
            CubeEntity cube = new CubeEntity(screen, world3D, CubeWorld.CUBE_WIDTH, Assets.wood, MathUtils.random(0.8f, 0.85f));
            cube.moveTo(positions[i*3] * CubeWorld.CUBE_WIDTH,
                        positions[i*3 + 1] * CubeWorld.CUBE_WIDTH,
                        positions[i*3 + 2] * CubeWorld.CUBE_WIDTH);
            cubes.add(cube);
        }
    }

    @Override
    public void update() {
        for(CubeEntity cube : cubes) {
            cube.update();
        }
    }

    @Override
    public void draw(float alpha) {
        for(CubeEntity cube : cubes) {
            cube.drawEntity(alpha);
        }
    }

    @Override
    public void moveTo(float x, float y, float z) {
        for(CubeEntity cube : cubes) {
            cube.moveTo(x, y, z);
        }
    }

    @Override
    public void moveBy(float x, float y, float z) {
        for(CubeEntity cube : cubes) {
            cube.moveBy(x, y, z);
        }
    }

}
