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

	@Override
	public Result execute(Options options) {
		System.out.println("");
		System.out.println("In execute() of PackageTask");
		String compiledJavaFile = FILE_NAME.replace(".java", ".class");
        String jarFileName = FILE_NAME.replace(".java", ".jar");
        ProcessBuilder jarProcessBuilder = new ProcessBuilder("jar", "cfe", jarFileName, MAIN_CLASS, compiledJavaFile);
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
        System.out.println("Running jar...");
        List<String> command = jarProcessBuilder.command();
        System.out.println("Jar build Command: " + String.join(" ", command));
        
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

}
