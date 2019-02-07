/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package faktury;

import ekran.EkranGlowny;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import ustawienia.Firma;
import ustawienia.Jednostki;
import ustawienia.StawkiVat;

/**
 *
 * @author wojtek
 */
public class DaneFaktury {
    
    
    public DaneFaktury()
     {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        DateFormat dateFormat2 = new SimpleDateFormat("MM/yyyy");
        Date date = new Date();
        dataWystawienia = dateFormat.format(date);
        dataPlatnosci = dateFormat.format(date);
        numerFaktury = " /"+dateFormat2.format(date);  
    }
    
    
    public void dodajPozycjeFaktury(PozycjaFaktury cos)
     {
        kupowane.add(cos);
     }
    
    public void kasujPozycjeFaktury(PozycjaFaktury cos)
     {
        kupowane.remove(cos);
     }
    
    public String getNumerFaktury()
      {   
          return numerFaktury;
      }

    public String getDataWystawienia()
      {
          return dataWystawienia;
      }
   
    public String getDataPlatnosci()
      {
          return dataPlatnosci;
      }
   
    public void setDataWystawienia(String _data)
      {
          dataWystawienia = _data;

      }
    
    public void setDataPlatnosci (String _data) 
      {
          dataPlatnosci = _data;

      }
    
    public void setNrFaktury (String _data)
      {
          numerFaktury = _data;
      }
    
    public String getNazwa() 
     {
        return nazwa;
     }
    
    public void setCena(Double cena)
     {
      cenaNetto = cena;  
     }

    public void setJednostka(int id)    
     {
        zastosowanaJednostka = EkranGlowny.jednostkiekran.listaJednostek.get(id); 
     }
    
    public void setVat(int vat)
     {
        stawkiVat = EkranGlowny.vatekran.listaVat.get(vat);
     }
    
    public void setNazwa(String nazwa)
     {
        this.nazwa = nazwa;
     }
    
    public Double  getCena()
     {
        return cenaNetto;
     }
      
    public Jednostki  getJednostka()
     {
        return zastosowanaJednostka;
     }
    
    public StawkiVat getStawkaVat()
     {
        return stawkiVat;
     }
    
    public String getNazwaVat()
     {
        return stawkiVat.getVatNazwa();
     }
    
    public Double getIloscKupowana()
     {
        return iloscKupowana;
     }
    
    public void setIloscKupowana(double iloscKupowana)
     {
        this.iloscKupowana = iloscKupowana;
     }
    
    public void setSposobPlatnosci (String spos)
     {
        sposobPlatnosci = spos;
     }
    
    public String getSposobPlatnosci()
     {
        return sposobPlatnosci;
     }

    public Object getObjectKupowane()
     {
      ArrayList obj = new ArrayList();
     
      for (int i=0;i < kupowane.size();i++)
       {
         ArrayList row = new ArrayList();
         row.add(kupowane.get(i).nazwaTowaru);
         row.add(kupowane.get(i).cenaNetto);
         row.add(kupowane.get(i).vat);
         row.add(kupowane.get(i).getJednostka().getNazwaJednostki());
         row.add(kupowane.get(i).ilosc);
         row.add(kupowane.get(i).kwotaVat);
         row.add(kupowane.get(i).wartoscBrutto);
         obj.add(row);
       }
      return obj.toString();
     }
    
    
      // clone() metoda twozaca kopie obiektu
    public Object clone() 
     {
        try 
         {
            return super.clone();
         }  catch (CloneNotSupportedException e) 
         {
            throw new Error("nieudane klonowanie");
         }
      }
    
    public Klienci klient;
    public Firma wystawca;
    public ArrayList <PozycjaFaktury> kupowane = new ArrayList<PozycjaFaktury>();
    public String nazwa;
    public Double cenaNetto;
    public Jednostki zastosowanaJednostka;
    public StawkiVat stawkiVat;
    public Double iloscKupowana;
    public String numerFaktury;
    public String data_wystawienia;
    public String data_platnosci;
    public String waluta;
    public String dataWystawienia;
    public String dataPlatnosci;
    private String sposobPlatnosci;
    
}
