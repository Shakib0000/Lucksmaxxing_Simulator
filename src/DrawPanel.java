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
        g.drawRect((int)button.getX(), (int)button.getY(), (int)button.getWidth(), (int)button.getHeight());
        if (rolledRarity != null) {
            g.setColor(rolledRarity.getColor());
            g.setFont(new Font(rolledRarity.getFontInfo().getName(), rolledRarity.getFontInfo().getStyle(), rolledRarity.getFontInfo().getSize()));
            g.drawString(rolledRarity.getName(), rolledRarity.getxPos(), rolledRarity.getyPos());
        }
        if (highestRolledRarity != null) {
            g.setColor(highestRolledRarity.getColor());
            g.setFont(new Font("Courier New", Font.PLAIN, 20));
            g.drawString("Rarest rarity obtained: " + highestRolledRarity.getName(), 10, 45);
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
                rolledRarity = rarities.generateRandomRarity(luck);
                if (highestRolledRarity == null) {
                    highestRolledRarity = rolledRarity;
                }
                else {
                    if (rolledRarity.getChance() > highestRolledRarity.getChance()) {
                        highestRolledRarity = rolledRarity;
                    }
                }
            } else if (!button.contains(clicked)) {
                // do nothing for now
            }
        }
    }
}