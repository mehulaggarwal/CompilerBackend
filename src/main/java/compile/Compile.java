	package compile;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.time.Clock;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import Model.CompilerInput;
import Model.Output;
import Utilities.Helper;

@RestController
public class Compile {

	public String removeWord(String string, String word) 
    { 
  
        // Check if the word is present in string 
        // If found, remove it using removeAll() 
		//System.out.println("string= "+string);
		//System.out.println("word= "+word);
        if (string.contains(word)) { 
        	//System.out.println("*");
            // To cover the case 
            // if the word is at the 
            // beginning of the string 
            // or anywhere in the middle 
            String tempWord = word + " "; 
            string = string.replaceAll(word, ""); 
  
            // To cover the edge case 
            // if the word is at the 
            // end of the string 
            tempWord = " " + word; 
            string = string.replaceAll(word, ""); 
        } 
        //System.out.println("resulted string= "+string);
        // Return the resultant string 
        return string; 
    } 

	@PostMapping("/execute")
	public Output executeCode(@RequestBody CompilerInput compilerInput) throws IOException {
		Helper helper = new Helper();
		String code = compilerInput.getCode();
		Output outputObject = new Output();
		String basePath = System.getProperty("user.dir"); 
		System.out.println(code);
		System.out.println(compilerInput.getLanguage());
		System.out.println("in excute code");
//		System.out.println("execute code");
		System.out.println(System.getProperty("user.dir"));
		String filePath = helper.writeIntoFileAndReturnPath(compilerInput);
		
		List<String> list = new ArrayList<String>();
		list.add("gcc");
		list.add("-o");
		list.add(basePath+"/answer");
		list.add(filePath);
		ProcessBuilder build = new ProcessBuilder(list);
		//build.start();
		build.command(list);
		Process process =build.start();
		
		
		
		String output = new String();

		BufferedReader reader = new BufferedReader(
				new InputStreamReader(process.getInputStream()));
		BufferedReader errorOnCommandLine=new BufferedReader(new InputStreamReader(process.getErrorStream()));
		String gettingErrorLineByLine="";
		//System.out.println(errorOnCommandLine);
		while ((gettingErrorLineByLine = errorOnCommandLine.readLine()) != null) {
			gettingErrorLineByLine = removeWord(gettingErrorLineByLine,"/Users/mehul.aggarwal/eclipse-workspace/compiler/Program.c:");
			output=output+gettingErrorLineByLine +"\n";
		}
		System.out.println("output= "+output);
		if(output.isEmpty()==false) {
			/*Code to capture the log output*/
			//output = removeWord(output,"/Users/mehul.aggarwal/eclipse-workspace/compiler/Program");
			//System.out.println(output);
			
			outputObject.setHasError(true);
			outputObject.setError(output);
			helper.deleteFile(filePath);
			return outputObject;
		}
		System.out.println("command line arguments==="+compilerInput.getCommandLineArguments());
		System.out.println("reaching this");
			List<String> list1 = new ArrayList<String>();
			System.out.println(basePath+"/./answer");
			list1.add(basePath+"/./answer");
			if(compilerInput.getCommandLineArguments()!=null&&!compilerInput.getCommandLineArguments().isEmpty()) {
				String[] cmdArugments = compilerInput.getCommandLineArguments().split(" ");
				for(String cmdArugment : cmdArugments) {
					list1.add(cmdArugment);
				}
			}
			ProcessBuilder build1 = new ProcessBuilder(list1);
			build1.command(list1);
			//Process process1 =build1.start();
			StringBuilder finalOutput = new StringBuilder();
			long timeMilli1 =  System.currentTimeMillis();
			if(compilerInput.getInput()==null||compilerInput.getInput().isEmpty()) {
				System.out.println("entering this");
				compilerInput.setInput("345465768798");
			}
			if(compilerInput.getInput()!=null) {
				List<String> list2 = new ArrayList<String>();
				String inputFilePath = helper.writeIntoFileAndReturnTxtPath(compilerInput.getInput());
				build1.redirectInput(new File(inputFilePath));
			}
			
			Process process1 =build1.start();
			BufferedReader outputOnCommandLine=new BufferedReader(new InputStreamReader(process1.getInputStream()));
			String gettingOutputLineByLine="";
			long timeMilli2 =  System.currentTimeMillis();
			/*Code to capture the log output*/
			finalOutput.append("\n");
			//System.out.println(outputOnCommandLine);
			while ((gettingOutputLineByLine = outputOnCommandLine.readLine()) != null) {
				System.out.println("*");
				System.out.println(gettingOutputLineByLine);
				finalOutput.append(gettingOutputLineByLine + "\n");
			}
			System.out.println(finalOutput);
			outputObject.setHasError(false);
			outputObject.setOutput(finalOutput);
			
		    Date date2 = new Date();  
		    
		    System.out.println(timeMilli2);
		    System.out.println(timeMilli2-timeMilli1);
		    double executionTime = (double)((timeMilli2-timeMilli1))/1000;
		    outputObject.setExecutionTime(executionTime);
			//System.out.println(finalOutput);	
			helper.deleteFile(filePath);
			return outputObject;
		}
		
		

	
	
}
