package edu.bim444.gh14.entity;

public class DecelerateInterpolator implements Interpolator {

    @Override
    public float getInterpolation(float t) {
        return (1 - (1 - t) * (1 - t));
    }

}
