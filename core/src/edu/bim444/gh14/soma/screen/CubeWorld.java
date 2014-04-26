package edu.bim444.gh14.soma.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.Ray;
import edu.bim444.gh14.entity.CubeEntity;
import edu.bim444.gh14.entity.Entity3D;
import edu.bim444.gh14.screen.CameraTouchController;
import edu.bim444.gh14.screen.Screen;
import edu.bim444.gh14.screen.World3D;
import edu.bim444.gh14.soma.entity.CubeGroup;

public class CubeWorld extends World3D {

    public static final float CUBE_WIDTH = 5;

    private CubeGroup selectingGroup;
    private CubeGroup selectedGroup;
    private int anchor;
    private Vector3 tmpV = new Vector3();

    public CubeWorld(Screen screen) {
        super(screen, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        Vector3 dir = new Vector3(-1, -0.75f, -1);
        Color color = new Color(0.3f, 0.3f, 0.3f, 1);
        getEnvironment().add(new DirectionalLight().set(color, dir));
        getEnvironment().add(new DirectionalLight().set(color, dir.rotate(Vector3.Y, 120)));
        getEnvironment().add(new DirectionalLight().set(color, dir.rotate(Vector3.Y, 120)));
        getEnvironment().add(new DirectionalLight().set(color, 0, 1, 0));

        addEntity(new CubeGroup(CubeGroup.PIECE_V, screen, this));
        addEntity(new CubeGroup(CubeGroup.PIECE_L, screen, this));
        addEntity(new CubeGroup(CubeGroup.PIECE_T, screen, this));
        addEntity(new CubeGroup(CubeGroup.PIECE_Z, screen, this));
        addEntity(new CubeGroup(CubeGroup.PIECE_A, screen, this));
        addEntity(new CubeGroup(CubeGroup.PIECE_B, screen, this));
        addEntity(new CubeGroup(CubeGroup.PIECE_P, screen, this));

        getEntity(1).moveBy(CUBE_WIDTH * 4, 0, 0);
        getEntity(2).moveBy(-CUBE_WIDTH * 4, 0, CUBE_WIDTH * 2);
        getEntity(3).moveBy(-CUBE_WIDTH * 4, 0, -CUBE_WIDTH * 4);
        getEntity(4).moveBy(CUBE_WIDTH * 4, 0, CUBE_WIDTH * 4);
        getEntity(5).moveBy(0, 0, CUBE_WIDTH * 5);
        getEntity(6).moveBy(0, 0, -CUBE_WIDTH * 4);

        setCameraTouchController(new CameraTouchController(getCamera()));
    }

    @Override
    public void draw(float alpha) {

    }

    @Override
    public boolean touchWorldDown(float deviceX, float deviceY, int pointer) {
        selectingGroup = (CubeGroup) getEntityFromCoordinates(deviceX, deviceY);

        return super.touchWorldDown(deviceX, deviceY, pointer);
    }

    @Override
    public boolean touchWorldUp(float deviceX, float deviceY, int pointer) {
        if(selectedGroup != null)
            selectedGroup.setSelected(false);
        selectedGroup = (CubeGroup) getEntityFromCoordinates(deviceX, deviceY);
        if(selectedGroup != null && selectedGroup == selectingGroup) {
            selectedGroup.setAnchor(anchor);
            selectedGroup.setSelected(true);
        }

        return super.touchWorldUp(deviceX, deviceY, pointer);
    }

    @Override
    public Entity3D getEntityFromCoordinates(float deviceX, float deviceY) {
        Ray ray = getCamera().getPickRay(deviceX, deviceY);
        CubeGroup entity = null;
        float distance = Float.MAX_VALUE;
        int anchor = -1;

        for(int i = 0; i < getSize(); i++) {
            CubeGroup g = (CubeGroup) getEntity(i);

            for(int j = 0; j < g.getCubeCount(); j++) {
                CubeEntity e = g.getCube(j);

                tmpV.set(e.getX(), e.getY(), e.getZ());
                float dst2 = ray.origin.dst2(tmpV);
                if(dst2 > distance)
                    continue;

                if(Intersector.intersectRayBoundsFast(ray, e.getBoundingBox())) {
                    entity = g;
                    distance = dst2;
                    anchor = j;
                }
            }
        }

        if(entity != null) {
            this.anchor = anchor;
        }

        return entity;
    }

}
