package org.example.models.algorithms.heuristics;

import static java.lang.Math.abs;

@FunctionalInterface
public interface HeuristicCalculator {
    int compute(int x1, int y1, int x2, int y2);

    static int manhattan(int x1, int y1, int x2, int y2){
        return abs(x1 - x2) + abs(y1 - y2);
    }

    static int euclid(int x1, int y1, int x2, int y2){
        return (x1-x2)*(x1-x2) + (y1-y2)*(y1-y2);
    }

}
