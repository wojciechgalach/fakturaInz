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
public class Jednostki implements Cloneable,Serializable{   //implementujemy klonowanie

    public Jednostki(String nazwaJednostki) 
     {
        this.nazwaJednostki = nazwaJednostki;
     }

    /**
     * przeciążony konstruktor
     */
    public Jednostki() {}

    /**
     * publiczna metoda zwracająca nazwę jednostki
     */
    public String getNazwaJednostki() 
     {  
        return nazwaJednostki;
     }
    
    /**
     * zmiana jednostki
     */
    public void setNazwaJednostki(String nazwaJednostki)
     {
        this.nazwaJednostki= nazwaJednostki;
     }

    /**
     * Nadpisana metoda toString do wypisania (używana w terminalu) 
     *
     */
    public String toString() 
     {
        return nazwaJednostki;
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
    /**
     * Jednostki miar jakie będą stosowane w programie np: szt, metr, litr, itd
     */
    private String nazwaJednostki;

}
