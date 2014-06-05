import java.util.*;
import java.io.*;

public class Driver
{

    public static void main(String[] args)
    {
	int ai = 0;
	try
	    {
		ai = Integer.parseInt(args[0]);
	    }
	catch (IndexOutOfBoundsException e) {}
	catch (NumberFormatException e) {}
	game = new Tree(ai);

	while (true)
	    {
		update();
		Thread thing = new Thread(game);
		thing.start();
		String input = getInput(game.currPlayer() == -1 ? "X" : "O");
		Move coords = getCoords(input);
		try
		    {
			thing.join(500);
		    }
		catch (InterruptedException e) 
		    {
			System.out.println("Something went wrong, sorry!");
			return;
		    }
		thing.interrupt();
		game.play(coords);
		if (game.currPlayer() == game.AIPlayer())
		    {
			update();
			game.play();
		    }
	    }
    }

    private static Tree game;

    //OUTPUT
    //----------------------------------------------------------------

    private static void update()
    {
	System.out.println(game);
    }

    //================================================================    

    //INPUT
    //----------------------------------------------------------------

    private static Move getCoords(String input)
    {
	Scanner s = new Scanner(input);
	return new Move( s.nextInt(), s.nextInt() ); 
    }

    private static String getInput(String prompt)
    {
	System.out.print(prompt + ": > ");
	BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
	try
	    {
		return in.readLine();
	    }
	catch (IOException e)
	    {
		return "";
	    }
    }

    //================================================================

}
