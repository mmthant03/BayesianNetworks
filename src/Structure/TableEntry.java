package Structure;

import java.util.ArrayList;

public class TableEntry {
    public int index;
    public double probability;
    public int[] binary = new int[3];
    public int bin;

    public TableEntry() {
    }


    public TableEntry(int index, double prob) {
        this.index = index;
        this.probability = prob;
        this.saveAsBinary(index);
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

    @Override
    public String toString() {
        return "{ CPT Index : " + this.index + ", Probability : " + this.probability + " }\n";
    }



}
