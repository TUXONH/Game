/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Scanner;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import multiplayer.Client;
import multiplayer.Server;
import javax.swing.JProgressBar;
import javax.swing.JTextArea;

/**
 *d
 * @author poseidon9
 */
public class Game extends JPanel
{
    public final int TILE_SIZE = 80;
    public final int COLUMN_TILES = 9;
    public final int ROW_TILES = 9;
    
    //temporary
    public final int[] MAP_SIZE = {12, 16};

    public int turn = 0;
    
    public int[] camera_position = {0, 0};
    public Cursor cursor;
    public List<Unit> units = new ArrayList<>();
    public List<Unit> my_units = new ArrayList<>();
    public List<Field> fields = new ArrayList<>();
    public List<Sea> seas = new ArrayList<>();
    public int team;

    Client client;
    
    public Game()
    {
        this.setPreferredSize(new Dimension(881, 661));

        try
        {
            Server server = new Server(9000, 2);
            server.start();
        }
        catch (IOException ex)
        {
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
        }
                
        client = new Client(this, "192.168.0.8", 9000);
        client.start();
        
        System.out.println("Connectadation");
        
        units.add(new Unit(this,3,3,2,0));
        units.add(new Unit(this,4,3,2,1));
        units.add(new Unit(this, 4, 4, 0, 0));
        units.add(new Unit(this, 4, 5, 1, 0));


        ////////////////////////////////////////////////////////////////////////
        /*
        int[][] map =
        {
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
        createMap(map);
        */
        ////////////////////////////////////////////////////////////////////////////
        int[][] map =
        {
            {1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0},
            {0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0},
            {0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 0},
            {0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 0},
            {0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0},
            {0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 0},
            {0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 0},
            {0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 0},
            {0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 0},
            {0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0},
            {0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 0}
        };
        createMap(map);

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
        
        
        ////////////////////////////////////////////////////////////////////////

        cursor = new Cursor(this, map, 0, 0);
        addKeyListener(new KeyListener()
        {
            @Override
            public void keyTyped(KeyEvent e)
            {
            }

            @Override
            public void keyReleased(KeyEvent e)
            {
            }

            @Override
            public void keyPressed(KeyEvent e)
            {
                try
                {
                    cursor.keyPressed(e);
                }
                catch (IOException ex)
                {
                    Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        setFocusable(true);
        
        
        //paint(fields, units, cursor);

        //littleBacktracking(units, cursor, fields, -1, already_passed_through_maze);
    }   
    
    
    private void createMap(int[][] map)
    {
        //ROW
        for(int i = 0; i < MAP_SIZE[1]; i++)
        {
            //COLUMN
            for(int j = 0; j < MAP_SIZE[0]; j++)
            {
                if(map[i][j] == 1)
                {
                    fields.add(new Field(this, j, i));
                }
                else
                {
                    seas.add(new Sea(this, j, i));
                }
            }
        }
    }
    
    private void getMyUnits()
    {
        for(Unit unit: units)
        {
            if(unit.getIdTeam() == team)
            {
                my_units.add(unit);
            }
        }
    }
    
    public void activateMyUnits()
    {
        for(Unit unit: my_units)
        {
            unit.setActive(true);
        }
    }
    
    //Change name
    public boolean checkAtleastOneActiveMyUnit()
    {
        boolean aux = false;
        for(Unit unit: my_units)
        {
            if(unit.getActive())
            {
                aux = true;
            }
        }
        return aux;
    }
    
    public boolean checkAtleastOneAliveMyUnit()
    {
        boolean aux = my_units.size() > 0;
        return aux;
    }
    
    //Old method
    /*
    public void createMap(int[][] map)
    {
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
    }
    */

    
    @Override
    public void paint(Graphics g)
    {
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

        cursor.paint(g2d);
    }

    
    private void gameOver()
    {
        JOptionPane.showMessageDialog(this, "Game Over", "Game Over", JOptionPane.YES_NO_OPTION);
        System.exit(ABORT);
    }
    
    
    public static void main(String[] args) throws InterruptedException, IOException
    {
        JFrame frame = new JFrame("Advance Wars Remake");
      
        Game game = new Game();
        frame.setPreferredSize(new Dimension(1330, 760));
        frame.pack();
        frame.add(game);
        game.setLocation(0, 0);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        while (true)
        {
            game.repaint();
            Thread.sleep(100);
        }
    }
}