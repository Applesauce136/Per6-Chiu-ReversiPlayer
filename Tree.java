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
	Node next = root.find(move);
	boolean output = next != null; //valid move?
	if (output) play(next); //play it
	return output; //return if it worked or not
    }

    public boolean play()
    {
	play(root.best());
	return true;
    }

    private void play(Node next)
    {
	root = next;
    }

    public void run()
    {
	if (!Thread.interrupted())
	    new Thread(root).start();
	else
	    return;
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
