package sk.uniza.fri.gulocky;

import sk.uniza.fri.herneKomponenty.BlockBreaker;

import java.util.Random;

/**
 * 4/30/2022 - 10:33 PM
 *
 *
 * PowerUp, ktorý sa môže spawnúť pri rozbití tehly. Po narazení do hernej dosky sa aktivuje jeho abilita.
 *
 * @author Ján Krajčík
 */
public class TrippyPowerup extends PowerUp {
    public TrippyPowerup(int priemer, int vyskaHernehoPola, int x, int y) {
        super(priemer, vyskaHernehoPola, x, y);
        this.model.zmenFarbu("magenta");
    }

    /**
     * Prepísaná metóda z predka, použíje metódu z predka, avšak k nej pridá abilitu. Konkrétne zmení farbu dosky a lopty na náhodnú.
     */
    @Override
    public void use() {
        super.use();
        BlockBreaker.getBlockBreaker().getDoska().zmenFarbu(this.generateColor());
        BlockBreaker.getBlockBreaker().getLopta().zmenFarbu(this.generateColor());
    }

    /**
     * Vygeneruje náhodú farbu z množiny {blue, magenta, green}.
     * @return farbu vo forme Stringu.
     */
    private String generateColor() {
        Random rnd = new Random();

        return switch (rnd.nextInt(3) + 1) {
            case 1 -> "blue";
            case 2 -> "magenta";
            case 3 -> "green";
            default -> null;
        };
    }
}
