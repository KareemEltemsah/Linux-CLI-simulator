import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Parser {
	private ArrayList<String> args = new ArrayList<String>(); // Will be filled by arguments extracted by parse method 
	private String cmd =  new String(); // Will be filled by the command extracted by parse method
	//cmd and args for the pipe operator
	private ArrayList<String> args2 = new ArrayList<String>(); // Will be filled by arguments extracted by parse method 
	private String cmd2 =  new String(); // Will be filled by the command extracted by parse method
	private ArrayList<String> validCMDs = new ArrayList<String>();
	boolean isTherePipeOperatopr = false;
	
	public Parser () {
		//adding our available CMDs
		validCMDs.add("cd");
		validCMDs.add("ls");
		validCMDs.add("cp");
		validCMDs.add("cat");
		validCMDs.add("more");
		validCMDs.add("mkdir");
		validCMDs.add("rmdir");
		validCMDs.add("mv");
		validCMDs.add("rm");
		validCMDs.add("args");
		validCMDs.add("date");
		validCMDs.add("help");
		validCMDs.add("pwd");
		validCMDs.add("clear");
		validCMDs.add("exit");
		
	}
	/** 
	* Return: true if it was able to parse user input correctly. Otherwise false 
	* Parameter input: user command 
	* In case of success, it should save the extracted command and arguments to args and cmd variables
	* It should also print error messages in case of too few arguments for a commands 
	* eg. “cp requires 2 arguments” 
	*/
	public boolean parse(String input) {
		//clear the arguments array because we are using a single parser instance
		cmd = new String();
		cmd2 = new String();
		args.clear();
		args2.clear();
		isTherePipeOperatopr = false;//return to the default value
		List<String> inputParts = Arrays.asList(input.split(" "));//splitting inputs per word
		if (inputParts.contains("|")) {//pipe operator case
			int pipeIndex = inputParts.indexOf("|");
			if (isValidCMD(inputParts.get(0))) { //check the 1st cmd
				cmd = inputParts.get(0);
				for (int i=1 ; i<pipeIndex ; i++) {//get the 1st cmd args
					args.add(inputParts.get(i));
				}
				if (isValidCMD(inputParts.get(pipeIndex+1))) { //check the 2nd cmd
					cmd2 = inputParts.get(pipeIndex+1);
					for (int i=pipeIndex+2 ; i<inputParts.size() ; i++) {//get the 2nd cmd args
						args2.add(inputParts.get(i));
					}
					isTherePipeOperatopr = true;//pipe flag
					return true;
				}
			}
			return false;
		}
		else {//no pipe operator (single cmd)
			if (isValidCMD(inputParts.get(0))) {
				cmd = inputParts.get(0);
				for (int i=1 ; i<inputParts.size() ; i++) {
					args.add(inputParts.get(i));
				}
				return true;
			}
			return false;
		}
	}
	public boolean isValidCMD(String cmd) {
		if (validCMDs.contains(cmd))
			return true;
		//in case wrong cmd
		System.out.println("invalid cmd");
		return false;
	}
	public String getCmd() {
		return cmd;
	}
	public ArrayList<String> getArgs() {
		return args;
	}
	public String getCmd2() {
		return cmd2;
	}
	public ArrayList<String> getArgs2() {
		return args2;
	}
}
