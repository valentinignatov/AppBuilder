import java.util.HashMap;
import java.util.Map;

public class Options {
	
	private Map<String, Object> map = new HashMap<>();
	
	Options set(String key, Object value) {
		map.put(key, value);
		return this;
	}
	
	Object get(String key) {
		return map.get(key);
	}
}
