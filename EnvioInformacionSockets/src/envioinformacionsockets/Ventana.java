package envioinformacionsockets;

import java.io.DataInputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import javax.swing.JOptionPane;

public class Ventana extends javax.swing.JFrame {
    public Ventana(String host,String puerto) {
        initComponents();
        iniciarCampos();
        this.host = host;
        this.puerto = Integer.parseInt(puerto);
        setSize(800,450);
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    public void iniciarCampos(){
        JTNombre.setText("Jorge");
        JTApellidoPaterno.setText("Mañon");
        JTApellidoMaterno.setText("Arroyo");
        JTNumeroControl.setText("12520163");
        JTMateria.setText("Base de datos");
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        JTMateria = new javax.swing.JTextField();
        JTNumeroControl = new javax.swing.JTextField();
        JTNombre = new javax.swing.JTextField();
        JTApellidoPaterno = new javax.swing.JTextField();
        JTApellidoMaterno = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);

        jLabel1.setFont(new java.awt.Font("Tekton Pro Cond", 0, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Instituto Tecnológico de Chilpancingo");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(130, 10, 540, 50);

        jLabel2.setFont(new java.awt.Font("Tekton Pro", 0, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setText("Materia:");
        getContentPane().add(jLabel2);
        jLabel2.setBounds(110, 260, 160, 30);

        jLabel3.setFont(new java.awt.Font("Tekton Pro", 0, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 255, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel3.setText("Numero de Control:");
        getContentPane().add(jLabel3);
        jLabel3.setBounds(110, 100, 160, 30);

        jLabel4.setFont(new java.awt.Font("Tekton Pro", 0, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 255, 255));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel4.setText("Nombre:");
        getContentPane().add(jLabel4);
        jLabel4.setBounds(110, 140, 160, 30);

        jLabel5.setFont(new java.awt.Font("Tekton Pro", 0, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 255, 255));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel5.setText("Apellido Paterno:");
        getContentPane().add(jLabel5);
        jLabel5.setBounds(110, 180, 160, 30);

        jLabel6.setFont(new java.awt.Font("Tekton Pro", 0, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 255, 255));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel6.setText("Apellido Materno:");
        getContentPane().add(jLabel6);
        jLabel6.setBounds(110, 220, 160, 30);
        getContentPane().add(JTMateria);
        JTMateria.setBounds(280, 260, 230, 30);
        getContentPane().add(JTNumeroControl);
        JTNumeroControl.setBounds(280, 100, 230, 30);
        getContentPane().add(JTNombre);
        JTNombre.setBounds(280, 140, 230, 30);
        getContentPane().add(JTApellidoPaterno);
        JTApellidoPaterno.setBounds(280, 180, 230, 30);
        getContentPane().add(JTApellidoMaterno);
        JTApellidoMaterno.setBounds(280, 220, 230, 30);

        jButton1.setText("Entrar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1);
        jButton1.setBounds(310, 310, 150, 30);

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/black-wallpapers-15.jpg"))); // NOI18N
        getContentPane().add(jLabel7);
        jLabel7.setBounds(-180, -60, 1040, 520);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        try {
            
            Alumno alumno = new Alumno("","","",JTNumeroControl.getText(),JTMateria.getText());
            Socket client = new Socket("192.168.43.124", 1234); 
          
            ObjectOutputStream output = new ObjectOutputStream(client.getOutputStream()); 
            output.writeObject(alumno);
            output.flush();
           
            output.flush();   
        
        
            InputStream aux = client.getInputStream();
            DataInputStream flujo = new DataInputStream(aux);
            String mensajito = flujo.readUTF();
            JOptionPane.showMessageDialog(null,mensajito);
            output.close();
        } catch (Exception ex) {System.out.println(ex.toString());}
    }//GEN-LAST:event_jButton1ActionPerformed

    private String host;
    private int puerto;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField JTApellidoMaterno;
    private javax.swing.JTextField JTApellidoPaterno;
    private javax.swing.JTextField JTMateria;
    private javax.swing.JTextField JTNombre;
    private javax.swing.JTextField JTNumeroControl;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    // End of variables declaration//GEN-END:variables
}
