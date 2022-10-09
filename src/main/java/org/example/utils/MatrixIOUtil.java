package org.example.utils;

import org.example.models.Matrix;
import org.example.utils.coloring.Color;
import org.example.utils.coloring.ColorfulPrinter;

import java.io.InputStream;
import java.util.Arrays;
import java.util.Scanner;

public class MatrixIOUtil {

    public static Matrix readMatrix(InputStream from) {
        Scanner scanner = new Scanner(from);
        int rows = 0;
        int cols = 0;

        int[][] array = new int[Matrix.MAX_SIZE][Matrix.MAX_SIZE];

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (rows == 0) {
                for (int i = 0; i < line.length(); i++)
                    if (line.charAt(i) == 'X') {
                        cols++;
                    }
                for (int i = 0; i < cols; i++) {
                    array[0][i] = -1;
                }
            } else {
                int start = -1;
                for (int i = 0; i < line.length(); i++) {
                    if (line.charAt(i) == 'X' && start == -1) {
                        start = i;
                    }
                    if ((i - start) % 2 == 0) {
                        if (line.charAt(i) == 'X') {
                            array[rows][(i - start) / 2] = -1;
                        } else {
                            array[rows][(i - start) / 2] = 0;
                        }
                    }
                    if ((i - start) / 2 >= cols) {
                        break;
                    }
                }
            }
            rows++;
        }

        array = Arrays.copyOf(array, rows);
        for (int i = 0; i < array.length; i++) {
            array[i] = Arrays.copyOf(array[i], cols);
        }

        return new Matrix(array);
    }

    public static void printToScreen(Matrix matrix) {
        int[][] array = matrix.getArray();
        for (int[] row : array) {
            for (int j = 0; j < row.length; j++) {
                if (row[j] == -1) {
                    ColorfulPrinter.printColorfullyAndReset(Color.ANSI_RED, "X  ");
                } else if (row[j] == 0) {
                    System.out.print("   ");
                } else if (row[j] == -4) {
                    ColorfulPrinter.printColorfullyAndReset(Color.ANSI_RED, "#  ");
                } else if (row[j] == -2) {
                    System.out.print(" * ");
                } else if (row[j] == -3) {
                    System.out.print(" @ ");
                }
                else {
                    String format = String.format("%2d ", row[j]);
                    ColorfulPrinter.printColorfullyAndReset(Color.ANSI_GREEN, format);
                }
            }
            System.out.println();
        }
    }


}