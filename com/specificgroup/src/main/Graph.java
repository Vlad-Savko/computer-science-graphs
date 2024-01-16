package main;

public interface Graph<T> {
    void bfs(T sourceVertex);

    void dfs(T sourceVertex);

    void addEdge(T source, T destination);

    void addVertex(T value);

    void removeEdge(T source, T destination);

    void removeVertex(T value);

    boolean areAdjacent(T vertex1, T vertex2);

    boolean hasVertex(T vertex);

    void print();
}
