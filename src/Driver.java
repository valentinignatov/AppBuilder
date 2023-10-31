

import java.io.*;
import java.util.List;
import java.util.jar.*;

public class Driver {
	
	private static final String MAIN_CLASS = "HelloWorld";
	private static final String RESOURCES_DIRECTORY = "bin";
	private static final String EXPECTED_OUTPUT = "Hello world!";// + System.lineSeparator();
	private static final String FILE = new String("HelloWorld.java");
	
	public static void main(String[] args) throws IOException, InterruptedException {
//        JarTool tool = new JarTool();
//        tool.startManifest();
////        tool.addToManifest("Main-Class", "org.jar.project.HelloWorld");
//        tool.addToManifest("Main-Class", "HelloWorld");
//
//        JarOutputStream target = tool.openJar("HelloWorld.jar");
//        
//        tool.addFile(target, System.getProperty("user.dir") + "\\src\\main\\java",
////          System.getProperty("user.dir") + "\\bin\\org\\jar\\project\\HelloWorld.class");
//        System.getProperty("user.dir") + "\\HelloWorld.class");
//        target.close();
		
//		new Driver().packageAndRun(); //Working
		
		TaskRunner appBuildTaskRunner = new TaskRunnerBuilder()
			    .addTask(
			        new CompileTask(), 
			        new Options().set("command", "javac").set("file", MAIN_CLASS)
			    )
			    .addTask(
			        new RunTask(), 
			        new Options().set("command", "java").set("file", MAIN_CLASS)
			    )
			    .addTask(
			        new PackageTask(), 
			        new Options().set("command", "jar").set("file", MAIN_CLASS)
			    )
			    .build();

//			try {
			   appBuildTaskRunner.start();
			   System.out.println("BUILDING Success!");
//			} catch (...) {
//				System.out.println("BUILDING Failure at step xyz!");
//			}
//		new CompileTask().execute(new Options());
//		new PackageTask().execute(new Options());
		
//		System.getProperty("curent.dir");
//		System.out.println(System.getProperty("user.dir"));
    }
	
	void packageAndRun() throws IOException, InterruptedException {
		
        String compiledJavaFile = FILE.replace(".java", ".class");
        String jarFileName = FILE.replace(".java", ".jar");
        ProcessBuilder jarProcessBuilder = new ProcessBuilder("jar", "cfe", jarFileName, MAIN_CLASS, compiledJavaFile);
        jarProcessBuilder.directory(new File(RESOURCES_DIRECTORY));

        List<String> command = jarProcessBuilder.command();
        System.out.println("Jar build Command: " + String.join(" ", command));

        Process jarProcess = jarProcessBuilder.start();
        int exitCode = jarProcess.waitFor();
        if (exitCode != 0) {
            System.out.println("Jar creation Failed! exit code " + exitCode);
            System.out.println(readFromConsole(jarProcess.getErrorStream()));
            return;
        }
        System.out.println("Jar file created successfully.");
        System.out.println("Running jar...");

        ProcessBuilder runJarProcessBuilder = new ProcessBuilder("java", "-jar", jarFileName);
        runJarProcessBuilder.directory(new File(RESOURCES_DIRECTORY));
        List<String> runCommand = runJarProcessBuilder.command();
        System.out.println("Jar run Command: " + String.join(" ", runCommand));

        run(runJarProcessBuilder);
    }
	
	private static void run(ProcessBuilder processBuilder) throws IOException, InterruptedException {
        Process process = processBuilder.start();
        int exitCode = process.waitFor();
        if (exitCode != 0){
            System.out.println("Process terminated abnormally: exit code " + exitCode);
            System.out.println(readFromConsole(process.getErrorStream()));
        } else {
            System.out.println("Process terminated successfully.");
            String processOutput = readFromConsole(process.getInputStream());
            System.out.println(processOutput);
            if (processOutput.equals(EXPECTED_OUTPUT)){
                System.out.println("Assert true");
            } else {
                System.out.println("Assert false");
            }
        }
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
