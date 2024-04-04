import java.awt.*;

public class Rarity {
    private String name;
    private Color color;
    private Font fontInfo;
    private double chance;

    public Rarity(String name, Color color, Font fontInfo, double chance) {
        this.name = name;
        this.color = color;
        this.fontInfo = fontInfo;
        this.chance = chance;
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

    public double getChance() {
        return chance;
    }
}
