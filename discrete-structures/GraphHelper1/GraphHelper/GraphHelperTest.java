import java.util.Set;
import java.util.LinkedHashSet;
import java.util.List;


public class GraphHelperTest {

	public static void main(String[] args) {
		
		// Graphs - depthFirstTraversal()
		GraphHelper graph = createGraph();
        GraphHelper.GraphIterator itr = graph.iterator();
        
        System.out.println("Adjacency vertex lists:");
        while (itr.hasNext())
            System.out.println(itr.next());
        
		String graphRoot = "Bob";

		System.out.println("\nPerforming a depth first traversal of the graph");
		System.out.println(depthFirstTraversal(graph, graphRoot));

	}
	
//*******************************************Graphs***************************************************

	static GraphHelper createGraph() {
		GraphHelper graph = new GraphHelper();
		graph.addVertex("Bob");
		graph.addVertex("Alice");
		graph.addVertex("Mark");
		graph.addVertex("Rob");
		graph.addVertex("Maria");
		graph.addEdge("Bob", "Alice");
		graph.addEdge("Bob", "Rob");
		graph.addEdge("Alice", "Mark");
		graph.addEdge("Rob", "Mark");
		graph.addEdge("Alice", "Maria");
		graph.addEdge("Rob", "Maria");
		return graph;
	}
    
	// TODO fill in these methods
	static Set<String> depthFirstTraversal(GraphHelper graph, String root) {
      
		LinkedHashSet<String> dftSet = new LinkedHashSet<>();
		dfs(graph, root, dftSet);
		return dftSet;
    }
    
    static void dfs(GraphHelper graph, String root, LinkedHashSet<String> dftSet){
		
		dftSet.add(root);
		List<String> nbrs = graph.getAdjVertices(root);
		for(String nbr : nbrs) {
			if (!dftSet.contains(nbr)) {
				dfs(graph, nbr, dftSet);
			}
		}
	}
}


