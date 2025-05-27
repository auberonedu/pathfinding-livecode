import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

public class PathFinder {

  public static void main(String[] args) {
    // 1) Create all vertices
    Vertex<String> hcesHouse = new Vertex<>("HCE's House");
    Vertex<String> phoenixPark = new Vertex<>("Phoenix Park");
    Vertex<String> sandymountStrand = new Vertex<>("Sandymount Strand");
    Vertex<String> towerAtSandycove = new Vertex<>("Tower at Sandycove");
    Vertex<String> davyByrnesPub = new Vertex<>("Davy Byrne's Pub");
    Vertex<String> nationalLibrary = new Vertex<>("National Library");
    Vertex<String> glasnevinCemetery = new Vertex<>("Glasnevin Cemetery");
    Vertex<String> ecclesStreet = new Vertex<>("Eccles Street");
    Vertex<String> finnegansHouse = new Vertex<>("Finnegan's House");

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

    System.out.println(distance(hcesHouse, finnegansHouse));
  }

  public static <T> int distance(Vertex<T> start, Vertex<T> end) {
    // TODO: implement shortest‑path distance

    // the queue will be based on smallest weights
    Queue<Edge<T>> minQ = new PriorityQueue<>();
    // shortest d from out start locale to end
    // key is the other location, and the value is how far away it is
    Map<Vertex<T>, Integer> dists = new HashMap<>();

    Map<Vertex<T>, Vertex<T>> prevs = new HashMap<>();

    // Our map is going to contain the distance between the start point AND EVERY
    // SINGLE NODE INSIDE OF THAT GRAPH (by adding weight)
    // graph structure
    // A: 2231
    // B: 12332
    // C: 12

    // so to get shortest distance, put in the dists.get() and you will be able the
    // shortest distance. The map is storing the shortest distance from the starting
    // point to EVERY single node in the graph...

    // (cause thats how get works with this map since were storing the locale as a
    // key, doing get will retrieve that keys shortest distance with its
    // corresponding value weight--- which reps the distance from the start and the
    // end)

    // adding something so queue has something to start with
    // starting at the current locale
    minQ.add(new Edge<>(0, start));

    // keep searching until queue is empty
    while (!minQ.isEmpty()) {
      // the meat of the proj.
      Edge<T> current = minQ.poll();
      // instead of visited since hashmaps have unique keys we can use that
      // if we already checked this node, than go ahead skip over. Dont calculate it
      // agaain
      if (dists.containsKey(current.endpoint)) {
        continue;
      }

      // what happens when things start
      // first time seeing it
      // first time you see something, it will always be the shortest one
      dists.put(current.endpoint, current.weight);

      for (Edge<T> neighbor : current.endpoint.edges) {
        // if you haven't seen the neighbor before
        if (!dists.containsKey(neighbor.endpoint)) {
          // BUT i want to add it with the culumative distance to how long it takes to
          // have gotten here up until this point
          int newDistance = current.weight + neighbor.weight;
          // make a new edge while aculamatively adding the distance and include the
          // neighborendpoint as the vertex
          Edge<T> newEdge = new Edge<>(newDistance, neighbor.endpoint);
          minQ.add(newEdge);

          //adding prevs nodes
          if (!prevs.containsKey(neighbor.endpoint)) {
            prevs.put(neighbor.endpoint, current.endpoint);
          }
        }
      }

    }

    Vertex<T> current = end;
    List<T> path = new LinkedList<>();
//where do we will it to iterate
    while(current != null){
       path.addFirst(current.data);
      current = prevs.get(current);
      
    }
    System.out.println(path.toString());
    // return shortest weight for the end
    return dists.get(end);
  }
}
