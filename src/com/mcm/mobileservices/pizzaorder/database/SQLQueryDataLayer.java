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

import com.mcm.mobileservices.pizzaorder.entities.UserDetails;

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
	public String readUserNameFromTelephoneNumber(String telephoneNumber)
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
	 * Method actually hitting database, and fetching user address using
	 * telephone number.
	 * 
	 * @param telephoneNumber
	 *            - 10 digit telephone number
	 * @return user address as string
	 * @throws Exception
	 *             - Address not found is one of the exception, and also other
	 *             exceptions.
	 */
	public String readAddressUsingTelephoneNumber(String telephoneNumber)
			throws Exception {

		// for storing user address
		String userAddress = "";

		try {

			// get SQL connection
			Connection sqlConnection = SQLConnectionDatabase.getConnection();

			// select query for selecting user address from database by passing
			// telephone number
			PreparedStatement preparedStatement = sqlConnection
					.prepareStatement("SELECT USERADDRESS FROM UserDetails WHERE TELEPHONENUMBER = ? ");

			// set the telephone number
			preparedStatement.setString(1, telephoneNumber);

			// execute query
			ResultSet resultSet = preparedStatement.executeQuery();

			// check for empty result set
			if (!resultSet.next()) {
				throw new Exception("User Address Not Found");
			}

			// move the cursor before the result set
			resultSet.beforeFirst();

			// iterate through result set and get data
			while (resultSet.next()) {
				userAddress = resultSet.getString("USERADDRESS");
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
		return userAddress;
	}

	/**
	 * This method checks if user is registered, this is called normally user
	 * speaks with operator and registers himself. Once he is registered in
	 * database, its session id can be mapped to his telephone number
	 * 
	 * @param telephoneNumber
	 *            telephone number
	 * @param sessionID
	 *            call id given by VOXEO, remains fix through out call.
	 * @return user name for telephone number
	 * @throws Exception
	 *             User not found exception
	 */
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
	public String readPizzaNameSelectedbyUser(String dummyPizzaNumber,
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

	/**
	 * method returns the pizza details description and its content as
	 * concatenated string from database. When user selects pizza number using
	 * IVR application, and sends dummy ids (1, 2, 3, 4 , 5) and along with
	 * session id, these details are checked in database and actual ID of pizza
	 * selected is found and its details are sent back.
	 * 
	 * @param dummyPizzaNumber
	 *            - dummy pizza numbers are from 1-5, different than their
	 *            actual IDs
	 * @param session
	 *            id - unique for each caller, when first call is made
	 *            automatically a new entry with this session id is created in
	 *            table.
	 * 
	 * @return pizza details concatenated string
	 * @throws Exception
	 *             - for empty result set exception is thrown
	 * 
	 */
	public String readPizzaDetailsSelectedbyUser(String dummyPizzaNumber,
			String sessionID) throws Exception {

		// pizza details to be returned
		String pizzaDetails = "";

		try {

			// get SQL connection
			Connection sqlConnection = SQLConnectionDatabase.getConnection();

			// select query for getting pizza details based on session details
			// and dummypizzaid sent by user
			PreparedStatement preparedStatement = sqlConnection
					.prepareStatement("SELECT PIZZADESCRIPTION, PIZZACONTENT FROM PizzaDetails WHERE PIZZAID IN ( "
							+ " SELECT PIZZAID FROM PizzaTransactions WHERE TRANSACTIONID = '"
							+ sessionID
							+ "' AND pizzadummyid = "
							+ dummyPizzaNumber + ")");

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
				pizzaDetails = resultSet.getString("PIZZADESCRIPTION");
				pizzaDetails = pizzaDetails + " And the contents are, "
						+ resultSet.getString("PIZZACONTENT");

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
		return pizzaDetails;
	}

	/**
	 * When the call is made first time, user session is created and stored in
	 * database,These session details are updated as call progress with pizza
	 * being added or removed
	 * 
	 * @param sessionID
	 *            - callid from voxeo
	 * @param telephoneNumber
	 *            - telephone number of user
	 * @throws Exception
	 *             - exception if session details cannot be stored
	 */
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

	/**
	 * Once the user is registered, only update his telephone number in session
	 * details.
	 * 
	 * @param telephoneNumber
	 * @param sessionID
	 * @throws Exception
	 */
	public void updateSessionDetailsTelephoneNumber(String telephoneNumber,
			String sessionID) throws Exception {

		try {

			// get SQL connection
			Connection sqlConnection = SQLConnectionDatabase.getConnection();

			// create statement
			Statement statement = sqlConnection
					.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
							ResultSet.CONCUR_UPDATABLE);

			// update query
			String updateQuery = "UPDATE PizzaTransactions SET TELEPHONENUMBER = '"
					+ telephoneNumber
					+ "' WHERE transactionid = '"
					+ sessionID
					+ "'";

			// set auto commit false
			sqlConnection.setAutoCommit(false);

			// add queries
			statement.addBatch(updateQuery);

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

	/**
	 * Update session details with specific pizza names and dummy numbers id for
	 * the particular customer and session. These later will help in getting
	 * details only from this table.
	 * 
	 * @param tempHashMap
	 *            - Hash map containing actual pizza id of 5 pizzas sent to
	 *            user, their dummy ids and and their names
	 * @param sessionID
	 *            - callid given by Voxeo
	 * @throws Exception
	 *             - exception if any generated during updating session details.
	 */
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

				// udpate queries
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

	/**
	 * Adds the pizza to the basket, in transaction details table across the
	 * session and user who is currently making a call. Number of pizzas and
	 * their sizes are added in the transaction table across the users session.
	 * 
	 * @param dummyPizzaNumber
	 *            - temporary pizza number from 1-5 as selected by user in VOXEO
	 * @param sessionID
	 *            - call id from VOXOE
	 * @param size
	 *            - size can take 3 values , small, medium and large, will be
	 *            added accordingly in database.
	 * @param numberOfPizzas
	 *            - number of pizza of that particular dummy number and that
	 *            particular size for that particular session
	 * 
	 * @return success message or exception is thrown if anything goes wrong,
	 *         which is accordingly handled in VOXEO
	 * @throws Exception
	 *             if something goes wrong
	 */
	public String addPizzaInUsersBasket(String dummyPizzaNumber,
			String sessionID, String size, String numberOfPizzas)
			throws Exception {

		try {

			// get SQL connection
			Connection sqlConnection = SQLConnectionDatabase.getConnection();

			// size column to be updated
			String sizeColumn = "";

			// create statement
			Statement statement = sqlConnection
					.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
							ResultSet.CONCUR_UPDATABLE);

			// decide which column to update
			if (size != null) {

				if (("small").equals(size)) {
					sizeColumn = "SMALLSIZE";
				} else if (("medium").equals(size)) {
					sizeColumn = "MEDIUMSIZE";
				} else if (("large").equals(size)) {
					sizeColumn = "LARGESIZE";
				}

			}

			// select query for selecting user name from database by passing
			// telephone number
			PreparedStatement preparedStatement = sqlConnection
					.prepareStatement("SELECT " + sizeColumn
							+ " FROM PizzaTransactions  "
							+ " WHERE transactionid = '" + sessionID
							+ "' AND pizzadummyid = " + dummyPizzaNumber);

			// execute query
			ResultSet resultSet = preparedStatement.executeQuery();

			// previous pizzas
			int previousPizzaNumber = 0;

			// iterate through result set and get data
			while (resultSet.next()) {
				previousPizzaNumber = resultSet.getInt(sizeColumn);
			}

			numberOfPizzas = (Integer.parseInt(numberOfPizzas) + previousPizzaNumber)
					+ "";

			// set auto commit false
			sqlConnection.setAutoCommit(false);

			// update queries
			String updateQuery = "UPDATE PizzaTransactions SET " + sizeColumn
					+ " = " + numberOfPizzas + "  WHERE transactionid = '"
					+ sessionID + "' AND pizzadummyid = " + dummyPizzaNumber;

			// add queries
			statement.addBatch(updateQuery);

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

		return "success";

	}

	/**
	 * Method actual connects to transaction table, and gets the complete final
	 * order of , so it can be repeated to user using VOXEO services
	 * 
	 * @param sessionID
	 *            - users current call session id
	 * 
	 * @return complete concatenated string which can directly used in VOXEO for
	 *         complete order of user
	 * @throws Exception
	 *             something goes wrong exception is thrown to VOXEO where it is
	 *             handled appropriately
	 */
	public String readFinalCompleteOrder(String sessionID) throws Exception {

		// pizza name to be returned
		String completeFinalOrder = "";

		try {

			// get SQL connection
			Connection sqlConnection = SQLConnectionDatabase.getConnection();

			// select query for getting pizza name based on session details and
			// dummypizzaid sent by user
			PreparedStatement preparedStatement = sqlConnection
					.prepareStatement("SELECT PIZZANAME, SMALLSIZE, MEDIUMSIZE, LARGESIZE FROM PizzaTransactions WHERE transactionid = '"
							+ sessionID + "'");

			// execute query
			ResultSet resultSet = preparedStatement.executeQuery();

			// check for empty result set
			if (!resultSet.next()) {
				throw new Exception(
						"Pizza Name not applicable for this session");
			}

			// move the cursor before the result set
			resultSet.beforeFirst();

			// counter
			int counter = 1;

			// iterate through result set and get data
			while (resultSet.next()) {
				String pizzaName = resultSet.getString("PIZZANAME");
				Integer smallSizePizzaNumbers = resultSet.getInt("SMALLSIZE");
				Integer mediumSizePizzaNumbers = resultSet.getInt("MEDIUMSIZE");
				Integer largeSizePizzaNumbers = resultSet.getInt("LARGESIZE");

				// check if no pizzas of this type were ordered
				if (smallSizePizzaNumbers == 0 && mediumSizePizzaNumbers == 0
						&& largeSizePizzaNumbers == 0) {
					continue;
				}

				completeFinalOrder = completeFinalOrder + counter + ". "
						+ pizzaName + " - ";

				// check small
				if (smallSizePizzaNumbers != 0) {
					completeFinalOrder = completeFinalOrder
							+ smallSizePizzaNumbers + " SMALL, ";
				}

				// check medium
				if (mediumSizePizzaNumbers != 0) {
					completeFinalOrder = completeFinalOrder
							+ mediumSizePizzaNumbers + " MEDIUM, ";

				}

				// check large
				if (largeSizePizzaNumbers != 0) {
					completeFinalOrder = completeFinalOrder
							+ largeSizePizzaNumbers + " LARGE, ";

				}

				// concatenate everything
				completeFinalOrder = completeFinalOrder + "\n";

				counter = counter + 1;

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
		return completeFinalOrder;
	}

	/**
	 * Web service removes all the pizzas from the order for the current user
	 * and current session. Allowing users to start fresh order in same call and
	 * adding new pizzas.
	 * 
	 * @param session
	 *            id - unique for each caller, when first call is made
	 *            automatically a new entry with this session id is created in
	 *            table.
	 * 
	 * @return success string if the basket was reseted successfully.
	 * @throws Exception
	 *             - for empty result set exception is thrown
	 * 
	 */
	public String removeAllPizzaFromBasket(String sessionID) throws Exception {

		try {

			// get SQL connection
			Connection sqlConnection = SQLConnectionDatabase.getConnection();

			// create statement
			Statement statement = sqlConnection
					.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
							ResultSet.CONCUR_UPDATABLE);

			// set auto commit false
			sqlConnection.setAutoCommit(false);

			// update query
			String updateQuery = "UPDATE PizzaTransactions SET SMALLSIZE = NULL, MEDIUMSIZE = NULL, LARGESIZE = NULL WHERE transactionid = '"
					+ sessionID + "'";

			// add queries
			statement.addBatch(updateQuery);

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

		return "success";

	}

	/**
	 * Method actually hitting database and saving user details in database.
	 * 
	 * @param userDetailsObject
	 *            - Object sent from UI, originally in JSON converted to entity
	 *            format
	 * @return the same object if saving is succeeded
	 */
	public UserDetails saveUserDetails(UserDetails userDetailsObject) {

		try {

			// get SQL connection
			Connection sqlConnection = SQLConnectionDatabase.getConnection();

			// insert queries
			String insertQuery = "INSERT INTO UserDetails (username,telephonenumber,useraddress) VALUES ( '"
					+ userDetailsObject.getUserName()
					+ "' , '"
					+ userDetailsObject.getTelephoneNumber()
					+ "' ,  '"
					+ userDetailsObject.getUserAddress() + "' )";

			// create statement
			Statement statement = sqlConnection
					.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
							ResultSet.CONCUR_UPDATABLE);

			// set auto commit false
			sqlConnection.setAutoCommit(false);

			// add queries
			statement.addBatch(insertQuery);

			// execute batch
			statement.executeBatch();

			// commit
			sqlConnection.commit();

			// close the connection
			sqlConnection.close();

			// user successfully registered
			userDetailsObject.setMessage("User Successfully Registered");

		} catch (Exception exception) {

			// set error message
			userDetailsObject.setMessage(exception.getMessage());

			// print stack trace
			exception.printStackTrace();

		}

		return userDetailsObject;

	}

	/**
	 * Method actually hitting database and searching user details in database.
	 * 
	 * @param telephoneNumber
	 *            - string telephone number sent from UI, to check if user is in
	 *            database details and fetch its details
	 * @return the same object if searching is succeeded
	 */
	public UserDetails searchUserDetails(String telephoneNumber) {

		// for returning userdetails
		UserDetails userDetails = new UserDetails();
		userDetails.setTelephoneNumber(telephoneNumber);
		userDetails.setUserName("");
		userDetails.setUserAddress("");

		try {

			// get SQL connection
			Connection sqlConnection = SQLConnectionDatabase.getConnection();

			// select query for selecting user name from database by passing
			// telephone number
			PreparedStatement preparedStatement = sqlConnection
					.prepareStatement("SELECT USERNAME,TELEPHONENUMBER,USERADDRESS FROM UserDetails WHERE TELEPHONENUMBER = ? ");

			// set the telephone number
			preparedStatement.setString(1, telephoneNumber);

			// execute query
			ResultSet resultSet = preparedStatement.executeQuery();

			// check for empty result set
			if (!resultSet.next()) {
				userDetails.setMessage("User is not registered.");
				return userDetails;
			}

			// move the cursor before the result set
			resultSet.beforeFirst();

			// iterate through result set and get data
			while (resultSet.next()) {
				userDetails.setUserName(resultSet.getString("USERNAME"));
				userDetails.setUserAddress(resultSet.getString("USERADDRESS"));
			}

			// close the connection
			sqlConnection.close();

			// user found
			userDetails.setMessage("User Details Found.");

		} catch (Exception exception) {
			// print stack trace
			exception.printStackTrace();

			// set error message
			userDetails.setMessage(exception.getMessage());
		}

		// return user details
		return userDetails;
	}

	public UserDetails updateUserDetails(UserDetails userDetailsObject) {

		try {

			// get SQL connection
			Connection sqlConnection = SQLConnectionDatabase.getConnection();

			// select query for selecting user name from database by passing
			// telephone number
			PreparedStatement preparedStatement = sqlConnection
					.prepareStatement("SELECT USERNAME,TELEPHONENUMBER,USERADDRESS FROM UserDetails WHERE TELEPHONENUMBER = ? ");

			// set the telephone number
			preparedStatement.setString(1,
					userDetailsObject.getTelephoneNumber());

			// execute query
			ResultSet resultSet = preparedStatement.executeQuery();

			// check for empty result set
			if (!resultSet.next()) {
				userDetailsObject
						.setMessage("User to be updated is not registered.");
				return userDetailsObject;
			}

			// insert queries
			String updatQuery = "UPDATE UserDetails set username = '"
					+ userDetailsObject.getUserName() + "' , useraddress = '"
					+ userDetailsObject.getUserAddress()
					+ "' WHERE telephonenumber = '"
					+ userDetailsObject.getTelephoneNumber() + "'";

			// create statement
			Statement statement = sqlConnection
					.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
							ResultSet.CONCUR_UPDATABLE);

			// set auto commit false
			sqlConnection.setAutoCommit(false);

			// add queries
			statement.addBatch(updatQuery);

			// execute batch
			statement.executeBatch();

			// commit
			sqlConnection.commit();

			// close the connection
			sqlConnection.close();

			// user found
			userDetailsObject.setMessage("User Details Successfully Updated.");

		} catch (Exception exception) {
			// print stack trace
			exception.printStackTrace();

			// set error message
			userDetailsObject.setMessage(exception.getMessage());
		}

		// return user details
		return userDetailsObject;
	}
}
