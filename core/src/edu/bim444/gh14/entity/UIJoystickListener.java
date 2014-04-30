package edu.bim444.gh14.entity;

public interface UIJoystickListener {

    public static final int LEFT = 0;
    public static final int RIGHT = 1;
    public static final int UP = 2;
    public static final int DOWN = 3;

    public void onDirection(int dir);

}
