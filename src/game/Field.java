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
public class Field
{
    private int ANIMATION_SEQUENCE;
    private final String PATH = "src/images/";

    private Game game = null;
    private CreateMap game2 = null;
    private List<Image> images = new ArrayList<>();
    private int _sequence = 0;
    private int x;
    private int y;
    private boolean platform;
    private int field_type;
    private ImageIcon image_icon;

    public Field(Game game, int x, int y, boolean platform,int field_type)
    {
        this.game = game;
        this.x = x;
        this.y = y;
        this.platform = platform;
        this.field_type = field_type;
        
        setImages();
        /////////////////////////////////////////////////////////
        
    }
    
    public Field(CreateMap game, int x, int y, boolean platform,int field_type)
    {
        this.game2 = game;
        this.x = x;
        this.y = y;
        this.platform = platform;
        this.field_type = field_type;
        
        setImages();
        /////////////////////////////////////////////////////////
        
    }

    public void setImages()
    {
        images = new ArrayList<>();
        if(field_type == 0)
        {
            if(platform)
            {
                image_icon = new ImageIcon(PATH + "field3.png");
                images.add(image_icon.getImage());
            }
            else
            {
                image_icon = new ImageIcon(PATH + "agua1.jpg");
                images.add(image_icon.getImage());
                image_icon = new ImageIcon(PATH + "agua2.jpg");
                images.add(image_icon.getImage());
                image_icon = new ImageIcon(PATH + "agua3.jpg");
                images.add(image_icon.getImage());
                image_icon = new ImageIcon(PATH + "agua4.jpg");
                images.add(image_icon.getImage());
                image_icon = new ImageIcon(PATH + "agua5.jpg");
                images.add(image_icon.getImage());
            }
        }
        else if(field_type == 1)
        {
            if(platform)
            {
                image_icon = new ImageIcon(PATH + "tierra1.png");
                images.add(image_icon.getImage());
            }
            else
            {
                image_icon = new ImageIcon(PATH + "lava.png");
                images.add(image_icon.getImage());
                image_icon = new ImageIcon(PATH + "lava1.png");
                images.add(image_icon.getImage());
                image_icon = new ImageIcon(PATH + "lava2.png");
                images.add(image_icon.getImage());
                image_icon = new ImageIcon(PATH + "lava3.png");
                images.add(image_icon.getImage());
            }
        }
         else if(field_type == 2)
        {
            if(platform)
            {
                image_icon = new ImageIcon(PATH + "nubes4.png");
                images.add(image_icon.getImage());
            }
            else
            {
                image_icon = new ImageIcon(PATH + "cielo5.png");
                images.add(image_icon.getImage());
                image_icon = new ImageIcon(PATH + "cielo1.png");
                images.add(image_icon.getImage());
                image_icon = new ImageIcon(PATH + "cielo3.png");
                images.add(image_icon.getImage());
                image_icon = new ImageIcon(PATH + "cielo4.png");
                images.add(image_icon.getImage());
            }
        }
        /////////////////////////////////////////////////////////
        ANIMATION_SEQUENCE = images.size();
    }
    
    public boolean getPlatform()
    {
        return platform;
    }
    
    public void setPlatform(boolean platform)
    {
        this.platform = platform;
    }
    
    public int getX()
    {
        return x;
    }

    public int getY()
    {
        return y;
    }

    public void paint(Graphics2D g)
    {
        if(insideCamera())
        {
            _sequence++;
            _sequence = _sequence % ANIMATION_SEQUENCE;
            
            if(game != null)
            {
                int x_draw =  (x - game.camera_position[0]) * game.TILE_SIZE;
                int y_draw =  (y - game.camera_position[1]) * game.TILE_SIZE;
                g.drawImage(images.get(_sequence), x_draw , y_draw, game);
            }
            else
            {
                int x_draw =  (x - game2.camera_position[0]) * game2.TILE_SIZE;
                int y_draw =  (y - game2.camera_position[1]) * game2.TILE_SIZE;
                g.drawImage(images.get(_sequence), x_draw , y_draw, game2);
            }
        }
    }

    private boolean insideCamera()
    {
        boolean inside_camera_range_x;
        boolean inside_camera_range_y;
        if(game != null)
        {
            inside_camera_range_x = x >= game.camera_position[0] && x < game.camera_position[0] + game.COLUMN_TILES;
            inside_camera_range_y = y >= game.camera_position[1] && y < game.camera_position[1] + game.ROW_TILES;
        }
        else
        {
            inside_camera_range_x = x >= game2.camera_position[0] && x < game2.camera_position[0] + game2.COLUMN_TILES;
            inside_camera_range_y = y >= game2.camera_position[1] && y < game2.camera_position[1] + game2.ROW_TILES;
        }
        if(inside_camera_range_x && inside_camera_range_y)
        {
            return true;
        }
        return false;
    }
}