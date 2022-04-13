package dataaccess;

import java.io.File;
import java.awt.Rectangle;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Scanner;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

// ------------------------------------------------------------------------------------------------
public class AppConfig 
{
	{
		try {
			// Crea el archivo de log en el directorio temporal del sistema
			Logger.getLogger (AppConfig.class.getName()).addHandler (new FileHandler("%t/dbclientgui.log"));
		} catch (SecurityException | IOException e) {
			e.printStackTrace();
		}
	}
	
	public static final Logger LOGGER 					= Logger.getLogger (AppConfig.class.getName());
	public static final String APP_DIRECTORY	 		= ".dbclientgui";
	public static final String CONFIG_FILE 				= "appconfig.properties";

	private MetaDataDriver mariadbMetaData;
	private MetaDataDriver derbyMetaData;
	private MetaDataDriver oracleMetaData;
	private MetaDataDriver mssqlMetaData;
	private boolean showStackTraceExceptions;
	private String preferredEngine;
	private boolean saveWorkSpace;
	private String sqlStatement;
	private String sqlResults;
	private String scriptPath;
	private boolean splitScript;
	private Rectangle bounds;
	
	// --------------------------------------------------------------------------------------------
	public AppConfig ()
	{
		sqlStatement		= "";
		sqlResults			= "";
		bounds				= new Rectangle (100, 100, 1103, 598);
		initMetaData ();
		loadFromDisk ();
	}
	
	// --------------------------------------------------------------------------------------------
	protected void initMetaData ()
	{
		mariadbMetaData 			= new MetaDataDriver ("mariadb", "jdbc:mariadb://localhost:3306/Test?user=mysqladmin&password=Password1&allowMultiQueries=true");
		derbyMetaData 				= new MetaDataDriver ("derby", "jdbc:derby:/home/user/.databases/derby/Test;create=true");
		oracleMetaData				= new MetaDataDriver ("oracle", "jdbc:oracle:thin:system/Password1@localhost:1521:");
		mssqlMetaData				= new MetaDataDriver ("sqlserver", "jdbc:sqlserver://localhost:1433;databaseName=Test;user=sqladmin;password=Password1;");
	}

	// --------------------------------------------------------------------------------------------
	protected void loadDefaults ()
	{
		showStackTraceExceptions 	= false;
		preferredEngine				= "mariadb";
		saveWorkSpace				= true;
		sqlStatement				= "";
		scriptPath					= "";
		splitScript					= true;
	}

	// --------------------------------------------------------------------------------------------
	public void saveToDisk () throws IOException
	{
		File fileDirectoryManager = new File (getAppPath());
		
		if (!fileDirectoryManager.exists())
		{
			fileDirectoryManager.mkdirs();
		}

		FileOutputStream fileOutputStream = new FileOutputStream (getConfigPath());
		
		fileOutputStream.write(getFormatedBytes (mariadbMetaData.getName(),mariadbMetaData.getConnectionString()));
		fileOutputStream.write(getFormatedBytes (derbyMetaData.getName(),derbyMetaData.getConnectionString()));
		fileOutputStream.write(getFormatedBytes (oracleMetaData.getName(),oracleMetaData.getConnectionString()));
		fileOutputStream.write(getFormatedBytes("showStackTraceExceptions", showStackTraceExceptions));
		fileOutputStream.write(getFormatedBytes("preferredEngine", preferredEngine));
		fileOutputStream.write(getFormatedBytes("saveWorkSpace", saveWorkSpace));
		fileOutputStream.write(getFormatedBytes("sqlStatement", sqlStatement.replaceAll("\n", "\\\\n")));
		fileOutputStream.write(getFormatedBytes("sqlResults", sqlResults.replaceAll("\n", "\\\\n")));
		fileOutputStream.write(getFormatedBytes("scriptPath", scriptPath));
		fileOutputStream.write(getFormatedBytes("splitScript", splitScript));
		fileOutputStream.write(getFormatedBytes ("bounds", getBoundsText ()));
		fileOutputStream.close ();
		
		printLog ("Save workspace ok");
	}
	
	// --------------------------------------------------------------------------------------------
	private String getBoundsText ()
	{
		return bounds.x + " " + bounds.y + " " + bounds.width + " " + bounds.height;
	}

	// --------------------------------------------------------------------------------------------
	private void setBoundsFromText (String boundsText)
	{
		Scanner scanner = new Scanner (boundsText);

		if (scanner.hasNextInt ()) bounds.x 		= scanner.nextInt();
		if (scanner.hasNextInt ()) bounds.y 		= scanner.nextInt();
		if (scanner.hasNextInt ()) bounds.width 	= scanner.nextInt();
		if (scanner.hasNextInt ()) bounds.height	= scanner.nextInt();
		
		scanner.close ();
	}
	
	// --------------------------------------------------------------------------------------------
	private byte[] getFormatedBytes (String key, Object value)
	{
		return (key + "=" + value + "\n").getBytes();
	}
	
	// --------------------------------------------------------------------------------------------
	private void loadFromDisk ()
	{
		try {
			HashMap <String, String> dictionaryConfig = getDictionaryFromStream (new FileInputStream (getConfigPath ()));
			
			
			if (dictionaryConfig.containsKey (mariadbMetaData.getName ())) mariadbMetaData.setConnectionString(dictionaryConfig.get(mariadbMetaData.getName ()));
			if (dictionaryConfig.containsKey (derbyMetaData.getName ())) derbyMetaData.setConnectionString(dictionaryConfig.get(derbyMetaData.getName ()));
			if (dictionaryConfig.containsKey (oracleMetaData.getName ())) oracleMetaData.setConnectionString(dictionaryConfig.get(oracleMetaData.getName ()));
			if (dictionaryConfig.containsKey (mssqlMetaData.getName ())) mssqlMetaData.setConnectionString(dictionaryConfig.get(mssqlMetaData.getName ()));
			if (dictionaryConfig.containsKey("showStackTraceExceptions")) showStackTraceExceptions = Boolean.parseBoolean(dictionaryConfig.get ("showStackTraceExceptions"));
			if (dictionaryConfig.containsKey("preferredEngine")) preferredEngine = dictionaryConfig.get("preferredEngine");
			if (dictionaryConfig.containsKey("saveWorkSpace")) saveWorkSpace = Boolean.parseBoolean(dictionaryConfig.get ("saveWorkSpace"));
			if (dictionaryConfig.containsKey("sqlStatement")) sqlStatement = dictionaryConfig.get ("sqlStatement").replaceAll("\\\\n", "\n");
			if (dictionaryConfig.containsKey("sqlResults")) sqlResults = dictionaryConfig.get ("sqlResults").replaceAll("\\\\n", "\n");
			if (dictionaryConfig.containsKey("scriptPath")) scriptPath = dictionaryConfig.get ("scriptPath");
			if (dictionaryConfig.containsKey("splitScript")) splitScript = Boolean.parseBoolean(dictionaryConfig.get ("splitScript"));
			if (dictionaryConfig.containsKey("bounds")) {
				String boundsText = dictionaryConfig.get ("bounds");
				setBoundsFromText (boundsText);
			}
			
		} catch (IOException e) {
			printLog ("Load defaults ok");
			loadDefaults ();
		}
	}
	
	// --------------------------------------------------------------------------------------------
	public static void printLog (String message)
	{
		LOGGER.info(message);
	}
	
	// --------------------------------------------------------------------------------------------
	public static HashMap <String, String> getDictionaryFromStream (InputStream inputStream) throws IOException
	{
		HashMap <String, String> dictionary = new HashMap <> ();
		
		InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
		
		Scanner scanner = new Scanner (inputStreamReader);

		
		dictionary.clear();

		while (scanner.hasNextLine())
		{
			String data = scanner.nextLine ();

			int index = data.indexOf('=');
			
			if (index < data.length() - 1)
			{
				String key = data.substring(0, index);
				String value = data.substring(index + 1);

				dictionary.put (
						key.trim (), 
						value.trim ().replace("\\n", "\n")
						);
			}
		}

		scanner.close();
		inputStream.close();
		
		return dictionary;
	}

	// --------------------------------------------------------------------------------------------
	public String getAppPath ()
	{
		return getHome () + File.separatorChar + APP_DIRECTORY;
	}
	
	// --------------------------------------------------------------------------------------------
	public String getConfigPath ()
	{
		return getHome () + File.separatorChar + APP_DIRECTORY + File.separatorChar + CONFIG_FILE;
	}
	
	// --------------------------------------------------------------------------------------------
	public String getHome ()
	{
		// Get home from Linux
		String home = System.getenv ("HOME");
		
		if (home == null)
		{
			// Get home from Windows
			home = System.getenv("USERPROFILE");
		}
		
		if (home == null)
		{
			// Default home at system fails
			home = ".";
		}
		
		return home;
	}
	
	// --------------------------------------------------------------------------------------------
	public MetaDataDriver[] getMetaDataDrivers ()
	{
		return new MetaDataDriver[] {mariadbMetaData, derbyMetaData, oracleMetaData, mssqlMetaData};
	}
	
	// --------------------------------------------------------------------------------------------
	public MetaDataDriver getMariadbMetaData() {
		return mariadbMetaData;
	}

	public void setMariadbMetaData(MetaDataDriver mariadbMetaData) {
		this.mariadbMetaData = mariadbMetaData;
	}

	public MetaDataDriver getDerbyMetaData() {
		return derbyMetaData;
	}

	public void setDerbyMetaData(MetaDataDriver derbyMetaData) {
		this.derbyMetaData = derbyMetaData;
	}

	public MetaDataDriver getOracleMetaData() {
		return oracleMetaData;
	}

	public void setOracleMetaData(MetaDataDriver oracleMetaData) {
		this.oracleMetaData = oracleMetaData;
	}

	public boolean isShowStackTraceExceptions() {
		return showStackTraceExceptions;
	}

	public void setShowStackTraceExceptions(boolean showStackTraceExceptions) {
		this.showStackTraceExceptions = showStackTraceExceptions;
	}

	public MetaDataDriver getMssqlMetaData() {
		return mssqlMetaData;
	}

	public void setMssqlMetaData(MetaDataDriver mssqlMetaData) {
		this.mssqlMetaData = mssqlMetaData;
	}

	public String getPreferredEngine() {
		return preferredEngine;
	}

	public void setPreferredEngine(String preferredEngine) {
		this.preferredEngine = preferredEngine;
	}

	public boolean isSaveWorkSpace() {
		return saveWorkSpace;
	}

	public void setSaveWorkSpace(boolean saveWorkSpace) {
		this.saveWorkSpace = saveWorkSpace;
	}

	public String getSqlStatement() {
		return sqlStatement;
	}

	public void setSqlStatement(String sqlStatement) {
		this.sqlStatement = sqlStatement;
	}

	public String getSqlResults() {
		return sqlResults;
	}

	public void setSqlResults(String sqlResults) {
		this.sqlResults = sqlResults;
	}

	public String getScriptPath() {
		return scriptPath;
	}

	public void setScriptPath(String scriptPath) {
		this.scriptPath = scriptPath;
	}

	public boolean isSplitScript() {
		return splitScript;
	}

	public void setSplitScript(boolean splitScript) {
		this.splitScript = splitScript;
	}

	public Rectangle getBounds() {
		return bounds;
	}

	public void setBounds(Rectangle bounds) {
		this.bounds = bounds;
	}
}
