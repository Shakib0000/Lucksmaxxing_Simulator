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
        rarities.add(new Rarity(400, 180, "Bronze", new Color(150, 75, 0), new Font("Courier New", Font.PLAIN, 40), 1));
    }

    public static ArrayList<Rarity> getRarities() {
        return rarities;
    }
}
