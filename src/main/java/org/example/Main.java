package org.example;

import org.example.controllers.Controller;
import org.example.models.algorithms.AStarPerformer;
import org.example.models.algorithms.Algorithm;
import org.example.models.algorithms.heuristics.HeuristicCalculator;

public class Main {
    public static void main(String[] args) {
        Controller controller = new Controller();
        Algorithm algorithm = new AStarPerformer(HeuristicCalculator::euclid);
        controller.start(algorithm);
    }
}