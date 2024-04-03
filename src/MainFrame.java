import javax.swing.JFrame;
import java.awt.*;

public class MainFrame extends JFrame implements Runnable {
    private DrawPanel p;
    private Thread windowThread;

    public MainFrame(String display) {
        super(display);
        int frameWidth = 1000;
        int frameHeight = 500;
        p = new DrawPanel();
        this.add(p);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(frameWidth, frameHeight);
        this.setLocation(450, 250);
        this.setVisible(true);
        startThread();
    }

    public void startThread() {
        windowThread = new Thread(this);
        windowThread.start();
    }

    public void run() {
        while (true) {
            p.repaint();
        }
    }
}