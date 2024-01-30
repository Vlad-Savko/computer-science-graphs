package main.impl;

import main.Graph;
import main.SearchType;
import main.StackAndQueueAdapter;

import java.util.*;

public class AdjacentListGraph<T> implements Graph<T> {
    private final Map<T, List<T>> adjacencyList;

    public AdjacentListGraph() {
        this.adjacencyList = new HashMap<>();
    }

    @Override
    public void bfs(T sourceVertex) {
        xfs(sourceVertex, SearchType.BREADTH);
    }

    @Override
    public void dfs(T sourceVertex) {
        xfs(sourceVertex, SearchType.DEPTH);
    }

    @Override
    public void addEdge(T source, T destination) {
        if (!adjacencyList.containsKey(source)) {
            this.addVertex(source);
        }
        if (!adjacencyList.containsKey(destination)) {
            this.addVertex(destination);
        }
        insertEdge(source, destination);
        insertEdge(destination, source);
    }

    @Override
    public void addVertex(T value) {
        this.adjacencyList.put(value, null);
    }

    @Override
    public void removeEdge(T source, T destination) {
        if (adjacencyList.containsKey(source) && adjacencyList.containsKey(destination)) {
            adjacencyList.get(source).remove(destination);
            adjacencyList.get(destination).remove(source);
        }
    }

    @Override
    public void removeVertex(T value) {
        adjacencyList.remove(value);
        for (List<T> values : adjacencyList.values()) {
            if (values != null) {
                values.remove(value);
            }
        }
    }

    @Override
    public boolean areAdjacent(T vertex1, T vertex2) {
        return adjacencyList.containsKey(vertex1)
                &&
                adjacencyList.containsKey(vertex2)
                &&
                adjacencyList.get(vertex1).contains(vertex2)
                &&
                adjacencyList.get(vertex2).contains(vertex1);
    }

    @Override
    public boolean hasVertex(T vertex) {
        return adjacencyList.containsKey(vertex);
    }

    public Set<T> getVertexes() {
        return new HashSet<>(adjacencyList.keySet());
    }

    public Map<T, List<T>> getVertexMap() {
        return new HashMap<>(adjacencyList);
    }

    @Override
    public void print() {
        System.out.printf("Adjacency list graph(%d) = {%n", hashCode());
        for (Map.Entry<T, List<T>> entry : adjacencyList.entrySet()) {
            StringJoiner stringJoiner = new StringJoiner(
                    ", ",
                    String.format("%s[", entry.getKey()),
                    "]");
            for (T value : entry.getValue()) {
                stringJoiner.add(value.toString());
            }
            System.out.println(stringJoiner);
        }
        System.out.println("}");
    }

    private void insertEdge(T source, T destination) {
        adjacencyList.compute(source, (k, v) -> {
            if (v == null) {
                v = new LinkedList<>();
            }
            v.add(destination);
            return v;
        });
    }

    private void lookForSourceVertex(List<T> adjacents, List<T> visited, T sourceVertex, T currVertex, StringBuilder sb) {
        if (adjacents.contains(sourceVertex)) {
            sb.append(String.format("%s -> %s", sourceVertex, currVertex));
        } else {
            for (T visitedVertex : visited) {
                if (adjacents.contains(visitedVertex)) {
                    lookForSourceVertex(adjacencyList.get(visitedVertex), visited, sourceVertex, visitedVertex, sb);
                    sb.append(String.format(" -> %s", currVertex));
                    break;
                }
            }
        }
    }

    private void xfs(final T sourceVertex, final SearchType type) {
        List<T> visited = new LinkedList<>();
        StackAndQueueAdapter<T> collection = switch (type) {
            case DEPTH -> new StackAdapter<>();
            case BREADTH -> new QueueAdapter<>();
        };
        collection.addElement(sourceVertex);
        StringBuilder sb = new StringBuilder();

        while (!collection.isEmpty()) {
            T currVertex = collection.getElement();
            if (!currVertex.equals(sourceVertex)) {
                List<T> adjacents = adjacencyList.get(currVertex);

                lookForSourceVertex(adjacents, visited, sourceVertex, currVertex, sb);
            }
            if (!visited.contains(currVertex)) {
                visited.add(currVertex);
            }

            for (T currVertexAdjacent : adjacencyList.get(currVertex)) {
                if (!visited.contains(currVertexAdjacent) && !collection.contains(currVertexAdjacent)) {
                    collection.addElement(currVertexAdjacent);
                }
            }
            if (!currVertex.equals(sourceVertex)) {
                System.out.println(sb);
                sb = new StringBuilder();
            }
        }
    }
}