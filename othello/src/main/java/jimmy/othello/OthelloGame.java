package jimmy.othello;

import java.util.Scanner;

/*
 * This class take user input and do place the chip on the Othello Board
 */
public class OthelloGame implements GameConstant {

	private OthelloBoard othelloBoard;
	
	public OthelloGame(OthelloBoard othelloBoard) {
		this.othelloBoard = othelloBoard;
	}
	
	// Take user input and place the chip on the board
	private void playGame() {
		Scanner scanner = new Scanner(System.in);
		int col = 0;
		int row = 0;
		
		boolean valid;
		do {
			valid = false;
			while (!valid) {
				System.out.print("Your input: ");
				String input = scanner.next();
				if ( input.trim().length()==2) {
					row = Character.getNumericValue(input.charAt(1))-1; // our row is zero based , so -1
					col = columnName.indexOf(input.charAt(0));
					
					valid = othelloBoard.place(row, col);  //place the chip
				}
				
				if (!valid) {
					System.out.println("Invalid input " + input);
				}
			}
		} while (othelloBoard.nextTurn()); // loop until Unable to perform next Turn ( eg, table full or Both users No valid move )
		
		othelloBoard.outputFinalResult(); // print out the final result of the game
			
		scanner.close();
		
	}
	
	// main function
	public static void main (String[] args) {
		Player blackPlayer = new Player(BLACK_COLOR);
		Player whitePlayer = new Player(WHITE_COLOR);
		
		OthelloBoard othelloBoard = new OthelloBoard(blackPlayer, whitePlayer);
		othelloBoard.initBoard(); // init the board first 
		
		OthelloGame othelloGame = new OthelloGame(othelloBoard);
		othelloGame.playGame();		
	}
	
	
}
