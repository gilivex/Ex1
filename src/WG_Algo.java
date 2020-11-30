import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

public class WG_Algo implements weighted_graph_algorithms{

    weighted_graph WG;

    @Override
    public void init(weighted_graph g) {
        // TODO Auto-generated method stub
        if (g == null)
            return;
        this.WG = (WGraph_DS)g;
    }

    @Override
    public weighted_graph getGraph() {
        // TODO Auto-generated method stub
        return WG;
    }

    @Override
    public weighted_graph copy() {
        // TODO Auto-generated method stub
        weighted_graph copy=new WGraph_DS(this.WG);
        return copy;
    }

    @Override
    public boolean isConnected() {
        if(WG.nodeSize()<2)
            return true;

        // TODO Auto-generated method stub
        Queue<Integer> q=new LinkedList<Integer>();
        boolean[] visit=new boolean[1000000];

        q.add(WG.getV().iterator().next().getKey());

        while(!q.isEmpty()) {
            int t=q.poll();
            if(!visit[t]) {
                visit[t]=true;
                Iterator<node_info> it=WG.getV(t).iterator();
                while(it.hasNext()) {
                    int tem=it.next().getKey();
                    if(!visit[tem])
                        q.add(tem);
                }
            }
        }
        Iterator<node_info> iter=WG.getV().iterator();
        while(iter.hasNext()) {
            if(!visit[iter.next().getKey()])
                return false;
        }
        return true;
    }


    @Override
    public double shortestPathDist(int src, int dest) {

        PriorityQueue<node_info> minHeap = new PriorityQueue<node_info>(new Comparator<node_info>() {
            @Override
            public int compare(node_info o1, node_info o2) {
                return - Double.compare(o2.getTag(),o1.getTag());
            }
        });

        Iterator<node_info> it=WG.getV().iterator();
        while(it.hasNext()) {
            node_info t=it.next();
            t.setInfo("white");
            t.setTag(Integer.MAX_VALUE);
        }
        minHeap.add(WG.getNode(src));
        while(!minHeap.isEmpty()){
            node_info cur=minHeap.poll();
            Iterator<node_info> iter=WG.getV(cur.getKey()).iterator();
            while(it.hasNext()) {
                node_info ni=iter.next();
                double CurPlusEdge=cur.getTag()+WG.getEdge(cur.getKey(), ni.getKey());
                if(!ni.getInfo().equals("black") && CurPlusEdge<ni.getTag()) {
                    minHeap.add(ni);
                    int NewDist=ni.getKey();
                    WG.getNode(NewDist).setTag(CurPlusEdge);
                }
                cur.setInfo("black");
            }
            if(WG.getNode(dest).getInfo().equals("black"))
                break;
        }
        return WG.getNode(dest).getTag();
    }


    @Override
    public List<node_info> shortestPath(int src, int dest) {
        PriorityQueue<node_info> minHeap = new PriorityQueue<node_info>(new Comparator<node_info>() {
            @Override
            public int compare(node_info o1, node_info o2) {
                return - Double.compare(o2.getTag(),o1.getTag());
            }
        });

        HashMap<Integer,String> Paths=new HashMap<Integer,String>();

        Iterator<node_info> it=WG.getV().iterator();
        while(it.hasNext()) {
            node_info t=it.next();
            t.setInfo("white");
            t.setTag(Integer.MAX_VALUE);
            Paths.put(t.getKey(), Integer.toString(t.getKey())+"->");
        }
        minHeap.add(WG.getNode(src));
        while(!minHeap.isEmpty()){
            node_info cur=minHeap.poll();
            Iterator<node_info> iter=WG.getV(cur.getKey()).iterator();
            while(it.hasNext()) {
                node_info ni=iter.next();
                double CurPlusEdge=cur.getTag()+WG.getEdge(cur.getKey(), ni.getKey());
                if(!ni.getInfo().equals("black") && CurPlusEdge<ni.getTag()) {
                    minHeap.add(ni);
                    int NewDist=ni.getKey();
                    WG.getNode(NewDist).setTag(CurPlusEdge);
                    Paths.put(ni.getKey(), Paths.get(cur.getKey())+Integer.toString(cur.getKey())+"->");
                    //save paths in hash map as as a string to extract it and transform the nodes from string to list
                }
                cur.setInfo("black");
            }
            if(WG.getNode(dest).getInfo().equals("black"))
                break;
        }
        List<node_info> ans=new ArrayList<node_info>();
        ans=GetList(Paths.get(dest));
        return ans;
    }

    private List<node_info> GetList(String info) {
        // TODO Auto-generated method stub
        List<node_info> newList = new ArrayList<node_info>();
        int i=0;
        while(i<info.length()) {
            while(info.charAt(i)!='-') {
                i++;
            }
            String Nextkey=info.substring(0, i);
            newList.add(WG.getNode(Integer.parseInt(Nextkey)));
            info=info.substring(i+2);
        }
        return newList;
    }

    @Override
    public boolean save(String file) {
        WG_Algo n=this;
        try {
            FileOutputStream fille = new FileOutputStream(file);
            ObjectOutputStream out = new ObjectOutputStream(fille);
            out.writeObject(n);
            out.close();
            fille.close();


        }catch (IOException e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean load(String file) {
        try{
            FileInputStream fille= new FileInputStream(file);
            ObjectInputStream input = new ObjectInputStream(fille);
            WG_Algo nGr = (WG_Algo) input.readObject();
            this.init(nGr.getGraph());
        }catch (IOException | ClassNotFoundException e){
            return false;

        }
        return true;
    }

    public boolean equals(WG_Algo sec) {
        WGraph_DS second=(WGraph_DS) sec.WG;
        WGraph_DS first=(WGraph_DS) this.WG;
        boolean flag=true;
        for(node_info i: first.getV()) {
            for(node_info j: first.getV(i.getKey()) ) {
                flag&=second.hasEdge(i.getKey(), j.getKey());
            }
        }
        return flag;
    }
}
