/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import java.awt.Graphics2D;
import java.awt.Image;
import javax.swing.ImageIcon;

/**
 *
 * @author poseidon9
 */
public class Field
{
    private final String PATH = "src/images/";

    private Game game;
    private Image image;
    private int x;
    private int y;
    private Unit unit = null;

    public Field(Game game, int x, int y)
    {        
        this.game = game;
        this.x = x;
        this.y = y;
        ImageIcon image_icon = new ImageIcon(PATH + "pasto1.png");
        image =image_icon.getImage();
    }

    public int getX()
    {
        return x;
    }
    
    public int getY()
    {
        return y;
    }
    
    public void setUnitInFiel()
    {
    
    }
    
    public Unit getUnitInFiel()
    {
        return unit;
    }

    public void paint(Graphics2D g)
    {
        if(insideCamera())
        {
        
            int x_draw =  (x - game.camera_position[0]) * game.TILE_SIZE;
            int y_draw =  (y - game.camera_position[1]) * game.TILE_SIZE;
            
            g.drawImage(image, x_draw , y_draw, game);
        }
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