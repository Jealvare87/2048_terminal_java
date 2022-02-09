package tp.p3.logic;

import java.io.*;
import java.util.Random;
import tp.p3.logic.multigames.GameRules;
import tp.p3.logic.multigames.GameType;
import tp.p3.util.MyStringUtils;
import tp.p3.exceptions.GameOverException;
import tp.p3.logic.Position;

public class Board {
	private Cell[][] board; // Array bidimensional de celdas
	private int boardSize; // Tamaño del tablaro (dimensión)
	private int puntos = 0;
	private int maximo = 0;
	//private Position[] iniCells;
	
	//////////////////////////////////////////////////////
	
	//Construye el tablero con las celdas inicializadas a cero
	public Board(int size) {
		this.boardSize = size;
		board = new Cell[size][size];
		for(int i = 0; i < size; i++) {
			for(int j = 0; j < size; j++) {
				board[i][j] = new Cell();
			}
		}
		/*
		board[0][0].setValue(32);
	    board[0][1].setValue(32);
	    board[0][2].setValue(512);
	    board[0][3].setValue(32);
	    board[1][0].setValue(64);
	    board[1][1].setValue(128);
	    board[1][2].setValue(256);
	    board[2][0].setValue(32);
	    board[2][1].setValue(1024);
	    board[2][2].setValue(256);
	    board[3][0].setValue(8);
	    board[3][1].setValue(2);
	    board[3][2].setValue(64);
	    board[3][3].setValue(256);
	    */
	}
	
	
	
	//Guarda el tablero en un archivo de texto
	public void storeBoard(BufferedWriter bw) throws IOException{
		String cadena = "";
		Position pos = new Position();
		for(int i = 0; i < this.boardSize; i++) {
			for(int j = 0; j < this.boardSize; j++) {
				pos.setPosition(i, j);
				int val = this.getCellValue(pos);
				cadena = String.valueOf(val);
				if(j == (this.boardSize - 1)) {
					bw.write(MyStringUtils.centre(cadena, 5));
				}
				else {
					bw.write(MyStringUtils.centre(cadena, 5) + MyStringUtils.repeat(" ", 3));
				}
			}
			 bw.newLine();
		}
		 bw.newLine();
		 bw.newLine();
	}
	
	
	//Carga el tablero de un archivo
	public void loadBoard(BufferedReader br) throws IOException{
		int contLinea = 0, contSize = 0;
		int contx = 0, conty = 0;
		int val = 0, max = 0;
		String linea = "", aux = "";
		linea = br.readLine();
		while (contLinea < linea.length()) {//para la primera linea y calcular el tamaño
			while(contLinea < linea.length() && linea.charAt(contLinea) == ' ') {
				contLinea++;
			}
			while(contLinea < linea.length() && linea.charAt(contLinea) != ' ') {
					aux += linea.charAt(contLinea);
					contLinea++;

			}
			if(contLinea < linea.length()) {
				val = Integer.parseInt(aux);
				this.board[contx][conty].setValue(val);
				if(val > max) {
					max = val;
				}
				aux = "";
				conty++;
				contSize++;
			}
		}
		contx++;
		this.boardSize = contSize;
		while(contx < this.boardSize) {//para el resto del tablero
			conty = 0;
			linea = br.readLine();
			contLinea = 0;
			while(conty < this.boardSize) {
				
				while(linea.charAt(contLinea) == ' ') {
					contLinea++;
				}
				while(linea.charAt(contLinea) != ' ') {
					aux += linea.charAt(contLinea);
					contLinea++;
				}
				if(contLinea <= linea.length()) {
					val = Integer.parseInt(aux);
					this.board[contx][conty].setValue(val);
					if(val > max) {
						max = val;
					}
					aux = "";
					conty++;
				}
			}
			contx++;
		}
		this.maximo = max;
		br.readLine();
	}
	
	
	//Este método genera una posición aleatoria dentro de los límites del tablero
	public Position randomCell(Random r) {
		Position p = new Position();
		int c1, c2;
		do {
			c1 = r.nextInt(boardSize); //coordenada 1
			c2 = r.nextInt(boardSize); //coordenada 2
		}while(this.board[c1][c2].getValue() != Cell.EMPTY);
		p.setPosition(c1, c2);
		return p;
	}
		
	
	//Este método asigna el valor "p" a la posición "pos"
	public void setCellValue(Position pos, int p) {
		this.board[pos.getPosFil()][pos.getPosCol()].setValue(p);
	}
	
	
	//Este método devuelve el valor de la celda situada en las coordenadas que se le pasan como parámetros
	private int getCellValue(Position pos) { 
		int x = board[pos.getPosFil()][pos.getPosCol()].getValue() ;
		return x;
	}
	
	
	//Esta función se encarga de comprobar si hay dos celdas en la misma fila/columna con el mismo valor, y las fusiona	
	public MoveResults executeMove(Direction dir, GameRules rules, GameType typeGame) {
		Position posi = new Position();
		int z = 0; // para la puntuacion
		Position posiVecino = new Position();
		Position posi2 = new Position();
		MoveResults m = new MoveResults();
		switch(dir) {
			case UP:
				m.setMoved(false);
				for(int i = 0; i < boardSize; i++) {
					for(int j = 0; j < boardSize; j++) {
						int cont = 0;
						boolean encontrado = false;
						posi.setPosition(j, i);
						posiVecino.setPosition(j, i);
						posiVecino = posi.neighbor(dir, boardSize);
						if(this.board[posi.getPosFil()][posi.getPosCol()].isEmpty() != true && posiVecino.getPosFil() < boardSize){ //Si encuentra una celda con un valor, busca una en esa fila con el mismo valor
							if(board[posiVecino.getPosFil()][posiVecino.getPosCol()].isEmpty() == true)  { //Si la siguiente a la comprobada no tiene valor, comprueba la(s) siguientes
								cont = posiVecino.getPosFil();
								while(cont < boardSize && encontrado == false) {
									if(board[cont][posiVecino.getPosCol()].isEmpty() == true) {
										cont++;
									}
									else {
										encontrado = true;
									}
								}
							}
							else {
								cont = posiVecino.getPosFil();
							}
							if(cont < boardSize) {
								if(board[cont][posiVecino.getPosCol()].isEmpty() != true) { //Si ha encontrado una celda con el mismo valor en esa fila y no hay otra entre medias, la fusiona
									z = board[posi.getPosFil()][posi.getPosCol()].doMerge(board[cont][posiVecino.getPosCol()], rules);
									if (z != 0) {
										if(typeGame == GameType.INV) {
											z = this.setPointsInv(z);
										}
										m.setPoints(z);
										m.setMoved(true);
										posi2.setPosition(cont, posiVecino.getPosCol());
										setCellValue(posi2, Cell.EMPTY);
									}
								}
							}
						}
					}
				}
				if(m.getMoved() == true) {
					m.setMaxToken(max());
				}
				puntos += m.getPoints();
				maximo = m.getMaxToken();
				break;
			case DOWN:
				m.setMoved(false);
				for(int i = boardSize - 1; i >= 0; i--) {
					for(int j = boardSize - 1; j >= 0; j--) {
						int cont = boardSize - 1;
						boolean encontrado = false;
						posi.setPosition(j, i); 
						posiVecino.setPosition(j, i);
						posiVecino = posi.neighbor(dir, boardSize);
						if(this.board[posi.getPosFil()][posi.getPosCol()].isEmpty() != true && posiVecino.getPosFil() >= 0){ //Si encuentra una celda con un valor, busca una en esa fila con el mismo valor
							if(board[posiVecino.getPosFil()][posiVecino.getPosCol()].isEmpty() == true)  { //Si la siguiente a la comprobada no tiene valor, comprueba la(s) siguientes
								cont = posiVecino.getPosFil();
								while(cont >= 0 && encontrado == false) {
									if(board[cont][posiVecino.getPosCol()].isEmpty() == true) {
										cont--;
									}
									else {
										encontrado = true;
									}
								}
							}
							else {
								cont = posiVecino.getPosFil();
							}
							if(cont >= 0) {
								if(board[cont][posiVecino.getPosCol()].isEmpty() != true) { //Si ha encontrado una celda con el mismo valor en esa fila y no hay otra entre medias, la fusiona
									z = board[posi.getPosFil()][posi.getPosCol()].doMerge(board[cont][posiVecino.getPosCol()], rules);
									if (z != 0) {
										if(typeGame == GameType.INV) {
											z = this.setPointsInv(z);
										}
										m.setPoints(z);
										m.setMoved(true);
										posi2.setPosition(cont, posiVecino.getPosCol());
										setCellValue(posi2, Cell.EMPTY);
									}
								}
							}
						}
					}
				}
				if(m.getMoved() == true) {
					m.setMaxToken(max());
				}
				puntos += m.getPoints();
				maximo = m.getMaxToken();
			break;
			case LEFT:
				m.setMoved(false);
				for(int j = 0; j < boardSize; j++) {
					for(int i = 0; i < boardSize; i++) {
						int cont = 0;
						boolean encontrado = false;
						posi.setPosition(j, i); 
						posiVecino.setPosition(j, i);
						posiVecino = posi.neighbor(dir, boardSize);
						if(this.board[posi.getPosFil()][posi.getPosCol()].isEmpty() != true && posiVecino.getPosCol() < boardSize){ //Si encuentra una celda con un valor, busca una en esa fila con el mismo valor
							if(board[posiVecino.getPosFil()][posiVecino.getPosCol()].isEmpty() == true)  { //Si la siguiente a la comprobada no tiene valor, comprueba la(s) siguientes
								cont = posiVecino.getPosCol();
								while(cont < boardSize && encontrado == false) {
									if(board[posiVecino.getPosFil()][cont].isEmpty() == true) {
										cont++;
									}
									else {
										encontrado = true;
									}
								}
							}
							else {
								cont = posiVecino.getPosCol();
							}
							if(cont < boardSize) {
								if(board[posiVecino.getPosFil()][cont].isEmpty() != true) { //Si ha encontrado una celda con el mismo valor en esa fila y no hay otra entre medias, la fusiona
									z = board[posi.getPosFil()][posi.getPosCol()].doMerge(board[posiVecino.getPosFil()][cont], rules);
									if (z != 0) {
										if(typeGame == GameType.INV) {
											z = this.setPointsInv(z);
										}
										m.setPoints(z);
										m.setMoved(true);
										posi2.setPosition(cont, posiVecino.getPosCol());
										setCellValue(posi2, Cell.EMPTY);
									}
								}
							}
						}
					}
				}
				if(m.getMoved() == true) {
					m.setMaxToken(max());
				}
				puntos += m.getPoints();
				maximo = m.getMaxToken();
			break;
			case RIGHT:
				m.setMoved(false);
				for(int j = boardSize - 1; j >= 0; j--) {
					for(int i = boardSize - 1; i >= 0; i--) {
						int cont = boardSize - 1;
						boolean encontrado = false;
						posi.setPosition(j, i); 
						posiVecino.setPosition(j, i);
						posiVecino = posi.neighbor(dir, boardSize);
						if(this.board[posi.getPosFil()][posi.getPosCol()].isEmpty() != true && posiVecino.getPosCol() >= 0){ //Si encuentra una celda con un valor, busca una en esa fila con el mismo valor
							if(board[posiVecino.getPosFil()][posiVecino.getPosCol()].isEmpty() == true)  { //Si la siguiente a la comprobada no tiene valor, comprueba la(s) siguientes
								cont = posiVecino.getPosCol();
								while(cont >= 0 && encontrado == false) {
									if(board[posiVecino.getPosFil()][cont].isEmpty() == true) {
										cont--;
									}
									else {
										encontrado = true;
									}
								}
							}
							else {
								cont = posiVecino.getPosCol();
							}
							if(cont >= 0) {
								if(board[posiVecino.getPosFil()][cont].isEmpty() != true) { //Si ha encontrado una celda con el mismo valor en esa fila y no hay otra entre medias, la fusiona
									z = board[posi.getPosFil()][posi.getPosCol()].doMerge(board[posiVecino.getPosFil()][cont], rules);
									if (z != 0) {
										if(typeGame == GameType.INV) {
											z = this.setPointsInv(z);
										}
										m.setPoints(z);
										m.setMoved(true);
										posi2.setPosition(cont, posiVecino.getPosCol());
										setCellValue(posi2, Cell.EMPTY);
									}
								}
							}
						}
					}
				}
				if(m.getMoved() == true) {
					m.setMaxToken(max());
				}
				puntos += m.getPoints();
				maximo = m.getMaxToken();
			break;
		}
						
		return m;
	}
	
	
	 //Este método se encarga de mover las celdas del tablero en caso de que sea posible
	public boolean moveBoard(Direction dir) {
		boolean moved = false;
		switch(dir) {
		case UP:
			for(int i = 1; i < boardSize; i++) {
				for (int j = 0; j < boardSize; j++) {
					if(board[i - 1][j].getValue() == Cell.EMPTY) { //Si encuentra una celda vacía, comprueba en esa columna si hay otra
						int cont = i;
						boolean encontrado = false;
						while(cont < boardSize && encontrado == false) {
							if(board[cont][j].getValue() == Cell.EMPTY) {
								cont++;
							}
							else {
								encontrado = true;
							}
						}
						if(encontrado == true) { //Si ha encontrado una celda no vacía, la desplaza hasta la posición detectada anteriormente
							Position posi = new Position();
							int x = board[cont][j].getValue();
							posi.setPosition(i - 1, j);
							setCellValue(posi, x);							
							posi.setPosition(cont, j);
							setCellValue(posi, Cell.EMPTY);
							moved = true;
						}
					}
				}
			}
			break;
		case DOWN:
			for(int i = boardSize - 2; i >= 0; i--) {
				for (int j = boardSize - 1; j >= 0; j--) {
					if(board[i + 1][j].getValue() == Cell.EMPTY) { //Si encuentra una celda vacía, comprueba en esa columna si hay otra
						int cont = i;
						boolean encontrado = false;
						while(cont >= 0 && encontrado == false) {
							if(board[cont][j].getValue() == Cell.EMPTY) {
								cont--;
							}
							else {
								encontrado = true;
							}
						}
						if(encontrado == true) { //Si ha encontrado una celda no vacía, la desplaza hasta la posición detectada anteriormente
							Position posi = new Position();
							int x = board[cont][j].getValue();
							posi.setPosition(i + 1, j);
							setCellValue(posi, x);							
							posi.setPosition(cont, j);
							setCellValue(posi, Cell.EMPTY);
							moved = true;
						}
					}
				}
			}
			break;
		case LEFT:
			for(int j = 1; j < boardSize; j++) {
				for (int i = 0; i < boardSize; i++) {
					if(board[i][j - 1].getValue() == Cell.EMPTY) { //Si encuentra una celda vacía, comprueba en esa fila si hay otra
						int cont = j;
						boolean encontrado = false;
						while(cont < boardSize && encontrado == false) {
							if(board[i][cont].getValue() == Cell.EMPTY) {
								cont++;
							}
							else {
								encontrado = true;
							}
						}
						if(encontrado == true) { //Si ha encontrado una celda no vacía, la desplaza hasta la posición detectada anteriormente
							Position posi = new Position();
							int x = board[i][cont].getValue();
							posi.setPosition(i, j - 1);
							setCellValue(posi, x);							
							posi.setPosition(i, cont);
							setCellValue(posi, Cell.EMPTY);
							moved = true;
						}
					}
				}
			}
			break;
			
		case RIGHT:
			for(int j = boardSize - 2; j >= 0; j--) {
				for (int i = boardSize - 1; i >= 0; i--) {
					if(board[i][j + 1].getValue() == Cell.EMPTY) { //Si encuentra una celda vacía, comprueba en esa fila si hay otra
						int cont = j;
						boolean encontrado = false;
						while(cont >= 0 && encontrado == false) {
							if(board[i][cont].getValue() == Cell.EMPTY) {
								cont--;
							}
							else {
								encontrado = true;
							}
						}
						if(encontrado == true) { //Si ha encontrado una celda no vacía, la desplaza hasta la posición detectada anteriormente
							Position posi = new Position();
							int x = board[i][cont].getValue();
							posi.setPosition(i, j + 1);
							setCellValue(posi, x);							
							posi.setPosition(i, cont);
							setCellValue(posi, Cell.EMPTY);
							moved = true;
						}
					}
				}
			}
			break;
		}
		return moved;
	}
	
	
	//Si se está jugando al 2048 inverso, establece la puntuación obtenida por una fusión en función del valor obtenido de esta
	private int setPointsInv(int x) {
		int z = 0;
		switch(x) {
		case 1024:
			z = 2;
			break;
		case 512:
			z = 4;
			break;
		case 256:
			z = 8;
			break;
		case 128:
			z = 16;
			break;
		case 64:
			z = 32;
			break;
		case 32:
			z = 64;
			break;
		case 16:
			z = 128;
			break;
		case 8:
			z = 256;
			break;
		case 4:
			z = 512;
			break;
		case 2:
			z = 1024;
			break;											
		}
		return z;
	}
	
	
	//Este método devuelve los puntos logrados en el juego
	public int getPuntos(){
		return this.puntos;
	}
	
	
	//Este método devuelve el valor máximo alcanzado mediante fusiones
	public int getMaximo() {
		return this.maximo;
	}
	
	
	//Este método devuelve una cadena con la que posteriormente se mostrará el tablero
	public String toString() {
		String show = "";
		String vDelimiter = "|";
		String hDelimiter = "-";
		Position pos = new Position();
		
		// -------------------------
		for(int k = 0; k < this.boardSize; k++) {	
			
			for(int j = 0; j < boardSize * 8; j++) {
				show += hDelimiter;
			}
			show += "\n";
			
			for(int i = 0; i < this.boardSize; i++) {
				pos.setPosition(k, i);
				if(this.getCellValue(pos) > 1000){
					show += vDelimiter + "  " + this.getCellValue(pos) + " ";
				}
				else if(this.getCellValue(pos) > 100) {
					show += vDelimiter + "  " + this.getCellValue(pos) + "  ";
				}
				else if(this.getCellValue(pos) > 10) {
					show += vDelimiter + "   " + this.getCellValue(pos) + "  ";
				}
				else if(this.getCellValue(pos) != Cell.EMPTY) {
					show += vDelimiter + "   " + this.getCellValue(pos) + "   ";
				}
				else {
					show += vDelimiter + "       ";
				}
			}
			show += vDelimiter + "\n";
		}
		for(int j = 0; j < boardSize * 8; j++) {
			show += hDelimiter;
		}
		return show;
	}
	
	
	//Este método devuelve el tamaño del tablero
	public int getSize() {
		return boardSize;
	}
	
	
	//Este método comprueba el valor máximo alcanzado mediante las fusiones
	public int max() {
		int maximo = 0;
		for(int i = 0; i < boardSize; i++) {
			for(int j = 0; j < boardSize; j++) {
				if(maximo < this.board[i][j].getValue()) {
					maximo = this.board[i][j].getValue();
				}
			}
		}
		return maximo;
	}
	
	
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 * Este método se encarga de comprobar que cuando el tablero esté completo, es decir, que no haya  *
	 * celdas vacías, comprobar si se pueden realizar fusiones en cualquiera de las cuatro direcciones *
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	private boolean canDoMerge(GameRules r) {
		 boolean ok = false;
		  int i = 0;
		  int j = 0;
		 
		  while (i < this.boardSize && ok == false) {
			  while(j < this.boardSize && ok == false) {
				  
				  if(j + 1 < this.boardSize) {
					  if(r.canMergeNeighbours(this.board[i][j],this.board[i][j + 1]) == true) {
						  ok = true;
					  }
				  }
				  if(i + 1 < this.boardSize) {
					  if(r.canMergeNeighbours(this.board[i][j],this.board[i + 1][j]) == true) {
						  ok = true;
					  }
				  }
				  if(j - 1 >= 0) {
					  if(r.canMergeNeighbours(this.board[i][j], this.board[i][j - 1]) == true) {
						  ok = true;
					  }
				  }
				  if(i - 1 >= 0) {
					  if(r.canMergeNeighbours(this.board[i][j], this.board[i - 1][j]) == true) {
						  ok = true;
					  }
				  }
				  else {
					  j++;
				  }
			  }
			  i++;
		  }  
		  return ok;
	}
	
	
	//Este método comprueba las posiciones vacías del tablero, y las almacena en un array de posiciones
	public boolean anyEmpty(Position[] pos) {
		boolean vacio = false;
		int contador = 0;
		
		for(int i = 0; i < boardSize; i++) {
			for(int j = 0; j < boardSize; j++) {
				if(board[i][j].getValue() == Cell.EMPTY) {
					vacio = true;
					pos[contador] = new Position();
					pos[contador].setPosition(i, j);
					contador++;
				}
			}
		}
		return vacio;
	}
	
	
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 * Este método es el encargado de que al haberse realizado un movimiento, se genere en *
	 * una celda aleatoria un valor 2 ó 4. Para ello, se le pasan como parámetros el array *
	 * donde se almacenan las posiciones vacías, el random para generar aleatoriamente una *
	 * posición dentro de las posibles, y el tamaño del array.                             *
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	public void fillEmpty(Position[] pos, Random r, int sizeEmpty, GameRules g) {
		int x = -1;
		int y = -1;
		x = r.nextInt(sizeEmpty);
		y = g.randomValue(r);
		this.board[pos[x].getPosFil()][pos[x].getPosCol()].setValue(y); 
	}

	
	//Recupera el estado del tablero (si existe)
	public int[][] getState(){
		int[][] m = new int[this.boardSize][this.boardSize];
		Position pos = new Position();
		
		for(int i = 0; i < this.boardSize; i++) {
			for(int j = 0; j < this.boardSize; j++) {
				pos.setPosition(i, j);
				m[i][j] = new Integer(this.getCellValue(pos));
			}
		}
		
		return m;
	}
	
	
	//Guarda el estado del tablero actual
	public void setState(int[][] aState) {
		Position pos = new Position();
		for(int i = 0; i < this.boardSize; i++) {
			for(int j = 0; j < this.boardSize; j++) {
				pos.setPosition(i, j);
				this.setCellValue(pos, aState[i][j]);
			}
		}
	}
	
	
	//Comprueba si el jugador ha perdido la partida
	public boolean lose(GameRules r) throws GameOverException{
		boolean si = false;
		boolean vacio = false;
		
		vacio = this.boardEmpty();
		
		//Comprueba si hay alguno vacío, y si no lo hay, comprueba si se puede realizar algún movimiento
		if(vacio == false) {
			if (this.canDoMerge(r) != true) {
				si = true;
				System.out.println(this.toString());
				throw new GameOverException("You lose.");
			}
		   }
		return si;
	}
	
	private boolean boardEmpty() {
		boolean ok = false;
		int i = 0;
		int j;
		while(i < boardSize && ok == false) {
			j = 0;
			while(j < boardSize && ok == false) {
				if(board[i][j].getValue() == Cell.EMPTY) {
					ok = true;
				}
				else {
					j++;
				}
			}
			i++;
		}
		return ok;
	}
}