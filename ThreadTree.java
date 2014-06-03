public class ThreadTree extends Thread
{

    private Tree game;

    public ThreadTree(Tree game)
    {
	this.game = game;
    }

    public void run()
    {
	try
	    {
		while (true)
		    {
			game.buildLevel();
		    }
	    }
	catch (StackOverflowError e)
	    {
		return;
	    }
    }

}