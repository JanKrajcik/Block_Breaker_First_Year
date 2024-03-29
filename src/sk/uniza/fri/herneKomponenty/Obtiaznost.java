package sk.uniza.fri.herneKomponenty;

/**
 * Enumeračná trieda Obtiaznost - Na začiatku hry si užívateľ vyberá obtiažnosť,
 * ktorá je enum. Každá inštancia tejto triedy má definovanú deltu (rýchlosť)
 * dosky, jej šírku, počet riadkov tehál a počet stĺpcov tehál.
 *
 * @author: Ján Krajčík, skupina: 5ZYR14
 * @version: 1.2 (20.12.2021)
 */
public enum Obtiaznost {
    /**
     * Inštancie s parametrom, využívajú sa pri vytvarani hry.
     */

    LAHKA(10, 80, 100000000),
    STREDNA(5, 50, 50000000),
    TAZKA(5, 40, 30000000);

    //Atributy

    private final int deltaDosky;
    private final int sirkaDosky;
    private final int tickLength;

    /**
     * @param  deltaDosky         počet pixelov o ktoré sa posunie doska pri každom volaní metódy
     *                            posunVlavo alebo posunVpravo.
     * @param  sirkaDosky         šírka dosky, čím menšia tým ťažšie je ju dostať pod loptu.
     * @param  tickLength         Rýchlosť ticku v hre, teda rýchlosť loptičky.
     */
    /*private*/ Obtiaznost(int deltaDosky, int sirkaDosky, int tickLength) {
        this.deltaDosky = deltaDosky;
        this.sirkaDosky = sirkaDosky;
        this.tickLength = tickLength;
    }

    /**
     * Vráti šírku dosky definovanú v každej inštancií enumu a priradí ju doske.
     */
    public int getSirkaDosky() {
        return this.sirkaDosky;
    }

    /**
     * Vráti deltu dosky definovanú v každej inštancií enumu a priradí ju doske.
     */
    public int getDeltaDosky() {
        return this.deltaDosky;
    }

    /**
     * @return Vráti dĺžku ticku, ktorý je definovaný v každej inštancií enumu.
     */
    public int getTickLength() {
        return this.tickLength;
    }
}
