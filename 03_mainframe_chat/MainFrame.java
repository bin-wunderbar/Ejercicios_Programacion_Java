import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

// FORMA 2 DE MANEJO DE EVENTOS
// ------------------------------------------------------------------------------------------------
class ButtonEnviarAccionExterna implements ActionListener
{
    private MainFrame mainFrame;

    // por defecto java crea un construtor por defecto sin parametros
    // constructor especifico con referencia al formulario
    public ButtonEnviarAccionExterna (MainFrame mainFrame)
    {
        this.mainFrame = mainFrame;
    }

    public void actionPerformed (ActionEvent actionEvent)
    {
        System.out.println ("Consola: " + mainFrame.getMensaje ());
    }
}

// ------------------------------------------------------------------------------------------------
public class MainFrame extends JFrame
{
    private JTextField textFieldMensaje;
    private JTextArea textAreaConversacion;
    private JButton buttonEnviar;
    private JButton buttonBorrar;

    // --------------------------------------------------------------------------------------------
    public MainFrame ()
    {

        setTitle ("MainFrame");
        
        // Por defecto no tienen tamaño
        setBounds (100, 100, 600, 400);

        // Por defecto no cierra, se hace invisible
        setDefaultCloseOperation (DISPOSE_ON_CLOSE);
        
        initComponents ();

        // Por defecto los formularios no son visibles
        setVisible (true);
    }

    // --------------------------------------------------------------------------------------------
    public void initComponents ()
    {
        textFieldMensaje        = new JTextField ();
        textAreaConversacion    = new JTextArea ();
        buttonEnviar            = new JButton ("Enviar");
        buttonBorrar            = new JButton ("Borrar");

        textAreaConversacion.setEditable (false);
        buttonEnviar.addActionListener (new ButtonEnviarAccion ());
        buttonEnviar.addActionListener (new ButtonEnviarAccionExterna (this));

        buttonBorrar.addActionListener (new ActionListener () {
            public void actionPerformed (ActionEvent actionEvent)
            {
                borrar ();
            }
        });

        getContentPane ().setLayout (new BorderLayout ());
        getContentPane ().add (textAreaConversacion, BorderLayout.CENTER);
        getContentPane ().add (buttonEnviar, BorderLayout.EAST);
        getContentPane ().add (textFieldMensaje, BorderLayout.SOUTH);
        getContentPane ().add (buttonBorrar, BorderLayout.WEST);

        getRootPane ().setDefaultButton(buttonEnviar);

    }

    // --------------------------------------------------------------------------------------------
    // FORMA 1 DE GESTION DE MANEJADORES DE EVENTOS (ESCUCHADORES)
    // Clase interna como manejador de eventos
    class ButtonEnviarAccion implements ActionListener
    {
        public void actionPerformed (ActionEvent actionEvent)
        {
            String mensaje = textFieldMensaje.getText();
            if (mensaje.length() > 0)
            {
                textAreaConversacion.append ("usario> " + mensaje + "\n");
                textFieldMensaje.setText ("");
            }
        }
    }

    // --------------------------------------------------------------------------------------------
    public void borrar ()
    {
        textAreaConversacion.setText ("");
    }

    // --------------------------------------------------------------------------------------------
    public String getMensaje ()
    {
        return textFieldMensaje.getText();
    }

    // --------------------------------------------------------------------------------------------
    public static void main (String[] args)
    {
        new MainFrame ();
    }
}


