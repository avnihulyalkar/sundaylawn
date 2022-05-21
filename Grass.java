import java.awt.*;
public class Grass extends Ground
{
    private Color color;
    private boolean isCut;
   
    public Grass(int xPos, int yPos)
    {
        super( xPos, yPos );
        //make grass dark green = uncut
        //light green = cut
        isCut = false;
        color = new Color(17, 117, 4);
    }
   
    public void cut()
    {
        isCut = true;
        //turn light green when hit by mower
        color = new Color( 68, 214, 49 );
    }
   
    public boolean getCut()
    {
        return isCut;
    }
   
    public void draw( Graphics page )
    {
        page.setColor( color );
        int x = super.getX();
        int y = super.getY();
        int size = super.getSize();
        page.fillRect( x, y, size, size );
    }
}