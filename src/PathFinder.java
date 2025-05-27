import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

import com.sun.tools.javac.util.List;

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
    // HCE's House ↔ Phoenix Park (800m) --- the relation between the two locations to show that they share the same distance or weight
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
    System.out.println();
    System.out.println(distance(hcesHouse, finnegansHouse));
  }

  public static <T> int distance(Vertex<T> start, Vertex<T> end) {
    // TODO: implement shortest‑path distance
    // ** Use Dijkstra's algorithm of a Priority Queue

    Queue<Edge<T>> minQ = new PriorityQueue<>(); // a pq where it determines the shortest path after doing the logic from below
    Map<Vertex<T>, Integer> dist = new HashMap<>(); // keeps track of the shortest distance
    Map<Vertex<T>, Vertex<T>> prevs = new HashMap<>(); // keeps track of the previous node's information

    minQ.add(new Edge<>(0, start)); // starting point

    while(!minQ.isEmpty()) {
      // current pointer
      Edge<T> current = minQ.poll();

      // this (node/path) has been visited inside the HashMap
      if(dist.containsKey(current.endpoint)) continue;

      // this is marking that (node/path) visited
      dist.put(current.endpoint, current.weight);

      // Loop through all the neighbors and determine the shortest path
      for(Edge<T> neighbor : current.endpoint.edges) {
        if(!dist.containsKey(neighbor.endpoint)) {
          int newDistance = current.weight + neighbor.weight; // adds up the path's weight 
          Edge<T> newEdge = new Edge<>(newDistance, neighbor.endpoint); // save the newDistance's weight into a new Edge variable, with the current neighbor's endpoint
          minQ.add(newEdge); // then add it to the Priority Queue where the PQ will always add the shortest path on top.
          if(!prevs.containsKey(neighbor.endpoint)) {
            prevs.put(neighbor.endpoint, current.endpoint); // this is adding the information from the previous endpoint/node 
          }
        }
      }
    }

    Vertex<T> current = end;

    //List<T> path = new ArrayList<>(); // alternative way
    
    while(current != null) {
      System.out.println(current.data);
      current = prevs.get(current);
    }

    // Alternative Way of doing 
    // while(current != null) {
    //   path.add(current.data);
    //   // System.out.println(current.data);
    //   current = prevs.get(current);
    // }

    // System.out.println(path.reversed());

    return dist.get(end);
  }
}
