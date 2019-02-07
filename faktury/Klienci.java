/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package faktury;

import java.io.Serializable;

/**
 *
 * @author wojtek
 */
public class Klienci implements Serializable, Cloneable {
    
    
    public Klienci() {}
      
    public String getNazwaFirmy()
     {
        return nazwaFirmy;
     }
    
    public void setNazwaFirmy(String nazwaFirmy)
     {
        this.nazwaFirmy = nazwaFirmy;
     }
    
    public String getUlica()
     {
        return ulica;
     }
    
    public void setUlica(String ulica)
     {
        this.ulica = ulica;
     }
    
    public String getNrUlicy()
     {
        return nrUlicy;    
     }
    
    public void setNrUlicy(String nrUlicy)
     {
        this.nrUlicy = nrUlicy;
     }
    
    public String getNrMieszkania()
     {
        return nrMieszkania;
     }
    
    public void setNrMieszkania(String nrMieszkania)
     {
        this.nrMieszkania = nrMieszkania;
     }
    
    public String getMiasto()
     {
        return miasto;
     }
    
    public void setMiasto(String miasto) 
     {
         this.miasto = miasto;        
     }
    
    public String getKraj()
     {
        return kraj;
     }
    
    public void setKraj(String kraj)
     {
        this.kraj= kraj;
     }
    
    public String getKodPocztowy()
     {
        return kodPocztowy;
     }
    
    public void setKodPocztowy(String kodPocztowy)
     {
        this.kodPocztowy = kodPocztowy;
     }
    
    public String getNIP()
     {
        return NIP;
     }
    
    public void setNIP(String NIP)
     {
        this.NIP = NIP;
     }

    
    public String toString() 
     {
        return nazwaFirmy + " "+ NIP;
     }

    
    /**
     * Klonowanie obiektu aby można było edytować elementy
     *
     */
    @Override
    public Object clone() // klonowanie obiektu aby moc edytowac 
    {
        try // obsłuba wyjątku 
        {
            return super.clone();
        } 
        catch (CloneNotSupportedException e) // gdy kolonowanie się nie powiedzie
        {
            throw new Error("Błąd klonowania");
        }
    }
    
    public String nazwaFirmy;
    public String ulica;
    public String nrUlicy;
    public String nrMieszkania;
    public String miasto;
    public String kraj;
    public String kodPocztowy;
    public String NIP;
    
}
