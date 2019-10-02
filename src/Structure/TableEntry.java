package Structure;

import java.util.ArrayList;

public class TableEntry {
    public int index;
    public double probability;
    public int[] binary;
    public int numOfParent;

    public TableEntry() {
    }

    public TableEntry(int index, double prob, int parentCount) {
        this.index = index;
        this.probability = prob;
        this.numOfParent = parentCount;
        this.binary = new int[parentCount];
        this.saveAsBinary(index);
    }

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
