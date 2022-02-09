package tp.p3.logic;

import tp.p3.logic.multigames.GameRules;
public class Cell {

	private int x;
    public static final int EMPTY = 0;

    /////////////////////////////////////////////////////

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     * Constructor de Cell con la celda inicializada al valor vacio. *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
    
    public Cell() {
    	this.x = EMPTY;
    }
    
    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 * Si la casilla está vacia entonces devuelve un "true". *
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
    
	public boolean isEmpty() {
		boolean ok = false;
		if(x == EMPTY) {
			ok = true;
		}
		return ok;
	}
	
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 * Mira si dos celdas son iguales y en tal caso duplica la casilla.*
	 * La casilla vecina se vacia.									   *
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	
	public int doMerge(Cell neighbourCell, GameRules rules) {
		return rules.merge(this, neighbourCell);
	}
	
	/* * * * * * * * * * * * * * * * *
	 * Introduce un valor a la celda.*
	 * * * * * * * * * * * * * * * * */
	
	public void setValue(int p) {
		this.x = p;
	}
	
	/* * * * * * * * * * * * * * * * *
	 * Devuelve el valor de la celda.*
	 * * * * * * * * * * * * * * * * */
	
	public int getValue() {
		return x;
	}
}



