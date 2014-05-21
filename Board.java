// -*-Java-*-

import java.util.Arrays;

public class Board implements Comparable<Board>
{
    
    private int
	rows,
	cols,
	
	player,
	score;
    
    //Each player's played tiles.
    private final BitBoard
	p1,
	p2;
    
    //The previous move.
    private Move last;
    
    //chars used in the toString() method.
    public static char
	p1Char = 'X',
	p2Char = 'O',
	spaceChar = '_';
    
    private Board[] valids;
    
    //CONSTRUCTORS
    //----------------------------------------------------------------
    
    //FIRST BOARD
    //--------------------------------
    
    public Board(int rows, int cols)
    {
	this.player = 1;
	this.rows = rows;
	this.cols = cols;
	p1 = new BitBoard(rows, cols);
	p2 = new BitBoard(rows, cols);
	last = null;
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
    
    //================================
    
    //RECURSIVE BOARD
    //--------------------------------
    
    public Board(Board old, Move last)
    {
	this.rows = old.getRows();
	this.cols = old.getCols();
	this.player = old.getPlayer();
	this.p1 = old.p1.clone();
	this.p2 = old.p2.clone();
	this.last = last;
	this.play(last);
    }
    
    //================================
    
    public void init()
    {
	initValids();
	initScore();
    }

    public void initValids()
    {
	Board[] raw = new Board[getRows() * getCols()];
	int index = 0;
	for (int row = 0; row < getRows(); row++)
	    {
		for (int col = 0; col < getCols(); col++)
		    {
			if (check(row, col))
			    {
				raw[index++] = new Board(this, new Move(row, col));
			    }
		    }
	    }
	
	valids = new Board[index--];
	for (; index >= 0; index--)
	    {
		valids[index] = raw[index];
	    }
    }

    //================================================================
    
    //IN DEVELOPMENT
    //----------------------------------------------------------------
    
    public int check(Move move)
    {
	init();
	for (int index = 0; index < valids.length; index++)
	    {
		if (valids[index].getLast().equals(move))
		    {
			return index;
		    }
	    }
	return -1;
    }
    
    //CHECK
    //--------------------------------
    
    private boolean check(int row, int col)
    {
	switch (getPlayer())
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
    
    public Board play(int index)
    {
	return valids[index];
    }
    
    private void play(Move move)
    {
	play(move.row(), move.col());
    }
    
    private void play(int row, int col)
    {
	switch (getPlayer())
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
	player = otherPlayer(player);
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
    
    public Move getLast()
    {
	return last;
    }

    //PLAYER INFO
    //--------------------------------
    
    public int getPlayer()
    {
	return player;
    }
    
    public static int otherPlayer(int player)
    {
	return 3 - player;
    }
    
    public static int playerSign(int player)
    //p1's sign is -, p2's sign is +
    {
	return 2 * player - 3;
    }
    //================================
    
    //================================================================
    
    //SCORING
    //----------------------------------------------------------------

    public int score()
    {
	return score;
    }

    public void initScore()
    {
	score = 0;
	score += winning();
    }

    public int compareTo(Board other)
    {
	return other.score() - this.score();
    }    

    public int winning()
    // output < 0 means p1 is winning, output > 0 means p2 is winning, output == 0 means tie
    {
	return p2.cardinality() - p1.cardinality();
    }
    
    public boolean isFull()
    {
	BitBoard union = (BitBoard)p1.clone();
	union.or(p2);
	return union.cardinality() == union.size();
    }
    
    //================================================================
    
    public String toString()
    {
	String output = getLast() + " <" + score + ">\n";
	for (int row = -1; row < p1.getRows(); row++)
	    //The -1 permits a top-row for indices
	    {
		if (row == -1) output += 'X'; //because it's the top
		else           output += row; //creates a vertical line listing the rows
		
		output += ' '; //leading space between indices and tiles
		for (int col = 0; col < p1.getCols(); col++)
		    {
			if (row == -1) output += col; //or, if in first pass, add the index
			
			else if (p1.getAt(row, col) && p2.getAt(row, col))
			    {
				//this should never happen
				System.out.println("TILE OVERFLOWED; CONTACT ADMINISTRATOR");
				System.exit(1);
			    }
			
			else if (p1.getAt(row, col)) output += p1Char;
			else if (p2.getAt(row, col)) output += p2Char;
			else                         output += spaceChar;
			
			output += ' '; //space between tiles
		    }
		output += '\n'; //newline between rows
	    }
	return output;
    }
    
}