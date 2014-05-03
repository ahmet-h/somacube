package edu.bim444.gh14.entity;

import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;
import edu.bim444.gh14.screen.Screen;
import edu.bim444.gh14.screen.World3D;

public class Entity3D extends Entity {

    private ModelInstance instance;
    private ModelBatch modelBatch;
    private Environment environment;
    private BoundingBox bounds;
    private final BoundingBox localBounds;
    private Matrix4 rotation = new Matrix4();

    private Vector3 tmpV = new Vector3();
    private Matrix4 tmpM = new Matrix4();

    public Entity3D(Screen screen, World3D world3D, Model model) {
        super(screen);

        modelBatch = world3D.getModelBatch();
        environment = world3D.getEnvironment();
        bounds = new BoundingBox();
        localBounds = new BoundingBox();
        if(model != null) {
            setModel(model);
        }
    }

    protected void setModel(Model model) {
        instance = new ModelInstance(model);
        instance.calculateBoundingBox(bounds);
        localBounds.set(bounds);
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
        float dx = x - getX();
        float dy = y - getY();
        float dz = z - getZ();
        super.moveTo(x, y, z);
        instance.transform.setTranslation(x, y, z);

        bounds.set(bounds.min.add(x - bounds.min.x, y - bounds.min.y, z - bounds.min.z),
                   bounds.max.add(x - bounds.max.x, y - bounds.max.y, z - bounds.max.z));

        //bounds.set(bounds.min.set(localBounds.getMin()).add(getX(), getY(), getZ()),
        //           bounds.max.set(localBounds.getMax()).add(getX(), getY(), getZ()));

        //bounds.mul(tmpM.set(dx, dy, dz, 0, 0, 0, 0));
    }

    @Override
    public void moveBy(float dx, float dy, float dz) {
        instance.transform.setTranslation(getX() + dx, getY() + dy, getZ() + dz);
        super.moveBy(dx, dy, dz);

        bounds.set(bounds.min.add(dx, dy, dz), bounds.max.add(dx, dy, dz));

        //bounds.set(bounds.min.set(localBounds.getMin()).add(getX(), getY(), getZ()),
        //           bounds.max.set(localBounds.getMax()).add(getX(), getY(), getZ()));

        //bounds.mul(tmpM.set(dx, dy, dz, 0, 0, 0, 0));
    }

    public void rotate(Vector3 axis, float degrees) {
        //float rx = rotation.x;
        //float ry = rotation.y;
        //float rz = rotation.z;
        //float rw = rotation.w;

        tmpM.setToRotation(axis, degrees);
        tmpM.mul(rotation);
        rotation.set(tmpM);
        instance.transform.set(tmpM.set(rotation).setTranslation(getX(), getY(), getZ()));
        tmpV.rotate(axis, degrees);
        //instance.transform.getRotation(rotation);
        //bounds.mul(tmpM.set(rotation.x - rx, rotation.y - ry, rotation.z - rz, rotation.w - rw));
    }

    public void rotateAround(Vector3 point, Vector3 axis, float degrees) {
        //float rx = rotation.x;
        //float ry = rotation.y;
        //float rz = rotation.z;
        //float rw = rotation.w;

        // I see matrices and vectors everywhere
        tmpV.set(point);
        tmpV.sub(getX(), getY(), getZ());
        moveBy(tmpV);
        tmpM.setToRotation(axis, degrees);
        tmpM.mul(rotation);
        rotation.set(tmpM);
        instance.transform.set(rotation);
        tmpV.rotate(axis, degrees);
        // Restores translation
        moveBy(-tmpV.x, -tmpV.y, -tmpV.z);

        //instance.transform.getRotation(rotation);
        //bounds.mul(tmpM.set(rotation.x - rx, rotation.y - ry, rotation.z - rz, rotation.w - rw));
    }

    public ModelInstance getModelInstance() {
        return instance;
    }

    public BoundingBox getBoundingBox() {
        return bounds;
    }

}
