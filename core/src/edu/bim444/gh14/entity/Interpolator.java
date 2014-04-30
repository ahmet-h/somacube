package edu.bim444.gh14.entity;

public interface Interpolator {

    public static final Interpolator LINEAR = new LinearInterpolator();
    public static final Interpolator ACCELERATE = new AccelerateInterpolator();
    public static final Interpolator DECELERATE = new DecelerateInterpolator();

    public float getInterpolation(float t);

}
