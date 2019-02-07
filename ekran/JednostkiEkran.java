/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ekran;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.WindowConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import ustawienia.Jednostki;

/**
 *
 * @author wojtek
 */
public class JednostkiEkran extends JFrame implements Serializable{

    public JednostkiEkran() {
        super("Jednostki");
        
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);    // wyłaczy okno edycji ale nie cały program
        setSize(400, 300);
        setLocationRelativeTo(null);   // spradzić ale usawia okno w srodku

        File f = new File("jednostki.txt");
        if(f.exists()) 
         {
          try
           {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream("jednostki.txt"));
            try 
             {        
              listaJednostek = (ArrayList<Jednostki>) in.readObject();
             } catch (ClassNotFoundException ex) 
             {
               Logger.getLogger(JednostkiEkran.class.getName()).log(Level.SEVERE, null, ex);
             }
            in.close();
           } catch (IOException ex) 
            {
             Logger.getLogger(JednostkiEkran.class.getName()).log(Level.SEVERE, null, ex);
            }

         }else
           {
            System.out.println("nie znalazlem pliku jednostek");
           }   

        /**
         * towzymy tabelaEdycjiPomocnik w kalasie JednostkiPomocnik (klasa
         * wykozystuje klase abstrakcyjna dzieki ktorej jest mozliwe
         * wyswietlanie w JTable zawartosci ArrayListy klasy Jednostki
         */
        tabelaEdycjiPomocnik = new JednostkiPomocnik(listaJednostek);

        /**
         * twozymy tabele jednostek
         */
        jednostekTabela = new JTable(tabelaEdycjiPomocnik);

        /**
         * dodajemy scrolla
         */
        scrollTabeli = new JScrollPane(jednostekTabela);

        /**
         * twożymy butony
         */
        dodajButton = new JButton("dodaj");
        edytujButton = new JButton("edytuj");
        kasujButton = new JButton("kasuj");

        /**
         * ustawiamy butony edycja i kasuj jako wywłaczone (najpierw trzeba coś
         * zazanczyc aby mozna bylo wprowadzać zmiany
         */
        edytujButton.setEnabled(false);
        kasujButton.setEnabled(false);

        /**
         * dodajemy panel w którym bedziemy wyświetlać buttony i dodajemy
         * przyciski do tego panelu
         */
        JPanel panelPrzyciskow = new JPanel(new FlowLayout());
        panelPrzyciskow.add(dodajButton);
        panelPrzyciskow.add(edytujButton);
        panelPrzyciskow.add(kasujButton);

        /**
         * dodajemy scroll i butony do pnelu
         */
        getContentPane().add(scrollTabeli, BorderLayout.CENTER);
        getContentPane().add(panelPrzyciskow, BorderLayout.SOUTH);

        ListSelectionModel selModel = jednostekTabela.getSelectionModel();
        selModel.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) 
             {
                int selRows = jednostekTabela.getSelectedRowCount();
                edytujButton.setEnabled(selRows == 1);  // pozwala edytowac jak zaznaczony jest tylko 1 wiersz
                kasujButton.setEnabled(selRows > 0);  // pozwala kasowac gdy zazaczonych jest wiecej od 0 wierszy
             }
        });
        // koniec zaznaczania

        /**
         * słuchacze wcisnietych przycisków
         */
        dodajButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) 
             {
                zrobDodaj();
             }
        });

        edytujButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) 
             {
                zrobEdytuj();
             }
        });

        kasujButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) 
             {
                zrobKasuj();
             }
        });
            
      jednostekTabela.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
    }
    
    /**
     * metoda dodająca nową Jednostkę
     */
    private void zrobDodaj()
    {
        Jednostki jednostka = new Jednostki();
        JednostkiEdycja edytujJednostki = new JednostkiEdycja(this);
        
        if (edytujJednostki.wczytajJednostki(jednostka)){
            
          tabelaEdycjiPomocnik.dodajJednostke(jednostka);
          try 
           {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("jednostki.txt"));
            out.writeObject(listaJednostek);
            out.close();
           } catch (IOException ex) 
           {
             Logger.getLogger(JednostkiEkran.class.getName()).log(Level.SEVERE, null, ex);
           }
        }
    }
    
    /**
     * metoda edytująca istniejącą Jednostkę (taka która jest zaznaczona) moze być zaznaczony tylko jeden
     */
    private void zrobEdytuj()
    {
      /**
       * getSelectedRow zapisze do zmiennej numer wiersza gdy zaden z wierszy nie jest
       * wybrany to zmienna = -1
       */
      int indeksWybranegoWiersza = jednostekTabela.getSelectedRow();
      
      if (indeksWybranegoWiersza !=-1){
          Jednostki jednostki = tabelaEdycjiPomocnik.wesJednostkiZIndeksu(indeksWybranegoWiersza);
          JednostkiEdycja edytujJednostke = new JednostkiEdycja(this);
          edytujJednostke.wczytajJednostki(jednostki);    
          tabelaEdycjiPomocnik.edytujJednostki(indeksWybranegoWiersza, jednostki);
          try 
           {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("jednostki.txt"));
            out.writeObject(listaJednostek);
            out.close();
           } catch (IOException ex) 
           {
             Logger.getLogger(JednostkiEkran.class.getName()).log(Level.SEVERE, null, ex);
           }
      }
    }
    
    /**
     * metoda kasująca jednostkę
     */
    private void zrobKasuj()
     {
        int indeksPierwszegoWybranegoWiersza = jednostekTabela.getSelectedRow();
        if (indeksPierwszegoWybranegoWiersza !=-1)
         {
            int ileZaznaczonych = jednostekTabela.getSelectedRowCount();
            tabelaEdycjiPomocnik.kasujJednostki(indeksPierwszegoWybranegoWiersza, ileZaznaczonych);
            try 
             {
              ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("jednostki.txt"));
              out.writeObject(listaJednostek);
              out.close();
             } catch (IOException ex) 
             {
              Logger.getLogger(JednostkiEkran.class.getName()).log(Level.SEVERE, null, ex);
             }
         }
    }
    
    
    public static ArrayList<Jednostki> listaJednostek = new ArrayList<Jednostki>();
    private JednostkiPomocnik tabelaEdycjiPomocnik;
    private JTable jednostekTabela;
    private JScrollPane scrollTabeli;
    private JButton dodajButton;
    private JButton edytujButton;
    private JButton kasujButton;
}
