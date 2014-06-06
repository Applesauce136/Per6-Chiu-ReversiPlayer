public class Tree implements Runnable
{

    private Node root;

    //-1 if player 1 is the AI, 1 if player 2 is the AI, 0 if two-player game
    private int ai;

    public Tree(int ai)
    {
	root = new Node();
	this.ai = (int)Math.signum(ai);
    }

    public boolean play(Move move)
    {
	Node next = 
	    (move == null) ?
	    root.best() :
	    root.find(move);
	boolean output = next != null; //valid move?
	if (output) play(next); //play it
	return output; //return if it worked or not
    }

    private void play(Node next)
    {
	root = next;
	Runtime.getRuntime().gc();
    }

    public void run()
    {
	while (Runtime.getRuntime().freeMemory() >= 1000000)
	    {
		Thread curr;
		try
		    {
			curr = new Thread(root, "root");
		    }
		catch (OutOfMemoryError e)
		    {
			return;
		    }
		if (!Thread.interrupted())
		    curr.start();
		else
		    {
			curr.interrupt();
			return;
		    }
	    }
    }

    public int check(int row, int col)
    {
	return root.check(row, col);
    }

    public int currPlayer()
    {
	return root.getPlayer();
    }

    public int AIPlayer()
    {
	return ai;
    }

    public String toString()
    {
	return root.toString();
    }

}
