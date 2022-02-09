package tp.p3.logic.multigames;

import java.util.Random;

import tp.p3.logic.Board;
import tp.p3.logic.Cell;
import tp.p3.logic.Position;
import tp.p3.util.MyMathsUtil;

public class RulesFib implements GameRules{
	
	private final int MAXIMO = 144;

	public void addNewCellAt(Board board, Position pos, Random rand) {
		board.setCellValue(pos, this.randomValue(rand));
				
	}

	public int merge(Cell self, Cell other) {
		int x = 0;
		if((MyMathsUtil.nextFib(this.minor(self, other).getValue()) == self.getValue()) || (MyMathsUtil.nextFib(this.minor(self, other).getValue()) == other.getValue()) 
				|| (self.getValue() == 1 && other.getValue() == 1)) {
			self.setValue(other.getValue() + self.getValue());
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
		int y; //valor 1 o 2
		switch (value) {
			case 9:
				y = 2;
				break;
			default:
				y = 1;
				break;				
		}		
		return y;
	}
	
	public Cell minor(Cell self, Cell other) {
		Cell minor = new Cell();
		
		if(self.getValue() <= other.getValue()) {
			minor = self;
		}
		else {
			minor = other;
		}
		
		return minor;
	}
	
	public String toString() {
		return "RulesFib";
	}

}
