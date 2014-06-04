import java.util.*;
import java.io.*;

public class Driver
{

    public static void main(String[] args)
    {
	game = new Tree(0);
	try
	    {
		game = new Tree(Integer.parseInt(args[0]));
	    }
	catch (IndexOutOfBoundsException e) {}
	catch (NumberFormatException e) {}

	while (true)
	    {
		System.out.println(game);
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
		game.play(coords);
		if (game.currPlayer() == game.AIPlayer())
		    {
			System.out.println(game);
			game.play();
		    }
	    }
    }

    private static Tree game;

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

}