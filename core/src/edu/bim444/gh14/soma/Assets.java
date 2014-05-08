package edu.bim444.gh14.soma;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

public class Assets {

    public static Texture joystickBase;
    public static Texture joystickHead;
    public static Texture wood;
    public static Texture woodBrown;
    public static Texture woodBlack;
    public static Texture arrowUp;
    public static Texture arrowDown;
    public static Texture redButton;
    public static Texture titleImage;
    public static Texture pauseButton;

    public static BitmapFont robotoSmall;
    public static BitmapFont robotoNormal;
    public static BitmapFont robotoBig;

    public static NinePatch redButtonNinePatch;

    public static void load() {
        // Load Textures
        joystickBase = new Texture("flatDark06.png");
        joystickBase.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        joystickHead = new Texture("flatDark00.png");
        joystickHead.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        wood = new Texture("wood.jpg");
        wood.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        woodBrown = new Texture("wood_brown.jpg");
        woodBrown.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        woodBlack = new Texture("wood_black.jpg");
        woodBlack.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        arrowUp = new Texture("flatDark25.png");
        arrowUp.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        arrowDown = new Texture("flatDark26.png");
        arrowDown.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        redButton = new Texture("red_button09.png");
        redButton.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        titleImage = new Texture("title.png");
        titleImage.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        pauseButton = new Texture("flatDark13.png");
        pauseButton.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        // Load Fonts
        FreeTypeFontGenerator ftfg = new FreeTypeFontGenerator(Gdx.files.internal("RobotoCondensed-Regular.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter fontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        fontParameter.minFilter = Texture.TextureFilter.Linear;
        fontParameter.magFilter = Texture.TextureFilter.Linear;
        fontParameter.size = 28;
        robotoSmall = ftfg.generateFont(fontParameter);
        fontParameter.size = 36;
        robotoNormal = ftfg.generateFont(fontParameter);
        fontParameter.size = 48;
        robotoBig = ftfg.generateFont(fontParameter);

        ftfg.dispose();

        // Load NinePatches
        redButtonNinePatch = new NinePatch(redButton, 8, 8, 8, 10);
    }

    public static void dispose() {
        joystickBase.dispose();
        joystickHead.dispose();
        wood.dispose();
        woodBrown.dispose();
        woodBlack.dispose();
        arrowUp.dispose();
        arrowDown.dispose();
        redButton.dispose();
        titleImage.dispose();
        pauseButton.dispose();

        robotoSmall.dispose();
        robotoNormal.dispose();
        robotoBig.dispose();
    }

}
