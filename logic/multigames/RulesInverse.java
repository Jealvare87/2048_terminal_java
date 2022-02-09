package tp.p3.logic.multigames;

import java.util.Random;

import tp.p3.logic.Board;
import tp.p3.logic.Cell;
import tp.p3.logic.Position;

public class RulesInverse implements GameRules {
	
	private final int MAXIMO = 2;

	public void addNewCellAt(Board board, Position pos, Random rand) {
		board.setCellValue(pos, this.randomValue(rand));
				
	}

	public int merge(Cell self, Cell other) {
		int x = 0;
		if(self.getValue() == other.getValue()) {
			self.setValue(other.getValue() / 2);
			other.setValue(0);
			x = self.getValue();
		}
		return x;
	}

	public int getWinValue(Board board) {
		return board.getMaximo();
	}

	public boolean win(Board board) {
		boolean win = false;
		
		if(this.getWinValue(board) == MAXIMO) {
			win = true;
		}
		
		return win;
	}
	
	public int randomValue(Random r) {
		int value = r.nextInt(10);
		int y; //valor 1024 o 2048
		switch (value) {
			case 9:
				y = 1024;
				break;
			default:
				y = 2048;
				break;				
		}		
		return y;
	}

	public String toString() {
		return "RulesInverse";
	}
}
