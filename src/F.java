import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

/**
 * Created by ahmad on 6/25/16.
 */
public class F extends JFrame{
    public F(){
        setSize(800,600);
        enableFullscreenMode();
    }

    protected void enableFullscreenMode() {

        setUndecorated(true);

        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        if(gd.isFullScreenSupported() && false) {
            gd.setFullScreenWindow(this);
            //requestFocusInWindow();
        } else {
            System.out.println("Fullscreen mode is not supported . trying to emulate it ...");
            setSize(getToolkit().getScreenSize());
            setLocation(0, 0);

            // requestFocusInWindow();
        }


        addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                setAlwaysOnTop(true);
                super.focusGained(e);
            }

            @Override
            public void focusLost(FocusEvent e) {
                setAlwaysOnTop(false);
                super.focusLost(e);
            }
        });
    }
    public static void main(String[] args) {
        F f = new F();
        System.out.println("fffffffffff");
        CustomGUI customGUI = new CustomGUI();
        customGUI.setSize(f.getSize());
        f.setLayout(null);
        customGUI.setLocation(0,0);
        f.add(customGUI);
        f.setVisible(true);
    }
}
