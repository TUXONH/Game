/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package multiplayer;

/**
 *
 * @author Omar
 */
import java.io.*;
import java.net.Socket;

public class Cliente implements Runnable
{
    public Cliente(String HOST, int PUERTO)
    {
        try
        {
            //estado.setText("Conectando");
            Socket miCliente = new Socket(HOST, PUERTO);
            DataOutputStream dOut = new DataOutputStream(miCliente.getOutputStream());

            dOut.writeUTF("Putas");

            dOut.flush();

            //InputStream aux = miCliente.getInputStream();
            //DataInputStream flujo = new DataInputStream(aux);
            //estado.setText("Conectado al servidor");// + aux.read());
            //System.out.println(flujo.readUTF());
            dOut.close();
            miCliente.close();
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void run()
    {

    }
}