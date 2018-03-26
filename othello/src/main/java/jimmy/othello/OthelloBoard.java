package jimmy.othello;
/*
 * The actual OthelloBoard allow users to play
 */
public class OthelloBoard implements GameConstant {

	public Player blackPlayer = null;
	public Player whitePlayer = null;
	
	// current player
	private Player currentPlayer = null;
			
	// the actual board
	public char[][] board = new char[BOARD_SIZE][BOARD_SIZE];
	
	public OthelloBoard(Player blackPlayer, Player whitePlayer) {
		this.blackPlayer = blackPlayer;
		this.whitePlayer = whitePlayer;
	}
	
	// return the current player of the board, good for test case usage
	public Player getCurrentPlayer() {
		return currentPlayer;
	}
	
	// init the board 
	public void initBoard() {
		
		// EMPTY out the board
		for (int row = 0 ; row < board.length; ++row) {
			for (int col = 0 ; col < board[row].length ; ++ col) {
				board[row][col] = EMPTY_COLOR;
			}
		}
		
		// first 4 initial chips position
		board[3][3] = WHITE_COLOR;
		board[3][4] = BLACK_COLOR;
		board[4][3] = BLACK_COLOR;
		board[4][4] = WHITE_COLOR;


		// black player first
		currentPlayer = blackPlayer;
		
		// display the game board, player score, etc...
		displayGameStatus();
		
	}
	
	// public function for the caller to place the chip by providing row and column position
	public boolean place(int row, int col) {
		return takeActualMoveIfPossible(currentPlayer, row, col);
	}
	
	private Player switchPlayer ( Player player) {
		return (player == blackPlayer ? whitePlayer : blackPlayer ) ;
	}

	// Function to switch the player, it will check if switch the player is possible or not ( depends on that player has valid move or not )
	// It has 3 possible outcome   
	//    1) able to switch player
	//    2) Unable to switch player ( not valid move ) - then back to original player 
	//    3) Both players have No valid move -- return null
	private Player switchPlayerIfPossible(Player originalPlayer, StringBuilder reason) {
		Player resultPlayer = null;
		
		resultPlayer = switchPlayer(originalPlayer);// try to swtich the player
		if ( ! ableToFlipInAnyDirection(resultPlayer)) { // is this new player has valid move
			reason.append("** " + resultPlayer + " does Not have any valid move\n");
			if ( ableToFlipInAnyDirection(originalPlayer)) {// new player does Not have valid move, how about original player?
				resultPlayer = originalPlayer ; 
			} else {
				reason.append("** " + originalPlayer + " does not have any valid move\n");
				resultPlayer = null;  // indicate Both players have No valid move
			}
		}
		return resultPlayer;								
	}
	
	// Check if the row and col position is a valid move for the input player
	private boolean isValidMove(Player player, int row, int col) {
		
		return  // make sure Not out of board size range and it is empty space
				!outOfBoardRange(row, col) && board[row][col] == EMPTY_COLOR &&   
				
				// check all 8 directions, pass false to indicate we just want to check any valid move but do NOT do actual flip
				(    
			 		doDirection( row, col, player.getColor(), UP, false ) ||   // use || for short circuit ( performance ) 
			 		doDirection( row, col, player.getColor(), DOWN, false ) || // as long as one single direction is good, the move is valid
			 		doDirection( row, col, player.getColor(), LEFT, false ) ||
			 		doDirection( row, col, player.getColor(), RIGHT, false ) ||
			 		doDirection( row, col, player.getColor(), UP_LEFT, false ) ||
			 		doDirection( row, col, player.getColor(), DOWN_LEFT, false ) ||
			 		doDirection( row, col, player.getColor(), UP_RIGHT, false ) ||
			 		doDirection( row, col, player.getColor(), DOWN_RIGHT, false ) 
				);			 		
	}
	
	// Perform the actual move if possible ( eg, flip opposite color chip if do-able ) 
	// If this function return false, it means it is Not a valid move 
	public boolean takeActualMoveIfPossible(Player player, int row, int col ) {
		
		boolean validMove = false;
		
		if (!outOfBoardRange(row, col) && board[row][col] == EMPTY_COLOR ) {
								
			// check all 8 directions , pass true means we are going to flip the opposite color chip if do-able 
			// if validMove return true, it means we have a valid move for *at least* one direction. Otherwise, this is a invalid move
	 		validMove |= doDirection( row, col, currentPlayer.getColor(), UP, true ) ;   				 				
	 		validMove |= doDirection( row, col, currentPlayer.getColor(), DOWN, true);
	 		validMove |= doDirection( row, col, currentPlayer.getColor(), LEFT, true); 
	 		validMove |= doDirection( row, col, currentPlayer.getColor(), RIGHT, true);
	 		validMove |= doDirection( row, col, currentPlayer.getColor(), UP_LEFT, true);
	 		validMove |= doDirection( row, col, currentPlayer.getColor(), DOWN_LEFT, true);
	 		validMove |= doDirection( row, col, currentPlayer.getColor(), UP_RIGHT, true);
	 		validMove |= doDirection( row, col, currentPlayer.getColor(), DOWN_RIGHT, true);
	 		
	 		if (validMove) {
	 			// we have valid move for *at least* once direction	 			
	 			board[row][col] = player.getColor(); // lastly, place the exact position (row/col) chip itself After finishing for all 8 directions
	 		}
		}
		
		return validMove;
		
	}
	
	// helper function to check if the co-ordination is out of board size range
	private boolean outOfBoardRange(int currentRow, int currentCol ) {
		
		return (currentRow>=BOARD_SIZE || currentRow < 0 || currentCol >= BOARD_SIZE || currentCol < 0 );								
	}		
	
	// Do actual move for *single* direction
	// the doActualFlip parameter tells the function to actually flip the opposite color chip   Or  just perform a checking only
	// Return true if this single direction is a valid move
	private boolean doDirection(int row , int column, char color, Direction direction, boolean doActualFlip) {
		boolean walkThruOpposite = false;
		int currentRow = row + direction.getRow();
		int currentCol = column + direction.getColumn();		
		boolean validDirection = false;
		
		while ( !outOfBoardRange(currentRow, currentCol) && (board[currentRow][currentCol] != EMPTY_COLOR) ) {
			if ( board[currentRow][currentCol] == color ) {// hit same color ?
				
				if ( ! walkThruOpposite )   
					break;		// hit same color but No opposite color in between
				
				validDirection = true; // since we have opposite color in between, this is a Valid direction
								
				if (doActualFlip) {
					// the following routine will walk backward ( note: "minus direction ) and flip Opposite player's chip one by one
					currentRow -= direction.getRow();
					currentCol -= direction.getColumn();
					while (! (currentRow == row && currentCol == column)) { // do until move backward to starting point
						board[currentRow][currentCol] = color; // flip the color chip
						currentRow -= direction.getRow();
						currentCol -= direction.getColumn();
					}
				}
				
				break; // done
			} else {
				// hit opposite color
				walkThruOpposite = true;
				currentRow += direction.getRow(); // keep move forward
				currentCol += direction.getColumn();
			}
		} // end while
		
		return validDirection;
			
	}
	
	// Calculate score for both black and white players
	private void calculateScore () {
		whitePlayer.setScore(0);
		blackPlayer.setScore(0);
		
		for (int row = 0 ; row < BOARD_SIZE ; ++row) {
			for (int col = 0 ; col < BOARD_SIZE ; ++ col) {
				if (board[row][col] == BLACK_COLOR) {
					blackPlayer.incrementScore();
				} else if (board[row][col] == WHITE_COLOR) {
					whitePlayer.incrementScore();
				}
			} // end col
		} // end row
	}
	
	// display the both player scores and current player info
	private void displayStatus() {
		System.out.println(blackPlayer + " has score " + blackPlayer.getScore() + " ; " + whitePlayer + " has score " + whitePlayer.getScore());
		
		if (currentPlayer != null) {
			System.out.println("CurrentPlayer is " + currentPlayer.getColor());
		}
	}
	
	// tell the caller is the whole game still have Next Turn( any Valid Move for the whole game) ? 
	// If this function return <false> , it means Either
	//     1) the table is full
	//     2) both players have No valid move
	public boolean nextTurn() {
		StringBuilder reason = new StringBuilder();
		// The reason I didn't check the TableFull first is because table full is very rare ( only happens once for the whole game ) , don't wanna to waste CPU usage
		currentPlayer = switchPlayerIfPossible(currentPlayer, reason);
		
		if (currentPlayer == null) { 
			// indicate Both player have NO valid move, Unable to switch both users
			boolean tableFull = tableFull();
			// No valid move due to table Full ?
			if (tableFull) {
				System.out.println(" ** Table Full ** ");
			} else {
				System.out.println(reason); // print out No valid move for Both users 
			}
			return false; // indicate No more turn/move for the Whole game 
		}
		
		if (reason.length() > 0) {
			// unable to switch 1 user ( only 1 user has No valid move )
			System.out.println(reason);
		}
		
		// after switching users, display the game status
		displayGameStatus();
		
		return true;  // good for next turn
	}
	
	// is table full?
	private boolean tableFull() {
		int totalCount = 0;
		for (int row = 0 ; row < board.length; ++row) {
			for (int col = 0 ; col < board[row].length ; ++ col) {
				if (board[row][col] != EMPTY_COLOR)
					totalCount++;
			}
		}
		
		return totalCount == BOARD_SIZE * BOARD_SIZE;
	}
	
	// Display the game status ( eg, board, score, etc... ) 
	private void displayGameStatus() {
		displayBoard();
		calculateScore();
		displayStatus();	
	}
	
	// Display the finally result -- The winner is....
	public void outputFinalResult() {
		displayGameStatus();
		System.out.println(whitePlayer.getScore() == blackPlayer.getScore() ? "Game over, it is tie game" : "Game over, the winner is " + ( whitePlayer.getScore() > blackPlayer.getScore() ? whitePlayer.getColor() : blackPlayer.getColor()));
	}
	
	// Function to check if the input player has Any valid move for the game   ( this function just perform the checking but does Not actually flip the opposite color! ) 
	public boolean ableToFlipInAnyDirection(Player player) {
		for (int row = 0 ; row < board.length; ++row) {
			for (int col = 0 ; col < board[row].length ; ++ col) {
				if (board[row][col] == EMPTY_COLOR) {
					// if it is empty square, perform the check
					if (isValidMove(player, row, col)) {
						return true; // good, player has valid move in any one of the direction
					}
				}
			}
		}
			
		return false;
	}
	
	// Display the Othello Board
	private void displayBoard() {
		System.out.println("                ");
		for ( int row = 0 ; row < board.length ; ++row) {
			System.out.print(row+1);
			for ( int col = 0 ; col < board[row].length; ++col) {
				if (board[row][col] == EMPTY_COLOR) {
					System.out.print("-");
				} else {
					System.out.print(board[row][col]);
				}
			}
			System.out.println("");
		}
		
		System.out.println(" " + columnName);
		
	}
	
}
