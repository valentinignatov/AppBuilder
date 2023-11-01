
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.*;

public class Driver {

	private static final String MAIN_CLASS = "HelloWorld";

	public static void main(String[] args) throws IOException, InterruptedException {
		TaskRunner appBuildTaskRunner = new TaskRunnerBuilder()
				.addTask(new CompileTask(), new Options().set("command", "javac").set("file", "HelloWorld.java"))
				.addTask(new RunTask(), new Options().set("command", "java").set("file", MAIN_CLASS))
				.addTask(new PackageTask(), new Options()
						.set("command", "jar")
						.set("commandTwo", "cfe")
						.set("jarFileName", "HelloWorld.jar")
						.set("file", MAIN_CLASS)
						.set("compiledJavaFile", "HelloWorld.class"))
				.build();

		try {
			appBuildTaskRunner.start();
		} catch (TaskFailureException e) {
			e.printStackTrace();
		}
		
	}
}
