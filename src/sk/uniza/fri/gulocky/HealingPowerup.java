package sk.uniza.fri.gulocky;

import sk.uniza.fri.herneKomponenty.BlockBreaker;

/**
 * 4/30/2022 - 10:33 PM
 *
 * PowerUp, ktorý sa môže spawnúť pri rozbití tehly. Po narazení do hernej dosky sa aktivuje jeho abilita.
 *
 * @author Ján Krajčík
 */
public class HealingPowerup extends PowerUp {
    public HealingPowerup(int priemer, int vyskaHernehoPola, int x, int y) {
        super(priemer, vyskaHernehoPola, x, y);
        this.model.zmenFarbu("white");
    }


    /**
     * Prepísaná metóda z predka, použíje metódu z predka, avšak k nej pridá abilitu.
     */
    @Override
    public void use() {
        super.use();
        BlockBreaker.getBlockBreaker().zvysPocetZivotov();
        BlockBreaker.getBlockBreaker().vymazPowerOrb(this);
    }
}
