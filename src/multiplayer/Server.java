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
    private ServerSocket miServidor;
    private List<ServerClientThread> miClientes = new ArrayList<>();
    private int turn = 0;

    public Server(int puerto, int max_players) throws IOException
    {
        miServidor = new ServerSocket(puerto);
        System.out.println("Inicio servidor");
        System.out.println("Iniciando en el puerto " +puerto+ " en la direccion "+ InetAddress.getLocalHost().getHostAddress());

        for(int i = 0; i < max_players; i++)
        {
            Socket miCliente = miServidor.accept();//crear objeto            
            DataInputStream dIn = new DataInputStream(miCliente.getInputStream());
            DataOutputStream dOut = new DataOutputStream(miCliente.getOutputStream());
            
            dOut.write(i);
            
            ServerClientThread sct = new ServerClientThread(miCliente, dIn, dOut);
            
            miClientes.add(sct);
            
            System.out.println("Jugador "+i+" Conectado!");
        }
        System.out.println("Server lleno!");
    }
    
    public void stopRunning() throws IOException
    {
        miServidor.close();
        for(ServerClientThread miCliente: miClientes)
        {
            miCliente.close();
        }
    }

    @Override
    public void run() {
        try {
            for(ServerClientThread miCliente: miClientes)
            {
                System.out.println("Mandando Datos Iniciales");
                miCliente.dOut.writeInt(0);
                miCliente.dOut.flush();
            }

            while(true)
            {
                    ServerClientThread sct = miClientes.get(turn);

                    System.out.println("Consiguiendo Datos");
                    int map_x = sct.dIn.readInt();
                    int map_y = sct.dIn.readInt();

                    for(ServerClientThread miCliente: miClientes)
                    {
                        System.out.println("Mandando Datos");
                        miCliente.dOut.writeInt(map_x);
                        miCliente.dOut.writeInt(map_y);
                        miCliente.dOut.flush();
                    }

                    Thread.sleep(100);
            }
        } catch (IOException | InterruptedException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void sendCameraPosition()
    {
        
    }
}