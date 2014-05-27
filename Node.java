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
    
    //CONSTRUCTOR
    //----------------------------------------------------------------

    public Node(Board board, int player)
    {
	this.board = board;
	this.player = player;
    }

    public void calcValids()
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
				raw[size++] = new Node(newboard, -1 * player);
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

}