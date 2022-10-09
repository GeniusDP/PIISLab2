package org.example.models;

public enum Direction {
    UP, DOWN, LEFT, RIGHT, NO_MOVE;

    public static Direction getValue(int dRow, int dCol){
        return switch (dRow){
            case 1 -> UP;
            case -1 -> DOWN;
            default -> {
                if(dCol == 1){
                    yield RIGHT;
                }
                if(dCol == -1){
                    yield LEFT;
                }
                yield NO_MOVE;
            }
        };
    }
}
