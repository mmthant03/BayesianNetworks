//Robert Dutile (radutile) & Myo Min Thant (mmthant)
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

public class BayesianNetworks {
    public static void main (String [] args) {
        
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
            	ArrayList<String> probabilities = new ArrayList<String>();
            	
            	int lastStop = 0;
            	
            	//read node name
            	for(i = 0; i < line.length(); i++) {
            		if(line.charAt(i) == ':') {
            			name = line.substring(lastStop, i);
            			lastStop = i + 3;
            			break;
            		}
            	}
            	
            	//read node parents
            	for(i = lastStop; i < line.length(); i++) {
            		if(line.charAt(i) == ']') {
            			String parHolder = line.substring(lastStop, i);
            			String[] holdArr = parHolder.split(", ");
            			
            			for(int j = 0; j < holdArr.length; j++) {
            				parents.add(holdArr[j]);
            			}
            			
            			lastStop = i + 3;
            			break;
            		}
            	}
            	
            	// read cpt
            	for(i = lastStop; i < line.length(); i++) {
            		if(line.charAt(i) == ']') {
            			String cptHolder = line.substring(lastStop, i);
            			String[] cptArr = cptHolder.split(", ");
            			
            			for(int k = 0; k < cptArr.length; k++) {
            				probabilities.add(cptArr[k]);
            			}
            			
            			break;
            		}
            	}
            	
            	//print trace;
            	System.out.println("Node Name: " + name);
            	System.out.println("Node Parents: " + parents);
            	System.out.println("Node CPT Values: " + probabilities);
            	System.out.println();
            	
            	//Construct Node here:
            	
            }
            
    	} catch(Exception e) {
    		System.out.println("Exception thrown: error reading file");
    		return;
    	}
    	
    }

}
