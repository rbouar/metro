package fr.univparis.metro;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.Set;
import java.util.function.Predicate;


public class WGraph<T>{

    private HashMap<T, List<Pair<T, Double>>> wGraph;

    public WGraph(){
        this.wGraph = new HashMap<T, List<Pair<T, Double>>>();
    }

    /**
     * @return the set of all the keys contained in the HashMap wGraph.
     */
    public Set<T> getVertices(){
        return this.wGraph.keySet();
    }

    public int nbVertex() {return wGraph.size();}

    /**
     * @param vertex
     * @return a list that contains all the vertices we can reach from the vertex "vertex".
     */
    public List<T> neighbors(T vertex){
        ArrayList<T> ret = new ArrayList<T>();
        List<Pair<T, Double>> edges = this.wGraph.get(vertex);
        for(int i=0; i<edges.size(); i++){
            ret.add(edges.get(i).getObj());
        }
        return ret;
    }


    /**
     * @param s
     * @param p
     * @return the weight between the vertex s and the vertex p.
     */
    public Double weight(T s, T p){
        List<Pair<T, Double>> edges = this.wGraph.get(s);
        for(int i=0; i<edges.size(); i++){
            Pair<T, Double> e = edges.get(i);
            if(p.equals(e.getObj())) return e.getValue();
        }
        return Double.NaN;
    }


    /**
     * @param v a vertex we add to the HashMap wGraph.
     * @return true if v has correctly been added, false otherwise.
     */
    public boolean addVertex(T v){
        if(this.wGraph.get(v)==null){
            this.wGraph.put(v, new ArrayList<Pair<T, Double>>());
            return true;
        }
        return false;
    }


    /**
     * @param v a vertex we delete from the HashMap wGraph.
     * @return true if v has been correctly deleted, false otherwise.
     */
    public boolean deleteVertex(T v){
        Pair<T, Double> rm = null;
        if(this.wGraph.containsKey(v)){
            for(Map.Entry<T, List<Pair<T, Double>>> c : this.wGraph.entrySet()){
                for(Pair<T, Double> e : c.getValue()){
                    if(e.getObj().equals(v)){
                        rm = e;
                        break;
                    }
                }
                if(rm!=null) c.getValue().remove(rm);
            }
            this.wGraph.remove(v);
            return true;
        }
        return false;
    }


    /**
     * @param s the start of the edge.
     * @param p the end of the edge.
     * @param weight the weight of the edge.
     * @return true if the edge has correctly been created and added to the HashMap wGraph.
     */
    public boolean addEdge(T s, T p, Double weight){
        if (s.equals(p)) return false;
        if(this.wGraph.containsKey(s) && this.wGraph.containsKey(p) && this.weight(s, p).equals(Double.NaN)) {
	    this.wGraph.get(s).add(new Pair<T, Double>(p, weight));
            return true;
        }
        return false;
    }


    /**
     * Remove the vertex that has the vertices s and p.
     * @param s one of the vertex of the edge we want to remove.
     * @param p one of the vertex of the edge we want to remove.
     * @return true if the edge has correctly been removed.
     */
    public boolean removeEdge(T s, T p){
        Pair<T, Double> rm = null;
        if(this.wGraph.containsKey(s) && this.wGraph.containsKey(p)){
            List<Pair<T, Double>> l_s = this.wGraph.get(s);
            for(Pair<T, Double> e : l_s){
                if(e.getObj().equals(p)){
                    rm = e;
                    break;
                }
            }
            l_s.remove(rm);
            return true;
        }
        return false;
    }

   /**
    * Add double edge between e and every element of the graph that evaluate true with p
    * @param e the vertex wich we want to add Edge
    * @param weight the weight for the added Edge
    * @param p the Predicate we want that evaluate if we want to add an edge or not for every element of the graph
    */
    public boolean addDoubleEdge(T e, Double weight, Predicate<T> p) {
      if (!wGraph.containsKey(e)) return false;
      Set<T> set = wGraph.keySet();
      for (T t : set) {
        if (p.test(t)) {
          addEdge(e, t, weight);
          addEdge(t, e, weight);
        }
      }
      return true;
    }

   /**
    * Return the number of Vertex that evaluate true with predicate p
    * @param p The predicate to test for every vertex
    * @return The number of vertex that evaluate true with predicate p
    *
    */
    public int nbVertex(Predicate<T> p) {
      int res = 0;
      Set<T> set = wGraph.keySet();
      for (T t : set) {
        if (p.test(t)) res++;
      }
      return res;
    }

   /**
    * Return a String containing the toString funtion of every vertex that evualuate true with predicate p
    * @param p The predicate to test xith every Vertex
    * @return a String containing the toString funtion of every vertex that evualuate true with predicate p
    *
    */
    public String vertexToString(Predicate<T> p) {
      String res = "";
      for (T t : wGraph.keySet()) {
        if (p.test(t)) res += t.toString() + "\n";
      }
      return res;
    }

    public boolean containsKey(T s){
      return wGraph.containsKey(s);
    }

    public List<Pair<T, Double>> get(T s){
      return wGraph.get(s);
    }

}