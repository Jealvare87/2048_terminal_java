package tp.p3.control.commands;

import java.util.Scanner;
import tp.p3.logic.multigames.Game;

public class ResetCommand extends NoParamsCommand{
	
	public ResetCommand() {
		super("reset", "start a new game.");
	}

	public boolean execute(Game game) {
		game.reset();
		return true;
	}

	public Command parse(String[] commandWords, Scanner in) {
		Command r = new ResetCommand();		
		return r;
	}
}
