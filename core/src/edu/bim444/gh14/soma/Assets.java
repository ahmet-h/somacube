package edu.bim444.gh14.soma;

import com.badlogic.gdx.graphics.Texture;

public class Assets {

    public static Texture joystickBase;
    public static Texture joystickHead;
    public static Texture wood;

    public static void load() {
        joystickBase = new Texture("flatDark06.png");
        joystickBase.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        joystickHead = new Texture("flatDark00.png");
        joystickHead.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        wood = new Texture("wood_small.jpg");
        wood.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
    }

}
