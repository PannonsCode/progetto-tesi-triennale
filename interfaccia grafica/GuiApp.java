/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spedizioneautomatizzata;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.sql.SQLException;
import javafx.scene.layout.Border;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
/**
 *
 * @author mattiapannone
 */
public class GuiApp extends JFrame{
    
    //elementi gui
    final JPanel nord = new JPanel();
    final JPanel centro = new JPanel();
    final JPanel sud = new JPanel();
    final JPanel stanze = new JPanel();
    final JPanel robot = new JPanel();
    final JPanel pulsanteSpedisci = new JPanel();
    final JPanel pulsanteRichiediSped=new JPanel();
    
    final JTextPane stanzeOnline = new JTextPane();
    final JTextPane robotOnline = new JTextPane();
    
    final JLabel titolo = new JLabel("SPEDIZIONE AUTOMATIZZATA");
    final JLabel infoStanze = new JLabel("Stanze disponibili al servizio:");
    final JLabel infoRobot= new JLabel("Robot online per il servizio");
    final JButton spedCentrale = new JButton("Spedisci da centrale");
    final JButton spedRichiesta = new JButton("Richiedi spedizione");
    final GridLayout griglia = new GridLayout(2,2);
    
    //elementi db
    final DatabaseApp db;
    String queryRes = null;
    
    //elementi listener
    final GuiAppListener ascoltatore = new GuiAppListener(this);
    
    public GuiApp() throws ClassNotFoundException, SQLException{
        
        super("Spedizione automatizzata");
        this.db = new DatabaseApp();
        this.getContentPane().setLayout(new BorderLayout());
        
        //pannello titolo
        titolo.setForeground(Color.black);
        titolo.setFont(new java.awt.Font("Times New Roman", 1, 50));
        nord.setBackground(Color.red);
        nord.add(titolo);
        
        //pannello info stanze
        centro.setLayout(griglia);
        infoStanze.setLayout(new FlowLayout());
        infoStanze.setFont(new java.awt.Font("Arial", 1, 20));
        centro.add(infoStanze);
        
        //pannello info robot
        infoRobot.setLayout(new FlowLayout());
        infoRobot.setFont(new java.awt.Font("Arial", 1, 20));
        centro.add(infoRobot);
        
        //interrohazione al db e stampa stanze
        this.db.res=this.db.stmt.executeQuery("SELECT nome,codice FROM stanza WHERE online=1");
        this.db.res.next();
        queryRes=this.db.res.getString("nome")+"     ->  codice:   "+this.db.res.getString("codice");
        queryRes+="\n";
        while(this.db.res.next()){
            queryRes+=this.db.res.getString("nome")+"     ->   codice:  "+this.db.res.getString("codice");
            queryRes+="\n";
        }
        
        stanzeOnline.setFont(new java.awt.Font("Arial", 1, 15));
        stanzeOnline.setText(queryRes);
        stanzeOnline.setEditable(false);
        stanzeOnline.setSize(250, 500);
        centro.add(stanzeOnline);
        
        //interrogazione al db e stampa robot
        this.db.res=this.db.stmt.executeQuery("SELECT nome,codice FROM robot WHERE online=1");
        queryRes = null;
        this.db.res.next();
        queryRes=this.db.res.getString("nome")+"     ->  codice:   "+this.db.res.getString("codice");
        queryRes+="\n";
        while(this.db.res.next()){
            queryRes+=this.db.res.getString("nome")+"     ->   codice:  "+this.db.res.getString("codice");
            queryRes+="\n";
        }
        
        robotOnline.setFont(new java.awt.Font("Arial", 1, 15));
        robotOnline.setText(queryRes);
        robotOnline.setEditable(false);
        robotOnline.setSize(250, 500);
        centro.add(robotOnline);
        
        //pannello con pulsanti opzioni
        sud.setBackground(Color.red);
        sud.setLayout(new GridLayout(1,2));
        spedCentrale.setFont(new java.awt.Font("Arial", 1, 25));
        pulsanteSpedisci.add(spedCentrale);
        spedRichiesta.setFont(new java.awt.Font("Arial", 1, 25));
        pulsanteRichiediSped.add(spedRichiesta);
        sud.add(pulsanteSpedisci);
        sud.add(pulsanteRichiediSped);
        
        //inizializzazione pulsanti
        this.spedCentrale.setEnabled(true);
        this.spedCentrale.addActionListener(ascoltatore);
        this.spedCentrale.setActionCommand(GuiAppListener.CENTRALE);
        this.spedRichiesta.setEnabled(true);
        this.spedRichiesta.addActionListener(ascoltatore);
        this.spedRichiesta.setActionCommand(GuiAppListener.RICHIESTA);
        
        //inscatolamento finale
        this.getContentPane().add(nord, BorderLayout.NORTH);
        this.getContentPane().add(centro, BorderLayout.CENTER);
        this.getContentPane().add(sud, BorderLayout.SOUTH);
        
        //opzioni gui in generale per il pannello finale
        this.setLocation(300, 200);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
    }
}
