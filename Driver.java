import java.util.*;
import java.io.*;

public class Driver
{

    public static void main(String[] args)
    {
	while (true)
	    {
		System.out.println(game);
		String input = getInput(game.currPlayer() == -1 ? "X" : "O");
		Move coords = getCoords(input);
		game.play(coords);
	    }
    }

    private static Tree game = new Tree(0);

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