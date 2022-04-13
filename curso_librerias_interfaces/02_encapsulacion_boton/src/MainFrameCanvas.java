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
    private CButton button1, button2;

    public CanvasScreen ()
    {
        button1 = new CButton (this, 100, 100, 100, 30, "test");
        button2 = new CButton (this, 300, 100, 100, 30, "ok");

        addMouseListener(new MouseAdapter (){
            @Override
            public void mousePressed (MouseEvent mouseEvent)
            {
                button1.mousePressed(mouseEvent);
                button2.mousePressed (mouseEvent);
            }

            @Override
            public void mouseReleased (MouseEvent mouseEvent)
            {
                button1.mouseReleased(mouseEvent);
                button2.mouseReleased(mouseEvent);
            }
        });
    }
    
    public void paint (Graphics g)
    {
        button1.paint(g);
        button2.paint(g);
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
