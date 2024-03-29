package sk.uniza.fri.herneKomponenty;

import sk.uniza.fri.gulocky.Lopta;
import sk.uniza.fri.gulocky.PowerUp;
import sk.uniza.fri.tvary.Obdlznik;

import java.util.ArrayList;

/**
 * Trieda Doska umožňuje vytvoriť obdĺžnikovú hernú dosku,
 * ktorá sa objaví na hernej ploche a hráč ju môže ovládať
 * pomocou šípok na klávesnici.
 *
 * @author: Ján Krajčík, skupina: 5ZYR14 upravil kód z cvičení.
 * (bližšie špecifikované v komentároch jednotlivých metód)
 * @version: 1.3 (30.12.2021)
 */
public class Doska extends ReckCollider implements IRespawnable {
    private final int sirkaHernehoPola;
    private final int defaultSirkaDosky;
    private final int delta;

    private int xOd;
    private int xDo;
    private int yOd;

    /**
     * Konštruktor dosky
     *
     * @author Ján Krajčík upravil kód z cvičení tak, aby bolo možné vytvárať dosku
     * na dynamicky veľkej hernej ploche, s dynamickou rýchlosťou a šírkou dosky.
     */
    public Doska(int sirkaHernehoPola, int vyskaHernehoPola, int sirkaDosky, int deltaDosky) {
        this.vyska = 10;
        this.sirka = sirkaDosky;
        this.defaultSirkaDosky = sirkaDosky;

        int defaultLavyHornyX = 60;
        int defaultLavyHornyY = 50;

        this.sirkaHernehoPola = sirkaHernehoPola;
        int zaciatocnyPosunDoskyX = this.sirkaHernehoPola / 2 - defaultLavyHornyX;
        int zaciatocnyPosunDoskyY = vyskaHernehoPola - defaultLavyHornyY;

        this.model = new Obdlznik();
        this.model.zmenFarbu("white");
        this.model.zobraz();
        this.model.zmenStrany(this.sirka, this.vyska);

        this.model.posunZvisle(zaciatocnyPosunDoskyY);
        this.model.posunVodorovne(zaciatocnyPosunDoskyX - (this.sirka / 2));

        this.lavyHornyX = defaultLavyHornyX + zaciatocnyPosunDoskyX - (this.sirka / 2);
        this.lavyHornyY = defaultLavyHornyX + zaciatocnyPosunDoskyY;
        this.delta = deltaDosky;
    }

    /**
     * Umožňuje posun dosky po hernej ploche smerom vľavo,
     * v prípade že sa drží v obmedzeniach hernej plochy.
     *
     * @author: kód z cvičení
     */
    public void posunVlavo() {
        if (this.lavyHornyX > 0) {
            this.lavyHornyX -= this.delta;
            this.model.posunVodorovne(-this.delta);
        }
    }

    /**
     * Umožňuje posun dosky po hernej ploche smerom vpravo,
     * v prípade že sa drží v obmedzeniach hernej plochy.
     *
     * @author: kód z cvičení
     */
    public void posunVpravo() {
        if (this.lavyHornyX + this.sirka < this.sirkaHernehoPola) {
            this.lavyHornyX += this.delta;
            this.model.posunVodorovne(this.delta);
        }
    }

    /**
     * Umožňuje znovuzrodiť dosku v strede na spodku hernej plochy,
     * využíva sa na začiatku hry alebo v prípade, že lopta
     * uletela pod spodnú hranicu herného poľa.
     *
     */
    public void respawn() {
        this.model.posunVodorovne(-this.lavyHornyX);
        this.lavyHornyX = (this.sirkaHernehoPola / 2 - (this.sirka / 2));
        this.model.posunVodorovne(this.lavyHornyX);
    }

    /**
     * Kontroluje, či lopta koliduje s doskou.
     *
     * @param powerUps arraylist v ktorom sú lopty a powerups
     */
    public void checkCollision(ArrayList<PowerUp> powerUps) {
        for (PowerUp current : powerUps) {
            this.xOd = this.lavyHornyX - current.getPolomer();
            this.xDo = this.lavyHornyX + this.sirka + current.getPolomer();
            
            if (current.getXLava() > this.xDo || current.getXPrava() < this.xOd) {
                continue;
            } else if (current.getYDolna() >= this.yOd) {
                current.use();
                break;
            }
        }
    }

    /**
     * @return výška tehly.
     */
    public int getVyska() {
        return this.vyska;
    }


    /**
     * Vráti šírku dosky, ktorá bola pri vytvorení dosky.
     * @return int defaultna šírka dosky.
     */
    public int getDefaultSirkaDosky() {
        return this.defaultSirkaDosky;
    }


    /**
     * Kontroluje, ci doska koliduje s loptou.
     * @param lopta  lopta, ktorou sa hrá.
     * @return vrati, ci a ako koliduje lopta a doska pomocou enumu.
     */
    @Override
    public Koliduje checkCollision(Lopta lopta) {
        //hitbox X
        this.xOd = this.lavyHornyX - lopta.getPolomer();
        this.xDo = this.lavyHornyX + this.sirka + lopta.getPolomer();
        //hitbox Y
        this.yOd = this.lavyHornyY - (2 * lopta.getPolomer());

        if (lopta.getXLava() > this.xDo || lopta.getXPrava() < this.xOd) {
            return Koliduje.NIE;
        } else if (lopta.getYDolna() >= this.yOd) {
            return Koliduje.ANO;
        }
        return Koliduje.NIE;
    }

    /**
     * Metoda zmeni sirku dosky podla parametra.
     * @param sirka sirka, na ktoru sa ma zmenit doska.
     */
    public void zmenSirku(int sirka) {
        this.sirka = sirka;
        this.model.zmenStrany(sirka, this.vyska);
    }

    /**
     * Zmeni farbu vizualneho modelu dosky na pozadovanu, podla parametra.
     * @param farba na kotru sa ma model zmenit.
     */
    public void zmenFarbu(String farba) {
        this.model.zmenFarbu(farba);
    }
}

