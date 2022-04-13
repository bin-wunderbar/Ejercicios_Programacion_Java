import javax.swing.JFrame;

public class MainFrame extends JFrame
{

    public MainFrame ()
    {

        setTitle ("MainFrame");
        
        // Por defecto no tienen tamaño
        setBounds (100, 100, 600, 400);

        // Por defecto no cierra, se hace invisible
        setDefaultCloseOperation (DISPOSE_ON_CLOSE);
        
        // Por defecto los formularios no son visibles
        setVisible (true);
    }

    public static void main (String[] args)
    {
        new MainFrame ();
    }
}