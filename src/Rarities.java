import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Rarities {
    private static ArrayList<Rarity> rarities;

    public Rarities() {
        rarities = new ArrayList<Rarity>();
        rarities.add(new Rarity(400, 180, "Bronze", new Color(150, 75, 0), new Font("Courier New", Font.PLAIN, 40), 2));
        rarities.add(new Rarity(400, 180, "Silver", new Color(192, 192, 192), new Font("Courier New", Font.PLAIN, 40), 10));
        rarities.add(new Rarity(425, 180, "Gold", new Color(255, 215, 0), new Font("Courier New", Font.PLAIN, 40), 14));
        rarities.add(new Rarity(390, 180, "Diamond", new Color(185, 242, 255), new Font("Courier New", Font.PLAIN, 40), 20));
    }

    public Rarity generateRandomRarity(double luck) {
        boolean obtainedRarity = false;
        while (!obtainedRarity) {
            Rarity randomRarity = rarities.get((int) (Math.random() * rarities.size()));
            int randomRarityCalculation = (int) ((Math.random() * ((int) randomRarity.getChance() / luck)) + 1);
            if (randomRarityCalculation == 1) {
                obtainedRarity = true;
                return randomRarity;
            }
        }
        // error?
        return new Rarity(425, 180, "NULL", new Color(255, 255, 255), new Font("Courier New", Font.PLAIN, 40), 1);
    }

    public static ArrayList<Rarity> getRarities() {
        return rarities;
    }
}
