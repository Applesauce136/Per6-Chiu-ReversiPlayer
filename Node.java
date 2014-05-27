// -*-Java-*-

public class Node
{
    //The thing that this class actually wraps around.
    private Board board;

    private int 
    //The player who is about to move.
	player,
    //The score of the board.
	score;

    //All the possible board states after this one.
    private Node[] children;
    
    //The previous move.
    private Move last;
    
    //SCORING
    //----------------------------------------------------------------
    /*
      Some notes on scores:
      -The magnitude of a score denotes the degree of advantage conferred;
      -The direction (+/-) denotes the player who has the advantage.
      -Negative denotes player 1, positive denotes player 2.
     */
    
    //================================================================

    //PLAYER INFO
    //----------------------------------------------------------------
    /*
      -Within the code, a negative number broadly signifies player 1,
      -while a positive number broadly signifies player 2.
      -When magnitude is not a concern,
      -Use a magnitude of 1.
     */

    //================================================================

    public void initValids()
    {
	Board[] raw = new Board[getRows() * getCols()];
	int index = 0;
	for (int row = 0; row < getRows(); row++)
	    {
		for (int col = 0; col < getCols(); col++)
		    {
			if (check(row, col))
			    {
				raw[index++] = new Board(this, new Move(row, col));
			    }
		    }
	    }
	
	valids = new Board[index--];
	for (; index >= 0; index--)
	    {
		valids[index] = raw[index];
	    }
    }

}