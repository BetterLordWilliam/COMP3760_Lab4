
import java.util.ArrayList;
import java.util.Arrays;

public class Graph
{
    private static int VERTEX_BAD = -1;

    private boolean isDirected;
    private int cardVertices;
    private String[] vertexLabels;
    private int[][] edges;
    private ArrayList<String> dfsvisited;
    private ArrayList<String> dfsdeadEnds;
    private ArrayList<String> bfsvisited;

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
        for (int[] row : edges)
        {
            sb.append(Arrays.toString(row));
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
            if (dfsvisited.contains(vertexLabels[i]))
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


        ArrayList<Graph> graphs = new ArrayList<>();
        graphs.add(g1);


        // DFS Testing
        for (Graph g : graphs)
        {
            g.runDFS(false);
            System.out.println("Visited: " + g.getLastDFSOrder());
            System.out.println("Dead ends: " + g.getLastDFSDeadEndOrder());
            System.out.println(g);
        }

        // BFS Testing

    }
}