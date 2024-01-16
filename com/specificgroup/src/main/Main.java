package main;

import main.impl.AdjacentListGraph;
import main.impl.AdjacentMatrixGraph;

public class Main {
    public static void main(String[] args) {
//        Graph<Integer> graph = new AdjacentListGraph<>();
        Graph<Integer> graph = new AdjacentMatrixGraph<>();

        graph.addVertex(1);
        graph.addVertex(2);
        graph.addVertex(3);
        graph.addVertex(4);
        graph.addVertex(5);
        graph.addVertex(6);
        graph.addVertex(7);


        graph.addEdge(1, 3);
        graph.addEdge(3, 2);
        graph.addEdge(3, 4);
        graph.addEdge(1, 4);
        graph.addEdge(2, 4);
        graph.addEdge(1, 5);
        graph.addEdge(5, 6);
        graph.addEdge(2, 6);
        graph.addEdge(2, 7);
        graph.addEdge(7, 6);
        graph.addEdge(7, 4);

        graph.removeEdge(7, 2);
        graph.removeEdge(7, 6);

        graph.removeVertex(7);

        graph.print();

        System.out.println("----------------------------------------------   BFS   ---------------------------------------------- ");

        graph.bfs(3);

        System.out.println("----------------------------------------------   DFS   ---------------------------------------------- ");

        graph.dfs(3);
    }
}
