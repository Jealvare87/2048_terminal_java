package tp.p3.control.commands;


import java.util.Scanner;

import tp.p3.exceptions.*;
import tp.p3.logic.multigames.Game;

public class LoadCommand extends NoParamsCommand{

	private String fileName;
	
	public LoadCommand() {
		super("load", "loads a game from an existing file.");
	}
	
	public LoadCommand(String x) {
		super("load", "loads a game from an existing file.");
		this.fileName = x;
	}

	
	public boolean execute(Game game) {
		boolean ok = false;
		if(game.loadGame(this.fileName) == true) {
			System.out.println("Game successfully loaded from file: " + game.getGameType() + "\n");
			ok = true;
		}
		else {
			// Si algo no ha ido bien en la carga del archivo se carga el anterior tablero.
			game.undo();
		}
		return ok;
	}

	
	public Command parse(String[] commandWords, Scanner in) throws ParseException{
		Command m = null;
		if(commandWords.length == 1) {
			throw new ParseException("Load must be followed by a filename. \\n");
		}
		else if((commandWords.length == 2)) {
			if(commandWords[0].equals("load")) {
				if(commandWords[1].length() > 4) {
					if(this.cumple(commandWords[1]) == true) {
						m = new LoadCommand(commandWords[1]);
					}
					else {
						throw new ParseException("Invalid filename: the filename contains invalid characters. \\n");
					}
				}
				else {
					throw new ParseException("Invalid filename: the filename must be a '.txt'. \\n");
				}
			}
		}
		else {
			throw new ParseException("Invalid filename: the filename contains spaces. \\n");
		}
		return m;
	}

	
	//Sirve para comprobar si la segunda palabra es un archivo de texto válido
	private boolean cumple(String palabra) {
		boolean cumple = false;
		if((palabra.charAt(palabra.length() - 1) == 't') && (palabra.charAt(palabra.length() - 2) == 'x') && (palabra.charAt(palabra.length() - 3) == 't') && (palabra.charAt(palabra.length() - 4) == '.')) {
			cumple = true;
		}
		return cumple;
	}	
}
