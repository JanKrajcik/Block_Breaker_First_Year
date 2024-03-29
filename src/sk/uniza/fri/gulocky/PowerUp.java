package sk.uniza.fri.gulocky;

/**
 * 4/30/2022 - 10:33 PM
 *
 * Abstraktná trieda po ktorej dedia powerUpy. Obsahuje metódu, ktorú využívajú potomkovia, ale aj takú, ktorú potomkovia prepisujú.
 *
 * @author Ján Krajčík
 */
public abstract class PowerUp extends BallGameObject {
    /**
     * Parametrický konštruktor.
     * @param priemer           nastavý priemer grafickému modelu powerUpu.
     * @param vyskaHernehoPola  nastavý vúšku herného poľa, ktorú vyúžíva powerUp na to, aby vedel, kedy sa má despawnúť.
     * @param x                 X-ová spradnica, na ktorej sa vytvorí PowerUp.
     * @param y                 Y-ová spradnica, na ktorej sa vytvorí PowerUp.
     */
    public PowerUp(int priemer, int vyskaHernehoPola, int x, int y) {
        super(priemer, vyskaHernehoPola);
        this.delta = 2;
        this.stredX += x;
        this.stredY += y;

        this.model.posunVodorovne(-DEFAULTNE_X_KRUHU + this.stredX - this.getPolomer());
        this.model.posunZvisle(-DEFAULTNE_Y_KRUHU + this.stredY - this.getPolomer());
    }

    /**
     * Posunie powerUp smerom dole.
     * @return vráti, či sa powerup posunul pod dosku.
     */
    @Override
    public boolean posunSa() {
        this.stredY += this.delta;
        this.model.posunZvisle(this.delta);
        return this.jeMimoDole();
    }

    /**
     * Metoda, ktora v zaklade len skryje vizualnu reprezentaciu (model) PowerUpu, avšak v jednotlivých potomkoch je overridnuta ďaľšími funkcionalitami.
     */
    public void use() {
        this.skry();
    }
}
