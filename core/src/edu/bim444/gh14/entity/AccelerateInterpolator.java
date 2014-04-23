package edu.bim444.gh14.entity;

public class AccelerateInterpolator implements Interpolator {

    @Override
    public float getInterpolation(float t) {
        return t*t;
    }

}
