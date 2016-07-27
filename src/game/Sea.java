/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
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
 * @author Omar
 */
public class Sea {
    private final int ANIMATION_SEQUENCE = 4;
    private final String PATH = "src/images/";

    private Game game;
    
    private List<Image> images = new ArrayList<>();
    private int _sequence = 0;
    private int x;
    private int y;
    
    public Sea(Game game, int x, int y)
    {        
        this.game = game;
        this.x = x;
        this.y = y;
        
         
        ImageIcon image_icon = new ImageIcon(PATH + "lava.png");
        images.add(image_icon.getImage());
        image_icon = new ImageIcon(PATH + "lava1.png");
        images.add(image_icon.getImage());
        image_icon = new ImageIcon(PATH + "lava2.png");
        images.add(image_icon.getImage());
        image_icon = new ImageIcon(PATH + "lava3.png");
        images.add(image_icon.getImage());
        
       
        
        //image =image_icon.getImage();
    }

    public int getX()
    {
        return x;
    }
    
    public int getY()
    {
        return y;
    }
    /*
    public void setUnitInSea()
    {
    
    }
    
    public Unit getUnitInSea()
    {
        return unit;
    }
*/
    public void paint(Graphics2D g)
    {
        if(insideCamera())
        {
            _sequence++;
            _sequence = _sequence % ANIMATION_SEQUENCE;

            int x_draw =  (x - game.camera_position[0]) * game.TILE_SIZE;
            int y_draw =  (y - game.camera_position[1]) * game.TILE_SIZE;
            g.drawImage(images.get(_sequence), x_draw , y_draw, game);
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
