package org.example.models;

import org.example.models.algorithms.astar.DistFinder;

import static org.example.models.algorithms.astar.Algorithm.INF;

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

    public void print(){
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[0].length; j++) {
                System.out.printf("%4d", array[i][j]);
            }
            System.out.println();
        }
    }

    public int getMetrics() {
        Point botPoint = findValue(-2);
        Point playerPoint = findValue(-3);
        Point exitPoint = findValue(-4);
        if(playerPoint == null || botPoint == null){
            return 0;
        }
        if(exitPoint == null){
            return INF;
        }
        int dExit = this.realDist(playerPoint, exitPoint);
        int dBot = this.realDist(playerPoint, botPoint);
//        System.out.println("Metrix: " + (dBot - dExit));
        return dBot - 2*dExit;
    }

    private int realDist(Point p1, Point p2) {
        DistFinder distFinder = new DistFinder();
        return distFinder.getMinimalDist(this, p1, p2);
    }

    public Point findValue(int value){
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if(array[i][j] == value){
                    return Point.ofZeroIndexationValues(i, j);
                }
            }
        }
        return null;
    }

    public boolean isFinish() {
        return findValue(-4) == null || findValue(-3) == null || findValue(-2) == null;
    }

    public boolean playerCanMove(Direction direction) {
        Point point = findValue(-3);
        if(point == null){
            return false;
        }
        return switch (direction) {
            case UP -> array[point.row - 1][point.col] != -1;
            case DOWN -> array[point.row + 1][point.col] != -1;
            case RIGHT -> array[point.row][point.col + 1] != -1;
            case LEFT -> array[point.row][point.col - 1] != -1;
            default -> true;
        };
    }

    public Matrix playerMove(Direction direction) {
        int[][] a = this.getArray();
        Point point = findValue(-3);
        a[point.row][point.col] = 0;
        switch (direction) {
            case UP -> a[point.row - 1][point.col] = -3;
            case DOWN -> a[point.row + 1][point.col] = -3;
            case RIGHT -> a[point.row][point.col + 1] = -3;
            case LEFT -> a[point.row][point.col - 1] = -3;
            case NO_MOVE -> a[point.row][point.col] = -3;
        }
        return new Matrix(a);

    }


}
