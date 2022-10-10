package org.example;

import org.example.controllers.Controller;
import org.example.models.algorithms.astar.AStarPerformer;
import org.example.models.algorithms.astar.Algorithm;
import org.example.models.algorithms.astar.heuristics.HeuristicCalculator;

public class Main {
    public static void main(String[] args) {
        Controller controller = new Controller();
        Algorithm algorithm = new AStarPerformer(HeuristicCalculator::euclid);
        controller.start(algorithm);
    }
}