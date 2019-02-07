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
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import ustawienia.Jednostki;

/**
 *
 * @author wojtek
 */
public class JednostkiEdycja extends JDialog {
    

    public JednostkiEdycja(JFrame owner)
    {
       super (owner, "Edycja Jednostek", true);
       setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
       
       JLabel jednostakLabel = new JLabel ("Jednostka");
       jednostakTekstField = new JTextField(20);   
        
       gridBag = new GridBagLayout();
       consolaPanel = new JPanel(gridBag);
       
       /**
        * dodaje linie do dodawania i edycji Jednostek 
        * w tym wypadku linia jest jedna
        */
       dodajLinieWyswietlania(0, jednostakLabel, jednostakTekstField);
       
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
        
    
    public boolean wczytajJednostki(Jednostki jednostka) {
        
        jednostakTekstField.setText(jednostka.getNazwaJednostki());      
        setVisible(true);     // wyświetlenie okna (bez tego okno sie nie pokarze)
        
        if (czyMoznaZmienic)
         {
            jednostka.setNazwaJednostki(jednostakTekstField.getText());
         }     
        return czyMoznaZmienic;    
    }
    
    /**
     * metoda kóra wyswietla i robi układ do zaprezentowania danych
     * co dzie ma być pokazane
     * @param i
     * @param jednostakLabel
     * @param jednostakTekstField 
     */
    private void dodajLinieWyswietlania(int i, JComponent jednostakLabel, JComponent jednostakTekstField) {
        
        GridBagConstraints c = new GridBagConstraints();
     
        c.gridy = i;     // okresla polozenie okienek edycji wyswietlanych  0 najwyzej  a potem w dół
        c.anchor = GridBagConstraints.LINE_START;   // anchor mówi gdzie ma zacząc wyswietlac linie

        // pierwszy rząd danych do wyświetlania
        c.gridx = 0;
        c.insets = new Insets(5, 5, 0, 5);
        gridBag.setConstraints(jednostakLabel, c);
        consolaPanel.add(jednostakLabel);

        // drugi rząd danych do wyświetlania
        c.gridx = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1.0;
        c.insets = new Insets(5, 5, 0, 5);
        gridBag.setConstraints(jednostakTekstField, c);
        consolaPanel.add(jednostakTekstField); 
    }
    
   private JTextField jednostakTekstField;
   private GridBagLayout gridBag;
   private JPanel consolaPanel;
   private final JButton okButton;
   private final JButton anulujButton;
   private boolean czyMoznaZmienic; 


}
