package sk.uniza.fri.herneKomponenty;

import sk.uniza.fri.gulocky.Lopta;
import sk.uniza.fri.tvary.Obdlznik;

/**
 * Trieda Tehla vytvorí tehlu, na začiatku hry vytvorí SpravcaSteny
 * maticu tehál, ktoré pri kolízií s loptou zmiznú z hernej plochy
 * a hráčovi je pripočítané skóre.
 *
 * @author: Ján Krajčík, skupina: 5ZYR14
 * @version: 1.6 (14.12.2021)
 */
public class Tehla extends ReckCollider implements IRespawnable {
    private int hp;
    private final int povodneHP;

    /**
     * Konštruktor Tehly
     *
     * @param stlpec stĺpec, do ktorého má byť tehla umiestnená, využíva sa pri začiatočnom
     *               posune tehál
     * @param riadok riadok, do ktorého má byť tehla umiestnená, využíva sa pri začiatočnom
     *               posune tehál
     */
    public Tehla(int riadok, int stlpec, int hp) {
        this.vyska = 25;
        this.sirka = 60;
        int defaultnyLavyHornyX = 60;
        int defaultnyLavyHornyY = 50;
        //Medzera medzi tehlami na osi Y a X, pouziva sa pri kalkulacii posunu aby boli tehly
        //spravne rozlozene po ploche
        int medzeraY = 15;
        int medzeraX = 15;
        int zaciatocnyOdskokTehalOdVrchuHernejPlochy = 50;
        //Vypocita sirku Tehly + Medzera ktora medzi nimi ma byt, aby ich vedel algoritmus spravne poposuvat
        int sirkaTehlyPlusMedzera = this.sirka + medzeraX;
        //Vypocita vysku Tehly + Medzera ktora medzi nimi ma byt, aby ich vedel algoritmus spravne poposuvat
        int vyskaTehlyPlusMedzera = this.vyska + medzeraY;

        int posunTehlyX = (stlpec * sirkaTehlyPlusMedzera - defaultnyLavyHornyX) + medzeraX;
        int posunTehlyY = (riadok * vyskaTehlyPlusMedzera - defaultnyLavyHornyY) + zaciatocnyOdskokTehalOdVrchuHernejPlochy;
        //model je graficka reprezentacia tehly na obrazovke
        this.model = new Obdlznik();
        this.model.zmenFarbu("magenta");
        this.model.zmenStrany(this.sirka, this.vyska);
        this.model.posunVodorovne(posunTehlyX);
        this.model.posunZvisle(posunTehlyY);

        this.lavyHornyX = defaultnyLavyHornyX + posunTehlyX;
        this.lavyHornyY = defaultnyLavyHornyY + posunTehlyY;

        this.povodneHP = hp;
        this.hp = hp;
    }

    /**
     * Kontroluje, či koliduje nejaká tehla s loptou a z akého smeru koliduje.
     *
     * @param lopta referencia na objekt Lopta.
     * @return String  vráti z akého smeru, ak z nejakého koliduje lopta s tehlou v podobe
     * Stringu, napr. ZlavaDole.
     */

    public Koliduje checkCollision(Lopta lopta) {
        //smer lopty na osy X a Y
        int loptaSmerX = lopta.getSmerX();
        int loptaSmerY = lopta.getSmerY();
        int loptaPolomer = lopta.getPolomer();
        //hitbox os X
        int xOd = this.lavyHornyX;
        int xDo = this.lavyHornyX + this.sirka;
        //hitbox os Y
        int yOd = this.lavyHornyY;
        int yDo = this.lavyHornyY + this.vyska;

        boolean letiVpravo = lopta.getSmerX() == 1;
        boolean letiVlavo = lopta.getSmerX() == -1;
        boolean letiHore = lopta.getSmerY() == -1;
        boolean letiDole = lopta.getSmerY() == 1;

        // boolean jeVnutriAleNeprehanaTo = (lopta.getXPrava() >= xOd && lopta.getXPrava() <= (xOd + loptaPolomer)) &&
        // (lopta.getXLava() <= xDo && lopta.getXLava() >= (xDo - loptaPolomer)) &&
        // (lopta.getYHorna() <= yDo && lopta.getYHorna() >= (yDo - loptaPolomer)) &&
        // (lopta.getYDolna() >= yOd && lopta.getYDolna() <= (yOd + loptaPolomer));

        // boolean kolidujeZlavaDole = letiVpravo && letiHore && jeVnutriAleNeprehanaTo;
        // boolean kolidujeZlavaHore = letiVpravo && letiDole && jeVnutriAleNeprehanaTo;
        // boolean kolidujeSpravaDole = letiVlavo && letiHore && jeVnutriAleNeprehanaTo;
        // boolean kolidujeSpravaHore = letiVlavo && letiDole && jeVnutriAleNeprehanaTo;

        // boolean kolidujeZlavaDole = (letiVpravo && lopta.getXPrava() >= xOd  && lopta.getXLava() <= xDo && (letiHore && (lopta.getYHorna() <= yDo) && (lopta.getYHorna() >= (yDo - loptaPolomer))));
        // boolean kolidujeZlavaHore = (letiVpravo && lopta.getXPrava() >= xOd && lopta.getXPrava() <= xDo && (letiDole && (lopta.getYDolna() >= yOd && lopta.getYDolna() <= (yOd + loptaPolomer)) && (lopta.getYHorna() <= yDo)));
        // boolean kolidujeSpravaDole = (letiVlavo && lopta.getXLava() <= xDo && lopta.getXLava() >= xOd && (letiHore && (lopta.getYHorna() <= yDo) && (lopta.getYHorna() >= (yDo - loptaPolomer))));
        // boolean kolidujeSpravaHore = (letiVlavo && lopta.getXLava() <= xDo && lopta.getXLava() >= xOd && (letiDole && (lopta.getYDolna() >= yOd) && (lopta.getYDolna() <= (yOd + loptaPolomer))));

        boolean kolidujeZlavaDole = (letiVpravo && lopta.getXPrava() >= xOd && lopta.getXLava() <= xDo && (letiHore && (lopta.getYHorna() <= yDo) && (lopta.getYHorna() >= (yDo - loptaPolomer))));
        boolean kolidujeZlavaHore = (letiVpravo && lopta.getXPrava() >= xOd && lopta.getXPrava() <= xDo && (letiDole && (lopta.getYDolna() >= yOd && lopta.getYDolna() <= (yOd + loptaPolomer)) && (lopta.getYHorna() <= yDo)));
        boolean kolidujeSpravaDole = (letiVlavo && lopta.getXLava() <= xDo && lopta.getXLava() >= xOd && (letiHore && (lopta.getYHorna() <= yDo) && (lopta.getYHorna() >= (yDo - loptaPolomer))));
        boolean kolidujeSpravaHore = (letiVlavo && lopta.getXLava() <= xDo && lopta.getXLava() >= xOd && (letiDole && (lopta.getYDolna() >= yOd) && (lopta.getYDolna() <= (yOd + loptaPolomer))));

        boolean koliduje = (lopta.getXPrava() >= xOd) && (lopta.getXLava() <= xDo) && (lopta.getYHorna() < yDo) && (lopta.getYDolna() >= yOd);

        if (kolidujeZlavaHore) {
            return Koliduje.ZLAVA_HORE;
        } else if (kolidujeSpravaHore) {
            return Koliduje.SPRAVA_HORE;
        } else if (kolidujeZlavaDole) {
            return Koliduje.ZLAVA_DOLE;
        } else if (kolidujeSpravaDole) {
            return Koliduje.SPRAVA_DOLE;
        } else if (koliduje && letiVpravo) {
            return Koliduje.ZLAVA;
        } else if (koliduje && letiVlavo) {
            return Koliduje.SPRAVA;
        } else if (koliduje && letiHore) {
            return Koliduje.ZDOLA;
        } else if (koliduje && letiDole) {
            return Koliduje.ZHORA;
        } else {
            return Koliduje.NEKOLIDUJE;
        }
    }

    /**
     * Zníži HP tehly o jedno.
     */
    public void znizHPTehly() {
        if (this.hp > 0) {
            this.hp--;
            this.zobraz();
        }
    }

    /**
     * Zmaže model kolidujúcej tehly z hernej plochy.
     */
    public void zmaz() {
        this.model.skry();
    }

    /**
     * Zobrazí model tehly na hernú plochu. Farba sa mení podľa poštu HP.
     */
    public void zobraz() {
        switch (this.hp) {
            case 5 -> {
                this.model.zmenFarbu("gray");
                this.model.zobraz();
            }
            case 4 -> {
                this.model.zmenFarbu("yellow");
                this.model.zobraz();
            }
            case 3 -> {
                this.model.zmenFarbu("green");
                this.model.zobraz();
            }
            case 2 -> {
                this.model.zmenFarbu("magenta");
                this.model.zobraz();
            }
            case 1 -> {
                this.model.zmenFarbu("white");
                this.model.zobraz();
            }
            case 0 -> this.zmaz();
        }
    }

    /**
     * Nastavý HP tehly podľa parametra.
     * @param hp na aké HP sa má nastaviť HP tehly.
     */
    public void setHp(int hp) {
        this.hp = hp;
        this.zobraz();
    }

    /**
     *Znovuzrodí tehlu s takými HP, aké mala pri vygenerovaní mapy.
     */
    public void respawn() {
        this.hp = this.povodneHP;
        this.zobraz();
    }

    /**
     * @return Vráti, či tehla má hp viac ako 0.
     */
    public boolean zije() {
        return this.hp > 0;
    }

    /**
     * @return vráti súradnicu stredu tehly na osy X.
     */
    public int getStredX() {
        return this.lavyHornyX + (this.sirka / 2);
    }

    /**
     * @return vráti súradnicu stredu tehly na osy Y.
     */
    public int getStredY() {
        return this.lavyHornyY + (this.vyska / 2);
    }
}
