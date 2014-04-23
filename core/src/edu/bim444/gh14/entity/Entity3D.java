package edu.bim444.gh14.entity;

import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.collision.BoundingBox;
import edu.bim444.gh14.screen.Screen;
import edu.bim444.gh14.screen.World3D;

public class Entity3D extends Entity {

    private ModelInstance instance;
    private ModelBatch modelBatch;
    private Environment environment;
    private BoundingBox bounds;

    public Entity3D(Screen screen, World3D world3D, Model model) {
        super(screen);

        modelBatch = world3D.getModelBatch();
        environment = world3D.getEnvironment();
        bounds = new BoundingBox();
        if(model != null) {
            instance = new ModelInstance(model);
            instance.calculateBoundingBox(bounds);
        }
    }

    protected void setModel(Model model) {
        instance = new ModelInstance(model);
        instance.calculateBoundingBox(bounds);
    }

    @Override
    public void update() {

    }

    @Override
    public void draw(float alpha) {
        modelBatch.begin(getScreen().getWorld().getCamera());
        modelBatch.render(instance, environment);
        modelBatch.end();
    }

    @Override
    public void updateBounds() {

    }

    @Override
    public void moveTo(float x, float y, float z) {
        super.moveTo(x, y, z);
        instance.transform.setTranslation(x, y, z);
    }

    @Override
    public void moveBy(float x, float y, float z) {
        instance.transform.setTranslation(getX() + x, getY() + y, getZ() + z);
        super.moveBy(x, y, z);
    }

    public ModelInstance getModelInstance() {
        return instance;
    }

}
