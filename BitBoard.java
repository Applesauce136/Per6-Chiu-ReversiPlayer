// -*-Java-*-
/**
   Two-dimensional array version of BitSet.
   Adds two-dimensional array functionality.
 */

import java.util.BitSet;

public class BitBoard extends BitSet
{

    private final int
	cols,
	rows;

    //CONSTRUCTORS
    //----------------------------------------------------------------
    public BitBoard(int rows, int cols)
    {
	super(cols * rows); //BitSet of length cols * rows
	this.cols = cols;
	this.rows = rows;
    }

    public BitBoard(int side)
    {
	this(side, side);
    }

    public BitBoard()
    {
	this(8);
    }
    //================================================================

    //2D FUNCTIONS
    //----------------------------------------------------------------

    public int getRows(){ return rows; }
    public int getCols(){ return cols; }

    public boolean getAt(int row, int col)
    {
	if ( inBounds(row, col) )
	    {
		return get( lineify(row, col) );
		//lineify converts a coordinate pair into an index (see below)
	    }
	else
	    {
		throw new IndexOutOfBoundsException();
	    }
    }

    public void setAt(int row, int col, boolean value)
    {
	if ( inBounds(row, col) )
	    {
		set( lineify(row, col), value );
		//lineify converts a coordinate pair into an index (see below)
	    }
	else
	    {
		throw new IndexOutOfBoundsException();
	    }
    }
    //for the record, I know for a FACT that Lisp macros would make this so much nicer

    public void setAt(int row, int col)
    {
	setAt(row, col, true);
    }

    //================================================================

    //HELPER FUNCTIONS
    //----------------------------------------------------------------

    public BitBoard clone()
    {
	return (BitBoard)super.clone();
    }

    private boolean inBounds(int row, int col)
    {
	//Note that if for whatever reason you need to check just one, you can simply input 0 for the other
	return (0 <= row && row < rows) && (0 <= col && col < cols);
    }

    private int lineify(int row, int col)
    {
	return (row * rows) + col;
	//think:
	//go down ROW rows, then go right COL cols
    }

    //================================================================



    public String toString()
    {
	String output = "";
	for (int row = 0; row < rows; row++) //iterate by row
	    {
		for (int col = 0; col < cols; col++) //iterate by col
		    {
			if ( getAt(row, col) )
			    {
				output += '1'; //true = 1
			    }
			else
			    {
				output += '0';
			    }
			output += ' '; //put a space between each thing
		    }
		output += '\n'; //put a newline at the end of each row
	    }
	return output;
    }

}