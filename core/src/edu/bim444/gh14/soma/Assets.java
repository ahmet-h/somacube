package edu.bim444.gh14.soma;

import com.badlogic.gdx.graphics.Texture;

public class Assets {

    public static Texture joystickBase;
    public static Texture joystickHead;
    public static Texture wood;
    public static Texture wood_brown;
    public static Texture wood_selected;
    public static Texture arrow_up;
    public static Texture arrow_down;

    public static void load() {
        joystickBase = new Texture("flatDark06.png");
        joystickBase.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        joystickHead = new Texture("flatDark00.png");
        joystickHead.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        wood = new Texture("wood_small.jpg");
        //wood.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        wood_brown = new Texture("wood_small_brown.jpg");
        //wood_brown.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        wood_selected = new Texture("wood_small_selected.jpg");
        //wood_selected.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        arrow_up = new Texture("flatDark25.png");
        arrow_up.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        arrow_down = new Texture("flatDark26.png");
        arrow_down.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
    }

    public static void dispose() {
        joystickBase.dispose();
        joystickHead.dispose();
        wood.dispose();
        wood_brown.dispose();
        wood_selected.dispose();
        arrow_up.dispose();
        arrow_down.dispose();
    }

}
