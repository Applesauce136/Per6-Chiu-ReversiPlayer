// -*-Java-*-

public class Move
{
    
    private final int
	row,
	col;
    
    public Move(int row, int col)
    {
	this.row = row;
	this.col = col;
    }
    
    public int row() { return row; }
    public int col() { return col; }
    
    public boolean equals(Move that)
    {
	return (this.row() == that.row() &&
		this.col() == that.col() );
    }

    public String toString()
    {
	return String.format("(%d, %d)", row, col);
    }
    
}
