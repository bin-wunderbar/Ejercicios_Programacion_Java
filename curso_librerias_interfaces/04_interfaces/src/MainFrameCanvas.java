import javax.swing.JFrame;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JOptionPane;

// ------------------------------------------------------------------------------------------------
class CanvasScreen extends Canvas
{
    private CButton button1, button2, button3;

    public CanvasScreen ()
    {
        button1 = new CButton (this, 100, 100, 100, 30, "test");
        button2 = new CButton (this, 300, 100, 100, 30, "ok");
        button3 = new CButton (this, 500, 100, 100, 30, "Close"){
            @Override
            public void onMouseClicked (MouseEvent mouseEvent)
            {
                System.exit (0);
            }
        };

        addMouseListener(new MouseAdapter (){
            @Override
            public void mousePressed (MouseEvent mouseEvent)
            {
                button1.mousePressed(mouseEvent);
                button2.mousePressed (mouseEvent);
                button3.mousePressed (mouseEvent);
            }

            @Override
            public void mouseReleased (MouseEvent mouseEvent)
            {
                button1.mouseReleased(mouseEvent);
                button2.mouseReleased(mouseEvent);
                button3.mouseReleased (mouseEvent);
            }
        });

        button2.setComponentAction(new IComponentAction (){
            @Override
            public void onComponentAction ()
            {
                JOptionPane.showMessageDialog (null, "Ok!");
            }
        });
    }
    
    public void paint (Graphics g)
    {
        button1.paint (g);
        button2.paint (g);
        button3.paint (g);
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
