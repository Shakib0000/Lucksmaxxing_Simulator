import java.awt.*;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;

class DrawPanel extends JPanel implements MouseListener {
    private Rectangle button;
    private Rarities rarities;
    private Rarity rolledRarity;
    private Rarity highestRolledRarity;
    private int totalRolls;
    private int totalRollsForHighestRarity;
    private String[] rarityPercentages;
    private final int STARTING_SIMULATION_TIMES = 1000000;

    public DrawPanel() {
        button = new Rectangle(375, 200, 200, 30);
        this.addMouseListener(this);
        this.setBackground(Color.BLACK);
        rarities = new Rarities(1);
        rarityPercentages = rarities.raritySimulation(rarities.getLuck(), STARTING_SIMULATION_TIMES, true);
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Test luck button
        g.setFont(new Font("Courier New", Font.PLAIN, 20));
        g.setColor(Color.white);
        g.drawString("Test your luck!", 386, 221);

        // Luck and total rolls display
        g.drawString("Luck: " + rarities.getLuck(), 10, 25);
        g.drawString("Total rolls: " + totalRolls, 10, 65);
        g.drawRect((int)button.getX(), (int)button.getY(), (int)button.getWidth(), (int)button.getHeight());

        // Displaying rarity chances
        for (int i = 0; i < rarities.getRarities().size(); i++) {
            g.setColor(rarities.getRarities().get(i).getColor());
            g.setFont(new Font("Courier New", Font.PLAIN, 20));
            g.drawString(rarities.getRarities().get(i).getName() + ": " + rarityPercentages[i], 775, 10 + 20*(i+1));
        }

        // Displaying currently rolled rarity
        if (rolledRarity != null) {
            g.setColor(rolledRarity.getColor());
            g.setFont(new Font(rolledRarity.getFontInfo().getName(), rolledRarity.getFontInfo().getStyle(), rolledRarity.getFontInfo().getSize()));
            g.drawString(rolledRarity.getName(), rolledRarity.getxPos(), rolledRarity.getyPos());
        }

        // Displaying rarest rarity rolled
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

        // Making sure luck doesn't exceed the chance variable of the rarest rarity
        if (rarities.getLuck() > rarities.getRarities().get(rarities.getRarities().size() - 1).getChance()) {
            changeLuck(rarities.getRarities().get(rarities.getRarities().size() - 1).getChance());
        }
    }

    public void changeLuck(int luck) {
        rarities.setLuck(luck);
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
                rolledRarity = rarities.generateRandomRarity(rarities.getLuck());
                totalRolls++;
                if (highestRolledRarity == null) { // No rarity has been rolled yet, therefore no highest rarity
                    highestRolledRarity = rolledRarity;
                    totalRollsForHighestRarity = totalRolls;
                    changeLuck(rarities.getLuck() + 5);
                }
                else {
                    if (rolledRarity.getChance() > highestRolledRarity.getChance()) { // New highest rarity
                        highestRolledRarity = rolledRarity;
                        totalRollsForHighestRarity = totalRolls;
                        changeLuck(rarities.getLuck() + 5);
                    }
                }
            } else if (!button.contains(clicked)) {
                // do nothing for now
            }
        }
    }
}