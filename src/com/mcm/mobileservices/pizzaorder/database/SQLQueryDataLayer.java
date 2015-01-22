/**
 * 
 */
package com.mcm.mobileservices.pizzaorder.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * This class contains the method actually fetching data from the database using
 * "Select" queries. It only has all read queries and contains several different
 * methods for performing various operations. Ultimately results from this class
 * are returned back to web service layer.
 * 
 * @author Shrikant Havale
 * 
 */
public class SQLQueryDataLayer {

	/**
	 * Method actually hitting database, and fetching user name using telephone
	 * number.
	 * 
	 * @param telephoneNumber
	 *            - 10 digit telephone number
	 * @return user name as string
	 * @throws Exception
	 *             - User not found is one of the exception, and also other
	 *             exceptions.
	 */
	public String getUserNameFromTelephoneNumber(String telephoneNumber)
			throws Exception {

		// for storing user name
		String userName = "";

		try {

			// get SQL connection
			Connection sqlConnection = SQLConnectionDatabase.getConnection();

			// select query for selecting user name from database by passing
			// telephone number
			PreparedStatement preparedStatement = sqlConnection
					.prepareStatement("SELECT USERNAME FROM UserDetails WHERE TELEPHONENUMBER = ? ");

			// set the telephone number
			preparedStatement.setString(1, telephoneNumber);

			// execute query
			ResultSet resultSet = preparedStatement.executeQuery();

			// check for empty result set
			if (!resultSet.next()) {
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

	/**
	 * Method actually hitting database, and fetching not more than 5 active
	 * pizzas at a time.
	 * 
	 * @return string with comma separated and numbered pizzas
	 * @throws Exception
	 *             - for empty result set exception is thrown
	 * 
	 */
	public String read5ActivePizzas() throws Exception {

		String pizzaConcatenatedString = "";

		try {

			// get SQL connection
			Connection sqlConnection = SQLConnectionDatabase.getConnection();

			// select query for selecting 5 active pizzas from database
			PreparedStatement preparedStatement = sqlConnection
					.prepareStatement("SELECT PIZZANAME FROM PizzaDetails WHERE PIZZAACTIVE = 'Y' ");

			// execute query
			ResultSet resultSet = preparedStatement.executeQuery();

			// check for empty result set
			if (!resultSet.next()) {
				throw new Exception("No Active Pizzas in Database");
			}

			// move the cursor before the result set
			resultSet.beforeFirst();

			// pizza counter
			int counter = 1;

			// iterate through result set and get data
			while (resultSet.next()) {

				String pizzaName = resultSet.getString("PIZZANAME");

				pizzaConcatenatedString = pizzaConcatenatedString + counter
						+ ". " + pizzaName + ", ";

				counter = counter + 1;

			}

			pizzaConcatenatedString = pizzaConcatenatedString.substring(0,
					pizzaConcatenatedString.length() - 2);
			pizzaConcatenatedString = pizzaConcatenatedString + ".";

			// close the connection
			sqlConnection.close();

		} catch (Exception exception) {
			// print stack trace
			exception.printStackTrace();

			// re-throw exception
			throw exception;
		}

		// return concatenated string
		return pizzaConcatenatedString;
	}

	/**
	 * method returns the pizza name from database. When user selects pizza
	 * number using IVR application, and sends dummy ids (1, 2, 3, 4 , 5) and
	 * along with session id, these details are checked in database and actual
	 * ID of pizza selected is found and its name is sent back.
	 * 
	 * @param sessionID
	 * @param dummyPizzaNumber
	 * 
	 * @param dummyPizzaNumber
	 *            - dummy pizza numbers are from 1-5, different than their
	 *            actual IDs
	 * @param session
	 *            id - unique for each caller, when first call is made
	 *            automatically a new entry with this session id is created in
	 *            table.
	 * 
	 * @return pizza name
	 * @throws Exception
	 *             - for empty result set exception is thrown
	 * 
	 */
	public String readpizzanameselectedbyuser(String dummyPizzaNumber,
			String sessionID) {
		System.out.println("******** dummy pizza number " + dummyPizzaNumber);
		System.out.println("******** session id " + sessionID);
		return "MARGHERITA";
	}

}
