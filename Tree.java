public class Tree
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
	Node next = root.play(move);
	boolean output = next != null; //valid move?
	if (output) root = next; //play it
	return output; //return if it worked or not
    }

    public void playBest()
    {
	root = root.playBest();
    }

    public void buildLevel()
    {
	//System.out.println("LB");
	root.buildLevel();
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
