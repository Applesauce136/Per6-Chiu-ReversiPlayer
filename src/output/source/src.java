import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class src extends PApplet {

private int 
    squareSize = 80,
    rows = 8,
    cols = 8;
private Tree game;
private Thread build;

public void setup()
{
    System.out.printf("setup...%n");
    size(squareSize * rows, squareSize * cols);
    game = new Tree(1);
    noLoop();
    System.out.printf("setup complete%n");
    redraw();
}

public void draw()
{
    System.out.printf("redrawing...%n");
    build = new Thread(game);
    build.start();

    background(0, 150, 0);
    int parts = rows;
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
    System.out.printf("done redrawing%n");
}

public void mouseClicked()
{
    int row = mouseX / squareSize;
    int col = mouseY / squareSize;
    round(new Move(row, col));
    redraw();
    if (game.AIPlayer() == game.currPlayer())
	{
	    System.out.printf("AI:%n");
	    round(null);
	    redraw();
	}
}

public void round(Move move)
{
    System.out.printf("playing...%n", move);
    try 
	{
	    build.join(500);
	} 
    catch (InterruptedException e) 
	{
	    System.out.println("interrupted");
	}
    build.interrupt();
    game.play(move);
    System.out.printf("done playing%n");
}
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "src" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
