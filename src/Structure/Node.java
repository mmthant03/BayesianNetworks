package Structure;

public class Node {
    public int name;
    public int edges;
    public Table cpt;

    public Node(int name) {
        this.name = name;
    }

    public void setCpt(Table table) {
        this.cpt = table;
    }
}
