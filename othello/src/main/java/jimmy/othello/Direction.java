package jimmy.othello;

public class Direction {

	private int row;
	private int column;
	
	public Direction(int column, int row) {
		this.column = column;
		this.row = row;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getColumn() {
		return column;
	}

	public void setColumn(int column) {
		this.column = column;
	}
	
	
}
