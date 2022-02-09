package tp.p3.control.commands;

import java.util.Scanner;

import tp.p3.control.commands.CommandParser;
import tp.p3.logic.multigames.Game;

public class HelpCommand extends NoParamsCommand {
	public HelpCommand() {
		super("help", "print this help message.");
	}

	public boolean execute(Game game) {
		System.out.println(CommandParser.commandHelp());
		return false;
	}

	public Command parse(String[] commandWords, Scanner in) {
		Command h = new HelpCommand();
		return h;
	}
} 