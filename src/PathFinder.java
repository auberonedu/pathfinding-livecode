import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

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

    System.out.println();
    System.out.println();
    System.out.println(distance(hcesHouse, finnegansHouse));
  }

  public static <T> int distance(Vertex<T> start, Vertex<T> end) {

    // For a priority que need to keep track of vertices we are visiting next, but need to sort them in which is closest
    Queue<Edge<T>> minQ = new PriorityQueue<>();

    // Map is used to keep track of distance from start location to every other location. Key is the next node, value is the distance
    // The keys of a map are unique, so this can be used as our visited. If we haven't calculated the distanced of a node yet, we haven't visited it
    Map<Vertex<T>, Integer> distances = new HashMap<>();

    Map<Vertex<T>, Vertex<T>> previous = new HashMap<>();

    // Adding the starting point to the queue. Has a weight of 0 since the distance to itself is 0
    minQ.add(new Edge<>(0, start));

    // Do a BFS now to see connected vertices
    while(!minQ.isEmpty()) {

      // Removing value at the front of the queue and saving it to a variable
      Edge<T> current = minQ.poll();

      // If the map has already calculated distance, skip over it
      if (distances.containsKey(current.endpoint)) {
        continue;
      }

      // Adding the current distance to the map
      distances.put(current.endpoint, current.weight);

      for (Edge<T> neighbor : current.endpoint.edges) {
        // If the map doesn't contain the weight path to a node
        if(!distances.containsKey(neighbor.endpoint)) {

          int newDistance = current.weight + neighbor.weight;
          Edge<T> newEdge = new Edge<>(newDistance, neighbor.endpoint);
          minQ.add(newEdge);

          // If the neighbor hasn't said what their previous node is
          if(!previous.containsKey(neighbor.endpoint)) {
            // Add the node was come from as the value
            previous.put(neighbor.endpoint, current.endpoint);
          }
        }
      }
    }

    Vertex<T> current = end;

    List<T> path = new ArrayList<>();

    while(current != null) {
      path.addFirst(current.data);
      // System.out.println(current.data);
      current = previous.get(current);
    }

    System.out.println(path.reversed());

    return distances.get(end);
  }
}
