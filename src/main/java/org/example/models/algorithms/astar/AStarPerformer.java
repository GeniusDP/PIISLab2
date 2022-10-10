package org.example.models.algorithms.astar;

import lombok.NoArgsConstructor;
import org.example.models.Direction;
import org.example.models.Matrix;
import org.example.models.Point;
import org.example.models.algorithms.astar.heuristics.HeuristicCalculator;
import org.example.utils.IntPair;
import org.example.utils.coloring.Color;
import org.example.utils.coloring.ColorfulPrinter;

import java.util.*;

import static org.example.models.Direction.*;

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
    public Direction perform(Matrix matrix, Point start, Point finish) {
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
            return NO_MOVE;
        }
        ColorfulPrinter.printlnColorfullyAndReset(Color.ANSI_CYAN, "" + g[finishId]);
        return findDirectionFromStartToFinish(from, array, finishId);
    }

    private Direction findDirectionFromStartToFinish(int[] parents, int[][] array, int finishId) {
        List<Integer> way = new ArrayList<>();
        int index = finishId;
        way.add(index);
        while (parents[index] != -1) {
            index = parents[index];
            way.add(index);
        }
        Collections.reverse(way);

        int x1 = 0, y1 = 0;
        int x2 = 0, y2 = 0;
        for (int i = 0; i < way.size(); i++) {
            int y = way.get(i) / array.length;
            int x = way.get(i) % array.length;
            if(i == 0){
                y1 = y;
                x1 = x;
            }
            if(i == 1){
                y2 = y;
                x2 = x;
            }
        }
        return Direction.getValue(y2-y1, x2-x1);
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
