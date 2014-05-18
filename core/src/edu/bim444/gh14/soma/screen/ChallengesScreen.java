package edu.bim444.gh14.soma.screen;

import edu.bim444.gh14.GdxGame;
import edu.bim444.gh14.screen.Screen;
import edu.bim444.gh14.soma.Assets;
import edu.bim444.gh14.soma.entity.ChallengesGrid;
import edu.bim444.gh14.soma.entity.SomaChallenges;

public class ChallengesScreen extends Screen {

    @Override
    public void init(GdxGame game) {
        super.init(game);

        SomaChallenges challenges = Assets.challenges;

        ChallengesGrid grid = new ChallengesGrid(this, challenges);
        addEntity(grid);
    }

    @Override
    public void update() {

    }

    @Override
    public void draw(float alpha) {

    }

}
