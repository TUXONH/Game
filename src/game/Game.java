/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author poseidon9
 */
public class Game extends JPanel {
    public final int TILE_SIZE = 80;
    public final int COLUMN_TILES = 9;
    public final int ROW_TILES = 9;
    
    //temporary
    public final int[] MAP_SIZE = {12, 16};

    public int player_turn = 0;
    
    public int[] camera_position = {0, 0};
    Cursor cursor;
    public List<Unit> units = new ArrayList<>();
    public List<Unit1> units1 = new ArrayList<>();
    public List<Field> fields = new ArrayList<>();
    public List<Sea> seas = new ArrayList<>();

    public Game() {
        this.setPreferredSize(new Dimension(720, 720));

        units.add(new Unit(this, 4, 4, 0, 0));
        units.add(new Unit(this, 4, 5, 1, 1));
        units.add(new Unit(this,4,3,2,2));
        //units1.add(new Unit1(this, 5, 5, 0));

        ////////////////////////////////////////////////////////////////////////
        int[][] map = {
            {0, 0}, {1, 0}, {2, 0}, 
            {0, 1}, {1, 1}, {2, 1},
            {2, 2}, {3, 2},
            {2, 3}, {3, 3}, {4, 3}, {8, 3}, {9, 3}, {10, 3},
            {3, 4}, {4, 4}, {5, 4}, {6,4}, {7, 4}, {8, 4}, {9, 4}, {10, 4},
            {4, 5}, {5, 5}, {6, 5}, {7, 5}, {8, 5}, {9, 5}, {10, 5},
            {4, 6}, {5, 6}, {6, 6}, {7, 6}, {8, 6}, {10, 6}, {11, 6},
            {8, 7},
            {8, 8},
            {8, 9}, {9, 9}, {10, 9},
            {5, 10}, {6, 10}, {7, 10}, {8, 10}, {9, 10}, {10, 10},
            {5, 11}, {6, 11}, {7, 11}, {8, 11}, {9, 11}, {10, 11},
            {3, 12}, {4, 12}, {5, 12}, {6, 12},
            {3, 13}, {4, 13}, {5, 13}, {6, 13},
            {3, 14}, {4, 14}, {5, 14}, {6, 14}, {7, 14}, {8, 14}, {9, 14}, {10, 14},
            {7, 15}, {8, 15}, {9, 15}, {10, 15}            
        };
        /*
        {
        "map":
            "row": 16
            "column": 12
            "field_list":
                [
                    [0, 0], [1, 0], [2, 0], 
                    [0, 1], [1, 1], [2, 1],
                    [2, 2], [3, 2],
                    [2, 3], [3, 3], [4, 3], [8, 3], [9, 3], [10, 3],
                    [3, 4], [4, 4], [5, 4], [6,4], [7, 4], [8, 4], [9, 4], [10, 4],
                    [4, 5], [5, 5], [6, 5], [7, 5], [8, 5], [9, 5], [10, 5],
                    [4, 6], [5, 6], [6, 6], [7, 6], [8, 6], [10, 6], [11, 6],
                    [8, 7],
                    [8, 8],
                    [8, 9], [9, 9], [10, 9],
                    [5, 10], [6, 10], [7, 10], [8, 10], [9, 10], [10, 10],
                    [5, 11], [6, 11], [7, 11], [8, 11], [9, 11], [10, 11],
                    [3, 12], [4, 12], [5, 12], [6, 12],
                    [3, 13], [4, 13], [5, 13], [6, 13],
                    [3, 14], [4, 14], [5, 14], [6, 14], [7, 14], [8, 14], [9, 14], [10, 14],
                    [7, 15], [8, 15], [9, 15], [10, 15]
                ]
        }
        */
        
        int map_iterator = 0;
        //ROW
        for(int i = 0; i < MAP_SIZE[1]; i++)
        {
            //COLUMN
            for(int j = 0; j < MAP_SIZE[0]; j++)
            {
                if(map_iterator < map.length)
                {
                    System.out.println(j + " " + map[map_iterator][0] +  ", " +  i + " " + map[map_iterator][1]);
                    if(j == map[map_iterator][0] && i == map[map_iterator][1])
                    {
                        fields.add(new Field(this, j, i));
                        map_iterator++;
                    }
                    else
                    {
                        seas.add(new Sea(this, j, i));
                    }
                }
                else
                {
                    seas.add(new Sea(this, j, i));
                }
            }
        }
        
        
        
        
        ////////////////////////////////////////////////////////////////////////
        
        cursor = new Cursor(this, units, fields, 0, 0);
        addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                cursor.keyPressed(e);
            }
        });
        setFocusable(true);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);
        
        for(Sea sea: seas)
        {
            sea.paint(g2d);
        }

        for(Field field : fields)
        {
            field.paint(g2d);
        }
        
        
        for(Unit unit : units)
        {
            unit.paint(g2d);
        }
        /*
        for(Unit1 unit1 : units1)
        {
            unit1.paint(g2d);
        }
        */
        
        cursor.paint(g2d);
    }

    public void gameOver() {
        JOptionPane.showMessageDialog(this, "Game Over", "Game Over", JOptionPane.YES_NO_OPTION);
        System.exit(ABORT);
    }

    public static void main(String[] args) throws InterruptedException, IOException {
        /*
        String current = new java.io.File( "." ).getCanonicalPath();
        System.out.println("Current dir:"+current);
         String currentDir = System.getProperty("user.dir");
        System.out.println("Current dir using System:" +currentDir);
        */
        JFrame frame = new JFrame("Advance Wars Remake");
        Game game = new Game();
        frame.getContentPane().add(game);
        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        while (true) {
            game.repaint();
            Thread.sleep(100);
        }
    }
}




/*
        fields.add(new Field(this, 0, 0));
        fields.add(new Field(this, 1, 0));
        fields.add(new Field(this, 2, 0));
        fields.add(new Field(this, 0, 1));
        fields.add(new Field(this, 1, 1));
        fields.add(new Field(this, 2, 1));
        fields.add(new Field(this, 2, 2));
        fields.add(new Field(this, 3, 2));
        fields.add(new Field(this, 2, 3));
        fields.add(new Field(this, 3, 3));
        fields.add(new Field(this, 4, 3));
        fields.add(new Field(this, 8, 3));
        fields.add(new Field(this, 9, 3));
        fields.add(new Field(this, 10, 3));
        fields.add(new Field(this, 3, 4));
        fields.add(new Field(this, 4, 4));
        fields.add(new Field(this, 5, 4));
        fields.add(new Field(this, 6, 4));
        fields.add(new Field(this, 7, 4));
        fields.add(new Field(this, 8, 4));
        fields.add(new Field(this, 9, 4));
        fields.add(new Field(this, 10, 4));
        fields.add(new Field(this, 4, 5));
        fields.add(new Field(this, 5, 5));
        fields.add(new Field(this, 6, 5));
        fields.add(new Field(this, 7, 5));
        fields.add(new Field(this, 8, 5));
        fields.add(new Field(this, 9, 5));
        fields.add(new Field(this, 10, 5));
        fields.add(new Field(this, 4, 6));
        fields.add(new Field(this, 5, 6));
        fields.add(new Field(this, 6, 6));
        fields.add(new Field(this, 7, 6));
        fields.add(new Field(this, 8, 6));
        fields.add(new Field(this, 10, 6));
        fields.add(new Field(this, 11, 6));
        fields.add(new Field(this, 8, 7));
        fields.add(new Field(this, 8, 8));
        fields.add(new Field(this, 8, 9));
        fields.add(new Field(this, 9, 9));
        fields.add(new Field(this, 10, 9));
        fields.add(new Field(this, 5, 10));
        fields.add(new Field(this, 6, 10));
        fields.add(new Field(this, 7, 10));
        fields.add(new Field(this, 8, 10));
        fields.add(new Field(this, 9, 10));
        fields.add(new Field(this, 10, 10));
        fields.add(new Field(this, 5, 11));
        fields.add(new Field(this, 6, 11));
        fields.add(new Field(this, 7, 11));
        fields.add(new Field(this, 8, 11));
        fields.add(new Field(this, 9, 11));
        fields.add(new Field(this, 10, 11));
        fields.add(new Field(this, 3, 12));
        fields.add(new Field(this, 4, 12));
        fields.add(new Field(this, 5, 12));
        fields.add(new Field(this, 6, 12));
        fields.add(new Field(this, 3, 13));
        fields.add(new Field(this, 4, 13));
        fields.add(new Field(this, 5, 13));
        fields.add(new Field(this, 6, 13));
        fields.add(new Field(this, 3, 14));
        fields.add(new Field(this, 4, 14));
        fields.add(new Field(this, 5, 14));
        fields.add(new Field(this, 6, 14));
        fields.add(new Field(this, 7, 14));
        fields.add(new Field(this, 8, 14));
        fields.add(new Field(this, 9, 14));
        fields.add(new Field(this, 10, 14));
        fields.add(new Field(this, 7, 15));
        fields.add(new Field(this, 8, 15));
        fields.add(new Field(this, 9, 15));
        fields.add(new Field(this, 10, 15));
        */

/*
        ////////////////////////////////////////////////////////////////////////
        
        seas.add(new Sea(this, 0, 2));
        seas.add(new Sea(this, 1, 2));
        seas.add(new Sea(this, 0, 3));
        seas.add(new Sea(this, 1, 3));
        seas.add(new Sea(this, 0, 4));
        seas.add(new Sea(this, 1, 4));
        seas.add(new Sea(this, 2, 4));
        seas.add(new Sea(this, 0, 5));
        seas.add(new Sea(this, 1, 5));
        seas.add(new Sea(this, 2, 5));
        seas.add(new Sea(this, 3, 5));
        seas.add(new Sea(this, 0, 6));
        seas.add(new Sea(this, 1, 6));
        seas.add(new Sea(this, 2, 6));
        seas.add(new Sea(this, 3, 6));
        seas.add(new Sea(this, 0, 7));
        seas.add(new Sea(this, 1, 7));
        seas.add(new Sea(this, 2, 7));
        seas.add(new Sea(this, 3, 7));
        seas.add(new Sea(this, 4, 7));
        seas.add(new Sea(this, 5, 7));
        seas.add(new Sea(this, 6, 7));
        seas.add(new Sea(this, 7, 7));
        seas.add(new Sea(this, 3, 0));
        seas.add(new Sea(this, 4, 0));
        seas.add(new Sea(this, 5, 0));
        seas.add(new Sea(this, 6, 0));
        seas.add(new Sea(this, 7, 0));
        seas.add(new Sea(this, 8, 0));
        seas.add(new Sea(this, 9, 0));
        seas.add(new Sea(this, 10, 0));
        seas.add(new Sea(this, 11, 0));
        seas.add(new Sea(this, 3, 1));
        seas.add(new Sea(this, 4, 1));
        seas.add(new Sea(this, 5, 1));
        seas.add(new Sea(this, 6, 1));
        seas.add(new Sea(this, 7, 1));
        seas.add(new Sea(this, 8, 1));
        seas.add(new Sea(this, 9, 1));
        seas.add(new Sea(this, 10, 1));
        seas.add(new Sea(this, 11, 1));
       
        seas.add(new Sea(this, 4, 2));
        seas.add(new Sea(this, 5, 2));
        seas.add(new Sea(this, 6, 2));
        seas.add(new Sea(this, 7, 2));
        seas.add(new Sea(this, 8, 2));
        seas.add(new Sea(this, 9, 2));
        seas.add(new Sea(this, 10, 2));
        seas.add(new Sea(this, 11, 2));
        
        seas.add(new Sea(this, 5, 3));
        seas.add(new Sea(this, 6, 3));
        seas.add(new Sea(this, 7, 3));
        seas.add(new Sea(this, 11, 3));
        seas.add(new Sea(this, 11, 4));
        seas.add(new Sea(this, 11, 5));
        
        seas.add(new Sea(this, 0, 8));
        seas.add(new Sea(this, 0, 9));
        seas.add(new Sea(this, 0, 10));
        seas.add(new Sea(this, 0, 11));
        */