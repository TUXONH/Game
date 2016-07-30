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
            
            game.team = dIn.read();
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void run()
    {
        try {
            dIn.readInt();
            Thread.sleep(5000);
            while(true)
            {
                    if(game.team == game.turn)
                    {
                        System.out.println("Escribiendo Datos");
                        dOut.writeInt(game.camera_position[0]);
                        dOut.writeInt(game.camera_position[1]);
                        dOut.writeInt(game.cursor.getX());
                        dOut.writeInt(game.cursor.getY());
                        dOut.flush();
                    }
                    else
                    {
                        System.out.println("Consiguiendo Datos");
                        game.camera_position[0] = dIn.readInt();
                        game.camera_position[1] = dIn.readInt();
                        game.cursor.setX(dIn.readInt());
                        game.cursor.setY(dIn.readInt());
                    }

                    Thread.sleep(250);
            }
        } catch (IOException | InterruptedException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void stopClient() throws IOException
    {
        miCliente.close();
    }
}