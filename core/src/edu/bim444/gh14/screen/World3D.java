package edu.bim444.gh14.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.utils.DefaultShaderProvider;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.Ray;
import edu.bim444.gh14.entity.Entity3D;

public class World3D extends World {

    private Environment environment;
    private CameraTouchController camController;

    protected ModelBatch modelBatch;

    private Vector3 tmpV = new Vector3();

    public World3D(Screen screen, float worldWidth, float worldHeight) {
        super(screen);

        environment = new Environment();
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.8f, 0.8f, 0.8f, 1f));
        DefaultShaderProvider dsprovider = new DefaultShaderProvider(Gdx.files.internal("default.vertex.glsl"),
                                                                     Gdx.files.internal("default.fragment.glsl"));
        dsprovider.config.numDirectionalLights = 3;
        modelBatch = new ModelBatch(null, dsprovider, null);

        PerspectiveCamera persCam = new PerspectiveCamera(67, worldWidth, worldHeight);
        persCam.position.set(12, 12, 12);
        persCam.lookAt(0, 0, 0);
        persCam.near = 1;
        persCam.far = 300;
        persCam.update();
        setCamera(persCam);
    }

    @Override
    public void updateWorld() {
        super.updateWorld();
        if(camController != null)
            camController.updateDelta();
    }

    @Override
    public void update() {

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

    public Entity3D getEntityFromCoordinates(float deviceX, float deviceY) {
        Ray ray = getCamera().getPickRay(deviceX, deviceY);
        Entity3D entity = null;
        float distance = Float.MAX_VALUE;

        for(int i = 0; i < getSize(); i++) {
            Entity3D e = (Entity3D) getEntity(i);

            tmpV.set(e.getX(), e.getY(), e.getZ());
            float dst2 = ray.origin.dst2(tmpV);
            if(dst2 > distance)
                continue;

            if(Intersector.intersectRayBoundsFast(ray, e.getBoundingBox())) {
                entity = e;
                distance = dst2;
            }
        }

        return entity;
    }

    @Override
    public void dispose() {
        super.dispose();
        modelBatch.dispose();
    }

    public ModelBatch getModelBatch() {
        return modelBatch;
    }

    public Environment getEnvironment() {
        return environment;
    }

    @Override
    public void translateCamera(float deltaX, float deltaY, float deltaZ) {
        super.translateCamera(deltaX, deltaY, deltaZ);
        if(camController != null)
            camController.target.add(deltaX, deltaY, deltaZ);
    }

}
