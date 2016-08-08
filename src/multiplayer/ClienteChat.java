
package multiplayer;

import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import javax.swing.*;

public class ClienteChat{
    private JTextArea mensajesChat;
    private Socket socket;
    
    private int puerto = 8000;
    private String host = "localhost";
    private String usuario = "tuxon";
    
    public ClienteChat(Container c){
        
        // Elementos de la ventana
        mensajesChat = new JTextArea();
        mensajesChat.setEnabled(false); // El area de mensajes del chat no se debe de poder editar
        mensajesChat.setLineWrap(true); // Las lineas se parten al llegar al ancho del textArea
        mensajesChat.setWrapStyleWord(true); // Las lineas se parten entre palabras (por los espacios blancos)
        JScrollPane scrollMensajesChat = new JScrollPane(mensajesChat);
        JTextField tfMensaje = new JTextField("");
        JButton btEnviar = new JButton("Enviar");
        
        
        // Colocacion de los componentes en la ventana
        //
        mensajesChat.setSize(540, 300);
        mensajesChat.setLocation(740, 320);
        
        c.add(mensajesChat);
        //Cambiar para que sea scrollMensajesChat y no mensajesChats
        
        
        tfMensaje.setSize(420, 24);
        tfMensaje.setLocation(740, 640);
        
        c.add(tfMensaje);
        
        btEnviar.setSize(100, 24);
        btEnviar.setLocation(1180, 640);
        
        c.add(btEnviar);

        
        System.out.println("Quieres conectarte a " + host + " en el puerto " + puerto + " con el nombre de ususario: " + usuario + ".");
        
        // Se crea el socket para conectar con el Sevidor del Chat
        try {
            socket = new Socket(host, puerto);
        } catch (UnknownHostException ex) {
            System.out.println("No se ha podido conectar con el servidor (" + ex.getMessage() + ").");
        } catch (IOException ex) {
            System.out.println("No se ha podido conectar con el servidor (" + ex.getMessage() + ").");
        }
        
        // Accion para el boton enviar
        btEnviar.addActionListener(new ConexionServidor(socket, tfMensaje, usuario));
        System.out.println("termino");
        
    }
    
    /**
     * Recibe los mensajes del chat reenviados por el servidor
     */
    public void recibirMensajesServidor(){
        // Obtiene el flujo de entrada del socket
        Thread thread = new Thread() 
        {
            @Override
            public void run() 
            {
                String mensaje;        
                boolean conectado = true;
        
                DataInputStream entradaDatos = null;
                try {
                    entradaDatos = new DataInputStream(socket.getInputStream());
                    System.out.println("entro");
                } catch (IOException ex) {
                    System.out.println("Error al crear el stream de entrada: " + ex.getMessage());
                } catch (NullPointerException ex) {
                    System.out.println("El socket no se creo correctamente. ");
                }
                while(conectado)
                {
                    try {
                        mensaje = entradaDatos.readUTF();
                        mensajesChat.append(mensaje + System.lineSeparator());
                        System.out.println("entro bucle");
                    } catch (IOException ex) {
                        System.out.println("Error al leer del stream de entrada: " + ex.getMessage());
                        conectado = false;
                    } catch (NullPointerException ex) {
                        System.out.println("El socket no se creo correctamente. ");
                        conectado = false;
                    }
                }
            }
        };
        thread.start();
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // Carga el archivo de configuracion de log4J     
    }

}