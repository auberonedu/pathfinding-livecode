import java.util.ArrayList;
import java.util.List;

public class Vertex<T> {
  T data;
  //instead of neighbors - now we have a list of edges
  // these edges have a weight for that connection and they have a endpoint onto the vertext it is coming from 
  List<Edge<T>> edges;

  public Vertex(T data, List<Edge<T>> edges) {
    this.data = data;
    this.edges = edges;
  }

  public Vertex(T data) {
    this(data, new ArrayList<>());
  }
}