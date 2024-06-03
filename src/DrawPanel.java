import java.awt.*;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.JPanel;

class DrawPanel extends JPanel implements MouseListener {
    private Rectangle testLuckButton;
    private Rectangle updateChancesButton;
    private Rectangle chooseProgressionModeButton;
    private Rectangle chooseNormalModeButton;
    private Rectangle rebirthButton;
    private Rectangle[] sockets;
    private int[] socketValues;
    private Rarities rarities;
    private ArrayList<Rarity> rebirthedRarities;
    private Rarity rolledRarity;
    private Rarity highestRolledRarity;
    private int totalRolls;
    private int totalRollsForHighestRarity;
    private String[] rarityPercentages;
    private final int STARTING_SIMULATION_TIMES = 1000000;
    private final int MAX_LUCK = 100;
    private boolean gamemodeChosen;
    private boolean progressionModeEnabled;
    private int currentGemIndexProgress;
    private int numRollsPerClick;

    public DrawPanel() {
        this.addMouseListener(this);
        this.setBackground(Color.BLACK);
        chooseProgressionModeButton = new Rectangle(100, 75, 250, 300);
        chooseNormalModeButton = new Rectangle(625, 75, 250, 300);
        socketValues = new int[8];
        numRollsPerClick = 1;
        rebirthedRarities = new ArrayList<Rarity>();
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // TITLE SCREEN
        if (!gamemodeChosen) {
            g.setFont(new Font("Courier New", Font.PLAIN, 30));
            g.setColor(Color.white);

            g.drawString("Welcome to Lucksmaxxing Simulator!", 200, 40);

            g.drawString("CHOOSE", 430, 195);
            g.drawString("YOUR", 448, 225);
            g.drawString("GAMEMODE", 415, 255);

            g.setFont(new Font("Courier New", Font.PLAIN, 20));
            g.drawString("PROGRESSION MODE", 125, 225);
            g.drawRect((int) chooseProgressionModeButton.getX(), (int) chooseProgressionModeButton.getY(), (int) chooseProgressionModeButton.getWidth(), (int) chooseProgressionModeButton.getHeight());

            g.drawString("NORMAL MODE", 680, 225);
            g.drawRect((int) chooseNormalModeButton.getX(), (int) chooseNormalModeButton.getY(), (int) chooseNormalModeButton.getWidth(), (int) chooseNormalModeButton.getHeight());
        }
        else {
            // PROGRESSION MODE
            if (progressionModeEnabled) {
                // Gamemode display
                g.setFont(new Font("Courier New", Font.PLAIN, 20));
                g.setColor(Color.white);
                g.drawString("GAMEMODE: PROGRESSION", 10, 450);

                // Test luck button
                g.setFont(new Font("Courier New", Font.PLAIN, 20));
                g.setColor(Color.white);
                g.drawString("Test your luck!", 386, 221);
                g.drawRect((int) testLuckButton.getX(), (int) testLuckButton.getY(), (int) testLuckButton.getWidth(), (int) testLuckButton.getHeight());

                // Update chances button
                g.setFont(new Font("Courier New", Font.PLAIN, 20));
                g.setColor(Color.white);
                g.drawString("UPDATE CHANCES", 785, 346);
                g.drawRect((int) updateChancesButton.getX(), (int) updateChancesButton.getY(), (int) updateChancesButton.getWidth(), (int) updateChancesButton.getHeight());

                // Rebirth button
                if (highestRolledRarity != null && highestRolledRarity.getChance() / 20 > 0 && highestRolledRarity != rarities.getRarities().get(rarities.getRarities().size()-1) && !isDuplicateRebirth()) {
                    rebirthButton = new Rectangle(375, 425, 200, 30);
                    g.setFont(new Font("Courier New", Font.PLAIN, 18));
                    g.setColor(Color.white);
                    g.drawString("REBIRTH (+" + highestRolledRarity.getChance() / 20 + " rolls)", 378, 446);
                    g.drawRect((int) rebirthButton.getX(), (int) rebirthButton.getY(), (int) rebirthButton.getWidth(), (int) rebirthButton.getHeight());
                    g.setFont(new Font("Courier New", Font.PLAIN, 20));
                }

                // Luck display and making sure luck doesn't exceed the maximum luck set by the program
                if (rarities.getLuck() >= MAX_LUCK) {
                    rarities.setLuck(MAX_LUCK);
                    g.drawString("Luck: " + rarities.getLuck() + " (MAX)", 10, 25);
                } else {
                    g.drawString("Luck: " + rarities.getLuck(), 10, 25);
                }

                // Total rolls display
                g.drawString("Total rolls: " + totalRolls, 10, 65);

                // Displaying rarity chances
                for (int i = 0; i < currentGemIndexProgress + 1; i++) {
                    g.setColor(rarities.getRarities().get(i).getColor());
                    g.setFont(new Font("Courier New", Font.PLAIN, 20));
                    g.drawString(rarities.getRarities().get(i).getName() + ": " + rarityPercentages[i], 775, 10 + 20 * (i + 1));
                }

                // Displaying currently rolled rarity
                if (rolledRarity != null && !rolledRarity.getName().equals("NULL")) {
                    g.setColor(rolledRarity.getColor());
                    g.setFont(new Font(rolledRarity.getFontInfo().getName(), rolledRarity.getFontInfo().getStyle(), rolledRarity.getFontInfo().getSize()));
                    g.drawString(rolledRarity.getName(), rolledRarity.getxPos(), rolledRarity.getyPos());
                }

                // Displaying rarest rarity rolled
                if (highestRolledRarity != null && !highestRolledRarity.getName().equals("NULL")) {
                    g.setColor(highestRolledRarity.getColor());
                    g.setFont(new Font("Courier New", Font.PLAIN, 20));
                    g.drawString("Rarest rarity obtained: " + highestRolledRarity.getName() + " (" + totalRollsForHighestRarity + ")", 10, 45);
                } else {
                    g.setColor(new Color(255, 255, 255));
                    g.setFont(new Font("Courier New", Font.PLAIN, 20));
                    g.drawString("Rarest rarity obtained: None", 10, 45);
                }

                // Gem sockets
                if (highestRolledRarity != rarities.getRarities().get(rarities.getRarities().size()-1)) {
                    for (int i = 0; i < sockets.length; i++) {
                        if (socketValues[i] == 1) {
                            g.setColor(highestRolledRarity.getColor());
                        } else {
                            g.setColor(new Color(255, 255, 255));
                        }
                        g.drawRect((int) sockets[i].getX(), (int) sockets[i].getY(), (int) sockets[i].getWidth(), (int) sockets[i].getHeight());
                    }
                }
                else {
                    g.drawString("Congratulations!", 90, 220);
                    g.drawString("You rolled the highest rarity.", 10, 280);
                }
            }
            // NORMAL MODE
            else {
                // Gamemode display
                g.setFont(new Font("Courier New", Font.PLAIN, 20));
                g.setColor(Color.white);
                g.drawString("GAMEMODE: NORMAL", 10, 450);

                // Test luck button
                g.setFont(new Font("Courier New", Font.PLAIN, 20));
                g.setColor(Color.white);
                g.drawString("Test your luck!", 386, 221);
                g.drawRect((int) testLuckButton.getX(), (int) testLuckButton.getY(), (int) testLuckButton.getWidth(), (int) testLuckButton.getHeight());

                // Update chances button
                g.setFont(new Font("Courier New", Font.PLAIN, 20));
                g.setColor(Color.white);
                g.drawString("UPDATE CHANCES", 785, 346);
                g.drawRect((int) updateChancesButton.getX(), (int) updateChancesButton.getY(), (int) updateChancesButton.getWidth(), (int) updateChancesButton.getHeight());

                // Luck display and making sure luck doesn't exceed the maximum luck set by the program
                if (rarities.getLuck() >= MAX_LUCK) {
                    rarities.setLuck(MAX_LUCK);
                    g.drawString("Luck: " + rarities.getLuck() + " (MAX)", 10, 25);
                } else {
                    g.drawString("Luck: " + rarities.getLuck(), 10, 25);
                }

                // Total rolls display
                g.drawString("Total rolls: " + totalRolls, 10, 65);

                // Displaying rarity chances
                for (int i = 0; i < rarities.getRarities().size(); i++) {
                    g.setColor(rarities.getRarities().get(i).getColor());
                    g.setFont(new Font("Courier New", Font.PLAIN, 20));
                    g.drawString(rarities.getRarities().get(i).getName() + ": " + rarityPercentages[i], 775, 10 + 20 * (i + 1));
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
                } else {
                    g.setColor(new Color(255, 255, 255));
                    g.setFont(new Font("Courier New", Font.PLAIN, 20));
                    g.drawString("Rarest rarity obtained: None", 10, 45);
                }
            }
        }
    }

    public boolean socketsFull() {
        for (int i = 0; i < socketValues.length; i++) {
            if (socketValues[i] == 0) {
                return false;
            }
        }
        return true;
    }

    public void emptySockets() {
        for (int i = 0; i < socketValues.length; i++) {
            socketValues[i] = 0;
        }
    }

    public void fillSocket() {
        for (int i = 0; i < socketValues.length; i++) {
            if (socketValues[i] == 0) {
                socketValues[i] = 1;
                break;
            }
        }
    }

    public boolean isDuplicateRebirth() {
        for (int i = 0; i < rebirthedRarities.size(); i++) {
            if (highestRolledRarity == rebirthedRarities.get(i)) {
                return true;
            }
        }
        return false;
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
            if (!gamemodeChosen) {
                if (chooseProgressionModeButton.contains(clicked)) {
                    gamemodeChosen = true;
                    progressionModeEnabled = true;
                    testLuckButton = new Rectangle(375, 200, 200, 30);
                    updateChancesButton = new Rectangle(770, 325, 200, 30);
                    rarities = new Rarities(1);
                    rarityPercentages = rarities.raritySimulation(rarities.getLuck(), STARTING_SIMULATION_TIMES, true);
                    sockets = new Rectangle[8];
                    for (int i = 0; i < sockets.length; i++) {
                        if (i % 2 == 0) {
                            sockets[i] = new Rectangle(60, i*30 + 100, 30, 30);
                        }
                        else {
                            sockets[i] = new Rectangle(140, (i-1)*30 + 100, 30, 30);
                        }
                    }
                }
                else if (chooseNormalModeButton.contains(clicked)) {
                    gamemodeChosen = true;
                    progressionModeEnabled = false;
                    testLuckButton = new Rectangle(375, 200, 200, 30);
                    updateChancesButton = new Rectangle(770, 325, 200, 30);
                    rarities = new Rarities(1);
                    rarityPercentages = rarities.raritySimulation(rarities.getLuck(), STARTING_SIMULATION_TIMES, true);
                }
            }
            if (testLuckButton.contains(clicked)) {
                //System.out.println(rarities.raritySimulation(luck, 1000000));
                //System.out.println(rarities.raritySimulation(luck, 1000000, "Moonstone"));
                for (int i = 0; i < numRollsPerClick; i++) {
                    if (progressionModeEnabled) {
                        rolledRarity = rarities.generateRandomRarity(rarities.getLuck(), currentGemIndexProgress);
                        totalRolls++;
                        if (highestRolledRarity == null || highestRolledRarity.getName().equals("NULL")) { // No rarity has been rolled yet, therefore no highest rarity
                            highestRolledRarity = rolledRarity;
                            totalRollsForHighestRarity = totalRolls;
                            fillSocket();
                        } else if (socketsFull() && rolledRarity.getChance() > highestRolledRarity.getChance()) { // New highest rarity
                            highestRolledRarity = rolledRarity;
                            totalRollsForHighestRarity = totalRolls;
                            emptySockets();
                            fillSocket();
                        } else if (!socketsFull() && rolledRarity.equals(highestRolledRarity) && highestRolledRarity != rarities.getRarities().get(rarities.getRarities().size()-1)) {
                            fillSocket();
                            if (socketsFull()) {
                                currentGemIndexProgress++;
                            }
                        }
                    } else {
                        rolledRarity = rarities.generateRandomRarity(rarities.getLuck());
                        totalRolls++;
                        rarities.setLuck(rarities.getLuck() + rolledRarity.getChance() / 100);
                        if (highestRolledRarity == null) { // No rarity has been rolled yet, therefore no highest rarity
                            highestRolledRarity = rolledRarity;
                            totalRollsForHighestRarity = totalRolls;
                        } else {
                            if (rolledRarity.getChance() > highestRolledRarity.getChance()) { // New highest rarity
                                highestRolledRarity = rolledRarity;
                                totalRollsForHighestRarity = totalRolls;
                            }
                        }
                    }
                }
                rarities.setLuck(rarities.getLuck() + rolledRarity.getChance() / 100);
            }
        }
        if (updateChancesButton.contains(clicked)) {
            rarityPercentages = rarities.raritySimulation(rarities.getLuck(), STARTING_SIMULATION_TIMES, true);
        }
        if (rebirthButton != null && rebirthButton.contains(clicked) && highestRolledRarity != rarities.getRarities().get(rarities.getRarities().size()-1)) {
            if (!isDuplicateRebirth()) {
                rebirthedRarities.add(highestRolledRarity);
                numRollsPerClick += highestRolledRarity.getChance() / 20;
                rolledRarity = new Rarity(425, 180, "NULL", new Color(255, 255, 255), new Font("Courier New", Font.PLAIN, 40), -1);
                highestRolledRarity = new Rarity(425, 180, "NULL", new Color(255, 255, 255), new Font("Courier New", Font.PLAIN, 40), -1);
                currentGemIndexProgress = 0;
                emptySockets();
                rebirthButton = null;
            }
        }
    }
}