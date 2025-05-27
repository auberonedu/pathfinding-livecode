import java.util.*;

public class PathFinder {

  public static void main(String[] args) {
    // 1) Create all vertices
    Vertex<String> hcesHouse        = new Vertex<>("HCE's House");
    Vertex<String> phoenixPark      = new Vertex<>("Phoenix Park");
    Vertex<String> sandymountStrand = new Vertex<>("Sandymount Strand");
    Vertex<String> towerAtSandycove = new Vertex<>("Tower at Sandycove");
    Vertex<String> davyByrnesPub    = new Vertex<>("Davy Byrne's Pub");
    Vertex<String> nationalLibrary  = new Vertex<>("National Library");
    Vertex<String> glasnevinCemetery= new Vertex<>("Glasnevin Cemetery");
    Vertex<String> ecclesStreet     = new Vertex<>("Eccles Street");
    Vertex<String> finnegansHouse   = new Vertex<>("Finnegan's House");

    // 2) Wire up edges (bidirectional)
    // HCE's House ↔ Phoenix Park (800m)
    hcesHouse.edges.add(new Edge<>(800, phoenixPark));
    phoenixPark.edges.add(new Edge<>(800, hcesHouse));

    // HCE's House ↔ Sandymount Strand (4200m)
    hcesHouse.edges.add(new Edge<>(4200, sandymountStrand));
    sandymountStrand.edges.add(new Edge<>(4200, hcesHouse));

    // Phoenix Park ↔ Davy Byrne's Pub (3100m)
    phoenixPark.edges.add(new Edge<>(3100, davyByrnesPub));
    davyByrnesPub.edges.add(new Edge<>(3100, phoenixPark));

    // Phoenix Park ↔ Glasnevin Cemetery (2400m)
    phoenixPark.edges.add(new Edge<>(2400, glasnevinCemetery));
    glasnevinCemetery.edges.add(new Edge<>(2400, phoenixPark));

    // Sandymount Strand ↔ Tower at Sandycove (1800m)
    sandymountStrand.edges.add(new Edge<>(1800, towerAtSandycove));
    towerAtSandycove.edges.add(new Edge<>(1800, sandymountStrand));

    // Sandymount Strand ↔ National Library (3300m)
    sandymountStrand.edges.add(new Edge<>(3300, nationalLibrary));
    nationalLibrary.edges.add(new Edge<>(3300, sandymountStrand));

    // Tower at Sandycove ↔ Davy Byrne's Pub (6000m)
    towerAtSandycove.edges.add(new Edge<>(6000, davyByrnesPub));
    davyByrnesPub.edges.add(new Edge<>(6000, towerAtSandycove));

    // Davy Byrne's Pub ↔ National Library (600m)
    davyByrnesPub.edges.add(new Edge<>(600, nationalLibrary));
    nationalLibrary.edges.add(new Edge<>(600, davyByrnesPub));

    // Davy Byrne's Pub ↔ Eccles Street (1300m)
    davyByrnesPub.edges.add(new Edge<>(1300, ecclesStreet));
    ecclesStreet.edges.add(new Edge<>(1300, davyByrnesPub));

    // National Library ↔ Eccles Street (1600m)
    nationalLibrary.edges.add(new Edge<>(1600, ecclesStreet));
    ecclesStreet.edges.add(new Edge<>(1600, nationalLibrary));

    // Glasnevin Cemetery ↔ Eccles Street (2700m)
    glasnevinCemetery.edges.add(new Edge<>(2700, ecclesStreet));
    ecclesStreet.edges.add(new Edge<>(2700, glasnevinCemetery));

    // Glasnevin Cemetery ↔ Finnegan's House (1800m)
    glasnevinCemetery.edges.add(new Edge<>(1800, finnegansHouse));
    finnegansHouse.edges.add(new Edge<>(1800, glasnevinCemetery));

    // Eccles Street ↔ Finnegan's House (1400m)
    ecclesStreet.edges.add(new Edge<>(1400, finnegansHouse));
    finnegansHouse.edges.add(new Edge<>(1400, ecclesStreet));

    // Sandymount Strand ↔ Finnegan's House (9000m detour)
    sandymountStrand.edges.add(new Edge<>(9000, finnegansHouse));
    finnegansHouse.edges.add(new Edge<>(9000, sandymountStrand));

    //test this baby out!
    System.out.println();
    System.out.println(distance(hcesHouse, finnegansHouse));
    System.out.println();
  }

  public static <T> int distance(Vertex<T> start, Vertex<T> end) {
    // TODO: implement shortest‑path distance
    // use BFS to find maybe top 5 shortest paths by nodes, then use the compareTo method to look at total weights per path; 
    Queue<Edge<T>> minQ = new PriorityQueue<>();
    //'visited' Map
    Map<Vertex<T>, Integer> dists = new HashMap<>();
    //to help print out shortest path
    Map<Vertex<T>, Vertex<T>> prevs = new HashMap<>();

    //start PQ at initial node with weight of zero
    minQ.add(new Edge<>(0, start));


    while(!minQ.isEmpty()) {
      Edge<T> current = minQ.poll();

      //if visited, continue
      if(dists.containsKey(current.endpoint)) {
        continue;
      }

      //haven't been here yet, add key/value to Map
      dists.put(current.endpoint, current.weight);
      //fill up PQ
      //loop over adjacent edges
      for (Edge<T> neighbor : current.endpoint.edges) {
        if(!dists.containsKey(neighbor.endpoint)) {
          int newDistance = current.weight + neighbor.weight;
          Edge<T> cumEdge = new Edge<>(newDistance, neighbor.endpoint);
          minQ.add(cumEdge);

          if(!prevs.containsKey(neighbor.endpoint)) {
            prevs.put(neighbor.endpoint, current.endpoint);
          }
        }
      }
    }
    //print linked list of shortest path
    Vertex<T> current = end;
    List<T> path = new LinkedList<>();

    while(current != null) {
      path.addFirst(current.data);
      current = prevs.get(current);
    }
    //print out path in reverse
    System.out.println(path);
    return dists.get(end);
  }
}
