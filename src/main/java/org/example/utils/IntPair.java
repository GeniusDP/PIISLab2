package com.kpi.fics.piis.zaranik.utils;

public class IntPair implements Comparable<IntPair>{
    public int first;
    public int second;

    public IntPair(int first, int second){
        this.first = first;
        this.second = second;
    }

    public static IntPair convertSumFormToIndexes(int sumForm, int n) {
        return new IntPair(sumForm / n, sumForm % n);
    }

    @Override
    public int compareTo(IntPair another) {
        return this.first - another.first;
    }

    public static IntPair of(int first, int second){
        return new IntPair(first, second);
    }
}
