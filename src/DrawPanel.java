import java.awt.*;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;

class DrawPanel extends JPanel implements MouseListener {
    private Rectangle button;
    public DrawPanel() {
        button = new Rectangle(375, 200, 200, 30);
        this.addMouseListener(this);
        this.setBackground(Color.BLACK);
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setFont(new Font("Courier New", Font.PLAIN, 20));
        g.setColor(Color.white);
        g.drawString("Test your luck!", 386, 221);
        g.drawRect((int)button.getX(), (int)button.getY(), (int)button.getWidth(), (int)button.getHeight());
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
                System.out.println("Left Click");
            } else if (!button.contains(clicked)) {
                System.out.println("N");
            }
        }
    }
}