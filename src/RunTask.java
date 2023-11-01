import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public class RunTask implements Task {
	private static final String FILE_NAME = new String("HelloWorld");
	private static final String SOURCES_DIRECTORY = "src";

	@Override
	public Result execute(Options options) {
		System.out.println("");
		
		ProcessBuilder classRunProcess = new ProcessBuilder(options.get("command"), options.get("file"));
        classRunProcess.directory(new File(SOURCES_DIRECTORY));

        List<String> command = classRunProcess.command();
        System.out.println("Class run Command: " + String.join(" ", command));
        
        Process classRun = null;
        int exitCode = 0;
        try {
        	classRun = classRunProcess.start();
			exitCode = classRun.waitFor();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        
        String processOutput;
        
        
        if(exitCode == 0) {
        	try {
				processOutput = readFromConsole(classRun.getInputStream());
				System.out.println("Class run successfully. " + processOutput);
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
        else {
        	System.out.println("Class run failed");
        }
		
		return null;
	}
	
	private static String readFromConsole(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            stringBuilder.append(line).append(System.lineSeparator());
        }
        return stringBuilder.toString();
    }

}
