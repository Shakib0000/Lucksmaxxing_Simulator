import java.awt.*;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;

class DrawPanel extends JPanel implements MouseListener {
    private Rectangle button;
    private Rarities rarities;
    private Rarity rolledRarity;
    private Rarity highestRolledRarity;
    private double luck;
    private int totalRolls;
    private int totalRollsForHighestRarity;

    public DrawPanel() {
        button = new Rectangle(375, 200, 200, 30);
        this.addMouseListener(this);
        this.setBackground(Color.BLACK);
        rarities = new Rarities();
        luck = 1;
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setFont(new Font("Courier New", Font.PLAIN, 20));
        g.setColor(Color.white);
        g.drawString("Test your luck!", 386, 221);
        g.drawString("Luck: " + luck, 10, 25);
        g.drawString("Total rolls: " + totalRolls, 10, 65);
        g.drawRect((int)button.getX(), (int)button.getY(), (int)button.getWidth(), (int)button.getHeight());
        for (int i = 0; i < Rarities.getRarities().size(); i++) {
            g.setColor(Rarities.getRarities().get(i).getColor());
            g.setFont(new Font("Courier New", Font.PLAIN, 20));
            // temporarily displays chance variable for now, will display the actual percentage to get the rarity soon
            g.drawString(Rarities.getRarities().get(i).getName() + ": " + Rarities.getRarities().get(i).getChance(), 800, 10 + 20*(i+1));
        }
        if (rolledRarity != null) {
            g.setColor(rolledRarity.getColor());
            g.setFont(new Font(rolledRarity.getFontInfo().getName(), rolledRarity.getFontInfo().getStyle(), rolledRarity.getFontInfo().getSize()));
            g.drawString(rolledRarity.getName(), rolledRarity.getxPos(), rolledRarity.getyPos());
        }
        if (highestRolledRarity != null) {
            g.setColor(highestRolledRarity.getColor());
            g.setFont(new Font("Courier New", Font.PLAIN, 20));
            g.drawString("Rarest rarity obtained: " + highestRolledRarity.getName() + " (" + totalRollsForHighestRarity + ")", 10, 45);
        }
        else {
            g.setColor(new Color(255,255,255));
            g.setFont(new Font("Courier New", Font.PLAIN, 20));
            g.drawString("Rarest rarity obtained: None", 10, 45);
        }
    }

    public void mouseReleased(MouseEvent e) { }
    public void mouseEntered(MouseEvent e) { }
    public void mouseExited(MouseEvent e) { }
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        Point clicked = e.getPoint();

        if (e.getButton() == 1) {
            if (button.contains(clicked)) {
                //System.out.println(rarities.raritySimulation(luck, 1000000));
                //System.out.println(rarities.raritySimulation(luck, 1000000, "Moonstone"));
                rolledRarity = rarities.generateRandomRarity(luck);
                totalRolls++;
                if (highestRolledRarity == null) {
                    highestRolledRarity = rolledRarity;
                    totalRollsForHighestRarity = totalRolls;
                }
                else {
                    if (rolledRarity.getChance() > highestRolledRarity.getChance()) {
                        highestRolledRarity = rolledRarity;
                        totalRollsForHighestRarity = totalRolls;
                    }
                }
            } else if (!button.contains(clicked)) {
                // do nothing for now
            }
        }
    }
}