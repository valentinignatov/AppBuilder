import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

public class TaskRunner {

	private LinkedHashMap<Task, Options> commandsMap = new LinkedHashMap<>();

	TaskRunner() {};

	void start() throws TaskFailureException {
		//I need to use ordered map because the commands are executing chaotically
		for (Entry<Task, Options> commandsFromMap: commandsMap.entrySet()) {
			commandsFromMap.getKey().execute(commandsFromMap.getValue());
		}
	}

	Map<Map<Task, Options>, Result> getLog() {
		return null;
	}

	public LinkedHashMap add(Task task, Options options) {
		this.commandsMap.put(task, options);
		return this.commandsMap;
	}
}
