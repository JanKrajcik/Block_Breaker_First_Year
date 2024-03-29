package sk.uniza.fri.tvary;

import java.awt.geom.Ellipse2D;

/**
 * Kruh, s ktorým možno pohybovať a nakreslí sa na plátno.
 * 
 * @author  Michael Kolling and David J. Barnes,
 * @author  Ján Krajčík
 * @version 1.0  (15 July 2000)
 */

public class Kruh extends Tvar {
    private int priemer;
    
    /**
     * Vytvor nový kruh preddefinovanej farby na preddefinovanej pozícii. 
     */
    public Kruh() {
        super();
        this.priemer = 30;
        this.lavyHornyX = 20;
        this.lavyHornyY = 60;
        this.farba = "blue";
        //this.jeViditelny = false;
    }

    /**
     * (Kruh) Zmeň priemer na hodnotu danú parametrom.
     * Priemer musí byť nezáporné celé číslo. 
     */
    public void zmenPriemer(int priemer) {
        this.zmaz();
        this.priemer = priemer;
        this.nakresli();
    }

    /*
     * Draw the circle with current specifications on screen.
     */
    protected void nakresli() {
        if (this.jeViditelny) {
            Platno canvas = Platno.dajPlatno();
            canvas.draw(this, this.farba, new Ellipse2D.Double(this.lavyHornyX, this.lavyHornyY, 
                                                          this.priemer, this.priemer));
            canvas.wait(10);
        }
    }
}
