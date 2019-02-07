/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ekran;

import faktury.DaneFaktury;
import faktury.PozycjaFaktury;
import faktury.Towar;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author wojtek
 */
public class FakturaNowaPomocnik extends AbstractTableModel {

    
    FakturaNowaPomocnik(ArrayList<PozycjaFaktury> _kupowane)
     {
       this.kupowane=_kupowane;
     }
    

    /**
     * metoda pozwalająca wyświetlać nazwy kolumn
     * @param kolumny
     * @return 
     */
    public String getColumnName(int kolumny){
        return nazwaKolumn[kolumny]; 
    }
    
    /**
     * metody apstrakcyjne klasy AbstractTableModel
     * 
     * @return 
     */
    @Override
    public int getRowCount() {
        return kupowane.size(); 
    }

    @Override
    public int getColumnCount() {
        return nazwaKolumn.length;
    }
    
        @Override
    public Object getValueAt(int rowIndex, int columnIndex) 
      {   
        PozycjaFaktury pozycja = kupowane.get(rowIndex);
        Towar towar = (Towar) pozycja._towar;
        
        switch (columnIndex)
         {
            case 0: return towar.getNazwa();
            case 1: return towar.getCena();
            case 2: return towar.getStawkaVat();  
            case 3: return towar.getJednostka();
            case 4: return pozycja.ilosc;
            case 5: return pozycja.zaakraglonawartoscNetto;
            case 6: return pozycja.zaakraglonaKwotaVat;
            case 7: return pozycja.zaakraglonawartoscBrutto;
         }     
        return null;  
      }   
    

    void dodajTowar(Towar towar, Double ilosc, DaneFaktury faktura) {
        this.kupowane = faktura.kupowane;
        PozycjaFaktury pozycja = new PozycjaFaktury(towar,ilosc);
        
        faktura.dodajPozycjeFaktury(pozycja);
        
        /**
         * w zmiennej adderRow zostanie zapamietna zostanie wielkosc 
         * ArrayListy -1
         * aby mozna bylo potem dodac towar na koncu wyswietlaniej tabeli (chyba)
         */
        int dodanoWiersz = faktura.kupowane.size()-1; 
        
        /**
         * odswierza tabele (zawiadamia wszystkie słuchacze ze wiersz został dodany
         */
        fireTableRowsInserted(dodanoWiersz, dodanoWiersz);
    }
    

    Towar wesTowarZIndeksu(int indeksWybranegoWiersza) 
       {
        Towar towar = kupowane.get(indeksWybranegoWiersza);        
        return (Towar) towar.clone();
       }

    void edytujTowar(int indeksWybranegoWiersza, Towar nowatowar, Double ilosc, DaneFaktury faktura) 
      {
        PozycjaFaktury pozycja = new PozycjaFaktury(nowatowar,ilosc);
        faktura.kupowane.set(indeksWybranegoWiersza, pozycja);
        fireTableDataChanged();
      }

    void kasujTowar(int indeksPierwszegoWybranegoWiersza, int ileZaznaczonych, DaneFaktury faktura) 
      {
        for (int i =0; i<ileZaznaczonych; i++)
        {
            faktura.kupowane.remove(indeksPierwszegoWybranegoWiersza);
        }
        fireTableRowsDeleted(indeksPierwszegoWybranegoWiersza, indeksPierwszegoWybranegoWiersza + ileZaznaczonych - 1 );
     }
    
    
    public static String[] nazwaKolumn = {"Nazwa Towaru", "cena netto", "stawka Vat", "jednostka", "ilość" ,"wartość netto", "kwota Vat", "wartość brutto" };
    public ArrayList<PozycjaFaktury> kupowane;

   
}
