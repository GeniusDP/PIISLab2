package org.example.models.minimax.template;

import lombok.Getter;
import org.example.models.Direction;
import org.example.models.Matrix;

import java.util.*;

public final class MinimaxTemplate {
    private static int cnt = 0;
    private static final int MAX_DEPTH = 12;

    private MinimaxTemplate() {
    }

    public static State minimaxDecision(State state) {
        cnt = 0;
        return state.getActions().stream()
                .max(Comparator.comparing(MinimaxTemplate::minValue)).get();
    }

    private static int maxValue(State state) {
        cnt++;
        if (state.isTerminal()) {
            return state.getMetrics();
        }
        return state.getActions().stream()
                .map(MinimaxTemplate::minValue)
                .max(Comparator.comparing(Integer::valueOf)).get();
    }

    private static int minValue(State state) {
        cnt++;
        if (state.isTerminal()) {
            return state.getMetrics();
        }
        return state.getActions().stream()
                .map(MinimaxTemplate::maxValue)
                .min(Comparator.comparing(Integer::valueOf)).get();
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


