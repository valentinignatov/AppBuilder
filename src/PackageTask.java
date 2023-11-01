import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public class PackageTask implements Task {
	private static final String MAIN_CLASS = "HelloWorld";
	private static final String FILE_NAME = new String("HelloWorld.java");
	private static final String RESOURCES_DIRECTORY = "src";
	private static final String EXPECTED_OUTPUT = "Hello World!";

	@Override
	public Result execute(Options options) {
		String compiledJavaFile = FILE_NAME.replace(".java", ".class");
        String jarFileName = FILE_NAME.replace(".java", ".jar");
        ProcessBuilder jarProcessBuilder = new ProcessBuilder(options.get("command"), 
        		options.get("commandTwo"), 
        		options.get("jarFileName"), 
        		options.get("file"), 
        		options.get("compiledJavaFile"));
        jarProcessBuilder.directory(new File(RESOURCES_DIRECTORY));

        System.out.println("");
        
        Process jarProcess = null;
        int exitCode = 0;
        try {
			jarProcess = jarProcessBuilder.start();
			exitCode = jarProcess.waitFor();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        if (exitCode != 0) {
            System.out.println("Jar creation Failed! exit code " + exitCode);
            System.out.println(readFromConsole(jarProcess.getErrorStream()));
        } else {
        	System.out.println("Jar file created successfully.");
        }
        
        List<String> command = jarProcessBuilder.command();
        System.out.println("Jar build Command: " + String.join(" ", command));
        
        
        ProcessBuilder runJarProcessBuilder = new ProcessBuilder("java", "-jar", jarFileName);
		runJarProcessBuilder.directory(new File(RESOURCES_DIRECTORY));
		List<String> runCommand = runJarProcessBuilder.command();
		System.out.println("Jar run Command: " + String.join(" ", runCommand));
        
        try {
        	System.out.println("Running jar...");
			run(runJarProcessBuilder);
		} catch (IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
		return null;
	}
	
	private static String readFromConsole(InputStream inputStream) {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        try {
			while ((line = bufferedReader.readLine()) != null) {
			    stringBuilder.append(line).append(System.lineSeparator());
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return stringBuilder.toString();
    }
	
	private static void run(ProcessBuilder processBuilder) throws IOException, InterruptedException {
		Process process = processBuilder.start();
		int exitCode = process.waitFor();
		if (exitCode != 0) {
			System.out.println("Process terminated abnormally: exit code " + exitCode);
			System.out.println(readFromConsole(process.getErrorStream()));
		} else {
			System.out.println("Process terminated successfully.");
			String processOutput = readFromConsole(process.getInputStream());
			System.out.println("processOutput " + processOutput + " EXPECTED_OUTPUT " + EXPECTED_OUTPUT);
			if (processOutput == EXPECTED_OUTPUT) {
				System.out.println("Assert true");
			} else {
				System.out.println("Assert false");
			}
		}
	}

}
