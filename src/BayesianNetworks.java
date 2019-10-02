//Robert Dutile (radutile) & Myo Min Thant (mmthant)
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Random;

import Structure.Event;
import Structure.Node;
import Structure.TableEntry;

public class BayesianNetworks {

	public static ArrayList<Node> nodeList = new ArrayList<>();

    public static void main (String [] args) {
    	// input argument
    	if(args.length < 1) {
    		System.out.println("Missing network file argument");
    		return;
    	} else if(args.length < 2) {
    		System.out.println("Missing query file argument");
		} else if(args.length < 3) {
    		System.out.println("Missing number of samples to be generated");
		}
    	// define variables
    	String inputFileName = args[0];
    	String queryFileName = "";
    	int num_samples = 0;

    	if(args[1] != null) queryFileName = args[1];
    	if(args[2] != null) num_samples = Integer.parseInt(args[2]);
    	
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
				int parCount = 0;
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
					parCount++;
				}
            	
            	// read cpt
            	for(i = lastStop; i < line.length(); i++) {
            		if(line.charAt(i) == ']') {
            			String cptHolder = line.substring(lastStop, i);
            			String[] cptArr = cptHolder.split(" ");
            			
            			for(int k = 0; k < cptArr.length; k++) {
							double prob = Double.parseDouble(cptArr[k]);
            				probabilities.add(prob);
							TableEntry te = new TableEntry(k, prob, parCount);
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

            	nodeList.add(newNode);
            	System.out.println();


            	
            	//Construct Node here:
            	
            }
            
    	} catch(Exception e) {
    		System.out.println("Exception thrown: error reading file");
    		e.printStackTrace();
    		return;
    	}

    	// read query file
		try{
			File file = new File(queryFileName);
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			String line = "";
			ArrayList<String> queries = new ArrayList<>();
			while((line = br.readLine()) != null) {
				String[] qs = line.split(",");
				for (String q : qs) {
					queries.add(q);
				}
			}
			for(int k = 0 ; k < nodeList.size() ; k++) {
				nodeList.get(k).setStatus(queries.get(k));
			}

		} catch(Exception e) {
			System.out.println("Exception thrown: error reading query file");
			e.printStackTrace();
			return;
		}

    }

    // get the node index from node name
    public static int getNodeNum(String nodeName) {
    	if(nodeName == "" || nodeName.length() < 1) return -1;
    	return ((int) nodeName.charAt(4)) - 48;
	}

	// random number generator
	// generate a double value between 0 and 1.
	public static double randNodeValue() {

		double randomValue = Math.random();
		System.out.println(randomValue);
		return randomValue;
	}
	
	//generates random samples for the boolean network
	public Event priorSample(ArrayList<Node> nodes) {
		Event event = new Event();
		
		for(int i = 0; i < nodes.size(); i++) {
			Node checkNode = nodes.get(i);
			
			event = priorSampleHelper(event, checkNode);
		}
		
		return event;
	}
	
	//recursive helper for priorSample()
	public Event priorSampleHelper(Event event, Node node) {
		
		if(event.values.containsKey(node.name)) {
			return event;
		}
		
		double prob;
		
		if(node.parents.size() > 0) {
			ArrayList<Boolean> boolList = new ArrayList<Boolean>();
			
			for(int j = 0; j < node.parents.size(); j++) {
				Node currentParent = node.parents.get(j);
				if(event.values.containsKey(currentParent.name)) {
					continue;
				} else {
					event = priorSampleHelper(event, currentParent);
				}
				boolList.add(event.values.get(currentParent.name));
			}
			
			//assign probability
			//place holder code, need to talk to myo about binary
			prob = node.cpt.get(0).probability;
		} else {
			//get probability of node = true
			prob = node.cpt.get(0).probability;
		}
		
		if(randNodeValue() <= prob) {
			event.setValue(node.name, Boolean.valueOf(true));
		} else {
			event.setValue(node.name, Boolean.valueOf(false));
		}
		
		return event;
	}


}
