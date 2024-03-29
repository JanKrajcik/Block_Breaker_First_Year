package sk.uniza.fri.herneKomponenty;

import sk.uniza.fri.gulocky.Lopta;

import java.util.Random;

/**
 * AI je trieda, ktorá dokáže pomocu "umelej ingeligencie" riadiť dosku tak, aby bola
 * pod loptou, teda simuluje činnosť hráča.
 * 
 * @author: Ján Krajčík, skupina: 5ZYR14
 */
public class AI {
    private static AI aISingleton;
    
    /**
     * Jednoducha tovarenska metoda na ziskanie AI ako singleton objektu.
     */
    public static AI getAI() {
        if (AI.aISingleton == null) {
            AI.aISingleton = new AI();
        }
        return AI.aISingleton;
    }
    
    //     ---- inštancia ----
    
    private final Random random;
    
    /**
     * Vytvorí umelú inteligenciu.
     */
    private AI() {
        this.random = new Random();
    }
    
    /**
     * Každý herný tik (250 ms) pošle BlockBreaker správu triede AI, AI skontroluje či je doska
     * pod loptou, ak nie je tak sa pod ňu posunie metódou posunVlavo() alebo posunVpravo()
     * Je tu aj možnosť, že AI bude pri volaní metódy tik povedané, aby  robilo chybu, teda
     * bude percentuálna šanca, že dosku posunie opačným smerom ako letí lopta.
     * 
     * @param  lopta referencia na objekt lopta, slúži na to, aby od lopty mohlo AI zisťovať 
     * rôzne informácie cez gettery. 
     * @param  doska referencia na objekt doska, slúži na to, aby ju mohlo AI ovládať
     * @param  canRobitChyby či má umelá inteligencia robiť chyby alebo má neustále posúvať dosku
     *  pod loptu
     */
    public void tik(Lopta lopta, Doska doska, boolean canRobitChyby) {
        if (lopta.getStredX() < doska.getStredX()) {
            this.posunVlavo(doska, canRobitChyby);
            return;
        } 
        this.posunVpravo(doska, canRobitChyby);
    }

    /**
     * Posúva dosku vlavo, môže túto činnosť vykonávať aj nespolahlivo(šanca 1:5 že urobí chybu),
     * teda niekedy ju posunúť vpravo, aby AI nebolo dokonalé, toto je definované v parametroch.
     * 
     * @param doska          referencia na dosku, aby ju mohlo AI ovládať
     * @param canRobitChyby  či môže robiť AI chyby
     */
    public void posunVlavo(Doska doska, boolean canRobitChyby) {
        if (canRobitChyby && this.random.nextInt(5) == 0) {
            doska.posunVpravo(); 
            return;
        } 
        doska.posunVlavo(); 
    }
    
    /**
     * Posúva dosku vpravo, môže túto činnosť vykonávať aj nespolahlivo(šanca 1:5 že urobí chybu),
     * teda niekedy ju posunúť vľavo, aby AI nebolo dokonalé, toto je definované v parametroch.
     * 
     * @param doska          referencia na dosku, aby ju mohlo AI ovládať
     * @param canRobitChyby  či môže robiť AI chyby
     */
    public void posunVpravo(Doska doska, boolean canRobitChyby) {
        if (canRobitChyby && this.random.nextInt(5) == 0) {
            doska.posunVlavo(); 
            return;
        } 
        doska.posunVpravo();
    }
}

