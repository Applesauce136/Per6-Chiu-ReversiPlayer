public class DecisionTree
{

    private Node root;

    public DecisionTree()
    {
	root = new Node();
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
