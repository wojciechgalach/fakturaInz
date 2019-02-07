/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ekran;

import faktury.Towar;
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
import java.util.LinkedList;
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

/**
 *
 * @author wojtek
 */
public class TowarEkran extends JFrame implements Serializable {
      
    
     public TowarEkran() //throws IOException, ClassNotFoundException
      {
        super("Towary");
        
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);    // wyłaczy okno edycji ale nie cały program
        setSize(400, 300);
        setLocationRelativeTo(null);
        
        File f = new File("towary.txt");
        if(f.exists()) 
         { 
          try
           {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream("towary.txt"));
            try 
             {
               listaTowar = (LinkedList<Towar>) in.readObject();
             } catch (ClassNotFoundException ex) 
             {
               Logger.getLogger(TowarEkran.class.getName()).log(Level.SEVERE, null, ex);
             }
             in.close();
            } catch (IOException ex) 
            {
              Logger.getLogger(TowarEkran.class.getName()).log(Level.SEVERE, null, ex);
            }
        
          }else
           {
            System.out.println("nie znalazlem pliku towarow");
           }   
             
        /**
         * towzymy tabelaEdycjiPomocnik w kalasie TowarPomocnik (klasa
         * wykozystuje klase abstrakcyjna dzieki ktorej jest mozliwe
         * wyswietlanie w JTable zawartosci LinkedList klasy Towar
         */
        tabelaEdycjiPomocnik = new TowarPomocnik(listaTowar);
        
        /**
         * twozymy tabele Towar
         */
        towarTabela = new JTable(tabelaEdycjiPomocnik);

        /**
         * dodajemy scrolla
         */
        scrollTabeli = new JScrollPane(towarTabela);
                
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
        
               // pozwala zaznaczyc 
        ListSelectionModel selModel = towarTabela.getSelectionModel();
        selModel.addListSelectionListener(new ListSelectionListener() 
         {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int selRows = towarTabela.getSelectedRowCount();
                // pozwala znaczy wyswietla butony
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
                try 
                 {
                   zrobDodaj();
                 } catch (IOException ex) 
                 {
                   Logger.getLogger(TowarEkran.class.getName()).log(Level.SEVERE, null, ex);
                 }
             }
        });

        edytujButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) 
             {
                try 
                 {
                   zrobEdytuj();
                 } catch (IOException ex) 
                 {
                   Logger.getLogger(TowarEkran.class.getName()).log(Level.SEVERE, null, ex);
                 }
             }
        });

        kasujButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) 
             {
                try 
                 {
                  zrobKasuj();
                 } catch (IOException ex) 
                 {
                  Logger.getLogger(TowarEkran.class.getName()).log(Level.SEVERE, null, ex);
                 }
              }
        });
             
     towarTabela.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);     
    }
     
    /**
     * metoda dodająca nowy Towar
     */
    private void zrobDodaj() throws IOException
    {
        Towar towar = new Towar();
        TowarEdycja edytujTowar = new TowarEdycja(this);
        
        if (edytujTowar.wczytajTowar(towar)){
            
          tabelaEdycjiPomocnik.dodajTowar(towar);
          EkranGlowny.class.toString();
          ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("towary.txt"));
            out.writeObject(listaTowar);
            out.close();
        }
    }
    
    /**
     * metoda edytująca istniejący Towar (taka która jest zaznaczona) moze być zaznaczony tylko jeden
     */
    private void zrobEdytuj() throws IOException
    {
      /**
       * getSelectedRow zapisze do zmiennej numer wiersza gdy zaden z wierszy nie jest
       * wybrany to zmienna = -1
       */
      int indeksWybranegoWiersza = towarTabela.getSelectedRow();
      
      if (indeksWybranegoWiersza !=-1){
          Towar towar = tabelaEdycjiPomocnik.wesTowarZIndeksu(indeksWybranegoWiersza);
          TowarEdycja edytujTowar = new TowarEdycja(this);
          if (edytujTowar.wczytajTowar(towar)){
              tabelaEdycjiPomocnik.edytujTowar(indeksWybranegoWiersza, towar);
              EkranGlowny.class.toString();
              ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("towary.txt"));
                out.writeObject(listaTowar);
                out.close();
          }
      }
    }
    
    /**
     * metoda kasująca jednostkę
     */
    private void zrobKasuj() throws IOException
    {
        int indeksPierwszegoWybranegoWiersza = towarTabela.getSelectedRow();
        if (indeksPierwszegoWybranegoWiersza !=-1)
         {
            int ileZaznaczonych = towarTabela.getSelectedRowCount();
            tabelaEdycjiPomocnik.kasujTowar(indeksPierwszegoWybranegoWiersza, ileZaznaczonych);
            EkranGlowny.class.toString();
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("towary.txt"));
            out.writeObject(listaTowar);
            out.close();
         }
    }   
    
    public static LinkedList<Towar> listaTowar = new LinkedList<Towar>();
    private JTable towarTabela;
    private JScrollPane scrollTabeli;
    private TowarPomocnik tabelaEdycjiPomocnik;
    private JButton dodajButton;
    private JButton edytujButton;
    private JButton kasujButton;
    
}
