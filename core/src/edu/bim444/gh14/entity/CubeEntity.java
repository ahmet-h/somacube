package edu.bim444.gh14.entity;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import edu.bim444.gh14.screen.Screen;
import edu.bim444.gh14.screen.World3D;

public class CubeEntity extends Entity3D {

    public CubeEntity(Screen screen, World3D world3D, float width) {
        super(screen, world3D, null);

        ModelBuilder modelBuilder = new ModelBuilder();
        setModel(modelBuilder.createBox(width, width, width,
                new Material(ColorAttribute.createDiffuse(new Color(0.4f, 0.8f, 0, 1))),
                VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal));
    }

    public CubeEntity(Screen screen, World3D world3D, float width, Texture texture) {
        super(screen, world3D, null);

        ModelBuilder modelBuilder = new ModelBuilder();
        setModel(modelBuilder.createBox(width, width, width,
                new Material(TextureAttribute.createDiffuse(texture), ColorAttribute.createSpecular(1f, 1f, 1f, 1f)),
                VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal | VertexAttributes.Usage.TextureCoordinates));
    }

    public CubeEntity(Screen screen, World3D world3D, float width, Texture texture, float alpha) {
        super(screen, world3D, null);

        ModelBuilder modelBuilder = new ModelBuilder();
        setModel(modelBuilder.createBox(width, width, width,
                new Material(TextureAttribute.createDiffuse(texture), ColorAttribute.createSpecular(1f, 1f, 1f, 1f),
                             ColorAttribute.createDiffuse(alpha, alpha, alpha, alpha)),
                VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal | VertexAttributes.Usage.TextureCoordinates));
    }

    @Override
    public void update() {

    }

}
