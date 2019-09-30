package Structure;

import java.util.ArrayList;

public class Table {
    public ArrayList<TableEntry> entries;

    public Table(ArrayList<TableEntry> entries) {
        for (TableEntry entry : entries) {
            this.entries.add(entry);
        }
    }
}
