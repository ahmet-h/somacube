package edu.bim444.gh14.screen;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;

public class CameraTouchController extends CameraInputController {

    public float zoomMin = 18;
    public float zoomMax = 64;

    private final Vector3 zoomMinV;
    private final Vector3 zoomMaxV;

    public float angleMax = 85;
    public float deltaMax = 0.02f;

    private float deltaX;
    private float deltaY;
    private float[] deltaArrayX;
    private float[] deltaArrayY;
    private final int deltaLength = 6;
    private int deltaIndex;
    private final float deltaFactor = 0.98f;

    private Vector3 tmpV = new Vector3();

    public CameraTouchController(Camera camera) {
        super(camera);
        pinchZoomFactor = 48;

        zoomMinV = new Vector3();
        zoomMaxV = new Vector3();

        deltaArrayX = new float[deltaLength];
        deltaArrayY = new float[deltaLength];
    }

    public void updateDelta() {
        if(deltaX != 0 || deltaY != 0) {
            process(deltaX, deltaY, rotateButton);
        }

        deltaX *= deltaFactor;
        deltaY *= deltaFactor;

        if(Math.abs(deltaX) < 0.0001f)
            deltaX = 0;
        if(Math.abs(deltaY) < 0.0001f)
            deltaY = 0;
    }

    public boolean isRenderingRequested() {
        return (deltaX != 0 || deltaY != 0);
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        deltaX = deltaY = 0;
        for(int i = 0; i < deltaLength; i++) {
            deltaArrayX[i] = 0;
            deltaArrayY[i] = 0;
        }
        deltaIndex = 0;

        return super.touchDown(screenX, screenY, pointer, button);
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        deltaX = deltaY = 0;
        for(int i = 0; i < deltaLength; i++) {
            deltaX += deltaArrayX[i];
            deltaY += deltaArrayY[i];
        }
        deltaX /= deltaLength;
        deltaY /= deltaLength;

        return super.touchUp(screenX, screenY, pointer, button);
    }

    @Override
    protected boolean process(float deltaX, float deltaY, int button) {
        super.process(deltaX, deltaY, button);

        float camAngle = getCameraAngle();
        float fixAngle = camAngle - angleMax;
        if(fixAngle > 0) {
            tmpV.set(camera.direction).crs(camera.up).nor();
            camera.rotateAround(target, tmpV, -fixAngle * Math.signum(camera.direction.y));
        }

        this.deltaArrayX[deltaIndex] = (Math.abs(deltaX) > deltaMax) ? deltaMax * Math.signum(deltaX) : deltaX;
        this.deltaArrayY[deltaIndex] = (Math.abs(deltaY) > deltaMax) ? deltaMax * Math.signum(deltaY) : deltaY;
        deltaIndex++;
        if(deltaIndex >= deltaLength)
            deltaIndex = 0;

        return true;
    }

    @Override
    public boolean zoom(float amount) {
        boolean result = super.zoom(amount);

        zoomMinV.set(tmpV.set(camera.direction).scl(zoomMin));
        zoomMaxV.set(tmpV.set(camera.direction).scl(zoomMax));

        tmpV.set(target).sub(camera.position);
        float zoom = tmpV.dot(camera.direction);
        if(zoom < zoomMin)
            camera.translate(tmpV.sub(zoomMinV));
        else if(zoom > zoomMax)
            camera.translate(tmpV.sub(zoomMaxV));

        return result;
    }

    public float getCameraAngle() {
        camera.normalizeUp();
        return MathUtils.radiansToDegrees * (float)Math.acos(camera.up.dot(Vector3.Y));
    }

}
