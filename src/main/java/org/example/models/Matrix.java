package com.kpi.fics.piis.zaranik.models;

public final class Matrix {
    public static final int MAX_SIZE = 200;

    private final int[][] array;
    private final int n;
    private final int m;

    public Matrix(int[][] array) {
        if (array == null) {
            throw new IllegalArgumentException("array should not be null!");
        }
        this.array = array;
        this.n = array.length;
        this.m = array[0].length;
    }

    public int[][] getArray() {
        int[][] cpyArray = new int[n][m];
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                cpyArray[i][j] = array[i][j];
            }
        }
        return cpyArray;
    }

    public int getN() {
        return n;
    }

    public int getM() {
        return m;
    }

    public boolean ceilIsObstacle(Point point){
        int x = point.getRow();
        int y = point.getCol();
        return array[x][y] == -1;
    }

}
