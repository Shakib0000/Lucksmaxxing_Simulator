import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Rarities {
    private static ArrayList<Rarity> rarities;

    public Rarities() {
        rarities = new ArrayList<Rarity>();
        parseRarities("data/Rarities");
    }

    public static void parseRarities(String fileName) {
        File f = new File(fileName);
        Scanner s = null;
        try {
            s = new Scanner(f);
        }
        catch (FileNotFoundException e) {
            System.out.println("File not found.");
            System.exit(1);
        }

        ArrayList<String> fileData = new ArrayList<String>();
        while (s.hasNextLine())
            fileData.add(s.nextLine());

        for (int i = 0; i < fileData.size(); i++) {
            String[] dataList = fileData.get(i).split("\\|");
            rarities.add(new Rarity(dataList[0], Color.getColor(dataList[1]), Font.getFont(dataList[2]), Double.parseDouble(dataList[3])));
        }
    }

    public String getRarities() {
        String printString = "";
        for (int i = 0; i < rarities.size(); i++) {
            Rarity rarity = rarities.get(i);
            printString += "Name: " + rarity.getName() + "\nColor: " + rarity.getColor() + "\n Font Name/Style/Size: " + rarity.getFontInfo().getFontName() + ", " + rarity.getFontInfo().getStyle() + ", " + rarity.getFontInfo().getSize() + "\nChance: " + rarity.getChance() + "\n";
        }
        return printString;
    }
}
