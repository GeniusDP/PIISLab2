package org.example.models.minimax.algorithm;

import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public final class MinimaxTemplate {

    private MinimaxTemplate() {
    }

    public static State minimaxDecision(State state) {
        return state.getActions().stream()
                .max(Comparator.comparing(MinimaxTemplate::minValue)).get();
    }

    private static int maxValue(State state) {
        if (state.isTerminal()) {
            return state.getUtility();
        }
        return state.getActions().stream()
                .map(MinimaxTemplate::minValue)
                .max(Comparator.comparing(Double::valueOf)).get();
    }

    private static int minValue(State state) {
        if (state.isTerminal()) {
            return state.getUtility();
        }
        return state.getActions().stream()
                .map(MinimaxTemplate::maxValue)
                .min(Comparator.comparing(Double::valueOf)).get();
    }

    public static class State {
        final int state;
        final boolean firstPlayer;
        final boolean secondPlayer;

        public State(int state, boolean firstPlayer) {
            this.state = state;
            this.firstPlayer = firstPlayer;
            this.secondPlayer = !firstPlayer;
        }

        Collection<State> getActions() {
            List<State> actions = new LinkedList<>();
            if (state > 4) {
                actions.add(new State(state - 5, secondPlayer));
            }
            if (state > 3) {
                actions.add(new State(state - 4, secondPlayer));
            }
            if (state > 2) {
                actions.add(new State(state - 3, secondPlayer));
            }
            return actions;
        }

        boolean isTerminal() {
            return state < 3;
        }

        int getUtility() {
            if (firstPlayer) {
                return -1;
            }
            return 1;
        }
    }
}

class Main {

    public static void main(String[] args){
        System.out.println("Welcome to my minimax algorithm");
        boolean end = false;
        int val = 21;
        boolean first = true;
        while(!end) {
            System.out.println("Current position = "+ val +", Player one: " + first);
            MinimaxTemplate.State s = new MinimaxTemplate.State(val, true);
            MinimaxTemplate.State decision = MinimaxTemplate.minimaxDecision(s);
            val = decision.state;
            if(decision.isTerminal()){
                end = true;
                System.out.println("Current position = "+ val +", Player one won: " + first);
                System.out.println("Game over");
            }
            first =! first;
        }
    }
}


