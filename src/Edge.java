public class Edge<T> implements Comparable<Edge<T>> {
  public int weight;
  public Vertex<T> endpoint;

  public Edge(int weight, Vertex<T> endpoint) {
    this.weight = weight;
    this.endpoint = endpoint;
  }

  @Override
  public int compareTo(Edge<T> other) {
    return Integer.compare(weight, other.weight);
  }
}

//edge corresponds to the line and between two nodes in the image ![Graph of Dublin](images/graph.png)

// so we have the place where it ends (endpoint) and then we have a weight for the direction

//theirs also a compare that compares things based on weight