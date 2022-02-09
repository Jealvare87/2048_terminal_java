package tp.p3.control.commands;


import java.util.Scanner;

import tp.p3.exceptions.ExecuteException;
import tp.p3.exceptions.ParseException;
import tp.p3.logic.multigames.Game;

public abstract class Command {
	private String helpText;
	private String commandText;
	protected final String commandName;
	
	//Constructor
	public Command(String commandInfo, String helpInfo) {
		commandText = commandInfo;
		helpText = helpInfo;
		String[] commandInfoWords = commandText.split("\\s+");
		commandName = commandInfoWords[0];
	}
	
	//Ejecuta el comando
	public abstract boolean execute(Game game) throws ExecuteException;
	
	
	//Se redefine el comando dependiendo de cual hayamos selecionado e indica si se puede imprimir por pantalla.
	public abstract Command parse(String[] commandWords, Scanner in) throws ParseException;
	
	
	//Crea el mensaje de ayuda de cada comando
	public String helpText() {
		return " " + commandText + ": " + helpText;
	}
}
