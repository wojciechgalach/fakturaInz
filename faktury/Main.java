/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package faktury;

import ekran.EkranGlowny;
import java.awt.EventQueue;

/**
 *
 * @author wojtek
 */
public class Main {

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() 
             {
               ekranGlowny.setVisible(true);   
             }
        });
    }
    
public static  EkranGlowny ekranGlowny = new EkranGlowny();
}
