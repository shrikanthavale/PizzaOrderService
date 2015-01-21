/**
 * 
 */
package com.mcm.mobileservices.pizzaorder.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


/**
 * This class contains the method actually fetching data from the database using "Select" queries.
 * It only has all read queries and contains several different methods for performing various operations. 
 * Ultimately results from this class are returned back to web service layer.
 * 
 * @author Shrikant Havale
 * 
 */
public class SQLQueryFetchData {

	/**
	 * Method actually hitting database, and fetching user name using telephone number. 
	 * 
	 * @param telephoneNumber - 10 digit telephone number
	 * @return user name as string
	 * @throws Exception - User not found is one of the exception, and also other exceptions.
	 */
	public String getUserNameFromTelephoneNumber(String telephoneNumber) throws Exception {

		// for storing user name
		String userName = "";
		
		try {
			
			// get SQL connection
			Connection sqlConnection = SQLConnectionDatabase.getConnection();
			
			// select query for selecting user name from database by passing telephone number
			PreparedStatement preparedStatement = sqlConnection
					.prepareStatement("SELECT USERNAME FROM UserDetails WHERE TELEPHONENUMBER = ? ");
			
			// set the telephone number
			preparedStatement.setString(1,telephoneNumber);
			
			// execute query
			ResultSet resultSet = preparedStatement.executeQuery();
			
			// check for empty result set
			if (!resultSet.next() ) {
				throw new Exception("User Not Found");
			}
			
			// move the cursor before the result set
			resultSet.beforeFirst();
			
			// iterate through result set and get data
			while (resultSet.next()) {
				userName = resultSet.getString("USERNAME");
			}
			
			// close the connection
			sqlConnection.close();
			
		} catch (Exception exception) {
			// print stack trace
			exception.printStackTrace();
			
			// re-throw exception
			throw exception;
		}
		
		// return user name
		return userName;
	}

}
