/*
 * INSTITUTO TECNOLOGICO DE CHILPANCINGO
 * INGENIERIA EN SISTEMAS COMPUTACIONALES
 * SISTEMAS DISTRIBUIDOS
 * SERVIDOR PARA REALIZAR CONSULTA DE ALUMNOS
 * DONDE EL SERVIDOR RECIBE LA MATRICULA DEL ALUMNO Y NOMBRE DE LA MATERIA
 * EL MISMO SERVIDOR SERA CAPAZ DE RENORTAR LA CALIFICACION ACTUAL DE LA MATERIA

 * ALUMNOS: JONATHAN ZARATE HERNANDEZ
            ERICK ZARATE HERNANDEZ
            LUCIA MOTA CASTREJON
            JORGE MAÑON ARROYO
 */
package server;


import ConexionSQLServer.Conect;
import conexionoracle.Conexion;
import envioinformacionsockets.Alumno;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.text.DefaultCaret;
import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Servidor extends JFrame
{
    
    //DECLARACION DE VARIABLES
    private Connection connectionORACLE;
    private Connection connectionServer;
    private Statement sentenciaSQL;
    private ResultSet resultado;
    //CONSTRUCTOR
    public Servidor() throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException
    {
        initComponets();
        Conect conexSQLServer = new Conect();
        connectionServer = conexSQLServer.Conectar("Yetzali","12345");
        Conexion conexOracle = new Conexion();
        connectionORACLE = conexOracle.Conectar("SERVICIOS_ESCOLARES","itch1984");
    }
    //METODO initComponets inicializa los componentes que se visualizaran en la interfaz de usuario
    private void initComponets()
    {
        scroll = new javax.swing.JScrollPane();
        textArea = new javax.swing.JTextArea();
        
        setLocationRelativeTo(null);
        setSize(400,200);
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("SERVIDOR CONTROL ESCOLAR ITCH");
        
        textArea.setColumns(20);
        textArea.setRows(5);
        scroll.setViewportView(textArea);
        
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scroll, javax.swing.GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(scroll, javax.swing.GroupLayout.DEFAULT_SIZE, 139, Short.MAX_VALUE)
                .addContainerGap())
        );
    }
        
    //METODO inicarServidor
    public void iniciarServidor()
    {
       //Cargamos la libreria Log4j para permitir filtrar los mensajes.
        PropertyConfigurator.configure("log4j.properties");        
        Logger log = Logger.getLogger(Servidor.class);
        
        //Actualizamos la barra de desplazamiento
        DefaultCaret caret = (DefaultCaret)textArea.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        
        int puerto = 1234; //Definimos el puerto a utilizar
        int maximoConexiones = 10; // Maximo de conexiones simultaneas
        ServerSocket servidor = null; 
        Socket socket = null;
        
        //creacion del objeto alumno
        //Alumno alumno = new Alumno();
        
        //try-catch para capturar posibles excepciones
       
        try
        {
            // Se crea el serverSocket
            servidor = new ServerSocket(puerto, maximoConexiones);
            // Bucle infinito para esperar conexiones
            while (true) {
                textArea.append("Servidor a la espera de conexiones."+System.lineSeparator());
                socket = servidor.accept();//Se acepta la conexion
                textArea.append("Cliente con la IP " + socket.getInetAddress().getHostName() + " conectado."
                    +System.lineSeparator());
                
                ObjectInputStream entrada = new ObjectInputStream(socket.getInputStream()); 
                //Object obj = entrada.readObject();
                Alumno alumno = (Alumno) entrada.readObject();
                
                System.out.println(alumno.toString());
                
                //SE CARGA INFORMACION A LA BASE DE DATOS
                //VALIDAR SI EXISTE EL ALUMNO EN LA BD
                String matricula = alumno.getNumeroControl();
                System.out.println(alumno.getNumeroControl());
                //PRIMERO SE REALIZA LA BUSQUEDA DEL ALUMNO EN LA BASE DE DATOS SQL SERVER
                //busqueda de la matricula
                resultado = obtenerMatricula(connectionServer);
                boolean banderaMatricula = false;
                while(resultado.next()){
                    
                    if(matricula.equalsIgnoreCase(resultado.getString(1)))//si esta la matrucla
                    {
                        //
                        System.out.println("Si esta la matricula "+matricula);
                        banderaMatricula = true;
                        break;
                    }else
                    {
                        banderaMatricula = false;
                        //enviar mensaje que la matricula no existe
                    }
                    
                }
                resultado.close();
                //busqueda de la materia
                String materia = alumno.getMateria();
                System.out.println(alumno.getMateria());
                boolean banderaMateria = false;
                resultado = obtenerMateria(connectionServer);
                while(resultado.next()){
                    
                    if(materia.equalsIgnoreCase(resultado.getString(1)))//si esta la matrucla
                    {
                        //
                        System.out.println("Si esta la materia "+materia);
                        banderaMateria = true;
                        break;
                    }else
                    {
                        banderaMateria = false;
                        //enviar mensaje que la matricula no existe
                    }
                    
                }
                resultado.close();
                
                //OBTENER EL PROMEDIO
                long nMatricula = Long.parseLong(matricula);
                if(banderaMateria==true && banderaMatricula==true)
                {
                    System.out.print("Si encontre en sql server");
                    sentenciaSQL = connectionServer.createStatement();
                    resultado = sentenciaSQL.executeQuery("select (MateriaAlumno.Parcial1+MateriaAlumno.Parcial2+MateriaAlumno.Parcial3+MateriaAlumno.Parcial4+MateriaAlumno.Parcial5)/5 AS PROMEDIO from MateriaAlumno where MateriaAlumno.IdMateria=(select Materia.IdMateria from Materia where Materia.Nombre='"+materia+"') AND MateriaAlumno.Matricula="+nMatricula);
                   
//                    System.out.println(resultado.first());
                    String promedio="";
                    while(resultado.next())
                    {
                        promedio = resultado.getString(1);
                        System.out.println(promedio);
                    }
                    resultado.close();
                    OutputStream aux = socket.getOutputStream();
                    DataOutputStream flujo= new DataOutputStream( aux );
                    if(promedio.equals(""))
                    {flujo.writeUTF( "NO ESTAS INSCRITO EN LA MATERIA");}else{
                    flujo.writeUTF( "Tu Promedio es: "+promedio);}
                }else{
                    //ahora buscar en oracle
                    System.out.println("buscare en oracle");
                    ResultSet resultadoSQL = obtenerMatricula(connectionORACLE);
                    banderaMatricula = false;
                    while(resultadoSQL.next()){
                    
                        if(matricula.equalsIgnoreCase(resultadoSQL.getString(1)))//si esta la matrucla
                        {
                        //
                            System.out.println("Si esta la matricula "+matricula);
                            banderaMatricula = true;
                            break;
                        }else
                        {
                            banderaMatricula = false;
                        //enviar mensaje que la matricula no existe
                        }
                    
                    }
                    resultadoSQL.close();
                    //busqueda de la materia
                    System.out.println(alumno.getMateria());
                    banderaMateria = false;
                    resultadoSQL = obtenerMateria(connectionORACLE);
                    while(resultadoSQL.next()){
                    
                        if(materia.equalsIgnoreCase(resultadoSQL.getString(1)))//si esta la materia
                        {
                        //
                            System.out.println("Si esta la materia "+materia);
                            banderaMateria = true;
                            break;
                        }else
                        {
                            banderaMateria = false;
                        //enviar mensaje que la matricula no existe
                        }
                    
                    }
                    resultadoSQL.close();
                    
                    //obtenemos el promedio
                    nMatricula = Long.parseLong(matricula);
                    if(banderaMateria==true && banderaMatricula==true)
                    {
                        System.out.print("Si encontre en oracle");
                    sentenciaSQL = connectionORACLE.createStatement();
                    resultadoSQL = sentenciaSQL.executeQuery("SELECT (M.PARCIAL_1 + M.PARCIAL_2 + M.PARCIAL_3 + M.PARCIAL_4 + M.PARCIAL_5)/5 AS PROMEDIO FROM MATERIA_ALUMNO M WHERE M.ID_MATERIA = (SELECT MA.ID_MATERIA FROM MATERIA MA WHERE MA.NOMBRE = '"+materia+"') AND M.MATRICULA ="+nMatricula);
                   
//                    System.out.println(resultado.first());
                    String promedio="";
                    while(resultadoSQL.next())
                    {
                        promedio = resultadoSQL.getString(1);
                        System.out.println("promedio:"+promedio);
                    }
                    resultadoSQL.close();
                    OutputStream aux = socket.getOutputStream();
                    DataOutputStream flujo= new DataOutputStream( aux );
                    if(promedio.equals(""))
                    {flujo.writeUTF( "NO ESTAS INSCRITO EN LA MATERIA");}else{
                    flujo.writeUTF( "Tu Promedio es: "+promedio);}
                    }else
                    {
                        //mandar mensaje que el alumno y/o  materia no existe
                        OutputStream aux = socket.getOutputStream();
                        DataOutputStream flujo= new DataOutputStream( aux );
                        flujo.writeUTF( "El ALUMNO Y/O LA MATERIA NO EXISTEN");
                    }
                    
                }
               
                
                
//                sentenciaSQL = connectionServer.createStatement();
//                resultado = sentenciaSQL.executeQuery("select (MateriaAlumno.Parcial1+MateriaAlumno.Parcial2+MateriaAlumno.Parcial3+MateriaAlumno.Parcial4+MateriaAlumno.Parcial5)/5 AS PROMEDIO from MateriaAlumno where MateriaAlumno.IdMateria=(select Materia.IdMateria from Materia where Materia.Nombre='FISICA GENERAL') AND MateriaAlumno.Matricula=12520214");
//                
                
                
                
                //ENVIA INFORMACION
                OutputStream aux = socket.getOutputStream();
                DataOutputStream flujo= new DataOutputStream( aux );
                flujo.writeUTF( "hola: "+alumno.getNombre());
                //socket.close();
                entrada.close();
                
            }
            
        }catch(Exception e){
            e.printStackTrace();
            textArea.append("Error: " + e.getMessage()+System.lineSeparator());
        
        }
        finally{
            try {
                socket.close();
                servidor.close();
            } catch (IOException ex) {
                textArea.append("Error al cerrar el servidor: " + ex.getMessage()+System.lineSeparator());
            }
        }
        
        
    }
    public ResultSet obtenerMatricula(Connection c) throws SQLException
    {
        try
        {
            Statement sentencia;
            sentencia = c.createStatement();
            resultado = sentencia.executeQuery("SELECT matricula FROM Alumno");
            //sentencia.close();
        }catch(Exception e){System.out.print("Error en el select");}
        return resultado;
    }
    
    public ResultSet obtenerMateria(Connection c) throws SQLException
    {
        try
        {
            Statement sentencia;
            sentencia = c.createStatement();
            resultado = sentencia.executeQuery("SELECT nombre FROM Materia");
            //sentencia.close();
        }catch(Exception e){System.out.print("Error en el select");}
        return resultado;
    }
    public static void main(String args[]) {
       
        try{
            Servidor objServer= new Servidor();
        objServer.setVisible(true);
        objServer.iniciarServidor();
        }catch(Exception e){}
        
    }
    
    //VARIABLES DE COMPONENTES
    JTextArea textArea;
    JScrollPane scroll;
}
