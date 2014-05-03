package edu.bim444.gh14.soma.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.Ray;
import edu.bim444.gh14.entity.*;
import edu.bim444.gh14.screen.CameraTouchController;
import edu.bim444.gh14.screen.Screen;
import edu.bim444.gh14.screen.World3D;
import edu.bim444.gh14.soma.entity.CubeGroup;

public class CubeWorld extends World3D {

    public static final float CUBE_WIDTH = 5;
    public static final int MOVE_ANIM_DURATION = 10;
    public static final int CAM_ANIM_DURATION = 20;

    private CubeGroup selectingGroup;
    private CubeGroup selectedGroup;
    private Vector3 tmpV = new Vector3();
    private Vector3 tmpV2 = new Vector3();

    private Animator moveAnimator;
    private Vector3 currPos = new Vector3();
    private Vector3 moveDir = new Vector3();
    private Animator rotationAnimator;
    private Vector3 rotationAxis = new Vector3();

    private Vector3 camPosBegin = new Vector3();
    private Vector3 camPosDelta = new Vector3();
    private Animator camAnimator;
    private boolean camMoveInterrupted;

    public CubeWorld(Screen screen, UIJoystick joystickLeft, UIJoystick joystickRight, UIButton upButton, UIButton downButton) {
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

        getEntity(0).moveTo(0, 0, 0);
        getEntity(1).moveTo(CUBE_WIDTH * 3, 0, -CUBE_WIDTH * 2);
        getEntity(2).moveTo(-CUBE_WIDTH * 3, 0, CUBE_WIDTH * 2);
        getEntity(3).moveTo(-CUBE_WIDTH * 3, 0, -CUBE_WIDTH * 2);
        getEntity(4).moveTo(CUBE_WIDTH * 3, 0, CUBE_WIDTH * 2);
        getEntity(5).moveTo(0, 0, CUBE_WIDTH * 4);
        getEntity(6).moveTo(0, 0, -CUBE_WIDTH * 4);

        setCameraTouchController(new CameraTouchController(getCamera()));

        moveAnimator = new Animator(0, 0, 0, Interpolator.DECELERATE);
        moveAnimator.setAnimatorListener(new AnimatorListener() {
            @Override
            public void onAnimationEnd() {
                getCameraTouchController().resetDelta();
                centerCameraPosition(true);
            }
        });
        rotationAnimator = new Animator(0, 0, 0, Interpolator.DECELERATE);
        rotationAnimator.setAnimatorListener(new AnimatorListener() {
            @Override
            public void onAnimationEnd() {
                getCameraTouchController().resetDelta();
                centerCameraPosition(true);
            }
        });
        camAnimator = new Animator(0, 0, 0, Interpolator.DECELERATE);

        joystickLeft.setUIJoystickListener(new UIJoystickListener() {
            @Override
            public void onDirection(int dir) {
                if(selectedGroup == null || !moveAnimator.isPaused() || !rotationAnimator.isPaused())
                    return;

                float dotX = getCamera().direction.dot(Vector3.X);
                float dotZ = getCamera().direction.dot(Vector3.Z);

                currPos.set(selectedGroup.getCube(selectedGroup.getAnchor()).getX(),
                            selectedGroup.getCube(selectedGroup.getAnchor()).getY(),
                            selectedGroup.getCube(selectedGroup.getAnchor()).getZ());

                if(Math.abs(dotX) > Math.abs(dotZ)) {
                    moveDir.set(Math.signum(dotX), 0, 0);
                } else {
                    moveDir.set(0, 0, Math.signum(dotZ));
                }

                if(dir == UIJoystickListener.DOWN)
                    moveDir.rotate(Vector3.Y, 180);
                else if(dir == UIJoystickListener.LEFT)
                    moveDir.rotate(Vector3.Y, 90);
                else if(dir == UIJoystickListener.RIGHT)
                    moveDir.rotate(Vector3.Y, -90);

                moveDir.x = Math.round(moveDir.x);
                moveDir.y = Math.round(moveDir.y);
                moveDir.z = Math.round(moveDir.z);

                moveAnimator.set(0, CUBE_WIDTH, MOVE_ANIM_DURATION);
                moveAnimator.start();
            }
        });

        joystickRight.setUIJoystickListener(new UIJoystickListener() {
            @Override
            public void onDirection(int dir) {
                if(selectedGroup == null || !moveAnimator.isPaused() || !rotationAnimator.isPaused())
                    return;

                currPos.set(selectedGroup.getCube(selectedGroup.getAnchor()).getX(),
                            selectedGroup.getCube(selectedGroup.getAnchor()).getY(),
                            selectedGroup.getCube(selectedGroup.getAnchor()).getZ());

                if(dir == UIJoystickListener.UP || dir == UIJoystickListener.DOWN) {
                    tmpV.set(getCamera().direction).crs(getCamera().up).y = 0f;
                    float dotX = tmpV.nor().dot(Vector3.X);
                    tmpV.set(getCamera().direction).crs(getCamera().up).y = 0f;
                    float dotZ = tmpV.nor().dot(Vector3.Z);

                    if(Math.abs(dotX) > Math.abs(dotZ)) {
                        rotationAxis.set(Math.signum(dotX), 0, 0);
                    } else {
                        rotationAxis.set(0, 0, Math.signum(dotZ));
                    }

                    if(dir == UIJoystickListener.UP)
                        rotationAxis.scl(-1);
                } else if(dir == UIJoystickListener.LEFT) {
                    rotationAxis.set(0, -1, 0);
                } else if(dir == UIJoystickListener.RIGHT) {
                    rotationAxis.set(0, 1, 0);
                }

                rotationAnimator.set(0, 90, MOVE_ANIM_DURATION);
                rotationAnimator.start();
            }
        });

        upButton.setUIButtonListener(new UIButtonListener() {
            @Override
            public void onClick() {
                if(selectedGroup == null || !moveAnimator.isPaused() || !rotationAnimator.isPaused())
                    return;

                currPos.set(selectedGroup.getCube(selectedGroup.getAnchor()).getX(),
                        selectedGroup.getCube(selectedGroup.getAnchor()).getY(),
                        selectedGroup.getCube(selectedGroup.getAnchor()).getZ());

                moveDir.set(0, 1, 0);

                moveAnimator.set(0, CUBE_WIDTH, MOVE_ANIM_DURATION);
                moveAnimator.start();
            }
        });

        downButton.setUIButtonListener(new UIButtonListener() {
            @Override
            public void onClick() {
                if(selectedGroup == null || !moveAnimator.isPaused() || !rotationAnimator.isPaused())
                    return;

                currPos.set(selectedGroup.getCube(selectedGroup.getAnchor()).getX(),
                        selectedGroup.getCube(selectedGroup.getAnchor()).getY(),
                        selectedGroup.getCube(selectedGroup.getAnchor()).getZ());

                moveDir.set(0, -1, 0);

                moveAnimator.set(0, CUBE_WIDTH, MOVE_ANIM_DURATION);
                moveAnimator.start();
            }
        });

        centerCameraPosition(false);
    }

    @Override
    public void update() {
        boolean movePaused = moveAnimator.isPaused();
        boolean rotationPaused = rotationAnimator.isPaused();
        boolean camAnimPaused = camAnimator.isPaused();

        moveAnimator.update();
        rotationAnimator.update();
        camAnimator.update();

        if(!movePaused) {
            if(moveDir.x == 1)
                selectedGroup.moveTo(currPos.x + moveAnimator.getCurrentValue(), currPos.y, currPos.z);
            else if(moveDir.x == -1)
                selectedGroup.moveTo(currPos.x - moveAnimator.getCurrentValue(), currPos.y, currPos.z);
            else if(moveDir.y == 1)
                selectedGroup.moveTo(currPos.x, currPos.y + moveAnimator.getCurrentValue(), currPos.z);
            else if(moveDir.y == -1)
                selectedGroup.moveTo(currPos.x, currPos.y - moveAnimator.getCurrentValue(), currPos.z);
            else if(moveDir.z == 1)
                selectedGroup.moveTo(currPos.x, currPos.y, currPos.z + moveAnimator.getCurrentValue());
            else if(moveDir.z == -1)
                selectedGroup.moveTo(currPos.x, currPos.y, currPos.z - moveAnimator.getCurrentValue());
        }

        if(!rotationPaused) {
            selectedGroup.rotateAround(currPos, rotationAxis, (float) 90 / MOVE_ANIM_DURATION);
        }

        if(!camAnimPaused) {
            moveCameraTo(tmpV.set(camPosBegin).add(tmpV2.set(camPosDelta).scl(camAnimator.getNormalizedValue())));
        }
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
    public boolean touchWorldDragged(float deviceX, float deviceY, int pointer) {
        if(!camAnimator.isPaused()) {
            camMoveInterrupted = true;
            camAnimator.stop();
            return false;
        }

        return super.touchWorldDragged(deviceX, deviceY, pointer);
    }

    @Override
    public boolean touchWorldUp(float deviceX, float deviceY, int pointer) {
        if(camMoveInterrupted) {
            getCameraTouchController().resetDelta();
            centerCameraPosition(true);
            camMoveInterrupted = false;
        }

        CubeGroup cg = (CubeGroup) getEntityFromCoordinates(deviceX, deviceY);

        if(cg != null && cg == selectingGroup) {
            if(selectedGroup != null)
                selectedGroup.setSelected(false);

            selectedGroup = cg;
            selectedGroup.setSelected(true);
        }

        return super.touchWorldUp(deviceX, deviceY, pointer);
    }

    @Override
    public Entity3D getEntityFromCoordinates(float deviceX, float deviceY) {
        if(!moveAnimator.isPaused())
            return null;

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
            entity.setAnchor(anchor);
        }

        return entity;
    }

    public void centerCameraPosition(boolean animated) {
        tmpV.set(Vector3.Zero);
        int cubeCount = 0;
        for(Entity group : getEntities()) {
            for(CubeEntity cube : ((CubeGroup)group).getCubes()) {
                tmpV.add(cube.getX(), cube.getY(), cube.getZ());
                cubeCount++;
            }
        }
        camPosBegin.set(getCamera().position);
        tmpV.scl(1f/cubeCount);
        camPosDelta.set(tmpV).sub(getCameraTouchController().target);

        if(!animated) {
            moveCameraBy(camPosDelta);
        } else {
            camAnimator.set(0, camPosDelta.len(), CAM_ANIM_DURATION);
            camAnimator.start();
        }
    }

    @Override
    public boolean isRenderingRequested() {
        return super.isRenderingRequested() || !moveAnimator.isPaused() || !rotationAnimator.isPaused() || !camAnimator.isPaused();
    }

}
