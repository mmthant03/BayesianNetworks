package Structure;

import java.util.ArrayList;

/**
 * This class represents each entry inside the conditional probability table
 */
public class TableEntry {
    public int index; // index of an entry of CPT table
    public double probability; // probability of that entry
    public int[] binary; // binary representation of that entry
    public int numOfParent; // num of parents of the node

    public TableEntry() {
    }

    public TableEntry(int index, double prob, int parentCount) {
        this.index = index;
        this.probability = prob;
        this.numOfParent = parentCount;
        this.binary = new int[parentCount];
        this.saveAsBinary(index);
    }

    // this function will transform index to binary representation
    public void saveAsBinary(int num) {
        String b = Integer.toBinaryString(num);
        if (num == 0) {
            for(int i = 0; i<binary.length; i++) {
                this.binary[i] = 0;
            }
        }
        int index = numOfParent-1;
        if (num >0 && num <8) {
            char[] cs = b.toCharArray();
            for (int i = cs.length-1; i >= 0 ; i--) {
                int bin = ((int) cs[i]) - 48;
                this.binary[index] = bin;
                index--;
                if(index < 0) break;
            }
        }
    }

    // for debugging purpose, this will return a string of binary representation
    public String binString() {
        String b = "";
        for (int i : this.binary) {
            b += i;
        }
        return b;
    }

    @Override
    public String toString() {
        return "{ CPT Index : " + this.index +
                ", Probability : " + this.probability +
                ", Binary : " + binString() +
                " }\n";
    }



}
