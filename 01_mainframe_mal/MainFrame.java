import javax.swing.JFrame;

public class MainFrame
{
    public static void main (String[] args)
    {
        JFrame frame = new JFrame ();


        // Por defecto no tienen tamaño
        frame.setBounds (100, 100, 600, 400);

        // Por defecto no cierra, se hace invisible
        frame.setDefaultCloseOperation (JFrame.DISPOSE_ON_CLOSE);
        
        // Por defecto los formularios no son visibles
        frame.setVisible (true);
    }
}