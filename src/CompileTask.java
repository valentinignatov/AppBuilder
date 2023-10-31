import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class CompileTask implements Task {
	private static final String FILE_NAME = new String("HelloWorld.java");
	private static final String SOURCES_DIRECTORY = "src"; //bin src
	private static final String COMPILATION_DIRECTORY = "bin"; //bin src
	
	@Override
	public Result execute(Options options) {
		System.out.println("");
		System.out.println("In execute() of CompileTask");
        ProcessBuilder classCompileProcess = new ProcessBuilder("javac", FILE_NAME);
        classCompileProcess.directory(new File(SOURCES_DIRECTORY));

        List<String> command = classCompileProcess.command();
        System.out.println("Class compile Command: " + String.join(" ", command));
        
        Process classCompile;
        int exitCode = 0;
        try {
        	classCompile = classCompileProcess.start();
			exitCode = classCompile.waitFor();
		} catch (IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        if(exitCode == 0)
        	System.out.println("Class compiled successfully.");
        else
        	System.out.println("Class compile failed");
        
		return null;
	}
	
static void executeCommand(String... coomand) {
		
		System.out.println("In executeCommand");
		System.out.println("Executing Command");
		for(int i = 0; i < coomand.length; i++) {
			System.out.println(coomand[i]);
		}
		System.out.println("");
		
		ProcessBuilder processBuilder = new ProcessBuilder();

        // Run this on Windows, cmd, /c = terminate after this run
        processBuilder.command(coomand);

        try {

            Process process = processBuilder.start();

			// blocked :(
            BufferedReader reader =
                    new BufferedReader(new InputStreamReader(process.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            int exitCode = process.waitFor();
            System.out.println("\nExited with error code : " + exitCode);
            System.out.println("");

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
	}

}
