import java.awt.*;

public class Rarity {
    private int xPos;
    private int yPos;
    private String name;
    private Color color;
    private Font fontInfo;
    private int chance; // not the actual chance, just a number to do calculations with

    public Rarity(int xPos, int yPos, String name, Color color, Font fontInfo, int chance) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.name = name;
        this.color = color;
        this.fontInfo = fontInfo;
        this.chance = chance;
    }

    public int getxPos() {
        return xPos;
    }

    public int getyPos() {
        return yPos;
    }

    public String getName() {
        return name;
    }

    public Color getColor() {
        return color;
    }

    public Font getFontInfo() {
        return fontInfo;
    }

    public int getChance() {
        return chance;
    }
}
