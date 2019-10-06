package Structure;

import java.util.HashMap;

/**
 * This class is to track each event perform during sampling
 */
public class Event {
	
	public HashMap<String, Boolean> values;
	public double weight;

	public Event() {
		this.values = new HashMap<>();
		this.weight = 0.0;
	}
	
	public void setValue(String name, boolean value) {
		values.put(name, Boolean.valueOf(value));
	}
	public void setWeight(double weight) {this.weight = weight; }

}
