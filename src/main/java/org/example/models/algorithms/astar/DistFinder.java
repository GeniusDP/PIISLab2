package org.example.models.algorithms.astar;

import org.example.models.Matrix;
import org.example.models.Point;
import org.example.models.algorithms.astar.heuristics.HeuristicCalculator;
import org.example.models.algorithms.astar.heuristics.HeuristicFinder;
import org.example.utils.IntPair;

import java.util.Arrays;
import java.util.PriorityQueue;

import static org.example.models.algorithms.astar.Algorithm.INF;

public class DistFinder {

    private boolean[] closed;
    private boolean[] isOpenNow;
    private int[] from;
    private int[] g;
    private int[] f;
    protected final HeuristicCalculator heuristicCalculator;
    protected final HeuristicFinder heuristicFinder;

    public DistFinder() {
        this.heuristicCalculator = HeuristicCalculator::manhattan;
        this.heuristicFinder = new HeuristicFinder(heuristicCalculator);
    }

    public int getMinimalDist(Matrix matrix, Point start, Point finish) {
        int[][] array = matrix.getArray();
        int[][] h = heuristicFinder.findHeuristic(matrix.getN(), matrix.getM());

        PriorityQueue<IntPair> open = new PriorityQueue<>();

        initArrays(matrix);

        int startId = start.getPointId(matrix.getN());
        int finishId = finish.getPointId(matrix.getN());
        g[startId] = 0;
        f[startId] = g[startId] + h[startId][finishId];
        isOpenNow[startId] = true;
        open.add(new IntPair(f[startId], startId));

        while (!open.isEmpty()) {
            int curr = open.peek().second;
            IntPair neighbour = IntPair.convertSumFormToIndexes(curr, matrix.getN());
            int neighbour_i = neighbour.first;
            int neighbour_j = neighbour.second;

            //extracting current node from the heap
            open.remove();
            closed[curr] = true;

            relax(array, curr, neighbour_i, neighbour_j - 1, open, matrix.getN(), finishId, h);
            relax(array, curr, neighbour_i, neighbour_j + 1, open, matrix.getN(), finishId, h);
            relax(array, curr, neighbour_i - 1, neighbour_j, open, matrix.getN(), finishId, h);
            relax(array, curr, neighbour_i + 1, neighbour_j, open, matrix.getN(), finishId, h);
        }

        if(from[finishId]==-1){
            return INF;
        }
        return g[finishId];
    }


    private void initArrays(Matrix matrix) {
        closed = new boolean[matrix.getN() * matrix.getM()];
        Arrays.fill(closed, false);

        isOpenNow = new boolean[matrix.getN() * matrix.getM()];
        Arrays.fill(closed, false);

        from = new int[matrix.getN() * matrix.getM()];
        Arrays.fill(from, -1);

        g = new int[matrix.getN() * matrix.getM()];
        Arrays.fill(g, INF);

        f = new int[matrix.getN() * matrix.getM()];
        Arrays.fill(f, INF);
    }


    private void relax(int[][] array, int curr, int neighbour_i, int neighbour_j, PriorityQueue<IntPair> open, int n, int fin, int[][] h) {
        if (!closed[neighbour_i * n + neighbour_j] && array[neighbour_i][neighbour_j] != -1) {
            int temp_g = g[curr] + 1;
            if (temp_g < g[neighbour_i * n + neighbour_j]) {
                from[neighbour_i * n + neighbour_j] = curr;
                g[neighbour_i * n + neighbour_j] = temp_g;
                f[neighbour_i * n + neighbour_j] = g[neighbour_i * n + neighbour_j] + h[neighbour_i * n + neighbour_j][fin];
            }

            if (!isOpenNow[neighbour_i * n + neighbour_j]) {
                open.add(new IntPair(f[neighbour_i * n + neighbour_j], neighbour_i * n + neighbour_j));
            }
        }
    }

}
