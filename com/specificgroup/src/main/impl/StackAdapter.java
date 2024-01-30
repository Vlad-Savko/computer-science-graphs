package main.impl;

import main.StackAndQueueAdapter;

import java.util.Stack;

public class StackAdapter<T> implements StackAndQueueAdapter<T> {
    private final Stack<T> stack;

    public StackAdapter() {
        stack = new Stack<>();
    }

    @Override
    public T getElement() {
        return stack.pop();
    }

    @Override
    public void addElement(final T element) {
        stack.push(element);
    }

    @Override
    public boolean contains(T t) {
        return stack.contains(t);
    }

    @Override
    public boolean isEmpty() {
        return stack.isEmpty();
    }
}
