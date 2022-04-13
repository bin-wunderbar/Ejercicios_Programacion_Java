package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import dataaccess.AppConfig;
import dataaccess.DBManager;
import dataaccess.MetaDataDriver;

// ------------------------------------------------------------------------------------------------
public class MainFrameLogic extends MainFrameDesign 
{
	private static final long serialVersionUID = 1L;
	
	// --------------------------------------------------------------------------------------------
	public MainFrameLogic ()
	{
		initApp ();
		loadValues ();
		selectedPreferredEngine();
		setListeners ();
	}

	// --------------------------------------------------------------------------------------------
	protected void initApp ()
	{
		appConfig = new AppConfig ();
		
		try {
			dbManager = new DBManager ();
		} catch (SQLException sqlException) {
			showException (sqlException);
		}
	}
	
	// --------------------------------------------------------------------------------------------
	protected void loadValues ()
	{
		for (MetaDataDriver metaDataDriver : appConfig.getMetaDataDrivers())
		{
			comboBoxConnection.addItem(metaDataDriver);
		}

		toggleButtonSplitStatements.setSelected(appConfig.isSplitScript());
		textAreaSql.setText(appConfig.getSqlStatement());
		textAreaResults.setText(appConfig.getSqlResults());
		textFieldScriptPath.setText(appConfig.getScriptPath());
		setBounds (appConfig.getBounds());
	}
	
	// --------------------------------------------------------------------------------------------
	protected void requestFocusSqlStatement() 
	{
		textAreaSql.requestFocus();
		textAreaSql.selectAll();
	}

	// --------------------------------------------------------------------------------------------
	protected void verifyWindowClosing() 
	{
		saveWorkSpace ();
		dispose ();
	}

	// --------------------------------------------------------------------------------------------
	protected void saveWorkSpace ()
	{
		if (appConfig.isSaveWorkSpace())
		{
			try {
				appConfig.setSqlStatement(textAreaSql.getText());
				appConfig.setSqlResults (textAreaResults.getText());
				appConfig.setScriptPath(textFieldScriptPath.getText());
				appConfig.setSplitScript(toggleButtonSplitStatements.isSelected());
				appConfig.setBounds (getBounds ());
				
				appConfig.saveToDisk();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	// --------------------------------------------------------------------------------------------
	protected void selectedPreferredEngine ()
	{
		for (int index = 0; index < comboBoxConnection.getItemCount(); index++)
		{
			MetaDataDriver metaDataDriver = (MetaDataDriver)comboBoxConnection.getItemAt(index); 
			if ((metaDataDriver.getName ().equalsIgnoreCase(appConfig.getPreferredEngine())))
			{
				comboBoxConnection.setSelectedIndex(index);
				textFieldConnection.setText (metaDataDriver.getConnectionString());
				break;
			}
		}
	}
	
	// --------------------------------------------------------------------------------------------
	protected void updateConnectionString ()
	{
		MetaDataDriver metaDataDriver = (MetaDataDriver) comboBoxConnection.getSelectedItem();
		metaDataDriver.setConnectionString(textFieldConnection.getText());
	}
	
	// --------------------------------------------------------------------------------------------
	protected void selectedScriptFile ()
	{
		if (fileChooser.showOpenDialog (this) == JFileChooser.APPROVE_OPTION)
		{
			textFieldScriptPath.setText(fileChooser.getSelectedFile().getAbsolutePath());
		}
	}
	
	// --------------------------------------------------------------------------------------------
	protected void clear ()
	{
		textAreaResults.setText("");
		requestFocusSqlStatement();
	}
	
	// --------------------------------------------------------------------------------------------
	protected void setConnectionString ()
	{
		MetaDataDriver metaDataDriver = ((MetaDataDriver)comboBoxConnection.getSelectedItem());
		appConfig.setPreferredEngine(metaDataDriver.getName());
		textFieldConnection.setText(metaDataDriver.getConnectionString());
	}
	
	// --------------------------------------------------------------------------------------------
	protected void printSql (String sql)
	{
		textAreaResults.append ("---------------------------------------------------------------------------------------------------\n");
		textAreaResults.append (sql + "\n");
		textAreaResults.append ("---------------------------------------------------------------------------------------------------\n");
	}
	
	// --------------------------------------------------------------------------------------------
	protected void executeSql() 
	{
		if (textAreaSql.getText().length() > 0)
		{
			printSql (textAreaSql.getText());
			
			try {
				String resultSetString = dbManager.executeSql (textFieldConnection.getText (), textAreaSql.getText());
				textAreaResults.append (resultSetString);
				textAreaResults.setCaretPosition(textAreaResults.getText().length());
				textAreaSql.selectAll();
				textAreaSql.requestFocus();

			} catch (SQLException sqlException) {
				showException (sqlException);
			}
		}
	}
	
	// --------------------------------------------------------------------------------------------
	protected void executeScriptFile ()
	{
		if (textFieldScriptPath.getText ().length () > 0)
		{
			try {
				String scriptSql = getFileAsString (textFieldScriptPath.getText());
				printSql (scriptSql);
	
				String resultSetString = dbManager.executeSql (textFieldConnection.getText (), scriptSql, toggleButtonSplitStatements.isSelected());
				textAreaResults.append (resultSetString);
				
			} catch (IOException | SQLException ioException) {
				showException (ioException);
			}
		}
		else
		{
			selectedScriptFile ();
		}
	}
	
	// --------------------------------------------------------------------------------------------
	protected String getFileAsString (String fileName) throws IOException
	{
		
		FileInputStream fileInputStream = new FileInputStream (fileName);
		byte[] content = new byte[fileInputStream.available()];
		fileInputStream.read (content);
		fileInputStream.close ();
		
		return new String (content, StandardCharsets.UTF_8);
	}
	
	// --------------------------------------------------------------------------------------------
	protected void showException (Exception exception)
	{
		textAreaResults.append (
				appConfig.isShowStackTraceExceptions() ?
				getStackTraceText (exception) + "\n" :
				exception.getMessage()
				);
		textAreaResults.setCaretPosition(textAreaResults.getText().length());
	}
	
	// --------------------------------------------------------------------------------------------
	protected String getStackTraceText (Exception exception)
	{
		StringWriter stringWriter = new StringWriter();
		PrintWriter printWriter = new PrintWriter(stringWriter);
		exception.printStackTrace (printWriter);
		
		return stringWriter.toString();
	}

	// --------------------------------------------------------------------------------------------
	protected void showAboutDialog ()
	{
		JOptionPane.showMessageDialog (this, "Cliente simple de base de datos 1.0\nEclipse 4.21.0\njava 16\nDaniel Cano Corces\nDBClientGUI 1.0");
	}
	
	// --------------------------------------------------------------------------------------------
	protected void setListeners ()
	{
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				requestFocusSqlStatement ();
			}
			
			@Override
			public void windowClosing(WindowEvent e) {
				verifyWindowClosing ();
			}
		});

		buttonExecute.addActionListener (new ActionListener () {
			@Override
			public void actionPerformed (ActionEvent actionEvent)
			{
				executeSql ();
			}
		});
		
		buttonClear.addActionListener(new ActionListener () {
			@Override
			public void actionPerformed (ActionEvent actionEvent)
			{
				clear ();
			}
		});
		
		comboBoxConnection.addActionListener(new ActionListener () {
			@Override
			public void actionPerformed (ActionEvent actionEvent)
			{
				setConnectionString ();
			}
		});
		
		menuItemExit.addActionListener(new ActionListener () {
			@Override
			public void actionPerformed (ActionEvent actionEvent)
			{
				verifyWindowClosing ();
			}
		});
		
		menuItemSelectedScriptFile.addActionListener (new ActionListener () {
			@Override
			public void actionPerformed (ActionEvent actionEvent)
			{
				selectedScriptFile ();
			}
		});
		
		buttonSelectedScriptPath.addActionListener (new ActionListener () {
			@Override
			public void actionPerformed (ActionEvent actionEvent)
			{
				selectedScriptFile ();
			}
		});
		
		menuItemExecuteScriptFile.addActionListener (new ActionListener () {
			@Override
			public void actionPerformed (ActionEvent actionEvent)
			{
				executeScriptFile ();
			}
		});
		
		buttonExecuteScriptPath.addActionListener (new ActionListener () {
			@Override
			public void actionPerformed (ActionEvent actionEvent)
			{
				executeScriptFile ();
			}
		});
		
		menuItemAbout.addActionListener (new ActionListener () {
			@Override
			public void actionPerformed (ActionEvent actionEvent)
			{
				showAboutDialog ();
			}
		});
		
		textFieldConnection.getDocument().addDocumentListener(new DocumentListener () {

			@Override
			public void insertUpdate(DocumentEvent e) { updateConnectionString (); }
			@Override
			public void removeUpdate(DocumentEvent e) { updateConnectionString (); }
			@Override
			public void changedUpdate(DocumentEvent e) { updateConnectionString (); }
			
		});
	}

}
