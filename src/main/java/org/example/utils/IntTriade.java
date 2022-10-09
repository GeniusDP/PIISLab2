package org.example.utils;

public class IntTriade implements Comparable<IntTriade>{
    public int first;
    public int second;
    public int third;

    public IntTriade(int first, int second, int third){
        this.first = first;
        this.second = second;
        this.third = third;
    }

    @Override
    public int compareTo(IntTriade another) {
        return this.first - another.first;
    }

    public static IntTriade of(int first, int second, int third){
        return new IntTriade(first, second, third);
    }
}
