package org.example.controllers.services;

import org.example.models.Matrix;
import org.example.models.Point;

public class MatrixValidator {
    public static boolean isValid(Matrix matrix, Point startPoint, Point finishPoint, Point exitPoint) {
        if (matrix.ceilIsObstacle(startPoint) || matrix.ceilIsObstacle(finishPoint) || matrix.ceilIsObstacle(exitPoint)){
            return false;
        }
        //проверка на связность

        return true;
    }
}
