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
 * 
*/
public class Unit
{
    private final int ANIMATION_SEQUENCE = 2;
    private final String PATH = "src/images/";
    private String[] team_colors = new String[]{"Blue", "Purple", "Red", "Yellow"};
    
    private Game game;
    private CreateMap game2;
    private List<Image> images = new ArrayList<>();
    private int _sequence = 0;
    private int x;
    private int y;
    private int health;
    private boolean active;
    
    private int id_team;
    private int soldier_type;
    private ImageIcon image_icon;
    
    public Unit(Game game, int x, int y, int soldier_type, int id_team)
    {
        this.game = game;
        this.x = x;
        this.y = y;
        health = 10;
        active = true;
        this.id_team = id_team;
        this.soldier_type = soldier_type;
        
        setImages();
    }
    
    public Unit(CreateMap game, int x, int y, int soldier_type, int id_team)
    {
        this.game2 = game;
        this.x = x;
        this.y = y;
        this.id_team = id_team;
        this.soldier_type = soldier_type;
        
        setImages();
    }
    
    public void setImages()
    {
        images = new ArrayList<>();
        String knight_name = "Guerrero_"+(soldier_type + 1)+"_";
        for(int i = 0; i < 3; i++)
        {
            image_icon = new ImageIcon(PATH + knight_name + i + "_" + team_colors[id_team] + ".png");
            images.add(image_icon.getImage());
        }
    }
    

    public int getX()
    {
        return x;
    }
    
    public int getY()
    {
        return y;
    }
    
    public int[] getPosition()
    {
        int[] current_position = {getX(), getY()};
        return current_position;
    }
    
    public int getHealth()
    {
        return health;
    }

    public int getIdTeam()
    {
        return id_team;
    }
    
    public int getSoldierType()
    {
        return soldier_type;
    }
    
    public void setIdTeam(int id_team)
    {
        this.id_team = id_team;
    }
    
    public void setSoldierType(int soldier_type)
    {
        this.soldier_type = soldier_type;
    }

    public boolean getActive()
    {
        return active;
    }
    
    public void setActive(boolean active)
    {
        this.active = active;
    }
    
    public void move(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    public void receiveDamage(int damage)
    {
        health -= damage;       
    }
    
    public boolean checkIfDead()
    {
        boolean flag = health < 0;
        return flag;
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
                g.drawString("Tale of Knigth", 0, 60);
            }
            else
            {
                int x_draw =  (x - game2.camera_position[0]) * game2.TILE_SIZE;
                int y_draw =  (y - game2.camera_position[1]) * game2.TILE_SIZE;

                g.drawImage(images.get(_sequence), x_draw , y_draw, game2);
                g.drawString("Tale of Knigth", 0, 60);
            }
        }
        if(game != null)
        {
            g.drawString("x = " + (game.camera_position[0] + game.COLUMN_TILES) + ", y = " + (game.camera_position[1] + game.ROW_TILES), 0, 40);
        }
        else
        {
            g.drawString("x = " + (game2.camera_position[0] + game2.COLUMN_TILES) + ", y = " + (game2.camera_position[1] + game2.ROW_TILES), 0, 40);
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
        if(inside_camera_range_x && inside_camera_range_y )
        {
            return true;
        }
        return false;
    }
}