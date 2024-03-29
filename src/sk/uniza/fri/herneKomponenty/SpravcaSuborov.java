package sk.uniza.fri.herneKomponenty;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Trieda zodpovedá za prácu so súbormi. Je schopná našítať zo súboru mapu, zistiť či je pre hráča odomknutá či odomknúť nasledujúci level.
 *
 * @author Ján Krajčík
 *
 */
public class SpravcaSuborov {
    private final String pathToMaps;

    public SpravcaSuborov(String pathToMaps) {
        this.pathToMaps = pathToMaps;
    }



    /**
     * Vráti mapu vo forme dvojrozmerného poľa, ktoré je naplnené počtom životov
     * tehly na danom mieste.
     *
     * @param selectedLevel - cesta ku textovému súboru, z ktorého sa má načítať mapa.
     * @return map - dvojrozmerne pole integerov, kazde policko je naplnene cislom od 0 po 5,
     * čo symbolizuje počet životov daného políčka.
     */
    public int[][] getMap(String selectedLevel) {
        File subor = new File(this.pathToMaps + "/" + selectedLevel + ".txt");

        int[][] map = new int[0][0];
        try (Scanner citac = new Scanner(subor)) {
            citac.next();
            int riadky = citac.nextInt();
            int stlpce = citac.nextInt();
            map = new int[riadky][stlpce];

            for (int riadok = 0; riadok < riadky; riadok++) {
                for (int stlpec = 0; stlpec < stlpce; stlpec++) {
                    map[riadok][stlpec] = citac.nextInt();
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Subor sa nenasiel");
        }
        return map;
    }

    /**
     * Vráti pole názvov(Stringov) súborov(máp), ktoré sa nachádzajú v zložke.
     *
     * @return zoznamSuborovAkoPoleStringov - Pole Stringov s názvami levelov.
     */
    public String[] getMaps() {
        File zlozka = new File(this.pathToMaps);
        File[] zoznamSuborov = zlozka.listFiles();
        if (zoznamSuborov != null) {
            Arrays.sort(zoznamSuborov);
        }


        String[] zoznamSuborovAkoPoleStringov = new String[0];
        if (zoznamSuborov != null) {
            zoznamSuborovAkoPoleStringov = new String[zoznamSuborov.length];
        }
        if (zoznamSuborov != null) {
            for (int i = 0; i < zoznamSuborov.length; i++) {
                if (zoznamSuborov[i].isFile()) {
                    zoznamSuborovAkoPoleStringov[i] = zoznamSuborov[i].getName().replace(".txt", "");

                }
            }
        }
        return zoznamSuborovAkoPoleStringov;
    }


    /**
     * Vráti, či je hráčom zvolený level odomknutý, na základe prečítania booleanu zo súboru s mapou/levelom.
     * @param selectedLevel Názov hráčom zvoleného Levelu, teda zároveň aj príslušného súboru bez prípony.
     * @return či je level odomknutý.
     */
    public boolean isLevelUnlocked(String selectedLevel) {
        File subor = new File(this.pathToMaps + "/" + selectedLevel + ".txt");
        try (Scanner citac = new Scanner(subor)) {
            return citac.nextBoolean();
        } catch (FileNotFoundException e) {
            System.out.println("Doslo k chybe pri nacitani mapy.");
        } catch (InputMismatchException e) {
            System.out.println("No, ta mapa je asi poskodena, prepacte.");
            System.exit(0);
        }
        return false;
    }

    /**
     * Odomkne ďaľší level, teda ak hráč je momentálne na levely 2, odomke level 3. Odomikanie funguje tak, že sa prepíše boolean v súbore.
     * @param currentLevel Názov hráčom zvoleného Levelu, teda zároveň aj príslušného súboru bez prípony.
     */
    public void unlockNextLevel(String currentLevel) {
        int nextLevelNumber = Integer.parseInt(String.valueOf(currentLevel.charAt(currentLevel.length() - 1))) + 1;
        String nextLevel = "Level " + nextLevelNumber + ".txt";

        //TODO
        File suborNaOdomknutie = new File(this.pathToMaps + "/" + nextLevel);
        File docasnySubor = new File(this.pathToMaps + "/" + "docasnySubor.txt");
        // do noveho suboru napise true, prekopiruje stary subor okrem prveho booleanu do noveho suboru.
        // Stary subor zmaze a novy premenuje z "Docasny subor" na nazov povodneho suboru.
        // vymaze stary a vytvory novy do ktoreho pripise docasny, sak chapes, ne?
        try (PrintWriter writer = new PrintWriter(docasnySubor); Scanner sc = new Scanner(suborNaOdomknutie)) {

            //writer napise na zaciatok suboru true, a citac preskoci jeden riadok, aby nenapisal hned pod to false zo stareho suboru
            writer.println("true");
            sc.nextLine();

            while (sc.hasNextLine()) {
                writer.println(sc.nextLine());
            }
            writer.close();
            sc.close();
            suborNaOdomknutie.delete();
            docasnySubor.renameTo(new File("src/sk/uniza/fri/Assets/txt/maps/" + nextLevel));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Pri odomikaní nasledujúceho levelu došlo k chybe!");
        }
    }
}

