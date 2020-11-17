import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;

public class Terminal {
	
	//Copy file CMD
	public void cp(String src, String dst ) throws IOException{
		src = checkShortPath(src);
		dst = checkShortPath(dst);
		Files.copy(Paths.get(src), Paths.get(dst),StandardCopyOption.REPLACE_EXISTING);
	}
	//move file CMD
	public void mv(String src, String dst) throws IOException {
		src = checkShortPath(src);
		dst = checkShortPath(dst);
		Files.move(Paths.get(src), Paths.get(dst));
	}
	//remove file CMD
	public void rm(ArrayList<String> files) {
		for (int i=0 ; i<files.size() ; i++) {
			File file = new File(files.get(i));
			file.delete();
		}
	}
	//Current directory CMD
	public String pwd() {
		return ("Working Directory = " + System.getProperty("user.dir"));
	}
	//concatenate CMD
	public void cat(ArrayList<String> args) throws IOException {
		if (!args.contains(">") && !args.contains(">>")) {//check if there redirection operators
			for (int i=0 ; i<args.size() ; i++)
				readFile(args.get(i));
		}
		else {//redirection case
			if (args.contains(">")) {
				//clear the file or make a new one if it's not exist
				FileWriter fileToBeClear = new FileWriter(checkShortPath(args.get(args.indexOf(">")+1)),false);
				fileToBeClear.close();
				for (int i=0 ; i<args.indexOf(">") ; i++)
					appendFileToFile(args.get(i), args.get(args.indexOf(">")+1));
			}
			else if (args.contains(">>")) {
				for (int i=0 ; i<args.indexOf(">>") ; i++)
					appendFileToFile(args.get(i), args.get(args.indexOf(">>")+1));
			}
		}
	}
	//clear screen CMD
	public void clear() {
		for (int i = 0; i < 10; i++) {
            System.out.println("\n\n\n\n\n");
        }
	}
	//list files CMD
	public ArrayList<String> ls() {
		File file = new File(System.getProperty("user.dir"));
        File[] files = file.listFiles();
        ArrayList<String> filesNames = new ArrayList<String>();
        for (File f : files) {  // iterates on the files array and get name of each one
            filesNames.add(f.getName());
        }
        return filesNames;
	}
	//show with scroll CMD
	public void more(String src) throws IOException {
		String line = new String();
        BufferedReader reader = new BufferedReader(new FileReader(checkShortPath(src)));
        int i=0;
        while ((line = reader.readLine()) != null) {
            if (i==10) {
            	Scanner input = new Scanner(System.in);
            	System.out.print("enter 1 to continue or anything to quit: ");
            	if (input.nextLine().equals("1"))
            		i = 0;
            	else
            		break;
            }
            System.out.println(line);
            i++;
        }
        System.out.println();
        reader.close();
	}
	//create directory CMD
	public void mkdir(ArrayList<String> directories) throws IOException {
		for (int i=0 ; i<directories.size() ; i++) {
			Files.createDirectories(Paths.get(checkShortPath(directories.get(i))));
		}
	}
	//remove directory CMD
	public void rmdir(ArrayList<String> directories) {
		for (int i=0 ; i<directories.size() ; i++) {
			File file = new File(directories.get(i));
			file.delete();
		}
	}
	//change current directory CMD
	public void cd(String dir) {
		dir = checkShortPath(dir);
		if (Files.exists(Paths.get(dir)))//check if this path is valid
        	System.setProperty("user.dir",dir);
        else
        	System.out.println("invalid path");
	}
	//show args for certain cmd
	public void args(ArrayList<String> CMDs) {
		HashMap<String, String> CommandsList = new HashMap<String, String>();
        CommandsList.put("mv", "arg1: SourcePath, arg2: DestinationPath");
        CommandsList.put("cp", "arg1: SourcePath, arg2: DestinationPath");
        CommandsList.put("rm", "[file1] [file2] etc");
        CommandsList.put("pwd", "no arguments but can redirect it's output");
        CommandsList.put("date", "no arguments but can redirect it's output");
        CommandsList.put("cd", "arg1: DirectoryPath");
        CommandsList.put("mkdir", "[directory1] [directory2] etc");
        CommandsList.put("rmidr", "[directory1] [directory2] etc");
        CommandsList.put("ls", "no arguments but can redirect it's output");
        CommandsList.put("cat", "[file1] [file2] etc");
        CommandsList.put("more", "arg1: filename");
        CommandsList.put("args", "args: command words  ex : cp cd");
        CommandsList.put("help", "arg1: command");
        CommandsList.put("clear", "no arguments");
        CommandsList.put("exit", "no arguments");
        if (CMDs.size() == 0) {
        	Iterator<Map.Entry<String, String>> itr = CommandsList.entrySet().iterator();
        	while (itr.hasNext()) {
                Map.Entry<String, String> entry = itr.next();
                System.out.println(entry.getKey() + " : " + entry.getValue());
            }
        }
        for (int i=0 ; i<CMDs.size() ; i++) {
        	if (CommandsList.get(CMDs.get(i)) != null)
        		System.out.println(CMDs.get(i) + " : " + CommandsList.get(CMDs.get(i)));
        }
	}
	//date CMD
	public String date() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
		LocalDateTime now = LocalDateTime.now();  
		return dtf.format(now);
	}
	//help shows cmd information
	public void help() {
		HashMap<String, String> Commands = new HashMap<String, String>();
        Commands.put("mv", "Moves file from one place to another.");
        Commands.put("cp", "Copies file from one place to another.");
        Commands.put("rm", "Removes one or more files.");
        Commands.put("pwd", "Prints the path of the working directory, starting from the root.");
        Commands.put("date", "Displays the date and time.");
        Commands.put("cd", "Changes the current directory.");
        Commands.put("mkdir", "Creates one or more directories.");
        Commands.put("rmidr", "Removes one or more directories.");
        Commands.put("ls", "Lists all the files and folders in the current directory.");
        Commands.put("cat", "Lists the contents of files to the terminal window.");
        Commands.put("more", "Views the text files in the command prompt with paging.");
        Commands.put("args", "list all parameters on the command line or certain command parameters.");
        Commands.put("help", "Provides information about the commands.");
        Commands.put("clear", "Clears the terminal screen.");
        Commands.put("exit", "close terminal.");
        
        Iterator<Map.Entry<String, String>> itr = Commands.entrySet().iterator();
        while (itr.hasNext()) {
        	Map.Entry<String, String> entry = itr.next();
        	System.out.println(entry.getKey() + " : " + entry.getValue());
        }
	}
	public String checkShortPath (String path) {
		if (path.equals("..")) {
			String temp = System.getProperty("user.dir");
			int flag = 0;
			for (int i=temp.length()-1 ; i>0 ; i--) {
				if (temp.charAt(i) == 92) {
						flag = i;
						break;
				}
			}
			if (temp.charAt(flag-1) == ':')
				return temp.substring(0, flag+1);
			else 
				return temp.substring(0,flag);
		}
		//complete the short paths
		if (!path.contains(":") && path.startsWith("\\"))
			path = System.getProperty("user.dir") + path;
		else if (!path.contains(":"))
			path = System.getProperty("user.dir") + "\\" + path;
		return path;	
	}
	private void appendFileToFile (String src, String dst) throws IOException {
        String line = null;
        BufferedReader reader = new BufferedReader(new FileReader(checkShortPath(src)));
        BufferedWriter writer = new BufferedWriter(new FileWriter(checkShortPath(dst),true));
        // (true) for append option
        while ((line = reader.readLine()) != null) {
            writer.append(line);
            writer.newLine();
        }
        reader.close();
        writer.close();
	}
	private void readFile (String fileName) throws IOException {
        String line = new String();
        BufferedReader reader = new BufferedReader(new FileReader(checkShortPath(fileName)));
        while ((line = reader.readLine()) != null) {
                System.out.println(line);
        }
        System.out.println();//to print newline between the files
        reader.close();
	}
	public void  redirectionCMD(String output, String dst, boolean append) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(checkShortPath(dst),append));
        //append boolean = (true) for append and (false) for replace
        writer.append(output);
        writer.newLine();
        writer.close();
	}
}
