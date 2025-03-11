import java.util.ArrayList;
import java.util.Arrays;

public class Graph
{
    private static int VERTEX_BAD = -1;

    private final boolean isDirected;
    private final int cardVertices;
    private final String[] vertexLabels;
    private final int[][] edges;

    private ArrayList<String> dfsvisited;
    private ArrayList<String> dfsdeadEnds;
    private ArrayList<String> bfsvisited;

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
     * Graph constructor.
     * 
     * @param vertexLabels
     * @param isDirected
     */
    public Graph(String[] vertexLabels, boolean isDirected)
    {
        // Save references
        this.vertexLabels = vertexLabels;
        this.isDirected = isDirected;

        // Initialize other stuff
        cardVertices = vertexLabels.length;
        edges = new int[cardVertices][cardVertices];
    }

    /**
     * Determines if the graph contains a vertex from a supplied label, returns index or -1 if not.
     * 
     * @param vertex
     * @return
     */
    private int containsVertex(String vertex)
    {
        int i = 0;
        for (; i < cardVertices; i++)
        {
            if (vertexLabels[i].equals(vertex))
                return i;
        }
        return -1;
    }

    /**
     * Indicates whether the graph is a boolean.
     * 
     * @return
     */
    public boolean isDirected()
    {
        return isDirected;
    }

    /**
     * Returns the number of vertices.
     * 
     * @return
     */
    public int size()
    {
        return cardVertices;
    }

    /**
     * Returns vertex label of V
     * 
     * @param v
     * @return
     */
    public String getLabel(int v)
    {
        return null;
    }

    /**
     * Adds an edge to the graph using existing vertex labels.
     * 
     * @param vertex1
     * @param vertex2
     */
    public void addEdge(String vertex1, String vertex2)
    {
        int v1 = containsVertex(vertex1);
        int v2 = containsVertex(vertex2);

        // Bad vertex labels supplied
        if (v1 == VERTEX_BAD || v1 == VERTEX_BAD)
        {
            System.out.println(String.format("Bad vertex label(s) given: %s, %s", vertex1, vertex2));
            System.out.println(String.format("Expected: %s", Arrays.toString(vertexLabels)));
            return;
        }

        // We good, add the edge
        if (isDirected)
            edges[v1][v2] = 1;
        if (!isDirected)
        {
            edges[v1][v2] = 1;
            edges[v2][v1] = 1;
        }
    }
    
    /**
     * String representation of the adjacency matrix.
     */
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("  ");
        sb.append(String.join("  ", vertexLabels));
        sb.append("\n");
        for (int i = 0; i < edges.length; i++)
        {
            sb.append(vertexLabels[i]);
            sb.append(Arrays.toString(edges[i]));
            sb.append("\n");
        }
        return sb.toString();
    }

    /**
     * Helper function for DFS, use call stack instead of creating a stack.
     * 
     * @param v
     * @param quiet
     */
    private void dfsHelper(int v, boolean quiet)
    {  
        dfsvisited.add(vertexLabels[v]);
        if (!quiet)
            System.out.println(String.format("Visisted: %s", vertexLabels[v]));

        for (int i = 0; i < edges[v].length; i++)
        {
            if (dfsvisited.contains(vertexLabels[i]) || edges[v][i] == 0)
                continue;
            dfsHelper(i, quiet);
        }
        dfsdeadEnds.add(vertexLabels[v]);
    }
    
    /**
     * 
     * 
     * @param v
     * @param quiet
     */
    private void bfsHelper(int v, boolean quiet)
    {

    }

    /**
     * Perform DFS.
     * 
     * @param quiet
     */
    public void runDFS(boolean quiet)
    {
        dfsvisited = new ArrayList<>();
        dfsdeadEnds = new ArrayList<>();
        for (int i = 0; i < edges.length; i++)
        {
            if (dfsvisited.contains(vertexLabels[i]))
                continue;
            dfsHelper(i, quiet);
        }
    }

    /**
     * Perform DFS from a starting vertex.
     * 
     * @param v
     * @param quiet
     */
    public void runDFS(String v, boolean quiet)
    {
        int vi = containsVertex(v);
        if (vi == VERTEX_BAD)
            return;

        dfsvisited = new ArrayList<>();
        dfsdeadEnds = new ArrayList<>();

        if (dfsvisited.contains(vertexLabels[vi]))
            return;
        dfsHelper(vi, quiet);
    }

    /**
     * Perform BFS.
     * 
     * @param quiet
     */
    public void runBFS(boolean quiet)
    {

    }

    /**
     * Perform BFS from a starting vertex.
     * 
     * @param v
     * @param quiet
     */
    public void runBFS(String v, boolean quiet)
    {
        
    }

    /**
     * Returns string of last DFS order.
     * 
     * @return
     */
    public String getLastDFSOrder()
    {
        return dfsvisited.toString();
    }

    /**
     * Returns string of last DFS dead ends order.
     * 
     * @return
     */
    public String getLastDFSDeadEndOrder()
    {
        return dfsdeadEnds.toString();
    }

    /**
     * Returns string of last BFS order.
     * 
     * @return
     */
    public String getLastBFSOrder()
    {
        return bfsvisited.toString();
    }

    /**
     * Testing method
     * 
     * @param args
     */
    public static void main(String[] args)
    {
        Graph g1 = new Graph(new String[] {"b0", "b1", "b2", "b3"}, false);
        g1.addEdge("b0", "b1");
        g1.addEdge("b1", "b2");
        g1.addEdge("b2", "b3");

        Graph g2 = new Graph(new String[] {"A", "B", "C", "D", "E", "F", "H"}, false);
        g2.addEdge("A", "B");
        g2.addEdge("A", "C");
        g2.addEdge("A", "D");
        g2.addEdge("C", "E");
        g2.addEdge("E", "F");
        g2.addEdge("F", "D");
        g2.addEdge("F", "H");

        Graph g3 = new Graph(new String[] {"A", "B", "C", "D", "E", "F", "H"}, true);
        g3.addEdge("A", "B");
        g3.addEdge("A", "C");
        g3.addEdge("A", "D");
        g3.addEdge("C", "E");
        g3.addEdge("E", "F");
        g3.addEdge("F", "D");
        g3.addEdge("F", "H");

        ArrayList<Pair<Graph, String>> graphs = new ArrayList<>();
        graphs.add(new Pair<>(g1, "b3"));
        graphs.add(new Pair<>(g2, "D"));
        graphs.add(new Pair<>(g3, "D"));

        // DFS Testing
        for (Pair<Graph, String> p : graphs)
        {
            Graph g = p.one;
            String vertex = p.two;

            g.runDFS(false);
            System.out.println("Visited: " + g.getLastDFSOrder());
            System.out.println("Dead ends: " + g.getLastDFSDeadEndOrder());
            System.out.println(g);

            g.runDFS(vertex, false);
            System.out.println("Visited: " + g.getLastDFSOrder());
            System.out.println("Dead ends: " + g.getLastDFSDeadEndOrder());
            System.out.println(g);
        }

        // BFS Testing

    }
}