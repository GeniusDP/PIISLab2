package org.example.models.algorithms;


import org.example.models.algorithms.heuristics.HeuristicCalculator;
import org.example.models.algorithms.heuristics.HeuristicFinder;

public abstract class AbstractAlgorithm implements Algorithm {
    protected final HeuristicCalculator heuristicCalculator;
    protected final HeuristicFinder heuristicFinder;

    public AbstractAlgorithm(HeuristicCalculator heuristicCalculator) {
        if (heuristicCalculator == null) {
            throw new IllegalArgumentException("heuristic should not be null!");
        }
        this.heuristicCalculator = heuristicCalculator;
        this.heuristicFinder = new HeuristicFinder(heuristicCalculator);
    }

    protected AbstractAlgorithm() {
        this.heuristicCalculator = HeuristicCalculator::manhattan;
        this.heuristicFinder = new HeuristicFinder(heuristicCalculator);
    }

}
