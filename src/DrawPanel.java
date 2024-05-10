import java.awt.*;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;

class DrawPanel extends JPanel implements MouseListener {
    private Rectangle testLuckButton;
    private Rectangle updateChancesButton;
    private Rarities rarities;
    private Rarity rolledRarity;
    private Rarity highestRolledRarity;
    private int totalRolls;
    private int totalRollsForHighestRarity;
    private String[] rarityPercentages;
    private final int STARTING_SIMULATION_TIMES = 1000000;
    private final int MAX_LUCK = 100;

    public DrawPanel() {
        testLuckButton = new Rectangle(375, 200, 200, 30);
        updateChancesButton = new Rectangle(770, 325, 200, 30);
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
        g.drawRect((int)testLuckButton.getX(), (int)testLuckButton.getY(), (int)testLuckButton.getWidth(), (int)testLuckButton.getHeight());

        // Update chances button
        g.setFont(new Font("Courier New", Font.PLAIN, 20));
        g.setColor(Color.white);
        g.drawString("UPDATE CHANCES", 785, 346);
        g.drawRect((int)updateChancesButton.getX(), (int)updateChancesButton.getY(), (int)updateChancesButton.getWidth(), (int)updateChancesButton.getHeight());

        // Luck display and making sure luck doesn't exceed the maximum luck set by the program
        if (rarities.getLuck() >= MAX_LUCK) {
            rarities.setLuck(MAX_LUCK);
            g.drawString("Luck: " + rarities.getLuck() + " (MAX)", 10, 25);
        }
        else {
            g.drawString("Luck: " + rarities.getLuck(), 10, 25);
        }

        // Total rolls display
        g.drawString("Total rolls: " + totalRolls, 10, 65);

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
            if (testLuckButton.contains(clicked)) {
                //System.out.println(rarities.raritySimulation(luck, 1000000));
                //System.out.println(rarities.raritySimulation(luck, 1000000, "Moonstone"));
                rolledRarity = rarities.generateRandomRarity(rarities.getLuck());
                totalRolls++;
                rarities.setLuck(rarities.getLuck() + rolledRarity.getChance() / 100);
                if (highestRolledRarity == null) { // No rarity has been rolled yet, therefore no highest rarity
                    highestRolledRarity = rolledRarity;
                    totalRollsForHighestRarity = totalRolls;
                }
                else {
                    if (rolledRarity.getChance() > highestRolledRarity.getChance()) { // New highest rarity
                        highestRolledRarity = rolledRarity;
                        totalRollsForHighestRarity = totalRolls;
                    }
                }
            } else if (!testLuckButton.contains(clicked)) {
                // do nothing for now
            }
            if (updateChancesButton.contains(clicked)) {
                rarityPercentages = rarities.raritySimulation(rarities.getLuck(), STARTING_SIMULATION_TIMES, true);
            }
        }
    }
}