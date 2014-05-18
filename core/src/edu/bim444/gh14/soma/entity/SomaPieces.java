package edu.bim444.gh14.soma.entity;

public interface SomaPieces {

    public static final int[] PIECE_V = new int[]{
            0, 0, 0,
            1, 0, 0,
            0, 0, 1};
    public static final int[] PIECE_L = new int[]{
            0, 0, 0,
            1, 0, 0,
            2, 0, 0,
            0, 0, 1};
    public static final int[] PIECE_T = new int[]{
            0, 0, 0,
            1, 0, 0,
            -1, 0, 0,
            0, 0, 1};
    public static final int[] PIECE_Z = new int[]{
            0, 0, 0,
            1, 0, 0,
            0, 0, 1,
            -1, 0, 1};
    public static final int[] PIECE_A = new int[]{
            0, 0, 0,
            0, 1, 0,
            0, 0, 1,
            1, 1, 0};
    public static final int[] PIECE_B = new int[]{
            0, 0, 0,
            0, 1, 0,
            0, 1, 1,
            1, 0, 0};
    public static final int[] PIECE_P = new int[]{
            0, 0, 0,
            0, 1, 0,
            0, 0, 1,
            1, 0, 0};

}
