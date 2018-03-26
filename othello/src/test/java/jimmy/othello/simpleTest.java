package jimmy.othello;


import junit.framework.Assert;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


public class simpleTest extends TestCase {


    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public simpleTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( simpleTest.class );
    }
  
	public void testSimplePositiveCase() {
		Player blackPlayer = new Player(GameConstant.BLACK_COLOR);
		Player whitePlayer = new Player(GameConstant.WHITE_COLOR);

		OthelloBoard myBoard = new OthelloBoard(blackPlayer, whitePlayer);
		myBoard.initBoard();
		
		Assert.assertTrue(myBoard.place(2, 3));
		Assert.assertTrue(myBoard.nextTurn());
		Assert.assertEquals(myBoard.getCurrentPlayer(), whitePlayer) ;
		Assert.assertEquals(blackPlayer.getScore(), 4);
		Assert.assertEquals(whitePlayer.getScore(), 1);
		
		
		Assert.assertTrue(myBoard.place(2,4));
		Assert.assertTrue(myBoard.nextTurn());
		Assert.assertEquals(myBoard.getCurrentPlayer(), blackPlayer) ;
		Assert.assertEquals(blackPlayer.getScore(), 3);
		Assert.assertEquals(whitePlayer.getScore(), 3);

		Assert.assertTrue(myBoard.place(2,5));
		Assert.assertTrue(myBoard.nextTurn());
		Assert.assertEquals(myBoard.getCurrentPlayer(), whitePlayer) ;
		Assert.assertEquals(blackPlayer.getScore(), 6);
		Assert.assertEquals(whitePlayer.getScore(), 1);

	}
	
	public void testSimpleNegative() {
		Player blackPlayer = new Player(GameConstant.BLACK_COLOR);
		Player whitePlayer = new Player(GameConstant.WHITE_COLOR);

		OthelloBoard myBoard = new OthelloBoard(blackPlayer, whitePlayer);
		myBoard.initBoard();
		
		Assert.assertTrue(myBoard.place(2, 3));
		Assert.assertTrue(myBoard.nextTurn());
		Assert.assertEquals(myBoard.getCurrentPlayer(), whitePlayer) ;
		Assert.assertEquals(blackPlayer.getScore(), 4);
		Assert.assertEquals(whitePlayer.getScore(), 1);
		
		
		Assert.assertFalse(myBoard.place(2,5)); // invalid move

	}
	
}

