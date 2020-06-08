package Form;
import Koneksi_DB.koneksinya;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.*;
/**
 *
 * @author Nurul fatimah
 */
public class Form_Barang extends javax.swing.JFrame {

    ResultSet Rs;
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    
    /**
     * Creates new form Form_barang
     */
    public Form_Barang() {
        initComponents();
        Datatabel();
    }
    
    public void Itemterpilih(){
        FormKategori fk = new FormKategori();
        fk.fo = this;
        txtidkategori.setText(idbarang);
        txtkategoribarang.setText(katbarang);
    }
    
    public void Tabelklik(){
        int pilih = tabelbarang.getSelectedRow();
        String id = (String) tabelbarang.getValueAt(pilih, 0);
        txtidbarang.setText(id);
        String nama = (String) tabelbarang.getValueAt(pilih, 1);
        txtnama.setText(nama);
        String jumlah = (String) tabelbarang.getValueAt(pilih, 2);
        txtjumlah.setText(jumlah);
        String idkate = (String) tabelbarang.getValueAt(pilih, 3);
        txtidkategori.setText(idkate);
        String kategoribarang = (String) tabelbarang.getValueAt(pilih, 4);
        txtkategoribarang.setText(kategoribarang);
        String harga = (String) tabelbarang.getValueAt(pilih, 6);
        txtharga.setText(harga);
    }
    
    public static Date AmbilTanggal(JTable table , int kolom){
        JTable tabelbarang = table;
        String tgl = String.valueOf(tabelbarang.getValueAt(tabelbarang.getSelectedRow(), kolom));
        Date tanggal = null;
        try {
            tanggal = new SimpleDateFormat("yyyy-MM-dd").parse(tgl);
        } catch (ParseException ex) {
            Logger.getLogger(Form_Barang.class.getName()).log(Level.SEVERE, null, ex);
        }
        return tanggal;
    }
    
    private void Autonomor(){
        String sql = "select max(id_barang) from barang";
        try{
            Statement state  = koneksinya.GetConnection().createStatement();
            Rs = state.executeQuery(sql);
            while (Rs.next()){
                int a = Rs.getInt(1);
                txtidbarang.setText("00"+ Integer.toString(a+1));
            }
        }catch (Exception e){
            System.out.println(""+ e.getMessage());
        }
    }
    
    public String idbarang, katbarang;
    public String getIdbarang(){
        return idbarang;
    }
    
    public String getKatbarang(){
        return katbarang;
    }
    
    public void Reset(){
        txtidbarang.setText("");
        txtnama.setText("");
        txtjumlah.setText("");
        txtidkategori.setText("");
        txtkategoribarang.setText("");
        datekadaluarsa.setDate(null);
        txtharga.setText("");
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
               tabelbarang.setModel(tabel);
           }
           state.close();
           Rs.close();
       }
       catch(Exception e){
           System.out.println(e);
       }
    }
    
    public void Simpan(){
        String id = txtidbarang.getText();
        String nama = txtnama.getText();
        int jumlah = Integer.parseInt(txtjumlah.getText());
        String idkategori = txtidkategori.getText();
        String kategori = txtkategoribarang.getText();
        String tgl = String.valueOf(format.format(datekadaluarsa.getDate()));
        String harga = txtharga.getText();
        try{
            Statement state  = koneksinya.GetConnection().createStatement();
            state.executeUpdate("insert into barang values ('"+id+"','"+nama+"','"+jumlah+"','"+idkategori+"','"+kategori+"','"+tgl+"', '"+harga+"')");
            JOptionPane.showMessageDialog(this, "Data Berhasil Disimpan !");
            Reset();
            state.close();
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(this, "Data Gagal Disimpan !");
        }
    }
    
    public void Update(){
        String id = txtidbarang.getText();
        String nama = txtnama.getText();
        int jumlah = Integer.parseInt(txtjumlah.getText());
        String idkategori = txtidkategori.getText();
        String kategori = txtkategoribarang.getText();
        String tgl = (format.format(datekadaluarsa.getDate()));
        String harga = txtharga.getText();
        try{
            Statement state  = koneksinya.GetConnection().createStatement();
            state.executeUpdate("update barang set nama_barang='"+nama
                    +"', jumlah='"+jumlah
                    +"', id_kategori='"+idkategori
                    +"', kategori='"+kategori
                    +"', tgl_kadaluarsa='"+tgl
                    +"', harga='"+harga
                    +"' where id_barang='"+id+"';");
            JOptionPane.showMessageDialog(this, "Data Berhasil Diupdate !");
            Reset();
            state.close();
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(this, "Data Gagal Diupdate !");
        }
    }
    
    public void Delete(){
        String id = txtidbarang.getText();
        try {
            Statement state  = koneksinya.GetConnection().createStatement();
            state.executeUpdate("delete from barang where id_barang='"+id+"';");
            JOptionPane.showMessageDialog(this, "Data Berhasil Dihapus !");
            Reset();
            state.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Data Gagal Dihapus !");
        }
    }
    
    public void Caribarang(){
       DefaultTableModel tabel = new DefaultTableModel();
       tabel.addColumn("ID BARANG");
       tabel.addColumn("NAMA BARANG");
       tabel.addColumn("JUMLAH");
       tabel.addColumn("ID KATEGORI");
       tabel.addColumn("KATEGORI");
       tabel.addColumn("TGL KADALUARSA");
       tabel.addColumn("HARGA");
       String pencarian =cbcari.getSelectedItem().toString();
       String kunci = txtkunci.getText();
       try{
           Statement state  = koneksinya.GetConnection().createStatement();
           Rs = state.executeQuery("Select * from barang where "+pencarian+" like '%"+kunci+"%'");
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
               tabelbarang.setModel(tabel);
           }
           txtkunci.setText(null);
           state.close();
           Rs.close();
       }
       catch(Exception e){
           JOptionPane.showMessageDialog(this, "Data Tidak Ditemukan !");
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

        jPanel1 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabelbarang = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtidbarang = new javax.swing.JTextField();
        txtnama = new javax.swing.JTextField();
        txtjumlah = new javax.swing.JTextField();
        txtharga = new javax.swing.JTextField();
        btngetid = new javax.swing.JButton();
        txtidkategori = new javax.swing.JTextField();
        btncarikate = new javax.swing.JButton();
        txtkategoribarang = new javax.swing.JTextField();
        datekadaluarsa = new com.toedter.calendar.JDateChooser();
        jPanel3 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        cbcari = new javax.swing.JComboBox();
        jLabel8 = new javax.swing.JLabel();
        txtkunci = new javax.swing.JTextField();
        btncari = new javax.swing.JButton();
        btnsimpan = new javax.swing.JButton();
        btnupdate = new javax.swing.JButton();
        btndelete = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(0, 153, 102));
        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 204, 0));
        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Pills-1.png"))); // NOI18N
        jLabel9.setText("DATA BARANG");

        jButton1.setText("X");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(310, 310, 310)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton1))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(16, Short.MAX_VALUE)
                .addComponent(jLabel9)
                .addContainerGap())
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jButton1)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        tabelbarang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "ID BARANG", "NAMA OBAT", "JUMLAH", "ID KATEGORI", "KATEGORI", "TGL KADALUARSA", "HARGA"
            }
        ));
        tabelbarang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelbarangMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tabelbarang);

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Data Barang"));

        jLabel1.setText("ID BARANG");

        jLabel2.setText("NAMA BARANG");

        jLabel3.setText("JUMLAH");

        jLabel4.setText("KATEGORI");

        jLabel5.setText("TGL KADALUARSA");

        jLabel6.setText("HARGA");

        txtidbarang.setEnabled(false);

        btngetid.setText("GET ID");
        btngetid.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btngetidActionPerformed(evt);
            }
        });

        txtidkategori.setEnabled(false);

        btncarikate.setText("CARI");
        btncarikate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncarikateActionPerformed(evt);
            }
        });

        txtkategoribarang.setEnabled(false);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4))
                        .addGap(46, 46, 46)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtidbarang)
                            .addComponent(txtnama)
                            .addComponent(txtjumlah)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(txtidkategori, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtkategoribarang, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtharga)
                            .addComponent(datekadaluarsa, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btngetid, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btncarikate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtidbarang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btngetid))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtnama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtjumlah, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtidkategori, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btncarikate)
                    .addComponent(txtkategoribarang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(datekadaluarsa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(14, 14, 14)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtharga, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Pencarian Barang"));

        jLabel7.setText("BERDASARKAN");

        cbcari.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "id_barang", "nama_barang" }));

        jLabel8.setText("KATA KUNCI");

        btncari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Search 24 h p8.png"))); // NOI18N
        btncari.setText("CARI");
        btncari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncariActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addComponent(jLabel8))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cbcari, 0, 167, Short.MAX_VALUE)
                            .addComponent(txtkunci)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btncari)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(cbcari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txtkunci, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(btncari)
                .addContainerGap(90, Short.MAX_VALUE))
        );

        btnsimpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Save Blue 24 n p8.png"))); // NOI18N
        btnsimpan.setText("SIMPAN");
        btnsimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnsimpanActionPerformed(evt);
            }
        });

        btnupdate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Edit.png"))); // NOI18N
        btnupdate.setText("UPDATE");
        btnupdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnupdateActionPerformed(evt);
            }
        });

        btndelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Recycle Bin.png"))); // NOI18N
        btndelete.setText("DELETE");
        btndelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btndeleteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane1)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(61, 61, 61)
                .addComponent(btnsimpan)
                .addGap(18, 18, 18)
                .addComponent(btnupdate)
                .addGap(18, 18, 18)
                .addComponent(btndelete)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnsimpan)
                    .addComponent(btnupdate)
                    .addComponent(btndelete))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 20, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btngetidActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btngetidActionPerformed
        // TODO add your handling code here:
        Autonomor();
    }//GEN-LAST:event_btngetidActionPerformed

    private void btnsimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnsimpanActionPerformed
        // TODO add your handling code here:
        Simpan();
        Reset();
        Datatabel();
    }//GEN-LAST:event_btnsimpanActionPerformed

    private void btnupdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnupdateActionPerformed
        // TODO add your handling code here:
        Update();
        Reset();
        Datatabel();
    }//GEN-LAST:event_btnupdateActionPerformed

    private void btndeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btndeleteActionPerformed
        // TODO add your handling code here:
        Delete();
        Datatabel();
        Reset();
    }//GEN-LAST:event_btndeleteActionPerformed

    private void btncarikateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncarikateActionPerformed
        // TODO add your handling code here:
        FormKategori fk = new FormKategori();
        fk.fo = this;
        fk.setVisible(true);
       
    }//GEN-LAST:event_btncarikateActionPerformed

    private void tabelbarangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelbarangMouseClicked
        // TODO add your handling code here:
        if(evt.getClickCount()==1){
            Tabelklik();
            datekadaluarsa.setDate(AmbilTanggal(tabelbarang, 5));
        }
    }//GEN-LAST:event_tabelbarangMouseClicked

    private void btncariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncariActionPerformed
        // TODO add your handling code here:
        Caribarang();
    }//GEN-LAST:event_btncariActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
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
            java.util.logging.Logger.getLogger(Form_Barang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Form_Barang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Form_Barang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Form_Barang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Form_Barang().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btncari;
    private javax.swing.JButton btncarikate;
    private javax.swing.JButton btndelete;
    private javax.swing.JButton btngetid;
    private javax.swing.JButton btnsimpan;
    private javax.swing.JButton btnupdate;
    private javax.swing.JComboBox cbcari;
    private com.toedter.calendar.JDateChooser datekadaluarsa;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tabelbarang;
    private javax.swing.JTextField txtharga;
    private javax.swing.JTextField txtidbarang;
    private javax.swing.JTextField txtidkategori;
    private javax.swing.JTextField txtjumlah;
    private javax.swing.JTextField txtkategoribarang;
    private javax.swing.JTextField txtkunci;
    private javax.swing.JTextField txtnama;
    // End of variables declaration//GEN-END:variables
}
