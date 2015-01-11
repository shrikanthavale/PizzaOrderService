/**
 * 
 */
package com.mcm.mobileservices.pizzaorder.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


/**
 * @author Shrikant Havale
 * 
 */
public class SQLQueryFetchData {

	public String getUserNameFromTelephoneNumber(String telephoneNumber) throws Exception {

		String userName = "";
		
		try {
			Connection sqlConnection = SQLConnectionDatabase.getConnection();
			PreparedStatement preparedStatement = sqlConnection
					.prepareStatement("SELECT USERNAME FROM UserDetails WHERE TELEPHONENUMBER = ? ");
			preparedStatement.setString(1,telephoneNumber);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (!resultSet.next() ) {
				throw new Exception("User Not Found");
			}
			resultSet.beforeFirst();
			while (resultSet.next()) {
				userName = resultSet.getString("USERNAME");
			}
			sqlConnection.close();
		} catch (Exception exception) {
			exception.printStackTrace();
			throw exception;
		}
		return userName;
	}

}
