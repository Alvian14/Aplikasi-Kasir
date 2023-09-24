/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package desaingui;

import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author LENOVO
 */
public class JFrameKaryawan extends javax.swing.JFrame {
 DefaultTableModel tabModel;
           ResultSet Rs=null;
    /**
     * Creates new form JFrameKaryawan
     */
    public JFrameKaryawan() {
        initComponents();
            this.jButton1.setBackground(new java.awt.Color(0, 0, 0, 0));
             this.jButton2.setBackground(new java.awt.Color(0, 0, 0, 0));
              this.jButton3.setBackground(new java.awt.Color(0, 0, 0, 0));
               this.jButton4.setBackground(new java.awt.Color(0, 0, 0, 0));
                this.jButton5.setBackground(new java.awt.Color(0, 0, 0, 0));
                 this.jButton6.setBackground(new java.awt.Color(0, 0, 0, 0));
                  this.jTextFieldkd_karyawan.setBackground(new java.awt.Color(0, 0, 0, 0));
                   this.jTextFieldnama_karyawan.setBackground(new java.awt.Color(0, 0, 0, 0));
                    this.jTextFieldno_telp.setBackground(new java.awt.Color(0, 0, 0, 0));
                     this.jTextFieldalamat.setBackground(new java.awt.Color(0, 0, 0, 0));
                      this.txtUsername.setBackground(new java.awt.Color(0, 0, 0, 0));
                       this.jButtonhapuskar.setBackground(new java.awt.Color(0, 0, 0, 0));
                        this.jButtonubahkar.setBackground(new java.awt.Color(0, 0, 0, 0));
                         
                        this.jTable1.getTableHeader().setFont(new java.awt.Font("Tahoma",0,16));
                       this.tekscari.setBackground(new java.awt.Color(0, 0, 0, 0));
                      this.butoncccari.setBackground(new java.awt.Color(0, 0, 0, 0));
                     this.butonlogout.setBackground(new java.awt.Color(0, 0, 0, 0));
                    this.btnMember.setBackground(new java.awt.Color(0, 0, 0, 0));
        
        updateTable();
        createkd();

        this.jTextFieldkd_karyawan.setText(this.createkd());
    }
    
     private void cariData(String key){
         try{
            Object[] judul_kolom = {"Kd Karyawan", "Nama Produk","No Telp", "Alamat", "Username"};
            tabModel=new DefaultTableModel(null,judul_kolom);
            jTable1.setModel(tabModel);
            
            java.sql.Connection conn=(java.sql.Connection)koneksi.configDB();
            Statement pst=conn.createStatement();
            tabModel.getDataVector().removeAllElements();
            
            Rs=pst.executeQuery("SELECT * from tbl_karyawan WHERE kd_karyawan LIKE '%"+key+"%' OR nama_karyawan LIKE '%"+key+"%' OR notelp_karyawan LIKE '%"+key+"%' OR alamat_karyawan LIKE '%"+key+"%' OR username LIKE '%"+key+"%'" );  
            while(Rs.next()){
                Object[] data={
                    Rs.getString("kd_karyawan"),
                    Rs.getString("nama_karyawan"),
                    Rs.getString("notelp_karyawan"),
                    Rs.getString("alamat_karyawan"),
                    Rs.getString("username"),                    
                };
               tabModel.addRow(data);
            }                
        } catch (Exception ex) {
        System.err.println(ex.getMessage());
        }
    }
    
     public String createkd(){
        try{
            // menyiapkan query untuk mendapatkan id terakhir
            String query = "SELECT * FROM tbl_karyawan ORDER BY kd_karyawan DESC LIMIT 0,1", lastID, kd_karyawan;
            Connection conn = (Connection) koneksi.configDB();
            Statement stat = conn.createStatement();
            ResultSet res = stat.executeQuery(query);
            // cek apakah query berhasil dieksekusi
            if(res.next()){
                // mendapatkan id terakhir
                lastID =  res.getString("kd_karyawan");
                if(lastID != null){
                    // mendapatkan nomor id
                    kd_karyawan = lastID.substring(2);
                }else{
                    kd_karyawan= "KR000";
                }
                // jika id barang belum exist maka id akan dibuat
                return String.format("KR%04d", Integer.parseInt(kd_karyawan)+1);
            }
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        return null;
    }
private void updateTable() {
        String[] columnNames = {"kd_karyawan", "Nama_Karyawan", "No_Telp", "Alamat","Username"};
        DefaultTableModel tablemodel = new DefaultTableModel(columnNames, 0);
        
        try {
            Connection c = (Connection) koneksi.configDB();
            Statement s = c.createStatement();
            ResultSet rs = s.executeQuery("SELECT * FROM tbl_karyawan");
            
            while(rs.next()) {
                String kd_karyawan = rs.getString("kd_karyawan"),
                            Nama_Karyawan = rs.getString("nama_karyawan"),
                            No_Telp = rs.getString("notelp_karyawan"),
                            Alamat = rs.getString("alamat_karyawan"),
                            Email = rs.getString("username");
                            
                
                // create a single array of one rows worth of data
                String[] data = {kd_karyawan, Nama_Karyawan, No_Telp, Alamat, Email};
                tablemodel.addRow(data);
                
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error : " + ex.getMessage());
        }
        this.jTable1.setModel(tablemodel);
    }

 private String idSelected;
        
      private void showData() {
        try{
            
           this.idSelected = this.jTable1.getValueAt(this.jTable1.getSelectedRow(), 0).toString();
            String sql = "SELECT * FROM tbl_karyawan WHERE kd_karyawan= '" + this.idSelected + "'";
                 
            Connection c = (Connection) koneksi.configDB();
            Statement s = c.createStatement();
            ResultSet r = s.executeQuery(sql);
            
            // get data
            if (r.next()) {
                String kd_karyawan = r.getString("kd_karyawan"),
                            Nama_Karyawan = r.getString("nama_karyawan"),
                            No_Telp = r.getString("notelp_karyawan"),
                            Alamat = r.getString("alamat_karyawan"),
                            Username = r.getString("username");
                            
                
                // menampilkan data
                this.jTextFieldkd_karyawan.setText(this.idSelected);
                this.jTextFieldnama_karyawan.setText(Nama_Karyawan);
                this.jTextFieldno_telp.setText(No_Telp);
                this.jTextFieldalamat.setText(Alamat);
                this.txtUsername.setText(Username);
               
          } 
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(this, "error : " + ex.getMessage());
        }
    }

      // filter hanya angka
    public void filterHuruf(KeyEvent a){
        if(Character.isAlphabetic(a.getKeyChar())){
            a.consume();
        }
    }
    
    // filter simbol
    public void filterChar(KeyEvent a){
        if(!Character.isLetterOrDigit(a.getKeyChar())){
            a.consume();
        }
    }
      
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jTextFieldkd_karyawan = new javax.swing.JTextField();
        jTextFieldnama_karyawan = new javax.swing.JTextField();
        jTextFieldno_telp = new javax.swing.JTextField();
        jTextFieldalamat = new javax.swing.JTextField();
        txtUsername = new javax.swing.JTextField();
        jButtonhapuskar = new javax.swing.JButton();
        jButtonubahkar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        butonlogout = new javax.swing.JButton();
        tekscari = new javax.swing.JTextField();
        butoncccari = new javax.swing.JButton();
        btnMember = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 150, 160, 30));

        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 290, 150, 30));

        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 330, 170, 30));

        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 420, 170, 40));

        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton5, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 370, 150, 40));

        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton6, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 200, 170, 30));

        jTextFieldkd_karyawan.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jTextFieldkd_karyawan.setBorder(null);
        getContentPane().add(jTextFieldkd_karyawan, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 200, 280, 30));

        jTextFieldnama_karyawan.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jTextFieldnama_karyawan.setBorder(null);
        jTextFieldnama_karyawan.setPreferredSize(new java.awt.Dimension(84, 84));
        getContentPane().add(jTextFieldnama_karyawan, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 270, 280, 30));

        jTextFieldno_telp.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jTextFieldno_telp.setBorder(null);
        jTextFieldno_telp.setPreferredSize(new java.awt.Dimension(84, 84));
        jTextFieldno_telp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldno_telpActionPerformed(evt);
            }
        });
        jTextFieldno_telp.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextFieldno_telpKeyTyped(evt);
            }
        });
        getContentPane().add(jTextFieldno_telp, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 330, 280, 30));

        jTextFieldalamat.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jTextFieldalamat.setBorder(null);
        jTextFieldalamat.setPreferredSize(new java.awt.Dimension(84, 84));
        getContentPane().add(jTextFieldalamat, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 400, 280, 30));

        txtUsername.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        txtUsername.setBorder(null);
        txtUsername.setPreferredSize(new java.awt.Dimension(84, 84));
        txtUsername.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtUsernameActionPerformed(evt);
            }
        });
        getContentPane().add(txtUsername, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 470, 280, 30));

        jButtonhapuskar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonhapuskarActionPerformed(evt);
            }
        });
        getContentPane().add(jButtonhapuskar, new org.netbeans.lib.awtextra.AbsoluteConstraints(920, 70, 160, 50));

        jButtonubahkar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonubahkarActionPerformed(evt);
            }
        });
        getContentPane().add(jButtonubahkar, new org.netbeans.lib.awtextra.AbsoluteConstraints(1100, 70, 170, 50));

        jTable1.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jTable1.setRowHeight(25);
        jTable1.getTableHeader().setReorderingAllowed(false);
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 200, 570, 470));

        butonlogout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butonlogoutActionPerformed(evt);
            }
        });
        getContentPane().add(butonlogout, new org.netbeans.lib.awtextra.AbsoluteConstraints(1240, 20, 110, 30));

        tekscari.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        tekscari.setBorder(null);
        tekscari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tekscariActionPerformed(evt);
            }
        });
        tekscari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tekscariKeyReleased(evt);
            }
        });
        getContentPane().add(tekscari, new org.netbeans.lib.awtextra.AbsoluteConstraints(910, 140, 340, 40));

        butoncccari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butoncccariActionPerformed(evt);
            }
        });
        getContentPane().add(butoncccari, new org.netbeans.lib.awtextra.AbsoluteConstraints(1260, 140, 50, 40));

        btnMember.setBorder(null);
        btnMember.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMemberActionPerformed(evt);
            }
        });
        getContentPane().add(btnMember, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 240, 140, 30));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/asset/KARYAWAN final pasti.png"))); // NOI18N
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        new JFrameDashboard().setVisible(true);
        dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        new JFrameProduk().setVisible(true);
        dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        new JFrameKaryawan().setVisible(true);
        dispose();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        new JFrameLaporan().setVisible(true);
        dispose();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        new JFrameSupplier().setVisible(true);
        dispose();
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
        new JFrameTransaksi().setVisible(true);
        dispose();
    }//GEN-LAST:event_jButton6ActionPerformed

    private void txtUsernameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtUsernameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtUsernameActionPerformed

    private void jButtonhapuskarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonhapuskarActionPerformed
        // mendapatkan nama data yang akan dihapus
        String nama = this.jTable1.getValueAt(this.jTable1.getSelectedRow(), 1).toString();
        
        // memunculkan pop up konfirmasi penghapusan data
        int status = JOptionPane.showConfirmDialog(this, "Apakah anda yakin ingin menghapus " + nama + "?", "confirm", JOptionPane.YES_OPTION, JOptionPane.QUESTION_MESSAGE);
        
        // jika pada pop up yang ditekan bukan button 'yes' maka data tidak akan dihapus
        if(status != JOptionPane.YES_OPTION){
            return;
        }
        
        
        // TODO add your handling code here:
          this.idSelected = this.jTable1.getValueAt(this.jTable1.getSelectedRow(),0).toString();
          try {
            String sql = "DELETE FROM akun WHERE username = '"+ txtUsername.getText() +"'";
              System.out.println(sql);
            Connection konekni = (Connection) koneksi.configDB();
            Statement stat = konekni.createStatement();
            stat.executeUpdate(sql);
            this.updateTable();
            this.jTextFieldkd_karyawan.setText("");
            this.jTextFieldnama_karyawan.setText("");
             this.jTextFieldno_telp.setText("");
            this.jTextFieldalamat.setText("");
            this.txtUsername.setText("");
          
            System.out.println("ID SELECTED: "+idSelected);
            JOptionPane.showMessageDialog(this, "Data berhasil di hapus ");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }//GEN-LAST:event_jButtonhapuskarActionPerformed

    private void jButtonubahkarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonubahkarActionPerformed
        // TODO add your handling code here:
         String kd_karyawan = this.jTextFieldkd_karyawan.getText(),
                    Nama_Karyawan = this.jTextFieldnama_karyawan.getText(),
                  No_Telp = this.jTextFieldno_telp.getText(),
                    Alamat = this.jTextFieldalamat.getText(),
                 username = this.txtUsername.getText();
                   
          // melakukan pengecek apakah nomor hp panjangnya diantara 8 sampai 14 karakter
          if(No_Telp.length() < 11 || No_Telp.length() >= 14){
              JOptionPane.showMessageDialog(this, "Nomor telephone harus diantara 11 sampai 13 angka!");
              return;
          }
                    
        try {
            String sql = String.format("UPDATE tbl_karyawan SET nama_karyawan = '%s', notelp_karyawan = '%s', alamat_karyawan = '%s', username = '%s' WHERE kd_karyawan = '%s'", Nama_Karyawan, No_Telp,
                    Alamat, username, kd_karyawan);
            Connection konekin = (Connection) koneksi.configDB();
            Statement stat = konekin.createStatement();
            stat.executeUpdate(sql);
            this.updateTable();
            this.jTextFieldkd_karyawan.setText("");
            this.jTextFieldnama_karyawan.setText("");
            this.jTextFieldno_telp.setText("");
            this.jTextFieldalamat.setText("");
            this.txtUsername.setText("");
           
            JOptionPane.showMessageDialog(this, "Data berhasil diubah");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }//GEN-LAST:event_jButtonubahkarActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        // TODO add your handling code here:
        this.showData();
    }//GEN-LAST:event_jTable1MouseClicked

    private void butoncccariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butoncccariActionPerformed
        // TODO add your handling code here:
         String[] columnNames = {"kd_karyawan", "Nama_Karyawan", "No_Telp", "Alamat","Email"};
        DefaultTableModel tablemodel = new DefaultTableModel(columnNames, 0);
        try {
            String findBarang = this.tekscari.getText();
            String sql = ("SELECT kd_karyawan, nama_karyawan, notelp_karyawan, alamat_karyawan, email_karyawan FROM tbl_karyawan WHERE nama_karyawan LIKE '%" + findBarang + "%'");
            Connection konekin = (Connection) koneksi.configDB();
            Statement stat = konekin.createStatement();
            ResultSet rs = stat.executeQuery(sql);
              while(rs.next()) {
                String kd_karyawan = rs.getString("kd_karyawan"),
                            Nama_Karyawan = rs.getString("nama_karyawan"),
                            No_Telp = rs.getString("notelp_karyawan"),
                            Alamat = rs.getString("alamat_karyawan"),
                            Email = rs.getString("email_karyawan");
                            
                
                // create a single array of one rows worth of data
                String[] data = {kd_karyawan, Nama_Karyawan, No_Telp, Alamat, Email};
                tablemodel.addRow(data);
                
            } }
             catch(SQLException ex){
            JOptionPane.showMessageDialog(this, "error : " + ex.getMessage());
        }
        this.jTable1.setModel(tablemodel);
    }//GEN-LAST:event_butoncccariActionPerformed

    private void butonlogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butonlogoutActionPerformed
        // TODO add your handling code here:
        int res = JOptionPane.showConfirmDialog(this, "Apakah anda yakin ingin logout?", "confirm", JOptionPane.YES_OPTION, JOptionPane.QUESTION_MESSAGE);
        
        if(res == JOptionPane.YES_OPTION){
            JFramelogin l = new JFramelogin();
            l.setVisible(true);
        }
    }//GEN-LAST:event_butonlogoutActionPerformed

    private void jTextFieldno_telpKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldno_telpKeyTyped
        this.filterChar(evt);
        this.filterHuruf(evt);
    }//GEN-LAST:event_jTextFieldno_telpKeyTyped

    private void jTextFieldno_telpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldno_telpActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldno_telpActionPerformed

    private void btnMemberActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMemberActionPerformed
        this.setVisible(false);
        new Member().setVisible(true);
    }//GEN-LAST:event_btnMemberActionPerformed

    private void tekscariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tekscariActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tekscariActionPerformed

    private void tekscariKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tekscariKeyReleased
         String key = tekscari.getText();
        System.out.println(key);
        
        if(key!=""){
            cariData(key);
        }else{
            updateTable();
        }
    }//GEN-LAST:event_tekscariKeyReleased
private void clearanceaddtextkaryawan(){
        this.jTextFieldkd_karyawan.setText("");
        this.jTextFieldnama_karyawan.setText("");
        this.jTextFieldno_telp.setText("");
        this.jTextFieldalamat.setText("");
        this.txtUsername.setText("");
}
    
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
            java.util.logging.Logger.getLogger(JFrameKaryawan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JFrameKaryawan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JFrameKaryawan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JFrameKaryawan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new JFrameKaryawan().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnMember;
    private javax.swing.JButton butoncccari;
    private javax.swing.JButton butonlogout;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButtonhapuskar;
    private javax.swing.JButton jButtonubahkar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextFieldalamat;
    private javax.swing.JTextField jTextFieldkd_karyawan;
    private javax.swing.JTextField jTextFieldnama_karyawan;
    private javax.swing.JTextField jTextFieldno_telp;
    private javax.swing.JTextField tekscari;
    private javax.swing.JTextField txtUsername;
    // End of variables declaration//GEN-END:variables
}
