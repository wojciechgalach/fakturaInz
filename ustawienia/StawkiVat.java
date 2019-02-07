/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ustawienia;

import java.io.Serializable;


/**
 *
 * @author wojtek
 */
public class StawkiVat implements Cloneable, Serializable{
    
    /**
     * konstruktory stawek vat
     */
    public StawkiVat() {}
    
    public StawkiVat(double vat)
     {
        this.vat=vat;
        vatString=Double.toString(this.vat); 
     }
    
    public StawkiVat(String svat)
     {
        vatString=svat;
        vat=0;
     }
    
    public StawkiVat(String svat, double _vat)
     {
        vatString=svat;
        vat = _vat;
     }
    
    /**
     * publiczna metoda zwracająca stawke vat
     */
    public double getVat()  
     {
        return this.vat;
     }
    
    /**
     * publiczna metoda zwracająca nazwę stawki vat
     */
    public String getVatNazwa()  
     {
        return vatString;
     }
       
    /**
     * zmiana stawki vat
     */
    public void setLiczbaStawki(double vat)
     {
        this.vat = vat;
     }

    /**
     * zmiana nazwy stawki vat
     */
    public void setNazwaStawki(String vatString)
     {
        this.vatString= vatString;
     }
    
    /**
     * zmiana nazwy stawki vat i samej stawki vat 2 w jednym (jak to zadziała to dwa wyżej są niepotrzebne)
     */
    public void setStawkiVat(String vatString, double vat)
     {
        this.vatString= vatString;
        this.vat = vat;       
     }    
    
    
    public String toString() 
     {
        return vatString;
     }
    
    @Override
    public Object clone()                                                       // klonowanie obiektu aby moc edytowac 
     {
       try                                                                      // obsłuba wyjątku 
        {
          return super.clone();
        } 
       catch (CloneNotSupportedException e)                                     // gdy kolonowanie się nie powiedzie
        {
          throw new Error("Błąd klonowania");
        }
     }

    private double vat;
    private String vatString;
    
}
