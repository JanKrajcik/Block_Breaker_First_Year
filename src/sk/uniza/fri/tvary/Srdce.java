package sk.uniza.fri.tvary;

/**
 * Vykreslí na plátno srdce, ktoré reprezentuje životy.
 * 
 * Obrázok Srdca pochádza zo stránky:
 * https://www.pngwing.com/en/free-png-nymsb
 * Licencia: non-commercial use.
 * 
 * https://www.pngwing.com/en/free-png-mwttk
 * 
 * @author Ján Krajčík, skupina: 5ZYR14
 * @version 1.1 (22.5.2022)
 */
public class Srdce {
    private Obrazok srdce;
    private int defaultLavyHornyYObrazka;
    private int defaultLavyHornyXObrazka;
    private int index;
    private int sirkaHernehoPola;
    private int vyskaHernehoPola;
    private int sirkaSrdca;
    private int medzeraMedziSrdcami;
    private int vyskaDosky;
    private int medzeraMedziDoskouASpodnouStenou;
    private int sirkaSteny;
    /**
     * Konštruktor srdca. Vytvorý srdce, podľa jeho indexu, šírky a výšky poľa, výšky dosky,
     * medzeri medzi doskom a spodnou stenou, šírkou steny ho posunie na správne miesto.
     * 
     * @param  index                             poradové číslo srdca.
     * @param  sirkaHernehoPola                  šírka herného poľa
     * @param  vyskaHernehoPola                  výška herného poľa
     * @param  vyskaDosky                        výška dosky v grafike
     * @param  medzeraMedziDoskouASpodnouStenou  medzera medzi doskou a spodnou stenou v grafike
     * @param  sirkaSteny                        šírka steny v grafike
     */
    public Srdce(int index, int sirkaHernehoPola, int vyskaHernehoPola, int vyskaDosky, int medzeraMedziDoskouASpodnouStenou, int sirkaSteny) {
        this.sirkaHernehoPola = sirkaHernehoPola;
        this.vyskaHernehoPola = vyskaHernehoPola;
        this.defaultLavyHornyYObrazka = 100;
        this.defaultLavyHornyXObrazka = 100;
        this.vyskaDosky = vyskaDosky;
        this.medzeraMedziDoskouASpodnouStenou = medzeraMedziDoskouASpodnouStenou;
        this.sirkaSteny = sirkaSteny;
        this.sirkaSrdca = 35;

        this.srdce = new Obrazok("src\\sk\\uniza\\fri\\Assets\\Pictures\\Srdce2.png");
        this.srdce.skry();
        this.srdce.zmenVelkost(this.sirkaSrdca, this.sirkaSrdca);
        this.index = index;
        this.medzeraMedziSrdcami = 5;
    }
    
    /**
     * Nakreslí srdce na plátno.
     */
    public void nakresli() {
        //this.index + 1 preto, aby aj prve srdce bolo odskočené od steny o šírku medzi srdcami.
        this.srdce.posunZvisle(-this.defaultLavyHornyYObrazka + this.vyskaHernehoPola + this.vyskaDosky + this.medzeraMedziDoskouASpodnouStenou + this.sirkaSteny);
        this.srdce.posunVodorovne(-this.defaultLavyHornyXObrazka + this.sirkaHernehoPola - (this.medzeraMedziSrdcami * (this.index) + this.sirkaSrdca * this.index) - this.sirkaSrdca);
        this.srdce.zobraz();
    }
    
    /**
     * Skryje srdce z plátna.
     */
    public void zmaz() {
        this.srdce.skry();
    }
}
