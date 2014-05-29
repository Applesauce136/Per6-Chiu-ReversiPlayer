public class Tree
{

    private Node root;
    private int ai;

    public Tree(int ai)
    {
	root = new Node();
	this.ai = (int)Math.signum(ai);
    }

    public boolean play(Move move)
    {
	buildLevel();
	Node next = root.play(move);
	boolean output = next != null;
	if (output) root = next;
	return output;
    }

    public int currPlayer()
    {
	return root.getPlayer();
    }

    public void buildLevel()
    {
	buildLevel(root);
    }

    private void buildLevel(Node curr)
    {
	if (!curr.initialized())
	    {
		curr.init();
	    }
	else
	    {
		for (int index = 0; index < curr.size(); index++)
		    {
			buildLevel(curr.getChild(index));
		    }
	    }
    }

    public String toString()
    {
	return root.toString();
    }

}
