package Structure;

import java.util.HashMap;

public class Event {
	
	public HashMap<String, Boolean> values;

	public Event() {
		this.values = new HashMap<>();
	}
	
	public void setValue(String name, boolean value) {
		values.put(name, Boolean.valueOf(value));
	}

}
