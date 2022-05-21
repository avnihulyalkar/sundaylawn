import javax.swing.*;
import java.awt.*;
public class Driver
{
    public static void main()
    {
        JFrame frame = new JFrame( "Sunday Lawn!!!" );
        frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        frame.setLocation( 100, 100 );
        Board board = new Board();
        frame.getContentPane().add( board );
        frame.pack();
        frame.setVisible( true );
        board.go();
    }
}

//TO DO:


//switch directions of mower image when directions switched
//add in picture of rocks/grass
//add in dog! and dog poop

// TODO: add WALLs that don't kill you, but you cannot go through
// A way to create walls is to create a random number of rectangles
// and place each rectangle randomly on the board. 

// TODO 2: Calculate the best score for a given board (tough algorithmic
// problem

// TODO 3: 
// Add DFS reachability check to make sure tha a given game is winnable. 
