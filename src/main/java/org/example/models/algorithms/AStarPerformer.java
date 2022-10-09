package com.kpi.fics.piis.zaranik.models.algorithms;

import com.kpi.fics.piis.zaranik.models.algorithms.heuristics.HeuristicCalculator;
import com.kpi.fics.piis.zaranik.utils.IntPair;
import com.kpi.fics.piis.zaranik.models.Matrix;
import com.kpi.fics.piis.zaranik.models.Point;
import lombok.NoArgsConstructor;

import java.util.*;

@NoArgsConstructor
public class AStarPerformer extends AbstractAlgorithm {

    private boolean[] closed;
    private boolean[] isOpenNow;
    private int[] from;
    private int[] g;
    private int[] f;

    public AStarPerformer(HeuristicCalculator heuristicCalculator) {
        super(heuristicCalculator);
    }

    @Override
    public Matrix perform(Matrix matrix, Point start, Point finish) {
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

            relax(matrix.getArray(), curr, neighbour_i, neighbour_j - 1, open, matrix.getN(), finishId, h);
            relax(matrix.getArray(), curr, neighbour_i, neighbour_j + 1, open, matrix.getN(), finishId, h);
            relax(matrix.getArray(), curr, neighbour_i - 1, neighbour_j, open, matrix.getN(), finishId, h);
            relax(matrix.getArray(), curr, neighbour_i + 1, neighbour_j, open, matrix.getN(), finishId, h);

            relax(matrix.getArray(), curr, neighbour_i + 1, neighbour_j - 1, open, matrix.getN(), finishId, h);
            relax(matrix.getArray(), curr, neighbour_i - 1, neighbour_j - 1, open, matrix.getN(), finishId, h);
            relax(matrix.getArray(), curr, neighbour_i - 1, neighbour_j + 1, open, matrix.getN(), finishId, h);
            relax(matrix.getArray(), curr, neighbour_i + 1, neighbour_j + 1, open, matrix.getN(), finishId, h);

        }

        if(from[finishId]==-1){
            throw new NoWayFoundException("OOOPS! NO WAY!");
        }
        return findWayFromStartToFinish(from, matrix.getArray(), finishId);
    }

    private Matrix findWayFromStartToFinish(int[] parents, int[][] array, int finishId) {
        List<Integer> way = new ArrayList<>();
        int index = finishId;
        way.add(index);
        while (parents[index] != -1) {
            index = parents[index];
            way.add(index);
        }
        way.add(index);
        Collections.reverse(way);
        for (int i = 0; i < way.size(); i++) {
            int y = way.get(i) / array.length;
            int x = way.get(i) % array.length;
            if (array[y][x] == -1) {
                continue;
            }
            array[y][x] = i;
        }
        return new Matrix(array);
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
