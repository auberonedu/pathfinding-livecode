import java.util.ArrayList;
import java.util.List;

public class Vertex<T> {
  T data;
  List<Edge<T>> edges;

  public Vertex(T data, List<Edge<T>> edges) {
    this.data = data;
    this.edges = edges;
  }

  public Vertex(T data) {
    this(data, new ArrayList<>());
  }
}