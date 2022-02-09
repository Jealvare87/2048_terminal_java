package tp.p3.logic.multigames;
import tp.p3.exceptions.GameOverException;
import tp.p3.logic.Board;
import tp.p3.logic.Cell;
import tp.p3.logic.Position;
import tp.p3.util.MyMathsUtil;

import java.util.Random;

public interface GameRules {
	void addNewCellAt(Board board, Position pos, Random rand); //Añade una celda nueva en la posición que recibe con un valor aleatorio en función del tipo de juego (Rules2048, RulesFib, ó RulesInverse)
	int merge(Cell self, Cell other); //Llama al doMerge que en función del tipo de juego buscará fusionar las celdas que pueda
	int getWinValue(Board board); //Devuelve el valor máximo que se puede conseguir en el tipo de juego al que se esté jugando actualmente
	boolean win(Board board); //Comprueba si el jugador ha ganado, comprobando si ha llegado al valor máximo del juego
	public int randomValue(Random r); //Genera un valor aleatorio en función del tipo de juego, para luego colocarlo en una celda
	default boolean lose(Board board) throws GameOverException { //Comprueba si el jugador ha perdido
		return board.lose(this);
	}
	
	default boolean canMergeNeighbours(Cell cell1, Cell cell2) {//Devuelve si dos celdas se pueden fusionar
		boolean ok = false;
			
		if(this.toString().equals("Rules2048") || this.toString().equals("RulesInverse")) {
			if(cell1.getValue() == cell2.getValue()) {
				ok = true;
			}
		}
		else if(this.toString().equals("RulesFib")) {
			if((MyMathsUtil.nextFib(cell1.getValue()) == cell2.getValue() || (MyMathsUtil.nextFib(cell2.getValue()) == cell1.getValue()) || (cell1.getValue() == 1 && cell2.getValue() == 1))){
				ok  = true;
			}
		}
		return ok;
	}
	
	default Board createBoard(int size) { //Crea un tablero nuevo
		Board b = new Board(size);
		return b;
	}
	default void addNewCell(Board board, Random rand) { //Genea una nueva celda
		this.addNewCellAt(board, board.randomCell(rand), rand);
	}
	default void initBoard(Board board, int numCells, Random rand) { //Inicializa el tablero generado anteriormente
		int cont = 0;
		while(cont < numCells) {
			this.addNewCell(board, rand);
			cont++;
		}
	}
}
