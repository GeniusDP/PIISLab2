package org.example.models.algorithms.astar;

import org.example.models.Direction;
import org.example.models.Matrix;
import org.example.models.Point;

public interface Algorithm {

    int INF = 1_000_000;

    Direction perform(Matrix matrix, Point start, Point finish);

}
