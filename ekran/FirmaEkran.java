/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ekran;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import ustawienia.Firma;

/**
 *
 * @author wojtek
 */
public class FirmaEkran extends JFrame implements Serializable {
    
    public FirmaEkran()
    {
        super("Ekran wprowadzania danych Firmy");
        
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);    // wyłaczy okno edycji ale nie cały program
        setLocationRelativeTo(null);
;
        File f = new File("firma.txt");
        if(f.exists()) 
         { 
          try{
            ObjectInputStream in = new ObjectInputStream(new FileInputStream("firma.txt"));
                try 
                 {
                    firma = (Firma) in.readObject();
                 } catch (ClassNotFoundException ex) 
                 {
                    Logger.getLogger(TowarEkran.class.getName()).log(Level.SEVERE, null, ex);
                 }
              in.close();
             } catch (IOException ex) {
                    Logger.getLogger(TowarEkran.class.getName()).log(Level.SEVERE, null, ex);
                }
        }else
         {
        System.out.println("nie znalazlem pliku firma");
         firma = new Firma();
         try 
          {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("firma.txt"));
            out.writeObject(firma);
            out.close();
          } catch (IOException ex) 
          {
            Logger.getLogger(TowarEkran.class.getName()).log(Level.SEVERE, null, ex);
          }
        }
        
        JLabel nazwaLabel = new JLabel ("Nazwa");
        klientTekstField = new JTextField(firma.getNazwaFirmy());  
         klientTekstField.setEditable(false);
        JLabel ulicaLabel = new JLabel ("Ulica");
        klientUlicaField = new JTextField(firma.getUlica());
         klientUlicaField.setEditable(false);
        JLabel ulicaNRLabel = new JLabel ("Numier Ulicy");
        nrUlicyField = new JTextField(firma.getNrUlicy());
         nrUlicyField.setEditable(false);
        JLabel mieszkanieNRLabel = new JLabel ("Numier mieszkania");
        nrMieszkaniaField = new JTextField(firma.getNrMieszkania());
         nrMieszkaniaField.setEditable(false);
        JLabel miastoLabel = new JLabel ("Miasto");  
        miastoField = new JTextField(firma.getMiasto());
         miastoField.setEditable(false);
        JLabel kodLabel = new JLabel ("Kod");  
        kodField = new JTextField(firma.getKodPocztowy()); 
         kodField.setEditable(false); 
        JLabel nipLabel = new JLabel ("NIP");  
        nipField = new JTextField(firma.getNIP()); 
         nipField.setEditable(false);
        JLabel krajLabel = new JLabel ("Kraj");  
        krajField = new JTextField(firma.getKraj()); 
         krajField.setEditable(false);
        JLabel nrKontaLabel = new JLabel ("Nr Kona");  
        nrKontaField = new JTextField(firma.getNrKontaBankowego()); 
         nrKontaField.setEditable(false);
         
        edytujButton = new JButton("edytuj");
        zapiszButton = new JButton("zapisz");
        anulujButton = new JButton("anuluj");
        zapiszButton.setEnabled(true);
        anulujButton.setEnabled(false);
       
        gridBag = new GridBagLayout();
        panelDanych = new JPanel(gridBag);
     
        JPanel panelPrzyciskow = new JPanel(new GridBagLayout());
 
       /**
        * dodaje linie do wyswietlania wszystkiego o firmie
        */
       dodajLinieWyswietlania(0, nazwaLabel, klientTekstField);
       dodajLinieWyswietlania(1, ulicaLabel, klientUlicaField);
       dodajLinieWyswietlania(3, ulicaNRLabel, nrUlicyField);
       dodajLinieWyswietlania(4, mieszkanieNRLabel, nrMieszkaniaField);
       dodajLinieWyswietlania(5, miastoLabel, miastoField);
       dodajLinieWyswietlania(6, kodLabel, kodField);
       dodajLinieWyswietlania(7, nipLabel, nipField);
       dodajLinieWyswietlania(8, nrKontaLabel, nrKontaField);
       dodajLinieWyswietlania(9, krajLabel, krajField);
  
       panelPrzyciskow.add(edytujButton); 
       panelPrzyciskow.add(zapiszButton); 
       panelPrzyciskow.add(anulujButton);
       
       getContentPane().add(panelDanych, BorderLayout.CENTER);
       getContentPane().add(panelPrzyciskow, BorderLayout.SOUTH);
        
        
       /**
        * słuchacze wciśnieńcia przycisków
        */
       edytujButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
              zrobEdytuj();
            }
        });
       
       zapiszButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) 
             {
               zrobZapisz();
               zrobNieEdytuj();
             }
        });
       
        anulujButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) 
             {
               zrobNieEdytuj();
               dispose();
             }
        });
       
      pack();
    }
    
    private void zrobZapisz()
    {
         firma.setNazwaFirmy(klientTekstField.getText());         
         firma.setUlica(klientUlicaField.getText());
         firma.setNrUlicy(nrUlicyField.getText());
         firma.setNrMieszkania(nrMieszkaniaField.getText());
         firma.setMiasto(miastoField.getText());
         firma.setKodPocztowy(kodField.getText());
         firma.setNIP(nipField.getText());
         firma.setKraj(krajField.getText());
         firma.setNrKontaBankowego(nrKontaField.getText());

         try 
         {
          ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("firma.txt"));
          out.writeObject(firma);
          out.close();
         } catch (IOException ex) 
         {
          Logger.getLogger(TowarEkran.class.getName()).log(Level.SEVERE, null, ex);
         }        
}
    
    private void zrobEdytuj()
     {
          klientTekstField.setEditable(true);
          klientUlicaField.setEditable(true);
          nrUlicyField.setEditable(true);
          nrMieszkaniaField.setEditable(true);
          miastoField.setEditable(true);
          kodField.setEditable(true);
          nipField.setEditable(true);
          krajField.setEditable(true);
          nrKontaField.setEditable(true);
          zapiszButton.setEnabled(true);
          anulujButton.setEnabled(true);
     }
    
     private void zrobNieEdytuj()
      {
         klientTekstField.setEditable(false);
         klientUlicaField.setEditable(false);
         nrUlicyField.setEditable(false);
         nrMieszkaniaField.setEditable(false);
         miastoField.setEditable(false);
         kodField.setEditable(false);
         nipField.setEditable(false);
         krajField.setEditable(false);
         nrKontaField.setEditable(false);
         zapiszButton.setEnabled(false);
         anulujButton.setEnabled(false);
      }
    
      private void dodajLinieWyswietlania(int i, JLabel kolumna1, JTextField kolumna2) 
       {
        GridBagConstraints c = new GridBagConstraints();
 
        c.gridy = i;     // okresla polozenie okienek edycji wyswietlanych  0 najwyzej  a potem w dół
        c.anchor = GridBagConstraints.LINE_START;   // anchor mówi gdzie ma zacząc wyswietlac linie

        // pierwszy rząd danych do wyświetlania
        c.gridx = 0;
        c.insets = new Insets(5, 5, 0, 5);
        gridBag.setConstraints(kolumna1, c);
        panelDanych.add(kolumna1);

        // drugi rząd danych do wyświetlania
        c.gridx = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1.0;
        c.insets = new Insets(5, 5, 0, 5);
        gridBag.setConstraints(kolumna2, c);
        panelDanych.add(kolumna2);
        
    }   
      
    
    public static Firma firma;
    private GridBagLayout gridBag;
    private JPanel panelDanych;
    private boolean czyMoznaZmienic; 
    private final JTextField klientTekstField;
    private final JTextField klientUlicaField;
    private final JTextField nrUlicyField;
    private final JTextField miastoField;
    private final JTextField kodField;
    private final JTextField nipField;
    private final JTextField krajField;
    private final JTextField nrMieszkaniaField;
    private final JTextField nrKontaField;
    
    private final JButton edytujButton;
    private final JButton zapiszButton;
    private final JButton anulujButton;

    
}
