package envioinformacionsockets;
import java.io.IOException;  
import java.io.ObjectInputStream;  
import java.io.ObjectOutputStream;  
import java.net.ServerSocket;  
import java.net.Socket;  
  
  
public class Servidor { 
    
    
    public static void main(String[] args) throws IOException, ClassNotFoundException {  
        ServerSocket server = new ServerSocket(1234);  
          
        for(int i=0; i<3; i++){
            Socket conexion = server.accept();
            ObjectInputStream entrada = new ObjectInputStream(conexion.getInputStream());
            //ObjectOutputStream salida = new ObjectOutputStream(conexion.getOutputStream()); 
            Alumno alumno = (Alumno) entrada.readObject();
            System.out.println(alumno.toString());
            entrada.close();
        }
    }  
    
} 