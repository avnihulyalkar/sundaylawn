import javax.swing.*;
import java.awt.*;
public class Mower extends JLabel
{
    private int x;
    private int y;
    private int size;
    private Color color;
    private int xInc;
    private int yInc;
    private boolean bonusUp;
    //for JLabel
    private ImageIcon image;
   
    public Mower()
    {
        x = 100;
        y = 500;
        xInc = 3;
        yInc = 3;
        size = 100;
        color = new Color(0,0,0);
        //for JLabel
        this.setBounds( x, y, 100, 100 );
        image = new ImageIcon( "MOWER.png" );
        this.setIcon( image );
        bonusUp = false;
    }
   
    public void move(String direction)
    {
        if ( direction == "UP" && y > 0 )
        {
            y -= yInc;
            x = (x + 50) / 100 * 100;
            image = new ImageIcon( "MOWER.png" );
            //bonusUp = true;
        }
        if ( direction == "DOWN" && y + size < 600 )
        {
            y += yInc;
            x = (x + 50) / 100 * 100;
            //bonusUp = true;
        }
        if ( direction == "RIGHT" && x + size < 1000 )
        {
            x += xInc;
            y = (y + 50) / 100 * 100;
            //bonusUp = true;
        }
        if ( direction == "LEFT" && x > 0 )
        {
            x -= xInc;
            y = (y + 50) / 100 * 100;
            //bonusUp = true;
        }
        this.setLocation( x, y );
       
    }
   
    public boolean getBonusUp()
    {
        return bonusUp;
    }
       
    public int getX()
    {
        return x;
    }
   
    public int getY()
    {
        return y;
    }
   
    public void speedUp()
    {
        xInc++;
        yInc++;
    }

   
    public void draw( Graphics page )
    {
        page.setColor( color );
        
        int myY = y + size;
        //draws an X underneath mower, so when mower dies, this X shows
        int[] xPoints = {x, x+5, x+100, x+95};
        int[] yPoints = {myY, myY+5, myY-100, myY-105};
        page.fillPolygon( xPoints, yPoints, 4 );
        
        int newX = x;
        int newY = myY - 95;
        int[] xPts = {newX, newX+100, newX+105, newX+5};
        int[] yPts = {newY, newY+100, newY+95, newY-5};
        page.fillPolygon( xPts, yPts, 4 );
    }
}
