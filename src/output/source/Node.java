// -*-Java-*-

import java.util.*;

public class Node implements Comparable<Node>, Runnable
{
    //The thing that this class actually wraps around.
    private Board board;

    private int 
    //The player who is about to move.
	player;

    //The last move that was played; or, the move that generated the current board.
    private Move last;

    //All the possible board states after this one.
    private PriorityQueue<Node> children;
    
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
	children = new PriorityQueue<Node>();
	for (int row = 0; row < board.getRows(); row++)
	    {for (int col = 0; col < board.getCols(); col++)
		    {
			if (board.check(player, row, col))
			    {
				Board newboard = board.clone();
				newboard.play(player, row, col);
				children.add(new Node(newboard, -1 * player, new Move(row, col)));
			    }
		    }
	    }
    }

    //================================================================

    //MAIN
    //----------------------------------------------------------------

    public int check(int row, int col)
    {
	return board.get(row, col);
    }

    public boolean initialized()
    {
	return children != null;
    }
    
    public int size()
    {
	if (!initialized()) init();
	return children.size();
    }

    public Node find(Move move)
    {
	if (!initialized()) init();
	for (Node child : children)
	    {
		if (child.getLast().equals(move))
		    {
			return child;
		    }
	    }
	return null;
    }

    public Node best()
    {
	if (!initialized()) init();
	return children.poll();
    }

    public Move getLast()
    {
	return last;
    }

    public void run()
    {
	while (Runtime.getRuntime().freeMemory() > 1000 * 100 &&
	       !Thread.interrupted())
	    {
		if (!initialized()) { init(); return; }
		if (Thread.interrupted()) return;
		for (Node child : children)
		    {
			try
			    {
				Thread curr = new Thread(child, child.getLast().toString());
				curr.start();
			    }
			catch (OutOfMemoryError e) {return;}
		    }
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
	p1.and(other);
	p2.and(other);
	return p2.cardinality() - p1.cardinality();
    }

    public int branchScore()
    {
	int score = score();
	if (initialized())
	    {
		for (Node child : children)
		    {
			score += child.branchScore();
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
				      //"Score: %d%n" +
				      "%s",
				      last,
				      //branchScore(),
				      board.toString());
	return output;
    }

    public int compareTo(Node that)
    {
	if (that == null)
	    {
		return Integer.MIN_VALUE;
	    }

	if (this.getPlayer() == that.getPlayer())
	    return player * (this.branchScore() - that.branchScore());
	else
	    throw new IllegalArgumentException("Player no. does not match");
    }

}
