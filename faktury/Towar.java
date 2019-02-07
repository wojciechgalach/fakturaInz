/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package faktury;

import ekran.JednostkiEkran;
import ekran.JednostkiPomocnik;
import ekran.VatEkran;
import ekran.*;
import java.io.Serializable;
import java.util.ArrayList;
import ustawienia.Jednostki;
import ustawienia.StawkiVat;

/**
 *
 * @author wojtek
 */

public class Towar implements Cloneable, Serializable {

     public Towar(String n, double c, int id, int id2) 
      {
        nazwa = n;
        cenaNetto = c;
        _idVat = id2;
        _idJednostki = id;

        Jednostki jednostka = (Jednostki) EkranGlowny.jednostkiekran.listaJednostek.get(id).clone();
        zastosowanaJednostka = jednostka;
        StawkiVat stawka = (StawkiVat)EkranGlowny.vatekran.listaVat.get(id2).clone();
        stawkiVat =  stawka;
        vat = stawkiVat.getVat();
     }
     public Towar (String n, double c, double _vat)
      {       
      }

    public Towar() 
     {   
     }
    
    public String toString()
     {
        return nazwa +"  "+cenaNetto+ "  "+zastosowanaJednostka+ "  "+stawkiVat;      
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
    public void setJednostka(Jednostki _jednostka)    
     {
        zastosowanaJednostka = _jednostka;
     }
    
    public void setVat(int vat)
     {
        stawkiVat = EkranGlowny.vatekran.listaVat.get(vat);
     }
    public void setVat2(Double _vat)
     {
        vat = _vat;
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
    public Double getStawkaVatValue()
     {
        return vat;
     }
    
      // clone() metoda twozaca kopie obiektu
    public Object clone() 
     {
        try 
         {
            return super.clone();
         } catch (CloneNotSupportedException e) 
         {
            throw new Error("nieudane klonowanie");
         }
      }
    
   
    private int id;  
    private String nazwa;
    private double cenaNetto;
    private boolean czyWyswietlac;                           
    private boolean czyUzywany;     
    private Jednostki zastosowanaJednostka;
    public StawkiVat stawkiVat;
    public Double vat;
    private int _idVat;
    private int _idJednostki;
    
}
