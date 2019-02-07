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
public class FakturaNowaEkranEdytuj extends JDialog {

    
    public FakturaNowaEkranEdytuj(JFrame owner)      
    {
        super (owner, "Dodawanie Towarow do faktury", true);     
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.setSize(600, 400);                                             // Podajemy rozmiary okna                      
         
        JLabel towarLabel = new JLabel ("Towar");       
        JLabel towarIloscLabel = new JLabel ("ilość");
        
        towariloscField = new JTextField(4);
              
        gridBag = new GridBagLayout();
        consolaPanel = new JPanel(gridBag);
        
        comboBoxDodawaniaTowarow = new JComboBox();
        for (int j=0; j<=TowarEkran.listaTowar.size()-1; j++)
         {
            comboBoxDodawaniaTowarow.addItem(TowarEkran.listaTowar.get(j));    
         }       
       
        dodajLinieWyswietlania(0, towarLabel, comboBoxDodawaniaTowarow);
        dodajLinieWyswietlania(1, towarIloscLabel, towariloscField);
        
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

                if (czyIloscLiczba())
                 {
                  czyMoznaZmienic = true;
                 }
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
   
    

      boolean wczytajTowar(Towar naszTowar) 
       {         
        czyMoznaZmienic = false;
        setVisible(true);     // wyświetlenie okna (bez tego okno sie nie pokarze)
      
        if (czyMoznaZmienic)
         {
            idWybranegoTowaru(comboBoxDodawaniaTowarow.getSelectedIndex());
            idWybranegoTowaru = comboBoxDodawaniaTowarow.getSelectedIndex();
   
            Towar t1 = (Towar) TowarEkran.listaTowar.get(comboBoxDodawaniaTowarow.getSelectedIndex());
            naszTowar.setNazwa(t1.getNazwa());
            naszTowar.setCena(t1.getCena());
            naszTowar.stawkiVat = t1.getStawkaVat();
            naszTowar.setVat2(t1.getStawkaVat().getVat());
            naszTowar.setJednostka(t1.getJednostka());
            _towar = naszTowar;
          }
         return czyMoznaZmienic;
    }
        
    
    private boolean czyIloscLiczba() {       // sprawdza poprawnosc danych jak liczba to ok
    try {
         wybrana_ilosc = Double.parseDouble(towariloscField.getText());
        } catch (NumberFormatException e) 
        {
         JOptionPane.showMessageDialog(this, "Ilość musi być liczbą (separator dziesiętnych to kropka)!", "ERROR",
         JOptionPane.ERROR_MESSAGE);
         towariloscField.requestFocusInWindow();
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

    public void idWybranegoTowaru(int selectedIndex) 
     {
        idWybranegoTowaru = selectedIndex;
     }
    

    private final JComboBox comboBoxDodawaniaTowarow;
    private final JTextField towariloscField;
    private final GridBagLayout gridBag;
    private final JPanel consolaPanel;
    private final JButton okButton;
    private final JButton anulujButton;
    private boolean czyMoznaZmienic;
    public  int idWybranegoTowaru;
    public Double wybrana_ilosc = 0.0;
    public Towar _towar;  
    
}

    