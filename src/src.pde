private int 
    squareSize = 80,
    rows = 8,
    cols = 8;
private Tree game;
private Thread build;

void setup()
{
    size(squareSize * 8, squareSize * 8);
    game = new Tree(1);
    noLoop();
    redraw();
}

void draw()
{
    background(0, 150, 0);
    int parts = 8;
    for (int count = 1; count <= parts - 1; count++)
	{
	    line(count * width / parts, height, count * width / parts, 0);
	    line(0, count * height / parts, width, count * height / parts);
	}

    for (int row = 0; row < rows; row++)
	{for (int col = 0; col < cols; col++)
		{
		    if (game.check(row, col) == -1)
			fill(255);
		    else if (game.check(row, col) == 1)
			fill(0);
		    else
			continue;
		    ellipse(squareSize * row + squareSize / 2, squareSize * col + squareSize / 2, squareSize - 10, squareSize - 10);
		}
	}

    build = new Thread(game);
    build.start();
}



void mouseClicked()
{
    try {build.join(500);} catch (InterruptedException e) {}
    build.interrupt();
    int row = mouseX / squareSize;
    int col = mouseY / squareSize;
    game.play(new Move(row, col));
    redraw();
    if (game.AIPlayer() == game.currPlayer())
	{
	    try 
		{
		    build.join(500);
		} 
	    catch (InterruptedException e) 
		{
		    System.out.println("interrupted");
		}
	    build.interrupt();
	    game.play();
	    redraw();
	}
}
