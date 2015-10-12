package envioinformacionsockets;
import java.io.Serializable;  
  
  
@SuppressWarnings("serial")  
public class Teste implements Serializable {  

    public Teste(String head) {
        this.head = head;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }
    
    private String head;   
} 