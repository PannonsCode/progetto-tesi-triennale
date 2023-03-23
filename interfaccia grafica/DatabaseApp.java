/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spedizioneautomatizzata;
import java.sql.*;
import java.io.*;
import javax.swing.JOptionPane;

/**
 *
 * @author mattiapannone
 */
public class DatabaseApp {
    
    public static GuiApp display;
    public final String IP_DB = "192.168.1.171";
    public final String PORT_DB = "3306";
    ResultSet res;
    Statement stmt;
    Connection conn;
    
    public DatabaseApp() throws ClassNotFoundException, SQLException{
        Class.forName("com.mysql.cj.jdbc.Driver");
        String url = "jdbc:mysql://"+IP_DB+":"+PORT_DB+"/appspedizioni";
        String usr = "app";
        String pwd = "app"; 
        
        try{
            conn = DriverManager.getConnection(url,usr,pwd);
            System.out.println("Connessione riuscita"); 
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(display,"Si è verificata la seguente eccezione:\n"
                                +e+"\nInformare l'amministratore di rete");
        }
        
        stmt = conn.createStatement();
    }
}
