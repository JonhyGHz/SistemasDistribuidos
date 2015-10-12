package conexionoracle;

import java.sql.Connection;
import java.sql.SQLException;


public class Conexion {
    public static java.sql.Connection conection;
    public java.sql.Statement sql;
    public static String url_bd = "jdbc:oracle:thin:@192.168.43.76:1521:XE";//esta es la ruta de la base de datos de oracle
//    public static String user = "";
//    public static String pas = ""; 
    public static String controlador = "oracle.jdbc.OracleDriver";//el driver se tiene que agregar el driver al proyecto de netbeans
    
    public Connection Conectar(String user,String pas) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException
    {//los parametros que 
       try{ // cargar el controlador
       
        Class.forName(controlador).newInstance();
        // llamamos el metodo conectar
        conection = java.sql.DriverManager.getConnection(url_bd, user, pas);
        // crear una sentencia sql
        //sql = conection.createStatement(java.sql.ResultSet.TYPE_SCROLL_INSENSITIVE, 
               // java.sql.ResultSet.CONCUR_UPDATABLE);
        if(conection!=null)
        {
            System.out.println("Conexion exitosa a Oracle");
       
        }else{
            System.out.println("Conexion erronea");
       
        }
        
        }catch(Exception e){e.printStackTrace();} 
       return conection;
    }
    
}
