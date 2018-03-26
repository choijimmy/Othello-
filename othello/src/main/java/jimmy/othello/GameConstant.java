package jimmy.othello;

public interface GameConstant {

	// empty square
	public static final char EMPTY_COLOR = ' ';
	
	// black chip
	public static final char BLACK_COLOR = 'X';

	// white chip
	public static final char WHITE_COLOR = 'O';

	// size of board
	public static final int BOARD_SIZE = 8;
	
	// mapping of user column input
	public String columnName = "abcdefgh";
	
	// eight different directions constant
	public final Direction UP = new Direction (0,-1);
	public final Direction DOWN = new Direction (0,1);
	public final Direction LEFT = new Direction (-1,0);
	public final Direction RIGHT = new Direction (1,0);
	public final Direction UP_LEFT = new Direction (-1,-1);
	public final Direction DOWN_LEFT = new Direction (-1,1);
	public final Direction UP_RIGHT = new Direction (1,-1);
	public final Direction DOWN_RIGHT = new Direction (1,1);
	
	
}
