package Structure;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * This class represents information of each node given in the network file
 */
public class Node {
    public String name;  // name of the node
    public int index; // index number of the node
    public double value; // value randomly assigned by prior sampling
    public ArrayList<Node> parents; // parents of the node
    public ArrayList<TableEntry> cpt; // conditional probability table of the node
    public String status; // status of the node such as t,f,?,-

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

    public ArrayList<Node> getParents() {
        ArrayList<Node> pars = new ArrayList<>();
        for(Node n : this.parents) {
            pars.add(n);
        }
        return pars;
    }

    public ArrayList<TableEntry> getCPT() {
        ArrayList<TableEntry> te = new ArrayList<TableEntry>();
        for(TableEntry t : this.cpt) {
            te.add(t);
        }
        return te;
    }

    public void setValue(double value) {
        this.value = value;
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

    public void setValue(int value) {
        this.value = value;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "\n{ name : " + this.name + ", index : " + this.index + " }";
    }
}
