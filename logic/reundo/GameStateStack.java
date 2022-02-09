package tp.p3.logic.reundo;

import java.util.EmptyStackException;

public class GameStateStack {
	private static final int CAPACITY = 1;
	private GameState[] buffer = new GameState[CAPACITY+1];
	
	
	//Devuelve un estado anterior y borra el actual
	public GameState pop(int pos) {
		GameState g = null;
					try {
						g =	buffer[pos];
						buffer[this.dimension() - 1] = null;
					}catch(EmptyStackException e){
						System.out.println("Array is empty" + e);
					}
		return g;
	}
	
	
	//Inserta un estado después de desplazar el array
	public void push(GameState state) {
		
		try {
				buffer[this.dimension()] = state;
		}catch(ArrayIndexOutOfBoundsException e) {
			int i = 1;
			while(i < this.dimension()) {
				if(i - 1 >= 0) {
					buffer[i - 1] = buffer[i];
					i++;
				}
			}
			buffer[CAPACITY] = state;
		}
	}

	
	//Comprueba si está el estado vacío
	public boolean isEmpty() {
		boolean empty = false;
		if(this.dimension() < 1) {
			empty = true;
		}
		return empty;
		// Devuelve true si la secuencia está vacía
	}
	
	
	//Devuelve la dimensión de array de estados
	public int dimension() {
		int x = 0;
		
		while(x < buffer.length && buffer[x] != null) {
			if(buffer[x] != null) {
				x++;
			}
		}
		
		return x;
	}
	
	
	//Devuelve un estado en la posición del buffer
	public GameState getBuffer(int pos) {
		return this.buffer[pos];
	}
}
