import javax.swing.JFrame;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

// ------------------------------------------------------------------------------------------------
class CanvasScreen extends Canvas
{
    private CWindow window1;

    public CanvasScreen ()
    {
        window1 = new CWindow (this, 100, 100, 300, 150, "Título");

        addMouseListener (new MouseAdapter (){
            @Override
            public void mousePressed (MouseEvent mouseEvent)
            {
                window1.mousePressed (mouseEvent);
            }

            @Override
            public void mouseReleased (MouseEvent mouseEvent)
            {
                window1.mouseReleased (mouseEvent);
            }        
        });

        addMouseMotionListener (new MouseMotionListener (){
            @Override
            public void mouseDragged (MouseEvent mouseEvent)
            {
                window1.mouseDragged (mouseEvent);
            }

            @Override
            public void mouseMoved (MouseEvent mouseEvent) {}
        });
    }
    
    public void paint (Graphics g)
    {
        window1.paint (g);
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
