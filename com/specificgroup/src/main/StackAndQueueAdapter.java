package main;

public interface StackAndQueueAdapter<T> {
    T getElement();

    void addElement(final T element);

    boolean contains(T t);

    boolean isEmpty();
}
