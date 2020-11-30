//import ex1.src.WGraph_DS;
//import ex1.src. weighted_graph;
//import ex1.src.WG_Algo;
//import ex1.src.*;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import static org.junit.jupiter.api.Assertions.*;

class test {
    private static Random _rnd = null;
    @Test
    void nodeSize() {
        weighted_graph g = new WGraph_DS();
        g.addNode(0);
        g.addNode(1);
        g.addNode(2);
        g.addNode(2);
        g.removeNode(0);
        g.removeNode(2);
        g.removeNode(4);
        g.removeNode(1);
        assertEquals(0,g.nodeSize());
    }

    @Test
    void edgeSize() {
        weighted_graph g = new WGraph_DS();
        g.addNode(0);
        g.addNode(1);
        g.addNode(2);
        g.connect(0,1,22);
        g.connect(0,2,30);
        g.connect(1,5,14);
        g.connect(1,1,0);
        g.connect(1,2,11);
        assertEquals(3, g.edgeSize());
        assertEquals(g.getEdge(0,1), g.getEdge(1,0));
    }

    @Test
    void getV() {
        weighted_graph g = new WGraph_DS();
        g.addNode(0);
        g.addNode(1);
        g.addNode(2);
        g.connect(0,1,22);
        g.connect(0,2,30);
        g.connect(1,5,14);
        g.connect(1,1,0);
        g.connect(1,2,11);
        Collection<node_info> c = g.getV();
        Iterator<node_info> path = c.iterator();
        while (path.hasNext()) {
            node_info node = path.next();
            assertNotNull(node);
        }
    }

    @Test
    void connect() {
        weighted_graph g = new WGraph_DS();
        g.addNode(0);
        g.addNode(1);
        g.addNode(2);
        g.connect(0,1,22);
        g.connect(0,2,30);
        g.connect(1,5,14);
        g.connect(1,1,0);
        g.connect(1,2,11);
        g.connect(1,2,100);
        assertEquals(22,g.getEdge(1,0));
        assertEquals(-1,g.getEdge(1,5));
        assertFalse(g.hasEdge(2,4));
        g.removeEdge(0,3);
        g.removeEdge(1,1);
        g.removeEdge(0,1);
        double x2 = g.getEdge(2,1);
        assertEquals(x2,100);
    }

    @Test
    void removeNode() {
        weighted_graph g = new WGraph_DS();
        g.addNode(0);
        g.addNode(1);
        g.addNode(2);
        g.connect(0,1,22);
        g.connect(0,2,30);
        g.connect(1,5,14);
        g.connect(1,1,0);
        g.connect(1,2,11);
        g.removeNode(2);
        g.removeNode(2);
        g.removeNode(0);
        g.removeNode(10);
        assertFalse(g.hasEdge(1,0));
        int s = g.edgeSize();
        assertEquals(0,s);
        assertEquals(1,g.nodeSize());
    }

    @Test
    void removeEdge() {
        weighted_graph g = new WGraph_DS();
        g.addNode(0);
        g.addNode(1);
        g.addNode(2);
        g.connect(0,1,22);
        g.connect(0,2,30);
        g.connect(1,5,14);
        g.connect(1,1,0);
        g.connect(1,2,11);
        g.removeEdge(0,1);
        g.removeEdge(0,4);
        g.removeEdge(1,1);
        double x = g.getEdge(0,1);
        assertEquals(x,-1);
    }
    /**
     * Checks that there is an arc between two specific vertices in the graph.
     */
    @Test
    void hasEdge() {
        int n = 5;
        int e = n*(n-1)/2;
        weighted_graph g = graph_creator(n,e,2);
        for(int i=0;i<n;i++) {
            for(int j=i+1;j<n;j++) {
                boolean b = g.hasEdge(i,j);
                assertTrue(b);
                assertTrue(g.hasEdge(j,i));
            }
        }
    }

    @Test
    void isConnected() {
        weighted_graph g0=graph_creator(0,0,1);
        weighted_graph_algorithms ag0 = new WG_Algo();
        ag0.init(g0);
        assertTrue(ag0.isConnected());

        g0=graph_creator(4,0,1);
        ag0 = new WG_Algo();
        ag0.init(g0);
        assertFalse(ag0.isConnected());

        g0=graph_creator(4,2,1);
        ag0=new WG_Algo();
        ag0.init(g0);
        assertFalse(ag0.isConnected());

        g0=graph_creator(10,22,1);
        ag0=new WG_Algo();
        ag0.init(g0);
        assertTrue(ag0.isConnected());

    }

    @Test
    void shortestPathDist() {
        weighted_graph g = myGraph();
        weighted_graph_algorithms ag = new WG_Algo();
        ag.init(g);
        assertTrue(ag.isConnected());
        double ans = ag.shortestPathDist(0,3);
        assertEquals(ans, 19);
    }

    @Test
    void shortestPath() {
        weighted_graph g = myGraph();
        weighted_graph_algorithms ag = new WG_Algo();
        ag.init(g);
        List<node_info> l = ag.shortestPath(0,3);
        int[] checkKey = {0,1,3};
        int i = 0;
        for(node_info n: l) {
            assertEquals(n.getKey(), checkKey[i]);
            i++;
        }
    }

    @Test
    void save_load() {
        weighted_graph g0 = graph_creator(10,30,1);
        weighted_graph_algorithms ag0 = new WG_Algo();
        ag0.init(g0);
        String str = "g0.obj";
        ag0.save(str);
        weighted_graph g1 =graph_creator(10,30,1);
        ag0.load(str);
        assertEquals(g0,g1);
        g0.removeNode(0);
        assertNotEquals(g0,g1);
    }

    private weighted_graph myGraph() {
        weighted_graph g =graph_creator(4,0,21);
        g.connect(0,1,9);
        g.connect(1,3,10);
        g.connect(1,2,15);
        g.connect(2,0,3);
        g.connect(2,3,20);
        return g;
    }


    /**
     * Generate a random graph with v_size nodes and e_size edges
     * @param v_size
     * @param e_size
     * @param seed
     * @return
     */
    public static weighted_graph graph_creator(int v_size, int e_size, int seed) {
        weighted_graph g = new WGraph_DS();
        _rnd = new Random(seed);
        for(int i=0;i<v_size;i++) {
            g.addNode(i);
        }
        int[] nodes = nodes(g);
        while(g.edgeSize() < e_size) {
            int a = nextRnd(0,v_size);
            int b = nextRnd(0,v_size);
            int i = nodes[a];
            int j = nodes[b];
            double w = _rnd.nextDouble();
            g.connect(i,j, w);
        }
        return g;
    }
    private static int nextRnd(int min, int max) {
        double v = nextRnd(0.0+min, (double)max);
        int ans = (int)v;
        return ans;
    }
    private static double nextRnd(double min, double max) {
        double d = _rnd.nextDouble();
        double dx = max-min;
        double ans = d*dx+min;
        return ans;
    }
    /**
     * Simple method for returning an array with all the node_data of the graph,
     * Note: this should be using an Iterator<node_edge> to be fixed in Ex1
     * @param g
     * @return
     */
    private static int[] nodes(weighted_graph g) {
        int size = g.nodeSize();
        Collection<node_info> V = g.getV();
        node_info[] nodes = new node_info[size];
        V.toArray(nodes);
        int[] ans = new int[size];
        for(int i=0;i<size;i++) {ans[i] = nodes[i].getKey();}
        Arrays.sort(ans);
        return ans;
    }
}