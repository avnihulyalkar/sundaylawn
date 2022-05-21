import java.awt.*;
public class Obstacle extends Ground
{
    private int x;
    private int y;
    private Color color;
   
    public Obstacle(int xPos, int yPos)
    {
        super( xPos, yPos );
        color = new Color( 107, 55, 3 );
    }
   
    //methods to override Ground (not used here)
    public boolean getCut()
    {
        return true;
    }
   
    public void cut(){}
   
    public void draw( Graphics page )
    {
        page.setColor( color );
        int x = super.getX();
        int y = super.getY();
        int size = super.getSize();
        page.fillRect( x, y, size, size );
    }
}