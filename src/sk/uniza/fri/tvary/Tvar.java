package sk.uniza.fri.tvary;

/**
 * 4/30/2022 - 10:33 PM
 *
 * Trieda tvar zoptimalizovala tvary Obdlznik a Kruh, nakolko z nich vynala rovnaké metódy do tohto predka.
 *
 * @author Jan, FRI Uniza
 */
public abstract class Tvar extends GrafickyObjekt {
    protected String farba;

    public Tvar() {
        this.jeViditelny = false;
    }

    /**
     * (Tvar) Zmeň farbu na hodnotu danú parametrom.
     * Nazov farby musí byť po anglicky.
     * Možné farby sú tieto:
     * červená - "red"
     * žltá    - "yellow"
     * modrá   - "blue"
     * zelená  - "green"
     * fialová - "magenta"
     * čierna  - "black"
     * biela   - "white"
     * hnedá   - "brown"
     */
    public void zmenFarbu(String farba) {
        this.farba = farba;
        this.nakresli();
    }

}