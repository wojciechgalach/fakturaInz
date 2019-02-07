/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ekran;

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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import ustawienia.StawkiVat;

/**
 *
 * @author wojtek
 */
public class VatEdycja extends JDialog {

    VatEdycja(JFrame owner)
     {
       super (owner, "Edycja Stawek Vat", true); 
       setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        
       JLabel vatLabel = new JLabel ("Nazwa");
        vatTekstField = new JTextField(20);  
       JLabel vatStawkaLabel = new JLabel ("Vat");
        vatStawkaField = new JTextField(4);
        
       gridBag = new GridBagLayout();
       consolaPanel = new JPanel(gridBag);
       
       /**
        * dodaje linie do dodawania i edycji stawek Vat
        * w tym wypadku są dwie linie
        */
       dodajLinieWyswietlania(0, vatLabel, vatTekstField);
       dodajLinieWyswietlania(1, vatStawkaLabel, vatStawkaField);
       
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
                if (czyVatLiczba())
                 {
                  czyMoznaZmienic = true;
                 }
                dispose();                                                       // pozwala wyłaczyc kono zwalnia zasoby
             } 
       });
       
       anulujButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e) 
             {
//                 czyMoznaZmienic = false;
                 dispose();                                                       // pozwala wyłaczyc kono zwalnia zasoby
             }           
         });
       
       pack();
       setLocationRelativeTo(owner);       // zwraca polozenie okna poto aby pojawilo sie w srodku naszego okna a nie ekranu kompa
    }
        
    
    public boolean wczytajVat(StawkiVat vatTekst) 
     {
        double vat = vatTekst.getVat();
        
        /*
         * tal linia pozwoli wczytac to zmiennej wartosc vat a jak brak to przypisze zero (chyba)
         */
        String vatTekstemWyswietlony = vat ==0 ?"0":String.valueOf(vat);
       
        vatTekstField.setText(vatTekst.getVatNazwa());
        vatStawkaField.setText(vatTekstemWyswietlony);
    
        setVisible(true);     // wyświetlenie okna (bez tego okno sie nie pokarze)

        if (czyMoznaZmienic)
         {
           /*
            * Double.parseDouble zaminieni tekst na liczbę
            */
            vat = Double.parseDouble(vatStawkaField.getText());           
            
            vatTekst.setNazwaStawki(vatTekstField.getText());         
            vatTekst.setLiczbaStawki(vat);
            
//            czyMoznaZmienic = false;    //  zmieniam wartość tej zmiennej aby znowu sprawdzać czy vat to liczba  
         }
         
         
         return czyMoznaZmienic;
     }
    
    
    /*
     * sprawdzam czy vat jest liczbą jeśli tak to można dokonać zmian jesli nie to niepozwoli
     */
    private boolean czyVatLiczba() {       // sprawdza poprawnosc danych jak liczba to ok
        try 
         {
            double spraw;
            spraw = Double.parseDouble(vatStawkaField.getText());
            if (spraw >= 0 && spraw <100)
             {
             }
            else
             {
              JOptionPane.showMessageDialog(this, "vat musi być liczbą! z zakresu 0-100", "ERROR",
                                          JOptionPane.ERROR_MESSAGE);
              vatStawkaField.requestFocusInWindow();
              return false;
                
             }
          } catch (NumberFormatException e) 
          {
            JOptionPane.showMessageDialog(this, "vat musi być liczbą!", "ERROR",
                                          JOptionPane.ERROR_MESSAGE);
            vatStawkaField.requestFocusInWindow();
            return false;
          }
        return true;
    }
    
    /**
     * metoda kóra wyswietla i robi układ do zaprezentowania danych
     * co dzie ma być pokazane
     * @param i
     * @param stawki
     * @param stawkiTekstField 
     */
    private void dodajLinieWyswietlania(int i, JComponent stawki, JComponent stawki1) {
        
        GridBagConstraints c = new GridBagConstraints();
    
        c.gridy = i;     // okresla polozenie okienek edycji wyswietlanych  0 najwyzej  a potem w dół
        c.anchor = GridBagConstraints.LINE_START;   // anchor mówi gdzie ma zacząc wyswietlac linie

        // pierwszy rząd danych do wyświetlania
        c.gridx = 0;
        c.insets = new Insets(5, 5, 0, 5);
        gridBag.setConstraints(stawki, c);
        consolaPanel.add(stawki);

        // drugi rząd danych do wyświetlania
        c.gridx = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1.0;
        c.insets = new Insets(5, 5, 0, 5);
        gridBag.setConstraints(stawki1, c);
        consolaPanel.add(stawki1);   
    }  

   private GridBagLayout gridBag;
   private JPanel consolaPanel;
   private final JButton okButton;
   private final JButton anulujButton;
   private boolean czyMoznaZmienic; 
   private final JTextField vatTekstField;
   private final JTextField vatStawkaField;

}