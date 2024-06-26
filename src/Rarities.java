import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Rarities {
    private ArrayList<Rarity> rarities;
    private ArrayList<Integer> rarityChances;
    private int luck;

    public Rarities(int luck) { // LEAST RARE TO MOST RARE
        this.luck = luck;
        rarityChances = parseRarityChancesFile();
        rarities = new ArrayList<Rarity>();
        rarities.add(new Rarity(408, 180, "Bronze", new Color(150, 75, 0), new Font("Century Schoolbook", Font.PLAIN, 40), rarityChances.get(rarities.size())));
        rarities.add(new Rarity(420, 180, "Silver", new Color(192, 192, 192), new Font("Century Schoolbook", Font.PLAIN, 40), rarityChances.get(rarities.size())));
        rarities.add(new Rarity(430, 180, "Gold", new Color(255, 215, 0), new Font("Century Schoolbook", Font.PLAIN, 40), rarityChances.get(rarities.size())));
        rarities.add(new Rarity(388, 180, "Diamond", new Color(185, 242, 255), new Font("Century Schoolbook", Font.PLAIN, 40), rarityChances.get(rarities.size())));
        rarities.add(new Rarity(390, 180, "Emerald", new Color(20, 201, 81), new Font("Century Schoolbook", Font.PLAIN, 40), rarityChances.get(rarities.size())));
        rarities.add(new Rarity(417, 180, "Topaz", new Color(255, 192, 103), new Font("Century Schoolbook", Font.PLAIN, 40), rarityChances.get(rarities.size())));
        rarities.add(new Rarity(391, 180, "Sapphire", new Color(15,82,186), new Font("Century Schoolbook", Font.PLAIN, 40), rarityChances.get(rarities.size())));
        rarities.add(new Rarity(429, 180, "Ruby", new Color(	224, 17, 95), new Font("Century Schoolbook", Font.PLAIN, 40), rarityChances.get(rarities.size())));
        rarities.add(new Rarity(386, 180, "Amethyst", new Color(125, 0, 255), new Font("Century Schoolbook", Font.PLAIN, 40), rarityChances.get(rarities.size())));
        rarities.add(new Rarity(362, 180, "Aquamarine", new Color(	127, 255, 212), new Font("Century Schoolbook", Font.PLAIN, 40), rarityChances.get(rarities.size())));
        rarities.add(new Rarity(432, 180, "Jade", new Color(3, 248, 162), new Font("Century Schoolbook", Font.PLAIN, 40), rarityChances.get(rarities.size())));
        rarities.add(new Rarity(429, 180, "Opal", new Color(246, 149, 226), new Font("Century Schoolbook", Font.PLAIN, 40), rarityChances.get(rarities.size())));
        rarities.add(new Rarity(389, 180, "Platinum", new Color(229,228,226), new Font("Century Schoolbook", Font.PLAIN, 40), rarityChances.get(rarities.size())));
        rarities.add(new Rarity(387, 180, "Titanium", new Color(121, 121, 130), new Font("Century Schoolbook", Font.PLAIN, 40), rarityChances.get(rarities.size())));
        rarities.add(new Rarity(376, 180, "Moonstone", new Color(190, 211, 255), new Font("Century Schoolbook", Font.PLAIN, 40), rarityChances.get(rarities.size())));
    }

    public Rarity generateRandomRarity(double luck) {
        boolean obtainedRarity = false;
        while (!obtainedRarity) {
            Rarity randomRarity = rarities.get((int) (Math.random() * rarities.size()));
            int randomRarityCalculation = (int) ((Math.random() * ((int) randomRarity.getChance() - luck)) + 1);
            if (randomRarityCalculation == 1) {
                obtainedRarity = true;
                return randomRarity;
            }
        }
        // error?
        return new Rarity(425, 180, "NULL", new Color(255, 255, 255), new Font("Courier New", Font.PLAIN, 40), 1);
    }

    public Rarity generateRandomRarity(double luck, int currentGemIndexProgress) {
        boolean obtainedRarity = false;
        while (!obtainedRarity) {
            Rarity randomRarity = rarities.get((int) (Math.random() * (currentGemIndexProgress + 1)));
            int randomRarityCalculation = (int) ((Math.random() * ((int) randomRarity.getChance() - luck)) + 1);
            if (randomRarityCalculation == 1) {
                obtainedRarity = true;
                return randomRarity;
            }
        }
        // error?
        return new Rarity(425, 180, "NULL", new Color(255, 255, 255), new Font("Courier New", Font.PLAIN, 40), 1);
    }

    public String raritySimulation(double luck, int times) {
        String str = "";
        int[] rarityCounts = new int[rarities.size()];
        for (int i = 0; i < times; i++) {
            Rarity rarity = generateRandomRarity(luck);
            for (int j = 0; j < rarities.size(); j++) {
                if (rarities.get(j).getName().equals(rarity.getName())) {
                    rarityCounts[j]++;
                }
            }
        }
        for (int i = 0; i < rarities.size(); i++) {
            str += rarities.get(i).getName() + ": " + rarityCounts[i] + " times or " + ((double) (rarityCounts[i])/times) * 100 + "%\n";
        }
        return str;
    }

    public String raritySimulation(double luck, int times, String specificRarity) {
        int[] rarityCounts = new int[rarities.size()];
        for (int i = 0; i < times; i++) {
            Rarity rarity = generateRandomRarity(luck);
            for (int j = 0; j < rarities.size(); j++) {
                if (rarities.get(j).getName().equals(rarity.getName())) {
                    rarityCounts[j]++;
                }
            }
        }
        for (int i = 0; i < rarities.size(); i++) {
            if (rarities.get(i).getName().equals(specificRarity)) {
                return ((double) (rarityCounts[i])/times) * 100 + "%";
            }
        }
        return "Not found";
    }

    public String[] raritySimulation(double luck, int times, boolean getPercentagesOnly) {
        if (getPercentagesOnly) {
            int[] rarityCounts = new int[rarities.size()];
            String[] percentageList = new String[rarities.size()];
            for (int i = 0; i < times; i++) {
                Rarity rarity = generateRandomRarity(luck);
                for (int j = 0; j < rarities.size(); j++) {
                    if (rarities.get(j).getName().equals(rarity.getName())) {
                        rarityCounts[j]++;
                    }
                }
            }
            for (int i = 0; i < percentageList.length; i++) {
                percentageList[i] = String.format("%.2f", ((double) (rarityCounts[i]) / times) * 100) + "%";
            }
            return percentageList;
        }
        else {
            return new String[0];
        }
    }

    public ArrayList<Integer> parseRarityChancesFile() {
        File f = new File("data/RarityChances");
        Scanner s = null;
        try {
            s = new Scanner(f);
        }
        catch (FileNotFoundException e) {
            System.out.println("File not found.");
            System.exit(1);
        }

        ArrayList<String> fileData = new ArrayList<String>();
        ArrayList<Integer> rarityChances = new ArrayList<Integer>();
        while (s.hasNextLine())
            fileData.add(s.nextLine());

        for (int i = 0; i < fileData.size(); i++) {
            String str = fileData.get(i);
            int index = str.indexOf("=");
            rarityChances.add(Integer.parseInt(str.substring(index + 1)));
        }

        return rarityChances;
    }

    public int getLuck() {
        return luck;
    }

    public void setLuck(int luck) {
        this.luck = luck;
    }

    public ArrayList<Rarity> getRarities() {
        return rarities;
    }
}
