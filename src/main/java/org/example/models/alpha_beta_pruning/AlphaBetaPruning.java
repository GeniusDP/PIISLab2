package org.example.models.alpha_beta_pruning;

import lombok.Getter;
import org.example.models.Direction;
import org.example.models.Matrix;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

import static org.example.models.algorithms.astar.Algorithm.INF;

public class AlphaBetaPruning {

    private static int cnt = 0;
    private static final int MAX_DEPTH = 12;

    private AlphaBetaPruning() {
    }

    public static State minimaxDecision(State state) {
        cnt = 0;
        return state.getActions().stream()
                .max((state1, state2) -> {
                    int v1 = minValue(state1, -INF, INF);
                    int v2 = minValue(state2, -INF, INF);
                    return Integer.compare(v1, v2);
                }).get();
        //Comparator.comparing(AlphaBetaPruning::minValue)
    }

    private static int maxValue(State state, int alpha, int beta) {
        cnt++;
        if (cnt > MAX_DEPTH || state.isTerminal()) {
            return state.getMetrics();
        }
        int maxEva = -INF;
        for (State st: state.getActions()){
            int eva = minValue(st, alpha, beta);
            maxEva = Math.max(maxEva, eva);
            alpha = Math.max(alpha, maxEva);
            if(beta <= alpha){
                break;
            }
        }
        return maxEva;
    }

    private static int minValue(State state, int alpha, int beta) {
        cnt++;
        if (cnt > MAX_DEPTH || state.isTerminal()) {
            return state.getMetrics();
        }
        int minEva = INF;
        for (State st: state.getActions()){
            int eva = maxValue(st, alpha, beta);
            minEva = Math.min(minEva, eva);
            alpha = Math.min(alpha, minEva);
            if(beta <= alpha){
                break;
            }
        }
        return minEva;
    }

    @Getter
    public static class State {
        final Matrix state;
        final boolean firstPlayer;
        final boolean secondPlayer;

        public State(Matrix state, boolean firstPlayer) {
            this.state = state;
            this.firstPlayer = firstPlayer;
            this.secondPlayer = !firstPlayer;
        }

        Collection<State> getActions() {
            List<State> actions = new ArrayList<>();
            if (state.playerCanMove(Direction.UP)) {
                Matrix moved = state.playerMove(Direction.UP);
                actions.add(new State(moved, secondPlayer));
            }
            if (state.playerCanMove(Direction.DOWN)) {
                Matrix moved = state.playerMove(Direction.DOWN);
                actions.add(new State(moved, secondPlayer));
            }
            if (state.playerCanMove(Direction.LEFT)) {
                Matrix moved = state.playerMove(Direction.LEFT);
                actions.add(new State(moved, secondPlayer));
            }
            if (state.playerCanMove(Direction.RIGHT)) {
                Matrix moved = state.playerMove(Direction.RIGHT);
                actions.add(new State(moved, secondPlayer));
            }
            return actions;
        }

        boolean isTerminal() {
            return cnt > MAX_DEPTH || state.isFinish();
        }

        int getMetrics() {
            return state.getMetrics();
        }
    }

}
