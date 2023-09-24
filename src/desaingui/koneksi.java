/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package desaingui;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;
/**
 *
 * @author LENOVO
 */
public class koneksi {
    
    private static Connection mysqlconfig;
    
    
    public static Connection configDB() throws SQLException {
        try {
            String url = "jdbc:mysql://localhost:3306/kasir_umkm";
            String user = "root";
            String pass = "";
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            mysqlconfig = DriverManager.getConnection(url, user, pass);
            System.out.println("Koneksi berhasil;");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error : " + ex.getMessage());
        }
        return mysqlconfig;
    }
    public static void main(String[] args) {
        try {
            koneksi.configDB();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
