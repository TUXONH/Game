/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import java.awt.Graphics2D;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;

/**
 *
 * @author poseidon9
 */
public class Unit1
{
    private final int ANIMATION_SEQUENCE = 3;
    private final String PATH = "src/images/";

    private Game game;
    private List<Image> images = new ArrayList<>();
    private int _sequence = 0;
    private int x;
    private int y;
    private int id_team;
    
    public Unit1(Game game, int x, int y, int id_team)
    {
        this.game = game;
        this.x = x;
        this.y = y;
        this.id_team = id_team;
        ImageIcon image_icon = new ImageIcon(PATH + "guerrero1.png");
        images.add(image_icon.getImage());
        image_icon = new ImageIcon(PATH + "guerrero2.png");
        images.add(image_icon.getImage());
        image_icon = new ImageIcon(PATH + "guerrero3.png");
       images.add(image_icon.getImage());
        //image_icon = new ImageIcon(PATH + "unit4.png");
        //images.add(image_icon.getImage());
        
        //ImageIcon image_icon=new ImageIcon(PATH + "guerrero3.png");
        //images.add(image_icon.getImage());
        //image_icon = new ImageIcon(PATH + "gerrero2.png");
        //images.add(image_icon.getImage());
    }
    

    public int getX()
    {
        return x;
    }
    
    public int getY()
    {
        return y;
    }

    public int getId_Team()
    {
        return id_team;
    }

    public void move(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    public void paint(Graphics2D g)
    {
        if(insideCamera())
        {
            _sequence++;
            _sequence = _sequence % ANIMATION_SEQUENCE;
        
            int x_draw =  (x - game.camera_position[0]) * game.TILE_SIZE;
            int y_draw =  (y - game.camera_position[1]) * game.TILE_SIZE;
            
            g.drawImage(images.get(_sequence), x_draw , y_draw, game);
            g.drawString("Tale of Knigth", 0, 60);
        }
        g.drawString("x = " + (game.camera_position[0] + game.COLUMN_TILES) + ", y = " + (game.camera_position[1] + game.ROW_TILES), 0, 40);
    }
    
    private boolean insideCamera()
    {
        boolean inside_camera_range_x = x >= game.camera_position[0] && x < game.camera_position[0] + game.COLUMN_TILES;
        boolean inside_camera_range_y = y >= game.camera_position[1] && y < game.camera_position[1] + game.ROW_TILES;
        if(inside_camera_range_x && inside_camera_range_y )
        {
            return true;
        }
        return false;
    }
}
