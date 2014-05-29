// -*-Java-*-

public class Node
{
    //The thing that this class actually wraps around.
    private Board board;

    private int 
    //The player who is about to move.
	player;

    //The last move that was played; or, the move that generated the current board.
    private Move last;

    //All the possible board states after this one.
    private Node[] children;
    
    //CONSTRUCTOR
    //----------------------------------------------------------------

    public Node(Board board, int player, Move last)
    {
	this.board = board;
	this.player = player;
	this.last = last;
    }

    public Node()
    {
	this(new Board(), -1, null);
    }

    public void init()
    {
	Node[] raw = new Node[board.getRows() * board.getCols()];
	int size = 0;
	for (int row = 0; row < board.getRows(); row++)
	    {for (int col = 0; col < board.getCols(); col++)
		    {
			if (board.check(player, row, col))
			    {
				Board newboard = board.clone();
				newboard.play(player, row, col);
				raw[size++] = new Node(newboard, -1 * player, new Move(row, col));
			    }
		    }
	    }

	children = new Node[size];
	while (--size >= 0)
	    {
		children[size] = raw[size];
	    }
    }

    //================================================================

    //MAIN
    //----------------------------------------------------------------

    public boolean initialized()
    {
	return children != null;
    }
    
    public int size()
    {
	return children.length;
    }

    public Node getChild(int index)
    {
	try
	    {
		return children[index];
	    }
	catch (NullPointerException e)
	    {
		throw new IllegalStateException("Children not initialized");
	    }
    }

    public Node play(Move move)
    {
	for (int index = 0; index < size(); index++)
	    {
		Node curr = getChild(index);
		if (curr.getLast().equals(move))
		    {
			return curr;
		    }
	    }
	return null;
    }

    public Move getLast()
    {
	return last;
    }

    //================================================================

    //SCORING
    //----------------------------------------------------------------
    /*
      Some notes on scores:
      -The magnitude of a score denotes the degree of advantage conferred;
      -The direction (+/-) denotes the player who has the advantage.
      -Negative denotes player 1, positive denotes player 2.
     */

    private static int 
	CORNER_MODIFIER = 10,
	EDGE_MODIFIER = 5;

    public int score()
    {
	int score = 0;
	score += countTiles();
	score += count(BitBoard.corners());
	score += count(BitBoard.edges());
	return score;
    }

    public int countTiles()
    {
	return board.p2().cardinality() - board.p1().cardinality();
    }

    public int count(BitBoard other)
    {
	BitBoard 
	    p1 = board.p1(),
	    p2 = board.p2();
	p1.or(other);
	p2.or(other);
	return p2.cardinality() - p1.cardinality();
    }

    public int branchScore()
    {
	int score = score();
	if (initialized())
	    {
		for (int index = 0; index < size(); index++)
		    {
			score += getChild(index).branchScore();
		    }
	    }
	return score;
    }

    //================================================================

    //PLAYER INFO
    //----------------------------------------------------------------
    /*
      -Within the code, a negative number broadly signifies player 1,
      -while a positive number broadly signifies player 2.
      -When magnitude is not a concern,
      -Use a magnitude of 1.
     */

    public int getPlayer()
    {
	return player;
    }

    //================================================================

    public String toString()
    {
	String output = String.format("Last move: %s%n" + 
				      board.toString(),
				      last);
	return output;
    }

}