package dataaccess;

// ------------------------------------------------------------------------------------------------
public class MetaDataDriver 
{
	private String name;
	private String connectionString;
	
	// --------------------------------------------------------------------------------------------
	public MetaDataDriver(String description, String connection) 
	{
		this.name 	= description;
		this.connectionString 	= connection;
	}
	
	// --------------------------------------------------------------------------------------------
	@Override
	public String toString ()
	{
		return name;
	}

	// --------------------------------------------------------------------------------------------
	/**
	 * @return the description
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param description the description to set
	 */
	public void setName (String description) {
		this.name = description;
	}

	/**
	 * @return the connection
	 */
	public String getConnectionString() {
		return connectionString;
	}

	/**
	 * @param connectionString the connection to set
	 */
	public void setConnectionString(String connectionString) {
		this.connectionString = connectionString;
	}

}
