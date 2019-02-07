/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ekran;

import faktury.Klienci;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author wojtek
 */
public class KlienciPomocnik extends AbstractTableModel {
    
    public KlienciPomocnik(ArrayList<Klienci> listaKlienci)
    {
        this.listaKlienci=listaKlienci;      
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
        return listaKlienci.size();               // zwróci wielkość tablicy
     }

    @Override
    public int getColumnCount() 
     {
        return nazwaKolumn.length;
     }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) 
     {
        Klienci klienic = listaKlienci.get(rowIndex);
        
        switch (columnIndex)
         {
            case 0: return klienic.getNazwaFirmy();
            case 1: return klienic.getUlica();
            case 2: return klienic.getNrUlicy();
            case 3: return klienic.getNrMieszkania();    
            case 4: return klienic.getMiasto();
            case 5: return klienic.getKodPocztowy();
            case 6: return klienic.getNIP();
            case 7: return klienic.getKraj();                      
         }
        return null;     
     }   
    
    
    /**
     * metoda dada Klienta i odświerzy wygląd
     *
     */
    void dodajKlient(Klienci klient) 
     {
      listaKlienci.add(klient);
        
        /**
         * w zmiennej adderRow zostanie zapamietna zostanie wielkosc 
         * ArrayListy -1
         * aby mozna bylo potem dodac towar na koncu wyswietlaniej tabeli (chyba)
         */
       int dodanoWiersz = listaKlienci.size()-1; 
        
        /**
         * odswierza tabele (zawiadamia wszystkie słuchacze ze wiersz został dodany
         */
       fireTableRowsInserted(dodanoWiersz, dodanoWiersz);
    }

    /**
     * metoda zwracajaca klon Klienta z danego wiersza
     * @param nrWiersza
     * @return 
     */
    Klienci wesKlientZIndeksu(int nrWiersza) 
     {        
        Klienci klient = listaKlienci.get(nrWiersza);        
        return (Klienci) klient.clone();
     }

    
        /**
     * metoda pozwala edytowac Klienta
     * @param indeksPierwszegoWybranegoWiersza
     * @param nowaTowar 
     */
    void edytujKlient(int indeksWybranegoWiersza, Klienci nowaklient) 
     {      
        Klienci klient = (Klienci) nowaklient.clone();
        listaKlienci.set(indeksWybranegoWiersza, klient);
        fireTableDataChanged();
     }

    void kasujKlient(int indeksPierwszegoWybranegoWiersza, int ileZaznaczonych) 
     {
        for (int i =0; i<ileZaznaczonych; i++)
         {
            listaKlienci.remove(indeksPierwszegoWybranegoWiersza);
         }
        fireTableRowsDeleted(indeksPierwszegoWybranegoWiersza, indeksPierwszegoWybranegoWiersza + ileZaznaczonych - 1 );
           // zawiadamia słuchacze ze wiersze zostały usuniete
     }
    
    private static String[] nazwaKolumn = {"Nazwa Firmy", "Ulica", "Nr Ulicy", "nr Mieszkania", "Miasto", "Kod Pocztowy", "NIP", "Kraj"};  
    private ArrayList<Klienci> listaKlienci;
   
}
