// -*-Java-*-

import java.util.Arrays;

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
	if (inBounds(row, col) &&
	    get(row, col) == 0)
	    return false;

	for (int drow = -1; drow <= 1; drow++)
	    {for (int dcol = -1; dcol <= 1; dcol++)
		    {if (drow == 0 && dcol == 0) continue;
			if (!checkDir(player, row + drow, col + dcol, drow, dcol))
			    return false;
		    }
	    }
	return true;
    }

    private boolean checkDir(int player, int row, int col, int drow, int dcol)
    {
	while (inBounds(row, col) &&
	       get(row, col) == -1 * player) //Or, the tile we're on is the other player's.
	    {
		row += drow;
		col += dcol;
	    }
	return (inBounds(row, col) &&
		get(row, col) == player);
    }

    //================================

    //PLAY
    //--------------------------------

    public void play(int player, int row, int col)
    {
	if (inBounds(row, col) &&
	    get(row, col) == 0)
	    return;

	for (int drow = -1; drow <= 1; drow++)
	    {for (int dcol = -1; dcol <= 1; dcol++)
		    {if (drow == 0 && dcol == 0) continue;
			playDir(player, row, col, drow, dcol);
		    }
	    }
	set(row, col, player);
    }

    private void playDir(int player, int row, int col, int drow, int dcol)
    {
	while (inBounds(row, col) &&
	       get(row, col) == -1 * player) //Or, the tile we're on is the other player's.
	    {
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
	return new Board(p1.clone(), p2.clone());
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
}
