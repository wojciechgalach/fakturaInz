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
import ustawienia.StawkiVat;

/**
 *
 * @author wojtek
 */
public class VatEkran extends JFrame implements Serializable {
      
    public VatEkran() {
        super("Stawki VAT");
        
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);    // wyłaczy okno edycji ale nie cały program
        setSize(400, 300);
        setLocationRelativeTo(null);

        File f = new File("stawki_vat.txt");
        if(f.exists()) 
         { 
          try
           {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream("stawki_vat.txt"));
             try 
              {
                listaVat = (ArrayList<StawkiVat>) in.readObject();
              } catch (ClassNotFoundException ex) 
              {
                Logger.getLogger(VatEkran.class.getName()).log(Level.SEVERE, null, ex);
              }
             in.close();
            } catch (IOException ex) 
            {
             Logger.getLogger(VatEkran.class.getName()).log(Level.SEVERE, null, ex);
            }
          }else
           {
            System.out.println("nie znalazlem pliku stawek vat");
           }

        /**
         * towzymy tabelaEdycjiPomocnik w kalasie VatPomocnik (klasa
         * wykozystuje klase abstrakcyjna dzieki ktorej jest mozliwe
         * wyswietlanie w JTable zawartosci ArrayListy klasy Jednostki
         */
        tabelaEdycjiPomocnik = new VatPomocnik(listaVat);

        /**
         * twozymy tabele VAT
         */
        vatTabela = new JTable(tabelaEdycjiPomocnik);

        /**
         * dodajemy scrolla
         */
        scrollTabeli = new JScrollPane(vatTabela);

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
         * dodajemy scroll i butony do napnelu
         */
        getContentPane().add(scrollTabeli, BorderLayout.CENTER);
        getContentPane().add(panelPrzyciskow, BorderLayout.SOUTH);

        // pozwala zaznaczyc 
        ListSelectionModel selModel = vatTabela.getSelectionModel();
        selModel.addListSelectionListener(new ListSelectionListener() {            
            @Override
            public void valueChanged(ListSelectionEvent e) 
             {
                int selRows = vatTabela.getSelectedRowCount();
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
            
        vatTabela.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
    }
    
    /**
     * metoda dodająca nową Stawke Vat
     */
    private void zrobDodaj()
    {
        StawkiVat vat = new StawkiVat();
        VatEdycja edytujVat = new VatEdycja(this);
        
        if (edytujVat.wczytajVat(vat))
        {
          tabelaEdycjiPomocnik.dodajVat(vat);
          try 
           {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("stawki_vat.txt"));
            out.writeObject(listaVat);
            out.close();
           } catch (IOException ex)
           {
             Logger.getLogger(VatEkran.class.getName()).log(Level.SEVERE, null, ex);
           }  
        }
    }
    
    /**
     * metoda edytująca istniejącą Stawke VAT (taka która jest zaznaczona) moze być zaznaczony tylko jeden
     */
    private void zrobEdytuj()
    {
      /**
       * getSelectedRow zapisze do zmiennej numer wiersza gdy zaden z wierszy nie jest
       * wybrany to zmienna = -1
       */
      int indeksWybranegoWiersza = vatTabela.getSelectedRow();

          StawkiVat vat = tabelaEdycjiPomocnik.wesVatZIndeksu(indeksWybranegoWiersza);
          VatEdycja edytujVat = new VatEdycja(this);
    //      if (edytujVat.wczytajVat(vat)){
          edytujVat.wczytajVat(vat);    
          tabelaEdycjiPomocnik.edytujVat(indeksWybranegoWiersza, vat);
          try 
           {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("stawki_vat.txt"));
            out.writeObject(listaVat);
            out.close();
           } catch (IOException ex) 
           {
             Logger.getLogger(VatEkran.class.getName()).log(Level.SEVERE, null, ex);
           }
      }
    
    /**
     * metoda kasująca Stawkę VAT
     */
    private void zrobKasuj()
    {
        int indeksPierwszegoWybranegoWiersza = vatTabela.getSelectedRow();
         {
            int ileZaznaczonych = vatTabela.getSelectedRowCount();
            tabelaEdycjiPomocnik.kasujVat(indeksPierwszegoWybranegoWiersza, ileZaznaczonych);
            try 
             {
              ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("stawki_vat.txt"));
              out.writeObject(listaVat);
              out.close();
             } catch (IOException ex) 
             {
               Logger.getLogger(VatEkran.class.getName()).log(Level.SEVERE, null, ex);
             }
         }
    }
    
    
    public static ArrayList<StawkiVat> listaVat = new ArrayList<StawkiVat>();
    private VatPomocnik tabelaEdycjiPomocnik;
    private JTable vatTabela;
    private JScrollPane scrollTabeli;
    private JButton dodajButton;
    private JButton edytujButton;
    private JButton kasujButton;
    
}
