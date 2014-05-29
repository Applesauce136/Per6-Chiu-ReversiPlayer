public class Driver
{

    private static boolean play(Board game, int player, int row, int col)
    {
	if (game.check(player, row, col))
	    {
		game.play(player, row, col);
		return true;
	    }
	return false;
    }

    private static int[] getCoords(String input)
    {
	Scanner s = new Scanner(input);
	return new int[] {s.nextInt(), s.nextInt()}; 
    }

    private static String getInput(char prompt)
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