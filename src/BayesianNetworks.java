//Robert Dutile (radutile) & Myo Min Thant (mmthant)
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import Structure.Node;
import Structure.Table;
import Structure.TableEntry;

public class BayesianNetworks {

	public static ArrayList<Node> nodeList;

    public static void main (String [] args) {
    	// input argument
    	if(args.length < 1) {
    		System.out.println("Missing input filename argument");
    		return;
    	}
    	
    	// define variables
    	String inputFileName = args[0];
    	
    	//read file
    	try {
            File file = new File(inputFileName);
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);

            String line;
            int i = 0;
            
            while((line = br.readLine()) != null) {
            	
            	String name = "";
            	ArrayList<String> parents = new ArrayList<String>();
            	ArrayList<Double> probabilities = new ArrayList<>();
            	ArrayList<TableEntry> entryList = new ArrayList<TableEntry>();
				Node newNode = new Node();
            	int lastStop = 0;
            	
            	//read node name
            	for(i = 0; i < line.length(); i++) {
            		if(line.charAt(i) == ':') {
            			name = line.substring(lastStop, i);
            			int nodeIndex = getNodeNum(name);
            			newNode = new Node(name, nodeIndex);
            			lastStop = i + 3;
            			break;
            		}
            	}
            	
            	//read node parents
            	for(i = lastStop; i < line.length(); i++) {
            		if(line.charAt(i) == ']') {
            			String parHolder = line.substring(lastStop, i);
						String[] holdArr = parHolder.split(" ");
            			
            			for(int j = 0; j < holdArr.length; j++) {
            				parents.add(holdArr[j]);
            			}
            			lastStop = i + 3;
            			break;
            		}
            	}
				for(String parent : parents) {
					int parentIndex = getNodeNum(parent);
					if(parentIndex == -1) break;
					Node n = new Node(parent, parentIndex);
					newNode.addParents(n);
				}
            	
            	// read cpt
            	for(i = lastStop; i < line.length(); i++) {
            		if(line.charAt(i) == ']') {
            			String cptHolder = line.substring(lastStop, i);
            			String[] cptArr = cptHolder.split(" ");
            			
            			for(int k = 0; k < cptArr.length; k++) {
							double prob = Double.parseDouble(cptArr[k]);
            				probabilities.add(prob);
							TableEntry te = new TableEntry(k, prob);
							entryList.add(te);
            			}
            			break;
            		}
            	}
            	newNode.setCPT(entryList);
            	
            	//print trace;
            	System.out.println("Node Name: " + newNode.name);
            	System.out.println("Node index: " + getNodeNum(name));
            	System.out.println("Node Parents: " + newNode.parents);
				System.out.println("Node CPT Values: ");
            	for(TableEntry te : newNode.cpt) {
					System.out.print(te);
				}

            	System.out.println();


            	
            	//Construct Node here:
            	
            }
            
    	} catch(Exception e) {
    		System.out.println("Exception thrown: error reading file");
    		e.printStackTrace();
    		return;
    	}

    }

    public static int getNodeNum(String nodeName) {
    	if(nodeName == "" || nodeName.length() < 1) return -1;
    	return ((int) nodeName.charAt(4)) - 48;
	}

}
