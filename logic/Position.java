package tp.p3.logic;

public class Position {
	
	private int fila;
	private int columna;
	
    /////////////////////////////////////////////////////

	/* * * * * * * * * * * * * * * * * * * * * *
	 * Constructor de Position, donde la fila  *  
	 * y la columna la inicializa a -1.        *
	 * * * * * * * * * * * * * * * * * * * * * */
	
	public Position() {
		this.fila = -1;
		this.columna = -1;
	}
	
	/* * * * * * * * * * * * * * * * * * * * * * * * * 
	 * Introduce dos valores (una fila y una columna)*
	 *  y los guarda en fila y columna.              *
	 * * * * * * * * * * * * * * * * * * * * * * * * */
	
	public void setPosition(int x, int y) {
		this.fila = x;
		this.columna = y;
	}
	
	// Devuelve la fila de la posición. 
	
	public int getPosFil() {
		return this.fila;
	}
	
	// Devuelve la columna de la posición.
	
	public int getPosCol() {
		return this.columna;
	}
	
	/* * * * * * * * * * * * * * * * * * * * * * * * * * *
	 * Dependiendo de la dirección devuelve la posicion, *
	 * con fila y columna, de la casilla contigua.       *
	 * * * * * * * * * * * * * * * * * * * * * * * * * * */
	
	public Position neighbor(Direction d, int boardSize) {
		
		Position n = new Position(); 			//Crea el vecino.
		
		switch(d) {
			case UP:
				n.fila = fila + 1;
				n.columna = columna;
				break;
			case DOWN:
				n.fila = fila - 1;
				n.columna = columna;
				break;
			case LEFT:
				n.fila = fila;
				n.columna = columna + 1;
				break;
			case RIGHT:
				 n.fila = fila;
				 n.columna = columna - 1;
				break;
			}
		return n;
	}
}
