package Structure;

import java.util.ArrayList;

public class Node {
    public String name;
    public int index;
    public ArrayList<Node> parents;
    public ArrayList<TableEntry> cpt;

    public Node() {
        this.name = "";
        this.index = 0;
        this.parents = new ArrayList<>();
        this.cpt = new ArrayList<TableEntry>();
    }

    public Node(String name, int index) {
        this.name = name;
        this.index = index;
        this.parents = new ArrayList<>();
        this.cpt = new ArrayList<TableEntry>();
    }

    public void setParents(ArrayList<Node> parents) {
        for(Node parent: parents) {
            this.parents.add(parent);
        }
    }
    public void addParents(Node parent) {
        this.parents.add(parent);
    }

    public void setCPT(ArrayList<TableEntry> teList) {
        for(TableEntry te : teList) {
            this.cpt.add(te);
        }
    }
    public void addParents(TableEntry te) {
        this.cpt.add(te);
    }

    @Override
    public String toString() {
        return "\n{ name : " + this.name + ", index : " + this.index + " }";
    }
}
