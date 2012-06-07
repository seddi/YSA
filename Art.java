/**
 *
 * @author seddi
 */

package yapay_sinir_aglari;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import java.io.*;

class x {
    public static int sinifi = 0;
    public static ArrayList<Integer> yasak = new ArrayList<Integer>(10);
}
public class Art extends javax.swing.JFrame {

    /**
     * Creates new form Art
     */
    public Art() {
        initComponents();
    }
    
    double[][] Gateileri;
    double[][] Gategeri;
    double[] y;
    double benzerlik;
    String[][] girdiler;
    int M, N;
    x[] girdi;
    int index = 0;
    File file;
    
    public String[][] ilklendir(){
        int i, j;
        String[] lines;

        try{
            fileread.split(file.toString());
         }catch (Exception e){
                System.out.print("Dosya bulunamadı.");
            }            

        M = fileread.linecount;
        N = fileread.all[M-1].length; // ornekteki girdi sayımız
        
        girdi = new x[M];        
        Gateileri = new double[N][M];
        Gategeri = new double[M][N];
        y = new double[M];
        
        for( i = 0; i < N; i++ ){
            for( j = 0; j < M; j++){
                y[j] = 0;
                Gategeri[j][i] = 1;
                Gateileri[i][j] = (double)1/(1+N);
            }
        }
        
        return fileread.all;
    }
    public void prossescikti(){
        int i, j;

        for( i = 0; i < N; i++){
            for( j = 0; j < M; j++){
                y[j] += Gateileri[i][j]*Double.parseDouble(girdiler[index][i]);
            }
        }
    }
    public void maxfind(){
        int i;
        double max = 0;
        
        for( i = 0; i < M; i++){
            if( y[i] > max && (girdi[index].yasak.indexOf(i) == -1)){
                max = y[i];
                girdi[index].sinifi = i;
            }
        }
    }
    public int uygunluk(){
        int i, j;
        double s1 = 0, s2 = 0;
        
        for( i = 0; i < N; i++){
            s1 += Double.parseDouble(girdiler[index][i]);
            s2 += Gategeri[girdi[index].sinifi][i]*Double.parseDouble(girdiler[index][i]);
        }
        if ((double)(s2/s1) > benzerlik )
            return 0;
        else
            return -1;
    }
    public void agirlikdegis(){
        int i, toplam = 0;
        
        for( i = 0; i < N; i++){
            Gategeri[girdi[index].sinifi][i] = Gategeri[girdi[index].sinifi][i]*Double.parseDouble(girdiler[index][i]);
            toplam += Gategeri[girdi[index].sinifi][i];
        }
        
        for( i = 0; i < N; i++){
            Gateileri[i][girdi[index].sinifi] = Gategeri[girdi[index].sinifi][i] /(0.5 +(double)toplam);
        }
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jDialog1 = new javax.swing.JDialog();
        jFileChooser1 = new javax.swing.JFileChooser();
        jButton1 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea2 = new javax.swing.JTextArea();
        jButton3 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();

        jFileChooser1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jFileChooser1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jDialog1Layout = new javax.swing.GroupLayout(jDialog1.getContentPane());
        jDialog1.getContentPane().setLayout(jDialog1Layout);
        jDialog1Layout.setHorizontalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDialog1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jFileChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, 367, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(21, Short.MAX_VALUE))
        );
        jDialog1Layout.setVerticalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDialog1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jFileChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, 356, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(72, Short.MAX_VALUE))
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jButton1.setText("Sınıflandır");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jTextArea2.setColumns(10);
        jTextArea2.setRows(5);
        jScrollPane2.setViewportView(jTextArea2);

        jButton3.setText("Dosya Seç");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Ubuntu", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(1, 95, 115));
        jLabel1.setText("Sınıflandırılacak Veriyi Seçiniz:");

        jLabel2.setFont(new java.awt.Font("Ubuntu", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(1, 95, 115));
        jLabel2.setText("Benzerlik oranı:");

        jLabel3.setFont(new java.awt.Font("Ubuntu", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(1, 95, 115));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(7, 7, 7)
                                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel1))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 35, Short.MAX_VALUE))))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(33, 33, 33)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton3)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 37, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addGap(100, 100, 100))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(39, 39, 39))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        int i,j;
        index = 0;
        jTextArea2.setText("");
        benzerlik = Double.parseDouble(jTextField1.getText());
        girdiler = ilklendir();
        
        while( index < M ){
            prossescikti();
            maxfind();
            while(true){
                if (uygunluk() == 0){
                    agirlikdegis();
                    jTextArea2.append(Integer.toString(index+1)+". girdinin sınıfı :  "+Integer.toString(girdi[index].sinifi)+"\n");
                    break;
                }
                else{
                    girdi[index].yasak.add(girdi[index].sinifi);
                }
            }
            index++;
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        jDialog1.setBounds(0,0,400,400);
        jDialog1.setVisible(true);
        
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jFileChooser1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jFileChooser1ActionPerformed
        // TODO add your handling code here:
        file = jFileChooser1.getSelectedFile();
        jLabel3.setText(file.getName().toString());
        jDialog1.setVisible(false);
        
    }//GEN-LAST:event_jFileChooser1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /*
         * Set the Nimbus look and feel
         */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /*
         * If Nimbus (introduced in Java SE 6) is not available, stay with the
         * default look and feel. For details see
         * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Art.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Art.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Art.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Art.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /*
         * Create and display the form
         */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new Art().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton3;
    public javax.swing.JDialog jDialog1;
    private javax.swing.JFileChooser jFileChooser1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea jTextArea2;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
