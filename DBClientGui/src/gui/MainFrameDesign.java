package gui;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import dataaccess.AppConfig;
import dataaccess.DBManager;
import dataaccess.MetaDataDriver;

import java.awt.FlowLayout;
import java.io.IOException;
import java.awt.event.KeyEvent;
import java.awt.event.InputEvent;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class MainFrameDesign extends JFrame {

	private static final long serialVersionUID = 1L;
	protected JPanel contentPane;
	protected JSplitPane splitPane;
	protected JTextField textFieldConnection;
	protected JTextArea textAreaSql;
	protected JTextArea textAreaResults;
	protected JPanel panelTop;
	protected JPanel panelActions;
	protected JPanel panelButtons;
	protected JComboBox <MetaDataDriver> comboBoxConnection;
	protected DBManager dbManager;
	protected AppConfig appConfig;
	protected JToolBar toolBarConnection;
	protected JToolBar toolBarExecuteScript;
	protected JMenuBar menuBar;
	protected JMenu menuFile;
	protected JMenu menuHelp;
	protected JMenuItem menuItemExit;
	protected JMenuItem menuItemAbout;
	protected JButton buttonExecute;
	protected JButton buttonClear;
	protected JMenuItem menuItemExecuteScriptFile;
	protected JMenuItem menuItemSelectedScriptFile;
	protected ToggleButton toggleButtonSplitStatements;
	protected JTextField textFieldScriptPath;
	protected JFileChooser fileChooser;
	protected JButton buttonExecuteScriptPath;
	protected JButton buttonSelectedScriptPath;
	protected JPanel panelScriptPath;


	// --------------------------------------------------------------------------------------------
	/**
	 * Create the frame.
	 */
	public MainFrameDesign() 
	{
		setTitle ("Cliente simple de base de datos");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds (100, 100, 1103, 598);
		
		try {
			setIconImage(ImageIO.read (MainFrameDesign.class.getResource("/resources/images/dbclientgui.png")));
		} catch (IOException e) {
			e.printStackTrace();
		}		
		initComponents ();
	}

	// --------------------------------------------------------------------------------------------
	protected void initComponents ()
	{
		fileChooser				= new JFileChooser ();
		contentPane 			= new JPanel();
		splitPane				= new JSplitPane ();
		panelTop				= new JPanel ();
		panelActions			= new JPanel ();
		panelButtons			= new JPanel ();
		panelScriptPath			= new JPanel ();
		
		toolBarExecuteScript	= new JToolBar ();
		toolBarConnection		= new JToolBar ();
		
		menuBar						= new JMenuBar ();
		menuFile					= new JMenu ("Archivo");
		menuHelp					= new JMenu ("Ayuda");
		menuItemSelectedScriptFile	= new JMenuItem ("Seleccionar script");
		menuItemExecuteScriptFile	= new JMenuItem ("Ejecutar script");
		menuItemExit				= new JMenuItem ("Salir");
		menuItemAbout				= new JMenuItem ("Acerca de...");
		
		textFieldConnection			= new JTextField ();
		textAreaResults				= new JTextArea ();
		textAreaSql					= new JTextArea ();
		buttonExecute				= new JButton ("Ejecutar");
		buttonClear					= new JButton ("Borrar");
		comboBoxConnection			= new JComboBox <> ();
		toggleButtonSplitStatements	= new ToggleButton ();
		textFieldScriptPath			= new JTextField ();
		buttonExecuteScriptPath 	= new JButton ();
		buttonSelectedScriptPath 	= new JButton ("...");

		setJMenuBar (menuBar);
		menuBar.add (menuFile);
		menuBar.add (menuHelp);
		menuFile.add (menuItemSelectedScriptFile);
		menuFile.add (menuItemExecuteScriptFile);
		menuFile.addSeparator();
		menuFile.add (menuItemExit);
		menuHelp.add (menuItemAbout);
		
		menuFile.setMnemonic('a');
		menuHelp.setMnemonic('u');

		menuItemExit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, InputEvent.CTRL_DOWN_MASK));
		menuItemSelectedScriptFile.setAccelerator (KeyStroke.getKeyStroke(KeyEvent.VK_F, InputEvent.CTRL_DOWN_MASK));
		menuItemExecuteScriptFile.setAccelerator (KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK));

		buttonExecuteScriptPath.setIcon(new ImageIcon(MainFrameDesign.class.getResource("/resources/images/execute_script.png")));
		
		toggleButtonSplitStatements.setIconOn (new ImageIcon(MainFrameDesign.class.getResource("/resources/images/break.png")));
		toggleButtonSplitStatements.setIconOff (new ImageIcon(MainFrameDesign.class.getResource("/resources/images/whole.png")));
		
		fileChooser.setDialogTitle ("Seleccione el archivo de script");
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		panelTop.setLayout (new BorderLayout ());
		panelActions.setLayout (new BorderLayout ());
		panelButtons.setLayout (new FlowLayout (FlowLayout.RIGHT));
		panelButtons.setBorder(new BevelBorder (BevelBorder.LOWERED));
		splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		splitPane.setDividerLocation(250);
		panelScriptPath.setLayout (new BorderLayout (5, 5));
		
		textAreaResults.setToolTipText ("Resultados de la sentencia");
		textAreaSql.setToolTipText ("Sentencia a ejecutar");
		buttonExecuteScriptPath.setToolTipText("Ejecuta el script seleccionado");
		comboBoxConnection.setToolTipText("Motor de base de datos");
		textFieldConnection.setToolTipText("Cadena de conexión con la base de datos");
		buttonClear.setToolTipText("Borra los resultados");
		buttonExecute.setToolTipText ("Ejecuta la sentencia en el servidor especificado");
		buttonSelectedScriptPath.setToolTipText("Seleccionar script");
		textFieldScriptPath.setToolTipText ("Ruta de script");
		
		toggleButtonSplitStatements.setToolTipOn ("Separa el contenido del script en múltiples sentencias divididas por punto y coma");
		toggleButtonSplitStatements.setToolTipOff ("Mantiene el script íntegro sin divisioines por sentencias");
		
		toolBarExecuteScript.add (buttonExecuteScriptPath);
		toolBarExecuteScript.add (toggleButtonSplitStatements);

		toolBarConnection.add (comboBoxConnection);
		toolBarConnection.add (textFieldConnection);

		panelScriptPath.add (new JLabel ("Ruta de script "), BorderLayout.WEST);
		panelScriptPath.add (textFieldScriptPath, BorderLayout.CENTER);
		panelScriptPath.add (buttonSelectedScriptPath, BorderLayout.EAST);
		
		panelTop.add (toolBarExecuteScript, BorderLayout.NORTH);
		panelTop.add (panelScriptPath, BorderLayout.CENTER);
		panelTop.add (toolBarConnection, BorderLayout.SOUTH);

		splitPane.setTopComponent (new JScrollPane (textAreaResults));
		splitPane.setBottomComponent (new JScrollPane (textAreaSql));
		
		panelButtons.add (buttonClear);
		panelButtons.add (buttonExecute);
		
		panelActions.add (panelButtons, BorderLayout.SOUTH);
		
		contentPane.add (panelTop, BorderLayout.NORTH);
		contentPane.add (splitPane, BorderLayout.CENTER);
		contentPane.add (panelActions, BorderLayout.SOUTH);
		
		fileChooser.addChoosableFileFilter(new FileNameExtensionFilter ("Scripts SQL", "sql"));
		fileChooser.setAcceptAllFileFilterUsed (true);
		
		getRootPane ().setDefaultButton(buttonExecute);
	}
	
}
