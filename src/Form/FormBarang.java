package Form;
import Koneksi_DB.koneksinya;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.util.HashMap;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import net.sf.jasperreports.swing.JRViewer;
/**
 *
 * @author nurul fatimah
 */
public class FormBarang extends javax.swing.JFrame {

    ResultSet Rs;
    Form_Trans_Detail ftd = null;
    /**
     * Creates new form Form_barang
     */
    public FormBarang() {
        initComponents();
        Datatabel();
    }
    
    public void Datatabel(){
       DefaultTableModel tabel = new DefaultTableModel();
       tabel.addColumn("ID BARANG");
       tabel.addColumn("NAMA BARANG");
       tabel.addColumn("JUMLAH");
       tabel.addColumn("ID KATEGORI");
       tabel.addColumn("KATEGORI");
       tabel.addColumn("TGL KADALUARSA");
       tabel.addColumn("HARGA");
       try{
           Statement state  = koneksinya.GetConnection().createStatement();
           Rs = state.executeQuery("Select * from barang");
           while(Rs.next()){
               tabel.addRow(new Object[]{
                   Rs.getString(1),
                   Rs.getString(2),
                   Rs.getString(3),
                   Rs.getString(4),
                   Rs.getString(5),
                   Rs.getString(6),
                   Rs.getString(7)
               });
               Tabelbarang.setModel(tabel);
           }
           state.close();
           Rs.close();
       }
       catch(Exception e){
           System.out.println(e);
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

        jScrollPane1 = new javax.swing.JScrollPane();
        Tabelbarang = new javax.swing.JTable();
        btncetak = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        Tabelbarang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "ID BARANG", "NAMA BARANG", "JUMLAH", "ID KATEGORI", "KATEGORI", "TGL KADALUARSA", "HARGA"
            }
        ));
        Tabelbarang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TabelbarangMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(Tabelbarang);

        btncetak.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Print-2.png"))); // NOI18N
        btncetak.setText("CETAK");
        btncetak.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncetakActionPerformed(evt);
            }
        });

        jButton1.setText("Close");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 707, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btncetak)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btncetak)
                    .addComponent(jButton1))
                .addGap(0, 11, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void TabelbarangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabelbarangMouseClicked
        // TODO add your handling code here:
        int tabelbarang = Tabelbarang.getSelectedRow();
        ftd.idbarang = Tabelbarang.getValueAt(tabelbarang, 0).toString();
        ftd.namabarang = Tabelbarang.getValueAt(tabelbarang, 1).toString();
        ftd.hargabarang = Tabelbarang.getValueAt(tabelbarang, 6).toString();
        ftd.Itemterpilih();
        this.dispose();
    }//GEN-LAST:event_TabelbarangMouseClicked

    private void btncetakActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncetakActionPerformed
        // TODO add your handling code here:
        String currentDir = System.getProperty("user.dir");
        java.io.File namaReport = new java.io.File(currentDir+"/src/Report/LaporanBarang.jasper");
        try {
            net.sf.jasperreports.engine.JasperReport jasper;
            jasper =(net.sf.jasperreports.engine.JasperReport)
            net.sf.jasperreports.engine.util.JRLoader.loadObject(namaReport.getPath());
            net.sf.jasperreports.engine.JasperPrint jp;
            jp = net.sf.jasperreports.engine.JasperFillManager.fillReport(jasper, null, koneksinya.GetConnection());
            net.sf.jasperreports.view.JasperViewer.viewReport(jp, false);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, e.getMessage());
        }
        
    }//GEN-LAST:event_btncetakActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FormBarang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FormBarang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FormBarang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FormBarang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FormBarang().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable Tabelbarang;
    private javax.swing.JButton btncetak;
    private javax.swing.JButton jButton1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}