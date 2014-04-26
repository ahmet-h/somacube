package edu.bim444.gh14.soma.entity;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import edu.bim444.gh14.entity.CubeEntity;
import edu.bim444.gh14.entity.Entity3D;
import edu.bim444.gh14.screen.Screen;
import edu.bim444.gh14.screen.World3D;
import edu.bim444.gh14.soma.Assets;
import edu.bim444.gh14.soma.screen.CubeWorld;

public class CubeGroup extends Entity3D {

    public static final int[] PIECE_V = new int[]{
            0, 0, 0,
            1, 0, 0,
            0, 0, 1};
    public static final int[] PIECE_L = new int[]{
            0, 0, 0,
            1, 0, 0,
            2, 0, 0,
            0, 0, 1};
    public static final int[] PIECE_T = new int[]{
            0, 0, 0,
            1, 0, 0,
            -1, 0, 0,
            0, 0, 1};
    public static final int[] PIECE_Z = new int[]{
            0, 0, 0,
            1, 0, 0,
            0, 0, 1,
            -1, 0, 1};
    public static final int[] PIECE_A = new int[]{
            0, 0, 0,
            0, 1, 0,
            0, 0, 1,
            1, 1, 0};
    public static final int[] PIECE_B = new int[]{
            0, 0, 0,
            0, 1, 0,
            0, 1, 1,
            1, 0, 0};
    public static final int[] PIECE_P = new int[]{
            0, 0, 0,
            0, 1, 0,
            0, 0, 1,
            1, 0, 0};

    private final Color SELECTED_COLOR = new Color(0.7f, 0.8f, 0.7f, 1);

    private int anchor;
    private Array<CubeEntity> cubes;
    private boolean selected;

    public CubeGroup(int[] positions, Screen screen, World3D world3D) {
        super(screen, world3D, null);

        cubes = new Array<>();
        for(int i = 0; i < positions.length / 3; i++) {
            CubeEntity cube = new CubeEntity(screen, world3D, CubeWorld.CUBE_WIDTH, Assets.wood, MathUtils.random(0.8f, 0.85f));
            cube.moveTo(positions[i * 3] * CubeWorld.CUBE_WIDTH,
                    positions[i * 3 + 1] * CubeWorld.CUBE_WIDTH,
                    positions[i * 3 + 2] * CubeWorld.CUBE_WIDTH);
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

    public CubeEntity getCube(int index) {
        return cubes.get(index);
    }

    public int getCubeCount() {
        return cubes.size;
    }

    public int getAnchor() {
        return anchor;
    }

    public void setAnchor(int anchor) {
        this.anchor = anchor;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        if(isSelected() == selected)
            return;

        this.selected = selected;
        if(selected) {
            TextureAttribute attr = (TextureAttribute) cubes.get(anchor).getModelInstance().materials.get(0).get(TextureAttribute.Diffuse);
            attr.textureDescription.texture = Assets.wood_selected;
        } else {
            TextureAttribute attr = (TextureAttribute) cubes.get(anchor).getModelInstance().materials.get(0).get(TextureAttribute.Diffuse);
            attr.textureDescription.texture = Assets.wood;
        }
    }

}
