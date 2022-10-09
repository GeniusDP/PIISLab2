package org.example.controllers;

import com.kpi.fics.piis.zaranik.models.Matrix;
import com.kpi.fics.piis.zaranik.models.Point;
import com.kpi.fics.piis.zaranik.models.algorithms.Algorithm;
import com.kpi.fics.piis.zaranik.models.algorithms.LiAlgorithmPerformer;
import com.kpi.fics.piis.zaranik.models.algorithms.NoWayFoundException;
import com.kpi.fics.piis.zaranik.models.algorithms.heuristics.HeuristicCalculator;
import com.kpi.fics.piis.zaranik.utils.MatrixIOUtil;

import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class Controller {

    public void start(Algorithm algorithm){
        try (FileInputStream fs = new FileInputStream("src/main/resources/input.txt")) {
            Matrix matrix = MatrixIOUtil.readMatrix(fs);
            Properties properties = new Properties();
            properties.load(new FileReader("src/main/resources/application.properties"));
            int stRow = Integer.parseInt((String)properties.get("st.row"));
            int stCol = Integer.parseInt((String)properties.get("st.col"));
            int finRow = Integer.parseInt((String)properties.get("fin.row"));
            int finCol = Integer.parseInt((String)properties.get("fin.col"));
            Point startPoint = new Point(stRow, stCol);
            Point finishPoint = new Point(finRow, finCol);
            if (matrix.ceilIsObstacle(startPoint) || matrix.ceilIsObstacle(finishPoint)){
                throw new NoWayFoundException("start and finish ceils should not be obstacles");
            }

            Matrix perform = algorithm.perform(matrix, startPoint, finishPoint);
            MatrixIOUtil.printToScreen(perform);
        } catch (NoWayFoundException e) {
            System.out.println("Way was not found: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("reading was not successful:(" + e);
        }
    }

}
