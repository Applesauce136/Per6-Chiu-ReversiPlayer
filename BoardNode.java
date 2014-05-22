// -*-Java-*-

public class BoardNode
{

    private int	
	player,
	score;

    private BoardNode[] children;
    
    //The previous move.
    private Move last;

    private Board board;
    
    //SCORING
    //----------------------------------------------------------------

    public int score()
    {
	return score;
    }

    public void initScore()
    {
	score = 0;
	score += winning();
    }

    public int compareTo(Board other)
    {
	return other.score() - this.score();
    }    

    public int winning()
    // output < 0 means p1 is winning, output > 0 means p2 is winning, output == 0 means tie
    {
	return p2.cardinality() - p1.cardinality();
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