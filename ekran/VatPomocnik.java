/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ekran;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;
import ustawienia.StawkiVat;

/**listaJednostek
 *
 * @author wojtek
 */
public class VatPomocnik extends AbstractTableModel {
//    private final ArrayList<StawkiVat> listaVat;

    VatPomocnik(ArrayList<StawkiVat> listaVat) 
     {
        this.listaVat=listaVat;
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
        return listaVat.size();               // zwróci wielkość tablicy
     }

    @Override
    public int getColumnCount() 
     {
        return nazwaKolumn.length;
     }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) 
     {
        StawkiVat vat = listaVat.get(rowIndex);
        
        switch (columnIndex)
         {
            case 0: return vat.getVatNazwa();
            case 1: return vat.getVat();
         }
        return null;     
     }
          
    /**
     * metoda dada jednostke i odświerzy wygląd
     *
     */
     public void dodajVat(StawkiVat vat) 
      {
        listaVat.add(vat);
        
        /**
         * w zmiennej adderRow zostanie zapamietna zostanie wielkosc 
         * ArrayListy -1
         * aby mozna bylo potem dodac towar na koncu wyswietlaniej tabeli (chyba)
         */
        int dodanoWiersz = listaVat.size()-1; 
        
        /**
         * odswierza tabele (zawiadamia wszystkie słuchacze ze wiersz został dodany
         */
        fireTableRowsInserted(dodanoWiersz, dodanoWiersz);
     }
    
    /**
     * metoda zwracajaca klon Vat z danego wiersza
     * @param nrWiersza
     * @return 
     */
    public StawkiVat wesVatZIndeksu(int nrWiersza)
     {
        StawkiVat vat = listaVat.get(nrWiersza);        
        return (StawkiVat) vat.clone();
     }
    
    /**
     * metoda pozwala edytowac Vat
     * @param indeksPierwszegoWybranegoWiersza
     * @param nowaTowar 
     */
    void edytujVat(int indeksPierwszegoWybranegoWiersza, StawkiVat nowaVat) 
      {  
        StawkiVat vat = (StawkiVat) nowaVat.clone();
        listaVat.set(indeksPierwszegoWybranegoWiersza, vat);
//        listaJednostek.set(indeksPierwszegoWybranegoWiersza, nowaJednostki);
//        fireTableRowsUpdated(indeksPierwszegoWybranegoWiersza, indeksPierwszegoWybranegoWiersza);  // powiadamia słuchacze o zmianach
         fireTableDataChanged();
      }
    
    void kasujVat(int indeksPierwszegoWybranegoWiersza, int ileZaznaczonych) 
      {
        for (int i =0; i<ileZaznaczonych; i++)
         {
            listaVat.remove(indeksPierwszegoWybranegoWiersza);
         }
        fireTableRowsDeleted(indeksPierwszegoWybranegoWiersza, indeksPierwszegoWybranegoWiersza + ileZaznaczonych - 1 );
           // zawiadamia słuchacze ze wiersze zostały usuniete
      }
    
    /**
     * specjany string do nazywania kolumn
     */
    private static String[] nazwaKolumn = {"Nazwa stawki", "stawka vat"};  
    public ArrayList<StawkiVat> listaVat;

}
