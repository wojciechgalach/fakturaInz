/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ekran;

import druk.*;
import faktury.DaneFaktury;
import faktury.Towar;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.WindowConstants;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;


/**
 *
 * @author wojtek
 */
public class FakturaNowaEkra extends JFrame implements ActionListener, Serializable, ItemListener {
    
       
    public FakturaNowaEkra()
    {
        this.setSize(600, 400);                                             // Podajemy rozmiary okna                      
        this.setTitle("Dodawania Faktur");   
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        
        danefaktury.wystawca = EkranGlowny.firmaEkran.firma;

        JLabel pusty= new JLabel(" ");
        JLabel klientLabel = new JLabel ("Klient"); 
        JLabel dataWystawienia = new JLabel ("Data wystawienia");
         dataWystawieniaFak = new JTextField(12);
         dataWystawieniaFak.setText(danefaktury.getDataWystawienia());
        JLabel dataPlatnosic = new JLabel ("Data platnosci");
         dataPlatnosciFak = new JTextField(12);
         dataPlatnosciFak.setText(danefaktury.getDataPlatnosci());
         dataPlatnosciFak.setEnabled(false);
         danefaktury.setSposobPlatnosci("gotówką");
        JLabel nrFaktury =  new JLabel ("Nr Faktury");
         nrFakturyFak   = new JTextField(12);
         nrFakturyFak.setText(danefaktury.getNumerFaktury());
        JLabel kwotaBruttoLabel =  new JLabel ("Kwota do zapłaty");
         kwotaBruttoField = new JTextField(8); 
         kwotaBruttoField.setEditable(false);
        JLabel kwotaNettoLabel =  new JLabel ("Kwota netto");
         kwotaNettoField = new JTextField(8); 
         kwotaNettoField.setEditable(false);         
        JLabel walutaNettoLabel =  new JLabel ("waluta");
         walutaField = new JTextField(4); 
         walutaField.setText(waluta);
      
        gridBag = new GridBagLayout();
        consolaPanel = new JPanel(gridBag);
         
        comboBoxKlient = new JComboBox();
        pPrzelew = new JCheckBox("Płatne przelewem");
        pPrzelew.setSelected(false);
        pPrzelew.addItemListener(this);  
     
        for (int j=0; j<=KlienciEkran.listaKlientow.size()-1; j++)
         {
            comboBoxKlient.addItem(KlienciEkran.listaKlientow.get(j));    
         }
        
        dodajLinieWyswietlania(0, klientLabel, comboBoxKlient);
        dodajLinieWyswietlania(1, pPrzelew, pusty);
        dodajLinieWyswietlania(2, dataWystawienia, dataWystawieniaFak);
        dodajLinieWyswietlania(3, dataPlatnosic, dataPlatnosciFak);
        dodajLinieWyswietlania(4, nrFaktury, nrFakturyFak);
        dodajLinieWyswietlania(5, pusty, pusty);
        dodajLinieWyswietlania(6, kwotaNettoLabel, kwotaNettoField);
        dodajLinieWyswietlania(7, kwotaBruttoLabel, kwotaBruttoField); 
        dodajLinieWyswietlania(8, walutaNettoLabel, walutaField); 
       
         dodajTowarButton = new JButton("dodaj Towar");
         edytujTowarButton = new JButton("edytuj Towar");
         kasujTowarButton = new JButton("kasuj Towar");
         drukujFaktureButton = new JButton("Drukuj");
               
         JPanel buttonyTowaru = new JPanel (new FlowLayout());
          buttonyTowaru.add(dodajTowarButton);
          buttonyTowaru.add(edytujTowarButton);
          buttonyTowaru.add(kasujTowarButton);
          buttonyTowaru.add(drukujFaktureButton);
         
        tabelaEdycjiPomocnik = new FakturaNowaPomocnik(danefaktury.kupowane);
         
         /**
         * twozymy tabele Dodawanych Towarow
         */
        towarTabela = new JTable(tabelaEdycjiPomocnik);

        /**
         * dodajemy scrolla
         */
        scrollTabeli = new JScrollPane(towarTabela);
         
        ListSelectionModel selModel = towarTabela.getSelectionModel();
        selModel.addListSelectionListener(new ListSelectionListener() {
            
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int selRows = towarTabela.getSelectedRowCount();
                edytujTowarButton.setEnabled(selRows == 1);  // pozwala edytowac jak zaznaczony jest tylko 1 wiersz
                kasujTowarButton.setEnabled(selRows > 0);  // pozwala kasowac gdy zazaczonych jest wiecej od 0 wierszy
            }
        });
         
         
        /**
         * słuchacze wcisnietych przycisków
         */
        dodajTowarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) 
             {
                zrobDodaj();
                czyJestTowar();
             }
        });

        edytujTowarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) 
             {
                zrobEdytuj();
                czyJestTowar();
             }         
        });

        kasujTowarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) 
             {
                zrobKasuj();
                czyJestTowar();
             }
        });
        
         
         drukujFaktureButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) 
            {                               
               podsumowanieFaktury();
               updateFaktura();

               drukowanie = new Drukowanie(danefaktury, podsumowanieFaktury, kwotaBruttoCala, waluta);
               drukowanie.setVisible(true);           
            }
        });
           
     towarTabela.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);     
           
          getContentPane().add(consolaPanel, BorderLayout.NORTH);
          getContentPane().add(scrollTabeli, BorderLayout.CENTER);
          getContentPane().add(buttonyTowaru, BorderLayout.SOUTH);
    }
    
    
    private void czyJestTowar() 
    {
         if (kupowane.size() != 0)
          {
            drukujFaktureButton.setEnabled(true);       
          }
         aktualizajcaNettoBrutto();
    }
    
    private void aktualizajcaNettoBrutto() 
    {
        totalNetto=0.0;
        totalBrutto=0.0;
        
        for (int i=0 ; i<tabelaEdycjiPomocnik.kupowane.size(); i++)
                {
                    Double zz = Double.parseDouble(danefaktury.kupowane.get(i).zaakraglonawartoscBrutto);  //specjalnie ze stringa bo ten string jest dobrze zaokraglany dobrze znaczy tak jak lubi Skaróbwka
                    
                    totalNetto = totalNetto + danefaktury.kupowane.get(i).wartoscNetto;
                    totalBrutto= totalBrutto +  zz;
                }
              
        DecimalFormat f = (DecimalFormat) NumberFormat.getInstance(Locale.US);
        f.setMaximumFractionDigits(2);
        String nettoTmp = f.format(totalNetto);
        String bruttoTmp = f.format(totalBrutto);
        kwotaNettoField.setText(nettoTmp);
        kwotaBruttoField.setText(bruttoTmp);
        kwotaBruttoCala = bruttoTmp;
    }
    
    public void podsumowanieFaktury()  
    {
        podsumowanieFaktury="% VAT, Wartość Netto, Wartość VAT, Wartość Brutto\n";
        EkranGlowny.vatekran.listaVat.size();
      
        for (int j=0; j < EkranGlowny.vatekran.listaVat.size(); j++)
         {   
          EkranGlowny.vatekran.listaVat.get(j);  
          totalNetto = 0.0;
          totalBrutto= 0.0;
          samVat = 0.0;
        
           for (int i=0 ; i<tabelaEdycjiPomocnik.kupowane.size(); i++)
            {
              String stawkaVat1 = EkranGlowny.vatekran.listaVat.get(j).getVatNazwa();
              String stawkaVat2 =  danefaktury.kupowane.get(i).getNazwaVat();
               if (stawkaVat1.equals (stawkaVat2)) //boolean equals(String str) 
                { 
                  Double zz = Double.parseDouble(danefaktury.kupowane.get(i).zaakraglonawartoscBrutto);  //specjalnie ze stringa bo ten string jest dobrze zaokraglany dobrze znaczy tak jak lubi Skaróbwka
                  Double vat = Double.parseDouble(danefaktury.kupowane.get(i).zaakraglonaKwotaVat);
                    
                  totalNetto = totalNetto + danefaktury.kupowane.get(i).wartoscNetto;
                  totalBrutto= totalBrutto +  zz;
                  samVat = samVat + vat;
                  }
               }
         podsumowanieFaktury = podsumowanieFaktury +" "+ EkranGlowny.vatekran.listaVat.get(j).getVatNazwa()+"  "+ totalNetto+"  "+ samVat+ "  " +totalBrutto+ "\n";
       }
     }
    

    private void updateFaktura(){
        danefaktury.klient = KlienciEkran.listaKlientow.get(comboBoxKlient.getSelectedIndex());
                   danefaktury.setDataPlatnosci(dataPlatnosciFak.getText());
                   danefaktury.setDataWystawienia(dataWystawieniaFak.getText());
                   danefaktury.setNrFaktury(nrFakturyFak.getText());
                   waluta = walutaField.getText();
    }
    
    /**
     * metoda dodająca nowy Towar
     */
    private void zrobDodaj()
    {              
        FakturaNowaEkranEdytuj edytujTowar = new FakturaNowaEkranEdytuj(this);
        Towar towar = (Towar) EkranGlowny.towaryekran.listaTowar.get(edytujTowar.idWybranegoTowaru).clone();
         if (edytujTowar.wczytajTowar(towar))
          {
          tabelaEdycjiPomocnik.dodajTowar(edytujTowar._towar,edytujTowar.wybrana_ilosc,danefaktury);
          }
    }
    
    /**
     * metoda edytująca istniejący Towar (taka która jest zaznaczona) moze być zaznaczony tylko jeden
     */
    private void zrobEdytuj()
    {
      /**
       * getSelectedRow zapisze do zmiennej numer wiersza gdy zaden z wierszy nie jest
       * wybrany to zmienna = -1
       */
      int indeksWybranegoWiersza = towarTabela.getSelectedRow();
      
      if (indeksWybranegoWiersza !=-1)
       {
          FakturaNowaEkranEdytuj edytujTowar = new FakturaNowaEkranEdytuj(this);
          Towar towar = (Towar) EkranGlowny.towaryekran.listaTowar.get(edytujTowar.idWybranegoTowaru).clone();
          
          if (edytujTowar.wczytajTowar(towar))
           {
              tabelaEdycjiPomocnik.edytujTowar(indeksWybranegoWiersza, towar,edytujTowar.wybrana_ilosc,danefaktury);
           }
        }
    }
    
    /**
     * metoda kasująca jednostkę
     */
    private void zrobKasuj()
    {
        int indeksPierwszegoWybranegoWiersza = towarTabela.getSelectedRow();
        if (indeksPierwszegoWybranegoWiersza !=-1)
         {
            int ileZaznaczonych = towarTabela.getSelectedRowCount();
            tabelaEdycjiPomocnik.kasujTowar(indeksPierwszegoWybranegoWiersza, ileZaznaczonych,danefaktury);
         }
    }   
    
    
    
    /*
     * słuchacz czy aby pola wyboru nie zaznaczone
     */
    public void itemStateChanged(ItemEvent e) {

    Object source = e.getItemSelectable();

    if (source == pPrzelew) 
     {
        dataPlatnosciFak.setEnabled(true);
        danefaktury.setSposobPlatnosci("przelewem");
     }
    if (e.getStateChange() == ItemEvent.DESELECTED)
     {
        dataPlatnosciFak.setEnabled(false);
        danefaktury.setSposobPlatnosci("gotówką");
     }
    
    }
    
//    @Override
//    public void actionPerformed(ActionEvent e) {
//        throw new UnsupportedOperationException("Not supported yet.");
//    }

 
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
      
      
      
      public String getNumerFaktury()
      {   
          numerFaktury = "12/02/2013";
          return numerFaktury;
      }
    
   
   public FakturaNowaEkranEdytuj towaryDodawanie;
   public transient ArrayList<Towar> kupowane = new ArrayList<Towar>();  
   private FakturaNowaPomocnik tabelaEdycjiPomocnik;
   private JTable towarTabela;
   public DaneFaktury danefaktury = new DaneFaktury();
   public Double totalNetto;
   public Double totalBrutto;
   public String totalNettoString;
   public String totalBruttoString;
   public Double samVat;
   public String samVatString;
   public String podsumowanieFaktury;
      
    private final JComboBox comboBoxKlient;
    private final GridBagLayout gridBag;
    private final JPanel consolaPanel;
    private final JCheckBox pPrzelew;
    private final JButton edytujTowarButton;
    private final JButton kasujTowarButton;
    private final JButton dodajTowarButton;
    private final JScrollPane scrollTabeli;
    public final JTextField kwotaBruttoField;
    public final JTextField kwotaNettoField;
    private final JButton drukujFaktureButton;
    public Drukowanie drukowanie;
    public String kwotaBruttoCala;
    
    public String numerFaktury;
    public String waluta = "PLN";
    private final JTextField walutaField;
    private final JTextField dataWystawieniaFak;
    private final JTextField dataPlatnosciFak;
    private final JTextField nrFakturyFak;

   @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
 
}
