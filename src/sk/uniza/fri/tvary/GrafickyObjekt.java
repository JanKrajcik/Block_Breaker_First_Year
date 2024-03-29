package sk.uniza.fri.tvary;

/**
 * 4/30/2022 - 10:33 PM
 *
 * Ján Krajčík upravil pre použitie v tvaroch tak, aby ušetril kopu riadkov kódu a použil polymorfizmus.
 *
 * @author Ján Krajčík,
 * @author Michael Kolling and David J. Barnes
 */
public abstract class GrafickyObjekt {

    protected int lavyHornyX;
    protected int lavyHornyY;
    protected boolean jeViditelny;

    /**
     * Konštruktor Grafického objektu.
     */
    public GrafickyObjekt() {
    }

    /**
     * (Tvar) Posuň sa vpravo o pevnú dĺžku.
     */
    public void posunVpravo() {
        this.posunVodorovne(20);
    }

    /**
     * (Tvar) Zobraz sa.
     */
    public void zobraz() {
        this.jeViditelny = true;
        this.nakresli();
    }

    /**
     * (Tvar) Skry sa.
     */
    public void skry() {
        this.zmaz();
        this.jeViditelny = false;
    }



    /**
     * (Tvar) Posuň sa vľavo o pevnú dĺžku.
     */
    public void posunVlavo() {
        this.posunVodorovne(-20);
    }

    /**
     * (Tvar) Posuň sa hore o pevnú dĺžku.
     */
    public void posunHore() {
        this.posunZvisle(-20);
    }

    /**
     * (Tvar) Posuň sa dole o pevnú dĺžku.
     */
    public void posunDole() {
        this.posunZvisle(20);
    }

    /**
     * (Tvar) Posuň sa vodorovne o dĺžku danú parametrom.
     */
    public void posunVodorovne(int vzdialenost) {
        this.zmaz();
        this.lavyHornyX += vzdialenost;
        this.nakresli();
    }

    /**
     * (Tvar) Posuň sa zvisle o dĺžku danú parametrom.
     */
    public void posunZvisle(int vzdialenost) {
        this.zmaz();
        this.lavyHornyY += vzdialenost;
        this.nakresli();
    }

    /**
     * (Tvar) Posuň sa pomaly vodorovne o dĺžku danú parametrom.
     */
    public void pomalyPosunVodorovne(int vzdialenost) {
        int delta;

        if (vzdialenost < 0) {
            delta = -1;
            vzdialenost = -vzdialenost;
        } else  {
            delta = 1;
        }

        for (int i = 0; i < vzdialenost; i++) {
            this.lavyHornyX += delta;
            this.nakresli();
        }
    }

    /**
     * (Tvar) Posuň sa pomaly vodorovne o dĺžku danú parametrom.
     */
    public void pomalyPosunZvisle(int vzdialenost) {
        int delta;

        if (vzdialenost < 0) {
            delta = -1;
            vzdialenost = -vzdialenost;
        } else {
            delta = 1;
        }

        for (int i = 0; i < vzdialenost; i++) {
            this.lavyHornyY += delta;
            this.nakresli();
        }
    }

    /*
     * Erase the square on screen.
     */
    protected void zmaz() {
        if (this.jeViditelny) {
            Platno canvas = Platno.dajPlatno();
            canvas.erase(this);
        }
    }

    /**
     * Abstraktná mnetóda nakresli, ktorá nakreslí objekt na plátno.
     */
    protected abstract void nakresli();
}