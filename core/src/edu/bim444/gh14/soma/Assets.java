package edu.bim444.gh14.soma;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import edu.bim444.gh14.soma.entity.SomaChallenges;

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
    public static Texture redPanel;
    public static Texture greyPanel;
    public static Texture helpButton;

    public static Texture placeholder;

    public static BitmapFont robotoTiny;
    public static BitmapFont robotoSmall;
    public static BitmapFont robotoNormal;
    public static BitmapFont robotoBig;

    public static NinePatch redButtonNinePatch;
    public static NinePatch redPanelNinePatch;
    public static NinePatch greyPanelNinePatch;

    public static SomaChallenges challenges;

    private static boolean loaded = false;

    public static boolean isLoaded() {
        return loaded;
    }

    public static void load() {
        if(loaded)
            return;

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
        redPanel = new Texture("red_panel.png");
        redPanel.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        greyPanel = new Texture("grey_panel.png");
        greyPanel.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        helpButton = new Texture("helpButton.png");
        helpButton.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        placeholder = new Texture("soma_placeholder.png");
        placeholder.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        // Load Fonts
        FreeTypeFontGenerator ftfg = new FreeTypeFontGenerator(Gdx.files.internal("RobotoCondensed-Regular.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter fontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        fontParameter.minFilter = Texture.TextureFilter.Linear;
        fontParameter.magFilter = Texture.TextureFilter.Linear;
        fontParameter.size = 28;
        robotoTiny = ftfg.generateFont(fontParameter);
        fontParameter.size = 36;
        robotoSmall = ftfg.generateFont(fontParameter);
        fontParameter.size = 48;
        robotoNormal = ftfg.generateFont(fontParameter);
        fontParameter.size = 64;
        robotoBig = ftfg.generateFont(fontParameter);

        ftfg.dispose();

        // Load NinePatches
        redButtonNinePatch = new NinePatch(redButton, 8, 8, 8, 10);
        redPanelNinePatch = new NinePatch(redPanel, 8, 8, 8, 10);
        greyPanelNinePatch = new NinePatch(greyPanel, 8, 8, 8, 10);

        // Soma Challenges
        challenges = new SomaChallenges();

        loaded = true;
    }

    public static void dispose() {
        if(!loaded)
            return;

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
        redPanel.dispose();
        helpButton.dispose();

        placeholder.dispose();

        robotoTiny.dispose();
        robotoSmall.dispose();
        robotoNormal.dispose();
        robotoBig.dispose();

        challenges.dispose();

        loaded = false;
    }

}
