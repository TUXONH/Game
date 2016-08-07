/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;

/**
 *
 * @author poseidon9
 */
public class CursorCreateMap
{
    private final String PATH = "src/images/";

    private Image image;
    private CreateMap game;
    private int[][] map;
    private int x;
    private int y;
    private int id_team;
    private int soldier_type;

    public CursorCreateMap(CreateMap game, int[][] map, int x, int y)
    {
        this.game = game;
        this.map = map;
        this.x = x;
        this.y = y;
        id_team = 0;
        soldier_type = 0;
        ImageIcon image_icon = new ImageIcon(PATH + "cursor.png");
        image = image_icon.getImage();
    }

    public void move()
    {

    }

    public void paint(Graphics2D g)
    {        
        int x_draw =  x * game.TILE_SIZE;
        int y_draw =  y * game.TILE_SIZE;
        g.drawImage(image, x_draw , y_draw, game);
    }

    public void keyTyped(KeyEvent e)
    {
    }

    public void keyPressed(KeyEvent e)
    {
        if (e.getKeyCode() == KeyEvent.VK_LEFT)
        {
            if(x > 0)
            {
                x--;
            }
            else
            {
                if(game.camera_position[0] > 0)
                {
                    game.camera_position[0]--;
                }
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_RIGHT)
        {
            if(x < game.COLUMN_TILES - 1) // son n columnas, pero se cuenta desde el cero asi que quitamos la "n-sima"
            {
                x++;
            }
            else
            {
                if(game.camera_position[0] < game.MAP_SIZE[0] - game.ROW_TILES)
                {
                    game.camera_position[0]++;
                }
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_UP)
        {
            if(y > 0)
            {
                y--;
            }
            else
            {                
                if(game.camera_position[1] > 0)
                {
                    game.camera_position[1]--;
                }
            }            
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN)
        {
            if(y < game.ROW_TILES - 1) // son n filas, pero se cuenta desde el cero asi que quitamos la "n-sima"
            {
                y++;
            }
            else
            {
                if(game.camera_position[1] < game.MAP_SIZE[1] - game.COLUMN_TILES)
                {
                    game.camera_position[1]++;
                }
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_ENTER)
        {
            int x_in_map = x + game.camera_position[0];
            int y_in_map = y + game.camera_position[1];

            System.out.println(x_in_map + " " + y_in_map);
            
            if(map[y_in_map][x_in_map] == 0)
            {
                map[y_in_map][x_in_map] = 1;
            }
            else
            {
                map[y_in_map][x_in_map] = 0;
                Unit unit_aux = null;
                for(Unit unit: game.units)
                {
                    if(unit.getX() == x_in_map && unit.getY() == y_in_map)
                    {
                        unit_aux = unit;
                        break;
                    }
                }
                if(unit_aux != null)
                {
                    game.units.remove(unit_aux);
                }
            }
            
            //int iterator1 = game.fields.size() / game.MAP_SIZE[0];
            //int iterator2 = game.fields.size() % game.MAP_SIZE[1];
            
            //System.out.println(iterator1 + " " + iterator2);

            int aux = game.MAP_SIZE[0] * y_in_map;
            aux += x_in_map;
            
            System.out.println(aux + " " + game.fields.size());

            Field field = game.fields.get(aux);
            field.setPlatform(!field.getPlatform());
            field.setImages();
        }
        if (e.getKeyCode() == KeyEvent.VK_1)
        {
            soldier_type = 0;
        }
        if (e.getKeyCode() == KeyEvent.VK_2)
        {
            soldier_type = 1;
        }
        if (e.getKeyCode() == KeyEvent.VK_3)
        {
            soldier_type = 2;
        }
        if (e.getKeyCode() == KeyEvent.VK_4)
        {
            soldier_type = 3;
        }
        if (e.getKeyCode() == KeyEvent.VK_5)
        {
            id_team = 0;
        }
        if (e.getKeyCode() == KeyEvent.VK_6)
        {
            id_team = 1;
        }
        if (e.getKeyCode() == KeyEvent.VK_7)
        {
            id_team = 2;
        }
        if (e.getKeyCode() == KeyEvent.VK_8)
        {
            id_team = 3;
        }
        if (e.getKeyCode() == KeyEvent.VK_SPACE)
        {
            int x_in_map = x + game.camera_position[0];
            int y_in_map = y + game.camera_position[1];
            
            int aux = game.MAP_SIZE[0] * y_in_map;
            aux += x_in_map;
            
            Field field = game.fields.get(aux);
            
            if(field.getPlatform())
            {
                boolean flag = true;
                Unit unit_aux = null;
                for(Unit unit: game.units)
                {
                   if(unit.getX() == x_in_map && unit.getY() == y_in_map)
                   {
                       System.out.println("There's a Unit here!");
                       if(unit.getIdTeam() != id_team || unit.getSoldierType() != soldier_type)
                       {
                           System.out.println("Unidad Change to: id team: " + id_team + ", soldier type: " + soldier_type);
                           unit.setIdTeam(id_team);
                           unit.setSoldierType(soldier_type);
                           unit.setImages();
                           flag = false;
                           break;
                       }
                       else
                       {
                           System.out.println("Removing Unit!");
                           unit_aux = unit;
                           break;
                       }
                   }
                }
                if(unit_aux != null)
                {
                    game.units.remove(unit_aux);
                }
                else if(flag)
                {
                    game.units.add(new Unit(game, x_in_map, y_in_map, soldier_type, id_team));
                }
            } 
        }
    }
    
    private boolean checkIfUnitInPosition(int[] position)
    {
        for(Unit u: game.units)
        {
            if(u.getX() == position[0] && u.getY() == position[1])
            {
                return true;
            }
        }
        return false;
    }

    
    private boolean insideCamera(int x, int y)
    {
        boolean inside_camera_range_x = x >= game.camera_position[0] && x < game.camera_position[0] + game.COLUMN_TILES;
        boolean inside_camera_range_y = y >= game.camera_position[1] && y < game.camera_position[1] + game.ROW_TILES;
        if(inside_camera_range_x && inside_camera_range_y)
        {
            return true;
        }
        return false;
    }
    
    public int getX()
    {
        return x;
    }
    
    public int getY()
    {
        return y;
    }
    
    public void setX(int new_x)
    {
        x = new_x;
    }
    
    public void setY(int new_y)
    {
        y = new_y;
    }
    //####################################################################################################

}