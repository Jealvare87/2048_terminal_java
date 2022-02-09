package tp.p3.control.commands;

import java.util.Scanner;
import tp.p3.exceptions.ParseException;

public class CommandParser {
	private static Command[] availableCommands = { new HelpCommand(), new ResetCommand(), new ExitCommand(), new MoveCommand(), new UndoCommand(), new RedoCommand(), new PlayCommand(), new LoadCommand(), new SaveCommand() };
	
	//Ejecuta el método parser del comando seleccionado y devuelve un tipo Command
	public static Command parseCommand(String[] commandWords, Scanner in) throws ParseException {
		Command c = null;
		
		switch(commandWords[0]) {
			case "help":
				c = availableCommands[0].parse(commandWords, in);
				break;
			case "reset":
				c = availableCommands[1].parse(commandWords, in);
				break;
			case "exit":
				c = availableCommands[2].parse(commandWords, in);
				break;
			case "move":
				c = availableCommands[3].parse(commandWords, in);
				break;
			case "undo":
				c = availableCommands[4].parse(commandWords, in);
				break;
			case "redo":
				c = availableCommands[5].parse(commandWords, in);
				break;
			case "play":
				c = availableCommands[6].parse(commandWords, in);
				break;
			case "load":
				c = availableCommands[7].parse(commandWords, in);
				break;
			case "save":
				c = availableCommands[8].parse(commandWords, in);
				break;
			default:
				throw new ParseException("Unknown command. \n");
		}
				
		return c;
	}
	
	
	//Este método junta todos los HelpText de cada comando
	public static String commandHelp() {
		String show = "";
		show += "Available Commands: \n";
		for(int i = 0; i < availableCommands.length; i++) {
			show += availableCommands[i].helpText() + "\n";
		}
		return show;
	}
}
