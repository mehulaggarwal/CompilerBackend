package Utilities;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import Model.CompilerInput;

public class Helper {

	public String writeIntoFileAndReturnPath(CompilerInput compilerInput) {
		BufferedWriter writer;
		String basePath = System.getProperty("user.dir"); 
		String extension=".";
		if(compilerInput.getLanguage().equalsIgnoreCase("C")) {
			extension = extension + "c";
		}
		else if(compilerInput.getLanguage().equalsIgnoreCase("C++")) {
			extension = extension + "cpp";
		}
		String path = basePath + "/Program"+extension;
		try {	
		String fileContent = compilerInput.getCode();
		writer = new BufferedWriter(new FileWriter(path));
	    writer.write(fileContent);
	    writer.close();
	   
		}
	    catch (IOException e) {
			e.printStackTrace();
		}
		 return path;
	}
	
	public String writeIntoFileAndReturnTxtPath(String fileContent) {
		BufferedWriter writer;
		String basePath = System.getProperty("user.dir"); 
		String path = basePath + "/input.txt";
		try {	
		writer = new BufferedWriter(new FileWriter(path));
	    writer.write(fileContent);
	    writer.close();
	   
		}
	    catch (IOException e) {
			e.printStackTrace();
		}
		 return path;
	}

	public void deleteFile(String filePath) {
		File file = new File(filePath); 
        
        if(file.delete()) 
        { 
            System.out.println("File deleted successfully"); 
        } 
        else
        { 
            System.out.println("Failed to delete the file"); 
        } 
	}

	
}
