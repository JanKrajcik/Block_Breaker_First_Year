package sk.uniza.fri.tvary;

import java.awt.Rectangle;

/**
 * Obdĺžnik, s ktorým možno pohybovať a nakreslí sa na plátno.
 *
 * @author  Michael Kolling and David J. Barnes, Ján Krajcik
 * @version 1.1
 */

public class Obdlznik extends Tvar {
    private int stranaA;
    private int stranaB;

    /**
     * Vytvor nový obdĺžnik preddefinovanej farby na preddefinovanej pozícii.
     */
    public Obdlznik() {
        super();
        this.stranaA = 30;
        this.stranaB = 60;
        this.lavyHornyX = 60;
        this.lavyHornyY = 50;
        this.farba = "red";
        //this.jeViditelny = false;
    }

    /**
     * (Obdĺžnik) Zmeň dĺžky strán na hodnoty dané parametrami.
     * Dĺžka strany musí byť nezáporné celé číslo.
     */
    public void zmenStrany(int stranaA, int stranaB) {
        this.zmaz();
        this.stranaA = stranaA;
        this.stranaB = stranaB;
        this.nakresli();
    }

    /**
     * Draw the square with current specifications on screen.
     * V tejto triede som zakomentoval canvas.wait(10),
     * aby hra bežala plynulejšie.
     */
    @Override
    protected void nakresli() {
        if (this.jeViditelny) {
            Platno canvas = Platno.dajPlatno();
            canvas.draw(this, this.farba,
                    new Rectangle(this.lavyHornyX, this.lavyHornyY, this.stranaA, this.stranaB));
            // canvas.wait(10);
        }
    }
}
