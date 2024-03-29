package sk.uniza.fri.herneKomponenty;

/**
 * Vykreslí na plátno tri číslice, ktoré reprezentujú skóre, ktoré hráč nahral,
 * Ak hráč rozbije všetky tehly na hernej ploche, pošle správu BlockBreaker-u,
 * ktorí dá vytvoriť novú stenu.
 *
 *
 * @author Ján Krajčík, skupina: 5ZYR14
 * @version 1.3 (21.12.2021)
 */

public class Skore {
    private int celkoveSkore;
    private int wallScore;
    private final int maximalneSkoreLevelu;
    private final SegmentovyZnak znakJednotky;
    private final SegmentovyZnak znakDesiatky;
    private final SegmentovyZnak znakStovky;
    /**
     * Konštruktor Skóre. Na plátno vykreslí tri číslice.
     *
     * @param  maximalneSkore    ak hráč dosiahne maximálne skóre v levely,
     *                           pošle správu BlockBreaker-u, ktrý dá vytvoriť
     *                           novú stenu.
     * @param  vyskaHernehoPola  dôležitá pri umiestňovaní skóre tabuľky na
     *                           plátno tak, aby bola pod hracou plochou.
     * @param  vyskaDosky        dôležitá pri umiestňovaní skóre tabuľky na
     *                           plátno tak, aby bola pod hracou plochou.
     */
    public Skore(int maximalneSkore, int vyskaHernehoPola, int vyskaDosky) {
        this.celkoveSkore = 0;
        this.wallScore = 0;
        this.maximalneSkoreLevelu = maximalneSkore;
        //bulharska konštanta
        int stranaASegmentuSegmentovehoZnaku = 12;
        int stranaBSegmentuSegmentovehoZnaku = 2;

        int medzeraMedziZnakmi = stranaASegmentuSegmentovehoZnaku / 2;
        int ySuradnicaZnaku = vyskaHernehoPola + vyskaDosky + medzeraMedziZnakmi;
        int vzdialenostMedziZaciatkomZnakov = stranaASegmentuSegmentovehoZnaku + stranaBSegmentuSegmentovehoZnaku + medzeraMedziZnakmi;
        this.znakJednotky = new SegmentovyZnak(vzdialenostMedziZaciatkomZnakov * 2 + medzeraMedziZnakmi, ySuradnicaZnaku, stranaASegmentuSegmentovehoZnaku, stranaBSegmentuSegmentovehoZnaku);
        this.znakDesiatky = new SegmentovyZnak(vzdialenostMedziZaciatkomZnakov + medzeraMedziZnakmi, ySuradnicaZnaku, stranaASegmentuSegmentovehoZnaku, stranaBSegmentuSegmentovehoZnaku);
        this.znakStovky = new SegmentovyZnak(medzeraMedziZnakmi, ySuradnicaZnaku, stranaASegmentuSegmentovehoZnaku, stranaBSegmentuSegmentovehoZnaku);
        this.vykresliSkore();
    }

    /**
     * Vykreslí plátno na skóre tabuľku.
     */
    public void vykresliSkore() {
        int stovky = this.celkoveSkore / 100;
        int desiatky = this.celkoveSkore / 10 - stovky * 10;
        int jednotky = this.celkoveSkore - desiatky * 10 - stovky * 100;

        this.znakStovky.zobrazCislice(stovky);
        this.znakDesiatky.zobrazCislice(desiatky);
        this.znakJednotky.zobrazCislice(jednotky);
    }

    /**
     * Vráti aktuálny stav celkového skóre.
     */
    public int getSkore() {
        return this.celkoveSkore;
    }

    /**
     * Zvýši aktuálne celkové skóre, ak hráč rozbil všetky tehly na hernej ploche,
     * oznámy to BlockBreaker-u a ten postavý novú stenu.
     *
     * @param  blockBreaker  referenfia na blockbreaker, aby sa mu dala poslať správa,
     *  že všetky tehly na hernej ploche sú rozbité.
     */
    public void zvysSkore(BlockBreaker blockBreaker) {
        this.celkoveSkore++;
        this.wallScore++;
        if (this.wallScore == this.maximalneSkoreLevelu) {
            blockBreaker.koniecSteny();
            this.wallScore = 0;
        }

        this.vykresliSkore();
    }

    /**
     * Vynuluje skóre a vykreslí ho na okno programu.
     */
    public void resetujSkore() {
        this.celkoveSkore = 0;
        this.wallScore = 0;
        this.vykresliSkore();
    }
}
