/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package desaingui;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;


/**
 *
 * @author Administrator
 */
public class Member extends javax.swing.JFrame {
           DefaultTableModel tabModel;
           ResultSet RsMember=null;
       
    /**
     * Creates new form Member
     */
    public Member() {
        initComponents();
        loadData();
        
        Hapus();
        
        
        this.btnTambah.setBackground(new Color (0,0,0,0));
         this.btnHapus.setBackground(new Color (0,0,0,0));
          this.btnUbah.setBackground(new Color (0,0,0,0));
           this.txtAlamat.setBackground(new Color (0,0,0,0));
            this.txtDiskon.setBackground(new Color (0,0,0,0));
             this.txtKodemember.setBackground(new Color (0,0,0,0));
              this.txtNama.setBackground(new Color (0,0,0,0));
               this.txtNotlp.setBackground(new Color (0,0,0,0));
                this.txtCari.setBackground(new Color (0,0,0,0));
                 this.jButton1.setBackground(new Color (0,0,0,0));
                  this.btnDasboard.setBackground(new Color (0,0,0,0));
                   this.btnProduk.setBackground(new Color (0,0,0,0));
                    this.btnKaryawan.setBackground(new Color (0,0,0,0));
                     this.btnLaporan.setBackground(new Color (0,0,0,0));
                      this.btnDasboard.setBackground(new Color (0,0,0,0));
                       this.btnMember.setBackground(new Color (0,0,0,0));
                        this.bntSupplier.setBackground(new Color (0,0,0,0));
               
       txtKodemember.setText(this.createID());          
    }
    
    private void loadData(){
        DefaultTableModel model = new DefaultTableModel ();
        model.addColumn("Kode Member");
        model.addColumn("Nama");
        model.addColumn("No Tlp");
        model.addColumn("Alamat");
        model.addColumn("Diskon");
        
        // menampilkan data pada tabel
        
        try  {
             
            String sql = "select * from tbl_member " ;
            java.sql.Connection conn=(java.sql.Connection)koneksi.configDB();
            java.sql.Statement smk=conn.createStatement();
            java.sql.ResultSet res=smk.executeQuery(sql);
            while(res.next()){
                model.addRow(new Object[] {res.getString(1), res.getString(2),res.getString(3),res.getString(4),res.getString(5) });
            }
            tblMember.setModel(model);
        }catch (Exception e){
            
        }
     }
    
     private void Hapus(){
         txtNama.setText(null);
           txtNotlp.setText(null);
             txtAlamat.setText(null);
              txtDiskon.setText(null);
     }
     
      private void cariData(String key){
         try{
            Object[] judul_kolom = {"Kode Member", "Nama","No Tlp", "Alamat","Diskon"};
            tabModel=new DefaultTableModel(null,judul_kolom);
            tblMember.setModel(tabModel);
            
            java.sql.Connection conn=(java.sql.Connection)koneksi.configDB();
            Statement pst=conn.createStatement();
            tabModel.getDataVector().removeAllElements();
            
            RsMember=pst.executeQuery("SELECT * from tbl_member WHERE kd_member LIKE '%"+key+"%' OR nama_member LIKE '%"+key+"%' OR notelp_member LIKE '%"+key+"%' OR alamat_member LIKE '%"+key+"%' OR diskon LIKE '%"+key+"%'" );  
            while(RsMember.next()){
                Object[] data={
                    RsMember.getString("kd_member"),
                    RsMember.getString("nama_member"),
                    RsMember.getString("notelp_member"),
                    RsMember.getString("alamat_member"),
                    RsMember.getString("diskon"),
                };
               tabModel.addRow(data);
            }                
        } catch (Exception ex) {
        System.err.println(ex.getMessage());
        }
    }
      
    private String createID(){
        try{
            // menyiapkan query untuk mendapatkan id terakhir
            String query = "SELECT * FROM tbl_member ORDER BY kd_member DESC LIMIT 0,1", lastID, nomor;
            Connection conn = (Connection)koneksi.configDB();
            Statement stat = conn.createStatement();
            ResultSet res = stat.executeQuery(query);
            
            // cek apakah query berhasil dieksekusi
            if(res.next()){
                // mendapatkan id terakhir
                lastID =  res.getString("kd_member");
                if(lastID != null){
                    // mendapatkan nomor id
                    nomor = lastID.substring(3);
                }else{
                    nomor = "0000";
                }
                conn.close();
            
                // jika id barang belum exist maka id akan dibuat
                return String.format("MB%03d", Integer.parseInt(nomor)+1);
            }
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        return null;
    }
    
     
     public void filterAngka(KeyEvent a){
        if(Character.isAlphabetic(a.getKeyChar())){
            a.consume();
           
        } else{
        
        }
    }
    
    public void filterChar(KeyEvent a){
        if(!Character.isLetterOrDigit(a.getKeyChar())){
            a.consume();
           
        } else{
        
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
        jScrollPane1 = new javax.swing.JScrollPane();
        tblMember = new javax.swing.JTable();
        btnHapus = new javax.swing.JButton();
        btnUbah = new javax.swing.JButton();
        btnTambah = new javax.swing.JButton();
        txtNama = new javax.swing.JTextField();
        txtNotlp = new javax.swing.JTextField();
        txtAlamat = new javax.swing.JTextField();
        txtDiskon = new javax.swing.JTextField();
        txtKodemember = new javax.swing.JTextField();
        txtCari = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        btnDasboard = new javax.swing.JButton();
        btnMember = new javax.swing.JButton();
        btnKaryawan = new javax.swing.JButton();
        bntSupplier = new javax.swing.JButton();
        btnLaporan = new javax.swing.JButton();
        btnProduk = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tblMember.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        tblMember.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Kode Member", "Nama Member", "No Tlp", "Alamat", "Diskon"
            }
        ));
        tblMember.setRowHeight(26);
        tblMember.getTableHeader().setReorderingAllowed(false);
        tblMember.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblMemberMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblMember);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 230, 510, 230));

        btnHapus.setBorder(null);
        btnHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHapusActionPerformed(evt);
            }
        });
        jPanel1.add(btnHapus, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 270, 90, 40));

        btnUbah.setBorder(null);
        btnUbah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUbahActionPerformed(evt);
            }
        });
        jPanel1.add(btnUbah, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 340, 90, 40));

        btnTambah.setBorder(null);
        btnTambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTambahActionPerformed(evt);
            }
        });
        jPanel1.add(btnTambah, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 210, 90, 40));

        txtNama.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        txtNama.setBorder(null);
        txtNama.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNamaActionPerformed(evt);
            }
        });
        jPanel1.add(txtNama, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 280, 280, 30));

        txtNotlp.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        txtNotlp.setBorder(null);
        txtNotlp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNotlpActionPerformed(evt);
            }
        });
        txtNotlp.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNotlpKeyTyped(evt);
            }
        });
        jPanel1.add(txtNotlp, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 340, 280, 40));

        txtAlamat.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        txtAlamat.setBorder(null);
        txtAlamat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtAlamatActionPerformed(evt);
            }
        });
        jPanel1.add(txtAlamat, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 410, 280, 40));

        txtDiskon.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        txtDiskon.setBorder(null);
        txtDiskon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDiskonActionPerformed(evt);
            }
        });
        jPanel1.add(txtDiskon, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 490, 60, 30));

        txtKodemember.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        txtKodemember.setBorder(null);
        txtKodemember.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtKodememberActionPerformed(evt);
            }
        });
        jPanel1.add(txtKodemember, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 210, 280, 40));

        txtCari.setBorder(null);
        txtCari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCariActionPerformed(evt);
            }
        });
        txtCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCariKeyReleased(evt);
            }
        });
        jPanel1.add(txtCari, new org.netbeans.lib.awtextra.AbsoluteConstraints(1050, 160, 230, 30));

        jButton1.setBorder(null);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 190, 160, 30));

        btnDasboard.setBorder(null);
        btnDasboard.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDasboardActionPerformed(evt);
            }
        });
        jPanel1.add(btnDasboard, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 140, 160, 30));

        btnMember.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMemberActionPerformed(evt);
            }
        });
        jPanel1.add(btnMember, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 230, 160, 30));

        btnKaryawan.setBorder(null);
        btnKaryawan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKaryawanActionPerformed(evt);
            }
        });
        jPanel1.add(btnKaryawan, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 320, 160, 30));

        bntSupplier.setBorder(null);
        bntSupplier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bntSupplierActionPerformed(evt);
            }
        });
        jPanel1.add(bntSupplier, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 370, 160, 30));

        btnLaporan.setBorder(null);
        btnLaporan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLaporanActionPerformed(evt);
            }
        });
        jPanel1.add(btnLaporan, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 410, 160, 30));

        btnProduk.setBorder(null);
        btnProduk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProdukActionPerformed(evt);
            }
        });
        jPanel1.add(btnProduk, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 280, 160, 30));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/asset/member final poll.png"))); // NOI18N
        jLabel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabel1MousePressed(evt);
            }
        });
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, -10, 1360, 770));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahActionPerformed
        String notelp = this.txtNotlp.getText();
        
        if(txtNama.getText().equals("")){
            JOptionPane.showMessageDialog(null, "Isi Nama Supplier");
        }else if(txtNotlp.getText().equals("")){
            JOptionPane.showMessageDialog(null, "Isi No Telepon");
        } else if (txtAlamat.getText().equals("")){
            JOptionPane.showMessageDialog(null, "Isi Alamat Supplier");
        } else if (notelp.length() < 11 || notelp.length() >= 13){
              JOptionPane.showMessageDialog(this, "Nomor telephone harus diantara 11 sampai 13 karakter!");
        }else{
        
         String sql = "insert into tbl_member values (?,?,?,?,?)";
         try {
            java.sql.Connection conn=(Connection) koneksi.configDB();
            java.sql.PreparedStatement stat=conn.prepareStatement(sql);
            stat.setString(1, txtKodemember.getText());
            stat.setString(2, txtNama.getText());
            stat.setString(3, txtNotlp.getText ());
            stat.setString(4, txtAlamat.getText());
            stat.setString(5, txtDiskon.getText());
            if (JOptionPane.showConfirmDialog(null, "Simpan data yang telah di input ?", "WARNING",
            JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {     
                 stat.executeUpdate();
                 JOptionPane.showMessageDialog(null, "Data berhasil diinput");
                 Hapus();
                  txtKodemember.setText(createID()); 
                 loadData();
            } else {
        } 
            txtKodemember.requestFocus();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "data failed to save"+e);
        }
     }
    }//GEN-LAST:event_btnTambahActionPerformed

    private void btnUbahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUbahActionPerformed
       int status;
       
       status = JOptionPane.showConfirmDialog(this, "Apakah anda yakin?", "confirm", JOptionPane.YES_OPTION, JOptionPane.QUESTION_MESSAGE);
       
       switch(status){
           case JOptionPane.YES_OPTION : 
        try {
            String sql ="UPDATE tbl_member SET kd_member = '"+txtKodemember.getText()+"', nama_member = '"+txtNama.getText()+"', notelp_member = '"+txtNotlp.getText()+"', alamat_member = '"+txtAlamat.getText()+"', diskon = '"+txtDiskon.getText()+"' WHERE kd_member = '"+txtKodemember.getText()+"'";
            java.sql.Connection conn=(java.sql.Connection)koneksi.configDB();
            java.sql.PreparedStatement pst=conn.prepareStatement(sql);
            pst.execute();
            JOptionPane.showMessageDialog(null, "Data berhasil di ubah");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Perubahan Data Gagal"+e.getMessage());
        }
        loadData();
        Hapus();
        txtKodemember.setText(createID()); 
          break;
       }
    }//GEN-LAST:event_btnUbahActionPerformed

    private void txtNamaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNamaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNamaActionPerformed

    private void txtNotlpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNotlpActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNotlpActionPerformed

    private void txtAlamatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtAlamatActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtAlamatActionPerformed

    private void txtDiskonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDiskonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDiskonActionPerformed

    private void txtKodememberActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtKodememberActionPerformed
        txtKodemember.setText(createID());
    }//GEN-LAST:event_txtKodememberActionPerformed

    private void btnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusActionPerformed
         int status;
       
       status = JOptionPane.showConfirmDialog(this, "Apakah anda yakin?", "confirm", JOptionPane.YES_OPTION, JOptionPane.QUESTION_MESSAGE);
       
       switch(status){
           case JOptionPane.YES_OPTION : 
        try {
           
           String sql = "delete from tbl_member where kd_member ='"+txtKodemember.getText()+"'";
           java.sql.Connection conn = (Connection)koneksi.configDB();
           java.sql.PreparedStatement pst=conn.prepareStatement(sql);
            
           pst.execute();
           JOptionPane.showMessageDialog(this, "Data berhasil di hapus");
       } catch (Exception e) {
           JOptionPane.showMessageDialog(this, e.getMessage());
       }
       loadData();
       Hapus();
       txtKodemember.setText(createID()); 
               break;
       }
    }//GEN-LAST:event_btnHapusActionPerformed

    private void tblMemberMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblMemberMouseClicked
        int baris = tblMember.rowAtPoint(evt.getPoint());
        String kd_member =tblMember.getValueAt(baris,0).toString();
        txtKodemember.setText(kd_member);
        String nama_member = tblMember.getValueAt(baris,1).toString();
        txtNama.setText(nama_member);
        String notelp_member = tblMember.getValueAt(baris, 2).toString();
        txtNotlp.setText(notelp_member);
        String alamat_member = tblMember.getValueAt(baris, 3).toString();
        txtAlamat.setText(alamat_member);
        String diskon = tblMember.getValueAt(baris, 4).toString();
        txtDiskon.setText(diskon);
    }//GEN-LAST:event_tblMemberMouseClicked

    private void txtCariKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCariKeyReleased
        String key = txtCari.getText();
        System.out.println(key);
        
        if(key!=""){
            cariData(key);
        }else{
            loadData();
        }
    }//GEN-LAST:event_txtCariKeyReleased

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        this.setVisible(false);
        new JFrameTransaksi().setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void btnDasboardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDasboardActionPerformed
        this.setVisible(false);
        new JFrameDashboard().setVisible(true);
    }//GEN-LAST:event_btnDasboardActionPerformed

    private void btnMemberActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMemberActionPerformed
        this.setVisible(false);
        new Member().setVisible(true);
    }//GEN-LAST:event_btnMemberActionPerformed

    private void btnProdukActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProdukActionPerformed
        this.setVisible(false);
        new JFrameProduk().setVisible(true);
    }//GEN-LAST:event_btnProdukActionPerformed

    private void btnKaryawanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKaryawanActionPerformed
        this.setVisible(false);
        new JFrameKaryawan().setVisible(true);
    }//GEN-LAST:event_btnKaryawanActionPerformed

    private void bntSupplierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bntSupplierActionPerformed
        this.setVisible(false);
        new JFrameSupplier().setVisible(true);
    }//GEN-LAST:event_bntSupplierActionPerformed

    private void btnLaporanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLaporanActionPerformed
        this.setVisible(false);
        new JFrameLaporan().setVisible(true);
    }//GEN-LAST:event_btnLaporanActionPerformed

    private void txtCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCariActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCariActionPerformed

    private void jLabel1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel1MousePressed

    private void txtNotlpKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNotlpKeyTyped
       this.filterAngka(evt);
        this.filterChar(evt);
    }//GEN-LAST:event_txtNotlpKeyTyped

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
            java.util.logging.Logger.getLogger(Member.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Member.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Member.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Member.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Member().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bntSupplier;
    private javax.swing.JButton btnDasboard;
    private javax.swing.JButton btnHapus;
    private javax.swing.JButton btnKaryawan;
    private javax.swing.JButton btnLaporan;
    private javax.swing.JButton btnMember;
    private javax.swing.JButton btnProduk;
    private javax.swing.JButton btnTambah;
    private javax.swing.JButton btnUbah;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblMember;
    private javax.swing.JTextField txtAlamat;
    private javax.swing.JTextField txtCari;
    private javax.swing.JTextField txtDiskon;
    private javax.swing.JTextField txtKodemember;
    private javax.swing.JTextField txtNama;
    private javax.swing.JTextField txtNotlp;
    // End of variables declaration//GEN-END:variables
}
