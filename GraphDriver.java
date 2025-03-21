import java.util.ArrayList;

public class GraphDriver
{
    /**
     * Helper class for the testing method.
     * 
     * I will be using it to store graphs with a testing start vertex.
     */
    private static class Pair <T1, T2>
    {
        final T1 one;
        final T2 two;

        Pair(T1 one, T2 two)
        {
            this.one = one;
            this.two = two;
        }
    }

    /**
     * Testing method helper, run this for all the supplied graphs.
     * 
     * @param graphs
     */
    private static void testGraphs(ArrayList<Pair<Graph, String>> graphs)
    {
        System.out.println("DFS Algorithms");
        // DFS Testing
        for (Pair<Graph, String> p : graphs)
        {
            Graph g = p.one;
            String vertex = p.two;

            g.runDFS(true);
            System.out.println("DFS Visited: " + g.getLastDFSOrder());
            System.out.println("Dead ends: " + g.getLastDFSDeadEndOrder());
            System.out.println(g);

            g.runDFS(vertex, true);
            System.out.println("DFS Visited from " + vertex + ": " + g.getLastDFSOrder());
            System.out.println("Dead ends: " + g.getLastDFSDeadEndOrder());
            System.out.println(g);

            g.runBFS(true);
            System.out.println("BFS Visited: " + g.getLastBFSOrder());
            System.out.println(g);

            g.runBFS(vertex, true);
            System.out.println("BFS Visited from " + vertex + ": " + g.getLastBFSOrder());
            System.out.println(g);
        }
    }

    /**
     * Testing method
     * 
     * @param args
     */
    public static void main(String[] args)
    {
        // Connected undirected
        Graph g1 = new Graph(new String[] {"b0", "b1", "b2", "b3"}, false);
        g1.addEdge("b0", "b1");
        g1.addEdge("b1", "b2");
        g1.addEdge("b2", "b3");

        // Connected undirected
        Graph g2 = new Graph(new String[] {"A", "B", "C", "D", "E", "F", "H"}, false);
        g2.addEdge("A", "B");
        g2.addEdge("A", "C");
        g2.addEdge("A", "D");
        g2.addEdge("C", "E");
        g2.addEdge("E", "F");
        g2.addEdge("F", "D");
        g2.addEdge("F", "H");

        // Connected directed
        Graph g3 = new Graph(new String[] {"A", "B", "C", "D", "E", "F", "H"}, true);
        g3.addEdge("A", "B");
        g3.addEdge("A", "C");
        g3.addEdge("A", "D");
        g3.addEdge("C", "E");
        g3.addEdge("E", "F");
        g3.addEdge("F", "D");
        g3.addEdge("F", "H");

        // Disconnected undirected
        Graph g4 = new Graph(new String[] {"A", "B", "C", "D", "E", "F", "G"}, false);
        // Connected component 1
        g4.addEdge("A", "B");
        g4.addEdge("A", "C");
        g4.addEdge("C", "D");
        g4.addEdge("D", "A");
        g4.addEdge("B", "D");
        // Connected component 2
        g4.addEdge("E", "F");
        g4.addEdge("E", "G");

        // Disconnected directed
        Graph g5 = new Graph(new String[] {"A", "B", "C", "D", "E", "F", "G", "H"}, true);
        // Connected component 1
        g5.addEdge("A", "B");
        g5.addEdge("A", "C");
        g5.addEdge("A", "H");
        g5.addEdge("B", "C");
        g5.addEdge("C", "A");
        g5.addEdge("H", "E");
        g5.addEdge("H", "F");
        g5.addEdge("E", "A");
        // Connected component 2
        g5.addEdge("D", "G");

        // Lab example
        Graph g6 = new Graph(new String[] {"1", "2", "3", "4", "5", "6"}, true);
        g6.addEdge("1", "4");
        g6.addEdge("2", "1");
        g6.addEdge("2", "3");
        g6.addEdge("4", "3");
        g6.addEdge("2", "4");
        g6.addEdge("5", "1");
        g6.addEdge("5", "2");
        g6.addEdge("5", "6");
        g6.addEdge("6", "3");

        // Super duper test
        Graph g7 = new Graph(new String[] {"A"}, false);

        // Super duper duper test
        Graph g8 = new Graph(new String[] {}, false);

        ArrayList<Pair<Graph, String>> graphs = new ArrayList<>();
        graphs.add(new Pair<>(g1, "b3"));
        graphs.add(new Pair<>(g2, "D"));
        graphs.add(new Pair<>(g3, "D"));
        graphs.add(new Pair<>(g4, "C"));
        graphs.add(new Pair<>(g5, "D"));
        graphs.add(new Pair<>(g6, "3"));
        graphs.add(new Pair<>(g7, "A"));
        graphs.add(new Pair<>(g8, ""));

        testGraphs(graphs);
    }
}
