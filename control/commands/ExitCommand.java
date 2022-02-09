package tp.p3.control.commands;

import java.util.Scanner;
import tp.p3.logic.multigames.Game;

public class ExitCommand extends NoParamsCommand{

	public ExitCommand() {
		super("exit", "terminate the program.");
	}

	public boolean execute(Game game) {
		System.out.println("Thanks for playing :)");
		return false;
	}

	public Command parse(String[] commandWords, Scanner in) {
		Command e = new ExitCommand();
		return e;
	}

}
