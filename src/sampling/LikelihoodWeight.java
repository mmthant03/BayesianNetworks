package sampling;

import Structure.Event;
import Structure.Node;
import Structure.TableEntry;
import java.util.ArrayList;
import java.util.Arrays;

public class LikelihoodWeight {

    public ArrayList<Node> nodeList = new ArrayList<Node>();
    public String queryVarName;

    // constructor for LikelihoodWeight class
    // it receives all the node information
    public LikelihoodWeight(ArrayList<Node> nodeList) {
        for (Node node : nodeList) {
            this.nodeList.add(node);
            if(node.status.contains("?")) {
                queryVarName = node.name;
            }
        }
    }

    // Likelihood Weighting Algorithm
    // For each sample, generate a weighted event
    // For each event, if query variable is found 't',
    // then increment vector[1] by weight
    // else increment vector[0] by weight
    public double likelihoodWeighting(int sampleSize) {
        // define local variables
        double[] countVector = new double[2];
        countVector[0] = 0;
        countVector[1] = 0;

        // iterate over sample size
        for(int j = 0; j < sampleSize; j++) {
            Event x = weightedSample(this.nodeList);
            // Error check for if query variable is missing in the event
            if(x.values.get(queryVarName) == null){
                System.out.println("ERROR : Query variable is not inside the event");
            }
            // if query variable comes out to be 't'
            else if(x.values.get(queryVarName)) {
                countVector[1] += x.weight;
            }
            // else if query variable comes out to be 'f'
            else {
                countVector[0] += x.weight;
            }
        }

        // normalize it and then return the final probability
        return normalize(countVector);
    }

    // Generate a weighted sample for likelihood weighting
    public Event weightedSample(ArrayList<Node> nodes) {
        // create new event of n samples
        Event x = new Event();
        // set the initial weight to be 1
        double weight = 1.0;
        x.setWeight(weight);

        // initialize each node values with random double between 1.0 and 0.0
        for(Node node : nodes ) {
            node.setValue(randNodeValue());
        }

        // for each node check for evidence
        for (Node node : nodes) {
            // if a node is evidence variable find prob of that node given its parents
            if(node.status.contains("t")) {
                x.setValue(node.name, Boolean.valueOf(true));
                weight = weight * findProb(x, node, nodes);
                x.setWeight(weight);
            }
            else if (node.status.contains("f")) {
                x.setValue(node.name, Boolean.valueOf(false));
                weight = weight * findProb(x, node, nodes);
                x.setWeight(weight);
            }
            // else sample the node to be 't' or 'f'
            else {
                boolean status;
                double newProb;
                if(x.values.containsKey(node.name)) { // this means it is already sample
                    status = x.values.get(node.name);
                } else { // if not yet, sample it to t or f
                    newProb = findProb(x, node, nodes);
                    status = node.value <= newProb;
                    x.setValue(node.name, Boolean.valueOf(status));
                }

            }
        }
        return x;

    }

    // Find the probability of given node
    public double findProb(Event x, Node node, ArrayList<Node> nodes) {
        ArrayList<Node> parents = node.getParents();
        int[] binary = new int[parents.size()];
        double prob = 0.0;

        // for each parent, build up an array in the form of binary representation
        int i = parents.size()-1;
        for(Node parent : parents) {
            for(Node curr : nodes) {
                if(curr.index != parent.index) {
                    continue;
                }
                else {
                    parent = curr;
                }

                // node is evidence and 't'
                if(parent.status.contains("t")) {
                    binary[i] = 1;
                    i--;
                }
                // node is evidence and 'f'
                else if(parent.status.contains("f")) {
                    binary[i] = 0;
                    i--;
                }
                // node is not evidence so require sampling
                else if(parent.status.contains("-")) {
                    boolean newStatus;
                    double newProb;
                    if(x.values.containsKey(parent.name)) {
                        newStatus = x.values.get(parent.name);
                    } else {
                        newProb = findProb(x, parent, nodes);
                        newStatus = parent.value <= newProb;
                        x.setValue(parent.name, Boolean.valueOf(newStatus));
                    }
                    binary[i] = (newStatus) ? 1 : 0;
                    i--;
                }
            }
        }

        // check it with conditional probability table
        for(TableEntry te : node.getCPT()) {
            if((te.binary.length == 0) || Arrays.equals(te.binary, binary)) {
                prob = te.probability;
            }
        }
        return prob;
    }

    // random number generator
    // generate a double value between 0 and 1.
    public static double randNodeValue() {

        double randomValue = Math.random();
        return randomValue;
    }

    // helper function that normalizes the count vector of rejectionSampling
    // returns probability
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
