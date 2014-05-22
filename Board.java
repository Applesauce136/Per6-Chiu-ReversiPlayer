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
	p1.flipAt(3, 4);
	p1.flipAt(4, 3);
	p2.flipAt(3, 3);
	p2.flipAt(4, 4);
	//These flips initiate a standard Reversi game.
	//p1 gets the top-right corner of the 2x2 box.
    }

    //================================================================
    
    //MAIN STUFF
    //----------------------------------------------------------------

    //CHECK
    //--------------------------------
    
    private boolean check(int player, int row, int col)
    {
	switch (player)
	    {
	    case 1:  return check(p1, p2, row, col);
	    case 2:  return check(p2, p1, row, col);
	    default: return false;
	    }
    }
    
    private boolean check(BitBoard act, BitBoard pas, int row, int col)
    {
	for (int drow = -1; drow <= 1; drow++)
	    {
		for (int dcol = -1; dcol <= 1; dcol++)
		    {if (drow == 0 && dcol == 0) continue;
			if (check(act, pas, row + drow, col + dcol, drow, dcol))
			    {
				return true;
			    }
		    }
	    }
	return false;
    }
    
    private boolean check(BitBoard act, BitBoard pas, int row, int col, int drow, int dcol)
    {
	boolean flipped = false;
	while ( inBounds(row, col) &&
		pas.getAt(row, col))
	    {
		flipped = true;
		row += drow;
		col += dcol;
	    }

	return ( inBounds(row, col) &&
		 act.getAt(row, col) &&
		 flipped);
    }
    
    //================================
    
    //PLAY
    //--------------------------------
    
    private void play(int player, int row, int col)
    {
	switch (player)
	    {
	    case 1:  play(p1, p2, row, col); break;
	    case 2:  play(p2, p1, row, col); break;
	    default: return;
	    }
    }
    
    private void play(BitBoard act, BitBoard pas, int row, int col)
    {
	for (int drow = -1; drow <= 1; drow++)
	    {
		for (int dcol = -1; dcol <= 1; dcol++)
		    {if (drow == 0 && dcol == 0) continue;
			if (check(act, pas, row + drow, col + dcol, drow, dcol))
			    play(act, pas, row + drow, col + dcol, drow, dcol);
		    }
	    }
	act.flipAt(row, col);
    }
    
    private void play(BitBoard act, BitBoard pas, int row, int col, int drow, int dcol)
    {
	while ( inBounds(row, col) &&
		pas.getAt(row, col))
	    {
		flip(row, col);
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
    
    public BitBoard clone()
    {
	return (BitBoard)this.clone();
    }

    private boolean empty(int row, int col)
    {
	return !( p1.getAt(row, col) || 
		  p2.getAt(row, col) );
    }
    
    private void flip(int row, int col)
    {
	p1.flipAt(row, col);
	p2.flipAt(row, col);
    }
    
    private boolean inBounds(int row, int col)
    {
	return (0 <= row && row < getRows() &&
		0 <= col && col < getCols());
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
