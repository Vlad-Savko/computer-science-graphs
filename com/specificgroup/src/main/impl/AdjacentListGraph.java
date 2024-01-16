package main.impl;

import main.Graph;

import java.util.*;

public class AdjacentListGraph<T> implements Graph<T> {
    private final Map<T, List<T>> adjacencyList;

    public AdjacentListGraph() {
        this.adjacencyList = new HashMap<>();
    }

    @Override
    public void bfs(T sourceVertex) {
        List<T> visited = new LinkedList<>();
        Queue<T> queue = new LinkedList<>(List.of(sourceVertex));
        StringBuilder sb = new StringBuilder();

        while (!queue.isEmpty()) {
            T currVertex = queue.poll();
            if (!currVertex.equals(sourceVertex)) {
                List<T> adjacents = adjacencyList.get(currVertex);

                lookForSourceVertex(adjacents, visited, sourceVertex, currVertex, sb);
            }
            if (!visited.contains(currVertex)) {
                visited.add(currVertex);
            }

            for (T currVertexAdjacent : adjacencyList.get(currVertex)) {
                if (!visited.contains(currVertexAdjacent) && !queue.contains(currVertexAdjacent)) {
                    queue.add(currVertexAdjacent);
                }
            }
            if (!currVertex.equals(sourceVertex)) {
                System.out.println(sb);
                sb = new StringBuilder();
            }
        }
    }

    @Override
    public void dfs(T sourceVertex) {
        List<T> visited = new LinkedList<>();
        Stack<T> stack = new Stack<>();
        StringBuilder sb = new StringBuilder();

        stack.push(sourceVertex);

        while (!stack.isEmpty()) {
            T currVertex = stack.pop();
            if (!currVertex.equals(sourceVertex)) {
                List<T> adjacents = adjacencyList.get(currVertex);

                lookForSourceVertex(adjacents, visited, sourceVertex, currVertex, sb);
            }
            if (!visited.contains(currVertex)) {
                visited.add(currVertex);
            }

            for (T currVertexAdjacent : adjacencyList.get(currVertex)) {
                if (!visited.contains(currVertexAdjacent) && !stack.contains(currVertexAdjacent)) {
                    stack.push(currVertexAdjacent);
                }
            }
            if (!currVertex.equals(sourceVertex)) {
                System.out.println(sb);
                sb = new StringBuilder();
            }
        }
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
        boolean result = false;
        if (adjacencyList.containsKey(vertex1) && adjacencyList.containsKey(vertex2)) {
            if (adjacencyList.get(vertex1).contains(vertex2) && adjacencyList.get(vertex2).contains(vertex1)) {
                result = true;
            }
        }
        return result;
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
}
