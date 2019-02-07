/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ekran;

import faktury.Klienci;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Toolkit;
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

/**
 *
 * @author wojtek
 */
public class KlienciEkran extends JFrame implements Serializable {
    
    public KlienciEkran() //throws FileNotFoundException, IOException
    {
        super("Klienici");
        
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);    // wyłaczy okno edycji ale nie cały program
        setSize(xOkna, yOkna);
        setLocationRelativeTo(null);

        File f = new File("klienci.txt");
        if(f.exists()) 
         {  
           ObjectInputStream in = null;
           try 
            {
             in = new ObjectInputStream(new FileInputStream("klienci.txt"));
             try 
              {
               listaKlientow = (ArrayList<Klienci>) in.readObject();
               in.close();
              } catch (IOException ex) 
              {
                Logger.getLogger(KlienciEkran.class.getName()).log(Level.SEVERE, null, ex);
              }
              } catch (IOException ex) 
              {
                Logger.getLogger(KlienciEkran.class.getName()).log(Level.SEVERE, null, ex);
              } catch (ClassNotFoundException ex) 
              {
                Logger.getLogger(TowarEkran.class.getName()).log(Level.SEVERE, null, ex);
              } finally 
              {
                try 
                 {
                  in.close();
                 } catch (IOException ex) 
                 {
                  Logger.getLogger(KlienciEkran.class.getName()).log(Level.SEVERE, null, ex);
                 }
               }
            
            }else
             {
              System.out.println("nie znalazlem pliku klientow");
             }
         
        /**
         * towzymy tabelaEdycjiPomocnik w kalasie KlkienicPomocnik (klasa
         * wykozystuje klase abstrakcyjna dzieki ktorej jest mozliwe
         * wyswietlanie w JTable zawartosci ArrayListy klasy Klienic
         */
         tabelaEdycjiPomocnik = new KlienciPomocnik(listaKlientow);
         
         /**
         * twozymy tabele Klienci
         */
         klienciTabela = new JTable(tabelaEdycjiPomocnik);
         
         /**
         * dodajemy scrolla
         */
         scrollTabeli = new JScrollPane(klienciTabela);

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
        ListSelectionModel selModel = klienciTabela.getSelectionModel();
        selModel.addListSelectionListener(new ListSelectionListener() {            
            @Override
            public void valueChanged(ListSelectionEvent e) 
             {
                int selRows = klienciTabela.getSelectedRowCount();
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
            
        klienciTabela.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
    }
    
    /**
     * metoda dodająca nowego Klienta
     */
    private void zrobDodaj()
     {
        Klienci klient = new Klienci();
        KlienciEdycja edytujKlient = new KlienciEdycja(this);
        
        if (edytujKlient.wczytajklient(klient))
         {
          tabelaEdycjiPomocnik.dodajKlient(klient);
          EkranGlowny.class.toString();
          try 
           {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("klienci.txt"));
            out.writeObject(listaKlientow);
            out.close();
           } catch (IOException ex) 
           {
             Logger.getLogger(KlienciEkran.class.getName()).log(Level.SEVERE, null, ex);
           }
         }      
     }
    
    /**
     * metoda edytująca istniejącego  (taka która jest zaznaczona) moze być zaznaczony tylko jeden
     */
    private void zrobEdytuj()
    {
      /**
       * getSelectedRow zapisze do zmiennej numer wiersza gdy zaden z wierszy nie jest
       * wybrany to zmienna = -1
       */
      int indeksWybranegoWiersza = klienciTabela.getSelectedRow();
      
      if (indeksWybranegoWiersza !=-1)
       {
          Klienci klient = tabelaEdycjiPomocnik.wesKlientZIndeksu(indeksWybranegoWiersza);
          KlienciEdycja edytujKlient = new KlienciEdycja(this);
          if (edytujKlient.wczytajklient(klient))
           {
              tabelaEdycjiPomocnik.edytujKlient(indeksWybranegoWiersza, klient);
              EkranGlowny.class.toString();
              try 
               {
                ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("klienci.txt"));
                out.writeObject(listaKlientow);
                out.close();
               } catch (IOException ex)
                {
                 Logger.getLogger(KlienciEkran.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    
    /**
     * metoda kasująca Klienta
     */
    private void zrobKasuj()
    {
        int indeksPierwszegoWybranegoWiersza = klienciTabela.getSelectedRow();
        if (indeksPierwszegoWybranegoWiersza !=-1)
         {
            int ileZaznaczonych = klienciTabela.getSelectedRowCount();
            tabelaEdycjiPomocnik.kasujKlient(indeksPierwszegoWybranegoWiersza, ileZaznaczonych);
            EkranGlowny.class.toString();
            try 
             {
              ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("klienci.txt"));
              out.writeObject(listaKlientow);
              out.close();
             } catch (IOException ex) 
             {
              Logger.getLogger(KlienciEkran.class.getName()).log(Level.SEVERE, null, ex);
             }
         }
    }
     
    
    public static ArrayList<Klienci> listaKlientow = new ArrayList<Klienci>();
    private KlienciPomocnik tabelaEdycjiPomocnik;
    private JTable klienciTabela;
    private JScrollPane scrollTabeli;
    private JButton dodajButton;
    private JButton edytujButton;
    private JButton kasujButton;
    private int xOkna=(int) ((Toolkit.getDefaultToolkit().getScreenSize().width)*0.999);
    private int yOkna=(int) ((Toolkit.getDefaultToolkit().getScreenSize().height)*0.5);
    
}
