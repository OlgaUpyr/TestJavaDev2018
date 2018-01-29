import java.util.ArrayList;
import java.util.List;

public class Edge {
    private String name;
    private int weight;
    private boolean visited;
    private List<Edge> neighbours;


    public Edge(){}

    public Edge(String name, int weight){
        this.name = name;
        this.weight = weight;
        this.visited = false;
        this.neighbours=new ArrayList<>();
    }

    public void addNeighbours(Edge neighbourEdge) {
        this.neighbours.add(neighbourEdge);
    }

    public String getName() {
        return name;
    }

    public int getWeight() {
        return weight;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        return getName() + ", " + getWeight();
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public List<Edge> getNeighbours() {
        return neighbours;
    }
}
