package edu.bim444.gh14.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import edu.bim444.gh14.entity.Entity;

public class World3D extends World {

    private Environment environment;
    private CameraTouchController camController;

    protected ModelBatch modelBatch;

    public World3D(Screen screen, float worldWidth, float worldHeight) {
        super(screen);

        environment = new Environment();
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.8f, 0.8f, 0.8f, 1f));
        modelBatch = new ModelBatch(Gdx.files.internal("default.vertex.glsl"), Gdx.files.internal("default.fragment.glsl"));

        PerspectiveCamera persCam = new PerspectiveCamera(67, worldWidth, worldHeight);
        persCam.position.set(12, 12, 12);
        persCam.lookAt(0, 0, 0);
        persCam.near = 1;
        persCam.far = 300;
        persCam.update();
        setCamera(persCam);
    }

    @Override
    public void drawWorld(float alpha) {
        super.drawWorld(alpha);
    }

    @Override
    public void update() {
        if(camController != null)
            camController.updateDelta();
    }

    @Override
    public void draw(float alpha) {

    }

    @Override
    public boolean touchWorldDown(float deviceX, float deviceY, int pointer) {
        camController.touchDown((int)deviceX, (int)deviceY, pointer, Input.Buttons.LEFT);
        return super.touchWorldDown(deviceX, deviceY, pointer);
    }

    @Override
    public boolean touchWorldUp(float deviceX, float deviceY, int pointer) {
        camController.touchUp((int)deviceX, (int)deviceY, pointer, Input.Buttons.LEFT);
        return super.touchWorldUp(deviceX, deviceY, pointer);
    }

    @Override
    public boolean touchWorldDragged(float deviceX, float deviceY, int pointer) {
        camController.touchDragged((int)deviceX, (int)deviceY, pointer);
        return super.touchWorldDragged(deviceX, deviceY, pointer);
    }

    @Override
    public boolean isRenderingRequested() {
        return super.isRenderingRequested() || (camController != null && camController.isRenderingRequested());
    }

    public void setCameraTouchController(CameraTouchController camController) {
        this.camController = camController;
        //Gdx.input.setInputProcessor(camController);
    }

    public Entity getEntityFromCoordinates(float deviceX, float deviceY) {
        return null;
    }

    @Override
    public void dispose() {
        modelBatch.dispose();
    }

    public ModelBatch getModelBatch() {
        return modelBatch;
    }

    public Environment getEnvironment() {
        return environment;
    }

}
