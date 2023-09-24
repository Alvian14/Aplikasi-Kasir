
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package desaingui;


import com.barcodelib.barcode.Linear;
import com.sun.glass.events.KeyEvent;
import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.image.BufferedImage;
import java.io.File;
import static java.lang.Character.UnicodeBlock.of;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import static java.util.OptionalInt.of;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author LENOVO
 */
public class JFrameProduk extends javax.swing.JFrame {
  DefaultTableModel tabModel;
           ResultSet Rs=null;
           private static Linear bc = new Linear();
           
    /**
     * Creates new form JFrameProduk
     */
           
//    private void Barcode(){
//        try{
//            Linear barcode = new Linear();
//            barcode.setType(Linear.CODE128B);
//            barcode.setData(this.jTextField2kdproduk.getText());
//            barcode.setI(12.0f);
//            barcode.renderBarcode("D:\\" + this.jTextField3namaproduk.getText() + ".png");
//        }catch (Exception e){
//            
//        }
//    }
           
      private void barcode() {
        
        String barcode_text = jTextField2kdproduk.getText(); // ganti dengan ID produk yang diambil dari database
        int width = barcode.getWidth();
        int height = barcode.getHeight();

        try {
           
            bc.setType(Linear.CODE128B);
            bc.setData(barcode_text);
            bc.setI(12.0f);

            BufferedImage barcode_image_buffered = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_BINARY);
            barcode_image_buffered = bc.renderBarcode();
            ImageIcon barcode_icon = new ImageIcon(barcode_image_buffered);

            JLabel barcode_label = new JLabel();
            barcode_label.setIcon(barcode_icon); // Set gambar barcode pada JLabel

            this.barcode.removeAll(); // Hapus semua komponen di panel barcodePanel
            this.barcode.add(barcode_label, BorderLayout.CENTER); // Tambahkan komponen barcode_label pada panel
            this.barcode.revalidate(); // Lakukan validasi ulang pada panel untuk menampilkan perubahan
        } catch (Exception e) {
            e.printStackTrace();
        }
    } 
      
      
    public String createkd(){
        try{
            // menyiapkan query untuk mendapatkan id terakhir
            String query = "SELECT * FROM tbl_barang ORDER BY kd_barang DESC LIMIT 0,1", lastID, kd_barang;
            Connection conn = (Connection) koneksi.configDB();
            Statement stat = conn.createStatement();
            ResultSet res = stat.executeQuery(query);
            // cek apakah query berhasil dieksekusi
            if(res.next()){
                // mendapatkan id terakhir
                lastID =  res.getString("kd_barang"); 
                if(lastID != null){
                    // mendapatkan nomor id
                    kd_barang = lastID.substring(2);
                }else{
                    kd_barang= "BG000";
                }
                // jika id barang belum exist maka id akan dibuat
                return String.format("BG%04d", Integer.parseInt(kd_barang)+1);
            }
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        return null;
    }
    
     private void cariData(String key){
         try{
            Object[] judul_kolom = {"KD Produk", "Nama Produk","Harga", "Stok"};
            tabModel=new DefaultTableModel(null,judul_kolom);
            jTable1.setModel(tabModel);
            
            java.sql.Connection conn=(java.sql.Connection)koneksi.configDB();
            Statement pst=conn.createStatement();
            tabModel.getDataVector().removeAllElements();
            
            Rs=pst.executeQuery("SELECT * from tbl_barang WHERE kd_barang LIKE '%"+key+"%' OR nama_barang LIKE '%"+key+"%' OR harga_barang LIKE '%"+key+"%' OR stok_barang LIKE '%"+key+"%'" );  
            while(Rs.next()){
                Object[] data={
                    Rs.getString("kd_barang"),
                    Rs.getString("nama_barang"),
                    Rs.getString("harga_barang"),
                    Rs.getString("stok_barang"),
                    
                };
               tabModel.addRow(data);
            }                
        } catch (Exception ex) {
        System.err.println(ex.getMessage());
        }
    }
    public JFrameProduk() {
        initComponents();
         this.jButton1.setBackground(new java.awt.Color(0,0,0,0));
            this.jButton2.setBackground(new java.awt.Color(0,0,0,0));
              this.jButton3.setBackground(new java.awt.Color(0,0,0,0));
                this.jButton4.setBackground(new java.awt.Color(0,0,0,0));
                  this.jButton5.setBackground(new java.awt.Color(0,0,0,0));
                   this.jButton6.setBackground(new java.awt.Color(0,0,0,0));
                    this.btnMember.setBackground(new java.awt.Color(0,0,0,0));
                     this.jButton7Tambahdataproduk.setBackground(new java.awt.Color(0,0,0,0));
                      this.jButton8hapusbarang.setBackground(new java.awt.Color(0,0,0,0));
                       this.jButton9ubahbarang.setBackground(new java.awt.Color(0,0,0,0));
                        this.jButton7pencarian.setBackground(new java.awt.Color(0,0,0,0));
                         this.jTextField1kolompencarian.setBackground(new java.awt.Color(0,0,0,0));
                          this.jTextField2kdproduk.setBackground(new java.awt.Color(0,0,0,0));
                           this.jTextField3namaproduk.setBackground(new java.awt.Color(0,0,0,0));
                            this.jTextField4hargaproduk.setBackground(new java.awt.Color(0,0,0,0));
                             this.jTextField5stokproduk.setBackground(new java.awt.Color(0,0,0,0));
                   updateTable();
                   createkd();
                   this.jTable1.getTableHeader().setFont(new java.awt.Font("Tahoma",0,16));
         this.jTextField2kdproduk.setText(this.createkd());
    }
    
    private void updateTable() {
        String[] columnNames = {"kd_barang", "nama_produk", "harga", "stok",};
        DefaultTableModel tablemodel = new DefaultTableModel(columnNames, 0);
        
        try {
            Connection c = (Connection) koneksi.configDB();
            Statement s = c.createStatement();
            ResultSet rs = s.executeQuery("SELECT kd_barang, nama_barang, harga_barang, stok_barang FROM tbl_barang");
            
            while(rs.next()) {
                String kd_produk = rs.getString("kd_barang"),
                            nama_produk = rs.getString("nama_barang"),
                            harga = rs.getString("harga_barang"),
                            stok = rs.getString("stok_barang");
                            
                
                // create a single array of one rows worth of data
                String[] data = {kd_produk, nama_produk, harga, stok};
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
            String sql = "SELECT * FROM tbl_barang WHERE kd_barang = '" + this.idSelected + "'";
            Connection c = (Connection) koneksi.configDB();
            Statement s = c.createStatement();
            ResultSet r = s.executeQuery(sql);
            
            // get data
            if (r.next()) {
                String nama_produk = r.getString("nama_barang"),
                            harga = r.getString("harga_barang"),
                            stok = r.getString("stok_barang");
                            
                
                // menampilkan data
                this.jTextField2kdproduk.setText(this.idSelected);
                this.jTextField3namaproduk.setText(nama_produk);
                this.jTextField4hargaproduk.setText(harga);
                this.jTextField5stokproduk.setText(stok);
                System.out.println(nama_produk);
//                this.barcode.setIcon(new ImageIcon(new File("D:\\" + nama_produk.replaceAll(" ", "") + ".png").toString()));
               
                
                
            } 
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(this, "error : " + ex.getMessage());
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

        jButton8hapusbarang = new javax.swing.JButton();
        jButton9ubahbarang = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton7pencarian = new javax.swing.JButton();
        jTextField2kdproduk = new javax.swing.JTextField();
        jTextField1kolompencarian = new javax.swing.JTextField();
        jTextField3namaproduk = new javax.swing.JTextField();
        jTextField4hargaproduk = new javax.swing.JTextField();
        jTextField5stokproduk = new javax.swing.JTextField();
        jButton7Tambahdataproduk = new javax.swing.JButton();
        btnMember = new javax.swing.JButton();
        barcode = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jButton8hapusbarang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8hapusbarangActionPerformed(evt);
            }
        });
        getContentPane().add(jButton8hapusbarang, new org.netbeans.lib.awtextra.AbsoluteConstraints(970, 70, 170, 50));

        jButton9ubahbarang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ubahbarangActionPerformed(evt);
            }
        });
        getContentPane().add(jButton9ubahbarang, new org.netbeans.lib.awtextra.AbsoluteConstraints(1150, 70, 150, 50));

        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 150, 190, 30));

        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 290, 190, 40));

        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 340, 190, 30));

        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 430, 200, 30));

        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton5, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 380, 200, 40));

        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton6, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 190, 190, 40));

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
        jTable1.setRowHeight(26);
        jTable1.getTableHeader().setReorderingAllowed(false);
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 250, 620, 330));

        jButton7pencarian.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7pencarianActionPerformed(evt);
            }
        });
        getContentPane().add(jButton7pencarian, new org.netbeans.lib.awtextra.AbsoluteConstraints(1270, 160, 60, 40));

        jTextField2kdproduk.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jTextField2kdproduk.setBorder(null);
        getContentPane().add(jTextField2kdproduk, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 200, 280, 30));

        jTextField1kolompencarian.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jTextField1kolompencarian.setBorder(null);
        jTextField1kolompencarian.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1kolompencarianActionPerformed(evt);
            }
        });
        jTextField1kolompencarian.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField1kolompencarianKeyReleased(evt);
            }
        });
        getContentPane().add(jTextField1kolompencarian, new org.netbeans.lib.awtextra.AbsoluteConstraints(920, 150, 340, 40));

        jTextField3namaproduk.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jTextField3namaproduk.setBorder(null);
        getContentPane().add(jTextField3namaproduk, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 260, 280, 30));

        jTextField4hargaproduk.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jTextField4hargaproduk.setBorder(null);
        jTextField4hargaproduk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField4hargaprodukActionPerformed(evt);
            }
        });
        getContentPane().add(jTextField4hargaproduk, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 330, 280, 30));

        jTextField5stokproduk.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jTextField5stokproduk.setBorder(null);
        jTextField5stokproduk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField5stokprodukActionPerformed(evt);
            }
        });
        getContentPane().add(jTextField5stokproduk, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 400, 280, 30));

        jButton7Tambahdataproduk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7TambahdataprodukActionPerformed(evt);
            }
        });
        getContentPane().add(jButton7Tambahdataproduk, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 70, 160, 50));

        btnMember.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMemberActionPerformed(evt);
            }
        });
        getContentPane().add(btnMember, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 243, 190, 30));

        barcode.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                barcodeMouseClicked(evt);
            }
        });
        barcode.setLayout(new java.awt.BorderLayout());
        getContentPane().add(barcode, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 470, 290, 110));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/asset/PRODUK FINAL POLL.png"))); // NOI18N
        jLabel1.setVerifyInputWhenFocusTarget(false);
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

    private void jButton8hapusbarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8hapusbarangActionPerformed
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
            String sql = "DELETE FROM tbl_barang WHERE kd_barang = '"+ idSelected +"'";
            Connection konekni = (Connection) koneksi.configDB();
            Statement stat = konekni.createStatement();
            stat.executeUpdate(sql);
            this.updateTable();
            this.jTextField2kdproduk.setText("");
            this.jTextField3namaproduk.setText("");
             this.jTextField4hargaproduk.setText("");
            this.jTextField5stokproduk.setText("");
          
            System.out.println("ID SELECTED: "+idSelected);
            JOptionPane.showMessageDialog(this, "Data berhasil di hapus");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
          createkd();
    }//GEN-LAST:event_jButton8hapusbarangActionPerformed

    private void jTextField1kolompencarianActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1kolompencarianActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1kolompencarianActionPerformed

    private void jButton7TambahdataprodukActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7TambahdataprodukActionPerformed
          String kd_produk = this.createkd(),
                    nama_produk = this.jTextField3namaproduk.getText(),
                    harga = this.jTextField4hargaproduk.getText(),
                    stok = this.jTextField5stokproduk.getText();
                    
        try{
               String sql = "INSERT INTO tbl_barang VALUES (?, ?, ?, ?)";
               Connection conn = (Connection) koneksi.configDB();
               PreparedStatement pst = conn.prepareStatement(sql);
               
               pst.setString(1, kd_produk);
               pst.setString(2, nama_produk);
               pst.setInt(3, Integer.parseInt(harga));
               pst.setInt(4, Integer.parseInt(stok));
               
               
               pst.executeUpdate();
               
               conn.close();
               clearanceaddtextbarang();
               
               JOptionPane.showMessageDialog(rootPane, "Data berhasil di input"
                       + "");
           }catch(SQLException ex){
               clearanceaddtextbarang();
               ex.printStackTrace();
               JOptionPane.showMessageDialog(this, "Error : " + ex.getMessage());
           }
        this.updateTable();
        barcode();
        createkd();
        this.jTextField2kdproduk.setText("");
        this.jTextField2kdproduk.setText(this.createkd());
        this.jTextField3namaproduk.setText("");
        this.jTextField4hargaproduk.setText("");
        this.jTextField5stokproduk.setText("");
//        
    }                                         


    
    private void clearanceaddtextbarang(){
        this.jTextField2kdproduk.setText("");
        this.jTextField3namaproduk.setText("");
        this.jTextField4hargaproduk.setText("");
        this.jTextField5stokproduk.setText("");
       
        
    }//GEN-LAST:event_jButton7TambahdataprodukActionPerformed

    private void jTextField4hargaprodukActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField4hargaprodukActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField4hargaprodukActionPerformed

    private void jButton9ubahbarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ubahbarangActionPerformed
        // TODO add your handling code here:
          String kd_barang = this.jTextField2kdproduk.getText(),
                    nama_produk = this.jTextField3namaproduk.getText(),
                  harga = this.jTextField4hargaproduk.getText(),
                    stok = this.jTextField5stokproduk.getText();
                   
                    
        try {
            String sql = String.format("UPDATE tbl_barang SET nama_barang = '%s', harga_barang = '%s', stok_barang = '%s' WHERE kd_barang = '%s'", nama_produk, harga,
                    stok, kd_barang);
            Connection konekin = (Connection) koneksi.configDB();
            Statement stat = konekin.createStatement();
            stat.executeUpdate(sql);
            this.updateTable();
            this.jTextField2kdproduk.setText("");
            this.jTextField3namaproduk.setText("");
            this.jTextField4hargaproduk.setText("");
            this.jTextField5stokproduk.setText("");
           
            JOptionPane.showMessageDialog(this, "Data berhasil diubah");
             this.jTextField2kdproduk.setText(this.createkd());
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
        
    }//GEN-LAST:event_jButton9ubahbarangActionPerformed

    private void jTextField5stokprodukActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField5stokprodukActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField5stokprodukActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
//        int baris = jTable1.getSelectedRow();
//        String kd_barang =jTable1.getValueAt(baris,0).toString();
//        jTextField2kdproduk.setText(kd_barang);
//        String nama_barang = jTable1.getValueAt(baris,1).toString();
//        jTextField3namaproduk.setText(nama_barang);
//        String harga_barang = jTable1.getValueAt(baris, 2).toString();
//        jTextField4hargaproduk.setText(harga_barang);
//        String stok_barang = jTable1.getValueAt(baris, 3).toString();
//        jTextField5stokproduk.setText(stok_barang);
//        this.lbBarcode.setIcon(new ImageIcon(new File("D:\\" + nama_barang.replaceAll(" ", "") + ".png").toString()));
        
        this.showData();
        barcode();
        
        
               
    }//GEN-LAST:event_jTable1MouseClicked

    private void jButton7pencarianActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7pencarianActionPerformed
       
    }//GEN-LAST:event_jButton7pencarianActionPerformed

    private void btnMemberActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMemberActionPerformed
       this.setVisible(false);
       new Member().setVisible(true);
    }//GEN-LAST:event_btnMemberActionPerformed

    private void jTextField1kolompencarianKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1kolompencarianKeyReleased
        String key = jTextField1kolompencarian.getText();
        System.out.println(key);
        
        if(key!=""){
            cariData(key);
        }else{
            updateTable();
        }
    }//GEN-LAST:event_jTextField1kolompencarianKeyReleased

    private void barcodeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_barcodeMouseClicked
      
        try {
            bc.renderBarcode("D:\\barcode\\"+this.jTextField3namaproduk.getText()+".png");
            JOptionPane.showMessageDialog(this, "berhasil menyimpan");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "gagal menyimpan");
        }
    }//GEN-LAST:event_barcodeMouseClicked
 
    
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
            java.util.logging.Logger.getLogger(JFrameProduk.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JFrameProduk.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JFrameProduk.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JFrameProduk.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new JFrameProduk().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel barcode;
    private javax.swing.JButton btnMember;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7Tambahdataproduk;
    private javax.swing.JButton jButton7pencarian;
    private javax.swing.JButton jButton8hapusbarang;
    private javax.swing.JButton jButton9ubahbarang;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1kolompencarian;
    private javax.swing.JTextField jTextField2kdproduk;
    private javax.swing.JTextField jTextField3namaproduk;
    private javax.swing.JTextField jTextField4hargaproduk;
    private javax.swing.JTextField jTextField5stokproduk;
    // End of variables declaration//GEN-END:variables
}
