package sk.uniza.fri.herneKomponenty;

import sk.uniza.fri.tvary.Obdlznik;

/**
 * Metóda slúži na vytvorenie 7 segmentového čísla z obdĺžnikov.
 * 
 * @author: Ján Krajčík, skupina: 5ZYR14, S pomocou kódov z cvičení
 * (bližšie špecifikované v komentároch jednotlivých metód).
 */
public class SegmentovyZnak {
    private final Obdlznik a;
    private final Obdlznik b;
    private final Obdlznik c;
    private final Obdlznik d;
    private final Obdlznik e;
    private final Obdlznik f;
    private final Obdlznik g;
    //stranaA bola 30
    //stranaB bola 5
    
    /**
     * Konštruktor segmentového znaku, vytvorí segmentový znak na daných súradniciach X a Y,
     * definovanej stany A a strany B obdĺžnikov, veľkosť strán udáva veľkosť vytvoreného znaku.
     * 
     * @param  x        Xová súradnica ľavého horného rohu vytvoreného znaku
     * @param  y        Yová súradnica ľavého horného rohu vytvoreného znaku
     * @param  stranaA  Veľkosť strany A siedmich obdĺžnikov ktoré tvoria znak
     * @param  stranaB  Veľkosť strany B siedmich obdĺžnikov ktoré tvoria znak
     * 
     * @author: Z cvičení
     */
    
    public SegmentovyZnak(int x, int y, int stranaA, int stranaB) {
        //sirka
        //vyska

        int defaultLavyHornyX = 60;
        int defaultLavyHornyY = 50;
        
        this.a = new Obdlznik();
        this.b = new Obdlznik();
        this.c = new Obdlznik();
        this.d = new Obdlznik();
        this.e = new Obdlznik();
        this.f = new Obdlznik();
        this.g = new Obdlznik();
        
        this.a.posunVodorovne(x - defaultLavyHornyX);
        this.a.posunZvisle(y - defaultLavyHornyY);
        this.a.zmenStrany(stranaA, stranaB);
        
        this.b.posunVodorovne(x - defaultLavyHornyX);
        this.b.posunZvisle(y - defaultLavyHornyY);
        this.b.zmenStrany(stranaB, stranaA);
        
        this.c.posunVodorovne(x - defaultLavyHornyX);
        this.c.posunZvisle(y - defaultLavyHornyY);
        this.c.zmenStrany(stranaB, stranaA);
        
        this.d.posunVodorovne(x - defaultLavyHornyX);
        this.d.posunZvisle(y - defaultLavyHornyY);
        this.d.zmenStrany(stranaA, stranaB);
        
        this.e.posunVodorovne(x - defaultLavyHornyX);
        this.e.posunZvisle(y - defaultLavyHornyY);
        this.e.zmenStrany(stranaB, stranaA);
        
        this.f.posunVodorovne(x - defaultLavyHornyX);
        this.f.posunZvisle(y - defaultLavyHornyY);
        this.f.zmenStrany(stranaB, stranaA);
        
        this.g.posunVodorovne(x - defaultLavyHornyX);
        this.g.posunZvisle(y - defaultLavyHornyY);
        this.g.zmenStrany(stranaA, stranaB);
        
        this.a.posunVodorovne(stranaB);
        this.f.posunZvisle(stranaB);
        this.b.posunZvisle(stranaB);
        this.b.posunVodorovne(stranaA + stranaB);
        this.g.posunVodorovne(stranaB);
        this.g.posunZvisle(stranaA + stranaB);
        this.c.posunZvisle(stranaA + stranaB * 2);
        this.c.posunVodorovne(stranaA + stranaB);
        this.e.posunZvisle(stranaA + stranaB * 2);
        this.d.posunVodorovne(stranaB);
        this.d.posunZvisle(stranaA * 2 + stranaB * 2);
    }
    
    /**
     * Zobrazí číslice, táto metóda má 2 verzie, teda dochádza k preťaženiu metódy,
     * druh polymorfizmu. Táto verzia je s parametrom číslica, metóda teda vie akú
     * číslicu má zobraziť, a teda ju zobrazí.
     * 
     * @author: Ján Krajčík
     */
    public void zobrazCislice(int cislica) {
        this.prekresliCislice(cislica);
    }
    
    /**
     * Vykreslí 7 obdĺžnikov tvoriacich číslicu na Plátno. 
     * 
     * @author: Z cvičení
     */
    public void prekresliCislice(int cislica) {
        this.a.skry();
        this.b.skry();
        this.c.skry();
        this.d.skry();
        this.e.skry();
        this.f.skry();
        this.g.skry();

        switch (cislica) {
            case 0 -> {
                this.a.zobraz();
                this.b.zobraz();
                this.c.zobraz();
                this.d.zobraz();
                this.e.zobraz();
                this.f.zobraz();
            }
            case 1 -> {
                this.b.zobraz();
                this.c.zobraz();
            }
            case 2 -> {
                this.a.zobraz();
                this.b.zobraz();
                this.d.zobraz();
                this.e.zobraz();
                this.g.zobraz();
            }
            case 3 -> {
                this.a.zobraz();
                this.b.zobraz();
                this.c.zobraz();
                this.d.zobraz();
                this.g.zobraz();
            }
            case 4 -> {
                this.b.zobraz();
                this.c.zobraz();
                this.f.zobraz();
                this.g.zobraz();
            }
            case 5 -> {
                this.a.zobraz();
                this.c.zobraz();
                this.d.zobraz();
                this.f.zobraz();
                this.g.zobraz();
            }
            case 6 -> {
                this.a.zobraz();
                this.c.zobraz();
                this.d.zobraz();
                this.e.zobraz();
                this.f.zobraz();
                this.g.zobraz();
            }
            case 7 -> {
                this.a.zobraz();
                this.b.zobraz();
                this.c.zobraz();
            }
            case 8 -> {
                this.a.zobraz();
                this.b.zobraz();
                this.c.zobraz();
                this.d.zobraz();
                this.e.zobraz();
                this.f.zobraz();
                this.g.zobraz();
            }
            case 9 -> {
                this.a.zobraz();
                this.b.zobraz();
                this.c.zobraz();
                this.d.zobraz();
                this.f.zobraz();
                this.g.zobraz();
            }
        }
    }  
}
