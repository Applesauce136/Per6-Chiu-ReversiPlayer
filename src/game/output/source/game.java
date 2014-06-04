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

public class game extends PApplet {

public void setup()
{
    size(640, 640);
    noLoop();
    redraw();
}

public void draw()
{
    background(0, 150, 0);
    int parts = 8;
    for (int count = 1; count <= parts - 1; count++)
	{
	    line(count * width / parts, height, count * width / parts, 0);
	    line(0, count * height / parts, width, count * height / parts);
	}
}
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "game" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
