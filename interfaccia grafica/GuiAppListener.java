/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spedizioneautomatizzata;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import static java.lang.Integer.parseInt;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
/**
 *
 * @author mattiapannone
 */
public class GuiAppListener implements ActionListener {
    
    public static GuiApp display;
    public final static String CENTRALE="centrale";
    public final static String RICHIESTA="richiesta";
    String stanza;
    String stanza2;
    String robot;
    String nome, descrizione;
    String ip;
    int port;
    int coord_x,coord_y;
    int coord_x2,coord_y2;
    
    public GuiAppListener(GuiApp d){
        GuiAppListener.display=d;
    }

    @Override
    public void actionPerformed(ActionEvent e){
        
        if(e.getActionCommand().equals(CENTRALE)){
            try {
                stanza = JOptionPane.showInputDialog("Inserisci codice stanza dove vuoi consegnare la spedizione");
                robot  = JOptionPane.showInputDialog("Inserisci codice robot da usare");
                nome  = JOptionPane.showInputDialog("Inserisci il tuo nome");
                descrizione = JOptionPane.showInputDialog("Inserisci descrizione");
                
                display.db.res = display.db.stmt.executeQuery("SELECT coord_x,coord_y FROM stanza where codice="+this.stanza);
                display.db.res.next();
                coord_x = display.db.res.getInt("coord_x");
                coord_y = display.db.res.getInt("coord_y");
                //JOptionPane.showMessageDialog(display, "Hai inserito ("+coord_x+","+coord_y+")");
                
                display.db.res = display.db.stmt.executeQuery("SELECT ip_address,porta FROM robot where codice="+this.robot);
                display.db.res.next();
                ip = display.db.res.getString("ip_address");
                port = display.db.res.getInt("porta");
                //JOptionPane.showMessageDialog(display, "Hai inserito ("+ip+","+port+")");
              
            } catch (SQLException ex) {
                Logger.getLogger(GuiAppListener.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            try {
                SocketApp socket = new SocketApp(ip,port,coord_x,coord_y,0);
                JOptionPane.showMessageDialog(display, "Inviato con successo");
            } catch (IOException ex) {
                Logger.getLogger(GuiAppListener.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        if(e.getActionCommand().equals(RICHIESTA)){
            try {
                stanza = JOptionPane.showInputDialog("Inserisci codice stanza dove vuoi ritirare la spedizione");
                stanza2 = JOptionPane.showInputDialog("Inserisci codice stanza dove vuoi consegnare la spedizione");
                robot  = JOptionPane.showInputDialog("Inserisci codice robot da usare");
                nome  = JOptionPane.showInputDialog("Inserisci il tuo nome");
                descrizione = JOptionPane.showInputDialog("Inserisci descrizione");
                
                display.db.res = display.db.stmt.executeQuery("SELECT coord_x,coord_y FROM stanza where codice="+this.stanza);
                display.db.res.next();
                coord_x = display.db.res.getInt("coord_x");
                coord_y = display.db.res.getInt("coord_y");
                //JOptionPane.showMessageDialog(display, "Hai inserito ("+coord_x+","+coord_y+")");
                
                display.db.res = display.db.stmt.executeQuery("SELECT coord_x,coord_y FROM stanza where codice="+this.stanza);
                display.db.res.next();
                coord_x2 = display.db.res.getInt("coord_x");
                coord_y2 = display.db.res.getInt("coord_y");
                //JOptionPane.showMessageDialog(display, "Hai inserito ("+coord_x+","+coord_y+")");
                
                display.db.res = display.db.stmt.executeQuery("SELECT ip_address,porta FROM robot where codice="+this.robot);
                display.db.res.next();
                ip = display.db.res.getString("ip_address");
                port = display.db.res.getInt("porta");
                //JOptionPane.showMessageDialog(display, "Hai inserito ("+ip+","+port+")");
              
            } catch (SQLException ex) {
                Logger.getLogger(GuiAppListener.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            try {
                SocketApp socket = new SocketApp(ip,port,coord_x,coord_y,coord_x2,coord_y2,1);
                JOptionPane.showMessageDialog(display, "Inviato con successo");
            } catch (IOException ex) {
                Logger.getLogger(GuiAppListener.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }
    
}
