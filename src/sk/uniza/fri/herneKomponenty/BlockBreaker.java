package sk.uniza.fri.herneKomponenty;

import sk.uniza.fri.gulocky.*;
import sk.uniza.fri.tvary.Manazer;
import java.util.ArrayList;
import java.util.Random;

/**
 * Trieda BlockBreaker riadi a organizuje činnosť všetkých ostatných tried v tomto programe,
 * čím zabezpečuje jeho chod a funkčnosť. Dostáva správy (tik(), posunVlavo(),
 * posunVpravo(), zrus(), aktivuj()) od manažéra, ktorého si pri vytvorení inštancie
 * vytvára.
 *
 * Cielom hry je odrážať loptu tak, aby rozbila čo najviac tehiel na hernom poly.
 * Vždy, keď lopta rozbije všetky lopty na hernom poly, objavia sa ďaľšie. Hra končí, keď
 * hráč stratí všetky životy.
 *
 *
 * @author: Ján Krajčík, skupina: 5ZYR14, S pomocou kódov z cvičení
 * (bližšie špecifikované v komentároch jednotlivých tried a metód).
 *
 */
public class BlockBreaker {
    private static BlockBreaker blockBreakerSingleton;

    /**
     * Jednoducha tovarenska metoda na ziskanie hry ako singleton objektu.
     */
    public static BlockBreaker getBlockBreaker() {
        if (BlockBreaker.blockBreakerSingleton == null) {
            try {
                BlockBreaker.blockBreakerSingleton = new BlockBreaker();
            } catch (Exception exception) {
                System.out.println("Došlo k chybe pri spusteni programu!");
                exception.printStackTrace();
            }
        }
        return BlockBreaker.blockBreakerSingleton;
    }

    //     ---- inštancia ----

    private final UI uzivatelskeRozhranie;
    private final Doska doska;

    private final SpravcaSuborov spravcaSuborov;
    private final SpravcaSteny spravcaSteny;
    private static AI aiSingleton;
    private final Lopta lopta;

    private static final int SIRKA_HERNEHO_POLA = 400;
    private static final int VYSKA_HERNEHO_POLA = 600;

    private int pocetZivotov;
    private int wallsDestroyed;

    private Obtiaznost obtiaznost;
    private TypHraca typHraca;
    private StavHry stavHry;
    private String selectedLevel;

    private final IRespawnable[] respawnables = new IRespawnable[2];
    private final ArrayList<PowerUp> powerUps;

    private static final String PATH_TO_MAPS = "src/sk/uniza/fri/Assets/txt/maps";

    /**
     * Vytvorí inštanciu hry BlockBreaker.
     *
     * @author: Ján Krajčík
     */
    private BlockBreaker() {
        this.pocetZivotov = 3;
        this.stavHry = StavHry.PAUZNUTA;
        this.uzivatelskeRozhranie = new UI();

        //toto bude dookola pýtať vstup od uživateľa, výber z tabuľky typovHraca
        while (this.typHraca == null) {
            this.typHraca = this.uzivatelskeRozhranie.getTypHraca();
        }

        if (this.typHraca == TypHraca.BOT) {
            aiSingleton = AI.getAI();
            this.stavHry = StavHry.PREBIEHA;
        }

        // toto bude dookola pýtať vstup od uživateľa, výber z tabuľky obtiažností
        while (this.obtiaznost == null) {
            this.obtiaznost = this.uzivatelskeRozhranie.getObtiaznost();
        }
        this.doska = new Doska(SIRKA_HERNEHO_POLA, VYSKA_HERNEHO_POLA, this.obtiaznost.getSirkaDosky(), this.obtiaznost.getDeltaDosky());
        this.lopta = new Lopta(SIRKA_HERNEHO_POLA, VYSKA_HERNEHO_POLA);
        this.respawnables[0] = this.doska;
        this.respawnables[1] = this.lopta;
        this.respawnRespawnables();

        this.powerUps = new ArrayList<>();

        this.spravcaSuborov = new SpravcaSuborov(PATH_TO_MAPS);
        while (this.selectedLevel == null) {
            this.selectedLevel = this.uzivatelskeRozhranie.getSelectedMap(this.spravcaSuborov);
        }
        //String[] mapy = this.spravcaSuborov.getMaps();

        this.spravcaSteny = new SpravcaSteny();

        boolean mapaVybrata = false;
        while (!mapaVybrata) {
            if (this.spravcaSuborov.isLevelUnlocked(this.selectedLevel)) {
                mapaVybrata = true;
                int[][] mapa = this.spravcaSuborov.getMap(this.selectedLevel);
                this.spravcaSteny.vygenerujTehly(mapa);
            } else {
                this.uzivatelskeRozhranie.vyhodPanelSChybou("No tak ale tento level si nemozete vybrat pane, musite prejst level pred nim.");
                this.selectedLevel = this.uzivatelskeRozhranie.getSelectedMap(this.spravcaSuborov);
            }
        }

        this.uzivatelskeRozhranie.vykresliHru(VYSKA_HERNEHO_POLA, SIRKA_HERNEHO_POLA, this.doska, this.pocetZivotov, this.spravcaSteny);

        Manazer manazer = new Manazer();
        manazer.spravujObjekt(this);
        manazer.setTickLength(this.obtiaznost.getTickLength());
    }

    /**
     * Metódu tik() spúšťa trieda Manažér, ktorá spravuje tento objekt každých 250 milisekund,
     * vo vnútri tik-u sa volajú všetky metódy, ktoré sú zodpovedné za periodické deje,
     * ako napríklad posun lopty, kontrola kolizie,... ;
     *
     * @author: Ján Krajčík
     */
    public void tik() {
        this.spravcaSteny.setBlockBreaker(this);
        if (aiSingleton != null && this.stavHry == StavHry.PREBIEHA) {
            aiSingleton.tik(this.lopta, this.doska, false);
        }

        if (this.stavHry != StavHry.PREBIEHA) {
            return;
        }

        //posunSa je uple vyborne, lebo okrem posunu hned vracia, ci sa objekt nedostal pod dosku.
        if (this.lopta.posunSa()) {
            this.pocetZivotov--;
            this.uzivatelskeRozhranie.znizPocetZivotov(this.pocetZivotov);
            this.pauzni();
            this.respawnRespawnables();
        }

        this.spravcaSteny.najdiAZnicKolidujuceTehly(this.lopta);

        if (this.lopta.narazilaHore()) {
            this.lopta.odrazZvisle();
        }

        if (this.lopta.narazilaVlavo() || this.lopta.narazilaVpravo()) {
            this.lopta.odrazHorizontalne();
        }

        if (this.doska.checkCollision(this.lopta) == Koliduje.ANO) {
            this.lopta.odrazHore();
        }

        this.doska.checkCollision(this.powerUps);

        ArrayList<PowerUp> powerUpsForDestruction = new ArrayList<>();
        for (PowerUp current : this.powerUps) {
            if (current.posunSa()) {
                current.skry();
                powerUpsForDestruction.add(current);
            }
        }
        this.powerUps.removeAll(powerUpsForDestruction);

        if (this.pocetZivotov == 0) {
            this.zmraz();
            this.uzivatelskeRozhranie.vyhodPanelSVyhodnotenimHry(this);
        }
    }

    /**
     * Vždy prebehne, keď hráč rozbije všetky tehly na hernom poly.
     */
    public void koniecSteny() {
        this.pauzni();
        this.respawnRespawnables();
        this.wallsDestroyed++;
        if (this.wallsDestroyed == 3) {
            this.spravcaSuborov.unlockNextLevel(this.selectedLevel);
        }
        this.spravcaSteny.resetujStenu();
    }

    /**
     * Túto metódu zavolá SpravcaSteny vždy, keď je rozbitá nejaká tehla.
     *
     * @author: Ján Krajčík
     */
    public void zvysSkore() {
        this.uzivatelskeRozhranie.zvysSkore(this);
    }

    /**
     * Zvysi pocet zivotov a zmenu prejavy aj graficky na uzivatelskom rozhrani.
     */
    public void zvysPocetZivotov() {
        if (this.pocetZivotov < 3) {
            this.pocetZivotov++;
            this.uzivatelskeRozhranie.zvysPocetZivotov(this.pocetZivotov);
        }
    }

    /**
     * Manažér zavolá túto metódu vždy, keď uživateľ stlačí klávesu LEFT
     *
     * @author: kód z cvičení s pridaním pauzy
     */
    public void posunVlavo() {
        if (this.stavHry == StavHry.PREBIEHA) {
            this.doska.posunVlavo();
        }
    }

    /**
     * Manažér zavolá túto metódu vždy, keď uživateľ stlačí klávesu RIGHT
     *
     * @author: kód z cvičení s pridaním pauzy
     */
    public void posunVpravo() {
        if (this.stavHry == StavHry.PREBIEHA) {
            this.doska.posunVpravo();
        }
    }

    /**
     * Manažér zavolá túto metódu vždy, keď uživateľ stlačí klávesu ESC.
     * Tato metoda uplne ukonci beh programu, zrusi okno.
     *
     * @author: kód z cvičení
     */
    public void zrus() {
        System.exit(0);
    }

    /**
     * Zastavý hru keď hráč vyhrá alebo prehrá. Túto pauzu nie je možné odpauzovať stlačením
     * ENTER alebo SPACE, teda metódou aktivuj(), k odpauzovaniu môže dôjsť jedine vtedy,
     * ak uživateľ začne novu hru.
     *
     * @author: Ján Krajčík
     */
    public void zmraz() {
        this.stavHry = StavHry.SKONCILA;
    }

    /**
     * Zapauzuje alebo odpauzuje odpauzovateľnú pauzu, teda aj priebeh hry, ak nie je
     * hra zapauzovateľná neodpauzovatelne. Využiteľné napríklad, ak si hráč chce ísť
     * dať polievočku alebo prepať pesničku.
     *
     * @author: Ján Krajčík
     */
    public void pauzni() {
        if (this.stavHry == StavHry.PREBIEHA) {
            this.stavHry = StavHry.PAUZNUTA;
        } else if (this.stavHry == StavHry.PAUZNUTA) {
            this.stavHry = StavHry.PREBIEHA;
        }
    }

    /**
     * Manažér zavolá túto metódu vždy, keď uživateľ stlačí klávesu ENTER
     * alebo SPACE, pričom otáča hodnotu atribútu stavHry na medzi PREBIEHA
     * a JEPAUZNUTA, čím pauzuje alebo odpauzuje hru. Ak je však hra zapauzovana
     * permanente, touto metódou ju nie je možné odpauzovať.
     *
     * @author: Ján Krajčík
     */
    public void aktivuj() {
        this.pauzni();
    }

    /**
     * Vygeneruje powerOrb na danych suradniciach a prida ho do  ArrayListu powerUps
     * @param x suradnica x na ktorej sa zobrazi.
     * @param y suradnica y na ktorej sa zobrazi.
     */
    public void vygenerujPowerOrb(int x, int y) {
        Random rnd = new Random();
        if (rnd.nextInt(2) == 1) {
            switch (rnd.nextInt(4) + 1) {
                case 1 -> this.powerUps.add(new ZvacsovaciPowerup(8, VYSKA_HERNEHO_POLA, x, y));
                case 2 -> this.powerUps.add(new ZmensovaciPowerup(7, VYSKA_HERNEHO_POLA, x, y));
                case 3 -> this.powerUps.add(new TrippyPowerup(6, VYSKA_HERNEHO_POLA, x, y));
                case 4 -> this.powerUps.add(new HealingPowerup(6, VYSKA_HERNEHO_POLA, x, y));
            }
        }
    }


    /**
     * Vymaže použitý powerOrb zo zoznamu.
     * @param powerUp  ktorý má byť vymazaný.
     */
    public void vymazPowerOrb(PowerUp powerUp) {
        this.powerUps.remove(powerUp);
    }

    /**
     * Táto metóda je volaná vždy, keď sa hráč po skončení hry rozhodne hrať znovu,
     * vráti hru do stavu ako pred  jej začatím.
     */
    public void reset() {
        this.spravcaSteny.resetujStenu();
        this.respawnRespawnables();
        this.pocetZivotov = 3;
        this.uzivatelskeRozhranie.setPocetZivotov(this.pocetZivotov);
        this.uzivatelskeRozhranie.resetujSkore();
        this.stavHry = StavHry.PAUZNUTA;
    }

    /**
     * Sposobí znovuzrodenie všetkých respawnables, teda lopty,
     */
    public void respawnRespawnables() {
        for (IRespawnable current : this.respawnables) {
            current.respawn();
        }
    }

    /**
     * Vrati referenciu na Dosku.
     * @return Doska
     */
    public Doska getDoska() {
        return this.doska;
    }

    /**
     * Vrati referenciu na Loptu.
     * @return Lopta
     */
    public Lopta getLopta() {
        return this.lopta;
    }
}