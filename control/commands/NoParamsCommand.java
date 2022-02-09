package tp.p3.control.commands;

import java.util.Scanner;

import tp.p3.exceptions.ExecuteException;
import tp.p3.exceptions.ParseException;
import tp.p3.logic.multigames.Game;

public abstract class NoParamsCommand extends Command{

	public NoParamsCommand(String commandInfo, String helpInfo) {
		super(commandInfo, helpInfo);
	}
	public abstract boolean execute(Game game) throws ExecuteException;
	
	public abstract Command parse(String[] commandWords, Scanner in) throws ParseException;
}
