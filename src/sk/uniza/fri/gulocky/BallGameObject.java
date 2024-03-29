package sk.uniza.fri.gulocky;

import sk.uniza.fri.tvary.Kruh;

/**
 * 4/30/2022 - 10:33 PM
 *
 * @author Jan
 */
public abstract class BallGameObject {
    protected static final int DEFAULTNE_X_KRUHU = 20;
    protected static final int DEFAULTNE_Y_KRUHU = 60;

    protected final int priemer;
    protected final int polomer;
    protected int delta;

    protected int stredX;
    protected int stredY;

    protected final Kruh model;

    protected int vyskaHernehoPola;


    /**
     * Konštruktor predka lopty a powerUp-u
     * @param priemer           Priemer loptového objektu.
     * @param vyskaHernehoPola  Dôležitá pre to, aby objekt vedel, kedy sa má despawnúť.
     */
    protected BallGameObject(int priemer, int vyskaHernehoPola) {
        this.priemer = priemer;
        this.polomer = priemer / 2;
        this.vyskaHernehoPola = vyskaHernehoPola;

        this.model = new Kruh();
        this.model.zmenPriemer(this.priemer);
        this.model.zobraz();
    }

    public int getPolomer() {
        return this.polomer;
    }

    /**
     * Vráti X-ovú súradnicú lavej hrany lopty.
     *
     * @return int - X-ová súradnica lavej hrany lopty.
     */
    public int getXLava() {
        return this.stredX - this.getPolomer();
    }

    /**
     * Vráti X-ovú súradnicú pravej hrany lopty.
     *
     * @return vráti X-ovú súradnicu pravej hrany lopty.
     */
    public int getXPrava() {
        return this.stredX + this.getPolomer();
    }

    /**
     * Vráti Y-ovú súradnicú spodnej hrany lopty.
     *
     * @return vráti Y-ovú súradnicu spodnej hrany lopty.
     */
    public int getYDolna() {
        return this.getStredY() + this.getPolomer();
    }


    /**
     * Vráti Y-ovú súradnicú stredu lopty.
     *
     * @return Y-ová súradnica stredu lopty.
     */
    public int getStredY() {
        return this.stredY;
    }

    /**
     * Skryje model(kruh) reprezentujuci objekt z platna.
     */
    public void skry() {
        this.model.skry();
    }

    /**
     * @return vrati hodnotu true alebo false podla toho, ci objekt
     * presiel (svojím stredom) pod dolnu hranicu herneho pola(pod dosku).
     */
    public boolean jeMimoDole() {
        return this.stredY > this.vyskaHernehoPola;
    }

    /**
     * Metodka, ktora posunie objekt, avsak ked po posune je pod doskou, vrati false
     * @return vráti hodnotu true alebo false podľa toho, či sa ešte mohol objekt posunúť alebo nie.
     */
    public abstract boolean posunSa();
}
