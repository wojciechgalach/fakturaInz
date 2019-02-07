/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ekran;

import faktury.Towar;
import java.util.LinkedList;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author wojtek
 */
public class TowarPomocnik extends AbstractTableModel {
  
    TowarPomocnik(LinkedList<Towar> listaTowar) 
     {
       this.listaTowar=listaTowar;
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
        return listaTowar.size(); 
     }

    @Override
    public int getColumnCount() 
     {
        return nazwaKolumn.length;
     }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) 
     {
        Towar towar = listaTowar.get(rowIndex);    
        switch (columnIndex)
         {
            case 0: return towar.getNazwa();
            case 1: return towar.getCena();
            case 2: return towar.getStawkaVat();
            case 3: return towar.getJednostka();
         }
        return null;  
    }   
    
    /**
     * metoda dada towar i odświerzy wygląd
     *
     */
    void dodajTowar(Towar towar) 
     {
      listaTowar.add(towar);
        
        /**
         * w zmiennej adderRow zostanie zapamietna zostanie wielkosc 
         * ArrayListy -1
         * aby mozna bylo potem dodac towar na koncu wyswietlaniej tabeli (chyba)
         */
        int dodanoWiersz = listaTowar.size()-1; 
        
        /**
         * odswierza tabele (zawiadamia wszystkie słuchacze ze wiersz został dodany
         */
        fireTableRowsInserted(dodanoWiersz, dodanoWiersz);
    }    
       
    /**
     * metoda zwracajaca klon Towar z danego wiersza
     * @param nrWiersza
     * @return 
     */
    Towar wesTowarZIndeksu(int indeksWybranegoWiersza) 
     {
        Towar towar = listaTowar.get(indeksWybranegoWiersza);        
        return (Towar) towar.clone();
     }
   
    /**
     * metoda pozwala edytowac Towar
     * @param indeksPierwszegoWybranegoWiersza
     * @param nowaJednostki 
     */
    void edytujTowar(int indeksWybranegoWiersza, Towar nowatowar) 
     {
        Towar towar = (Towar) nowatowar.clone();
        listaTowar.set(indeksWybranegoWiersza, towar);
        fireTableDataChanged();
     }

    void kasujTowar(int indeksPierwszegoWybranegoWiersza, int ileZaznaczonych) {
        for (int i =0; i<ileZaznaczonych; i++)
        {
            listaTowar.remove(indeksPierwszegoWybranegoWiersza);
        }
        fireTableRowsDeleted(indeksPierwszegoWybranegoWiersza, indeksPierwszegoWybranegoWiersza + ileZaznaczonych - 1 );
           // zawiadamia słuchacze ze wiersze zostały usuniete
    }
    
    private static String[] nazwaKolumn = {"Nazwa Towaru", "cena", "stawka Vat", "jednostka"};
    private LinkedList<Towar> listaTowar;
}
