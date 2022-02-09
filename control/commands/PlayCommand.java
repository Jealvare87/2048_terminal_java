package tp.p3.control.commands;


import java.util.Scanner;
import tp.p3.exceptions.*;
import tp.p3.logic.multigames.Game;
import tp.p3.logic.multigames.Rules2048;
import tp.p3.logic.multigames.RulesFib;
import tp.p3.logic.multigames.RulesInverse;
import tp.p3.logic.multigames.GameType;

public class PlayCommand extends NoParamsCommand{
	private GameType gamet;
	private Scanner sp;
	
	public PlayCommand() {
		super("play <game>", "start a new game of one of the game types: original, fib, inverse.");
	}
	
	public PlayCommand(GameType t, Scanner x) {
		super("play <game>", "start a new game of one of the game types: original, fib, inverse.");
		this.sp = x;
		this.gamet = t;
	}

	public boolean execute(Game game) throws ExecuteException{ /////////////////////cuando pulsa return tiene que coger los parametros normales
		Scanner sc = this.sp;
		String arg;
		int initCells = 0, size = 0;
		long seed = 0;
		boolean ok = false;
		int defCells = game.getGameCells();
		int defSize = game.getGameSize();
		long defSeed = game.getGameSeed();
		boolean wellDone = false;
		
		//////////////////////////////////////////////////////////
		
		while(wellDone == false) {
			try {
				do { // Selecciona el tamaño de la tabla o si no pone el tamaño introducido por defecto
					System.out.print("Please enter the size of the board: ");
					arg = sc.nextLine();
					if(arg.equals("")) {
						size = game.getGameSize();
						System.out.println(" Using the default size of the board: " + game.getGameSize());
					}
					else {
						size = Integer.parseInt(arg);
					}
				}while(size < 1);
				wellDone = true;
			}catch(NumberFormatException e) {
	        	System.out.println("Board size must be numbers" + e);
	        	wellDone = false;
	        }
		}
		wellDone = false;
		while(wellDone == false) {
			try {
				do { // Selecciona las celdas iniciales o si no pone las que tiene por defecto
					System.out.print("Please enter the number of initial cells: ");
					arg = sc.nextLine();
					if(arg.equals("")) {
						initCells = game.getGameCells();
						System.out.println(" Using the default number of initial cells: " + game.getGameCells());
					}
					else {
						initCells = Integer.parseInt(arg);
					}
				}while(initCells < 1 || (initCells > (size * size)));
				wellDone = true;
			}catch(NumberFormatException e) {
	        	System.out.println("Initial cells must be numbers" + e);
	        	wellDone = false;
	        }
		}
		wellDone = false;
		while(wellDone == false) {
			try {
				do { // Selecciona la semilla o si no pone la semilla por defecto
					System.out.print("Please enter the seed for the pseudo-random number generator: ");
					arg = sc.nextLine();
					if(arg.equals("")) {
						seed = game.getGameSeed();
						System.out.println(" Using the default seed for the pseudo-random number generator: " + game.getGameSeed());
					}
					else {
						seed = Integer.parseInt(arg);
					}
				}while(seed < 0 || seed > 1000);
				wellDone = true;
			}catch(NumberFormatException e) {
	        	System.out.println("Seed must be numbers" + e);
	        	wellDone = false;
	        }
		}
		switch(this.gamet) {
			case ORIG:
				game.gameN(initCells, size, seed, new Rules2048());
				game.setGameType(GameType.ORIG);
				game.setDefCells(defCells);
				game.setDefSeed(defSeed);
				game.setDefSize(defSize);
				ok = true;
				break;
			case INV:
				game.gameN(initCells, size, seed, new RulesInverse());
				game.setGameType(GameType.INV);
				game.setDefCells(defCells);
				game.setDefSeed(defSeed);
				game.setDefSize(defSize);
				ok = true;
				break;
			case FIB:
				game.gameN(initCells, size, seed, new RulesFib());
				game.setGameType(GameType.FIB);
				game.setDefCells(defCells);
				game.setDefSeed(defSeed);
				game.setDefSize(defSize);
				ok = true;
				break;
		}	
		if(ok == false) {
			throw new ExecuteException("Something went wrong while creating new game. \n");
		}
		return ok;
	}
	
	public Command parse(String[] commandWords, Scanner in) throws ParseException{
		Command m = null;
		if(commandWords.length == 1) {
			throw new ParseException("Play must be followed by a game type: original, fib, inverse \\n");
		}
		else if (commandWords.length == 2){
			switch(commandWords[1]) {
			case "fib":
				m = new PlayCommand(GameType.FIB, in);
				GameType.parse("fib");
				break;
			case "inverse":
				m = new PlayCommand(GameType.INV, in);
				GameType.parse("inverse");
				break;
			case "original":
				m = new PlayCommand(GameType.ORIG, in);
				GameType.parse("original");
				break;
			default:
				throw new ParseException("Unknown game type for play command \n");
			}
		}
		else {
			throw new ParseException("Unknown game type for play command \n");
		}
		
		return m;
	}	

}
