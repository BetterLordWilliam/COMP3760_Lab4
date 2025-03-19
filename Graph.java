import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

public class Graph
{
    private static final int VERTEX_BAD = -1;

    private final boolean isDirected;
    private final int cardVertices;
    private final String[] vertexLabels;
    private final int[][] edges;

    private ArrayList<String> dfsvisited;
    private ArrayList<String> dfsdeadEnds;
    private ArrayList<String> bfsvisited;
    private LinkedList<Integer> lineup;

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
     * Prints visited line to the console.
     * 
     * @param vertex
     */
    private void printVisited(int vertex)
    {
        System.out.println(String.format("Visisted: %s", vertexLabels[vertex]));
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
        return vertexLabels[v];
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
        else
        {
            edges[v1][v2] = 1;
            edges[v2][v1] = 1;
        }
    }

    /**
     * String representation of the adjacency matrix.
     */
    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        // sb.append("  ");
        // sb.append(String.join("  ", vertexLabels));
        // sb.append("\n");
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
        if (!quiet) printVisited(v);

        for (int i = 0; i < edges[v].length; i++)
        {
            if (dfsvisited.contains(vertexLabels[i]) || edges[v][i] == 0)
                continue;
            dfsHelper(i, quiet);
        }
        dfsdeadEnds.add(getLabel(v));
    }

    /**
     * Nice public interface
     * 
     * @param quiet
     */
    public void runDFS(boolean quiet)
    {
        // Just use the first vertex label
        dfsvisited = new ArrayList<>();
        dfsdeadEnds = new ArrayList<>();

        for (int i = 0; i < edges.length; i++)
        {
            if (dfsvisited.contains(getLabel(i)))
                continue;
            if (dfsvisited.size() == cardVertices)
                break;
            dfsHelper(i, quiet);

            if (i == edges.length - 1) i = 0;
        }
    }

    /**
     * Nice public interface
     * 
     * @param quiet
     */
    public void runDFS(String vertex, boolean quiet)
    {
        // Get the vertex for the supplied string
        int v = containsVertex(vertex);
        if (v == VERTEX_BAD) return;
        dfsvisited = new ArrayList<>();
        dfsdeadEnds = new ArrayList<>();

        for (int i = v; i < edges.length; i++)
        {
            if (dfsvisited.contains(getLabel(i)))
                continue;
            if (dfsvisited.size() == cardVertices)
                break;
            dfsHelper(i, quiet);

            if (i == edges.length - 1) i = 0;
        }
    }

    /**
     * Returns string of last DFS order.
     * 
     * @return
     */
    public String getLastDFSOrder()
    {
        if (dfsvisited == null)
            return "DFS not run yet";
        return dfsvisited.toString();
    }

    /**
     * Returns string of last DFS dead ends order.
     * 
     * @return
     */
    public String getLastDFSDeadEndOrder()
    {
        if (dfsdeadEnds == null)
            return "DFS not run yet";
        return dfsdeadEnds.toString();
    }

    /**
     * 
     * 
     * @param v
     * @param quiet
     */
    private void bfsHelper(int v, boolean quiet)
    {
        // LinkedList is an implementation of a queue structure in java
        lineup = new LinkedList<>();
        bfsvisited.add(getLabel(v));
        if (!quiet) printVisited(v);

        // Queue my man up
        lineup.add(v);
        while (!lineup.isEmpty())
        {
            for (int i = 0; i < edges[lineup.peek()].length; i++)
            {
                if (bfsvisited.contains(getLabel(i)) || edges[lineup.peek()][i] == 0)
                    continue;
                // Queue my mans unvisited neighbours
                bfsvisited.add(getLabel(i));
                if (!quiet) printVisited(i);
                lineup.add(i);
            }
            // bye bye
            lineup.poll();
        }
    }

    /**
     * Perform BFS.
     * 
     * @param quiet
     */
    public void runBFS(boolean quiet)
    {
        bfsvisited = new ArrayList<>();
        for (int i = 0; i < edges.length; i++)
        {
            if (bfsvisited.contains(getLabel(i)))
                continue;
            if (bfsvisited.size() == cardVertices)
                break;
            bfsHelper(i, quiet);
            
            if (i == edges.length - 1) i = 0;
        }
    }

    /**
     * Perform BFS from a starting vertex.
     * 
     * @param v
     * @param quiet
     */
    public void runBFS(String vertex, boolean quiet)
    {
        // Get the vertex for the supplied string
        int v = containsVertex(vertex);
        if (v == VERTEX_BAD) return;
        bfsvisited = new ArrayList<>();

        for (int i = v; i < edges.length; i++)
        {
            if (bfsvisited.contains(getLabel(i)))
                continue;
            if (bfsvisited.size() == cardVertices)
                break;
            bfsHelper(i, quiet);
            
            if (i == edges.length - 1) i = 0;
        }
    }

    /**
     * Returns string of last BFS order.
     * 
     * @return
     */
    public String getLastBFSOrder()
    {
        if (bfsvisited == null)
            return "BFS not run yet";
        return bfsvisited.toString();
    }
}