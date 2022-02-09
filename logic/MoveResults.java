package tp.p3.logic;

public class MoveResults {
	
	private boolean moved = false;  // para identificar si ha habido movimiento
	private int points = 0; 		//para almacenar el número de puntos obtenidos en el movimiento
	private int maxToken = 0; 		//para llevar el valor más alto tras el movimiento
	
	
	//Constructor de esta clase
	public MoveResults() {
		this.moved = false;
		this.points = 0;
		this.maxToken = 0;
	}
	
	
	//Va sumando los puntos introducidos
	public void setPoints(int x) {
		this.points += x;
	}
	
	
	//Devuelve los puntos
	public int getPoints() {
		return this.points;
	}
	
	
	// Introduce el valor máximo de todas las celdas
	public void setMaxToken(int y) { 
		this.maxToken = y;
	}
	
	
	//Devuelve el "best value"
	public int getMaxToken() {
		return this.maxToken;
	}
	

	// Identifica si se ha movido
	public void setMoved(boolean z) {
		this.moved = z;
	}
	
	
	// Devuelve si se ha movido
	public boolean getMoved() {
		return this.moved;
	}
}
