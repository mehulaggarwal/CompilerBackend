package Model;

public class CompilerInput {

	public CompilerInput() {
		
	}

	private String code;
	private String language;
	private String input;
	private String commandLineArguments;

	public String getInput() {
		return input;
	}

	public void setInput(String input) {
		this.input = input;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCommandLineArguments() {
		return commandLineArguments;
	}

	public void setCommandLineArguments(String commandLineArguments) {
		this.commandLineArguments = commandLineArguments;
	}
	

}
