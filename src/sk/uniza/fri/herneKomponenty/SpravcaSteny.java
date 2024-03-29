package sk.uniza.fri.herneKomponenty;

import sk.uniza.fri.gulocky.Lopta;

/**
 * SpravcaSteny vie vygenerovať maticu tehál na herné poľe, nájsť tehly kolidujúce s loptou
 * a vymazať ich z herného poľa.
 *
 * @author: Ján Krajčík
 * @version: 1.6(16.12.2021)
 */
public class SpravcaSteny {
    private Tehla[][] poleTehal;
    private boolean narazilaNejaka;
    private int pocetRiadkov;
    private int pocetStlpcov;
    private int maxSkore;

    private BlockBreaker blockBreaker;

    /**
     * Konštruktor správcu steny.
     */
    public void setBlockBreaker(BlockBreaker blockBreaker) {
        this.blockBreaker = blockBreaker;
    }

    /**
     * Vygeneruje tehly na základe dvojrozmerného poľa intov, kde každý prvok poľa je jedna tehla.
     * @param mapa dvojrozmerné poľe intov, reprezentujúcich HPs.
     */
    public void vygenerujTehly(int[][] mapa) {
        this.pocetRiadkov = mapa.length;
        this.pocetStlpcov = mapa[0].length;
        this.poleTehal = new Tehla[this.pocetRiadkov][this.pocetStlpcov];

        for (int riadok = 0; riadok < mapa.length; riadok++) {
            for (int stlpec = 0; stlpec < mapa[0].length; stlpec++) {
                this.setTehla(riadok, stlpec, mapa[riadok][stlpec]);
                this.poleTehal[riadok][stlpec].zobraz();
                this.maxSkore += mapa[riadok][stlpec];
            }
        }
    }

    /**
     * Nastaví počet HP tehle na danej pozícií.
     * @param riadok  v ktorom majú byť zmenené HP tehly.
     * @param stlpec  v ktorom majú byť zmenené HP tehly.
     * @param hp      počet HP
     */
    public void setTehla(int riadok, int stlpec, int hp) {
        try {
            this.poleTehal[riadok][stlpec] = new Tehla(riadok, stlpec, hp);
        } catch (Exception e) {
            System.out.println("Došlo ku chybe pri načítaní mapy zo súboru");
        }
    }

    /**
     * Nájde a zruší tehly, ktoré kolidujú s loptou z herného poľa.
     *
     * @param lopta  referencia na loptu
     */
    public void najdiAZnicKolidujuceTehly(Lopta lopta) {
        this.narazilaNejaka = false;
        for (int riadok = 0; riadok < this.pocetRiadkov; riadok++) {
            for (int stlpec = 0; stlpec < this.pocetStlpcov; stlpec++) {
                if (this.poleTehal[riadok][stlpec].zije() && !this.narazilaNejaka) {
                    switch (this.poleTehal[riadok][stlpec].checkCollision(lopta)) {
                        case ZLAVA_HORE:
                            lopta.odrazDoprava();
                            lopta.odrazHore();
                            this.znizHPTehly(riadok, stlpec);
                            break;

                        case SPRAVA_HORE:
                            lopta.odrazDolava();
                            lopta.odrazHore();
                            this.znizHPTehly(riadok, stlpec);
                            break;

                        case ZLAVA_DOLE:
                            lopta.odrazDoprava();
                            lopta.odrazDole();
                            this.znizHPTehly(riadok, stlpec);
                            break;

                        case SPRAVA_DOLE:
                            lopta.odrazDolava();
                            lopta.odrazDole();
                            this.znizHPTehly(riadok, stlpec);
                            break;

                        case ZLAVA:
                            lopta.odrazDolava();
                            this.znizHPTehly(riadok, stlpec);
                            break;

                        case SPRAVA:
                            lopta.odrazDoprava();
                            this.znizHPTehly(riadok, stlpec);
                            break;

                        case ZDOLA:
                            lopta.odrazDole();
                            this.znizHPTehly(riadok, stlpec);
                            break;

                        case ZHORA:
                            lopta.odrazHore();
                            this.znizHPTehly(riadok, stlpec);
                            break;

                        case NEKOLIDUJE:
                            break;
                    }
                }
            }
        }
    }

    /**
     * Zruší tehlu z herného poľa, pričom zvýši skóre.
     *  @param riadok       riadok v ktorom sa nachádza tehla
     *  @param stlpec       stĺpec, v ktorom sa nachádza tehla
     */
    public void znizHPTehly(int riadok, int stlpec) {
        this.poleTehal[riadok][stlpec].znizHPTehly();
        this.narazilaNejaka = true;
        this.blockBreaker.zvysSkore();
        this.blockBreaker.vygenerujPowerOrb(this.poleTehal[riadok][stlpec].getStredX(), this.poleTehal[riadok][stlpec].getStredY());
    }


    /**
     * Vráti score, ktoré sa dá získať vybúchaním všetkých tehál.
     * @return maximálne skóre
     */
    public int getMaxSkore() {
        return this.maxSkore;
    }

    /**
     * Postaví stenu z tehál tak, ako bola na začiatku hry.
     */
    public void resetujStenu() {
        for (int riadok = 0; riadok < this.pocetRiadkov; riadok++) {
            for (int stlpec = 0; stlpec < this.pocetStlpcov; stlpec++) {
                this.poleTehal[riadok][stlpec].respawn();
            }
        }
    }
}