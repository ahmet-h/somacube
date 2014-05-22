package edu.bim444.gh14.soma.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import edu.bim444.gh14.soma.SomaGame;

public class SomaLauncher {

    public static void main(String[] args) {
        LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
        cfg.title = "Soma Cube";
        cfg.width = 800;
        cfg.height = 450;
        cfg.resizable = false;
        /*Dimension screenDimension = Toolkit.getDefaultToolkit().getScreenSize();
        cfg.width = screenDimension.width;
        cfg.height = screenDimension.height;
        cfg.fullscreen = true;*/
        //cfg.vSyncEnabled = true;
        cfg.useGL30 = true;
        new LwjglApplication(new SomaGame(), cfg);
    }

}
