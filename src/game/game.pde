void setup()
{
    size(640, 640);
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
}

void mousePressed()
{
    
}