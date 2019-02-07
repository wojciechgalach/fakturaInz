/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package faktury;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

/**
 *
 * @author wojtek
 */
public class PozycjaFaktury extends Towar{
    
     public PozycjaFaktury(Towar towar, Double ilosc)
      {
        this.setNazwa(towar.getNazwa());
        this.setCena(towar.getCena());
        this._towar = towar;
        this.ilosc = ilosc;
        stawkaVat = _towar.getStawkaVat().getVat();
        jednostki = _towar.getJednostka().getNazwaJednostki();
        cenaNetto = _towar.getCena(); 
        this.wartoscNetto = _towar.getCena() * ilosc;        
        this.kwotaVat = (this.wartoscNetto * (_towar.getStawkaVat().getVat())/100)+0.00499;   // +0.00499 aby zaakrąglał jak skarbówka a nie matematyka         
        this.wartoscBrutto = this.wartoscNetto + this.kwotaVat;    
        vatNazwa = _towar.getStawkaVat().getVatNazwa();
        
       DecimalFormat f = (DecimalFormat) NumberFormat.getInstance(Locale.US);
        f.setMaximumFractionDigits(2);
        
        zaakraglonaKwotaVat=f.format(kwotaVat);
        zaakraglonawartoscNetto= f.format(wartoscNetto);
        zaakraglonawartoscBrutto = f.format(wartoscBrutto);
        zaakraglonawartoscNetto = zaakraglonawartoscNetto.replaceAll(",", "");
        zaakraglonawartoscBrutto = zaakraglonawartoscBrutto.replaceAll(",", "");
        zaakraglonaKwotaVat = zaakraglonaKwotaVat.replaceAll(",", "");  //.replace(',', ' ');
    }
    
    public Double getCenaNetto()
     {
        return wartoscNetto;
     }
    
    public String getNazwaVat() 
     {
        return vatNazwa;
     }
        
    public Towar _towar;
    public String nazwaTowaru;
    public Double cenaNetto;
    public Double stawkaVat;
    public String jednostki;
    public Double ilosc;
    public Double kwotaVat;
    public String zaakraglonaKwotaVat;
    public Double wartoscNetto;
    public String zaakraglonawartoscNetto;
    public Double wartoscBrutto;
    public String zaakraglonawartoscBrutto;
    public String vatNazwa;

}
