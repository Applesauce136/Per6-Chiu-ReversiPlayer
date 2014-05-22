// -*-Java-*-

import java.io.*;
import java.util.Scanner;
import java.util.NoSuchElementException;

public class Driver_Othello
{
    
    private static void help()
    {
	String output = (
			 "Hello player!" + "\n" +
			 "To select a square, enter its coordinates." + "\n" +
			 "Input the number on the left first, then the number on top." + "\n" +
			 "The character by the prompt represents whose turn it is." + "\n" +
			 "You can change the characters used by typing either WBmode or XOmode." + "\n" +
			 "The game will automatically end when the board is full," + "\n" +
			 "and you can also resign by typing 'resign'." + "\n" +
			 
			 "Type 'help' to re-display this help message." + "\n" +
			 "The board shall be reprinted:" + "\n" +
			 "");
	
	System.out.println(output);
	update();
    }
    
    public static void main(String[] args)
    {
	board = new Board();
	help();
	play();
    }
    
    private static Board board;
    
    private static void play()
    {
	while (halfRound())
	    {
		update();
	    }
	
	int winner = (board.isFull())? board.winning() : Board.playerSign(Board.otherPlayer(board.getPlayer()));
	//winner > 0 means p1 wins,
	//winner < 0 means p2 wins
	
	//thus if it was p1's turn, p2 wins
	//and if it was p2's turn, p1 wins
	
	
	System.out.println("\n================================");
	if (winner < 0)
	    {
		System.out.println(board.p1Char + " wins!");
	    }
	else if (winner > 0)
	    {
		System.out.println(board.p2Char + " wins!");
	    }
	else if (winner == 0)
	    {
		System.out.println("It's a tie!");
	    }
	update();
    }
    
    private static boolean halfRound()
    //true = do another
    {
	char prompt;
	switch (board.getPlayer())
	    {
	    case 1: prompt = Board.p1Char; break;
	    case 2: prompt = Board.p2Char; break;
	    default: return false;
	    }
	
	do
	    {
		String input = getInput(prompt);
		Move coords = getCoords(input);
		
		if      ("resign".equals(input))           return false;
		else if ("WBmode".equals(input))           WBmode();
		else if ("XOmode".equals(input))           XOmode();
		else if ("help".equals(input))             help();
		else if (coords == null)                   invalid();
		else if (-1 == board.check(coords))        badSquare();
		else return play(board.check(coords));
	    }
	while (true);
    }
    
    private static boolean play(int index)
    {
	board = board.play(index);
	return !board.isFull();
    }
    
    private static void update()
    {
	System.out.println(board);
    }
    
    private static void invalid()
    {
	System.out.println("Invalid input.");
    }
    
    private static void badSquare()
    {
	System.out.println("You can't play that square!");
    }
    
    private static Move getCoords(String input)
    {
	Scanner s = new Scanner(input);
	Move coords = null;
	try
	    {
		coords = new Move(s.nextInt(), s.nextInt());
	    }
	catch (NoSuchElementException e)
	    {
		return null;
	    }
	return coords;
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
    
    private static void WBmode()
    {
	Board.p1Char = 'W';
	Board.p2Char = 'B';
	update();
    }
    
    private static void XOmode()
    {
	Board.p1Char = 'X';
	Board.p2Char = 'O';
	update();
    }
    
}