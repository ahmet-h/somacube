package edu.bim444.gh14.soma.entity;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import edu.bim444.gh14.entity.Animator;
import edu.bim444.gh14.entity.CubeEntity;
import edu.bim444.gh14.entity.Entity3D;
import edu.bim444.gh14.entity.Interpolator;
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
    public static final int[] PIECE = new int[] {0, 0, 0};

    private final Color SELECTED_COLOR = new Color(0.7f, 0.8f, 0.7f, 1);

    private static final int HIGHLIGHT_DURATION = 30;

    private int anchor;
    private Array<CubeEntity> cubes;
    private boolean selected;

    private Animator highlightAnimator;
    private Color tmpC = new Color();

    public CubeGroup(int[] positions, Screen screen, World3D world3D) {
        super(screen, world3D, null);

        cubes = new Array<>();
        for(int i = 0; i < positions.length / 3; i++) {
            CubeEntity cube = new CubeEntity(screen, world3D, CubeWorld.CUBE_WIDTH, Assets.wood, MathUtils.random(0.8f, 0.85f));
            cube.moveBy(positions[i * 3] * CubeWorld.CUBE_WIDTH,
                    positions[i * 3 + 1] * CubeWorld.CUBE_WIDTH,
                    positions[i * 3 + 2] * CubeWorld.CUBE_WIDTH);
            cubes.add(cube);
        }

        highlightAnimator = new Animator(0, 0, 0, Interpolator.DECELERATE);
    }

    @Override
    public void update() {
        boolean highlightPaused = highlightAnimator.isPaused();

        highlightAnimator.update();

        for(CubeEntity cube : cubes) {
            cube.update();

            if(!highlightPaused) {
                float alpha = cube.getDiffuseColor().a;
                tmpC.set(1, 0, 0, 1).lerp(alpha, alpha, alpha, alpha, highlightAnimator.getNormalizedValue());
                cube.setDiffuseColor(tmpC);
            }
        }
    }

    @Override
    public void draw(float alpha) {
        for(CubeEntity cube : cubes) {
            cube.draw(alpha);
        }
    }

    @Override
    public void moveTo(float x, float y, float z) {
        float xx = getX();
        float yy = getY();
        float zz = getZ();
        for(CubeEntity cube : cubes) {
            cube.moveBy(x - xx, y - yy, z - zz);
        }
    }

    @Override
    public void moveBy(float dx, float dy, float dz) {
        for(CubeEntity cube : cubes) {
            cube.moveBy(dx, dy, dz);
        }
    }

    @Override
    public void rotate(Vector3 axis, float degrees) {
        for(CubeEntity cube : cubes) {
            cube.rotate(axis, degrees);
        }
    }

    @Override
    public void rotateAround(Vector3 point, Vector3 axis, float degrees) {
        for(CubeEntity cube : cubes) {
            cube.rotateAround(point, axis, degrees);
        }
    }

    @Override
    public float getX() {
        return cubes.get(anchor).getX();
    }

    @Override
    public float getY() {
        return cubes.get(anchor).getY();
    }

    @Override
    public float getZ() {
        return cubes.get(anchor).getZ();
    }

    public CubeEntity getCube(int index) {
        return cubes.get(index);
    }

    public int getCubeCount() {
        return cubes.size;
    }

    public Array<CubeEntity> getCubes() {
        return cubes;
    }

    public int getAnchor() {
        return anchor;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected, int anchor) {
        this.selected = selected;
        this.anchor = anchor;
        for(CubeEntity cube : cubes) {
            if(selected) {
                TextureAttribute attr = (TextureAttribute) cube.getModelInstance().materials.get(0).get(TextureAttribute.Diffuse);
                attr.textureDescription.texture = (cube == cubes.get(anchor)) ? Assets.woodBlack : Assets.woodBrown;
            } else {
                TextureAttribute attr = (TextureAttribute) cube.getModelInstance().materials.get(0).get(TextureAttribute.Diffuse);
                attr.textureDescription.texture = Assets.wood;
            }
        }
    }

    public void setSelected(boolean selected) {
        setSelected(selected, anchor);
    }

    public boolean collidesWith(CubeGroup other) {
        for(CubeEntity cube : cubes) {
            for(CubeEntity otherCube : other.getCubes()) {
                if(cube.collidesWith(otherCube, 0.1f))
                    return true;
            }
        }

        return false;
    }

    public boolean checkCollisionWith(CubeGroup other) {
        if(collidesWith(other)) {
            highlightAnimator.set(0, 1, HIGHLIGHT_DURATION);
            highlightAnimator.start();

            return true;
        }

        return false;
    }

    public boolean isHighlighting() {
        return !highlightAnimator.isPaused();
    }

}
