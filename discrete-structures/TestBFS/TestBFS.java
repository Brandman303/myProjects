package Liang.chpt28;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class TestBFS {

static String[] vertices = {"Seattle", "San Francisco", "Los Angeles",
  "Denver", "Kansas City", "Chicago", "Boston", "New York",
  "Atlanta", "Miami", "Dallas", "Houston"};

static int[][] edges = {
  {0, 1}, {0, 3}, {0, 5},
  {1, 0}, {1, 2}, {1, 3},
  {2, 1}, {2, 3}, {2, 4}, {2, 10},
  {3, 0}, {3, 1}, {3, 2}, {3, 4}, {3, 5},
  {4, 2}, {4, 3}, {4, 5}, {4, 7}, {4, 8}, {4, 10},
  {5, 0}, {5, 3}, {5, 4}, {5, 6}, {5, 7},
  {6, 5}, {6, 7},
  {7, 4}, {7, 5}, {7, 6}, {7, 8},
  {8, 4}, {8, 7}, {8, 9}, {8, 10}, {8, 11},
  {9, 8}, {9, 11},
  {10, 2}, {10, 4}, {10, 8}, {10, 11},
  {11, 8}, {11, 9}, {11, 10}
};

  public static void main(String[] args) {
		// Graphs - breadthFirstTraversal()
		GraphHelper graph = createGraph();
        GraphHelper.GraphIterator itr;
        
        System.out.println("Adjacency vertex lists:");       
        itr = graph.iterator();
        while (itr.hasNext())
            System.out.println(itr.next());
        
		String graphRoot = "Chicago";

		System.out.println("\nPerforming a breadth-first traversal of the graph");
		System.out.println(breadthFirstTraversal(graph, graphRoot));
  }

//*******************************************Graphs***************************************************

	static GraphHelper createGraph() {

		GraphHelper graph = new GraphHelper();
        
        // define vertices in graph
        for(String v : vertices)
            graph.addVertex(v);
        
        // populate the adjacency edge list
        for(int e = 0; e < edges.length; e++)
            graph.addEdge(vertices[edges[e][0]], vertices[edges[e][1]]);

        
		return graph;
	}
    
	// TODO fill in these methods...NOT COMPLETED!!
	static Set<String> breadthFirstTraversal(GraphHelper graph, String root) {
        LinkedHashSet<String> bftSet = new LinkedHashSet();
        ArrayList<String> vertexQueue = new ArrayList<>();
        List<String> neighbors;
        
        vertexQueue.add(root);
        while(vertexQueue.size() > 0){
            String u = vertexQueue.remove(0);
            if(!bftSet.contains(u)){
                bftSet.add(u);
                neighbors = graph.getAdjVertices(u);
                for (String v : neighbors)
                    if(!bftSet.contains(v)){
                        vertexQueue.add(v);
                    }
            }
        }            
        return bftSet;
    }
}