import javax.swing.JFrame;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

// ------------------------------------------------------------------------------------------------
class CanvasScreen extends Canvas
{
    private int x, y, width, height;
    private boolean pressed;

    // --------------------------------------------------------------------------------------------
    public CanvasScreen ()
    {
        setBackground (Color.LIGHT_GRAY);
        x       = 50;
        y       = 50;
        width   = 100;
        height  = 30;
        pressed = false;

        addMouseListener (new MouseAdapter () {
            @Override
            public void mousePressed (MouseEvent mouseEvent)
            {
                if (
                    mouseEvent.getX () >= x && mouseEvent.getX () < x + width && 
                    mouseEvent.getY () >= y && mouseEvent.getY () < y + height
                )
                {
                    pressed = true;
                    repaint ();
                }
            }

            @Override
            public void mouseReleased (MouseEvent mouseEvent)
            {
                pressed = false;
                repaint ();
            }
        });
    }
    
    // --------------------------------------------------------------------------------------------
    @Override
    public void paint (Graphics g)
    {
        if (pressed)
        {
            g.setColor (Color.LIGHT_GRAY);
            g.fillRoundRect (x + 2, y + 2, width, height, 5, 5);
            g.setColor (Color.GRAY);
            g.drawRoundRect (x + 2, y + 2, width, height, 5, 5);
        }
        else
        {
            g.setColor (Color.LIGHT_GRAY);
            g.fillRoundRect (x, y, width, height, 5, 5);
            g.setColor (Color.GRAY);
            g.drawRoundRect (x, y, width, height, 5, 5);
        }
    }
}

// ------------------------------------------------------------------------------------------------
public class MainFrameCanvas extends JFrame
{

    // --------------------------------------------------------------------------------------------
    public MainFrameCanvas ()
    {
        setDefaultCloseOperation (DISPOSE_ON_CLOSE);

        setBounds (100, 100, 800, 600);
        getContentPane ().add (new CanvasScreen (), BorderLayout.CENTER);

        setVisible (true);
    }

    // --------------------------------------------------------------------------------------------
    public static void main (String[] args)
    {
        new MainFrameCanvas ();
    }
}
