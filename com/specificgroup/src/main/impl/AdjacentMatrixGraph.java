package main.impl;

import main.Graph;
import main.SearchType;
import main.StackAndQueueAdapter;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.StringJoiner;

public class AdjacentMatrixGraph<T> implements Graph<T> {
    private List<T> values;
    private boolean[][] adjacentMatrix;
    private int size;

    public AdjacentMatrixGraph() {
        this.size = 0;
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
        int sourceIndex = values.indexOf(source);
        int destinationIndex = values.indexOf(destination);

        if (sourceIndex > -1 && destinationIndex > -1) {
            this.adjacentMatrix[sourceIndex][destinationIndex] = true;
            this.adjacentMatrix[destinationIndex][sourceIndex] = true;
        } else {
            addVertex(source);
            addVertex(destination);

            sourceIndex = values.indexOf(source);
            destinationIndex = values.indexOf(destination);

            this.adjacentMatrix[sourceIndex][destinationIndex] = true;
            this.adjacentMatrix[destinationIndex][sourceIndex] = true;
        }
    }

    @Override
    public void addVertex(T value) {
        reallocateMemory();

        values.add(value);
        this.adjacentMatrix[this.size - 1][this.size - 1] = true;
    }

    private void reallocateMemory() {
        if (values == null) {
            values = new LinkedList<>();
        }
        if (this.adjacentMatrix == null) {
            this.adjacentMatrix = new boolean[this.size + 1][this.size + 1];
        }

        this.adjacentMatrix = Arrays.copyOf(this.adjacentMatrix, this.size + 1);
        this.adjacentMatrix[this.size] = new boolean[this.size + 1];
        for (int i = 0; i < adjacentMatrix.length; i++) {
            this.adjacentMatrix[i] = Arrays.copyOf(this.adjacentMatrix[i], this.size + 1);
        }
        this.size++;
    }

    @Override
    public void removeEdge(T source, T destination) {
        int sourceIndex = values.indexOf(source);
        int destinationIndex = values.indexOf(destination);

        if (sourceIndex > -1 && destinationIndex > -1) {
            this.adjacentMatrix[sourceIndex][destinationIndex] = false;
            this.adjacentMatrix[destinationIndex][sourceIndex] = false;
        }
    }

    @Override
    public void removeVertex(T value) {
        int valueIndex = values.indexOf(value);

        if (valueIndex > -1) {
            System.arraycopy(adjacentMatrix, valueIndex + 1, adjacentMatrix, valueIndex, adjacentMatrix.length - 1 - valueIndex);
            for (boolean[] arr : adjacentMatrix) {
                System.arraycopy(arr, valueIndex + 1, arr, valueIndex, arr.length - 1 - valueIndex);
            }
        }

        values.remove(valueIndex);
        this.size--;
    }

    @Override
    public boolean areAdjacent(T vertex1, T vertex2) {
        int sourceIndex = values.indexOf(vertex1);
        int destinationIndex = values.indexOf(vertex2);

        return this.adjacentMatrix[sourceIndex][destinationIndex];
    }

    @Override
    public boolean hasVertex(T vertex) {
        return values.contains(vertex);
    }

    public List<T> getVertexes() {
        return new LinkedList<>(values);
    }

    @Override
    public void print() {
        System.out.printf("Adjacency matrix graph(%d) = {%n", hashCode());
        for (T value : values) {
            int index = values.indexOf(value);
            StringJoiner stringJoiner = new StringJoiner(
                    ", ",
                    String.format("%s[", value),
                    "]");
            for (int i = 0; i < this.size; i++) {
                if (this.adjacentMatrix[index][i]) {
                    if (index != i) {
                        stringJoiner.add(values.get(i).toString());
                    }
                }
            }
            System.out.println(stringJoiner);
        }
        System.out.println("}");
    }

    private void lookForSourceVertex(List<T> adjacents, List<T> visited, T sourceVertex, T currVertex, StringBuilder sb) {
        if (adjacents.contains(sourceVertex)) {
            sb.append(String.format("%s -> %s", sourceVertex, currVertex));
        } else {
            for (T visitedVertex : visited) {
                if (adjacents.contains(visitedVertex)) {
                    lookForSourceVertex(findAdjacents(visitedVertex), visited, sourceVertex, visitedVertex, sb);
                    sb.append(String.format(" -> %s", currVertex));
                    break;
                }
            }
        }
    }

    private List<T> findAdjacents(T vertex) {
        List<T> adjacents = new LinkedList<>();

        int index = values.indexOf(vertex);
        for (int i = 0; i < this.size; i++) {
            if (this.adjacentMatrix[index][i]) {
                if (index != i) {
                    adjacents.add(values.get(i));
                }
            }
        }
        return adjacents;
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
                List<T> adjacents = findAdjacents(currVertex);

                lookForSourceVertex(adjacents, visited, sourceVertex, currVertex, sb);
            }
            if (!visited.contains(currVertex)) {
                visited.add(currVertex);
            }

            for (T currVertexAdjacent : findAdjacents(currVertex)) {
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