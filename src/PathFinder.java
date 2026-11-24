import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;


public class PathFinder {

  public static void main(String[] args) {
    Vertex<String> hcesHouse        = new Vertex<>("HCE's House");
    Vertex<String> phoenixPark      = new Vertex<>("Phoenix Park");
    Vertex<String> sandymountStrand = new Vertex<>("Sandymount Strand");
    Vertex<String> towerAtSandycove = new Vertex<>("Tower at Sandycove");
    Vertex<String> davyByrnesPub    = new Vertex<>("Davy Byrne's Pub");
    Vertex<String> nationalLibrary  = new Vertex<>("National Library");
    Vertex<String> glasnevinCemetery= new Vertex<>("Glasnevin Cemetery");
    Vertex<String> ecclesStreet     = new Vertex<>("Eccles Street");
    Vertex<String> finnegansHouse   = new Vertex<>("Finnegan's House");

    hcesHouse.edges.add(new Edge<>(800, hcesHouse, phoenixPark));
    phoenixPark.edges.add(new Edge<>(800, phoenixPark, hcesHouse));

    hcesHouse.edges.add(new Edge<>(4200, hcesHouse, sandymountStrand));
    sandymountStrand.edges.add(new Edge<>(4200, sandymountStrand, hcesHouse));

    phoenixPark.edges.add(new Edge<>(3100, phoenixPark, davyByrnesPub));
    davyByrnesPub.edges.add(new Edge<>(3100, davyByrnesPub, phoenixPark));

    phoenixPark.edges.add(new Edge<>(2400, phoenixPark, glasnevinCemetery));
    glasnevinCemetery.edges.add(new Edge<>(2400, glasnevinCemetery, phoenixPark));

    sandymountStrand.edges.add(new Edge<>(1800, sandymountStrand, towerAtSandycove));
    towerAtSandycove.edges.add(new Edge<>(1800, towerAtSandycove, sandymountStrand));

    sandymountStrand.edges.add(new Edge<>(3300, sandymountStrand, nationalLibrary));
    nationalLibrary.edges.add(new Edge<>(3300, nationalLibrary, sandymountStrand));

    towerAtSandycove.edges.add(new Edge<>(6000, towerAtSandycove, davyByrnesPub));
    davyByrnesPub.edges.add(new Edge<>(6000, davyByrnesPub, towerAtSandycove));

    davyByrnesPub.edges.add(new Edge<>(600, davyByrnesPub, nationalLibrary));
    nationalLibrary.edges.add(new Edge<>(600, nationalLibrary, davyByrnesPub));

    davyByrnesPub.edges.add(new Edge<>(1300, davyByrnesPub, ecclesStreet));
    ecclesStreet.edges.add(new Edge<>(1300, ecclesStreet, davyByrnesPub));

    nationalLibrary.edges.add(new Edge<>(1600, nationalLibrary, ecclesStreet));
    ecclesStreet.edges.add(new Edge<>(1600, ecclesStreet, nationalLibrary));

    glasnevinCemetery.edges.add(new Edge<>(2700, glasnevinCemetery, ecclesStreet));
    ecclesStreet.edges.add(new Edge<>(2700, ecclesStreet, glasnevinCemetery));

    glasnevinCemetery.edges.add(new Edge<>(1800, glasnevinCemetery, finnegansHouse));
    finnegansHouse.edges.add(new Edge<>(1800, finnegansHouse, glasnevinCemetery));

    ecclesStreet.edges.add(new Edge<>(1400, ecclesStreet, finnegansHouse));
    finnegansHouse.edges.add(new Edge<>(1400, finnegansHouse, ecclesStreet));

    sandymountStrand.edges.add(new Edge<>(9000, sandymountStrand, finnegansHouse));
    finnegansHouse.edges.add(new Edge<>(9000, finnegansHouse, sandymountStrand));

    System.out.println("Distance from Finnegan's House to Sandymount Strand: " + distance( finnegansHouse, sandymountStrand));
  }

  public static <T> int distance(Vertex<T> start, Vertex<T> end) {
    // TODO: implement shortest‑path distance
    Map<Vertex<T>, Integer> distances = new HashMap<>();
    Queue<Edge<T>> queue = new PriorityQueue<>();
    Map<Vertex<T>, Vertex<T>> previous = new HashMap<>();

    queue.add(new Edge<>(0, start, start));

    while(!queue.isEmpty()){
      Edge<T> currentEdge = queue.poll();
      Vertex<T> currentVertex = currentEdge.endpoint;
      
      if(distances.containsKey(currentVertex)) continue;

      distances.put(currentVertex, currentEdge.weight);
      previous.put(currentVertex, currentEdge.start);

      for(Edge<T> neighbor : currentVertex.edges){
        int cumulativeDistance = currentEdge.weight + neighbor.weight;
        Edge<T> cumulativeEdge = new Edge<>(cumulativeDistance, currentVertex, neighbor.endpoint);
        queue.add(cumulativeEdge);
      }
    }

    if(!distances.containsKey(end)){
      throw new IllegalArgumentException("No path exists between the start and end vertices.");
    }
    List<T> path = new LinkedList<>();
    Vertex<T> current = end;
    while(current != start){
      path.addFirst(current.data);
      current = previous.get(current);
    }

    System.out.println("Shortest path from " + start.data + " to " + end.data + ": " + path);
    return distances.get(end);
  }
}
