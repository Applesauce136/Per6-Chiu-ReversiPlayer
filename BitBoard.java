// -*-Java-*-

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

    public void setAt(int row, int col)
    {
	setAt(row, col, true);
    }

    public void flipAt(int row, int col)
    {
	if ( inBounds(row, col) )
	    {
		flip( lineify(row, col));
		//lineify converts a coordinate pair into an index (see below)
	    }
	else
	    {
		throw new IndexOutOfBoundsException();
	    }
    }

    //for the record, I know for a fact that Lisp macros would make this so much nicer   
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

    //PRESETS
    //----------------------------------------------------------------

    public static void main(String[] args)
    {
	System.out.println(corners());
	System.out.println(edges());
    }

    public static BitBoard corners()
    {
	BitBoard output = new BitBoard();
	output.setAt(0, 0);
	output.setAt(output.getRows() - 1, 0);
	output.setAt(0, output.getRows() - 1);
	output.setAt(output.getRows() - 1, output.getRows() - 1);
	return output;
    }

    public static BitBoard edges()
    {
	BitBoard output = new BitBoard();
	for (int count = 0; count < output.getRows(); count++)
	    {
		output.setAt(0, count);
		output.setAt(count, 0);

		output.setAt(output.getRows() - 1, count);
		output.setAt(count, output.getRows() - 1);
	    }
	return output;
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