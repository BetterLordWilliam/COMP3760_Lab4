import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Arrays;
import java.util.Map;
import java.util.Map.Entry;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Graph class for lab 4.
 * 
 * @author Will Otterbein, Set D, A01372608
 * @version 2025-1
 */
public class Graph
{
    // Constant for when I cannot find a vertex integer for a given string
    private static final int            VERTEX_BAD          = -1;
    private static final String         OUTSIDE_ARRAY       = "Outside array bounds.";
    private static final String         VERTEX_VISITED      = "Visited: %s";
    private static final String         BAD_VERTEX_LABELS   = "Bad vertex label(s) given: %s, %s";
    private static final String         EXPECTED_LABELS     = "Expected: %s";

    // Instance variables, initialized in the constructor
    private final boolean               isDirected;
    private final int                   cardVertices;
    private final int[][]               adjMat;

    // My traversal lists as well as the queue used in BFS helper
    private final String[]              vertexLabels;
    private final List<String>          dfsvisited;
    private final List<String>          dfsdeadEnds;
    private final List<String>          bfsvisited;
    private final List<Integer>         lineup;
    // private final Map<String, Integer>  vertexLabelIndexMap;
    private final Map<String, Boolean>  visitedMap;

    /**
     * Graph constructor.
     * 
     * @param   vertexLabels    String[]    Vertex strings for the graph
     * @param   isDirected      boolean     Indicating whether the graph is directed or undirected
     */
    public Graph(
        final String[]  vertexLabels,
        final boolean   isDirected
    ) {
        this.isDirected             = isDirected;
        this.cardVertices           = vertexLabels.length;
        this.adjMat                 = new int[cardVertices][cardVertices];

        // Label trackers
        this.vertexLabels           = vertexLabels;
        this.dfsvisited             = new ArrayList<>();
        this.dfsdeadEnds            = new ArrayList<>();
        this.bfsvisited             = new ArrayList<>();
        this.lineup                 = new LinkedList<>();

        // Initialize a map to keep track of visited vertex
        this.visitedMap             = new HashMap<>();
    }

    /**
     * Indicates whether the graph is a boolean.
     * 
     * @return whether the graph is directed or undirected
     */
    public boolean isDirected()
    {
        return isDirected;
    }

    /**
     * Returns the number of vertices.
     * 
     * @return the number of vertices in the graph
     */
    public int size()
    {
        return cardVertices;
    }

    /**
     * Returns vertex label of V
     * 
     * @param v     int     vertex label
     * @return
     */
    public String getLabel(final int v)
    {
        // Item exists then return the label for the item
        try {
            return vertexLabels[v];
        // Otherwise, return an empty string
        } catch (IndexOutOfBoundsException e) {
            return OUTSIDE_ARRAY;
        }
    }

    /**
     * Returns the vertex index of a specified string label.
     * 
     * @param vertex
     * @return
     */
    public int getIndex(final String vertex)
    {
        for (int i = 0; i < vertexLabels.length; i++)
        {
            if (vertexLabels[i].equals(vertex)) return i;
        }
        return VERTEX_BAD;
    }

    /**
     * Adds an edge to the graph using existing vertex labels.
     * 
     * @param vertex1
     * @param vertex2
     */
    public void addEdge(String vertex1, String vertex2)
    {
        final int v1 = getIndex(vertex1);
        final int v2 = getIndex(vertex2);

        // Bad vertex labels supplied
        if (v1 == VERTEX_BAD || v1 == VERTEX_BAD)
        {
            System.out.println(String.format(BAD_VERTEX_LABELS, vertex1, vertex2));
            System.out.println(String.format(EXPECTED_LABELS, vertexLabels));
            return;
        }

        // We good, add the edge
        if (isDirected)
            adjMat[v1][v2] = 1;
        else
        {
            adjMat[v1][v2] = 1;
            adjMat[v2][v1] = 1;
        }
    }

    /**
     * String representation of the adjacency matrix.
     * 
     * @return      String
     */
    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        for (int[] adjMat1 : adjMat) {
            sb.append(Arrays.toString(adjMat1));
            sb.append("\n");
        }
        return sb.toString();
    }

    /**
     * Helper function for DFS, use call stack instead of creating a stack.
     * 
     * @param v     int     vertex to start running BFS on
     * @param quiet boolean whether to run BFS in quiet mode
     */
    private void dfsHelper(final int vertex, boolean quiet)
    {
        // Save index of the vertex
        final int v = getIndex(vertex);

        // Print visited message, if not running quietly
        if (!quiet) System.out.println(String.format(VERTEX_VISITED, vertex));
        
        // Update visited list and map
        dfsvisited.add(vertex);
        visitedMap.put(vertex, true);

        for (int i = 0; i < adjMat[v].length; i++)
        {
            // If the vertex is already visited or there is no edge, skip
            if (visitedMap.get(vertexLabels[i]) == true || adjMat[v][i] == 0)
                continue;
            dfsHelper(vertex, quiet);
        }
        
        // Update dead ends list
        dfsdeadEnds.add(vertex);
    }

    /**
     * Nice public interface
     * 
     * @param quiet
     */
    public void runDFS(boolean quiet)
    {
        dfsvisited.clear();
        dfsdeadEnds.clear();
        visitedMap.clear();

        // Run DFS on the graph
        for (String vertex : vertexLabels)
        {
            if (visitedMap.get(vertex) == true)
                continue;
            dfsHelper(vertex, quiet);
        }
    }

    /**
     * Nice public interface
     * 
     * @param quiet
     */
    public void runDFS(String startVertex, boolean quiet)
    {
        // Validate the supplied vertex is in the graph


        dfsvisited.clear();
        dfsdeadEnds.clear();
        visitedMap.clear();

        // Run DFS on the supplied label
        dfsHelper(startVertex, quiet);
        
        // Run DFS on the graph
        for (String vertex : vertexLabels)
        {
            if (visitedMap.get(vertex) == true)
                continue;
            dfsHelper(vertex, quiet);
        }
    }

    /**
     * Returns string of last DFS order.
     * 
     * @return
     */
    public String getLastDFSOrder()
    {
        // Avoid nulls
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
        // Avoid nulls
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
        lineup.clear();
        bfsvisited.add(getLabel(v));
        if (!quiet) printVisited(v);

        // Queue my man up
        lineup.add(v);
        while (!lineup.isEmpty())
        {
            for (int i = 0; i < adjMat[lineup.peek()].length; i++)
            {
                // If we have already visited this vertex or there is no edge
                if (bfsvisited.contains(getLabel(i)) || adjMat[lineup.peek()][i] == 0)
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
        bfsvisited.clear();
        visitedMap.clear();

        for (int i = 0; i < adjMat.length; i++)
        {
            // If we have already visited the vertex
            if (bfsvisited.contains(getLabel(i)))
                continue;
            // Start the bfs from this vertex
            if (bfsvisited.size() == cardVertices)
                break;
            bfsHelper(i, quiet);
            
            if (i == adjMat.length - 1) i = 0;
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
        
        bfsvisited.clear();
        visitedMap.clear();

        for (int i = v; i < adjMat.length; i++)
        {
            // If we have already visited this vertex
            if (bfsvisited.contains(getLabel(i)))
                continue;
            // start the bfs form this vertex
            if (bfsvisited.size() == cardVertices)
                break;
            bfsHelper(i, quiet);
            
            if (i == adjMat.length - 1) i = 0;
        }
    }

    /**
     * Returns string of last BFS order.
     * 
     * @return
     */
    public String getLastBFSOrder()
    {
        // Avoid nulls
        if (bfsvisited == null)
            return "BFS not run yet";
        return bfsvisited.toString();
    }
}