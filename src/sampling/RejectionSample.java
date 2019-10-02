package sampling;

import java.util.ArrayList;

import Structure.Event;
import Structure.Node;
import Structure.TableEntry;

public class RejectionSample {
	
	ArrayList<Node> nodeList = new ArrayList<Node>();
	String queryVarName;

	public RejectionSample(ArrayList<Node> nodeList) {
		this.nodeList = nodeList;
		
		for(int i = 0; i < nodeList.size(); i++) {
			if(nodeList.get(i).status.contains("?")) {
				queryVarName = nodeList.get(i).name;
			}
		}
	}
	
	//rejection sampling function
	public double rejectionSampling(int sampleSize) {
		//define local variables
		double[] countVector = new double[2];
		countVector[0] = 0;
		countVector[1] = 0;
		boolean isConsistent = true;
		
		
		for(int j = 0; j < sampleSize; j++) {
			Event x = priorSample(nodeList);
			isConsistent = true;
			
			//check if sample is consistent with evidence
			for(int k = 0; k < nodeList.size(); k++) {
				Node checkNode = nodeList.get(k);
				String checkString = checkNode.status;
				if((checkString.contains("?")) || (checkString.contains("-"))) {
					continue;
				} else if(checkString.contains("t") && !(x.values.get(checkNode.name))) {
					isConsistent = false;
					break;
				} else if(checkString.contains("f") && (x.values.get(checkNode.name))) {
					isConsistent = false;
					break;
				}
			}
			//add to counter
			if(isConsistent) {
				if(x.values.get(queryVarName)) {
					countVector[1] = countVector[1] + 1;
				} else {
					countVector[0] = countVector[0] + 1;
				}
			}
		}
		
		return normalize(countVector);
		
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
			String cptCodeString = "";
			for(int l = 0; l < boolList.size(); l++) {
				if(boolList.get(l)) {
					cptCodeString = "1" + cptCodeString;
				} else {
					cptCodeString = "0" + cptCodeString;
				}
			}
			int cptIndex = Integer.parseInt(cptCodeString, 2);
			prob = node.cpt.get(cptIndex).probability;
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
	
	//helper function that normalizes the count vector of rejectionSampling
	//returns probability
	public double normalize(double[] vector) {
		if(vector.length != 2) {
			return -1;
		}
		
		double scale0 = vector[0] * vector[0];
		double scale1 = vector[1] * vector[1];
		
		double magnitude = Math.sqrt(scale0 + scale1);
		
		return vector[1] / magnitude;
	}

}
