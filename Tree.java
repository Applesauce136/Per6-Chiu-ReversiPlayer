public class Tree
{

    private Node root;

    public Tree()
    {
	root = new Node();
    }

    public boolean play(Move move)
    {
	Node next = root.play(move);
	if (next != null)
	    {
		root = next;
		return true;
	    }
	else
	    {
		return false;
	    }
    }

    public void buildLevel()
    {
	buildLevel(root);
    }

    public void buildLevel(Node curr)
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

}
