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
import java.io.DataInputStream;
import java.net.Socket;
import java.net.InetAddress;
import java.net.ServerSocket;

import javax.swing.JTextField;
import javax.swing.JTextPane;

public class Servidor implements Runnable
{
    public static int puerto;
    private ServerSocket miServidor;

    public void stopRunning()
    {
        try
        {
            miServidor.close();
        }
        catch (Exception e)
        {
            // TODO Auto-generated catch block
            System.out.println("\nDetenido");
        }
    }

    public void run()
    {
        int numCli = 0;
        try
        {
            miServidor = new ServerSocket(puerto);

            System.out.println("Inicio servidor");
            System.out.println("Iniciando en el puerto " +puerto+ " en la direccion "+ InetAddress.getLocalHost().getHostAddress());	
            while(true)
            {
                Socket miCliente = miServidor.accept();//crear objeto
                DataInputStream dIn = new DataInputStream(miCliente.getInputStream());
                numCli++;
                //java.io.OutputStream aux = miCliente.getOutputStream();
                //DataOutputStream flujo = new DataOutputStream(aux);
                miCliente.close();
            }
        }
        catch(Exception e)
        {
            
        }
    }
}