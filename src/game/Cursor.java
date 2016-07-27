/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.util.List;
import javax.swing.ImageIcon;

/**
 *
 * @author poseidon9
 */
public class Cursor
{
    private final String PATH = "src/images/";

    private Image image;
    private Game game;
    List<Unit> units;
    //List<Unit1> units1;
    List<Field> fields;
    private int x;
    private int y;
    
    private Unit unit = null;
    //private Unit1 unit1 = null;
    

    public Cursor(Game game, List<Unit> units, List<Field> fields, int x, int y)
    {
        this.game = game;
        //this.units1=units1;
        this.units = units;
        this.fields = fields;
        this.x = x;
        this.y = y;
        ImageIcon image_icon = new ImageIcon(PATH + "cursor.png");
        image = image_icon.getImage();
    }

    public void move()
    {
        //System.out.println(image.getWidth(game));
    }

    public void paint(Graphics2D g)
    {
        if(unit != null)
        {
            g.drawString("Unidad Seleccionada", 0, 80);
        }
        //if(unit1 != null)
        //{
        //    g.drawString("Unidad Seleccionada", 0, 80);
        //}
        int x_draw =  x * game.TILE_SIZE;
        int y_draw =  y * game.TILE_SIZE;
        g.drawImage(image, x_draw , y_draw, game);
        g.drawString("x = " + game.camera_position[0] + ", y = " + game.camera_position[1], 0, 20);
    }


    
    public void keyTyped(KeyEvent e)
    {
    }

    public void keyPressed(KeyEvent e)
    {
        System.out.println(e.getKeyCode());
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
            //System.out.println("izquierda");
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
            //System.out.println("derecha");
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
            //System.out.println("arriba");
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
            //System.out.println("abajo");
        }
        if (e.getKeyCode() == KeyEvent.VK_ENTER)
        {
            int x_in_map = x + game.camera_position[0];
            int y_in_map = y + game.camera_position[1];
            
            if(unit == null)
            {
                for(Unit u : units)
                {
                    if(x_in_map == u.getX() && y_in_map == u.getY())
                    {
                        unit = u;
                    }
                }
                //System.out.println("x = " + x + ", posicion de camara: " + game.camera_position[0]);
                //System.out.println("y = " + y + ", posicion de camara: " + game.camera_position[1]);
                //System.out.println("Posicion en el mapa: x = " + (x_in_map) + ", y = " + (y_in_map));
            }
            else
            {
                if(x_in_map == unit.getX() && y_in_map == unit.getY())
                {
                    unit = null;
                }
                else
                {
                    boolean flag = false;
                    for(Field field: game.fields)
                    {
                        if(field.getX() == x_in_map && field.getY() == y_in_map)
                        {
                            flag = true;
                        }
                    }
                    if(flag)
                    {
                        unit.move(x_in_map, y_in_map);
                        unit = null;
                    }
                }
            }
            
            /*
            if(unit1 == null)
            {
                for(Unit1 u : units1)
                {
                    if(x_in_map == u.getX() && y_in_map == u.getY())
                    {
                        unit1 = u;
                    }
                }
                System.out.println("x = " + x + ", posicion de camara: " + game.camera_position[0]);
                System.out.println("y = " + y + ", posicion de camara: " + game.camera_position[1]);
                System.out.println("Posicion en el mapa: x = " + (x_in_map) + ", y = " + (y_in_map));
            }
            else
            {
                if(x_in_map == unit1.getX() && y_in_map == unit1.getY())
                {
                    unit1 = null;
                }
                else
                {
                    boolean flag = false;
                    for(Field field: game.fields)
                    {
                        if(field.getX() == x_in_map && field.getY() == y_in_map)
                        {
                            flag = true;
                        }
                    }
                    if(flag)
                    {
                        unit1.move(x_in_map, y_in_map);
                        unit1 = null;
                    }
                }
            }
            */
        }
    }
}