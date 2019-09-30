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
        System.out.println(b);
        if (num == 0) {
            for(int i = 0; i<binary.length; i++) {
                this.binary[i] = 0;
            }
        }
        int index = 2;
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

    @Override
    public String toString() {
        return "{ CPT Index : " + this.index +
                ", Probability : " + this.probability +
                ", Binary : " + this.binary[0]+""+this.binary[1]+""+this.binary[2]+
                " }\n";
    }



}
