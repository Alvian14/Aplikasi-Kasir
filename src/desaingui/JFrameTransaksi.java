/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package desaingui;

import com.mysql.cj.protocol.Message;
import java.awt.event.WindowEvent;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author LENOVO
 */
public class JFrameTransaksi extends javax.swing.JFrame {
    private int total_harga = 0;

    private String idMenuSelected = "", namaMenu, hargaMenu, jenisMenu;

    private int jumlah // jumlah menu yg dipilih
            , ttlHrgMenu // total harga dari menu (harga menu * jumlah menu)
            , ttlHargaBayar = 0; // total keseluruhan harga dari menu

    private DefaultTableModel tblModel;
    private Object[][] dataPenjualan = new Object[1][6];
    String harga;

    /**
     * Creates new form JFrameTransaksi
     */
    
    DefaultTableModel tabModel;
           ResultSet Rs=null;
    public JFrameTransaksi() {
        initComponents();
        Hapustbl();
       
        this.txtKdpenjualan.setEditable(false);
        txtTotalHarga.setText(Integer.toString(total_harga));
        txt_karyawan.setText("0");
        this.jButton1.setBackground(new java.awt.Color(0, 0, 0, 0));
         this.jButton2.setBackground(new java.awt.Color(0, 0, 0, 0));
          this.jButton3.setBackground(new java.awt.Color(0, 0, 0, 0));
           this.jButton4.setBackground(new java.awt.Color(0, 0, 0, 0));
            this.jButton5.setBackground(new java.awt.Color(0, 0, 0, 0));
             this.jButton6.setBackground(new java.awt.Color(0, 0, 0, 0));
              this.btnTambahdata.setBackground(new java.awt.Color(0, 0, 0, 0));
               this.btnHapus_penjualan.setBackground(new java.awt.Color(0, 0, 0, 0));
                this.btnHitung.setBackground(new java.awt.Color(0, 0, 0, 0));
                 this.btnKonfirmasi.setBackground(new java.awt.Color(0, 0, 0, 0));
                  this.butontranbeli.setBackground(new java.awt.Color(0, 0, 0, 0));
                   this.butontranjual.setBackground(new java.awt.Color(0, 0, 0, 0));
                    this.txtKdpenjualan.setBackground(new java.awt.Color(0, 0, 0, 0));
                     this.txtProduk.setBackground(new java.awt.Color(0, 0, 0, 0));
                      this.txtNamaproduk.setBackground(new java.awt.Color(0, 0, 0, 0));
                       this.txtJumlah1.setBackground(new java.awt.Color(0, 0, 0, 0));
                      this.txt_karyawan.setBackground(new java.awt.Color(0, 0, 0, 0));
                     this.btnKembali.setBackground(new java.awt.Color(0, 0, 0, 0));
                    this.txtCari.setBackground(new java.awt.Color(0, 0, 0, 0));
                   this.btnCari.setBackground(new java.awt.Color(0, 0, 0, 0));
                  this.btnMember.setBackground(new java.awt.Color(0, 0, 0, 0));
                 this.btnCarimember.setBackground(new java.awt.Color(0, 0, 0, 0));
                this.txtKdmember.setBackground(new java.awt.Color(0, 0, 0, 0));
               this.txtNamamember.setBackground(new java.awt.Color(0, 0, 0, 0));
              this.txtDiskon.setBackground(new java.awt.Color(0, 0, 0, 0));
             this.txtBayar.setBackground(new java.awt.Color(0, 0, 0, 0));
            this.txtKembali.setBackground(new java.awt.Color(0, 0, 0, 0));
        
        this.jTablebarang.getTableHeader().setFont(new java.awt.Font("Tahoma",0,16));
        this.jTablepenjualan.getTableHeader().setFont(new java.awt.Font("Tahoma",0,16));

// setting tabel model
        tblModel = new DefaultTableModel(
                new String[][]{}, // default valuenya kosong
                new String[]{
                    "kd penjualan","kd karyawan", "kd produk", "nama produk", "harga", "jumlah", "total"
                }
        ) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };
        this.updateTableBarang();
        this.txtKdpenjualan.setText(this.createkd());
     
        txtKdpenjualan.setText(this.createkd());
        Hapus();
       txtKdmember.setText("MB001");
       txtNamamember.setText("No diskon");
       txtDiskon.setText("0");
       this.txt_karyawan.setText(JFramelogin.karyawan);
    }
    
      private void Hapus(){
         txtProduk.setText(null);
           txtNamaproduk.setText(null);
//           txt_karyawan.setText(null);
             txtKdmember.setText(null);
              txtNamamember.setText(null);
              txtDiskon.setText(null);
              txtJumlah1.setText(null);
      }
      
      public void cetakStrukPenjualan(Connection conn, String idTransaksi) {
        try {
            // menyiapkan id transaksi
            HashMap parameter = new HashMap();
            parameter.put("kd_transaksi", idTransaksi);

            // meyiapkan jasper report
            InputStream file = getClass().getResourceAsStream("/IReport/report2.jrxml");
            JasperDesign desain = JRXmlLoader.load(file);
            JasperReport report = JasperCompileManager.compileReport(desain);
            JasperPrint print = JasperFillManager.fillReport(report, parameter, conn);

            // membuka jasper report
            JasperViewer jview = new JasperViewer(print, false);
            jview.setTitle("Cetak Struk " + idTransaksi);
            jview.setVisible(true);
            jview.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            jview.setLocationRelativeTo(null);
            jview.setFitPageZoomRatio();

            // solved bug jasper report tiba-tiba minimaze
            jview.addWindowListener(new java.awt.event.WindowAdapter() {

                // menutup jasper report saat user menekan tombol close
                @Override
                public void windowClosing(WindowEvent e) {
                    System.out.println("JASPER CLOSING");
                    jview.dispose();
                }

                // menutup jasper report saat user menekan tombol close
                @Override
                public void windowClosed(WindowEvent e) {
                    System.out.println("JASPER CLOSED");
                    jview.dispose();
                }

                // memaksa jasper report untuk tetap aktif (tidak minimaze)
                @Override
                public void windowDeactivated(WindowEvent e) {
                    System.out.println("JASPER ACTIVATED");
                    jview.setVisible(true);
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
//            Message.Exception(null, e);
        }
    }
      
 private void cariData(String key){
         try{
            Object[] judul_kolom = {"KD Produk", "Nama Produk","Harga", "Stok"};
            tabModel=new DefaultTableModel(null,judul_kolom);
            jTablebarang.setModel(tabModel);
            
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
      
       private void Hapustbl(){
        DefaultTableModel model = (DefaultTableModel) jTablepenjualan.getModel();
        while (model.getRowCount()>0) {
        model.removeRow(0);
    }
       } 
    
      

     public String createkd(){
        try{
            // menyiapkan query untuk mendapatkan id terakhir
            String query = "SELECT * FROM tbl_penjualan ORDER BY kd_penjualan DESC LIMIT 0,1", lastID, kd_penjualan;
            Connection conn = (Connection) koneksi.configDB();
            Statement stat = conn.createStatement();
            ResultSet res = stat.executeQuery(query);
            // cek apakah query berhasil dieksekusi
            if(res.next()){
                // mendapatkan id terakhir
                lastID =  res.getString("kd_penjualan");
                if(lastID != null){
                    // mendapatkan nomor id
                    kd_penjualan = lastID.substring(2);
                }else{
                    kd_penjualan= "KP000";
                }
                // jika id barang belum exist maka id akan dibuat
                return String.format("KP%04d", Integer.parseInt(kd_penjualan)+1);
            }
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        return null;
    }
    
    private void updateTableBarang() {
        String[] columnNames = {"KD Produk", "Nama Produk", "Harga", "Stok",};
        DefaultTableModel tablemodel = new DefaultTableModel(columnNames, 0);

        try {
            Connection c = (Connection) koneksi.configDB();
            Statement s = c.createStatement();
            ResultSet rs = s.executeQuery("SELECT kd_barang, nama_barang, harga_barang, stok_barang FROM tbl_barang");

            while (rs.next()) {
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
        this.jTablebarang.setModel(tablemodel);
    }



    private String idSelected;

    private void showData() {
        try {
            this.idSelected = this.jTablebarang.getValueAt(this.jTablebarang.getSelectedRow(), 0).toString();
            System.out.println(idSelected);
            String sql = "SELECT * FROM tbl_barang WHERE kd_barang = '" + this.idSelected + "'";
            Connection c = (Connection) koneksi.configDB();
            Statement s = c.createStatement();
            ResultSet r = s.executeQuery(sql);

            // get data
            if (r.next()) {
                String nama_produk = r.getString("nama_barang"),
                        harga = r.getString("harga_barang"),
                        stok = r.getString("stok_barang");
                this.harga = harga;
                // menampilkan data
//                this.jTextField2kdproduk.setText(this.idSelected);
//                this.jTextFieldnamaproduk.setText(nama_produk);
//                this.jTextFieldhargaproduk.setText(harga);
//                this.jTextField5stokproduk.setText(stok);
                this.txtProduk.setText(this.idSelected);
                this.txtNamaproduk.setText(nama_produk);
//                this.txtJumlah.setText(harga);
//                this.txtPenjualan.setText(this.idSelected);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "error : " + ex.getMessage());
        }
    }
    private void showData1() {
        try {
            this.idSelected = this.jTablepenjualan.getValueAt(this.jTablepenjualan.getSelectedRow(), 0).toString();
            System.out.println(idSelected);
            String sql = "SELECT * FROM tbl_barang WHERE kd_barang = '" + this.idSelected + "'";
            Connection c = (Connection) koneksi.configDB();
            Statement s = c.createStatement();
            ResultSet r = s.executeQuery(sql);

            // get data
            if (r.next()) {
                String nama_produk = r.getString("nama_barang"),
                        harga = r.getString("harga_barang"),
                        stok = r.getString("stok_barang");
                this.harga = harga;
                // menampilkan data
//                this.jTextField2kdproduk.setText(this.idSelected);
//                this.jTextFieldnamaproduk.setText(nama_produk);
//                this.jTextFieldhargaproduk.setText(harga);
//                this.jTextField5stokproduk.setText(stok);
                this.txtProduk.setText(this.idSelected);
                this.txtNamaproduk.setText(nama_produk);
//                this.txtJumlah.setText(harga);
//                this.txtPenjualan.setText(this.idSelected);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "error : " + ex.getMessage());
        }
    }

    private void updateTabel() {

        // cek apakah data sudah ada didalam tabel atau tidak
        for (int i = 0; i < this.tblModel.getRowCount(); i++) {
            if (this.txtProduk.getText().equals(String.valueOf(this.jTablepenjualan.getValueAt(i, 1)))) {
                // nanti dulu
                this.jumlah += Integer.parseInt(String.valueOf(this.jTablepenjualan.getValueAt(i, 5)));
            }
        }
      
        DefaultTableModel model = (DefaultTableModel) jTablepenjualan.getModel();

        boolean noMerge = true;
        int jmlLama, jmlBaru;
        /**
         * Mengecek apakah data nama menu yang dipilih sudah ada atau belum
         * didalam tabel transaksi yang ada di window Jika sudah ada maka data
         * jumlah pada tabel transaksi akan diupdate dan tidak membuat baris
         * baru pada tabel Jika belum maka data nama menu akan ditambahkan pada
         * baris baru pada tabel
         */
        if (jTablepenjualan.getRowCount() >= 1) {
//             membaca isi tabel transaksi
            for (int i = 0; i < model.getRowCount(); i++) {
                // mendapatkan data nama menu dari tabel
                String name = model.getValueAt(i, 0).toString();
                System.out.println("name " +name);
                System.out.println("name " +name);
                // jika nama menu sudah ada didalam tabel, maka akan mengupdate data jumlah yang ada didalam tabel transaksi
                if (name.equalsIgnoreCase(this.namaMenu)) {
                    noMerge = false;
                    // mendapatkan data jumlah menu yang dipesan
                    jmlLama = Integer.parseInt((String) model.getValueAt(i, 5));
                    // mengupdate data jumlah menu yang dipesan
                    jmlBaru = jmlLama + Integer.parseInt(this.txtJumlah1.getText());

                    // update data jumlah yang ada didalam tabel
                    model.setValueAt(Integer.toString(jmlBaru), i, 5);
                    String total = Integer.toString(jmlBaru * Integer.parseInt(hargaMenu));
                    model.setValueAt(total, i, 6);
                }

            }
        }
      
        this.ttlHrgMenu  = Integer.parseInt(this.txtJumlah1.getText()) * Integer.parseInt(this.jTablebarang.getValueAt(this.jTablebarang.getSelectedRow(), 2).toString());
        this.total_harga += ttlHrgMenu;
        // jika nama menu belum ada didalam tabel maka nama menu akan ditambahkan pada baris baru
        String karyawan = this.txt_karyawan.getText();
        String karyawan1[] = karyawan.split(" ");
        String data1 = karyawan1[0],data2 = karyawan1[1];
        int jumlah = Integer.parseInt(txtJumlah1.getText());
        if(jumlah<1){
            JOptionPane.showMessageDialog(this, "Jumlah tidak boleh kosong");
            return;
        }
        // pengecekan jika jumlah melebihi stok produk
        else if(jumlah > Integer.parseInt(this.jTablebarang.getValueAt(this.jTablebarang.getSelectedRow(), 3).toString())){
            JOptionPane.showMessageDialog(this, "Jumlah yang dimasukan melebihi stok produk!!");
            return;            
        }
        if (noMerge) {
            //update table
            model.addRow(new Object[]{
//                this.txtPenjualan.getText(),
                this.txtKdpenjualan.getText(),
                data2,
//                this.idMenuSelected,
                this.txtProduk.getText(),
//                this.namaMenu,
                this.txtNamaproduk.getText(),
//                this.jenisMenu,
                this.jTablebarang.getValueAt(this.jTablebarang.getSelectedRow(), 2),
//                this.txtJumlah.getText(),
                jumlah,
                this.ttlHrgMenu,
                this.txtDiskon.getText()
            });
        }
        jTablepenjualan.setModel(model);
        int menu = this.ttlHrgMenu;
        int total = Integer.parseInt(txtTotalHarga.getText()) + menu;
        txtTotalHarga.setText(""+total);
    }
    
    private void updateHarga(){
        int harga = 0;
        for(int i = 0; i < this.jTablepenjualan.getRowCount(); i++){
            harga = Integer.parseInt(this.jTablepenjualan.getValueAt(i, 6).toString());
        }
        this.txtTotalHarga.setText(""+harga);
    }
    
    private void editTabel() {

        // cek apakah data sudah ada didalam tabel atau tidak
        for (int i = 0; i < this.tblModel.getRowCount(); i++) {
            if (this.txtProduk.getText().equals(String.valueOf(this.jTablepenjualan.getValueAt(i, 1)))) {
                // nanti dulu
                this.jumlah += Integer.parseInt(String.valueOf(this.jTablepenjualan.getValueAt(i, 5)));
            }
        }

        DefaultTableModel model = (DefaultTableModel) jTablepenjualan.getModel();

        boolean noMerge = true;
        int jmlLama, jmlBaru;
        /**
         * Mengecek apakah data nama menu yang dipilih sudah ada atau belum
         * didalam tabel transaksi yang ada di window Jika sudah ada maka data
         * jumlah pada tabel transaksi akan diupdate dan tidak membuat baris
         * baru pada tabel Jika belum maka data nama menu akan ditambahkan pada
         * baris baru pada tabel
         */
        if (jTablepenjualan.getRowCount() >= 1) {
//             membaca isi tabel transaksi
            for (int i = 0; i < model.getRowCount(); i++) {
                // mendapatkan data nama menu dari tabel
                String name = model.getValueAt(i, 0).toString();
                System.out.println("name " +name);
                System.out.println("name " +name);
                // jika nama menu sudah ada didalam tabel, maka akan mengupdate data jumlah yang ada didalam tabel transaksi
                if (name.equalsIgnoreCase(this.namaMenu)) {
                    noMerge = false;
                    // mendapatkan data jumlah menu yang dipesan
                    jmlLama = Integer.parseInt((String) model.getValueAt(i, 5));
                    // mengupdate data jumlah menu yang dipesan
                    jmlBaru = jmlLama + Integer.parseInt(this.txtJumlah1.getText());

                    // update data jumlah yang ada didalam tabel
                    model.setValueAt(Integer.toString(jmlBaru), i, 5);
                    String total = Integer.toString(jmlBaru * Integer.parseInt(hargaMenu));
                    model.setValueAt(total, i, 6);
                }

            }
        }
        
        this.ttlHrgMenu  = Integer.parseInt(this.txtJumlah1.getText()) * Integer.parseInt(this.jTablebarang.getValueAt(this.jTablebarang.getSelectedRow(), 2).toString());
        this.total_harga += ttlHrgMenu;
        // jika nama menu belum ada didalam tabel maka nama menu akan ditambahkan pada baris baru
        String karyawan = this.txt_karyawan.getText();
        String karyawan1[] = karyawan.split(" ");
        String data1 = karyawan1[0],data2 = karyawan1[1];
        int jumlah = Integer.parseInt(txtJumlah1.getText());
        if(jumlah<1){
            JOptionPane.showMessageDialog(this, "Jumlah tidak boleh kosong");
            return;
        }
        if (noMerge) {

//            model.addRow(new Object[]{
////                this.txtPenjualan.getText(),
//                this.txtPenjualan.getText(),
//                data2,
////                this.idMenuSelected,
//                this.txtProduk.getText(),
////                this.namaMenu,
//                this.txtNama_produk.getText(),
////                this.jenisMenu,
//                this.jTablebarang.getValueAt(this.jTablebarang.getSelectedRow(), 2),
////                this.txtJumlah.getText(),
//                jumlah,
//                this.ttlHrgMenu
//            });
        }
        jTablepenjualan.setModel(model);
        txtTotalHarga.setText(Integer.toString(total_harga));
    }

    private void tambahDataMenu() {
        // update data total harga
        this.jumlah = Integer.parseInt(this.txtJumlah1.getText());
        this.ttlHrgMenu = Integer.parseInt(this.hargaMenu) * jumlah;
        this.ttlHargaBayar += this.ttlHrgMenu;
        this.txtTotalHarga.setText(""+ttlHargaBayar);
        // reset tabel dan textfield
        this.updateTabel();
//        this.resetTambah();
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
        txtKdpenjualan = new javax.swing.JTextField();
        txtProduk = new javax.swing.JTextField();
        txtNamaproduk = new javax.swing.JTextField();
        txt_karyawan = new javax.swing.JTextField();
        btnTambahdata = new javax.swing.JButton();
        btnHapus_penjualan = new javax.swing.JButton();
        btnHitung = new javax.swing.JButton();
        butontranbeli = new javax.swing.JButton();
        btnCari = new javax.swing.JButton();
        btnKonfirmasi = new javax.swing.JButton();
        txtCari = new javax.swing.JTextField();
        txtTotalHarga = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTablebarang = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTablepenjualan = new javax.swing.JTable();
        btnKembali = new javax.swing.JButton();
        butontranjual = new javax.swing.JButton();
        btnMember = new javax.swing.JButton();
        txtKdmember = new javax.swing.JTextField();
        txtNamamember = new javax.swing.JTextField();
        txtDiskon = new javax.swing.JTextField();
        txtKembali = new javax.swing.JTextField();
        txtBayar = new javax.swing.JTextField();
        btnCarimember = new javax.swing.JButton();
        txtJumlah1 = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 150, 160, 40));

        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 280, 160, 40));

        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 330, 160, 40));

        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 430, 160, 30));

        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton5, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 380, 170, 40));

        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton6, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 200, 180, 40));

        txtKdpenjualan.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        txtKdpenjualan.setBorder(null);
        txtKdpenjualan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtKdpenjualanActionPerformed(evt);
            }
        });
        getContentPane().add(txtKdpenjualan, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 200, 280, 30));

        txtProduk.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        txtProduk.setBorder(null);
        txtProduk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtProdukActionPerformed(evt);
            }
        });
        getContentPane().add(txtProduk, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 270, 280, 30));

        txtNamaproduk.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        txtNamaproduk.setBorder(null);
        getContentPane().add(txtNamaproduk, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 330, 280, 30));

        txt_karyawan.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        txt_karyawan.setBorder(null);
        txt_karyawan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_karyawanActionPerformed(evt);
            }
        });
        getContentPane().add(txt_karyawan, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 470, 280, 30));

        btnTambahdata.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTambahdataActionPerformed(evt);
            }
        });
        getContentPane().add(btnTambahdata, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 200, 90, 40));

        btnHapus_penjualan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHapus_penjualanActionPerformed(evt);
            }
        });
        getContentPane().add(btnHapus_penjualan, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 200, 90, 40));

        btnHitung.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHitungActionPerformed(evt);
            }
        });
        getContentPane().add(btnHitung, new org.netbeans.lib.awtextra.AbsoluteConstraints(890, 200, 90, 40));

        butontranbeli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butontranbeliActionPerformed(evt);
            }
        });
        getContentPane().add(butontranbeli, new org.netbeans.lib.awtextra.AbsoluteConstraints(1030, 110, 230, 40));

        btnCari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCariActionPerformed(evt);
            }
        });
        getContentPane().add(btnCari, new org.netbeans.lib.awtextra.AbsoluteConstraints(1290, 170, 30, 30));

        btnKonfirmasi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKonfirmasiActionPerformed(evt);
            }
        });
        getContentPane().add(btnKonfirmasi, new org.netbeans.lib.awtextra.AbsoluteConstraints(1010, 700, 230, 60));

        txtCari.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
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
        getContentPane().add(txtCari, new org.netbeans.lib.awtextra.AbsoluteConstraints(1040, 170, 250, 30));

        txtTotalHarga.setFont(new java.awt.Font("Tahoma", 1, 30)); // NOI18N
        getContentPane().add(txtTotalHarga, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 710, 310, 50));

        jTablebarang.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jTablebarang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "KD Produk", "Nama Produk", "Harga", "Stok"
            }
        ));
        jTablebarang.setRowHeight(25);
        jTablebarang.getTableHeader().setReorderingAllowed(false);
        jTablebarang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTablebarangMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jTablebarangMouseEntered(evt);
            }
        });
        jScrollPane1.setViewportView(jTablebarang);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 250, 660, 140));

        jTablepenjualan.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jTablepenjualan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "kd penjualan", "kd karyawan", "kd produk", "nama produk", "harga", "jumlah", "total", "Diskon"
            }
        ));
        jTablepenjualan.setRowHeight(25);
        jTablepenjualan.getTableHeader().setReorderingAllowed(false);
        jTablepenjualan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTablepenjualanMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jTablepenjualan);

        getContentPane().add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 420, 660, 190));

        btnKembali.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKembaliActionPerformed(evt);
            }
        });
        getContentPane().add(btnKembali, new org.netbeans.lib.awtextra.AbsoluteConstraints(1280, 10, 70, 60));

        butontranjual.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butontranjualActionPerformed(evt);
            }
        });
        getContentPane().add(butontranjual, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 110, 230, 40));

        btnMember.setBorder(null);
        btnMember.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMemberActionPerformed(evt);
            }
        });
        getContentPane().add(btnMember, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 240, 160, 40));

        txtKdmember.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        txtKdmember.setBorder(null);
        txtKdmember.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtKdmemberActionPerformed(evt);
            }
        });
        getContentPane().add(txtKdmember, new org.netbeans.lib.awtextra.AbsoluteConstraints(359, 530, 280, 30));

        txtNamamember.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        txtNamamember.setBorder(null);
        getContentPane().add(txtNamamember, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 600, 250, 30));

        txtDiskon.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        txtDiskon.setBorder(null);
        txtDiskon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDiskonActionPerformed(evt);
            }
        });
        getContentPane().add(txtDiskon, new org.netbeans.lib.awtextra.AbsoluteConstraints(359, 660, 50, 30));

        txtKembali.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        txtKembali.setBorder(null);
        getContentPane().add(txtKembali, new org.netbeans.lib.awtextra.AbsoluteConstraints(1040, 650, 230, 30));

        txtBayar.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        txtBayar.setBorder(null);
        txtBayar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBayarActionPerformed(evt);
            }
        });
        getContentPane().add(txtBayar, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 650, 230, 30));

        btnCarimember.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCarimemberActionPerformed(evt);
            }
        });
        getContentPane().add(btnCarimember, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 600, 30, 30));

        txtJumlah1.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        txtJumlah1.setBorder(null);
        txtJumlah1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtJumlah1ActionPerformed(evt);
            }
        });
        getContentPane().add(txtJumlah1, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 400, 280, 30));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/asset/Penjualan.png"))); // NOI18N
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        new JFrameKaryawan().setVisible(true);
        dispose();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        
        new JFrameDashboard().setVisible(true);
        dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        new JFrameProduk().setVisible(true);
        dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

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

    private void txt_karyawanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_karyawanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_karyawanActionPerformed

    private void jTablebarangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTablebarangMouseClicked
        this.showData();
        
//                this.txtPenjualan.getText(),
//                this.idMenuSelected,
//                this.namaMenu,
//                this.jenisMenu,
//                this.hargaMenu,
//                this.txtJumlah.getText(),
//                this.ttlHrgMenu
    }//GEN-LAST:event_jTablebarangMouseClicked

    private void btnTambahdataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahdataActionPerformed
//        // TODO add your handling code here:
//        String kd_penjualan = txtPenjualan.getText();
//        String kd_produk = txtProduk.getText();
//        String nama_produk = txtNama_produk.getText();
//        int jumlah = Integer.parseInt(txtJumlah.getText());
//        int total = Integer.parseInt(harga) + jumlah;
////        System.out.println(dataPenjualan);
//        int panjang = dataPenjualan.length;
//        System.out.println(kd_penjualan);
//        dataPenjualan[panjang][0] = kd_penjualan;
//        dataPenjualan[panjang][1] = kd_produk;
//        dataPenjualan[panjang][2] = nama_produk;
//        dataPenjualan[panjang][3] = harga;
//        dataPenjualan[panjang][4] = jumlah;
//        dataPenjualan[panjang][5] = total;
//        ArrayList t = new ArrayList(Arrays.asList(dataPenjualan));
//        t.add(panjang++);
//        dataPenjualan = (Object[][]) t.toArray(dataPenjualan);
////        System.out.println("panjang data "+panjang);
//        updateTable1();
        
//        this.tambahDataMenu();
        if(txtJumlah1.getText().equals("")){
            JOptionPane.showMessageDialog(null, "Jumlah tidak boleh kosong");
        }
        this.updateTabel();
    }//GEN-LAST:event_btnTambahdataActionPerformed

    private void txtProdukActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtProdukActionPerformed
        try{
           String sql = "select * from tbl_barang where kd_barang='"+txtProduk.getText()+"'";
           Connection con = (Connection) koneksi.configDB();
           Statement st = con.createStatement();
           ResultSet rs = st.executeQuery(sql);
           System.out.println(sql);
           if(rs.next()){
               txtNamaproduk.setText(rs.getString("nama_barang"));
               
           }
       }catch(Exception e){
           JOptionPane.showMessageDialog(this, e+" ERROR PADA ID BARANG");
       }          
    }//GEN-LAST:event_txtProdukActionPerformed

    private int getJumlahBarang(){
        int index = 0;
        for(int i = 0; i < this.jTablepenjualan.getRowCount(); i++){
            index += Integer.parseInt(this.jTablepenjualan.getValueAt(i, 5).toString());
        }
        return index;
    }
    
    private int getTotalHarga(){
        int index = 0;
        for(int i = 0; i < this.jTablepenjualan.getRowCount(); i++){
            index += Integer.parseInt(this.jTablepenjualan.getValueAt(i, 6).toString());
        }
        return index;
    }
    
    private boolean tfPenjualan(){
        try {
            Connection c = (Connection) koneksi.configDB();
            PreparedStatement p = c.prepareStatement("INSERT INTO tbl_penjualan (kd_penjualan, kd_karyawan, total_barang, total_harga, kd_member, diskon, bayar)VALUES (?, ?, ?, ?, ?, ?, ?)");
            p.setString(1, this.txtKdpenjualan.getText());
            p.setString(2, this.txt_karyawan.getText().substring(0, 6));
            p.setInt(3, this.getJumlahBarang());
            p.setString(4, this.txtTotalHarga.getText());
            p.setString(5, txtKdmember.getText());
            p.setString(7, txtBayar.getText());
            if(this.txtKdmember.getText().equalsIgnoreCase("MB001")){
                p.setString(6, "Tidak");
            }else{
                p.setString(6, "Ya");
            }
            
            return p.executeUpdate() > 0;
            
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "error : " + ex.getMessage());
        }
        return false;
    }
    
    private void btnKonfirmasiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKonfirmasiActionPerformed
        if(txtBayar.getText().equals("")){
            JOptionPane.showMessageDialog(null, "Masukkan Nominal Bayar Terlebih Dahulu");
        }else{
           
        
        
        if(this.tfPenjualan()){
            try {
                    Connection c = (Connection) koneksi.configDB();
                for(int i = 0; i < this.jTablepenjualan.getRowCount(); i++){
                    PreparedStatement p = c.prepareStatement("INSERT INTO detail_jualbarang VALUES (?, ?, ?, ?)");
                    p.setString(1, this.txtKdpenjualan.getText());
                    p.setString(2, this.jTablepenjualan.getValueAt(i, 2).toString());
                    p.setString(3, this.jTablepenjualan.getValueAt(i, 5).toString());
                    p.setString(4, this.jTablepenjualan.getValueAt(i, 6).toString());
                   
                    p.executeUpdate();
                }
                JOptionPane.showMessageDialog(this, "Transaksi Berhasil!");
                System.out.println(this.txtKdpenjualan.getText());
                cetakStrukPenjualan(c,txtKdpenjualan.getText());
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "error : " + ex.getMessage());
            }
           
        }
        this.updateTableBarang();
        Hapus();
        txtKdpenjualan.setText(this.createkd());
       txtKdmember.setText("MB001");
       txtNamamember.setText("no diskon");
       txtDiskon.setText("0");
       this.txtTotalHarga.setText("");
       this.txtBayar.setText("");
       this.txtKembali.setText("");
       Hapustbl();
        }  
    }//GEN-LAST:event_btnKonfirmasiActionPerformed

    
    private void jTablebarangMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTablebarangMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_jTablebarangMouseEntered

    private void jTablepenjualanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTablepenjualanMouseClicked
        this.showData1();
    }//GEN-LAST:event_jTablepenjualanMouseClicked

    private void btnHapus_penjualanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapus_penjualanActionPerformed
        // TODO add your handling code here:
         if(this.jTablepenjualan.getSelectedRow() > -1){
            
            // mendapatkan nama data yang akan dihapus
            String nama = this.jTablepenjualan.getValueAt(this.jTablepenjualan.getSelectedRow(), 3).toString();

            // memunculkan pop up konfirmasi penghapusan data
            int status = JOptionPane.showConfirmDialog(this, "Apakah anda yakin ingin menghapus " + nama + "?", "confirm", JOptionPane.YES_OPTION, JOptionPane.QUESTION_MESSAGE);

            // jika pada pop up yang ditekan bukan button 'yes' maka data tidak akan dihapus
            if(status != JOptionPane.YES_OPTION){
                return;
            }
           
            DefaultTableModel model = (DefaultTableModel) jTablepenjualan.getModel();
            int row = jTablepenjualan.getSelectedRow();
            model.removeRow(row);            
            this.updateHarga();
        }else{
           
        }
    }//GEN-LAST:event_btnHapus_penjualanActionPerformed

    private void btnHitungActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHitungActionPerformed

        double hargaAwal, hargaDiskon, diskon;

        hargaAwal = Double.parseDouble(txtTotalHarga.getText());
        diskon = Double.parseDouble(txtDiskon.getText());
        hargaDiskon = hargaAwal - (hargaAwal * (diskon/100));
        txtTotalHarga.setText(Double.toString(hargaDiskon));


        this.jTablepenjualan.setValueAt(txtDiskon.getText(), this.jTablepenjualan.getRowCount()-1, 7);
        this.txtTotalHarga.setText(txtTotalHarga.getText().substring(0, this.txtTotalHarga.getText().lastIndexOf(".")));
       
        int ttlHarga = Integer.parseInt(this.jTablepenjualan.getValueAt(this.jTablepenjualan.getRowCount()-1, 4).toString()),
            jml = Integer.parseInt(this.jTablepenjualan.getValueAt(this.jTablepenjualan.getRowCount()-1, 5).toString());   
        this.jTablepenjualan.setValueAt(Integer.toString((int)ttlHarga*jml), this.jTablepenjualan.getRowCount()-1, 6);
        
    
    }//GEN-LAST:event_btnHitungActionPerformed

    private void txtKdpenjualanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtKdpenjualanActionPerformed

        // TODO add your handling code here:
    }//GEN-LAST:event_txtKdpenjualanActionPerformed

    private void butontranbeliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butontranbeliActionPerformed
        // TODO add your handling code here:
        new JFramepembelian().setVisible(true);
        dispose();
    }//GEN-LAST:event_butontranbeliActionPerformed

    private void txtCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCariActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCariActionPerformed

    private void btnKembaliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKembaliActionPerformed
        // TODO add your handling code here:
        new JFrameDashboard().setVisible(true);
        dispose();
    }//GEN-LAST:event_btnKembaliActionPerformed

    private void butontranjualActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butontranjualActionPerformed
        // TODO add your handling code here:
        new JFrameTransaksi().setVisible(true);
        dispose();
    }//GEN-LAST:event_butontranjualActionPerformed

    private void btnMemberActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMemberActionPerformed
        new Member().setVisible(true);
        dispose();
    }//GEN-LAST:event_btnMemberActionPerformed

    private void txtBayarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBayarActionPerformed
         int total, bayar, kembalian;
       
       total = Integer.valueOf(txtTotalHarga.getText());
       bayar = Integer.valueOf(txtBayar.getText());
       
       //jika total lebih dari bayar makan akan muncul uang kurang
       if (total > bayar) {
           JOptionPane.showMessageDialog(null, "Uang anda kurang");
       }else { 
           kembalian = bayar - total;
           txtKembali.setText(String.valueOf(kembalian));
       }
    }//GEN-LAST:event_txtBayarActionPerformed

    private void txtDiskonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDiskonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDiskonActionPerformed

    private void btnCarimemberActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCarimemberActionPerformed
        this.setVisible(true);
        new JFramePopup().setVisible(true);
    }//GEN-LAST:event_btnCarimemberActionPerformed

    private void txtKdmemberActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtKdmemberActionPerformed
       try{
           String sql = "select * from tbl_member where kd_member='"+txtKdmember.getText()+"'";
           Connection con = (Connection) koneksi.configDB();
           Statement st = con.createStatement();
           ResultSet rs = st.executeQuery(sql);
           System.out.println(sql);
           if(rs.next()){
               txtNamamember.setText(rs.getString("nama_member"));
               txtDiskon.setText(rs.getString("diskon"));
           }
       }catch(Exception e){
           JOptionPane.showMessageDialog(this, e+" ERROR PADA ID MEMBER");
       }
    }//GEN-LAST:event_txtKdmemberActionPerformed

    private void txtCariKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCariKeyReleased
  String key = txtCari.getText();
        System.out.println(key);
        
        if(key!=""){
            cariData(key);
        }else{
            showData();
        }
    }//GEN-LAST:event_txtCariKeyReleased

    private void btnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCariActionPerformed
       
    }//GEN-LAST:event_btnCariActionPerformed

    private void txtJumlah1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtJumlah1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtJumlah1ActionPerformed

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
            java.util.logging.Logger.getLogger(JFrameTransaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JFrameTransaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JFrameTransaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JFrameTransaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new JFrameTransaksi().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCari;
    private javax.swing.JButton btnCarimember;
    private javax.swing.JButton btnHapus_penjualan;
    private javax.swing.JButton btnHitung;
    private javax.swing.JButton btnKembali;
    private javax.swing.JButton btnKonfirmasi;
    private javax.swing.JButton btnMember;
    private javax.swing.JButton btnTambahdata;
    private javax.swing.JButton butontranbeli;
    private javax.swing.JButton butontranjual;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTablebarang;
    private javax.swing.JTable jTablepenjualan;
    private javax.swing.JTextField txtBayar;
    private javax.swing.JTextField txtCari;
    public static javax.swing.JTextField txtDiskon;
    private javax.swing.JTextField txtJumlah1;
    public static javax.swing.JTextField txtKdmember;
    private javax.swing.JTextField txtKdpenjualan;
    private javax.swing.JTextField txtKembali;
    public static javax.swing.JTextField txtNamamember;
    private javax.swing.JTextField txtNamaproduk;
    private javax.swing.JTextField txtProduk;
    private javax.swing.JLabel txtTotalHarga;
    private javax.swing.JTextField txt_karyawan;
    // End of variables declaration//GEN-END:variables
}
