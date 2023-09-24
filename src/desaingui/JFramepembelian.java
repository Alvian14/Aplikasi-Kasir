/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package desaingui;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author LENOVO
 */
public class JFramepembelian extends javax.swing.JFrame {
    private int total_harga = 0;
    private void listgay() {
        try {
            String sql = "SELECT * From tbl_supplier ";
            Connection conn = (Connection) koneksi.configDB();
            java.sql.Statement pst = conn.createStatement();
            java.sql.ResultSet res = pst.executeQuery(sql);

            while (res.next()) {
                pilihkd_supplier.addItem( res.getString("kd_supplier") +" " +  res.getString("nama_supplier"));

            }

        } catch (SQLException ex ) {
            JOptionPane.showMessageDialog(this, ex.getMessage());

        }
    }
private void showDate() {
    Date D = new Date();
    SimpleDateFormat s = new SimpleDateFormat("dd-MM-yyyy");
    String Dt = s.format(D);
    this.textdate.setText(Dt);
}
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
    public JFramepembelian() {
        initComponents();
        txtTotalHarga.setText(Integer.toString(total_harga));
        teksjumlah.setText("0");
        this.jButton1.setBackground(new java.awt.Color(0, 0, 0, 0));
        this.jButton2.setBackground(new java.awt.Color(0, 0, 0, 0));
        this.jButton3.setBackground(new java.awt.Color(0, 0, 0, 0));
        this.jButton4.setBackground(new java.awt.Color(0, 0, 0, 0));
        this.jButton5.setBackground(new java.awt.Color(0, 0, 0, 0));
        this.jButton6.setBackground(new java.awt.Color(0, 0, 0, 0));
        this.btnTambahdata.setBackground(new java.awt.Color(0, 0, 0, 0));
        this.btnHapus_penjualan.setBackground(new java.awt.Color(0, 0, 0, 0));
        this.btnMember.setBackground(new java.awt.Color(0, 0, 0, 0));
        this.btnKonfirmasi.setBackground(new java.awt.Color(0, 0, 0, 0));
        
        this.jButtontransaksi_penjualan.setBackground(new java.awt.Color(0, 0, 0, 0));
        this.tekskd_pembelian.setBackground(new java.awt.Color(0, 0, 0, 0));
        this.teksnama_produk.setBackground(new java.awt.Color(0, 0, 0, 0));
        this.teksharga_barang.setBackground(new java.awt.Color(0, 0, 0, 0));
        this.teksjumlah.setBackground(new java.awt.Color(0, 0, 0, 0));
        this.pilihkd_supplier.setBackground(new java.awt.Color(0, 0, 0, 0)); 
        this.teksjtotal_harga.setBackground(new java.awt.Color(0, 0, 0, 0));
        
        this.butonkembali.setBackground(new java.awt.Color(0, 0, 0, 0));
        this.butonpembelian.setBackground(new java.awt.Color(0, 0, 0, 0));
        listgay();
        createkd();
        showDate();

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
        this.tekskd_pembelian.setText(this.createkd());
        
    }
    
    private void AddRowToTable(Object[] dataRow)    {
        DefaultTableModel model = (DefaultTableModel) jTablepembelian.getModel();
        model.addRow(dataRow);
    }
    public void totalharga() {
        int jumlahbaris = jTablepembelian.getRowCount();
        int totalharga = 0;
        int hargabarang;
        for (int i = 0; i < jumlahbaris; i++) {
            hargabarang =  Integer.parseInt(jTablepembelian.getValueAt(i, 4).toString().replace(" ", ""));
            totalharga= totalharga + hargabarang;
        
    }
    String tot = Integer.toString(totalharga);
    
    this.txtTotalHarga.setText(tot);
}
     public String createkd(){
        try{
            // menyiapkan query untuk mendapatkan id terakhir
            String query = "SELECT * FROM tbl_pembelian ORDER BY kd_pembelian DESC LIMIT 0,1", lastID, kd_pembelian;
            Connection conn = (Connection) koneksi.configDB();
            Statement stat = conn.createStatement();
            ResultSet res = stat.executeQuery(query);
            // cek apakah query berhasil dieksekusi
            if(res.next()){
                // mendapatkan id terakhir
                lastID =  res.getString("kd_pembelian");
                if(lastID != null){
                    // mendapatkan nomor id
                    kd_pembelian = lastID.substring(2);
                }else{
                    kd_pembelian= "KB000";
                }
                // jika id barang belum exist maka id akan dibuat
                return String.format("KB%04d", Integer.parseInt(kd_pembelian)+1);
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
       
    }

    private void updateHarga(){
        int harga = 0;
        for(int i = 0; i < this.jTablepembelian.getRowCount(); i++){
            harga = Integer.parseInt(this.jTablepembelian.getValueAt(i, 4).toString());
        }
        this.txtTotalHarga.setText(""+harga);
    }

    private String idSelected;

//    private void showData() {
//        try {
//            this.idSelected = this.jTablebarang.getValueAt(this.jTablebarang.getSelectedRow(), 0).toString();
//            System.out.println(idSelected);
//            String sql = "SELECT * FROM tbl_barang WHERE kd_barang = '" + this.idSelected + "'";
//            Connection c = (Connection) koneksi.configDB();
//            Statement s = c.createStatement();
//            ResultSet r = s.executeQuery(sql);
//
//            // get data
//            if (r.next()) {
//                String nama_produk = r.getString("nama_barang"),
//                        harga = r.getString("harga_barang"),
//                        stok = r.getString("stok_barang");
//                this.harga = harga;
//                // menampilkan data
////                this.jTextField2kdproduk.setText(this.idSelected);
////                this.jTextFieldnamaproduk.setText(nama_produk);
////                this.jTextFieldhargaproduk.setText(harga);
////                this.jTextField5stokproduk.setText(stok);
//                this.teksnama_produk.setText(this.idSelected);
//                this.teksharga_barang.setText(nama_produk);
////                this.txtJumlah.setText(harga);
////                this.txtPenjualan.setText(this.idSelected);
//            }
//        } catch (SQLException ex) {
//            JOptionPane.showMessageDialog(this, "error : " + ex.getMessage());
//        }
//    }
    private void showData1() {
        try {
            this.idSelected = this.jTablepembelian.getValueAt(this.jTablepembelian.getSelectedRow(), 0).toString();
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
                this.teksnama_produk.setText(this.idSelected);
                this.teksharga_barang.setText(nama_produk);
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
            if (this.teksnama_produk.getText().equals(String.valueOf(this.jTablepembelian.getValueAt(i, 1)))) {
                // nanti dulu
                this.jumlah += Integer.parseInt(String.valueOf(this.jTablepembelian.getValueAt(i, 5)));
            }
        }

        DefaultTableModel model = (DefaultTableModel) jTablepembelian.getModel();

        boolean noMerge = true;
        int jmlLama, jmlBaru;
        /**
         * Mengecek apakah data nama menu yang dipilih sudah ada atau belum
         * didalam tabel transaksi yang ada di window Jika sudah ada maka data
         * jumlah pada tabel transaksi akan diupdate dan tidak membuat baris
         * baru pada tabel Jika belum maka data nama menu akan ditambahkan pada
         * baris baru pada tabel
         */
        if (jTablepembelian.getRowCount() >= 1) {
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
                    jmlBaru = jmlLama + Integer.parseInt(this.teksjumlah.getText());

                    // update data jumlah yang ada didalam tabel
                    model.setValueAt(Integer.toString(jmlBaru), i, 5);
                    String total = Integer.toString(jmlBaru * Integer.parseInt(hargaMenu));
                    model.setValueAt(total, i, 6);
                }

            }
        }
        
    
        this.total_harga += ttlHrgMenu;
        // jika nama menu belum ada didalam tabel maka nama menu akan ditambahkan pada baris baru
        String karyawan = (String) this.pilihkd_supplier.getSelectedItem();
        String karyawan1[] = karyawan.split(" ");
        String data1 = karyawan1[0],data2 = karyawan1[1];
        int jumlah = Integer.parseInt(teksjumlah.getText());
        if(jumlah<1){
            JOptionPane.showMessageDialog(this, "Jumlah tidak boleh kosong");
            return;
        }
        if (noMerge) {
            //update table
            model.addRow(new Object[]{
//                this.txtPenjualan.getText(),
                this.tekskd_pembelian.getText(),
                data2,
//                this.idMenuSelected,
                this.teksnama_produk.getText(),
//                this.namaMenu,
                this.teksharga_barang.getText(),
//                this.jenisMenu,
                this.jTablepembelian.getValueAt(this.jTablepembelian.getSelectedRow(), 2),
//                this.txtJumlah.getText(),
                jumlah,
                this.ttlHrgMenu
            });
        }
        jTablepembelian.setModel(model);
        txtTotalHarga.setText(Integer.toString(total_harga));
    }
    private void editTabel() {

        // cek apakah data sudah ada didalam tabel atau tidak
        for (int i = 0; i < this.tblModel.getRowCount(); i++) {
            if (this.teksnama_produk.getText().equals(String.valueOf(this.jTablepembelian.getValueAt(i, 1)))) {
                // nanti dulu
                this.jumlah += Integer.parseInt(String.valueOf(this.jTablepembelian.getValueAt(i, 5)));
            }
        }

        DefaultTableModel model = (DefaultTableModel) jTablepembelian.getModel();

        boolean noMerge = true;
        int jmlLama, jmlBaru;
        /**
         * Mengecek apakah data nama menu yang dipilih sudah ada atau belum
         * didalam tabel transaksi yang ada di window Jika sudah ada maka data
         * jumlah pada tabel transaksi akan diupdate dan tidak membuat baris
         * baru pada tabel Jika belum maka data nama menu akan ditambahkan pada
         * baris baru pada tabel
         */
        if (jTablepembelian.getRowCount() >= 1) {
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
                    jmlBaru = jmlLama + Integer.parseInt(this.teksjumlah.getText());

                    // update data jumlah yang ada didalam tabel
                    model.setValueAt(Integer.toString(jmlBaru), i, 5);
                    String total = Integer.toString(jmlBaru * Integer.parseInt(hargaMenu));
                    model.setValueAt(total, i, 6);
                }

            }
        }
        
        this.ttlHrgMenu  = Integer.parseInt(this.teksjumlah.getText()) * Integer.parseInt(this.teksharga_barang.getText());
        this.total_harga += ttlHrgMenu;
        // jika nama menu belum ada didalam tabel maka nama menu akan ditambahkan pada baris baru
        String karyawan = (String) this.pilihkd_supplier.getSelectedItem();
        String karyawan1[] = karyawan.split(" ");
        String data1 = karyawan1[0],data2 = karyawan1[1];
        int jumlah = Integer.parseInt(teksjumlah.getText());
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
        jTablepembelian.setModel(model);
        txtTotalHarga.setText(Integer.toString(total_harga));
    }

    private void tambahDataMenu() {
        // update data total harga
        this.jumlah = Integer.parseInt(this.teksjumlah.getText());
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

        jButtontransaksi_penjualan = new javax.swing.JButton();
        butonkembali = new javax.swing.JButton();
        butonpembelian = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        tekskd_pembelian = new javax.swing.JTextField();
        teksnama_produk = new javax.swing.JTextField();
        teksharga_barang = new javax.swing.JTextField();
        teksjumlah = new javax.swing.JTextField();
        btnTambahdata = new javax.swing.JButton();
        btnHapus_penjualan = new javax.swing.JButton();
        btnKonfirmasi = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTablepembelian = new javax.swing.JTable();
        txtTotalHarga = new javax.swing.JLabel();
        teksjtotal_harga = new javax.swing.JTextField();
        textdate = new javax.swing.JLabel();
        pilihkd_supplier = new javax.swing.JComboBox();
        btnMember = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jButtontransaksi_penjualan.setForeground(new java.awt.Color(0,0,0,0)
        );
        jButtontransaksi_penjualan.setBorderPainted(false);
        jButtontransaksi_penjualan.setContentAreaFilled(false);
        jButtontransaksi_penjualan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtontransaksi_penjualanActionPerformed(evt);
            }
        });
        getContentPane().add(jButtontransaksi_penjualan, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 70, 220, 50));

        butonkembali.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butonkembaliActionPerformed(evt);
            }
        });
        getContentPane().add(butonkembali, new org.netbeans.lib.awtextra.AbsoluteConstraints(1280, 20, 70, 50));

        butonpembelian.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butonpembelianActionPerformed(evt);
            }
        });
        getContentPane().add(butonpembelian, new org.netbeans.lib.awtextra.AbsoluteConstraints(1040, 80, 230, 40));

        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 150, 170, 30));

        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 290, 170, 40));

        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 340, 170, 30));

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
        getContentPane().add(jButton5, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 380, 170, 30));

        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton6, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 190, 170, 40));

        tekskd_pembelian.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        tekskd_pembelian.setBorder(null);
        tekskd_pembelian.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tekskd_pembelianActionPerformed(evt);
            }
        });
        getContentPane().add(tekskd_pembelian, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 190, 280, 30));

        teksnama_produk.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        teksnama_produk.setBorder(null);
        teksnama_produk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                teksnama_produkActionPerformed(evt);
            }
        });
        getContentPane().add(teksnama_produk, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 250, 280, 40));

        teksharga_barang.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        teksharga_barang.setBorder(null);
        getContentPane().add(teksharga_barang, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 320, 280, 30));

        teksjumlah.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        teksjumlah.setBorder(null);
        teksjumlah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                teksjumlahActionPerformed(evt);
            }
        });
        getContentPane().add(teksjumlah, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 390, 280, 30));

        btnTambahdata.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTambahdataActionPerformed(evt);
            }
        });
        getContentPane().add(btnTambahdata, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 190, 100, 30));

        btnHapus_penjualan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHapus_penjualanActionPerformed(evt);
            }
        });
        getContentPane().add(btnHapus_penjualan, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 190, 90, 30));

        btnKonfirmasi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKonfirmasiActionPerformed(evt);
            }
        });
        getContentPane().add(btnKonfirmasi, new org.netbeans.lib.awtextra.AbsoluteConstraints(920, 660, 420, 90));

        jTablepembelian.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jTablepembelian.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "kd Pembelian", "Nama Produk", "Harga Barang", "Jumlah", "Total Harga", "Waktu", "Kode Supplier"
            }
        ));
        jTablepembelian.setRowHeight(30);
        jTablepembelian.getTableHeader().setReorderingAllowed(false);
        jTablepembelian.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTablepembelianMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jTablepembelian);

        getContentPane().add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 250, 670, 380));

        txtTotalHarga.setFont(new java.awt.Font("Tahoma", 1, 50)); // NOI18N
        getContentPane().add(txtTotalHarga, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 670, 270, 60));

        teksjtotal_harga.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        teksjtotal_harga.setBorder(null);
        teksjtotal_harga.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                teksjtotal_hargaActionPerformed(evt);
            }
        });
        getContentPane().add(teksjtotal_harga, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 460, 280, 30));

        textdate.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        getContentPane().add(textdate, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 530, 280, 30));

        pilihkd_supplier.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        pilihkd_supplier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pilihkd_supplierActionPerformed(evt);
            }
        });
        getContentPane().add(pilihkd_supplier, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 610, 280, 30));

        btnMember.setBorder(null);
        btnMember.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMemberActionPerformed(evt);
            }
        });
        getContentPane().add(btnMember, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 240, 170, 40));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/asset/PEMBELIAN final.png"))); // NOI18N
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
        // TODO add your handling code here:
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

    private void teksjumlahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_teksjumlahActionPerformed
        // TODO add your handling code here:
        int harga = Integer.parseInt(this.teksharga_barang.getText());
        int jumlah = Integer.parseInt(this.teksjumlah.getText());
        String Hasil = Integer.toString(harga*jumlah);
        this.teksjtotal_harga.setText(Hasil);
        
    }//GEN-LAST:event_teksjumlahActionPerformed

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
//        this.updateTabel();
        
        Object SelectedItem = pilihkd_supplier.getSelectedItem();
      
            String SelectedItemStr = SelectedItem.toString();
            
        
        AddRowToTable(new Object[]{
        this.tekskd_pembelian.getText(), 
            this.teksnama_produk.getText(),
            this.teksharga_barang.getText(),
            this.teksjumlah.getText(),
            this.teksjtotal_harga.getText(),
            this.textdate.getText(),
           SelectedItemStr
            
    
        }); 
        totalharga();
    }//GEN-LAST:event_btnTambahdataActionPerformed


    private void teksnama_produkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_teksnama_produkActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_teksnama_produkActionPerformed

    private int getJumlahBarang(){
        int index = 0;
        for(int i = 0; i < this.jTablepembelian.getRowCount(); i++){
            index += Integer.parseInt(this.jTablepembelian.getValueAt(i, 5).toString());
        }
        return index;
    }
    
    private int getTotalHarga(){
        int index = 0;
        for(int i = 0; i < this.jTablepembelian.getRowCount(); i++){
            index += Integer.parseInt(this.jTablepembelian.getValueAt(i, 6).toString());
        }
        return index;
    }
    
    private boolean tfPenjualan(){
        try {
            Connection c = (Connection) koneksi.configDB();
            PreparedStatement p = c.prepareStatement("INSERT INTO tbl_penjualan (kd_penjualan, kd_karyawan, total_barang, total_harga)VALUES (?, ?, ?, ?)");
            p.setString(1, this.tekskd_pembelian.getText());
            p.setString(2, this.pilihkd_supplier.getSelectedItem().toString().substring(0, 6));
            p.setInt(3, this.getJumlahBarang());
            p.setInt(4, this.getTotalHarga());
           
            
            return p.executeUpdate() > 0;
            
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "error : " + ex.getMessage());
        }
        return false;
    }
    
    private void btnKonfirmasiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKonfirmasiActionPerformed
//        if(this.tfPenjualan()){
//            try {
//                for(int i = 0; i < this.jTablepembelian.getRowCount(); i++){
//                    Connection c = (Connection) koneksi.configDB();
//                    PreparedStatement p = c.prepareStatement("INSERT INTO detail_belibarang VALUES (?, ?, ?, ?)");
//                    p.setString(1, this.tekskd_pembelian.getText());
//                    p.setString(2, this.jTablepembelian.getValueAt(i, 2).toString());
//                    p.setString(3, this.jTablepembelian.getValueAt(i, 5).toString());
//                    p.setString(4, this.jTablepembelian.getValueAt(i, 6).toString());
//                    p.executeUpdate();
//                }
//                JOptionPane.showConfirmDialog(this, "Transaksi Berhasil!");
//            } catch (SQLException ex) {
//                ex.printStackTrace();
//                JOptionPane.showMessageDialog(this, "error : " + ex.getMessage());
//            }
//        }
        
        String kdpembelian = this.createkd();
        System.out.println("KODE PEMBELIAN : " + this.tekskd_pembelian.getText());
         Object SelectedItem = pilihkd_supplier.getSelectedItem();
            String SelectedItemStr = SelectedItem.toString().substring(0,6);
        String totalharga = this.txtTotalHarga.getText();
        
        try {
            String sql ="INSERT INTO tbl_pembelian (kd_pembelian, kd_supplier, total_hargabarangbelii) VALUES (?,?,?)";
            System.out.println();
            Connection conn = (Connection ) koneksi.configDB();
            PreparedStatement pst = conn.prepareStatement(sql);
            
            pst.setString(1,kdpembelian);
            pst.setString(2,SelectedItemStr);
             pst.setInt(3,Integer.parseInt(totalharga));
             pst.executeUpdate();
             conn.close(); 
             JOptionPane.showMessageDialog(this, "Transaksi Berhasil");
             this.tekskd_pembelian.setText(this.createkd());
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "ERror :" +ex.getMessage());
        }
        
        for(int i = 0; i<jTablepembelian.getRowCount(); i++) {
            
          try{
              
              String namabarang = this.jTablepembelian.getValueAt(i, 1).toString();
              String hargabarang = this.jTablepembelian.getValueAt(i, 2).toString();
              String jumlahbarang = this.jTablepembelian.getValueAt(i, 3).toString();
              String forsql = "INSERT INTO detail_belibarang VALUES (?,?,?,?)";
               Connection conn = (Connection ) koneksi.configDB();
            PreparedStatement pst = conn.prepareStatement(forsql);
            
            pst.setString(1,kdpembelian);
            pst.setString(2,namabarang);
             pst.setInt(3,Integer.parseInt(hargabarang));
             pst.setInt(4,Integer.parseInt(jumlahbarang));
             pst.executeUpdate();
             conn.close();
             
          } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "ERror :" +ex.getMessage()); 
        }
    }//GEN-LAST:event_btnKonfirmasiActionPerformed

        //clear value of all field

        teksnama_produk.setText("");
        teksnama_produk.setText("");
        teksjumlah.setText("");
        teksjtotal_harga.setText("");
        teksharga_barang.setText("");
        //clear value of table
        DefaultTableModel model = (DefaultTableModel)this.jTablepembelian.getModel();
        int rowCount = model.getRowCount();
        for (int i= rowCount-1;i>=0; i--) {
            model.removeRow(i);
        }
    
        
    }
    private void jTablepembelianMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTablepembelianMouseClicked
        this.showData1();
    }//GEN-LAST:event_jTablepembelianMouseClicked

    private void btnHapus_penjualanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapus_penjualanActionPerformed
        // TODO add your handling code here:
         if(this.jTablepembelian.getSelectedRow() > -1){
            
                        // mendapatkan nama data yang akan dihapus
            String nama = this.jTablepembelian.getValueAt(this.jTablepembelian.getSelectedRow(), 1).toString();

            // memunculkan pop up konfirmasi penghapusan data
            int status = JOptionPane.showConfirmDialog(this, "Apakah anda yakin ingin menghapus " + nama + "?", "confirm", JOptionPane.YES_OPTION, JOptionPane.QUESTION_MESSAGE);

            // jika pada pop up yang ditekan bukan button 'yes' maka data tidak akan dihapus
            if(status != JOptionPane.YES_OPTION){
                return;
            }
           
            DefaultTableModel model = (DefaultTableModel) jTablepembelian.getModel();
            int row = jTablepembelian.getSelectedRow();
            model.removeRow(row);          
            this.updateHarga();
        }else{
           
        }
    }//GEN-LAST:event_btnHapus_penjualanActionPerformed

    private void pilihkd_supplierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pilihkd_supplierActionPerformed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_pilihkd_supplierActionPerformed

    private void tekskd_pembelianActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tekskd_pembelianActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tekskd_pembelianActionPerformed

    private void teksjtotal_hargaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_teksjtotal_hargaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_teksjtotal_hargaActionPerformed

    private void jButtontransaksi_penjualanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtontransaksi_penjualanActionPerformed
        // TODO add your handling code here:
         new JFrameTransaksi().setVisible(true);
        dispose();
        
    }//GEN-LAST:event_jButtontransaksi_penjualanActionPerformed

    private void butonpembelianActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butonpembelianActionPerformed
        // TODO add your handling code here:
         new JFramepembelian().setVisible(true);
        dispose();
    }//GEN-LAST:event_butonpembelianActionPerformed

    private void butonkembaliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butonkembaliActionPerformed
        // TODO add your handling code here:
         new JFrameDashboard().setVisible(true);
        dispose();
    }//GEN-LAST:event_butonkembaliActionPerformed

    private void btnMemberActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMemberActionPerformed
        new Member().setVisible(true);
        dispose();
    }//GEN-LAST:event_btnMemberActionPerformed

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
            java.util.logging.Logger.getLogger(JFramepembelian.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JFramepembelian.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JFramepembelian.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JFramepembelian.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new JFramepembelian().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnHapus_penjualan;
    private javax.swing.JButton btnKonfirmasi;
    private javax.swing.JButton btnMember;
    private javax.swing.JButton btnTambahdata;
    private javax.swing.JButton butonkembali;
    private javax.swing.JButton butonpembelian;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButtontransaksi_penjualan;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTablepembelian;
    private javax.swing.JComboBox pilihkd_supplier;
    private javax.swing.JTextField teksharga_barang;
    private javax.swing.JTextField teksjtotal_harga;
    private javax.swing.JTextField teksjumlah;
    private javax.swing.JTextField tekskd_pembelian;
    private javax.swing.JTextField teksnama_produk;
    private javax.swing.JLabel textdate;
    private javax.swing.JLabel txtTotalHarga;
    // End of variables declaration//GEN-END:variables
}
