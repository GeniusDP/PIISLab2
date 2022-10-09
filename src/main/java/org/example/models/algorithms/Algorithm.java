package com.kpi.fics.piis.zaranik.models.algorithms;

import com.kpi.fics.piis.zaranik.models.Matrix;
import com.kpi.fics.piis.zaranik.models.Point;

public interface Algorithm {

    int INF = 1_000_000;

    Matrix perform(Matrix matrix, Point start, Point finish);

}
