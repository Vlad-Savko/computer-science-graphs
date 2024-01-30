package main.impl;

import main.StackAndQueueAdapter;

import java.util.LinkedList;
import java.util.Queue;

public class QueueAdapter<T> implements StackAndQueueAdapter<T> {
    private final Queue<T> queue;

    public QueueAdapter() {
        queue = new LinkedList<>();
    }

    @Override
    public T getElement() {
        return queue.poll();
    }

    @Override
    public void addElement(T element) {
        queue.add(element);
    }

    @Override
    public boolean contains(T t) {
        return queue.contains(t);
    }

    @Override
    public boolean isEmpty() {
        return queue.isEmpty();
    }
}
