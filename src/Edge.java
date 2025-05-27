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
