package com.kpi.fics.piis.zaranik.models;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public final class Point {
    private final int row;
    private final int col;

    public Point(int rowReal, int colReal) {
        if (rowReal <= 0 || colReal <= 0) {
            throw new IllegalArgumentException("not valid params");
        }
        this.row = rowReal - 1;
        this.col = colReal - 1;
    }

    public int getPointId(int rowsNum) {
        return row * rowsNum + col;
    }

}
