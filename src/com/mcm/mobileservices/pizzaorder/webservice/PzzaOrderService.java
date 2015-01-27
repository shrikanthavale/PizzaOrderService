/**
 * 
 */
package com.mcm.mobileservices.pizzaorder.webservice;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;
import com.mcm.mobileservices.pizzaorder.database.SQLQueryDataLayer;
import com.mcm.mobileservices.pizzaorder.entities.PizzaDetails;
import com.mcm.mobileservices.pizzaorder.entities.UserDetails;

/**
 * @author Shrikant Havale
 * 
 */

@Path("/pizzaorder")
public class PzzaOrderService {

	/**
	 * Fetch data layer - containing sql queries
	 */
	private SQLQueryDataLayer sqlQueryDataLayer = new SQLQueryDataLayer();

	/**
	 * Web service accepts the telephone number and retrieves user name from
	 * database.
	 * 
	 * @param telephoneNumber
	 *            - 10 digit telephone number
	 * @param sessionID
	 *            - session ID created by VOXEO IVR system, this session id will
	 *            be stored in dummy database to keep track of users progress
	 *            throughout call.
	 * @return user name as string
	 * @throws Exception
	 *             - User not found exception is thrown if telephone number is
	 *             not present in database.
	 * 
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/readusername")
	public String readUserNameUsingTelephoneNumber(
			@QueryParam("telephonenumber") String telephoneNumber,
			@QueryParam("sessionid") String sessionID) throws Exception {

		// this is first call to database, make sure session is established and
		// stored in database
		sqlQueryDataLayer.createSessionDetails(sessionID, telephoneNumber);

		// get the user name from telephone number
		String username = sqlQueryDataLayer
				.readUserNameFromTelephoneNumber(telephoneNumber);

		// return the user name
		return username;

	}

	/**
	 * Web service accepts the telephone number and retrieves user name from
	 * database.
	 * 
	 * @param telephoneNumber
	 *            - 10 digit telephone number
	 * @param sessionID
	 *            - session ID created by VOXEO IVR system, this session id will
	 *            be stored in dummy database to keep track of users progress
	 *            throughout call.
	 * @return user name as string
	 * @throws Exception
	 *             - User not found exception is thrown if telephone number is
	 *             not present in database.
	 * 
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/readuseraddress")
	public String readAddressUsingTelephoneNumber(
			@QueryParam("telephonenumber") String telephoneNumber)
			throws Exception {

		// get the user address from telephone number
		String username = sqlQueryDataLayer
				.readAddressUsingTelephoneNumber(telephoneNumber);

		// return the user address
		return username;

	}

	/**
	 * Web service accepts the telephone number and retrieves user name from
	 * database.
	 * 
	 * @param telephoneNumber
	 *            - 10 digit telephone number
	 * @param sessionID
	 *            - session ID created by VOXEO IVR system, this session id will
	 *            be stored in dummy database to keep track of users progress
	 *            throughout call.
	 * @return user name as string
	 * @throws Exception
	 *             - User not found exception is thrown if telephone number is
	 *             not present in database.
	 * 
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/checkuserregistered")
	public String checkUserRegistered(
			@QueryParam("telephonenumber") String telephoneNumber,
			@QueryParam("sessionid") String sessionID) throws Exception {

		// get the user name from telephone number
		String username = sqlQueryDataLayer.checkUserRegistered(
				telephoneNumber, sessionID);

		// return the user name
		return username;

	}

	/**
	 * Web service returns 5 active Pizzas from database, at a time not more
	 * than 5 Pizzas are active.
	 * 
	 * @return string with comma separated and numbered pizzas
	 * @throws Exception
	 *             - for empty result set exception is thrown
	 * 
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/readfiveactivepizza")
	public String read5ActivePizzas(@QueryParam("sessionid") String sessionID)
			throws Exception {

		// get not more than 5 active pizza concatenated string
		String pizzaConcatenatedCommaSeparated = sqlQueryDataLayer
				.read5ActivePizzas(sessionID);

		// return concatenated string of 5 pizzas
		return pizzaConcatenatedCommaSeparated;

	}

	/**
	 * Web service returns the pizza name from database. When user selects pizza
	 * number using IVR application, and sends dummy ids (1, 2, 3, 4 , 5) and
	 * along with session id, these details are checked in database and actual
	 * ID of pizza is selected and its name is sent back.
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
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/readpizzanameselectedbyuser")
	public String readPizzaNameSelectedbyUser(
			@QueryParam("pizzanumber") String dummyPizzaNumber,
			@QueryParam("sessionid") String sessionID) throws Exception {

		// get pizza name
		String pizzaName = sqlQueryDataLayer.readPizzaNameSelectedbyUser(
				dummyPizzaNumber, sessionID);

		// return pizza name of single pizza selected by user
		return pizzaName;

	}

	/**
	 * Web service returns the pizza details description and contents from
	 * database. When user selects pizza number using IVR application, and sends
	 * dummy ids (1, 2, 3, 4 , 5) and along with session id, these details are
	 * checked in database and actual ID of pizza is selected and its details
	 * are sent back so that they can be read for user.
	 * 
	 * @param dummyPizzaNumber
	 *            - dummy pizza numbers are from 1-5, different than their
	 *            actual IDs
	 * @param session
	 *            id - unique for each caller, when first call is made
	 *            automatically a new entry with this session id is created in
	 *            table.
	 * 
	 * @return selected pizza details, a big concatenated string
	 * @throws Exception
	 *             - for empty result set exception is thrown
	 * 
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/readpizzadetailsselectedbyuser")
	public String readPizzaDetailsSelectedbyUser(
			@QueryParam("pizzanumber") String dummyPizzaNumber,
			@QueryParam("sessionid") String sessionID) throws Exception {

		// get pizza name
		String pizzaName = sqlQueryDataLayer.readPizzaDetailsSelectedbyUser(
				dummyPizzaNumber, sessionID);

		// return pizza name of single pizza selected by user
		return pizzaName;

	}

	/**
	 * Web service returns the pizza details description and contents from
	 * database. When user selects pizza number using IVR application, and sends
	 * dummy ids (1, 2, 3, 4 , 5) and along with session id, these details are
	 * checked in database and actual ID of pizza is selected and its details
	 * are sent back so that they can be read for user.
	 * 
	 * @param dummyPizzaNumber
	 *            - dummy pizza numbers are from 1-5, different than their
	 *            actual IDs
	 * @param session
	 *            id - unique for each caller, when first call is made
	 *            automatically a new entry with this session id is created in
	 *            table.
	 * @param size
	 *            size of the pizza selected to be added in basket
	 * @param numberOfPizzas
	 *            number of pizzas of that particular size to be added
	 * 
	 * @return a string success or failure exception
	 * @throws Exception
	 *             - for empty result set exception is thrown
	 * 
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/writepizzaaddedinbasket")
	public String addPizzaInUsersBasket(
			@QueryParam("pizzanumber") String dummyPizzaNumber,
			@QueryParam("sessionid") String sessionID,
			@QueryParam("size") String size,
			@QueryParam("numberofpizzas") String numberOfPizzas)
			throws Exception {

		// Put in the
		String successString = sqlQueryDataLayer.addPizzaInUsersBasket(
				dummyPizzaNumber, sessionID, size, numberOfPizzas);

		// return success string if everything is ok, or an exception is thrown
		// automatically.
		return successString;

	}

	/**
	 * Web service returns the complete order for particular user and particular
	 * session, based on the VOXEO session id
	 * 
	 * @param session
	 *            id - unique for each caller, when first call is made
	 *            automatically a new entry with this session id is created in
	 *            table.
	 * 
	 * @return concatenated string of complete order for a particular user and
	 *         particular session.
	 * @throws Exception
	 *             - for empty result set exception is thrown
	 * 
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getfinalcompleteorder")
	public String readFinalCompleteOrder(
			@QueryParam("sessionid") String sessionID) throws Exception {

		// Put in the
		String completeOrderString = sqlQueryDataLayer
				.readFinalCompleteOrder(sessionID);

		// return success string if everything is ok, or an exception is thrown
		// automatically.
		return completeOrderString;

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
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/removeallpizzafrombasket")
	public String removeAllPizzaFromBasket(
			@QueryParam("sessionid") String sessionID) throws Exception {

		// removes all pizza from basket, allowing user to start fresh
		String successString = sqlQueryDataLayer
				.removeAllPizzaFromBasket(sessionID);

		// return success string if everything is ok, or an exception is thrown
		// automatically.
		return successString;

	}

	/**
	 * web service for saving user details in database.
	 * 
	 * @param userDetailsJsonString
	 *            user object specifically formatted in JSON format
	 * 
	 * @return returned JSON string of saved object
	 * 
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/saveuserdetails")
	public String saveUserDetails(
			@QueryParam("userdetails") String userDetailsJsonString) {

		// convert to object
		Gson gson = new Gson();
		UserDetails userDetailsObject = gson.fromJson(userDetailsJsonString,
				UserDetails.class);

		UserDetails userDetailsReturnedObject = sqlQueryDataLayer
				.saveUserDetails(userDetailsObject);

		// return success string if everything is ok, or an exception is thrown
		// automatically.
		return gson.toJson(userDetailsReturnedObject);

	}

	/**
	 * web service for searching user details in database.
	 * 
	 * @param telephoneNumber
	 *            user object specifically formatted in JSON format
	 * 
	 * @return returned JSON string of saved object
	 * 
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/searchuserdetails")
	public String searchUserDetails(
			@QueryParam("telephonenumber") String telephoneNumber) {

		// convert to object
		Gson gson = new Gson();

		UserDetails userDetailsReturnedObject = sqlQueryDataLayer
				.searchUserDetails(telephoneNumber);

		// return success string if everything is ok, or an exception is thrown
		// automatically.
		return gson.toJson(userDetailsReturnedObject);

	}

	/**
	 * web service for updating user details in database.
	 * 
	 * @param userDetailsJsonString
	 *            user object specifically formatted in JSON format
	 * 
	 * @return returned JSON string of updated object
	 * 
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/updateuserdetails")
	public String updateUserDetails(
			@QueryParam("userdetails") String userDetailsJsonString) {

		// convert to object
		Gson gson = new Gson();
		UserDetails userDetailsObject = gson.fromJson(userDetailsJsonString,
				UserDetails.class);

		UserDetails userDetailsReturnedObject = sqlQueryDataLayer
				.updateUserDetails(userDetailsObject);

		// return success string if everything is ok, or an exception is thrown
		// automatically.
		return gson.toJson(userDetailsReturnedObject);

	}

	/**
	 * web service for searching pizza details in database.
	 * 
	 * @param pizzaName
	 *            pizza name to be searched in database
	 * 
	 * @return returned JSON string of saved object
	 * 
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/searchpizzadetails")
	public String searchPizzaDetails(@QueryParam("pizzaname") String pizzaName) {

		// convert to object
		Gson gson = new Gson();

		PizzaDetails userDetailsReturnedObject = sqlQueryDataLayer
				.searchPizzaDetails(pizzaName);

		// return success string if everything is ok, or an exception is thrown
		// automatically.
		return gson.toJson(userDetailsReturnedObject);

	}

	/**
	 * web service for saving pizza details in database.
	 * 
	 * @param pizzaDetailsJsonString
	 *            pizza object specifically formatted in JSON format
	 * 
	 * @return returned JSON string of saved object
	 * 
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/savepizzadetails")
	public String savePizzaDetails(
			@QueryParam("pizzadetails") String pizzaDetailsJsonString) {

		// convert to object
		Gson gson = new Gson();
		PizzaDetails pizzaDetailsObject = gson.fromJson(pizzaDetailsJsonString,
				PizzaDetails.class);

		PizzaDetails pizzaDetailsReturnedObject = sqlQueryDataLayer
				.savePizzaDetails(pizzaDetailsObject);

		// return success string if everything is ok, or an exception is thrown
		// automatically.
		return gson.toJson(pizzaDetailsReturnedObject);

	}

	/**
	 * web service for saving pizza details in database.
	 * 
	 * @param pizzaDetailsJsonString
	 *            pizza object specifically formatted in JSON format
	 * 
	 * @return returned JSON string of saved object
	 * 
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/updatepizzadetails")
	public String updatePizzaDetails(
			@QueryParam("pizzadetails") String pizzaDetailsJsonString) {

		// convert to object
		Gson gson = new Gson();
		PizzaDetails pizzaDetailsObject = gson.fromJson(pizzaDetailsJsonString,
				PizzaDetails.class);

		PizzaDetails pizzaDetailsReturnedObject = sqlQueryDataLayer
				.updatePizzaDetails(pizzaDetailsObject);

		// return success string if everything is ok, or an exception is thrown
		// automatically.
		return gson.toJson(pizzaDetailsReturnedObject);

	}
}
