/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package druk;

import faktury.PozycjaFaktury;
import faktury.Towar;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author wojtek
 */
public class DrukowaniePomocnik extends AbstractTableModel {

    
    DrukowaniePomocnik(ArrayList<PozycjaFaktury> _kupowane)
     {
       this.kupowane=_kupowane;
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
        return kupowane.size(); 
     }

    @Override
    public int getColumnCount() 
     {
        return nazwaKolumn.length;
     }
    
        @Override
    public Object getValueAt(int rowIndex, int columnIndex) 
     {  
        PozycjaFaktury pozycja = kupowane.get(rowIndex);
        Towar towar = (Towar) pozycja._towar;
        
        switch (columnIndex){
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
 
    public static String[] nazwaKolumn = {"1-Nazwa", "2", "3", "4", "5" ,"6", "7", "8" }; 
    public ArrayList<PozycjaFaktury> kupowane;
   
}
