package tp.p3.logic.reundo;

import tp.p3.logic.Position;

public class GameState {
	private int[][] boardState; // estado del tablero
	private int score;
	
	//Establece la puntuación a 'x'
	public GameState(int x){
		this.score = x;
	}	
	
	//Establece el estado del tablero en un array auxiliar
	public void setState(int[][] x) {
		this.boardState = new int[x.length][x.length];
		for(int i = 0; i < x.length; i++) {
			for(int j = 0; j < x.length; j++) {
				this.boardState[i][j] = x[i][j];
			}
		}
		
	}
	
	//Recupera el estado del tablero 
	public int getCellState(Position pos) {
		return boardState[pos.getPosFil()][pos.getPosCol()];
	}
	
	//Devuelve la puntuación del tablero
	public int getScore() {
		return this.score;
	}
}
