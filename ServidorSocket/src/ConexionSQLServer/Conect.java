/*
 CLASE PARA REALIZAR LA CONEXION A LA BASE DE DATOS DE SQL SERVER
 */
package ConexionSQLServer;


import java.sql.Connection;
import java.sql.SQLException;


public class Conect {
    public static java.sql.Connection conection;
    //public java.sql.Statement sql;
    public static String url_bd = "jdbc:sqlserver://192.168.43.74:1433;databaseName=ServiciosEscolares";
//    public static String user = "";
//    public static String pas = ""; 
    public static String controlador = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    
    public Connection Conectar(String user,String pas) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException
    {
       try{ // cargar el controlador
       
        Class.forName(controlador).newInstance();
        // llamamos el metodo conectar
        conection = java.sql.DriverManager.getConnection(url_bd, user, pas);
        // crear una sentencia sql
        //sql = conection.createStatement(java.sql.ResultSet.TYPE_SCROLL_INSENSITIVE, 
               // java.sql.ResultSet.CONCUR_UPDATABLE);
        if(conection!=null)
        {
            System.out.println("Conexion exitosa a SQL Server");
       
        }else{
            System.out.println("Conexion erronea");
       
        }
        
        }catch(Exception e){e.printStackTrace();} 
       return conection;
    }
}
