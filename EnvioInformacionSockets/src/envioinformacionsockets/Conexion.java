package envioinformacionsockets;
public class Conexion extends javax.swing.JFrame {
    public Conexion() {
        initComponents();
        JTHost.setText("192.168.10.10");
        JTPuerto.setText("1234");
        setSize(500,350);
        setVisible(true);
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        JTHost = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        JTPuerto = new javax.swing.JTextField();
        JBConectar = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);

        jLabel1.setFont(new java.awt.Font("Tekton Pro Cond", 0, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Instituto Tecnologico de Chilpancingo");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(0, 0, 500, 46);

        jLabel2.setFont(new java.awt.Font("Tekton Pro Cond", 0, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Conexión Remota");
        getContentPane().add(jLabel2);
        jLabel2.setBounds(0, 40, 500, 30);

        jLabel3.setFont(new java.awt.Font("Tekton Pro Cond", 0, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 255, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel3.setText("IP Host:");
        getContentPane().add(jLabel3);
        jLabel3.setBounds(80, 120, 60, 30);

        JTHost.setFont(new java.awt.Font("Tekton Pro Cond", 0, 18)); // NOI18N
        getContentPane().add(JTHost);
        JTHost.setBounds(160, 120, 200, 30);

        jLabel4.setFont(new java.awt.Font("Tekton Pro Cond", 0, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 255, 255));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel4.setText("Puerto:");
        getContentPane().add(jLabel4);
        jLabel4.setBounds(80, 170, 60, 30);

        JTPuerto.setFont(new java.awt.Font("Tekton Pro Cond", 0, 18)); // NOI18N
        getContentPane().add(JTPuerto);
        JTPuerto.setBounds(160, 170, 200, 30);

        JBConectar.setText("Conectar");
        JBConectar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JBConectarActionPerformed(evt);
            }
        });
        getContentPane().add(JBConectar);
        JBConectar.setBounds(180, 220, 140, 40);

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/fondo vector 10.png"))); // NOI18N
        getContentPane().add(jLabel5);
        jLabel5.setBounds(-60, 10, 780, 490);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void JBConectarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JBConectarActionPerformed
        setVisible(false);
        new Ventana(JTHost.getText(),JTPuerto.getText());
    }//GEN-LAST:event_JBConectarActionPerformed

    public static void main(String args[]) {
        new Conexion();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton JBConectar;
    private javax.swing.JTextField JTHost;
    private javax.swing.JTextField JTPuerto;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    // End of variables declaration//GEN-END:variables
}
