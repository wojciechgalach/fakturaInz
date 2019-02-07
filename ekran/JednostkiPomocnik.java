/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ekran;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;
import ustawienia.Jednostki;

/**
 *
 * @author wojtek
 */
public class JednostkiPomocnik extends AbstractTableModel  {

    public JednostkiPomocnik(ArrayList<Jednostki> listaJednostek) 
     {
        this.listaJednostek=listaJednostek;
     }


    /**
     * metoda pozwalająca wyświetlać nazwy kolumn
     * @param kolumny
     * @return 
     */
    public String getColumnName(int kolumny)
     {
        return nazwaKolumn[kolumny]; 
     }
        
    /**
     * metody apstrakcyjne klasy AbstractTableModel
     * 
     * @return 
     */
    @Override
    public int getRowCount() 
     {
        return listaJednostek.size();               // zwróci wielkość tablicy
     }

    @Override
    public int getColumnCount() 
     {
        return nazwaKolumn.length;
     }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) 
     {
        Jednostki jednostki = listaJednostek.get(rowIndex);
        
        switch (columnIndex){
         case 0: return jednostki.getNazwaJednostki();
        }
        return null;     
     }
    
    public Object getNaweJednostki(int rowIndex) {
        Jednostki jednostki = listaJednostek.get(rowIndex);
        
          return jednostki.getNazwaJednostki(); 
    }
        
    /**
     * metoda dada jednostke i odświerzy wygląd
     *
     */
     public void dodajJednostke(Jednostki jednostka) 
      {
        listaJednostek.add(jednostka);
        
        /**
         * w zmiennej adderRow zostanie zapamietna zostanie wielkosc 
         * ArrayListy -1
         * aby mozna bylo potem dodac towar na koncu wyswietlaniej tabeli (chyba)
         */
        int dodanoWiersz = listaJednostek.size()-1; 
        
        /**
         * odswierza tabele (zawiadamia wszystkie słuchacze ze wiersz został dodany
         */
        fireTableRowsInserted(dodanoWiersz, dodanoWiersz);
      }
    
    /**
     * metoda zwracajaca klon Jednostki z danego wiersza
     * @param nrWiersza
     * @return 
     */
    public Jednostki wesJednostkiZIndeksu(int nrWiersza)
     { 
        Jednostki jednostki = listaJednostek.get(nrWiersza);        
        return (Jednostki) jednostki.clone();
     }
    
    /**
     * metoda pozwala edytowac Jednostki
     * @param indeksPierwszegoWybranegoWiersza
     * @param nowaJednostki 
     */
    void edytujJednostki(int indeksPierwszegoWybranegoWiersza, Jednostki nowaJednostki) 
     {      
        Jednostki jednostki = (Jednostki) nowaJednostki.clone();
        listaJednostek.set(indeksPierwszegoWybranegoWiersza, jednostki);
        fireTableDataChanged();
    }
    
    void kasujJednostki(int indeksPierwszegoWybranegoWiersza, int ileZaznaczonych) {
        for (int i =0; i<ileZaznaczonych; i++)
        {
         listaJednostek.remove(indeksPierwszegoWybranegoWiersza);
        }
        fireTableRowsDeleted(indeksPierwszegoWybranegoWiersza, indeksPierwszegoWybranegoWiersza + ileZaznaczonych - 1 );
           // zawiadamia słuchacze ze wiersze zostały usuniete
    }
    
    /**
     * specjany string do nazywania kolumn
     */
    private static String[] nazwaKolumn = {"Jednostka"};
    public ArrayList<Jednostki> listaJednostek;
     
}
