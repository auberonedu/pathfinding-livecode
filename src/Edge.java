public class Edge<T> {
  public int weight;
  public Vertex<T> endpoint;

  public Edge(int weight, Vertex<T> endpoint) {
    this.weight = weight;
    this.endpoint = endpoint;
  }
}
