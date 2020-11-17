import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.sound.midi.Soundbank;

public class CLI {
	static Terminal T = new Terminal();
	public static void main (String args []) throws IOException {
		Parser P =  new Parser();
		while (true) {
			try {
				String command;
				System.out.print("ex@ubuntu:" + System.getProperty("user.dir"));
				System.out.print("$ ");
				Scanner input = new Scanner(System.in);
				command = input.nextLine();
				if (P.parse(command)) {
					if (P.getCmd().equals("exit"))
						break;
					passCMDtoTerminal(P.getCmd(), P.getArgs());
					if (P.isTherePipeOperatopr)
						passCMDtoTerminal(P.getCmd2(), P.getArgs2());
				}
			}
			catch (Exception e) {
				
			}
		}
	}
	public static void passCMDtoTerminal(String cmd, ArrayList<String> args) throws IOException {
		if (cmd.equals("cd")) {
			if (args.size()==1)
				T.cd(args.get(0));
			else 	
				System.out.println("cd CMD requires only 1 argument");
		}
		else if (cmd.equals("ls")) {
			ArrayList<String> result = T.ls();
			if (args.size()==0) {
				for (int i=0 ; i<result.size() ; i++)
					System.out.println(result.get(i));
			}
			else if (args.get(0).equals(">")) {
				T.redirectionCMD(result.get(0), args.get(1),false);
				for (int i=1 ; i<result.size() ; i++)
					T.redirectionCMD(result.get(i), args.get(1), true);
			}
			else if (args.get(0).equals(">>")) {
				for (int i=0 ; i<result.size() ; i++)
					T.redirectionCMD(result.get(i), args.get(1), true);
			}
			else
				System.out.println("ls CMD accepts no arguments except redirection operators");
		}
		else if (cmd.equals("cp")) {
			if (args.size()==2)
				T.cp(args.get(0),args.get(1));
			else
				System.out.println("cp CMD requires 2 arguments");
		}
		else if (cmd.equals("cat")) {
			if (args.size()>0)
				T.cat(args);
			else
				System.out.println("cat CMD requires at least 1 argument");
		}
		else if (cmd.equals("more")) {
			if (args.size()==1)
				T.more(args.get(0));
			else
				System.out.println("more CMD requires 1 argument");
		}
		else if (cmd.equals("mkdir")) {
			if (args.size()>0)
				T.mkdir(args);
			else 
				System.out.println("mkdir CMD requires at least 1 argument");
		}
		else if (cmd.equals("rmdir")) {
			if (args.size()>0)
				T.rmdir(args);
			else 
				System.out.println("mkdir CMD requires at least 1 argument");
		}
		else if (cmd.equals("mv")) {
			if (args.size()==2)
				T.mv(args.get(0),args.get(1));
			else
				System.out.println("mv CMD requires 2 arguments");
		}
		else if (cmd.equals("rm")) {
			if (args.size()>0)
				T.rm(args);
			else
				System.out.println("rm CMD requires at least 1 argument");
		}
		else if (cmd.equals("args")) {
			T.args(args);
		}
		else if (cmd.equals("date")) {
			if (args.size()==0)
				System.out.println(T.date());
			else if (args.get(0).equals(">"))
				T.redirectionCMD(T.date(), args.get(1),false);
			else if (args.get(0).equals(">>"))
				T.redirectionCMD(T.date(), args.get(1), true);
			else 
				System.out.println("date CMD accepts no arguments except redirection operators");
		}
		else if (cmd.equals("help")) {
			T.help();
		}
		else if (cmd.equals("pwd")) {
			if (args.size()==0)
				System.out.println(T.pwd());
			else if (args.get(0).equals(">"))
				T.redirectionCMD(T.pwd(), args.get(1),false);
			else if (args.get(0).equals(">>"))
				T.redirectionCMD(T.pwd(), args.get(1), true);
			else 
				System.out.println("pwd CMD accepts no arguments except redirection operators");
		}
		else if (cmd.equals("clear")) {
			if (args.size()==0)
				T.clear();
			else 
				System.out.println("clear CMD requires no arguments");
		}
	}
}
