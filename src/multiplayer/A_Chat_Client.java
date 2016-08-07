package multiplayer;

import game.Game;
import java.net.*;
import java.io.*;
import java.util.Scanner;

public class A_Chat_Client implements Runnable{
    
    Socket SOCK;
    Scanner INPUT;
    Scanner SEND = new Scanner(System.in);
    PrintWriter OUT;

    public A_Chat_Client(Socket X) 
    {
        this.SOCK = X;
    }
    
    public void run()
    {
        try
        {
            try
            {
                INPUT = new Scanner(SOCK.getInputStream());
                OUT = new PrintWriter(SOCK.getOutputStream());
                OUT.flush();
                CheckStream();
            }
            finally
            {
                SOCK.close();
            }
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }
    
    
    public void CheckStream()
    {
        while(true)
        {
            RECEIVE();
        }
    }
    
    public void RECEIVE()
    {
        if(INPUT.hasNext())
        {
            String MESSAGE = INPUT.nextLine();
            
            if(MESSAGE.contains("#?!"))
            {
                String TEMP1 = MESSAGE.substring(3);
                TEMP1 = TEMP1.replace("[", "");
                TEMP1 = TEMP1.replace("]", "");
                
                String[] CurrentUsers = TEMP1.split(", ");
                
                Game gm = new Game();
                gm.JL_ONLINE.setListData(CurrentUsers);
            }
            else
            {
                Game gm = new Game();
                gm.TA_CONVERSATION.append(MESSAGE + "\n");
            }
        }
    }
    
    public void SEND(String x)
    {
        Game gm = new Game();
        OUT.println(gm.UserName + ": " + x);
        OUT.flush();
        gm.TF_Message.setText("");
    }
    
    
    
}
