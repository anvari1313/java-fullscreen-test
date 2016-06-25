import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.VolatileImage;
import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

// other classes may be extended - canvas is a common selection however.
public class CustomGUI extends Canvas {
    int x;
    Image i;

    Timer t;
    public CustomGUI(){
        t = new Timer();
        t.schedule(new TimerTask() {
            @Override
            public void run() {
                repaint();
            }
        },0,20);

        (new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    x++;
                    try {
                        Thread.sleep(21);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        })).start();

        try {
            i = ImageIO.read(new File("fish2.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private VolatileImage volatileImg;

    // ...

    public void paint(Graphics g) {
        // create the hardware accelerated image.
        createBackBuffer();

        // Main rendering loop. Volatile images may lose their contents.
        // This loop will continually render to (and produce if neccessary) volatile images
        // until the rendering was completed successfully.
        do {

            // Validate the volatile image for the graphics configuration of this
            // component. If the volatile image doesn't apply for this graphics configuration
            // (in other words, the hardware acceleration doesn't apply for the new device)
            // then we need to re-create it.
            GraphicsConfiguration gc = this.getGraphicsConfiguration();
            int valCode = volatileImg.validate(gc);

            // This means the device doesn't match up to this hardware accelerated image.
            if(valCode==VolatileImage.IMAGE_INCOMPATIBLE){
                createBackBuffer(); // recreate the hardware accelerated image.
            }

            Graphics offscreenGraphics = volatileImg.getGraphics();
            offscreenGraphics.setColor(Color.WHITE);
            offscreenGraphics.fillRect(0, 0, getWidth(), getHeight());
            offscreenGraphics.setColor(Color.BLACK);
            offscreenGraphics.drawLine(0, 0, 10, 10); // arbitrary rendering logic
            offscreenGraphics.drawImage(i,x,0,null);

            // paint back buffer to main graphics
            g.drawImage(volatileImg, 0, 0, this);
            // Test if content is lost
        } while(volatileImg.contentsLost());
    }

    // This method produces a new volatile image.
    private void createBackBuffer() {
        GraphicsConfiguration gc = getGraphicsConfiguration();
        volatileImg = gc.createCompatibleVolatileImage(getWidth(), getHeight());
    }

    public void update(Graphics g) {
        paint(g);
    }
}
