package org.example.controllers;

import org.example.controllers.services.MatrixValidator;
import org.example.models.Direction;
import org.example.models.Matrix;
import org.example.models.Point;
import org.example.models.algorithms.Algorithm;
import org.example.models.algorithms.NoWayFoundException;
import org.example.utils.MatrixIOUtil;
import org.example.utils.Pair;

import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class Controller {

    public void start(Algorithm algorithm) {
        try (FileInputStream fs = new FileInputStream("src/main/resources/input.txt")) {
            Matrix matrix = MatrixIOUtil.readMatrix(fs);

            Point botPoint = Point.ofZeroIndexationValues(matrix.getN()-2, 1);
            Point playerPoint = Point.ofZeroIndexationValues(matrix.getN()-2, matrix.getM()-2);

            Properties properties = new Properties();
            properties.load(new FileReader("src/main/resources/application.properties"));

            int exitRow = Integer.parseInt((String) properties.get("exit.row"));
            int exitCol = Integer.parseInt((String) properties.get("exit.col"));

            Point exitPoint = Point.ofZeroIndexationValues(exitRow, exitCol);

            if (!MatrixValidator.isValid(matrix, botPoint, playerPoint, exitPoint)) {
                throw new NoWayFoundException("start and finish ceils should not be obstacles");
            }

            matrix = generateContent(matrix, botPoint, playerPoint, exitPoint);


            MatrixIOUtil.printToScreen(matrix);
            Direction direction = algorithm.perform(matrix, playerPoint, botPoint);
            System.out.println(direction);
            MatrixIOUtil.printToScreen(matrix);

        } catch (NoWayFoundException e) {
            System.err.println("ERROR: Way could not be found: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("ERROR: reading was not successful:(" + e);
        }
    }

    private Matrix generateContent(Matrix matrix, Point botPoint, Point playerPoint, Point exitPoint) {
        int[][] array = matrix.getArray();
        array[botPoint.row][botPoint.col] = -2;
        array[playerPoint.row][playerPoint.col] = -3;
        array[exitPoint.row][exitPoint.col] = -4;
        return new Matrix(array);
    }



}
