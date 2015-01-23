/**
 * 
 */
package com.mcm.mobileservices.pizzaorder.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

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

	public String checkUserRegistered(String telephoneNumber, String sessionID)
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

			// user is registered after he used a call to setup and is found now
			// update the session details with user found.
			updateSessionDetailsTelephoneNumber(telephoneNumber, sessionID);

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
	 * @param sessionID
	 *            sessionID to update session details
	 * 
	 * @return string with comma separated and numbered pizzas
	 * @throws Exception
	 *             - for empty result set exception is thrown
	 * 
	 */
	public String read5ActivePizzas(String sessionID) throws Exception {

		// concatenated string to be returned
		String pizzaConcatenatedString = "";

		// pizza hash map
		Map<Integer, String> tempHashMap = new HashMap<Integer, String>();

		try {

			// get SQL connection
			Connection sqlConnection = SQLConnectionDatabase.getConnection();

			// select query for selecting 5 active pizzas from database
			PreparedStatement preparedStatement = sqlConnection
					.prepareStatement("SELECT PIZZAID , PIZZANAME FROM PizzaDetails WHERE PIZZAACTIVE = 'Y' ");

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
				int pizzaId = resultSet.getInt("PIZZAID");
				tempHashMap.put(pizzaId, counter + ":" + pizzaName);

				pizzaConcatenatedString = pizzaConcatenatedString + counter
						+ ". " + pizzaName + ", ";

				counter = counter + 1;

			}

			pizzaConcatenatedString = pizzaConcatenatedString.substring(0,
					pizzaConcatenatedString.length() - 2);
			pizzaConcatenatedString = pizzaConcatenatedString + ".";

			// close the connection
			sqlConnection.close();

			// update the session details with pizza names found.
			updateSessionDetailsPizzaNames(tempHashMap, sessionID);

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
			String sessionID) throws Exception {

		// pizza name to be returned
		String pizzaName = "";

		try {

			// get SQL connection
			Connection sqlConnection = SQLConnectionDatabase.getConnection();

			// select query for getting pizza name based on session details and
			// dummypizzaid sent by user
			PreparedStatement preparedStatement = sqlConnection
					.prepareStatement("SELECT PIZZANAME FROM PizzaTransactions WHERE transactionid = '"
							+ sessionID
							+ "' AND pizzadummyid = "
							+ dummyPizzaNumber);

			// execute query
			ResultSet resultSet = preparedStatement.executeQuery();

			// check for empty result set
			if (!resultSet.next()) {
				throw new Exception(
						"Pizza Name not applicable for this session");
			}

			// move the cursor before the result set
			resultSet.beforeFirst();

			// iterate through result set and get data
			while (resultSet.next()) {
				pizzaName = resultSet.getString("PIZZANAME");

			}

			// close the connection
			sqlConnection.close();

		} catch (Exception exception) {
			// print stack trace
			exception.printStackTrace();

			// re-throw exception
			throw exception;
		}

		// return concatenated string
		return pizzaName;
	}

	public void createSessionDetails(String sessionID, String telephoneNumber)
			throws Exception {

		try {

			// get SQL connection
			Connection sqlConnection = SQLConnectionDatabase.getConnection();

			// insert queries
			String insertQuery1 = "INSERT INTO PizzaTransactions (transactionid,telephonenumber,pizzadummyid) VALUES ( '"
					+ sessionID + "' , '" + telephoneNumber + "' , 1 )";
			String insertQuery2 = "INSERT INTO PizzaTransactions (transactionid,telephonenumber,pizzadummyid) VALUES ( '"
					+ sessionID + "' , '" + telephoneNumber + "' , 2 )";
			String insertQuery3 = "INSERT INTO PizzaTransactions (transactionid,telephonenumber,pizzadummyid) VALUES ( '"
					+ sessionID + "' , '" + telephoneNumber + "' , 3 )";
			String insertQuery4 = "INSERT INTO PizzaTransactions (transactionid,telephonenumber,pizzadummyid) VALUES ( '"
					+ sessionID + "' , '" + telephoneNumber + "' , 4 )";
			String insertQuery5 = "INSERT INTO PizzaTransactions (transactionid,telephonenumber,pizzadummyid) VALUES ( '"
					+ sessionID + "' , '" + telephoneNumber + "' , 5 )";

			// create statement
			Statement statement = sqlConnection
					.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
							ResultSet.CONCUR_UPDATABLE);

			// set auto commit false
			sqlConnection.setAutoCommit(false);

			// add queries
			statement.addBatch(insertQuery1);
			statement.addBatch(insertQuery2);
			statement.addBatch(insertQuery3);
			statement.addBatch(insertQuery4);
			statement.addBatch(insertQuery5);

			// execute batch
			statement.executeBatch();

			// commit
			sqlConnection.commit();

			// close the connection
			sqlConnection.close();

		} catch (Exception exception) {
			// print stack trace
			exception.printStackTrace();

			// re-throw exception
			throw exception;
		}

	}

	public void updateSessionDetailsTelephoneNumber(String telephoneNumber,
			String sessionID) throws Exception {

		try {

			// get SQL connection
			Connection sqlConnection = SQLConnectionDatabase.getConnection();

			// create statement
			Statement statement = sqlConnection
					.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
							ResultSet.CONCUR_UPDATABLE);

			// insert queries
			String updateQuery = "UPDATE PizzaTransactions SET TELEPHONENUMBER = '"
					+ telephoneNumber + "' WHERE transactionid = '" + sessionID;

			// execute query
			statement.executeQuery(updateQuery);

			// close the connection
			sqlConnection.close();

		} catch (Exception exception) {
			// print stack trace
			exception.printStackTrace();

			// re-throw exception
			throw exception;
		}

	}

	public void updateSessionDetailsPizzaNames(
			Map<Integer, String> tempHashMap, String sessionID)
			throws Exception {

		try {

			// get SQL connection
			Connection sqlConnection = SQLConnectionDatabase.getConnection();

			// create statement
			Statement statement = sqlConnection
					.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
							ResultSet.CONCUR_UPDATABLE);

			// set auto commit false
			sqlConnection.setAutoCommit(false);

			// iterate temp hash map
			for (Integer temp : tempHashMap.keySet()) {

				String pizzaName = tempHashMap.get(temp).split(":")[1];
				String pizzaDummyID = tempHashMap.get(temp).split(":")[0];

				// insert queries
				String updateQuery = "UPDATE PizzaTransactions SET PIZZANAME = '"
						+ pizzaName
						+ "', PIZZAID = "
						+ temp
						+ "  WHERE transactionid = '"
						+ sessionID
						+ "' AND pizzadummyid = " + pizzaDummyID;

				// add queries
				statement.addBatch(updateQuery);

			}

			// execute batch
			statement.executeBatch();

			// commit
			sqlConnection.commit();

			// close the connection
			sqlConnection.close();

		} catch (Exception exception) {
			// print stack trace
			exception.printStackTrace();

			// re-throw exception
			throw exception;
		}

	}

}
