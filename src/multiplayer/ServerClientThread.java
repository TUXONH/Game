/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package multiplayer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 *
 * @author poseidon9
 */
public class ServerClientThread
{
    public Socket miCliente;
    public DataInputStream dIn;
    public DataOutputStream dOut;

    public ServerClientThread(Socket miCliente,DataInputStream dIn, DataOutputStream dOut)
    {
        this.miCliente = miCliente;
        this.dIn = dIn;
        this.dOut = dOut;
    }
    
    public void close() throws IOException
    {
        miCliente.close();
        dIn.close();
        dOut.close();
    }
}