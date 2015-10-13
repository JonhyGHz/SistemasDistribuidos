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
        //se realizan las conexiones a las base de datos
        //NOTA: NO ES RECOMENDABLE USAR ESTE TIPO DE IMPLEMENTACION, YA QUE ESTAN VISIBLES EL USUARIO Y PASSWORD
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
        int maximoConexiones = 20; // Maximo de conexiones simultaneas
        ServerSocket servidor = null; //Declaramos el socketServidor
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
                String matricula = alumno.getNumeroControl().toUpperCase();
                System.out.println(alumno.getNumeroControl());
                //PRIMERO SE REALIZA LA BUSQUEDA DEL ALUMNO EN LA BASE DE DATOS SQL SERVER
                //busqueda de la matricula
                resultado = obtenerMatricula(connectionServer);
                boolean banderaMatricula = false;//variable booleana que controla la busqueda de
                //la matricula del alumno

                while(resultado.next()) 
                {
                    
                    if(matricula.equalsIgnoreCase(resultado.getString(1)))//si esta la matrucla
                    {
                        //se imprime la matricula
                        System.out.println("Si esta la matricula "+matricula);
                        banderaMatricula = true;//cambiamos bandera a verdadero
                        break;//salimos del while
                    }else
                    {
                        banderaMatricula = false;
                    }
                    
                }
                resultado.close();//cerramos el resultado de la consulta

                //busqueda de la materia
                String materia = alumno.getMateria().toUpperCase();//obtenemos la materia a buscar
                
                boolean banderaMateria = false;//variable booleana que controla la busqueda de materia

                resultado = obtenerMateria(connectionServer);//obtenmos las materias de la base de datos
                while(resultado.next()){
                    
                    if(materia.equalsIgnoreCase(resultado.getString(1)))//si esta la matrucla
                    {
                        banderaMateria = true;//cambiamos la variable a verdadero
                        break;//salimos del while
                    }else
                    {
                        banderaMateria = false;
                    }
                    
                }
                resultado.close();//cerramos el resultado de la consulta
                
                //OBTENER EL PROMEDIO
                long nMatricula = Long.parseLong(matricula);//convertimos la materia a tipo de dato a entero largo
                if(banderaMateria==true && banderaMatricula==true)
                {//Si las banderas quedaron en verdadero
                    System.out.print("SI ESTA EN BD SQL SERVER");
                    //realizamos una nueva consulta donde nos devuelve el promedio del alumno
                    sentenciaSQL = connectionServer.createStatement();
                    resultado = sentenciaSQL.executeQuery("select (MateriaAlumno.Parcial1+MateriaAlumno.Parcial2+MateriaAlumno.Parcial3+MateriaAlumno.Parcial4+MateriaAlumno.Parcial5)/5 AS PROMEDIO from MateriaAlumno where MateriaAlumno.IdMateria=(select Materia.IdMateria from Materia where Materia.Nombre='"+materia+"') AND MateriaAlumno.Matricula="+nMatricula);
                   
                    String promedio="";//variable para almacenar el promedio
                    while(resultado.next())
                    {
                        promedio = resultado.getString(1);//obtemos el promedio del resultado
                        System.out.println(promedio);//se imprime promedio
                    }
                    resultado.close();//se cierra el resultado de la consulta
                    //enviamos el promedio al cliente
                    OutputStream aux = socket.getOutputStream();
                    DataOutputStream flujo= new DataOutputStream( aux );
                    if(promedio.equals(""))
                    {
                        flujo.writeUTF( "NO ESTAS INSCRITO EN LA MATERIA");//mensaje de warning al cliente
                    }else{
                        flujo.writeUTF( "Tu Promedio es: "+promedio);//mensaje correcto
                    }
                }else{
                    //EN CASO DE NO ENCONTRAR EN SQL SERVER
                    //AHORA SE BUSCA EN ORACLE
                    System.out.println("buscare en oracle");
                    ResultSet resultadoSQL = obtenerMatricula(connectionORACLE);//Se obtiene las matriculas
                    banderaMatricula = false;
                    while(resultadoSQL.next()){
                    
                        if(matricula.equalsIgnoreCase(resultadoSQL.getString(1)))//si esta la matrucla
                        {
                        //
                            System.out.println("Si esta la matricula "+matricula);
                            banderaMatricula = true;//cambiamos la variable a verdadero
                            break;//salimos del while
                        }else
                        {
                            banderaMatricula = false;
                        }
                    
                    }
                    resultadoSQL.close();//cerramos la variable de resultado de la consulta
                    //busqueda de la materia
                    System.out.println(alumno.getMateria());
                    banderaMateria = false;
                    resultadoSQL = obtenerMateria(connectionORACLE);//obtenemos las materias
                    while(resultadoSQL.next()){
                    
                        if(materia.equalsIgnoreCase(resultadoSQL.getString(1)))//si esta la materia
                        {
                        //
                            System.out.println("Si esta la materia "+materia);
                            banderaMateria = true;//cambiamos la variable a verdadero
                            break;//salimos del ciclo
                        }else
                        {
                            banderaMateria = false;
                        }
                    
                    }
                    resultadoSQL.close();//cerramos la consulta
                    
                    //obtenemos el promedio
                    nMatricula = Long.parseLong(matricula);
                    if(banderaMateria==true && banderaMatricula==true)
                    {//en caso de ser las variables verdaderas

                        //ejecutamos la consulta que nos devuelve el promedio
                        System.out.print("Si encontre en oracle");
                        sentenciaSQL = connectionORACLE.createStatement();
                        resultadoSQL = sentenciaSQL.executeQuery("SELECT (M.PARCIAL_1 + M.PARCIAL_2 + M.PARCIAL_3 + M.PARCIAL_4 + M.PARCIAL_5)/5 AS PROMEDIO FROM MATERIA_ALUMNO M WHERE M.ID_MATERIA = (SELECT MA.ID_MATERIA FROM MATERIA MA WHERE MA.NOMBRE = '"+materia+"') AND M.MATRICULA ="+nMatricula);
                   
//                    System.out.println(resultado.first());
                        String promedio="";
                        while(resultadoSQL.next())
                        {
                            promedio = resultadoSQL.getString(1);//obtenemos el cliente
                            System.out.println("promedio:"+promedio);
                        }
                        resultadoSQL.close();//cerramos la consulta
                        //ENVIAMOS PROMEDIO AL CLIENTE
                        OutputStream aux = socket.getOutputStream();
                        DataOutputStream flujo= new DataOutputStream( aux );
                        if(promedio.equals(""))
                        {
                            System.out.println("no tiene promedio");
                            flujo.writeUTF( "NO ESTAS INSCRITO EN LA MATERIA");
                        }
                        else{
                            flujo.writeUTF( "Tu Promedio es: "+promedio);
                        }
                    }else
                    {
                        //mandar mensaje que el alumno y/o  materia no existe
                        OutputStream aux = socket.getOutputStream();
                        DataOutputStream flujo= new DataOutputStream( aux );
                        flujo.writeUTF( "El ALUMNO Y/O LA MATERIA NO EXISTEN");
                    }
                    
                }
                entrada.close();//cerramos la conexion del cliente
                
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
    //metodo para obtenter todas las matriculas dependiendo la conexion de la base de datos
    public ResultSet obtenerMatricula(Connection c) throws SQLException
    {
        try
        {
            Statement sentencia;
            sentencia = c.createStatement();
            resultado = sentencia.executeQuery("SELECT matricula FROM Alumno");
            //sentencia.close();
        }catch(Exception e){System.out.print("Error en el select");}
        return resultado;//regresamos el resultado de la consulta
    }

    //Metodo para obtener todas las materias de una determina conexion
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
            Servidor objServer= new Servidor();//se crea objeto servidor
            objServer.setVisible(true);//se visualiza la ventana
            objServer.iniciarServidor();//mandamos a llamar el metodo para iniciar el servidor
        }catch(Exception e){}
        
    }
    
    //VARIABLES DE COMPONENTES
    JTextArea textArea;
    JScrollPane scroll;
}
