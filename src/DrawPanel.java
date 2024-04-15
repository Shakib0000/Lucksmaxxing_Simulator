import java.awt.*;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;

class DrawPanel extends JPanel implements MouseListener {
    private Rectangle button;
    private Rarities rarities;
    private Rarity rolledRarity;

    public DrawPanel() {
        button = new Rectangle(375, 200, 200, 30);
        this.addMouseListener(this);
        this.setBackground(Color.BLACK);
        rarities = new Rarities();
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setFont(new Font("Courier New", Font.PLAIN, 20));
        g.setColor(Color.white);
        g.drawString("Test your luck!", 386, 221);
        g.drawRect((int)button.getX(), (int)button.getY(), (int)button.getWidth(), (int)button.getHeight());
        if (rolledRarity != null) {
            g.setColor(rolledRarity.getColor());
            g.setFont(new Font(rolledRarity.getFontInfo().getName(), rolledRarity.getFontInfo().getStyle(), rolledRarity.getFontInfo().getSize()));
            g.drawString(rolledRarity.getName(), rolledRarity.getxPos(), rolledRarity.getyPos());
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
                rolledRarity = rarities.getRarities().get((int) (Math.random() * rarities.getRarities().size()));

            } else if (!button.contains(clicked)) {
                // do nothing for now
            }
        }
    }
}