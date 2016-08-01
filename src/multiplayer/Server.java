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
import game.Unit;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server extends Thread
{
    //MESSAGES TYPE LIST
    private final int MESSAGE_CAMERA_POSITION = 0;
    private final int MESSAGE_MOVE_UNIT = 1;
    private final int MESSAGE_KILL_UNIT = 2;
    private final int MESSAGE_NEXT_TURN = 3;

    //END MESSAGES TYPE LIST
    
    private ServerSocket miServidor;
    private List<ServerClientThread> miClientes = new ArrayList<>();
    private int turn = 0;
    private int max_players;

    public Server(int puerto, int max_players) throws IOException
    {
        this.max_players = max_players;
        miServidor = new ServerSocket(puerto);
        System.out.println("Inicio servidor");
        System.out.println("Iniciando en el puerto " +puerto+ " en la direccion "+ InetAddress.getLocalHost().getHostAddress());
    }
    
    
    private void startServer() throws IOException
    {
        for(int i = 0; i < max_players; i++)
        {
            Socket miCliente = miServidor.accept();//crear objeto            
            DataInputStream dIn = new DataInputStream(miCliente.getInputStream());
            DataOutputStream dOut = new DataOutputStream(miCliente.getOutputStream());

            dOut.writeInt(i);

            ServerClientThread sct = new ServerClientThread(miCliente, dIn, dOut);

            miClientes.add(sct);

            System.out.println("Jugador "+i+" Conectado!");
        }
        System.out.println("Server lleno!");

        for(ServerClientThread miCliente: miClientes)
        {
            System.out.println("Mandando Datos Iniciales");
            miCliente.dOut.writeInt(0);
            miCliente.dOut.flush();
        }
    }
    
    
    public void stopServer() throws IOException
    {
        miServidor.close();
        for(ServerClientThread miCliente: miClientes)
        {
            miCliente.close();
        }
    }

    
    @Override
    public void run()
    {
        ServerClientThread sct;
        int message_type;
        try
        {
            startServer();

            while(true)
            {
                sct = miClientes.get(turn);
                System.out.println("Team turn: " + turn);
                message_type = sct.dIn.readInt();
                
                if(message_type == MESSAGE_CAMERA_POSITION)
                {
                    sendCameraPosition(sct);
                }
                else if(message_type == MESSAGE_MOVE_UNIT)
                {
                    sendMoveUnit(sct);
                }
                else if(message_type == MESSAGE_KILL_UNIT)
                {
                    sendKillUnit(sct);
                }
                else if(message_type == MESSAGE_NEXT_TURN)
                {
                    sendNextTurn(sct);
                }
            }
        }
        catch (IOException ex)
        {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public void sendCameraPosition(ServerClientThread sct) throws IOException
    {
        int[] map_coordinates = getCameraPosition(sct);
        for(ServerClientThread miCliente: miClientes)
        {
            if(miCliente != sct)
            {
                miCliente.dOut.writeInt(MESSAGE_CAMERA_POSITION);
                
                System.out.println("Server Mandando Datos, Camera Position");
                miCliente.dOut.writeInt(map_coordinates[0]);
                miCliente.dOut.writeInt(map_coordinates[1]);
                
                miCliente.dOut.writeInt(map_coordinates[2]);
                miCliente.dOut.writeInt(map_coordinates[3]);
                miCliente.dOut.flush();
            }
        }
    }
    
    
    private int[] getCameraPosition(ServerClientThread sct) throws IOException
    {
        System.out.println("Server Consiguiendo Datos, Camera Position");
        int map_x = sct.dIn.readInt();
        int map_y = sct.dIn.readInt();
        
        int cursor_x = sct.dIn.readInt();
        int cursor_y = sct.dIn.readInt();
        int[] map_coordinates = {map_x, map_y, cursor_x, cursor_y};
        return map_coordinates;
    }
    
    public void sendMoveUnit(ServerClientThread sct) throws IOException
    {
        int[] unit_references = getMoveUnit(sct);
        for(ServerClientThread miCliente: miClientes)
        {
            if(miCliente != sct)
            {
                miCliente.dOut.writeInt(MESSAGE_MOVE_UNIT);
                
                System.out.println("Server Mandando Datos, Move Unit");
                miCliente.dOut.writeInt(unit_references[0]);
                miCliente.dOut.writeInt(unit_references[1]);
                miCliente.dOut.writeInt(unit_references[2]);
                miCliente.dOut.flush();
            }
        }
    }
    
    
    private int[] getMoveUnit(ServerClientThread sct) throws IOException
    {
        System.out.println("Server Consiguiendo Datos, Move Unit");
        int unit_index = sct.dIn.readInt();
        int unit_new_x = sct.dIn.readInt();
        int unit_new_y = sct.dIn.readInt();
        
        int[] unit_references = {unit_index, unit_new_x, unit_new_y};
        
        return unit_references;
    }
    
    
    public void sendKillUnit(ServerClientThread sct) throws IOException
    {
        int unit_index = getKillUnit(sct);
        for(ServerClientThread miCliente: miClientes)
        {
            if(miCliente != sct)
            {
                miCliente.dOut.writeInt(MESSAGE_KILL_UNIT);
                
                System.out.println("Server Mandando Datos, Kill Unit");
                miCliente.dOut.writeInt(unit_index);
                miCliente.dOut.flush();
            }
        }
    }
    
    
    private int getKillUnit(ServerClientThread sct) throws IOException
    {
        System.out.println("Server Consiguiendo Datos, Kill Unit");
        int unit_index = sct.dIn.readInt();
        
        return unit_index;
    }
    
    
    public void sendNextTurn(ServerClientThread sct) throws IOException
    {
        int new_turn = getNextTurn(sct);
        for(ServerClientThread miCliente: miClientes)
        {
            miCliente.dOut.writeInt(MESSAGE_NEXT_TURN);

            System.out.println("Server Mandando Datos, Next Turn");
            miCliente.dOut.writeInt(new_turn);
            miCliente.dOut.flush();
        }
    }
    
    
    private int getNextTurn(ServerClientThread sct) throws IOException
    {
        System.out.println("Server Consiguiendo Datos, Next Turn");
        int new_turn = sct.dIn.readInt();
        new_turn = (turn + 1) % max_players;
        turn = new_turn;
        return new_turn;
    }
}