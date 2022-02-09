package tp.p3.control.commands;


import java.util.Scanner;

import tp.p3.logic.multigames.Game;

public class UndoCommand extends NoParamsCommand{

	public UndoCommand() {
		super("undo","return to the last state.");
	}
	public boolean execute(Game game) {
		game.undo();
		return true;
	}

	public Command parse(String[] commandWords, Scanner in) {
		Command u = new UndoCommand();
		return u;
	}

}
