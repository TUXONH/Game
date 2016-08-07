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

import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


/**
 *
 * @author poseidon9
 */
public class CreateMap extends JPanel{
     
    public final int TILE_SIZE = 80;
    public final int COLUMN_TILES = 9;
    public final int ROW_TILES = 9;
    
    //temporary
    public final int[] MAP_SIZE;
    
    public int[] camera_position = {0, 0};
    public CursorCreateMap cursor;
    int map_index;
    int map_type;
    public List<Unit> units = new ArrayList<>();
    public List<Field> fields = new ArrayList<>();

    public CreateMap(int width, int height, int map_type) throws InterruptedException
    {
        int[] aux = {width, height};
        MAP_SIZE = aux;

        this.map_type = map_type;
        this.setPreferredSize(new Dimension(881, 661));

        int[][] map = new int[MAP_SIZE[1]][MAP_SIZE[0]];
                
        
        for(int i = 0; i < MAP_SIZE[1]; i++)
        {
            for(int j = 0; j < MAP_SIZE[0]; j++)
            {
                map[i][j] = 0;
            }
        }
        
        createMap(map);
        
        cursor = new CursorCreateMap(this, map, 0, 0);
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
    
    public void createMap(int[][] map_reference)
    {
        int[][] map = map_reference;
        //ROW
        for(int i = 0; i < MAP_SIZE[1]; i++)
        {
            //COLUMN
            for(int j = 0; j < MAP_SIZE[0]; j++)
            {
                if(map[i][j] == 1)
                {
                    fields.add(new Field(this, j, i, true,map_index));
                }
                else
                {
                    fields.add(new Field(this, j, i, false,map_index));
                }
            }
        }
    }
  

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);

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

    public void gameOver() {
        JOptionPane.showMessageDialog(this, "Game Over", "Game Over", JOptionPane.YES_NO_OPTION);
        System.exit(ABORT);
    }
    
    public static void CreateJframe(int width,int height, int map_type) throws InterruptedException
    {
        JFrame frame = new JFrame("Advance Wars Remake");
      
        final CreateMap game = new CreateMap(width, height, map_type);
        frame.setPreferredSize(new Dimension(1330, 760));
        frame.pack();
        frame.add(game);
        game.setLocation(0, 0);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Thread thread = new Thread() 
        {
            @Override
            public void run() 
            {
                while(true)
                {
                    try 
                    {
                        game.repaint();
                        Thread.sleep(100);
                    } catch (InterruptedException ex) {
                        System.out.println("FUCK YOU");
                    }
                }
            }
        };
        thread.start();
       
    }
}