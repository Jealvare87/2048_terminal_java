package tp.p3.control.commands;


import java.util.Scanner;

import tp.p3.exceptions.ExecuteException;
import tp.p3.exceptions.ParseException;
import tp.p3.logic.Direction;
import tp.p3.logic.multigames.Game;

public class MoveCommand extends Command {	
	
	private Direction movDir;
	
	public MoveCommand() {
		super("move", "<direction>: execute a move in one of the four directiones, up, down, left, right. ");
	}
	
	public MoveCommand(Direction x) {
		super("move", "<direction>: execute a move in one of the four directiones, up, down, left, right. ");
		this.movDir = x;
	}

	public boolean execute(Game game) throws ExecuteException{
		boolean ok = false;
		
		if((this.movDir.equals(Direction.RIGHT) || this.movDir.equals(Direction.LEFT) ||
				this.movDir.equals(Direction.DOWN) || this.movDir.equals(Direction.UP))) {
			game.move(this.movDir);
			ok = true;
		}
		else {
			throw new ExecuteException("Unknown direction for move command. ");
		}
		return ok;
	}

	public Command parse(String[] commandWords, Scanner in) throws ParseException {
		Command m = null;
		if(commandWords.length == 1) {
			throw new ParseException("Move must be followed by a direction: up, down, left, right \n");
		}
		else if(commandWords.length == 2) {
			switch(commandWords[1]) {
				case "up": 
					m = new MoveCommand(Direction.UP);
					break;
				case "down":
					m = new MoveCommand(Direction.DOWN);
					break;
				case "right": 
					m = new MoveCommand(Direction.RIGHT);
					break;
				case "left":
					m = new MoveCommand(Direction.LEFT);
					break;
				default:
					throw new ParseException("Unknown direction for move command. \n");
				}
		}
		else {
			throw new ParseException("Unknown direction for move command. \n");
		}
		return m;
	}
	
}
