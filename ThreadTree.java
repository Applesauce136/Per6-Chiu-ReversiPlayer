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
			if (!Thread.interrupted())
			    new Thread(game).start();
			else
			    return;

		    }
	    }
	catch (StackOverflowError e)
	    {
		//System.out.println("OOM");
	    }
    }

}