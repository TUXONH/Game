/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package multiplayer;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import javax.swing.*;
 /**
 *
 * @author Mauricio
 */
public class ClienteChat extends JFrame {
    
    private JTextArea mensajesChat;
    private Socket socket;
    
    private int puerto;
    private String host;
    private String usuario;
    
    public ClienteChat(JTextArea mensajesChat,  JTextField tfMensaje, JButton btEnviar ){
        super("Cliente Chat");
        
        // Ventana de configuracion inicial
        host = "127.0.0.1";
        puerto = 8000;
        usuario = "tuxon";
        
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
        
    }
    
    /**
     * Recibe los mensajes del chat reenviados por el servidor
     */
    public void recibirMensajesServidor(){
        // Obtiene el flujo de entrada del socket
        DataInputStream entradaDatos = null;
        String mensaje;
        try {
            entradaDatos = new DataInputStream(socket.getInputStream());
        } catch (IOException ex) {
            System.out.println("Error al crear el stream de entrada: " + ex.getMessage());
        } catch (NullPointerException ex) {
            System.out.println("El socket no se creo correctamente. ");
        }
        
        // Bucle infinito que recibe mensajes del servidor
        boolean conectado = true;
        while (conectado) {
            try {
                mensaje = entradaDatos.readUTF();
                mensajesChat.append(mensaje + System.lineSeparator());
            } catch (IOException ex) {
                System.out.println("Error al leer del stream de entrada: " + ex.getMessage());
                conectado = false;
            } catch (NullPointerException ex) {
                System.out.println("El socket no se creo correctamente. ");
                conectado = false;
            }
        }
    } 
}  
