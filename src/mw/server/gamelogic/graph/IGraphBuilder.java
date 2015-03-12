/**
 * @author Charlie Bloomfield
 * Mar 12, 2015
 */

package mw.server.gamelogic.graph;

import java.util.Collection;
import java.util.HashMap;

/**
 * 
 */
public interface IGraphBuilder {
	
	GraphNode[][] initializeGraphNodes(int[][] pCoordinates);
	
	/**
	 * Builds a graph from a 2d set of GraphNodes
	 */
	HashMap<GraphNode, Collection<GraphNode>> buildGraph(GraphNode[][] pCoordinates);
}
