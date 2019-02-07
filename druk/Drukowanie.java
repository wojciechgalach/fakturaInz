/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package druk;


import faktury.DaneFaktury;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;

/**
 *
 * @author wojtek
 */
public class Drukowanie extends JFrame {


    
 public Drukowanie(DaneFaktury _danefaktury, String podsuma, String doZaplaty, String waluta)
    {
      this.danefaktury = _danefaktury;
      setTitle("Drukowanie");
      setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
      podumowanie = podsuma;
      doZaplacenia = doZaplaty;
      this.waluta = waluta;
      
 


kupujacy = new String();
kupujacy = danefaktury.klient.nazwaFirmy+"\n"+danefaktury.klient.ulica+" "
        +danefaktury.klient.nrUlicy+" "+danefaktury.klient.nrMieszkania+"\n"
        +danefaktury.klient.kodPocztowy+" "+danefaktury.klient.miasto+",  "
        +danefaktury.klient.kraj+"\nNIP: "+danefaktury.klient.NIP;

firma = new String();
firma = danefaktury.wystawca.nazwaFirmy+"\n"+danefaktury.wystawca.ulica+" "
        +danefaktury.wystawca.nrUlicy+" "+danefaktury.wystawca.nrMieszkania+"\n"
        +danefaktury.wystawca.kodPocztowy+" "+danefaktury.wystawca.miasto+",  "
        +danefaktury.wystawca.kraj+"\nNIP: "+danefaktury.wystawca.NIP
        +"   Nr konta: "+danefaktury.wystawca.nrKontaBankowego;

nrFaktur = new String();
nrFaktur = "Faktura VAT nr: " + danefaktury.numerFaktury;

daty = new String();
daty = "\nData wystawienia: " + danefaktury.dataWystawienia 
        +"      Data płatności: "+ danefaktury.dataPlatnosci
        +"   Waluta faktury: "+ waluta
        +"\n"+"Płatność  "+danefaktury.getSposobPlatnosci()
        + "       Kwota do zapłacenia "+doZaplacenia+ "\n"
        +"Legenda: 1-Nazwa 2-Cena Netto 3-VAT 4-Jednostka 5-Ilość 6-Wartość Netto 7-Kwota Vat 8-Wartość Brutto";

sprzedKup = new String();
sprzedKup = "Sprzedający: \n"+firma+"\n\n"+"Kupujacy: \n"+kupujacy;


      attributes = new HashPrintRequestAttributeSet();
      
      JPanel buttonPanel = new JPanel();
      JButton printButton = new JButton("Drukuj");
   
      contentPanel = new JPanel(new BorderLayout());
      panelKtoKomu = new JPanel (new BorderLayout());

       
JTextArea textAreaNR = new JTextArea(0, 0);
		textAreaNR.setMargin(new Insets(0,0,0,0));
		textAreaNR.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		textAreaNR.setEditable(false);

                textAreaNR.setText(nrFaktur);
                
JTextArea textAreaData = new JTextArea(0, 0);
		textAreaData.setMargin(new Insets(0,0,0,0));
		textAreaData.setFont(new Font("Times New Roman", Font.PLAIN, 10));
		textAreaData.setEditable(false);

                textAreaData.setText(daty);


JTextArea textArea1 = new JTextArea(0, 0);
		textArea1.setMargin(new Insets(0,0,0,0));
		textArea1.setFont(new Font("Times New Roman", Font.PLAIN, 10));
		textArea1.setEditable(false);

                textArea1.setText(sprzedKup);
                
  
        DrukowaniePomocnik fakturaTableModel = new DrukowaniePomocnik(danefaktury.kupowane);
        JTable table = new  JTable(fakturaTableModel);
        JScrollPane scrollTabeli = new JScrollPane(table);
        table.setFont(new Font("Times New Roman", Font.PLAIN, 9));
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        
        table.getColumnModel().getColumn(0).setPreferredWidth(140);
        table.getColumnModel().getColumn(1).setPreferredWidth(50);
        table.getColumnModel().getColumn(2).setPreferredWidth(20);
        table.getColumnModel().getColumn(3).setPreferredWidth(40);
        table.getColumnModel().getColumn(4).setPreferredWidth(40);
        table.getColumnModel().getColumn(5).setPreferredWidth(50);
        table.getColumnModel().getColumn(6).setPreferredWidth(50);
        table.getColumnModel().getColumn(7).setPreferredWidth(50);
           
        JTextArea pods = new JTextArea(0, 0);
		pods.setMargin(new Insets(0,0,0,0));
		pods.setFont(new Font("Times New Roman", Font.PLAIN, 10));
		pods.setEditable(false);
                pods.setText(podumowanie);
        


       contentPanel.add(panelKtoKomu, BorderLayout.NORTH);
       contentPanel.add(scrollTabeli, BorderLayout.CENTER);
       contentPanel.add(pods , BorderLayout.SOUTH);
          
        panelKtoKomu.add(textAreaNR, BorderLayout.NORTH);
        panelKtoKomu.add(textArea1 ,BorderLayout.CENTER);
        panelKtoKomu.add(textAreaData, BorderLayout.SOUTH);

      buttonPanel.add(printButton);

      printButton.addActionListener(new ActionListener()
       {
        public void actionPerformed(ActionEvent event)
         {
          try
           {
            PrinterJob pj = PrinterJob.getPrinterJob();
            pj.setPrintable(new Printable() 
             {
              public int print(Graphics pg, PageFormat pf, int pageNum)                    
               {
                if (pageNum > 0)
                  {
                    return Printable.NO_SUCH_PAGE;
                  }
                 Graphics2D g2 = (Graphics2D) pg;

                 g2.translate(pf.getImageableX(), pf.getImageableY());
                 contentPanel.paint(g2);          
                 return Printable.PAGE_EXISTS;
                }
               });
            if (pj.printDialog() == false)
            return;
            pj.print();
             if (pj.printDialog(attributes)) pj.print(attributes);
            }
               catch (PrinterException e)
                {
                  JOptionPane.showMessageDialog(Drukowanie.this, e);
                }
           }
          });

      JButton pageSetupButton = new JButton("Ustawienia");
      buttonPanel.add(pageSetupButton);
      pageSetupButton.addActionListener(new ActionListener()
         {
            public void actionPerformed(ActionEvent event)
            {
               PrinterJob job = PrinterJob.getPrinterJob();
               job.pageDialog(attributes);
            }
         });

      add(contentPanel, BorderLayout.SOUTH);
      add(buttonPanel, BorderLayout.NORTH);
   }


   private DaneFaktury danefaktury;
   private String kupujacy;
   private String firma;
   private String nrFaktur;
   private String daty;
   private String sprzedKup;
   private String podumowanie = new String();
   private final String doZaplacenia;
   private final String waluta;
   
   private PrintRequestAttributeSet attributes;
   private JPanel contentPanel;
   private JPanel panelNrFak;
   private JPanel panelKtoKomu;
   private JPanel daneFirmy;
   private JPanel daneKupca;
   private JPanel daneTowarow;
   private JPanel danePodsumowanie;

   private static final int DEFAULT_WIDTH = 500;
   private static final int DEFAULT_HEIGHT = 1300; 
    
}
