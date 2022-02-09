package tp.p3.control.commands;

import java.util.Scanner;
import tp.p3.logic.multigames.Game;

public class RedoCommand extends NoParamsCommand {

	public RedoCommand() {
		super("redo", "fix the undo if you want.");
	}

	public boolean execute(Game game) {
		game.redo();
		return true;
	}

	public Command parse(String[] commandWords, Scanner in) {
		Command r = new RedoCommand();
		return r;
	}

}
