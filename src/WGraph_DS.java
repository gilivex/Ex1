import  java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import java.util.Set;

public class WGraph_DS implements weighted_graph {
    private int MC = 0;
    private HashMap<Integer, node_info> MapG;
    private HashMap<Integer, HashMap<Integer, Double>> Edges;
    private int EdgeNum;

    private class Node implements node_info {

        private int key;
        private String info;
        private double tag;

        public Node(int key) {
            this.key = key;
        }

        /**
         * Return the key (id) associated with this node.
         * Note: each node_data should have a unique key.
         *
         * @return
         */
        @Override
        public int getKey() {
            return key;
        }

        /**
         * return the remark (meta data) associated with this node.
         *
         * @return
         */
        @Override
        public String getInfo() {
            return info;
        }

        /**
         * Allows changing the remark (meta data) associated with this node.
         *
         * @param s
         */
        @Override
        public void setInfo(String s) {
            this.info=s;
        }

        /**
         * Temporal data (aka distance, color, or state)
         * which can be used be algorithms
         *
         * @return
         */
        @Override
        public double getTag() {
            return tag;
        }

        /**
         * Allow setting the "tag" value for temporal marking an node - common
         * practice for marking by algorithms.
         *
         * @param t - the new value of the tag
         */
        @Override
        public void setTag(double t) {
            this.tag=t;
        }
    }

    public WGraph_DS(weighted_graph wg) {
        this.MapG=new HashMap<Integer, node_info>();
        this.Edges=new HashMap<Integer, HashMap<Integer, Double>>();
        WGraph_DS temp=(WGraph_DS)wg;
        Iterator<node_info> itG=temp.MapG.values().iterator();
        while(itG.hasNext()) {
            node_info t=itG.next();
            this.MapG.put(t.getKey(),t);
            MC++;
        }
        Iterator<Integer> itNi=temp.Edges.keySet().iterator();
        while(itNi.hasNext()) {
            int tem=itNi.next();
            this.Edges.put(tem, temp.Edges.get(tem));
            MC++;
        }
        this.EdgeNum=temp.EdgeNum;

    }

    public WGraph_DS() {
        // TODO Auto-generated constructor stub
        MapG=new HashMap<Integer, node_info>();
        Edges=new HashMap<Integer, HashMap<Integer, Double>>();
        EdgeNum=0;
    }

    /**
     * return the node_data by the node_id,
     *
     * @param key - the node_id
     * @return the node_data by the node_id, null if none.
     */
    @Override
    public node_info getNode(int key) {
        return MapG.get(key);
    }

    /**
     * return true iff (if and only if) there is an edge between node1 and node2
     * Note: this method should run in O(1) time.
     *
     * @param node1
     * @param node2
     * @return
     */
    @Override
    public boolean hasEdge(int node1, int node2) {
        if (!MapG.containsKey(node1) || !MapG.containsKey(node2))
            return false;
        return Edges.get(node1).containsKey(node2);
    }

    /**
     * return the weight if the edge (node1, node1). In case
     * there is no such edge - should return -1
     * Note: this method should run in O(1) time.
     *
     * @param node1
     * @param node2
     * @return
     */
    @Override
    public double getEdge(int node1, int node2) {
        if (!MapG.containsKey(node1) || !MapG.containsKey(node2) || !hasEdge(node1, node2))
            return -1;
        return Edges.get(node1).get(node2);
    }

    /**
     * add a new node to the graph with the given key.
     * Note: this method should run in O(1) time.
     * Note2: if there is already a node with such a key -> no action should be performed.
     *
     * @param key
     */
    @Override
    public void addNode(int key) {
        if (MapG.containsKey(key))
            return;
        MapG.put(key,new Node(key));
        Edges.put(key,new HashMap<>());
        MC++;
    }

    /**
     * Connect an edge between node1 and node2, with an edge with weight >=0.
     * Note: this method should run in O(1) time.
     * Note2: if the edge node1-node2 already exists - the method simply updates the weight of the edge.
     *
     * @param node1
     * @param node2
     * @param w
     */
    @Override
    public void connect(int node1, int node2, double w) {
        if(MapG.containsKey(node1) && MapG.containsKey(node2))
            if(!hasEdge(node1, node2) && node1!=node2 && w >= 0) {
                Edges.get(node1).put(node2,w);
                Edges.get(node2).put(node1,w);
                MC++;
                EdgeNum++;
            }
    }

    /**
     * This method return a pointer (shallow copy) for a
     * Collection representing all the nodes in the graph.
     * Note: this method should run in O(1) tim
     *
     * @return Collection<node_data>
     */
    @Override
    public Collection<node_info> getV() {
        return MapG.values();
    }

    /**
     * This method returns a Collection containing all the
     * nodes connected to node_id
     * Note: this method can run in O(k) time, k - being the degree of node_id.
     *
     * @param node_id
     * @return Collection<node_data>
     */
    @Override
    public Collection<node_info> getV(int node_id) {
        if(!MapG.containsKey(node_id))
            return null;
        Set<Integer> l=Edges.get(node_id).keySet();
        Collection<node_info> ans = new ArrayList<node_info>();
        Iterator<Integer> it=l.iterator();
        while(it.hasNext()) {
            int n=it.next();
            if(MapG.containsKey(n))
                ans.add(MapG.get(n));
        }
        return ans;
    }

    /**
     * Delete the node (with the given ID) from the graph -
     * and removes all edges which starts or ends at this node.
     * This method should run in O(n), |V|=n, as all the edges should be removed.
     *
     * @param key
     * @return the data of the removed node (null if none).
     */
    @Override
    public node_info removeNode(int key) {
        if(!MapG.containsKey(key))
            return null;


        //create an iterator to add all Node key Ni to an DataS
        Iterator<Integer> itInt=Edges.get(key).keySet().iterator();
        ArrayList<Integer> ToRemove=new  ArrayList<Integer>();
        while(itInt.hasNext()) {
            ToRemove.add(itInt.next());
        }
        //remove all edges from Node key Ni to Node key
        Iterator<Integer> itnextToRemove=ToRemove.iterator();
        while(!ToRemove.isEmpty() && itnextToRemove.hasNext()) {
            removeEdge(itnextToRemove.next(),key);

        }
        MC++;
        return MapG.remove(key);

    }


    /**
     * Delete the edge from the graph,
     * Note: this method should run in O(1) time.
     *
     * @param node1
     * @param node2
     */
    @Override
    public void removeEdge(int node1, int node2) {
        if(!MapG.containsKey(node1) || !MapG.containsKey(node2)) {
            return;
        }
        if(Edges.get(node1).containsKey(node2) || Edges.get(node2).containsKey(node1)) {
            if(Edges.get(node1).containsKey(node2))
                Edges.get(node1).remove(node2);
            if(Edges.get(node2).containsKey(node1))
                Edges.get(node2).remove(node1);
            EdgeNum--;
            MC++;
        }
    }

    /**
     * return the number of vertices (nodes) in the graph.
     * Note: this method should run in O(1) time.
     *
     * @return
     */
    @Override
    public int nodeSize() {
        return MapG.size();
    }

    /**
     * return the number of edges (undirectional graph).
     * Note: this method should run in O(1) time.
     *
     * @return
     */
    @Override
    public int edgeSize() {
        return EdgeNum;
    }

    /**
     * return the Mode Count - for testing changes in the graph.
     * Any change in the inner state of the graph should cause an increment in the ModeCount
     *
     * @return
     */
    @Override
    public int getMC() {
        return MC;
    }
}

