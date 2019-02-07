/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ekran;

import faktury.Towar;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

/**
 *
 * @author wojtek
 */
public class TowarEdycja extends JDialog  {


    TowarEdycja(JFrame owner) 
     {
       super (owner, "Edycja Towarów", true);
       setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
       
       JLabel towarLabel = new JLabel ("Nazwa");
        towarTekstField = new JTextField(20);
       JLabel towarCenaLabel = new JLabel ("cena");
        towarCenaField = new JTextField(4);
       JLabel towarVatLabel = new JLabel ("VAT");
       JLabel towarJednostkaLabel = new JLabel ("Jednostka");
        
       gridBag = new GridBagLayout();
       consolaPanel = new JPanel(gridBag);
   
       comboBoxVat = new JComboBox();
       comboBoxJednostka = new JComboBox();
     
       for (int j=0; j<=VatEkran.listaVat.size()-1; j++)
        {
            comboBoxVat.addItem(VatEkran.listaVat.get(j));    
        }
       
       for (int j=0; j<=JednostkiEkran.listaJednostek.size()-1; j++)
        {
            comboBoxJednostka.addItem(JednostkiEkran.listaJednostek.get(j));
        }
        
       /**
        * dodaje linie do dodawania i edycji stawek Vat
        * w tym wypadku są dwie linie
        */
       dodajLinieWyswietlania(0, towarLabel, towarTekstField);
       dodajLinieWyswietlania(1, towarCenaLabel, towarCenaField);
       dodajLinieWyswietlania(2, towarVatLabel, comboBoxVat);
       dodajLinieWyswietlania(3, towarJednostkaLabel, comboBoxJednostka);
       
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
                 * tu sprawdzam warunek czy cena to liczba jesli true to wchodzi do if i pozwala na zmiane
                 */
                if (czyCenaLiczba())
                {
                czyMoznaZmienic = true;
                }
                dispose();                                                       // pozwala wyłaczyc kono zwalnia zasoby
             }           
       });
       
       anulujButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();                                                       // pozwala wyłaczyc kono zwalnia zasoby
            }           
        });
       
       pack();
       setLocationRelativeTo(owner);       // zwraca polozenie okna poto aby pojawilo sie w srodku naszego okna a nie ekranu kompa
    }

    
    
    
    boolean wczytajTowar(Towar naszTowar) 
     {         
       double cena = naszTowar.getCena();
        
        /*
         * tal linia pozwoli wczytac to zmiennej wartosc vat a jak brak to przypisze zero (chyba)
         */
       String cenaTekstemWyswietlona = cena ==0 ?"0":String.valueOf(cena);
        
       czyMoznaZmienic = false;
       towarTekstField.setText(naszTowar.getNazwa());
       towarCenaField.setText(cenaTekstemWyswietlona);
       setVisible(true);     // wyświetlenie okna (bez tego okno sie nie pokarze)
      
        if (czyMoznaZmienic)
         {
           /*
            * Double.parseDouble zaminieni tekst na liczbę
            */
            cena = Double.parseDouble(towarCenaField.getText());                      
            naszTowar.setNazwa(towarTekstField.getText());         
            naszTowar.setCena(cena);    
            naszTowar.setVat(comboBoxVat.getSelectedIndex());
            naszTowar.setJednostka(comboBoxJednostka.getSelectedIndex());
         }
        return czyMoznaZmienic;    
    }
    
    /*
     * sprawdzam czy cena jest liczbą jeśli tak to można dokonać zmian jesli nie to niepozwoli
     */
    private boolean czyCenaLiczba() {       // sprawdza poprawnosc danych jak liczba to ok
        try 
         {
            Double.parseDouble(towarCenaField.getText());
         } catch (NumberFormatException e) 
         {
            JOptionPane.showMessageDialog(this, "Cena musi być liczbą!", "ERROR",
                                                      JOptionPane.ERROR_MESSAGE);
            towarCenaField.requestFocusInWindow();
            return false;
         }
        return true;
    }
    

    private void dodajLinieWyswietlania(int i, JComponent stawki, JComponent stawki1) 
     {
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
    
    
    private final GridBagLayout gridBag;
    private final JPanel consolaPanel;
    private final JTextField towarTekstField;
    private final JTextField towarCenaField;
    private final JButton okButton;
    private final JButton anulujButton;
    private boolean czyMoznaZmienic;
    JComboBox comboBoxVat = new JComboBox();
    JComboBox comboBoxJednostka = new JComboBox();
}
