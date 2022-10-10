package org.example.models;

import lombok.Getter;
import lombok.ToString;
import org.example.models.algorithms.astar.DistFinder;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point point = (Point) o;
        return row == point.row && col == point.col;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, col);
    }

    public int manhattanDist(Point point){
        return Math.abs(this.row - point.row) + Math.abs(this.col - point.col);
    }

}
