package edu.bim444.gh14.soma.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import edu.bim444.gh14.soma.SomaGame;

public class SomaLauncher {

    public static void main(String[] args) {
        LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
        cfg.title = "SomaCube";
        cfg.width = 800;
        cfg.height = 480;
        new LwjglApplication(new SomaGame(), cfg);
    }

}
