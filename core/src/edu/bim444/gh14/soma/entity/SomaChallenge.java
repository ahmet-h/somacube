package edu.bim444.gh14.soma.entity;

import com.badlogic.gdx.graphics.Texture;

public class SomaChallenge {

    private int id;
    private String title;
    private Texture image;
    private int[] targetGroupPositions;
    private int bestTime;
    private boolean finished;

    public SomaChallenge(int id, String name, Texture image, int[] targetGroupPositions) {
        this.id = id;
        this.title = name;
        this.image = image;
        this.targetGroupPositions = targetGroupPositions;
    }

    public void setBestTime(int bestTime) {
        this.bestTime = bestTime;
    }

    public int getBestTime() {
        return bestTime;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public boolean isFinished() {
        return finished;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Texture getImage() {
        return image;
    }

    public int[] getTargetGroupPositions() {
        return targetGroupPositions;
    }

}
