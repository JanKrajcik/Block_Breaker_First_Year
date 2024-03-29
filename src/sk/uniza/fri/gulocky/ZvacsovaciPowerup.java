package sk.uniza.fri.gulocky;

import sk.uniza.fri.herneKomponenty.BlockBreaker;
import sk.uniza.fri.herneKomponenty.Doska;

/**
 * 4/30/2022 - 10:33 PM
 *
 * PowerUp, ktorý sa môže spawnúť pri rozbití tehly. Po narazení do hernej dosky sa aktivuje jeho abilita.
 *
 * @author Ján Krajčík
 */
public class ZvacsovaciPowerup extends PowerUp {
    public ZvacsovaciPowerup(int priemer, int vyskaHernehoPola, int x, int y) {
        super(priemer, vyskaHernehoPola, x, y);
        this.model.zmenFarbu("blue");
    }

    /**
     * Prepísaná metóda z predka, použíje metódu z predka, avšak k nej pridá abilitu. Konktétne zväčší šírku dosky.
     */
    @Override
    public void use() {
        super.use();
        Doska doska = BlockBreaker.getBlockBreaker().getDoska();
        doska.zmenSirku((int)Math.round(doska.getDefaultSirkaDosky() * 1.5));
    }
}