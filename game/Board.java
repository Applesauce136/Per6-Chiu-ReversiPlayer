// -*-Java-*-

import java.util.*;
import java.io.*;

public class Board
{
    
    private final int
	rows,
	cols;

    //Each player's played tiles.
    private final BitBoard
	p1,
	p2;
    
    //CONSTRUCTORS
    //----------------------------------------------------------------

    public Board(int rows, int cols)
    {
	this.rows = rows;
	this.cols = cols;
	p1 = new BitBoard(rows, cols);
	p2 = new BitBoard(rows, cols);
    }
    
    public Board(int side)
    {
	this(side, side);
    }
    
    public Board()
    {
	this(8);
	set(3, 4, -1);
	set(4, 3, -1);
	set(3, 3, 1);
	set(4, 4, 1);
	//These flips initiate a standard Reversi game.
	//p1 gets the top-right corner of the 2x2 box.
    }

    private Board(BitBoard p1, BitBoard p2)
    {
	rows = p1.getRows();
	cols = p1.getCols();
	this.p1 = p1;
	this.p2 = p2;
    }

    //================================================================
    
    //MAIN STUFF
    //----------------------------------------------------------------
    
    //CHECK
    //--------------------------------

    public boolean check(int player, int row, int col)
    //Player = the player that is making this move.
    {
	//System.out.println("CALLED check"); //DEBUG
	//System.out.println("    inBounds(row, col): " + inBounds(row, col)); //DEBUG
	//System.out.println("    get(row, col) == 0: " + (get(row, col) == 0)); //DEBUG
	if (inBounds(row, col) &&
	    get(row, col) == 0)
	    {
		//System.out.println("    ENTERING LOOP OF check"); //DEBUG
		for (int drow = -1; drow <= 1; drow++)
		    {for (int dcol = -1; dcol <= 1; dcol++)
			    {if (drow == 0 && dcol == 0) continue;
				if (checkDir(player, row + drow, col + dcol, drow, dcol))
				    {
					//System.out.printf("    TERMINATED ON (%d, %d)\n", drow, dcol); //DEBUG
					return true;
				    }
			    }
		    }
	    }
	//System.out.println("RETURNING FALSE"); //DEBUG
	return false;
    }

    private boolean checkDir(int player, int row, int col, int drow, int dcol)
    {
	//System.out.printf("        CHECKING (%d, %d) FROM (%d, %d)\n", drow, dcol, row, col); //DEBUG
	boolean entered = false;
	while (inBounds(row, col) &&
	       get(row, col) == -1 * player) //Or, the tile we're on is the other player's.
	    {
		//System.out.printf("            PASSED (%d, %d)\n", row, col); //DEBUG
		row += drow;
		col += dcol;
		entered = true;
	    }
	//System.out.printf("        inBounds(row, col): %s%n", inBounds(row, col)); //DEBUG
	//System.out.printf("        get(row, col) == player): %s%n", get(row, col) == player); //DEBUG
	return (inBounds(row, col) &&
		entered &&
		get(row, col) == player);
    }

    //================================

    //PLAY
    //--------------------------------

    public void play(int player, int row, int col)
    {
	//System.out.print("CALLED PLAY\n"); //DEBUG
	if (inBounds(row, col) &&
	    get(row, col) == 0)
	    {
		//System.out.println("    ENTERING LOOP OF play"); //DEBUG
		for (int drow = -1; drow <= 1; drow++)
		    {for (int dcol = -1; dcol <= 1; dcol++)
			    {if (drow == 0 && dcol == 0) continue;
				if (checkDir(player, row + drow, col + dcol, drow, dcol))
				    playDir(player, row + drow, col + dcol, drow, dcol);
			    }
		    }
		set(row, col, player);
	    }
    }

    private void playDir(int player, int row, int col, int drow, int dcol)
    {
	//System.out.printf("        PLAYING IN (%d, %d) FROM (%d, %d)%n", drow, dcol, row, col); //DEBUG
	//System.out.printf(                                                //DEBUG
	//                  "        inBounds(row, col):            %s%n" + //DEBUG
	//                  "        get(row, col) == -1 * player): %s%n",  //DEBUG
	//                  inBounds(row, col),                             //DEBUG
	//                  get(row, col) == (-1 * player));                //DEBUG


	while (inBounds(row, col) &&
	       get(row, col) == -1 * player) //Or, the tile we're on is the other player's.
	    {
		//System.out.printf("            FLIPPING (%d, %d)%n", row, col); //DEBUG
		set(row, col, player);
		row += drow;
		col += dcol;
	    }
    }

    //================================

    //================================================================
    
    //UTILITY FUNCTIONS
    //----------------------------------------------------------------
    
    public int getRows() { return rows; }
    public int getCols() { return cols; }

    public BitBoard p1() { return p1.clone(); }
    public BitBoard p2() { return p2.clone(); }

    public void set(int row, int col, int value)
    {
	p1.setAt(row, col, value == -1);
	p2.setAt(row, col, value == 1);
    }

    public int get(int row, int col)
    {
	if (p1.getAt(row, col))
	    return -1;
	else if (p2.getAt(row, col))
	    return 1;
	else
	    return 0;
    } 
    
    private boolean inBounds(int row, int col)
    {
	return (0 <= row && row < getRows() &&
		0 <= col && col < getCols());
    }

    public Board clone()
    {
	return new Board(p1(), p2());
    }

    //================================================================

    //TOSTRING
    //----------------------------------------------------------------

    public String toString()
    {
	String output = "";
	for (int row = -1; row < p1.getRows(); row++)
	    //The -1 permits a top-row for indices
	    {
		if (row == -1) output += 'X'; //because it's the top
		else           output += row; //creates a vertical line listing the rows
		
		output += ' '; //leading space between indices and tiles
		for (int col = 0; col < p1.getCols(); col++)
		    {
			if (row == -1) output += col; //or, if in first pass, add the index
			else if (p1.getAt(row, col)) output += 'X';
			else if (p2.getAt(row, col)) output += 'O';
			else                         output += '_';
			
			output += ' '; //space between tiles
		    }
		output += '\n'; //newline between rows
	    }
	return output;
    }

    //================================================================

    //BASIC TESTER THING
    //----------------------------------------------------------------

    public static void main(String[] args)
    {
	
	Board game = new Board();

	for (int player = -1; true; )
	    {
		System.out.println(game);
		int[] coords = Driver_Board.getCoords(Driver_Board.getInput( player == -1 ? 'X' : 'O'));
		if (Driver_Board.play(game, player, coords[0], coords[1]))
		    player *= -1;
	    }

    }

    private static class Driver_Board
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

    //================================================================
}
