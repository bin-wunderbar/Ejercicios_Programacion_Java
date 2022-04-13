package dataaccess;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

// ------------------------------------------------------------------------------------------------
public class DBManager 
{
	// --------------------------------------------------------------------------------------------
	public DBManager () throws SQLException
	{
		registerDrivers ();
	}
	
	// --------------------------------------------------------------------------------------------
	protected void registerDrivers () throws SQLException
	{
		DriverManager.registerDriver (new org.mariadb.jdbc.Driver());
		DriverManager.registerDriver (new org.apache.derby.jdbc.EmbeddedDriver());
		DriverManager.registerDriver (new oracle.jdbc.driver.OracleDriver());
		DriverManager.registerDriver (new com.microsoft.sqlserver.jdbc.SQLServerDriver());
	}
	
	// --------------------------------------------------------------------------------------------
	public String executeSql (String connectionString, String sql) throws SQLException
	{
		return executeSql (connectionString, sql, false);
	}
	
	// --------------------------------------------------------------------------------------------
	public String executeSql (String connectionString, String sql, boolean splitStatements) throws SQLException 
	{
		String resultSetString;
		
		Connection connection = DriverManager.getConnection (connectionString);
		
		Statement statement = connection.createStatement();
		
		if (splitStatements)
		{
			for (String simpleStatement : sql.split(";"))
			{
				if (!simpleStatement.isBlank())
				{
					AppConfig.printLog (simpleStatement);
					statement.execute(simpleStatement);
				}
			}
		}
		else
		{
			statement.execute (sql);
		}
		
		
		resultSetString = getResultSetString (statement.getResultSet());
		
		statement.close();
		connection.close ();
		
		return resultSetString; 
	}
	
	// --------------------------------------------------------------------------------------------
	protected String getResultSetString (ResultSet resultSet) throws SQLException
	{
		String result = "";
		
		if (resultSet != null)
		{
			ResultSetMetaData metaData = resultSet.getMetaData();
			
			int count = metaData.getColumnCount();
			
			if (count > 0)
			{
				String[] columnNames = new String[count];
				
				for (int i = 0; i < count; i++)
				{
					columnNames[i] = metaData.getColumnName (i + 1);
					result += columnNames[i];
					if (i < count - 1) result += ";";
				}
				result += "\n";
				
				while (resultSet.next())
				{
					for (int i = 0; i < count; i++)
					{
						result += resultSet.getObject (columnNames[i]);
						if (i < count - 1) result += ";"; 
					}
					result += "\n";
				}
			}
		}
		
		return result;
	}
}
