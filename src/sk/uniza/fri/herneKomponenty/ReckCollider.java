package sk.uniza.fri.herneKomponenty;

import sk.uniza.fri.gulocky.Lopta;
import sk.uniza.fri.tvary.Obdlznik;

/**
 * 4/30/2022 - 10:33 PM
 *
 * Trieda, po ktorej dedia tehly a doska.
 *
 * @author Jan
 */

abstract class ReckCollider {
    protected int lavyHornyX;
    protected int lavyHornyY;
    protected int sirka;
    protected int vyska;
    protected Obdlznik model;

    /**
     * @param lopta lopta, ktorou sa hrá.
     * @return či koliduje rectangle s loptou.
     */
    public abstract Koliduje checkCollision(Lopta lopta);

    /**
     * Vráti stred dosky/tehly na osy X.
     *
     * @author: Ján Krajčík
     */
    public int getStredX() {
        return this.lavyHornyX + (this.sirka / 2);
    }
}
