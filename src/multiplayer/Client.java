/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package multiplayer;

/**
 *
 * @author poseidon9
 */
import game.Game;
import java.io.*;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Client extends Thread
{
    //MESSAGES TYPE LIST
    private final int MESSAGE_CAMERA_POSITION = 0;
    private final int MESSAGE_MOVE_UNIT = 1;
    private final int MESSAGE_KILL_UNIT = 2;
    private final int MESSAGE_NEXT_TURN = 3;

    //END MESSAGES TYPE LIST

    private Game game;
    private Socket miCliente;
    private DataOutputStream dOut;
    private DataInputStream dIn;

    public Client(Game game, String host, int puerto)
    {
        this.game = game;
        try
        {
            miCliente = new Socket(host, puerto);
            dIn = new DataInputStream(miCliente.getInputStream());
            dOut = new DataOutputStream(miCliente.getOutputStream());
            
            game.team = dIn.readInt();
            System.out.println("Team: " + game.team);
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
    }

    
    @Override
    public void run()
    {
        int message_type;
        try {
            System.out.println("waiting for other players...");
            dIn.readInt();
            while(true)
            {
                System.out.println("waiting for messages...");
                message_type = dIn.readInt();
                if(game.team != game.turn)
                {
                    if(message_type == MESSAGE_CAMERA_POSITION)
                    {
                        getCameraPosition();
                    }
                    else if(message_type == MESSAGE_MOVE_UNIT)
                    {
                        getMoveUnit();
                    }
                    else if(message_type == MESSAGE_KILL_UNIT)
                    {
                        getKillUnit();
                    }
                    else if(message_type == MESSAGE_NEXT_TURN)
                    {
                        getNextTurn();
                    }
                }
                else
                {
                    
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public void stopClient() throws IOException
    {
        miCliente.close();
    }
    
    
    //SENT FROM GAME!
    public void sendCameraPosition() throws IOException
    {
        //Message type camera position
        dOut.writeInt(MESSAGE_CAMERA_POSITION);
        
        System.out.println("Escribiendo Datos, Camera Position");
        dOut.writeInt(game.camera_position[0]);
        dOut.writeInt(game.camera_position[1]);
        dOut.writeInt(game.cursor.getX());
        dOut.writeInt(game.cursor.getY());
        dOut.flush();
    }
    
    
    private void getCameraPosition() throws IOException
    {
        System.out.println("Consiguiendo Datos, Camera Position");
        game.camera_position[0] = dIn.readInt();
        game.camera_position[1] = dIn.readInt();
        game.cursor.setX(dIn.readInt());
        game.cursor.setY(dIn.readInt());
    }
    
    public void sendMoveUnit(int[] unit_position, int unit_index) throws IOException
    {
        //Message type camera position
        dOut.writeInt(MESSAGE_MOVE_UNIT);
        
        System.out.println("Escribiendo Datos, Move Unit");
        dOut.writeInt(unit_index);
        dOut.writeInt(unit_position[0]);
        dOut.writeInt(unit_position[1]);
        dOut.flush();
    }
    
    
    private void getMoveUnit() throws IOException
    {
        System.out.println("Consiguiendo Datos, Move Unit");
        int unit_index = dIn.readInt();
        int unit_new_x = dIn.readInt();
        int unit_new_y = dIn.readInt();
        game.units.get(unit_index).move(unit_new_x, unit_new_y);
    }

    
    public void sendKillUnit(int unit_index) throws IOException {
        //Message type camera position
        dOut.writeInt(MESSAGE_KILL_UNIT);
        
        System.out.println("Escribiendo Datos, Kill Unit");
        dOut.writeInt(unit_index);
        dOut.flush();
    }
    
    
    public void getKillUnit() throws IOException {
        System.out.println("Consiguiendo Datos, Kill Unit");
        int unit_index = dIn.readInt();
        game.units.remove(unit_index);
    }
    
    
    public void sendNextTurn(int turn) throws IOException {
        //Message type camera position
        dOut.writeInt(MESSAGE_NEXT_TURN);
        
        System.out.println("Escribiendo Datos, Next Turn");
        dOut.writeInt(turn);
        dOut.flush();
    }
    
    public void getNextTurn() throws IOException {
        System.out.println("Consiguiendo Datos, Next Turn");
        int turn = dIn.readInt();
        game.turn = turn;
        System.out.println(game.team + " wtf " + game.checkAtleastOneAliveMyUnit());
        System.out.println(game.my_units);
        if(game.turn == game.team)
        {
            System.out.println(game.team + " wtf " + game.checkAtleastOneAliveMyUnit());
            System.out.println(game.my_units);
            if(!game.checkAtleastOneAliveMyUnit())
            {
                game.turn = -1;
                sendNextTurn(turn);
            }
        }
    }
}