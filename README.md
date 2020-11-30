*** ReadMe by Gil Vexberg- EX1 ***

== WGraph_DS.java ==
-Use HashMap to represent Node in the graph and neighbors.

WGraph_DS()
-Creating a new WGraph_DS.

nodeEquals(node_info n1, node_info n2)
-Check if node are equals.

equals(Object g)
-Check if graph are equals.

NodeInfo()
-Creating a new node.

NodeInfo(node_info n)
-Constructor by node.

NodeInfo(int key)
-Creating a new node by key.

node_info getDaddy()
-Give the node that gives the way to shortestPath.

setDaddy(node_info daddy)
-Set a daddy node (for shortestPath).

boolean hasDaddy()
-If has shortestPath node.

Stack<node_info> sugarDaddy()
-Stack for shortestPath node.

getKey()
-Gives the key of this Node.

getInfo()
-Gives this Node info.

setInfo(String s)
-Set a new info @s to this Node.

getTag()
-Gives this Node tag.

setTag(int t)
-Set a new tag @t to this Node.

compareTo(NodeInfo o)
-Use for PriorityQueue in Dijkstra's Algorithm.

clearall()
-Set all tags in the graph as INFINITY.
-Set all info in the graph as "no".

getNode(int key)
-Gives the Node by the @key.

hasEdge(int node1, int node2)
-Return true iff there is an edge between @node1 and @node2.

hasEdge(int node1, int node2)
-Return the vaule of the edge between @node1 and @node2.

addNode(int key)
-Add Node by key to the graph.

connect(int node1, int node2, double w)
-Conecting between @node1 and @node2 and value of @w.

Collection<node_info> getV()
-Gives a collection with all the Nodes of this graph (shallow copy).

Collection<node_info> getV(int node_id)
-Gives a Nodes collection with all neighbors of this @node_id (key).

removeNode(int key)
-Remove the Node by using this @key from the graph
-Removes all edges that connect to this Node.

removeEdge(int node1, int node2)
-Remove the edge between @node1 and @node2 (keys) from the graph.

nodeSize()
-Gives the number of Nodes in this graph.

edgeSize()
-Gives the number of edge in this graph.

getMC()
-(Mode Count) Gives the number of changes in this graph.

addNode(node_info n)
-add new node n to this graph.

copy(weighted_graph g)
-Makes a deep copy of the graph @g.


== WGraph_Algo.java ==


init(weighted_graph g)
-Initiate the graph.

getGraph()
-Get this graph @g.

copy()
-Makes a deep copy of the WGraph_DS.

isConnected()
-Returns true if and only if the graph is connectivity.

shortestPathDist(int src, int dest)
-Gives the shortest distance(by value of edges) between Node key @src to @dest.

shortestPath(int src, int dest)
-Return List of Nodes that the shortest distance(by value of edges) between Node key @src to @dest.

save(String file)
-Save graph in file.

load(String file)
-Load graph from file.
