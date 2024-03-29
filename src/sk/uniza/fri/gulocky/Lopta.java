package sk.uniza.fri.gulocky;

import sk.uniza.fri.herneKomponenty.IRespawnable;

import java.util.Random;

/**
 * Trieda Lopta slúži na vytvorenie lopty, ktorá "lieta" po hernej ploche, odráža
 * sa od dosky a rozbíja tehly. Na začiatku hry a vždy po tom, čo spadne mimo
 * hernú plochu sa znovu objaví v strede na spodku obrazovky na doske, a hráč
 * ju môže znovu "vystreliť", pokiaľ teda ešte neskončila hra na výhru alebo prehru.
 *
 * @author Z cvičení, mierne vylepšil Ján Krajčík
 * @version 1.4 (18.12.2021)
 */

public class Lopta extends BallGameObject implements IRespawnable {

    private final int defaultneX;
    private final int defaultneY;

    private int smerX;
    private int smerY;

    // hitboxy
    private int loptaYHorna;


    private int pocetOdrazov;
    private int sirkaHernehoPola;
    private int vyskaHernehoPola;

    /**
     * Konštruktor lopty, vďaka parametrom umožňuje fungovanie lopty
     * na dynamicky veľkej hernej ploche.
     *
     * @param sirkaHernehoPola šírka herného poľa, musí byť pochopyteľne kladné číslo.
     * @param vyskaHernehoPola šírka herného poľa, musí byť pochopyteľne kladné číslo.
     */
    public Lopta(int sirkaHernehoPola, int vyskaHernehoPola) {
        super(10, vyskaHernehoPola);
        this.stredX = 100;
        this.stredY = 100;

        this.defaultneX = sirkaHernehoPola / 2;
        this.defaultneY = vyskaHernehoPola - this.priemer;

        this.vyskaHernehoPola = vyskaHernehoPola;
        this.sirkaHernehoPola = sirkaHernehoPola;

        this.model.posunVodorovne(-DEFAULTNE_X_KRUHU + stredX - this.getPolomer());
        this.model.posunZvisle(-DEFAULTNE_Y_KRUHU + stredY - this.getPolomer());

        this.delta = 5;
        this.smerY = 1;
        this.smerX = 1;
    }

    /**
     * Umiestni loptu do stredu šírky herného poľa(s najväčšou pravdepodobnosťou aj dosku),
     * a na spodok herného poľa (na dosku), toto sa vykonáva ak lopta spadne pod hranicu herného poľa,
     * alebo pri spustení hry. Po stlačení klávesy SPACE sa lopta "vystrelý" 45° vľavo alebo vpravo
     * hore.
     */
    public void respawn() {
        Random rnd = new Random();

        this.model.posunVodorovne(this.defaultneX - this.stredX);
        this.model.posunZvisle(this.defaultneY - this.stredY);

        this.stredX = this.defaultneX;
        this.stredY = this.defaultneY;
        if (rnd.nextBoolean()) {
            this.smerX *= -1;
        }
        this.smerY = -1;
    }

    /**
     * Posunie loptu jej aktuálnym smeromX a Y o jej deltu (vzdialenosť,
     * o ktorú sa posúva).
     * @return vráti, či sa lopta posunula pod dosku.
     */
    @Override
    public boolean posunSa() {
        int posunX = this.smerX * this.delta;
        int posunY = this.smerY * this.delta;

        this.stredX += posunX;
        this.stredY += posunY;
        this.model.posunVodorovne(posunX);
        this.model.posunZvisle(posunY);
        return this.jeMimoDole();
    }

    /**
     * Vráti smer lopty na osy X.
     *
     * @return int(celé číslo) -1 ak smer lopty na osy X je vpravo,
     * +1 ak smer lopty na osy Y je vľavo. nikdy nevracia iné hodnoty.
     */
    public int getSmerX() {
        return this.smerX;
    }

    /**
     * Vráti smer lopty na osy Y.
     *
     * @return int(celé číslo) -1 ak smer lopty na osy Y je dole,
     * +1 ak smer lopty na osy Y je hore. nikdy nevracia iné hodnoty.
     */
    public int getSmerY() {
        return this.smerY;
    }

    /**
     * Zmení smer lopty na osy Y na -1, teda lopta sa pri pohybe
     * (volaní metódy posunSa()) hude hýbať smerom hore,kým sa
     * jej smer nezmení.
     */
    public void odrazHore() {
        this.smerY = -1;
    }

    /**
     * Zmení smer lopty na osy Y na +1, teda lopta sa pri pohybe
     * (volaní metódy posunSa()) hude hýbať smerom dole,kým sa
     * jej smer na osy Y nezmení.
     */
    public void odrazDole() {
        this.smerY = 1;
    }

    /**
     * Zmení smer lopty na osy X na -1, teda lopta sa pri pohybe
     * (volaní metódy posunSa()) hude hýbať smerom vľavo,kým sa
     * jej smer na osy X nezmení.
     */
    public void odrazDolava() {
        this.smerX = -1;
    }

    /**
     * Zmení smer lopty na osy X na +1, teda lopta sa pri pohybe
     * (volaní metódy posunSa()) hude hýbať smerom vpravo,kým sa
     * jej smer na osy X nezmení.
     */
    public void odrazDoprava() {
        this.smerX = 1;
    }

    /**
     * Zmení smer lopty na osy Y na -1 ak bol pôvodne +1, a na +1,
     * ak bol pôvodne -1, teda lopta sa pri pohybe (volaní metódy
     * posunSa()) hude hýbať na osy Y opačným smerom, ako pred
     * zavolaním tejto metódy.
     */
    public void odrazZvisle() {
        this.smerY *= -1;
    }

    /**
     * Zmení smer lopty na osy X na -1 ak bol pôvodne +1, a na +1,
     * ak bol pôvodne -1, teda lopta sa pri pohybe (volaní metódy
     * posunSa()) hude hýbať na osy X opačným smerom, ako pred
     * zavolaním tejto metódy.
     */
    public void odrazHorizontalne() {
        this.smerX *= -1;
    }

    /**
     * @return vráti hodnotu true alebo false podľa toho, či lopta
     * narazila (svojou vrchnou hranou) do vrchu herného poľa.
     */
    public boolean narazilaHore() {
        return this.stredY - this.getPolomer() <= 0;
    }

    /**
     * @return vráti hodnotu true alebo false podľa toho, či lopta
     * narazila (svojou pravou hranou) do pravej hrany herného poľa.
     */
    public boolean narazilaVpravo() {
        return this.stredX + this.getPolomer() >= sirkaHernehoPola;
    }

    /**
     * @return vráti hodnotu true alebo false podľa toho, či lopta
     * narazila (svojou ľavou hranou) do ľavej hrany herného poľa.
     */
    public boolean narazilaVlavo() {
        return this.stredX - this.getPolomer() <= 0;
    }

    /**
     * Vráti X-ovú súradnicú stredu lopty.
     *
     * @return X-ová súradnica stredu lopty.
     */
    public int getStredX() {
        return this.stredX;
    }


    /**
     * Vráti X-ovú súradnicú lavej hrany lopty.
     *
     * @return vráti X-ovú súradnicu lavej hrany lopty.
     */
    public int getXLava() {
        return this.stredX - this.polomer;
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
     * Vráti Y-ovú súradnicú vrchnej hrany lopty.
     *
     * @return vráti Y-ovú súradnicu vrchnej hrany lopty.
     */
    public int getYHorna() {
        return this.getStredY() - this.getPolomer();
    }

    /**
     * Zmeni farbu vizualneho modelu Lopty na pozadovanu, podla parametra.
     * @param farba na kotru sa ma model zmenit.
     */
    public void zmenFarbu(String farba) {
        this.model.zmenFarbu(farba);
    }
}
