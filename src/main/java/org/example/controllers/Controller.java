package org.example.controllers;

import org.example.controllers.services.MatrixValidator;
import org.example.models.Direction;
import org.example.models.Matrix;
import org.example.models.Point;
import org.example.models.algorithms.Algorithm;
import org.example.models.algorithms.NoWayFoundException;
import org.example.utils.MatrixIOUtil;
import org.example.utils.coloring.Color;
import org.example.utils.coloring.ColorfulPrinter;

import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.util.Scanner;

import static org.example.models.Direction.*;

public class Controller {

    public void start(Algorithm algorithm) {
        try (FileInputStream fs = new FileInputStream("src/main/resources/input.txt")) {
            Matrix matrix = MatrixIOUtil.readMatrix(fs);

            Point botPoint = Point.ofZeroIndexationValues(matrix.getN() - 2, 1);
            Point playerPoint = Point.ofZeroIndexationValues(matrix.getN() - 2, matrix.getM() - 2);

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
            while (true) {
                Direction direction = algorithm.perform(matrix, botPoint, playerPoint);
                matrix = botMakesStep(direction, matrix, botPoint);
//                System.out.println(direction);
                MatrixIOUtil.printToScreen(matrix);
                checkIfEndOfTheGame(playerPoint, botPoint, exitPoint);

                //step of player
                Scanner scanner = new Scanner(System.in);
                String s = scanner.nextLine();
//                System.out.println(s);
//                System.out.println(s.equals("up"));
                Direction playerDirection = switch (s) {
                    case "up" -> UP;
                    case "down" -> DOWN;
                    case "left" -> LEFT;
                    case "right" -> RIGHT;
                    default -> NO_MOVE;
                };
//                System.out.println(playerDirection);
                matrix = playerMakesStep(playerDirection, matrix, playerPoint);
                MatrixIOUtil.printToScreen(matrix);
                checkIfEndOfTheGame(playerPoint, botPoint, exitPoint);
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

    private Matrix playerMakesStep(Direction direction, Matrix matrix, Point point) {
        int[][] array = matrix.getArray();
        array[point.row][point.col] = 0;
        switch (direction) {
            case UP -> {
                array[point.row - 1][point.col] = -3;
                point.row--;
            }
            case DOWN -> {
                array[point.row + 1][point.col] = -3;
                point.row++;
            }
            case RIGHT -> {
                array[point.row][point.col + 1] = -3;
                point.col++;
            }
            case LEFT -> {
                array[point.row][point.col - 1] = -3;
                point.col--;
            }
            case NO_MOVE -> array[point.row][point.col] = -3;
        }
        return new Matrix(array);
    }



    private Matrix generateContent(Matrix matrix, Point botPoint, Point playerPoint, Point exitPoint) {
        int[][] array = matrix.getArray();
        array[botPoint.row][botPoint.col] = -2;
        array[playerPoint.row][playerPoint.col] = -3;
        array[exitPoint.row][exitPoint.col] = -4;
        return new Matrix(array);
    }


}
