package org.example.models;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class Point {
    public int row;
    public int col;

    private Point(int rowReal, int colReal) {
        if (rowReal <= 0 || colReal <= 0) {
            throw new IllegalArgumentException("not valid params");
        }
        this.row = rowReal - 1;
        this.col = colReal - 1;
    }

    public int getPointId(int rowsNum) {
        return row * rowsNum + col;
    }

    public static Point ofZeroIndexationValues(int row, int col){
        return new Point(row + 1, col + 1);
    }

}
