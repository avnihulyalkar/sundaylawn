import java.awt.*;
public abstract class Ground
{
    //ground will be class that is inherited by Obstacles and Grass
    //ground will be initialized as a 2d array in board
    //each spot in array will contain grass or obstacle
    private int x;
    private int y;
    private int size;
   
    public Ground(int xPos, int yPos)
    {
        x = xPos;
        y = yPos;
        size = 99;
    }
   
    public abstract boolean getCut();
   
    public abstract void cut();
   
    public int getX()
    {
        return x;
    }
   
    public int getY()
    {
        return y;
    }
   
    public int getSize()
    {
        return size;
    }
   
    public void draw( Graphics page )
    {
        page.fillRect( x, y, size, size );
    }
}