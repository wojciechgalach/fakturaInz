/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ekran;

import faktury.Klienci;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

/**
 *
 * @author wojtek
 */
public class KlienciEdycja extends JDialog {
 

    KlienciEdycja(JFrame owner) {
        
        super (owner, "Edycja Klientow", true);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
       
       JLabel nazwaLabel = new JLabel ("Nazwa");
        klientTekstField = new JTextField(50);  
       JLabel ulicaLabel = new JLabel ("Ulica");
        klientUlicaField = new JTextField(30);
       JLabel ulicaNRLabel = new JLabel ("Numier Ulicy");
        nrUlicyField = new JTextField(5);
       JLabel mieszkanieNRLabel = new JLabel ("Numier mieszkania");
        nrMieszkaniaField = new JTextField(5);
       JLabel miastoLabel = new JLabel ("Miasto");  
        miastoField = new JTextField(20);
       JLabel kodLabel = new JLabel ("Kod");  
        kodField = new JTextField(20); 
       JLabel nipLabel = new JLabel ("NIP");  
        nipField = new JTextField(20); 
       JLabel krajLabel = new JLabel ("Kraj");  
        krajField = new JTextField(20); 
           
       gridBag = new GridBagLayout();
       consolaPanel = new JPanel(gridBag);
       
       /**
        * dodaje linie do wyswietlania wszystkiego o kliencie
        */
       dodajLinieWyswietlania(0, nazwaLabel, klientTekstField);
       dodajLinieWyswietlania(1, ulicaLabel, klientUlicaField);
       dodajLinieWyswietlania(3, ulicaNRLabel, nrUlicyField);
       dodajLinieWyswietlania(4, mieszkanieNRLabel, nrMieszkaniaField);
       dodajLinieWyswietlania(5, miastoLabel, miastoField);
       dodajLinieWyswietlania(6, kodLabel, kodField);
       dodajLinieWyswietlania(7, nipLabel, nipField);
       dodajLinieWyswietlania(8, krajLabel, krajField);
       
       
       /**
        * dodaje przyciski
        */
       okButton = new JButton("OK");
       anulujButton = new JButton("Anuluj");
             
       /**
        * dodaje panel buttonów 
        */
       JPanel panelPrzyciskow = new JPanel(new FlowLayout());
       
       /**
        * dodajemy przyciski do panelu
        */
       panelPrzyciskow.add(okButton);
       panelPrzyciskow.add(anulujButton);
       
       getContentPane().add(consolaPanel, BorderLayout.CENTER);
       getContentPane().add(panelPrzyciskow, BorderLayout.SOUTH);
       
       /**
        * słuchacze wciśnieńcia przycisków
        */
       okButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) 
             {
             /*
              * tu sprawdzam warunek czy vat to liczba jesli true to wchodzi do if i pozwala na zmiane
              */
              czyMoznaZmienic = true;   
              dispose();                                                       // pozwala wyłaczyc kono zwalnia zasoby
             }   
       });
       
       anulujButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) 
             {
                dispose();                                                       // pozwala wyłaczyc kono zwalnia zasoby
             }           
       });
       
       pack();    
       setLocationRelativeTo(owner);       // zwraca polozenie okna poto aby pojawilo sie w srodku naszego okna a nie ekranu kompa       
    }
    
 
       boolean wczytajklient(Klienci klient) 
        { 
    
        czyMoznaZmienic = false;
       
        klientTekstField.setText(klient.getNazwaFirmy());
        klientUlicaField.setText(klient.getUlica());
        nrUlicyField.setText(klient.getNrUlicy());
        nrMieszkaniaField.setText(klient.getNrMieszkania());
        miastoField.setText(klient.getMiasto());
        kodField.setText(klient.getKodPocztowy());
        nipField.setText(klient.getNIP());
        krajField.setText(klient.getKraj());
                    
        setVisible(true);     // wyświetlenie okna (bez tego okno sie nie pokarze) 
       
        if (czyMoznaZmienic)
         {
            klient.setNazwaFirmy(klientTekstField.getText());         
            klient.setUlica(klientUlicaField.getText());
            klient.setNrUlicy(nrUlicyField.getText());
            klient.setNrMieszkania(nrMieszkaniaField.getText());
            klient.setMiasto(miastoField.getText());
            klient.setKodPocztowy(kodField.getText());
            klient.setNIP(nipField.getText());
            klient.setKraj(krajField.getText());
         }
        
         return czyMoznaZmienic;    
        }    
    
     private void dodajLinieWyswietlania(int i, JComponent kolumna1, JComponent kolumna2) 
      {
       GridBagConstraints c = new GridBagConstraints();
        c.gridy = i;     // okresla polozenie okienek edycji wyswietlanych  0 najwyzej  a potem w dół
        c.anchor = GridBagConstraints.LINE_START;   // anchor mówi gdzie ma zacząc wyswietlac linie

        // pierwszy rząd danych do wyświetlania
        c.gridx = 0;
        c.insets = new Insets(5, 5, 0, 5);
        gridBag.setConstraints(kolumna1, c);
        consolaPanel.add(kolumna1);

        // drugi rząd danych do wyświetlania
        c.gridx = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1.0;
        c.insets = new Insets(5, 5, 0, 5);
        gridBag.setConstraints(kolumna2, c);
        consolaPanel.add(kolumna2);
    }
    
    
   private GridBagLayout gridBag;
   private JPanel consolaPanel;
   private final JButton okButton;
   private final JButton anulujButton;
   private boolean czyMoznaZmienic; 
   private final JTextField klientTekstField;
   private final JTextField klientUlicaField;
   private final JTextField nrUlicyField;
   private final JTextField miastoField;
   private final JTextField kodField;
   private final JTextField nipField;
   private final JTextField krajField;
   private final JTextField nrMieszkaniaField; 
}
