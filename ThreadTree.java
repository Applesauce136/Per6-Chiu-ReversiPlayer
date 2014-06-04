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
			if (this.isInterrupted() || Thread.interrupted())
			    return;
			game.buildLevel();
		    }
	    }
	catch (StackOverflowError e)
	    {
		//System.out.println("OOM");
	    }
    }

}