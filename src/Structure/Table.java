package Structure;

import java.util.ArrayList;

public class Table {
    public int index;
    public ArrayList<Double> probabilities;
    public int[] binary = new int[3];
    public int bin;
    public ArrayList<Node> parents;

    public Table(int index, ArrayList<Node> parents, ArrayList<Double> values) {
        this.index = index;
        this.saveAsBinary(index);
        this.parents = new ArrayList<Node>();
        for(Node parent: parents) {
            this.parents.add(parent);
        }
        for(double value: values) {
            this.probabilities.add(value);
        }
    }

    public void saveAsBinary(int num) {
        String b = Integer.toBinaryString(num);
        if (num == 0) {
            for(int i = 0; i<binary.length; i++) {
                this.binary[i] = 0;
            }
        }
        int index = 2;
        if (num >0 && num <8) {
            for (char c : b.toCharArray()) {
                int bin = (int) c;
                this.binary[index] = bin;
                index--;
                if(index < 0) break;
            }
        }
    }



}
