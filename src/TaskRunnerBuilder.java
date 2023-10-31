import java.util.Map;

public class TaskRunnerBuilder {
	public TaskRunner taskRunner = new TaskRunner();

	TaskRunnerBuilder addTask(Task task, Options options) {
		this.taskRunner.add(task, options);
		return this;
	};

	public TaskRunner build() {
		return taskRunner;
	};

}
