package org.example.utils;

public final class Pair<T, E> {
    private final T m_first;
    private final E m_second;

    public Pair(T first, E second) {
        m_first = first;
        m_second = second;
    }

    public T first() {
        return m_first;
    }

    public E second() {
        return m_second;
    }
}