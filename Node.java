// -*-Java-*-

public class Node
{
    //The thing that this class actually wraps around.
    private Board board;

    private int	
	player;

    private Node[] children;
    
    //The previous move.
    private Move last;
    
    //SCORING
    //----------------------------------------------------------------

    public int score()
    {
	return score;
    }
    
    public boolean isFull()
    {
	BitBoard union = (BitBoard)p1.clone();
	union.or(p2);
	return union.cardinality() == union.size();
    }
    
    //================================================================

    //PLAYER INFO
    //--------------------------------
    
    public int getPlayer()
    {
	return player;
    }
    
    public static int otherPlayer(int player)
    {
	return 3 - player;
    }
    
    public static int playerSign(int player)
    //p1's sign is -, p2's sign is +
    {
	return 2 * player - 3;
    }
    //================================

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
    
    public Move getLast()
    {
	return last;
    }

}