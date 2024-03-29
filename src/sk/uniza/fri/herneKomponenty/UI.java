package sk.uniza.fri.herneKomponenty;

import sk.uniza.fri.tvary.Obdlznik;
import sk.uniza.fri.tvary.Srdce;

import javax.swing.JOptionPane;

/**
 * UI - uživateľské rozhranie vykresuje skoro všetko, čo na obrazovke
 * môže hráč vidieť. Vyhadzuje na obrazovku okná s výberom typu hráča,
 * či obtiažnosti.
 *
 * @author Ján Krajčík, skupina: 5ZYR14
 * @version 1.4 (24.12.2021)
 */
public class UI {
    private static final String LAHKA = "Lahka";
    private static final String STREDNA = "Stredna";
    private static final String TAZKA = "Tazka";
    private static final String CHYBA = "Chyba";

    private static final String BOT = "Bot";
    private static final String CLOVEK = "Clovek";

    private Obdlznik spodnaStena;
    private Obdlznik pravaStena;
    private Srdce[] poleSrdiecok;
    private Skore skoreTabula;

    private TypHraca typHraca;
    private Obtiaznost obtiaznost;

    private int pocetZivotov;
    private int sirkaHernehoPola;
    private int vyskaHernehoPola;
    private int sirkaSteny;
    private int defaultLavyHornyXObdlznika;
    private int defaultLavyHornyYObdlznika;
    private int defaultLavyHornyXObrazka;
    private int medzeraMedziDoskouASpodnouStenou;
    private int vyskaDosky;

    /**
     * Konštruktor UI
     */
    public UI() {
    }

    /**
     * Získa od hráča vstup, obtiažnosť ktorú si zvolý z množiny:
     * Ľahká, Stredná, Ťažká prostredníctvom vyskakujúceho okna
     * s roletkou JOptionPane.
     */
    public Obtiaznost getObtiaznost() {
        if (this.obtiaznost == null) {
            this.vyhodPanelSVyberomObtiaznosti();
        }
        return this.obtiaznost;
    }

    /**
     * Získa od hráča vstup, typ hráča ktorý si zvolý z množiny:
     * Bot, Človek prostredníctvom vyskakujúceho okna
     * s roletkou JOptionPane.
     */
    public TypHraca getTypHraca() {
        this.vyhodPanelSVyberomTypuHraca();
        return this.typHraca;
    }

    public String getSelectedMap(SpravcaSuborov spravcaSuborov) {
        String[] maps = spravcaSuborov.getMaps();
        String selectedMap;

        try {
            selectedMap = (String)JOptionPane.showInputDialog(null, "Vyberte level", "Výber levelu", JOptionPane.QUESTION_MESSAGE, null, maps, maps[0]);
        } catch (Exception exception) {
            this.vyhodPanelSChybou("Pri vybere obtiaznosti doslo k chybe!");
            System.out.println(exception);
            exception.printStackTrace();
            selectedMap = maps[0];
        }
        System.out.println(selectedMap);
        return selectedMap;
    }

    /**
     * Po skončení hry, teda po tom čo hráč píde o všetky životy BlockBreaker
     * zavolá túto triedu, ktorá vyhodí okno s dosiahnutým skóre, obtiažnosťou
     * ktorú hráč hral a hláškou.
     */
    public void vyhodPanelSVyhodnotenimHry(BlockBreaker blockBreaker) {
        String hlaska;
        if (this.typHraca == this.typHraca.BOT) {
            hlaska = "Hral za teba bot :(";
        } else if (this.skoreTabula.getSkore() > 60) {
            hlaska = "Frajerské skóre!";
        } else if (this.skoreTabula.getSkore() > 30) {
            hlaska = "Dobrá práca!";
        } else {
            hlaska = "Mohlo to byť aj lepšie, skús to znovu!";
        }
        JOptionPane.showMessageDialog(null, "Si VÍ-ŤAZ! Tvoje skóre bolo: " + this.skoreTabula.getSkore() + ", Obtiažnosť bola: " + this.obtiaznost + ", " + hlaska, "Hra skončila!", JOptionPane.INFORMATION_MESSAGE);
        int pokracovanieHry = -1;
        while (pokracovanieHry == -1 || pokracovanieHry == 2) {
            pokracovanieHry = JOptionPane.showConfirmDialog(null, "Chcete pokračovať v hre?");
        }

        // 0 znamena, ze bola stlacena klavesa OK
        if (pokracovanieHry == 0) {
            blockBreaker.reset();
        } else {
            System.exit(0);
        }
    }

    /**
     * Získa od hráča vstup, obtiažnosť ktorú si zvolý z množiny:
     * Ľahká, Stredná, Ťažká prostredníctvom vyskakujúceho okna
     * s roletkou JOptionPane.
     */
    public void vyhodPanelSVyberomObtiaznosti() {
        Obtiaznost[] obtiaznosti = new Obtiaznost[3];
        obtiaznosti[0] = Obtiaznost.LAHKA;
        obtiaznosti[1] = Obtiaznost.STREDNA;
        obtiaznosti[2] = Obtiaznost.TAZKA;
        try {
            Obtiaznost vybranaObtiaznost = (Obtiaznost)JOptionPane.showInputDialog(null, "Vyberte obtiažnosť prosím", "Výber obtiažnosti", JOptionPane.QUESTION_MESSAGE, null, obtiaznosti, obtiaznosti[1]);
            this.obtiaznost = vybranaObtiaznost;
        } catch (Exception exception) {
            this.vyhodPanelSChybou("Pri vybere obtiaznosti doslo k chybe!");
            System.out.println(exception);
            exception.printStackTrace();
            this.obtiaznost = Obtiaznost.STREDNA;
        }
    }

    /**
     * Získa od hráča vstup, obtiažnosť ktorú si zvolý z množiny:
     * Ľahká, Stredná, Ťažká prostredníctvom vyskakujúceho okna
     * s roletkou JOptionPane.
     */
    public void vyhodPanelSVyberomTypuHraca() {
        TypHraca[] typyHraca = new TypHraca[2];
        typyHraca[0] = TypHraca.CLOVEK;
        typyHraca[1] = TypHraca.BOT;
        try {
            TypHraca vybranyTypHraca = (TypHraca)JOptionPane.showInputDialog(null, "Vyberte typ hráča prosím", "Výber typu hráča", JOptionPane.QUESTION_MESSAGE, null, typyHraca, typyHraca[0]);
            this.typHraca = vybranyTypHraca;
        } catch (Exception exception) {
            this.vyhodPanelSChybou("Pri vybere hraca doslo k chybe!");
            this.typHraca = TypHraca.CLOVEK;
        }
    }

    /**
     * Vyhodí panel s definovanou chybou.
     */
    public void vyhodPanelSChybou(String chybovaHlaska) {
        JOptionPane.showMessageDialog(null, chybovaHlaska, "Chyba!", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Vykreslí hru s rozhraním podľa parametrov.
     *
     * @param  vyskaHernehoPola  udáva, aká je výška herného poľa
     * @param  sirkaHernehoPola  udáva, aká je šírka herného poľa
     * @param  doska             je referenciou na hernú dosku
     * @param  pocetZivotov      udáva, aký má byť počet životov
     *
     */
    public void vykresliHru (int vyskaHernehoPola, int sirkaHernehoPola, Doska doska, int pocetZivotov, SpravcaSteny spravcaSteny/*, int stavSkore*/) {
        this.defaultLavyHornyXObdlznika = 60;
        this.defaultLavyHornyYObdlznika = 50;

        this.medzeraMedziDoskouASpodnouStenou = 1;
        this.sirkaSteny = 2;

        int maximalnyPocetBodov = spravcaSteny.getMaxSkore();

        this.skoreTabula = new Skore(maximalnyPocetBodov, vyskaHernehoPola, doska.getVyska());

        this.pocetZivotov = pocetZivotov;
        this.sirkaHernehoPola = sirkaHernehoPola;
        this.vyskaHernehoPola = vyskaHernehoPola;

        this.vyskaDosky = doska.getVyska();
        this.spodnaStena = new Obdlznik();
        this.spodnaStena.zobraz();
        this.spodnaStena.posunVodorovne(-this.defaultLavyHornyXObdlznika);
        this.spodnaStena.posunZvisle(-this.defaultLavyHornyYObdlznika + this.vyskaHernehoPola + this.vyskaDosky + this.medzeraMedziDoskouASpodnouStenou);
        this.spodnaStena.zmenStrany(this.sirkaHernehoPola, this.sirkaSteny);

        this.pravaStena = new Obdlznik();
        this.pravaStena.posunVodorovne(this.sirkaHernehoPola - this.defaultLavyHornyXObdlznika);
        this.pravaStena.posunZvisle(-this.defaultLavyHornyYObdlznika);
        //na konci sa pripocitava sirka steny pre to, aby nebol roh prazdny pixel
        this.pravaStena.zmenStrany(this.sirkaSteny, this.vyskaHernehoPola + this.vyskaDosky + this.medzeraMedziDoskouASpodnouStenou + this.sirkaSteny);
        this.pravaStena.zobraz();
        this.vykresliSrdiecka();
    }

    /**
     * Na obrazovku vykrelí 3 srdiečka.
     */
    public void vykresliSrdiecka() {
        this.poleSrdiecok = new Srdce[this.pocetZivotov];

        for (int i = 0; i < this.poleSrdiecok.length; i++) {
            this.poleSrdiecok[i] = new Srdce(i, this.sirkaHernehoPola, this.vyskaHernehoPola, this.vyskaDosky, this.medzeraMedziDoskouASpodnouStenou, this.sirkaSteny);
            this.poleSrdiecok[i].nakresli();
        }
    }

    /**
     * Pošle správu blockBreaker-u že sa zvýšilo skóre.
     */
    public void zvysSkore(BlockBreaker blockBreaker) {
        this.skoreTabula.zvysSkore(blockBreaker);
    }

    /**
     * Zníži počet srdiečok, ktoré reprezentujú životy na obrazovke.
     */
    public void znizPocetZivotov(int pocetZivotov) {
        this.poleSrdiecok[pocetZivotov].zmaz();
        this.pocetZivotov = pocetZivotov;
    }

    public void zvysPocetZivotov(int pocetZivotov) {
        this.pocetZivotov = pocetZivotov;
        this.vykresliSrdiecka();
    }

    /**
     * Setter na nastavenie počtu životov.
     */
    public void setPocetZivotov(int pocetZivotov) {
        this.pocetZivotov = pocetZivotov;
        this.vykresliSrdiecka();
    }

    /**
     * Vynulovanie skore.
     */
    public void resetujSkore() {
        this.skoreTabula.resetujSkore();
    }
}
