package org.example.controllers;

import org.example.controllers.services.MatrixValidator;
import org.example.models.Direction;
import org.example.models.Matrix;
import org.example.models.Point;
import org.example.models.algorithms.astar.Algorithm;
import org.example.models.algorithms.astar.NoWayFoundException;
import org.example.models.alpha_beta_pruning.AlphaBetaPruning;
import org.example.models.minimax.template.MinimaxTemplate;
import org.example.utils.MatrixIOUtil;
import org.example.utils.coloring.Color;
import org.example.utils.coloring.ColorfulPrinter;

import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class Controller {

    public void start(Algorithm algorithm) {
        try (FileInputStream fs = new FileInputStream("src/main/resources/input.txt")) {
            Matrix matrix = MatrixIOUtil.readMatrix(fs);

//            Point botPoint = Point.ofZeroIndexationValues(3, 4);
//            Point playerPoint = Point.ofZeroIndexationValues(2, 2);

            Properties properties = new Properties();
            properties.load(new FileReader("src/main/resources/application.properties"));

            Point exitPoint = readPoint(properties, "exit");
            Point botPoint = readPoint(properties, "bot");
            Point playerPoint = readPoint(properties, "player");

            if (!MatrixValidator.isValid(matrix, botPoint, playerPoint, exitPoint)) {
                throw new NoWayFoundException("start and finish ceils should not be obstacles");
            }

            matrix = generateContent(matrix, botPoint, playerPoint, exitPoint);
            MatrixIOUtil.printToScreen(matrix);
            while (true) {
                Direction direction = algorithm.perform(matrix, botPoint, playerPoint);
                matrix = botMakesStep(direction, matrix, botPoint);
                MatrixIOUtil.printToScreen(matrix);
                checkIfEndOfTheGame(playerPoint, botPoint, exitPoint);

                //step of player
                MinimaxTemplate.State s = new MinimaxTemplate.State(matrix, true);
                matrix = MinimaxTemplate.minimaxDecision(s).getState();
//                AlphaBetaPruning.State state = new AlphaBetaPruning.State(matrix, true);
//                matrix = AlphaBetaPruning.minimaxDecision(state).getState();
                playerPoint = matrix.findValue(-3);
                MatrixIOUtil.printToScreen(matrix);
                checkIfEndOfTheGame(playerPoint, botPoint, exitPoint);
                ColorfulPrinter.printlnColorfullyAndReset(Color.ANSI_BLUE,
                        "****************************************************");
            }

        } catch (NoWayFoundException e) {
            System.err.println("ERROR: Way could not be found: " + e.getMessage());
        } catch (LooseException e) {
            ColorfulPrinter.printColorfullyAndReset(Color.ANSI_RED, e.getMessage());
        } catch (WinException e) {
            ColorfulPrinter.printColorfullyAndReset(Color.ANSI_GREEN, e.getMessage());
        } catch (IOException e) {
            System.err.println("ERROR: reading was not successful:(" + e);
        }
    }

    private Point readPoint(Properties properties, String name) {
        int row = Integer.parseInt((String) properties.get(name + ".row"));
        int col = Integer.parseInt((String) properties.get(name + ".col"));

        return Point.ofZeroIndexationValues(row, col);
    }

    private void checkIfEndOfTheGame(Point playerPoint, Point botPoint, Point exitPoint) {
        if (botPoint.equals(playerPoint)) {
            throw new LooseException("You have lost!");
        }
        if (playerPoint.equals(exitPoint)) {
            throw new WinException("You have won!");
        }
    }

    private Matrix botMakesStep(Direction direction, Matrix matrix, Point point) {
        int[][] array = matrix.getArray();
        array[point.row][point.col] = 0;
        switch (direction) {
            case UP -> {
                array[point.row - 1][point.col] = -2;
                point.row--;
            }
            case DOWN -> {
                array[point.row + 1][point.col] = -2;
                point.row++;
            }
            case RIGHT -> {
                array[point.row][point.col + 1] = -2;
                point.col++;
            }
            case LEFT -> {
                array[point.row][point.col - 1] = -2;
                point.col--;
            }
            case NO_MOVE -> array[point.row][point.col] = -2;
        }
        return new Matrix(array);
    }

//    private Matrix playerMakesStep(Direction direction, Matrix matrix, Point point) {
//        int[][] array = matrix.getArray();
//        array[point.row][point.col] = 0;
//        switch (direction) {
//            case UP -> {
//                array[point.row - 1][point.col] = -3;
//                point.row--;
//            }
//            case DOWN -> {
//                array[point.row + 1][point.col] = -3;
//                point.row++;
//            }
//            case RIGHT -> {
//                array[point.row][point.col + 1] = -3;
//                point.col++;
//            }
//            case LEFT -> {
//                array[point.row][point.col - 1] = -3;
//                point.col--;
//            }
//            case NO_MOVE -> array[point.row][point.col] = -3;
//        }
//        return new Matrix(array);
//    }


    private Matrix generateContent(Matrix matrix, Point botPoint, Point playerPoint, Point exitPoint) {
        int[][] array = matrix.getArray();
        array[botPoint.row][botPoint.col] = -2;
        array[playerPoint.row][playerPoint.col] = -3;
        array[exitPoint.row][exitPoint.col] = -4;
        return new Matrix(array);
    }


}
