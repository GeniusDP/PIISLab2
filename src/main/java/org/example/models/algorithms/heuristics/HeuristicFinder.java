package org.example.models.algorithms.heuristics;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class HeuristicFinder {
    private final HeuristicCalculator hc;

    public int[][] findHeuristic(int n, int m) {
        int[][] h = new int[n * m][n * m];

        for (int x1 = 0; x1 < n; x1++) {
            for (int y1 = 0; y1 < m; y1++) {
                for (int x2 = 0; x2 < n; x2++) {
                    for (int y2 = 0; y2 < m; y2++) {
                        h[x1 * n + y1][x2 * n + y2] = hc.compute(x1, y1, x2, y2);
                    }
                }
            }
        }
        return h;
    }

}
