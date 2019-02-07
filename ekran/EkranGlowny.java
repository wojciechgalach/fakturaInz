/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ekran;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
//import javax.swing.JTabbedPane;
import javax.swing.JTextField;

/**
 *
 * @author wojtek 
 */
public class EkranGlowny extends JFrame implements ActionListener, Serializable {

    
    public EkranGlowny()
    {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);                    // Pozwala zamykać okno
        this.setSize(xOkna/2, yOkna/2);                                         // Podajemy rozmiary okna                      
        this.setTitle("Faktury");                                               // Nazwa wyświetlanego okna
        this.setJMenuBar(pasekMenu);                                            // Ustawienie gdzie ma być wyswietlany pasek menu (bedzie tu naszej ramce)
           
        JMenu menuEdycja = pasekMenu.add(new JMenu("Edytuj"));                  // do paskaMenu dodajemy nowy obiekt menu o nazwie ".." w ten sposób dodajemy bo bedziemy dzięki temu odwołać się do tego obiektu (aby zoabaczyć jak to ctrl + lewy klik na add) film 4programowanie lekcja19 min4,00                        
         menuEdycja.addSeparator();                                              // separator w menu
         JMenu menuOpcje = new JMenu("Ustawienia");                              // Twozymy nowe menu 
         menuEdycja.add(menuOpcje);                                              // to nowe menu dodajemy jako nowy element do naszego menuEdycja        
         JMenuItem podMenuOpcjeUstVat = menuOpcje.add(new JMenuItem("Ustawienia podatku VAT"));
         JMenuItem podMenuOpcjeUstJed = menuOpcje.add(new JMenuItem("Ustawienia jednostek"));
         menuEdycja.addSeparator();                                              // separator w menu
         JMenuItem podMenuOpcjeFirma = menuEdycja.add(new JMenuItem("Ustawienie danych Firmy"));
         menuEdycja.addSeparator();
         JMenuItem podMenuKoniec = menuEdycja.add(new JMenuItem("Zakończ program")); // dodanie do menu edycji pojedynczej instancji konczącej program
        
        // Słuchacze do menu - jak wcisniemy odpowieni element menu to odpowiednio reaguja 
        podMenuOpcjeUstVat.addActionListener(new ActionListener()               // towozymy do podMenuNowy słucza implemenutjemy wszystkie mwymagane metody ktore cos beda robić)
           {                  
            @Override
            public void actionPerformed(ActionEvent e)                          // metody ActionListener do robienia tego co w tej metodzie kazemy
                {
                  akutalizacjaDanych();
                  vatekran.setVisible(true);
                }
           });  // koniec ActionListener
               
        podMenuOpcjeUstJed.addActionListener(new ActionListener() 
           {
             @Override
             public void actionPerformed(ActionEvent e) 
                {
                  akutalizacjaDanych();
                  jednostkiekran.setVisible(true);
                }
           });
        
        podMenuKoniec.addActionListener(new ActionListener() 
           {
             @Override
             public void actionPerformed(ActionEvent e) 
                {
                  System.exit(0);
                }
           });
        
        
        podMenuOpcjeFirma.addActionListener(new ActionListener() 
           {
             @Override
             public void actionPerformed(ActionEvent e) 
                {
                  akutalizacjaDanych();
                  firmaEkran.setVisible(true);
                }
           });
        
        
        JLabel iloscTowarowLabel =  new JLabel ("Ilość towarów ");
        iloscTowarowField =  new JTextField(8); 
        iloscTowarowField.setEditable(false);       
        JLabel iloscKlientowLabel =  new JLabel ("Ilość Klientów ");
        iloscKlientowField = new JTextField(8); 
        iloscKlientowField.setEditable(false);       
        

        fakturaButton = new JButton("Nowa Faktura");
        klientButton = new JButton("kient");
        towarButton = new JButton("towar");     
        
        JPanel panelDanych = new JPanel (new FlowLayout());
        
        zarobionaKasaMiesiacLabel = new JLabel();
        zarobionaKasaMiesiacWyswietlKwote = new JLabel();
        panelDanych.add(zarobionaKasaMiesiacLabel);
        panelDanych.add(zarobionaKasaMiesiacWyswietlKwote);
      
        panelDanych.add(iloscTowarowLabel);
        panelDanych.add(iloscTowarowField);
        panelDanych.add(iloscKlientowLabel);
        panelDanych.add(iloscKlientowField);
            
        JPanel panelPrzyciskow = new JPanel(new FlowLayout());
        panelPrzyciskow.add(fakturaButton);
        panelPrzyciskow.add(klientButton);
        panelPrzyciskow.add(towarButton);

        panelDanych.setBorder(BorderFactory.createTitledBorder("Informacje"));

        getContentPane().add(panelDanych, BorderLayout.CENTER);
        getContentPane().add(panelPrzyciskow, BorderLayout.SOUTH);
           
        towarButton.addActionListener(new ActionListener() 
           {
             @Override
             public void actionPerformed(ActionEvent e) 
                {
                  towaryekran.setVisible(true);   
                  akutalizacjaDanych();
                }
           });        
      
        fakturaButton.addActionListener(new ActionListener() 
           {
             @Override
             public void actionPerformed(ActionEvent e) 
                {
                  faktyryNowe = new FakturaNowaEkra();
                  faktyryNowe.setVisible(true);   
                  akutalizacjaDanych();
                }
           }); 
        
        klientButton.addActionListener(new ActionListener() 
           {
             @Override
             public void actionPerformed(ActionEvent e) 
                {
                  klienciEkran = new KlienciEkran();
                  klienciEkran.setVisible(true);   
                  akutalizacjaDanych();
                }
           });   
     
      akutalizacjaDanych();
      pack();
      setVisible(true);   
    }
     
     
     public void akutalizacjaDanych()
     {
        int ik;
        int it;
        ik = klienciEkran.listaKlientow.size();
        it = towaryekran.listaTowar.size();
        
        DecimalFormat f = (DecimalFormat) NumberFormat.getInstance(Locale.US);
        f.setMaximumFractionDigits(0);
        String ikTmp = f.format(ik);
        String itTmp = f.format(it);
        
        iloscKlientowField.setText(ikTmp);
        iloscTowarowField.setText(itTmp);        
     }
     
     public String toString() 
      {
         akutalizacjaDanych();
         return "";
      }
     
    
  private JLabel zarobionaKasaMiesiacLabel = new JLabel(); 
  private JLabel zarobionaKasaMiesiacWyswietlKwote = new JLabel();  
  public static JednostkiEkran jednostkiekran = new JednostkiEkran();
  public static VatEkran vatekran = new VatEkran();
  public static TowarEkran towaryekran = new TowarEkran();
  public static KlienciEkran klienciEkran = new KlienciEkran();
  public static FirmaEkran firmaEkran = new FirmaEkran();
//  private static UstawieniaEkran ustawienia = new UstawieniaEkran();
  private JMenuBar pasekMenu = new JMenuBar();                                  // menu 
//  private JTabbedPane zakladki = new JTabbedPane();                              // tablety
  private JPanel panelFaktur;
  private JPanel panelKlientow;
  private JPanel panelTowarow;
  private final JButton fakturaButton;
  private final JButton klientButton;
  private final JButton towarButton;
  public final JTextField iloscTowarowField;
  public final JTextField iloscKlientowField;   
  public FakturaNowaEkra faktyryNowe;
  
  private int xOkna=(int) ((Toolkit.getDefaultToolkit().getScreenSize().width)*0.999);
  private int yOkna=(int) ((Toolkit.getDefaultToolkit().getScreenSize().height)*0.96);  

    @Override
    public void actionPerformed(ActionEvent e) {
    }    
    
}
