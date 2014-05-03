package edu.bim444.gh14.entity;

public class Animator {

    private float fromValue, toValue;
    private float deltaValue;
    private int duration;
    private Interpolator interpolator;
    private int startDelay, endDelay;
    private int startDelayCount, endDelayCount;
    private int tick;
    private boolean paused;
    private AnimatorListener listener;

    public Animator(float fromValue, float toValue, int duration, Interpolator interpolator, int startDelay, int endDelay) {
        set(fromValue, toValue, duration, interpolator, startDelay, endDelay);
    }

    public Animator(float fromValue, float toValue, int duration, Interpolator interpolator) {
        this(fromValue, toValue, duration, interpolator, 0, 0);
    }

    public void set(float fromValue, float toValue, int duration, Interpolator interpolator, int startDelay, int endDelay) {
        this.fromValue = fromValue;
        this.toValue = toValue;
        this.deltaValue = toValue - fromValue;
        this.duration = duration;
        this.interpolator = interpolator;
        this.startDelay = startDelay;
        this.endDelay = endDelay;
        reset();
        paused = true;
    }

    public void set(float fromValue, float toValue, int duration, Interpolator interpolator) {
        set(fromValue, toValue, duration, interpolator, startDelay, endDelay);
    }

    public void set(float fromValue, float toValue, int duration) {
        set(fromValue, toValue, duration, interpolator);
    }

    public void update() {
        if(paused) return;

        if(startDelayCount > 0) {
            startDelayCount--;
            return;
        }

        if(tick < duration) {
            tick++;
        }

        if(tick == duration) {
            if(endDelayCount > 0) {
                endDelayCount--;
            } else {
                pause();
                if(listener != null)
                    listener.onAnimationEnd();
            }
        }
    }

    public float getCurrentValue() {
        final float normalized = (float) tick / duration;
        return fromValue + interpolator.getInterpolation(normalized) * deltaValue;
    }

    public float getNormalizedValue() {
        return interpolator.getInterpolation((float) tick / duration);
    }

    public float getProgress() {
        return (float) tick / duration;
    }

    public void start() {
        paused = false;
    }

    public void stop() {
        pause();
        reset();
    }

    public void pause() {
        paused = true;
    }

    public void reset() {
        tick = 0;
        startDelayCount = startDelay;
        endDelayCount = endDelay;
    }

    public void setAnimatorListener(AnimatorListener listener) {
        this.listener = listener;
    }

    public boolean isPaused() {
        return paused;
    }

}
