package envioinformacionsockets;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;  
import java.io.InputStream;
import java.io.ObjectInputStream;  
import java.io.ObjectOutputStream;  
import java.io.OutputStream;
import java.net.Socket;  
  
  
public class Cliente {   
    
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Teste teste = new Teste("Jorge"); 
            Alumno alumno = new Alumno("jonathan","zarate","hernandez","12520214","Fisica General");
                  
        Socket client = new Socket("192.168.43.124", 1234);   
        ObjectOutputStream output = new ObjectOutputStream(client.getOutputStream()); 
        output.writeObject(alumno);
        output.flush();
           
        output.flush();   
        
        
        InputStream aux = client.getInputStream();
        DataInputStream flujo = new DataInputStream(aux);
        System.out.println(flujo.readUTF());
        output.close();
        //client.close();
    } 
    
}  