package tp.p3.control;

import java.util.Scanner;
//import tp.p2.logic.Direction;
import tp.p3.logic.multigames.Game;
import tp.p3.control.commands.Command;
import tp.p3.control.commands.CommandParser;
import tp.p3.exceptions.*;


public class Controller {
	private Scanner in;
	private Game game;
	private boolean printGameState;
	String direccion;
	String gameType;
	String save = null;
	
	//Constructor primario
	public Controller(Game game, Scanner in) {
		this.game = game;
		this.in = in;	
		this.printGameState = true;
	}
	
	//Constructor secundario
	/*
		public Controller(Game game, Scanner in, boolean ok) {
			this.game = game;
			this.in = in;
			this.printGameState = true;
			//this.run();
		}*/
	
	//Establece el nombre de fichero a guardar
	public void setSave(String x) {
		this.save = x;
	}
	
	
	//Devuelve el nombre del fichero
	public String getSave() {
		return this.save;
	}
	
	//Establece el tipo de juego
	public void setGameType(String x) {
		this.gameType = x;
	}
	
	
	//Devuelve el tipo de juego
	public String getGameType() {
		return this.gameType;
	}
	
	
	//Establece la direccion
	public void setDireccion(String x) {
		this.direccion = x;
	}
	
	
	//Devuelve la direccion
	public String getDireccion() {
		return this.direccion;
	}
	
	
	//Devuelve el scanner que se está utilizando
	public Scanner getScanner() {
		return this.in;
	}
	

	
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 * Método por el cual ejecuta el comando, después de imprimir el tablero.*
	 * Únicamente sale si el juego ha acabado o ponemos el comando "exit".   *
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	public void run(){
		String linea;
		String[] words;
		boolean exit = false;
		try {
			do  {
				if(this.printGameState == true) {
					System.out.println(game);            // Imprime por pantalla el juego con el tablero y la puntuación.
				}
				System.out.print("Command: ");
				linea = in.nextLine().toLowerCase(); // Transforma el texto introducido en minúscula.
				words = linea.trim().split(" ");	 // Coge cada palabra, le quita los espacios laterales y lo mete en un array.
				System.out.print("\n");
				try {
					Command command = CommandParser.parseCommand(words, this.in);							
					this.printGameState = command.execute(game);
					if (words[0].equals("exit")) {
						exit = true;
					}
					else if(words[0].equals("undo")) {
						System.out.println("Undoing one move...\n");
					}
					
				}catch(NullPointerException e) {
					if(!words[0].equals("move") && !words[0].equals("play")){
						System.out.println("Command null" + e);
						System.out.println("Unknown command. Use ’help’ to see the available commands. \n");
						this.printGameState = false;
				 	}
				}catch(ExecuteException e) {
					System.out.println(e);
				}catch(ParseException e) {
					System.out.println(e);
				}
				
			}while(exit != true && game.isFinished() != true);	//Si el juego ha terminado o se introduce el comando "exit", sale del bucle.
		}catch(GameOverException e) {
			System.out.println(e);
		}
	}

	
	//Establece en función del comando si se mostrará por pantalla el tablero o no
	public void setNoPrintGameState(String[] commandWords) {
		
		if(commandWords[0].equals("move") || commandWords[0].equals("play")|| commandWords[0].equals("reset") || commandWords[0].equals("undo") || commandWords[0].equals("redo")) {
			this.printGameState = true;
		}
		else {
			this.printGameState = false;
		}
	}
}