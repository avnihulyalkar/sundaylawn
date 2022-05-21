import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class Board extends JPanel implements KeyListener, ActionListener
{
    private Mower m;
    private JLabel ending;
    private JLabel gameWon;
    private Ground[][] gArray;
    private Ground current;
   
    private boolean done;
    private JLabel score;
    private JLabel bonusLabel;
    int points = 0;
    //every consecutive block, bonus+=2
    //every hit block, points += 1 + bonus
    int bonus = 0;
    int timer = 0;
    private JButton restart;
    private JButton nextLevel;
    private JLabel levelLabel;
    private int level;
   
    private boolean bonusUp = true;
    private String direction = "STOP";
    public Board()
    {
        this.setLayout( null );
        this.setBackground( new Color( 2, 87, 5 ));
        this.setPreferredSize( new Dimension( 1000, 600 ) );
       
        //initialize objects
       
        //create gArray with randomly assigned grass and obstacle objects
        //gArray should fill entire grid
        gArray = new Ground[10][6];
        for( int r = 0; r < gArray.length; r++ )
        {
            for( int c = 0; c < gArray[0].length; c++ )
            {
                //1/10 ground objects will be an obstacle
                //rest will be grass
                
                int percentGrass = (int)(Math.random() * 10) + 1;
                if( percentGrass % 10 == 0 )
                {
                    Ground g = new Obstacle( r * 100, c * 100 );
                    gArray[r][c] = g;
                }
                else
                {
                    Ground g = new Grass( r * 100, c * 100 );
                    gArray[r][c] = g;
                }
            }
        }
        current = gArray[5][1];
       
        m = new Mower();
        this.add( m );
       
       
        //add JLabel for the score
        score = new JLabel( "Score: 0" );
        this.add( score );
        score.setBounds( 10, 65, 200, 50 );
        score.setFont( new Font( "Ariel", Font.BOLD, 30 ));
        score.setForeground( Color.WHITE );
       
        //add JLabel for the bonus
        bonusLabel = new JLabel( "Bonus: 0" );
        this.add( bonusLabel );
        bonusLabel.setBounds( 10, 105, 200, 50 );
        bonusLabel.setFont( new Font( "Ariel", Font.BOLD, 30 ));
        bonusLabel.setForeground( Color.WHITE );
       
        //add JLabel for the level
        level = 1;
        levelLabel = new JLabel( "Level: " + level);
        this.add( levelLabel );
        levelLabel.setBounds( 10, 15, 200, 50 );
        levelLabel.setFont( new Font( "Ariel", Font.BOLD, 30 ));
        levelLabel.setForeground( Color.WHITE );
       
        //add restart button
        restart = new JButton( "Play Again" );
        this.add( restart );
        restart.setBounds( 450, 350, 100, 50 );
        restart.setVisible( false );
       
        this.addKeyListener( this );
        restart.addActionListener( this );
        this.setFocusable( true );
       
        //add nextLevel button
        nextLevel = new JButton( "Next Level!" );
        this.add( nextLevel );
        nextLevel.setBounds( 450, 350, 100, 50 );
        nextLevel.setVisible( false );
        nextLevel.addActionListener( this );
       
        //for ending = GAME OVER
        ending = new JLabel();
        this.add( ending );
        ending.setBounds( 0, 250, 1000, 100 );
        ending.setFont( new Font( "Ariel", Font.BOLD, 100 ));
        ending.setForeground( Color.BLACK );
        ending.setHorizontalAlignment( SwingConstants.CENTER );
        ending.setText( "Oh no!" );
        ending.setVisible( false );
       
        //for ending = GAME WON
        gameWon = new JLabel();
        this.add( gameWon );
        gameWon.setBounds( 0, 250, 1000, 100 );
        gameWon.setFont( new Font( "Ariel", Font.BOLD, 100 ));
        gameWon.setForeground( Color.BLACK );
        gameWon.setHorizontalAlignment( SwingConstants.CENTER );
        gameWon.setText( "Killin' it!" );
        gameWon.setVisible( false );
    }
   
    public void go()
    {
        while ( true )
        {
            try
            {
                Thread.sleep( 500 );
            }catch( InterruptedException ex ){}
            while( !done )
            {
                m.move(direction);
               
                //check if game is won
                int count = 0;
                int total = gArray.length * gArray[0].length;
               
                //check if points gained
                for( int r = 0; r < gArray.length; r++)
                {
                    for( int c = 0; c < gArray[0].length; c++ )
                    {
                        Ground g = gArray[r][c];
                       
                        //check if game is won:
                        //if it is cut and grass or an obstacle, count++
                        //if count equals total, then game is won
                        if ( g.getCut() == true )
                        {
                            count++;
                            //numMowed.setText( "Mow: " + count + "/" + total );
                            if ( count >= total )
                            {
                                gameWon.setVisible( true );
                                nextLevel.setVisible( true );
                                done = true;
                            }
                        }
                        //if it's grass, it is not cut, and the mower hits this square,
                        //then set bonus to 0
                        //bonusUp checks if it is a new square the mower is cutting
                        if ( g instanceof Grass && !g.getCut() && Math.abs( m.getX() - g.getX() ) <= 50 && Math.abs( m.getY() - g.getY()) <= 50)
                        {
                            g.cut();
                            bonus++;
                            points += bonus;
                            score.setText( "Score: " + points );
                            bonusLabel.setText( "Bonus: " + bonus );
                            bonusUp = false;
                            current = g;
                        }
                       
                        //if it's grass, it is already cut, and the mower hits this square,
                        //then set bonus to 0
                        else if ( bonusUp && g instanceof Grass && !g.equals(current) && g.getCut() == true && Math.abs( m.getX() - g.getX() ) <= 50 && Math.abs( m.getY() - g.getY()) <= 50)
                        {
                            bonus = 0;
                            bonusLabel.setText( "Bonus: " + bonus );
                            bonusUp = false;  
                        }
                       
                        //check if game over
                        //if collision with obstacle, game is over
                        //game over: ending is visible, restart is visible, done is true
                        else if ( g instanceof Obstacle && timer >= 3000 && Math.abs( m.getX() - g.getX() ) <= 50 && Math.abs( m.getY() - g.getY()) <= 50)
                        {
                            ending.setVisible( true );
                            restart.setVisible( true );
                            done = true;
                            this.remove(m);
                        }
                    }
                }
                this.repaint();
                try
                {
                    Thread.sleep( 10 );
                    timer += 10;
                } catch( InterruptedException ex ){}
               
            }
        }
    }
   
    public void actionPerformed( ActionEvent event )
    {
        JButton temp = (JButton)event.getSource();
        //restart game
        if ( temp == restart )
        {
            restart.setVisible( false );
            ending.setVisible( false );
            this.remove(m);
            //create new objects
            //gArray = new Ground[10][6];
            for( int r = 0; r < gArray.length; r++ )
            {
                for( int c = 0; c < gArray[0].length; c++ )
                {
                    //1/10 ground objects will be an obstacle
                    //rest will be grass
                    int percentGrass = (int)(Math.random() * 10) + 1;
                    if( percentGrass % 10 == 0 )
                    {
                        Ground g = new Obstacle( r * 100, c * 100 );
                        gArray[r][c] = g;
                    }
                    else
                    {
                        Ground g = new Grass( r * 100, c * 100);
                        gArray[r][c] = g;
                    }
                }
            }
           
           
           
            m = new Mower();
            this.add( m );
            timer = 0;
            points = 0;
            bonus = 0;
            level = 1;
            score.setText( "Score: " + points );
            bonusLabel.setText( "Bonus: " + bonus );
            levelLabel.setText( "Level: " + level );
            done = false;
            direction = "STOP";
        }
       
        //level up!
        if ( temp == nextLevel )
        {
            nextLevel.setVisible( false );
            gameWon.setVisible( false );
            this.remove(m);
            //create new objects
            //gArray = new Ground[10][6];
            for( int r = 0; r < gArray.length; r++ )
            {
                for( int c = 0; c < gArray[0].length; c++ )
                {
                    //1/10 ground objects will be an obstacle
                    //rest will be grass
                    
                    //increase proportion of obstacles
                    int lvl;
                    if ( level >= 6 )
                    {
                        lvl = 6;
                    }
                    else 
                    {
                        lvl = 11 - level;
                    }
                    int percentGrass = (int)(Math.random() * lvl) + 1;
                    
                    if( percentGrass % lvl == 0 )
                    {
                        Ground g = new Obstacle( r * 100, c * 100 );
                        gArray[r][c] = g;
                    }
                    else
                    {
                        Ground g = new Grass( r * 100, c * 100);
                        gArray[r][c] = g;
                    }
                }
            }
           
            m = new Mower();
            this.add( m );
            m.speedUp();
            //points = 0;
            direction = "STOP";
            bonus = 0;
            timer = 0;
            level++;
            score.setText( "Score: " + points );
            bonusLabel.setText( "Bonus: " + bonus );
            levelLabel.setText( "Level: " + level );
            done = false;
        }
    }
   
    public void keyTyped( KeyEvent event ){}
   
    public void keyPressed( KeyEvent event ){}
   
    public void keyReleased( KeyEvent event )
    {
        if( event.getKeyCode() == KeyEvent.VK_UP )
        {
            direction = "UP";
            bonusUp = true;
        }
        if( event.getKeyCode() == KeyEvent.VK_DOWN )
        {
            direction = "DOWN";
            bonusUp = true;
        }
        if( event.getKeyCode() == KeyEvent.VK_LEFT )
        {
            direction = "LEFT";
            bonusUp = true;
        }
        if( event.getKeyCode() == KeyEvent.VK_RIGHT )
        {
            direction = "RIGHT";
            bonusUp = true;
        }
    }
   
    public void paintComponent( Graphics page )
    {
        super.paintComponent( page );
        for( int r = 0; r < gArray.length; r++ )
        {
            for( int c = 0; c < gArray[0].length; c++ )
            {
                gArray[r][c].draw( page );
            }
        }
        m.draw( page );
    }
}