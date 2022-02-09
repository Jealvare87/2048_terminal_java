package tp.p3.logic.multigames;

import tp.p3.exceptions.*;
import tp.p3.logic.Board;
import tp.p3.logic.Position;
import tp.p3.logic.Direction;
import tp.p3.logic.reundo.GameState;
import tp.p3.logic.reundo.GameStateStack;
import java.util.Random;
import java.io.*;

public class Game {
	private Board board; 		// Tablero
	private int size; 			// Dimensión del tablero
	private int initCells; 		// Número de baldosas no nulas iniciales
	private Random myRandom; 	// Comportamiento aleatorio del juego
	private int score; 		    // Puntuación
	private int highest; 	    // La celda más alta
	private Position[] vacio; 	// Array de posiciones vacias en el tablero
	private GameStateStack undoStack = new GameStateStack();
	private GameStateStack redoStack = new GameStateStack();
	private GameRules currentRules;
	private GameType gameType;
	private long defGameSeed;
	private int defCells;
	private int defSize;
	private String name;
	
    /////////////////////////////////////////////////////
	
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String x) {
		this.name = x;
	}
	
	
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 * Este es el constructor de Game, el cual genera un "Game" en función de los parámetros *
	 * "sizeBoard" que es el tamaño del tablero, "initials" que son las celdas ocupadas ini- *
	 * cialmente, y "random" con su respectiva semilla para generaraleatorios.               *
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	public Game(GameRules rules, long seed, int dim, int initCells) {
		this.myRandom = new Random(seed);
		this.setGameType(GameType.ORIG); //se inicializa con el 2048 original, pero posteriormente será cambiado si no es el original
		this.size = dim;
		this.initCells = initCells;
		this.currentRules = rules;
		this.score = 0;
		this.highest = 0;
		//--------------------------------------------------
		int vacios = (this.size * this.size);
		this.vacio = new Position[vacios];
		//--------------------------------------------------
		this.board = rules.createBoard(this.size);
		rules.initBoard(this.board, initCells, this.myRandom);
		highest = this.board.max();
		undoStack.push(this.getState());
	}
	
	/*
	public Direction getDirection() {
		return this.dir;
	}
	
	public void setDirection(Direction x) {
		this.dir = x;
	}*/
	
	//Guarda el juego en un fichero
	public boolean storeGame(String filenameString) {
		boolean exito = false;
		if(filenameString != null) {
			exito = true;
			try(BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filenameString), "utf-8"))) {
				bw.write("This file stores a saved 2048 game");
				bw.newLine();
				bw.newLine();
				board.storeBoard(bw);
				bw.write(this.getGameCells() + "   " + this.score + "   " + this.type());
				bw.flush();
				bw.close();
			}catch(IOException e){	
	            System.out.println("Error E/S: "+ e);
	        }	
			
		}		
		return exito;
	}
	
	
	//Carga el juego de un fichero
	public boolean loadGame(String fileName) {
		boolean exito = false;
		try(BufferedReader br = new BufferedReader(new FileReader(fileName))){
			String linea = br.readLine();
			String aux = "";
			int cont = 0;
			if(linea.equals("This file stores a saved 2048 game")) {
				br.readLine();
				this.board.loadBoard(br); // Carga la tabla introduciendo el buffer.
				br.readLine(); // Segunda linea.
				linea = br.readLine();
				if(cont < linea.length()) {
					while (cont < linea.length() && (linea.charAt(cont) != ' ')) { 
							aux += linea.charAt(cont);
							cont++;
					}
					this.initCells = Integer.parseInt(aux);
					aux = "";
					while(cont < linea.length() && linea.charAt(cont) == ' ') {
						cont++;
					}
					while(cont < linea.length() && (linea.charAt(cont) != ' ')) {
							aux += linea.charAt(cont);
							cont++;
						
					}
					///////////////////////////////////////////////////////////////
					this.score = Integer.parseInt(aux); // Pasa la puntación
					aux = "";
					while(cont < linea.length()) { 
							if(linea.charAt(cont) != ' ') {
							aux += linea.charAt(cont);
							cont++;
							}
							else {
								cont++;
							}
					}
					switch(aux) {
						case "original":
							this.setGameType(GameType.ORIG);
							this.currentRules = new Rules2048();
							exito = true;
							break;
						case "fibonacci":
							this.setGameType(GameType.FIB);
							this.currentRules = new RulesFib();
							exito = true;
							break;
						case "inverse":
							this.setGameType(GameType.INV);
							this.currentRules = new RulesInverse();
							exito = true;
							break;
						default:
							System.out.println("GameType not recognized.\n");
							break;
					}
					this.highest = board.getMaximo();
					br.close();
				}
				
			}
			else {
				System.out.println("Something has gone wrong...\n");
			}
		}catch(StringIndexOutOfBoundsException e) {
        	System.out.println("Overflow of the string. " + e);
        }catch(NumberFormatException e) {
        	System.out.println("You can't introduce a letter as a number. " + e);
        }catch(IOException e){
            System.out.println("Error E/S: "+e);
        }
		return exito;
	}
	
	public void gameN(int initCells, int dim, long seed, GameRules rules) {
		this.myRandom = new Random(seed);
		this.setGameType(GameType.ORIG); //se inicializa con el 2048 original, pero posteriormente será cambiado si no es el original
		this.size = dim;
		this.initCells = initCells;
		this.currentRules = rules;
		this.score = 0;
		this.highest = 0;
		//--------------------------------------------------
		int vacios = (this.size * this.size);
		this.vacio = new Position[vacios];
		//--------------------------------------------------
		this.board = rules.createBoard(this.size);
		rules.initBoard(this.board, initCells, this.myRandom);
		highest = this.board.max();
		this.undoStack = new GameStateStack();
		this.redoStack = new GameStateStack();
		this.undoStack.push(this.getState());
	}
	
	//Devuelve un string con el tipo de juego
	private String type() {
		String x = "";
		switch(this.gameType) {
		case ORIG:
			x = "original";
			break;
		case INV:
			x = "inverse";
			break;
		case FIB:
			x = "fibonacci";
			break;			
		}
		return x;
	}
	
	//Inserta las celdas iniciales por defecto
	public void setDefCells(int x) {
		this.defCells = x;
	}
	
	
	//Inserta la semilla por defecto
	public void setDefSeed(long x) {
		this.defGameSeed = x;
	}
	
	
	//Inserta el tamaño por defecto
	public void setDefSize(int x) {
		this.defSize = x;
	}
	
	
	//Devuelve el tamaño del tablero primero
	public int getGameSize() {
		return this.defSize;
	}
	
	
	//Devuelve el numero de celdas iniciales
	public int getGameCells() {
		return this.defCells;
	}
	
	
	//Devuelve la semilla
	public long getGameSeed() {
		return this.defGameSeed;
	}
	
	
	//Establece el tipo de juego (original, inverso, fibonacci)
	public void setGameType(GameType type) {
		this.gameType = type;
	}
	
	
	//Devuelve el tipo de juego actual
	public GameType getGameType() {
		return this.gameType;
	}
	
	
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *  
	 * Este método es el encargado de que si se ejecuta un movimiento, se llame a los métodos que  *
	 * realizan dicha función                                                                      *
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	public void move(Direction dir) {
		if(this.redoStack.isEmpty() != true) {
			this.redoStack = new GameStateStack();
		}
		if(board.executeMove(dir, this.currentRules, this.gameType).getMoved() == true) {
		    board.moveBoard(dir);
			score = board.getPuntos();
			highest = board.getMaximo();
			if( this.boardEmpty() == true) {
				 this.board.fillEmpty(vacio, this.myRandom, this.sizeEmpty(), this.currentRules);
			}
			this.pushState();
		}
		
		/*********************************************************************************************
		 * Si hay alguna celda libre y se ha realizado algún movimiento (lo cual no implica fusión), *
		 * se genera un valor aleatorio (2 ó 4) en una celda aleatoria de las que están libres.      *
		 ********************************************************************************************/
		//----------------------------------------------
		else {
			if(board.moveBoard(dir) == true) {
				if( this.boardEmpty() == true) {
				  this.board.fillEmpty(vacio, this.myRandom, this.sizeEmpty(), this.currentRules);
				}
				this.pushState();
			}
		}
	}
	
	
	//Este método es el encargado de realiazr un nuevo tablero con las respectivas características iniciales
	public void reset() {
		this.board = new Board(size);
		this.currentRules.initBoard(this.board, initCells, this.myRandom);
		score = 0;
		highest = this.board.max();
		this.undoStack = new GameStateStack();
		undoStack.push(this.getState());
		this.redoStack = new GameStateStack();
	}
	
	
	//Este método deshace un movimiento
	public void undo() {
		if(undoStack.dimension() - 2 >= 0) {
			GameState pop; 
			pop = undoStack.getBuffer(this.undoStack.dimension() - 1);
			redoStack.push(pop);
			pop = undoStack.pop(this.undoStack.dimension() - 2);
			this.setState(pop);
			this.score = pop.getScore();
			
		}
		else if (undoStack.dimension() == 1){
			GameState pop; 
			pop = undoStack.getBuffer(0);
			this.setState(pop);
			
		}
		else {
			System.out.println("Can't undo more moves");
		}
		
	}
	
	
	//Rehace un movimiento deshecho
	public void redo() {
		if(redoStack.dimension() > 0) {
			GameState pop; 
			pop = redoStack.pop(this.redoStack.dimension() - 1);
			this.setState(pop);
			this.score = pop.getScore();

		}
			
		else {
			System.out.println("Can't redo more moves");
		}
	}
	
	
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 * Este método es llamado siempre que se realiza un movimiento para hacer dos comprobaciones:  *
	 * La primera es ver si se ha llegado a un "highscore" de 2048, lo que implicaría la victoria  *
	 * por parte del jugador.                                                                      *
	 * La segunda es comprobar si queda alguna celda libre, y en caso contrario ver si se puede    *
	 * realizar movimiento en cualquiera de las cuatro direcciones.                                *
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	public boolean isFinished() throws GameOverException{
		boolean si = false;
		   if(this.currentRules.win(this.board)){
			    si = true;
			    System.out.println(this.toString());
			    throw new GameOverException("Congratulations.");
		   }
		   else {
			  //si = this.board.lose(this.currentRules);
			   si = this.currentRules.lose(board);
		   }
		   
		  return si;
	}
	
	
	//Este método devuelve una cadena de string con el tablero, los puntos y el número mayor alcanzado
	public String toString() {
		return this.board.toString() + "\n" + " Score: " + score + " Best value: " + highest; 
	}
	
	 
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 * La función de este método es que va comprobando las posiciones del tablero, y si  *
	 * encuentra una vacía la almacena en un array de posiciones.                        *
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	private boolean boardEmpty() {
		boolean ok = false;
		for(int i = 0; i < this.size*this.size; i++) {
			this.vacio[i] = new Position(); 
		}
		ok = this.board.anyEmpty(this.vacio);
		return ok;
	}
	
	
	//Este método devuelve el tamaño del array de celdas vacías
	public int sizeEmpty() {
		int contador = 0;
		for(int i = 0; i < size * size; i++) {
			if((this.vacio[i].getPosFil() != -1) && (this.vacio[i].getPosCol() != -1)) {
				contador++;
			}
		}
		return contador;
	}
	
	
	//Saca el tablero de la pila
	public GameState getState() {
		GameState g = new GameState(this.score);
		g.setState(this.board.getState());
		return g;
	}
	
	
	//Establece el estado del tablero actual
	public void setState(GameState g) {
		Position pos = new Position();
		for(int i = 0; i < this.board.getSize(); i++) {
			for(int j = 0; j < this.board.getSize(); j++) {
				pos.setPosition(i, j);
				this.board.setCellValue(pos, g.getCellState(pos));
			}
		}
	}
	
	
	//Guarda el tablero actual en la pila
	public void pushState() {
		undoStack.push(this.getState());
	}	
	
	
	//Devuelve el tipo de juego actual
	public GameRules getGameRules() {
		return this.currentRules;
	}
	
}