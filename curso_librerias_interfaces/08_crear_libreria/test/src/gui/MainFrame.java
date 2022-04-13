package gui;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import javax.swing.JOptionPane;
import java.awt.event.MouseEvent;

import ccomponents.CButton;
import ccomponents.CWindowManager;
import ccomponents.CWindow;

// ------------------------------------------------------------------------------------------------
public class MainFrame extends JFrame
{
    private CWindowManager windowManager;
    private CWindow window1, window2;
    private CButton button1, button2, button3;

    // --------------------------------------------------------------------------------------------
    public MainFrame ()
    {
        setDefaultCloseOperation (DISPOSE_ON_CLOSE);

        setBounds (100, 100, 1024, 768);
        windowManager = new CWindowManager ();
        getContentPane ().add (windowManager, BorderLayout.CENTER);


        window1 = new CWindow (100, 100, 400, 250, "Ventana1");
        button1 = new CButton (50, 100, 100, 30, "Test"){
            public void onMouseClicked (MouseEvent mouseEvent)
            {
                JOptionPane.showMessageDialog (null, "button event");
            }
        };

        button2 = new CButton (200, 100, 100, 30, "Test2");

        windowManager.addComponent (window1);
        window1.addComponent(button1);
        window1.addComponent(button2);

        window2 = new CWindow (500, 150, 400, 250, "Ventana2");
        windowManager.addComponent (window2);

        button3 = new CButton (150, 150, 100, 30, "button3");
        window2.addComponent(button3);
        
        // Esta operacion generaria excepcion
        //button3.setBounds (150, 150, 0, 0);

        setVisible (true);
    }

    // --------------------------------------------------------------------------------------------
    public static void main (String[] args)
    {
        new MainFrame ();
    }
}
