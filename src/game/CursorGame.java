/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javax.swing.ImageIcon;

/**
 *
 * @author poseidon9
 */
public class CursorGame
{
    //TEMPORARY, will change later
    private final int MOVING = 0;
    private final int ATTACKING = 1;
    
    private final String PATH = "src/images/";

    private Image image;
    private Image image2;
    private Image image3;
    private Game game;
    private int[][] map;
    private int x;
    private int y;
    private List<Integer[]> possible_new_positions = new ArrayList<>();
    private List<Integer[]> possible_attack_positions = new ArrayList<>();

    
    private Unit unit = null;
    private int unit_index = -1;
    private int moving_or_attacking;

    public CursorGame(Game game, int[][] map, int x, int y)
    {
        this.game = game;
        this.map = map;
        this.x = x;
        this.y = y;
        moving_or_attacking = MOVING;
        
        ImageIcon image_icon = new ImageIcon(PATH + "cursor.png");
        image = image_icon.getImage();
        image_icon = new ImageIcon(PATH + "Seleccion.png");
        image2 = image_icon.getImage();
        
        //Cambiar imagen
        image_icon = new ImageIcon(PATH + "Seleccion.png");
        image3 = image_icon.getImage();
    }

    public void move()
    {

    }

    public void paint(Graphics2D g)
    {
        if(unit != null)
        {
            g.drawString("Unidad Seleccionada", 0, 80);
        }
        int x_draw =  x * game.TILE_SIZE;
        int y_draw =  y * game.TILE_SIZE;
        g.drawImage(image, x_draw , y_draw, game);
        g.drawString("x = " + (game.camera_position[0] + x) + ", y = " + (game.camera_position[1] + y), 0, 20);
        
        for(Integer[] possible_new_position : possible_new_positions)
        {
            if(insideCamera(possible_new_position[0], possible_new_position[1]))
            {
                int x_draw2 = (possible_new_position[0] - game.camera_position[0]) * game.TILE_SIZE;
                int y_draw2 = (possible_new_position[1] - game.camera_position[1]) * game.TILE_SIZE;

                g.drawImage(image2, x_draw2 , y_draw2, game);
            }
        }
        for(Integer[] possible_attack_position : possible_attack_positions)
        {
            if(insideCamera(possible_attack_position[0], possible_attack_position[1]))
            {
                int x_draw3 = (possible_attack_position[0] - game.camera_position[0]) * game.TILE_SIZE;
                int y_draw3 = (possible_attack_position[1] - game.camera_position[1]) * game.TILE_SIZE;

                g.drawImage(image3, x_draw3 , y_draw3, game);
            }
        }
    }

    public void keyTyped(KeyEvent e)
    {
    }

    public void keyPressed(KeyEvent e) throws IOException
    {
        if(game.turn == game.team)
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
                game.client.sendCameraPosition();
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
                game.client.sendCameraPosition();
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
                game.client.sendCameraPosition();
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
                game.client.sendCameraPosition();
            }
            if (e.getKeyCode() == KeyEvent.VK_ENTER)
            {
                int x_in_map = x + game.camera_position[0];
                int y_in_map = y + game.camera_position[1];

                if(moving_or_attacking == MOVING)
                {
                    if(unit == null)
                    {
                        int iterator = 0;
                        for(Unit u : game.units)
                        {
                            if(x_in_map == u.getX() && y_in_map == u.getY() && u.getIdTeam() == game.team && u.getActive())
                            {
                                unit = u;
                                unit_index = iterator;
                                int[] aux = {x_in_map, y_in_map};
                                littleBacktracking(aux, -1, 0);
                                break;
                            }
                            iterator++;
                        }
                    }
                    else
                    {
                        if(x_in_map == unit.getX() && y_in_map == unit.getY())
                        {
                            unit = null;
                            unit_index = -1;
                            possible_new_positions = new ArrayList<>();
                        }
                        else
                        {
                            boolean flag = false;
                            for(Integer[] possible_new_position : possible_new_positions)
                            {
                                if(x_in_map == possible_new_position[0] && y_in_map == possible_new_position[1])
                                {
                                    flag = true;
                                    break;
                                }
                            }
                            if(flag)
                            {
                                int[] aux = {x_in_map, y_in_map};
                                if(!checkIfUnitInPosition(aux))
                                {
                                    unit.move(x_in_map, y_in_map);
                                    game.client.sendMoveUnit(unit.getPosition(), unit_index);                                    
                                    possible_new_positions = new ArrayList<>();
                                    moving_or_attacking = ATTACKING;
                                    setAttackRange(3);
                                }
                            }
                        }
                    }
                }
                else if(moving_or_attacking == ATTACKING)
                {
                    if(x_in_map == unit.getX() && y_in_map == unit.getY())
                    {
                        unit.setActive(false);
                        unit = null;
                        unit_index = -1;
                        possible_attack_positions = new ArrayList<>();
                        moving_or_attacking = MOVING;
                        if(!game.checkAtleastOneActiveMyUnit())
                        {
                            game.turn = -1;
                            game.client.sendNextTurn(game.turn);
                            game.activateMyUnits();
                        }
                    }
                    
                    boolean flag = false;
                    for(Integer[] possible_attack_position : possible_attack_positions)
                    {
                        if(x_in_map == possible_attack_position[0] && y_in_map == possible_attack_position[1])
                        {
                            flag = true;
                            break;
                        }
                    }
                    if(flag)
                    {
                        int iterator2 = 0;
                        for(Unit u : game.units)
                        {
                            if(x_in_map == u.getX() && y_in_map == u.getY() && u.getIdTeam() != game.team)
                            {
                                unitAttack(unit, u, iterator2);
                                unit.setActive(false);
                                unit = null;
                                unit_index = -1;
                                possible_attack_positions = new ArrayList<>();
                                moving_or_attacking = MOVING;
                                if(!game.checkAtleastOneActiveMyUnit())
                                {
                                    game.turn = -1;
                                    game.client.sendNextTurn(game.turn);
                                    game.activateMyUnits();
                                }
                                break;              
                            }
                            iterator2++;
                        }
                    }
                    
                }
            }
        }
    }
    
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private void littleBacktracking(int[] cursor, int direction, int distance)
    {
        if(distance > 3)
        {
            return;
        }

        if((cursor[0] >= 0 && cursor[0] < game.MAP_SIZE[0]) && (cursor[1] >= 0 && cursor[1] < game.MAP_SIZE[1]))
        {
            if(map[cursor[1]][cursor[0]] == 0)
            {
                return;
            }
        }
        else
        {
            return;
        }
        
        if(checkIfEnemyInPosition(cursor))
        {
            return;
        }

        boolean flag = true;
        for(Integer[] possible_new_position : possible_new_positions)
        {
            if(possible_new_position[0] == cursor[0] && possible_new_position[1] == cursor[1])
            {
                flag = false;
            }
        }
        if(flag)
        {
            if(unit.getX() != cursor[0] || unit.getY() != cursor[1])
            {
                Integer[] aux = {cursor[0], cursor[1]};        
                possible_new_positions.add(aux);
            }
        }

        //Izquierda
        if(direction != 2)
        {
            //System.out.println("Izquierda");
            int new_cursor[] = {cursor[0] - 1 , cursor[1] };
            littleBacktracking(new_cursor, 0, distance + 1);
        }

        //Arriba
        if(direction != 3)
        {
            //System.out.println("Arriba");
            int new_cursor[] = {cursor[0], cursor[1] - 1};
            littleBacktracking(new_cursor, 1, distance + 1);
        }

        //Derecha
        if(direction != 0)
        {
            //System.out.println("Derecha");
            int new_cursor[] = {cursor[0] + 1, cursor[1]};
            littleBacktracking(new_cursor, 2, distance + 1);
        }

        //Abajo
        if(direction != 1)
        {
            //System.out.println("Abajo");
            int new_cursor[] = {cursor[0], cursor[1] + 1};
            littleBacktracking(new_cursor, 3, distance + 1);
        }

        return;
    }
    
    private boolean checkIfEnemyInPosition(int[] position)
    {
        for(Unit u: game.units)
        {
            if(u.getX() == position[0] && u.getY() == position[1])
            {
                if(u != unit)
                {
                    if(unit.getIdTeam() != u.getIdTeam())
                    {
                        return true;
                    }
                }
                else
                {
                    return false;
                }
            }
        }
        return false;
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
    
    private void setAttackRange(int range)
    {        
        for(int i = -range; i <= range; i++)
        {
            for(int j = -range; j <= range; j++)
            {
                int aux = Math.abs(i) + Math.abs(j);
                if(aux != 0 && aux <= range)
                {
                    Integer[] aux2 = {unit.getX() + i, unit.getY() + j};        
                    possible_attack_positions.add(aux2);
                }
            }
        }
        
    }
    
    private void unitAttack(Unit attacking_unit, Unit defending_unit, int defending_unit_index) throws IOException
    {
        if(attacking_unit.getSoldierType() == 0)
        {
            if(attacking_unit.getSoldierType() == 0)
            {
                defending_unit.receiveDamage(4);
            }
            else if(attacking_unit.getSoldierType() == 1)
            {
                defending_unit.receiveDamage(2);
            }
            else if(attacking_unit.getSoldierType() == 2)
            {
                defending_unit.receiveDamage(6);
            }
        }
        else if(attacking_unit.getSoldierType() == 1)
        {
            if(attacking_unit.getSoldierType() == 0)
            {
                defending_unit.receiveDamage(6);
            }
            else if(attacking_unit.getSoldierType() == 1)
            {
                defending_unit.receiveDamage(4);
            }
            else if(attacking_unit.getSoldierType() == 2)
            {
                defending_unit.receiveDamage(2);
            }
        }
        else if(attacking_unit.getSoldierType() == 2)
        {
            if(attacking_unit.getSoldierType() == 0)
            {
                defending_unit.receiveDamage(2);
            }
            else if(attacking_unit.getSoldierType() == 1)
            {
                defending_unit.receiveDamage(6);
            }
            else if(attacking_unit.getSoldierType() == 2)
            {
                defending_unit.receiveDamage(4);
            }
        }
        if(defending_unit.checkIfDead())
        {
            game.units.remove(defending_unit_index);
            game.client.sendKillUnit(defending_unit_index); 
        }
    }
    
    
    //####################################################################################################

}