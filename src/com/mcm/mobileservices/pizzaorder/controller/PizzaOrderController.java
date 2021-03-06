/**
 * 
 */
package com.mcm.mobileservices.pizzaorder.controller;

import java.util.Observable;

import com.google.gson.Gson;
import com.mcm.mobileservices.pizzaorder.entities.PizzaDetails;
import com.mcm.mobileservices.pizzaorder.entities.UserDetails;
import com.mcm.mobileservices.pizzaorder.restclient.RestClient;
import com.mcm.mobileservices.pizzaorder.restclient.RestClient.RequestMethod;

/**
 * @author Mobile Services Team Jan 25, 2015
 * 
 */
@SuppressWarnings("unused")
public class PizzaOrderController extends Observable {

	public void submitUserDetails(UserDetails userDetails) {

		String url = "http://tomcat7-shrikanthavale.rhcloud.com/PizzaOrderService/rest/pizzaorder/saveuserdetails";
		String dummyURL = "http://localhost:8086/PizzaOrderService/rest/pizzaorder/saveuserdetails";

		// Gson object
		Gson gson = new Gson();

		// gson string
		String gsonString = gson.toJson(userDetails);

		// create rest client
		RestClient restClient = new RestClient(url);

		// send the json string to webservice
		restClient.AddParam("userdetails", gsonString);
		try {

			// get the json string
			String userDetailsJSONString = restClient
					.execute(RequestMethod.GET);

			// convert back to object
			userDetails = gson.fromJson(userDetailsJSONString,
					UserDetails.class);

		} catch (Exception e) {

			userDetails
					.setMessage("Something went wrong ... Please contact Admin");

			e.printStackTrace();
		}

		// to trigger notification
		setChanged();

		// Notify all observers
		notifyObservers(userDetails);

	}

	public void searchUserDetails(String telephoneNumber) {

		String url = "http://tomcat7-shrikanthavale.rhcloud.com/PizzaOrderService/rest/pizzaorder/searchuserdetails";
		String dummyURL = "http://localhost:8086/PizzaOrderService/rest/pizzaorder/searchuserdetails";

		// Gson object
		Gson gson = new Gson();
		UserDetails userDetails = new UserDetails();

		// create rest client
		RestClient restClient = new RestClient(url);

		// send the json string to webservice
		restClient.AddParam("telephonenumber", telephoneNumber);
		try {

			// get the json string
			String userDetailsJSONString = restClient
					.execute(RequestMethod.GET);

			// convert back to object
			userDetails = gson.fromJson(userDetailsJSONString,
					UserDetails.class);

		} catch (Exception e) {

			userDetails
					.setMessage("Something went wrong ... Please contact Admin");

			e.printStackTrace();
		}

		// to trigger notification
		setChanged();

		// Notify all observers
		notifyObservers(userDetails);

	}

	public void updateUserDetails(UserDetails userDetails) {

		String url = "http://tomcat7-shrikanthavale.rhcloud.com/PizzaOrderService/rest/pizzaorder/updateuserdetails";
		String dummyURL = "http://localhost:8086/PizzaOrderService/rest/pizzaorder/updateuserdetails";

		// Gson object
		Gson gson = new Gson();

		// gson string
		String gsonString = gson.toJson(userDetails);

		// create rest client
		RestClient restClient = new RestClient(url);

		// send the json string to webservice
		restClient.AddParam("userdetails", gsonString);
		try {

			// get the json string
			String userDetailsJSONString = restClient
					.execute(RequestMethod.GET);

			// convert back to object
			userDetails = gson.fromJson(userDetailsJSONString,
					UserDetails.class);

		} catch (Exception e) {

			userDetails
					.setMessage("Something went wrong ... Please contact Admin");

			e.printStackTrace();
		}

		// to trigger notification
		setChanged();

		// Notify all observers
		notifyObservers(userDetails);

	}

	public void searchPizzaDetails(String pizzaName) {

		String url = "http://tomcat7-shrikanthavale.rhcloud.com/PizzaOrderService/rest/pizzaorder/searchpizzadetails";
		String dummyURL = "http://localhost:8086/PizzaOrderService/rest/pizzaorder/searchpizzadetails";

		// Gson object
		Gson gson = new Gson();
		PizzaDetails pizzaDetails = new PizzaDetails();

		// create rest client
		RestClient restClient = new RestClient(url);

		// send the json string to webservice
		restClient.AddParam("pizzaname", pizzaName);
		try {

			// get the json string
			String pizzaDetailsJSONString = restClient
					.execute(RequestMethod.GET);

			// convert back to object
			pizzaDetails = gson.fromJson(pizzaDetailsJSONString,
					PizzaDetails.class);

		} catch (Exception e) {

			pizzaDetails
					.setMessage("Something went wrong ... Please contact Admin");

			e.printStackTrace();
		}

		// to trigger notification
		setChanged();

		// Notify all observers
		notifyObservers(pizzaDetails);

	}

	public void submitPizzaDetails(PizzaDetails pizzaDetails) {

		String url = "http://tomcat7-shrikanthavale.rhcloud.com/PizzaOrderService/rest/pizzaorder/savepizzadetails";
		String dummyURL = "http://localhost:8086/PizzaOrderService/rest/pizzaorder/savepizzadetails";

		// Gson object
		Gson gson = new Gson();

		// gson string
		String gsonString = gson.toJson(pizzaDetails);

		// create rest client
		RestClient restClient = new RestClient(url);

		// send the json string to webservice
		restClient.AddParam("pizzadetails", gsonString);
		try {

			// get the json string
			String pizzaDetailsJSONString = restClient
					.execute(RequestMethod.GET);

			// convert back to object
			pizzaDetails = gson.fromJson(pizzaDetailsJSONString,
					PizzaDetails.class);

		} catch (Exception e) {

			pizzaDetails
					.setMessage("Something went wrong ... Please contact Admin");

			e.printStackTrace();
		}

		// to trigger notification
		setChanged();

		// Notify all observers
		notifyObservers(pizzaDetails);

	}

	public void updatePizzaDetails(PizzaDetails pizzaDetails) {

		String url = "http://tomcat7-shrikanthavale.rhcloud.com/PizzaOrderService/rest/pizzaorder/updatepizzadetails";
		String dummyURL = "http://localhost:8086/PizzaOrderService/rest/pizzaorder/updatepizzadetails";

		// Gson object
		Gson gson = new Gson();

		// gson string
		String gsonString = gson.toJson(pizzaDetails);

		// create rest client
		RestClient restClient = new RestClient(url);

		// send the json string to webservice
		restClient.AddParam("pizzadetails", gsonString);
		try {

			// get the json string
			String pizzaDetailsJSONString = restClient
					.execute(RequestMethod.GET);

			// convert back to object
			pizzaDetails = gson.fromJson(pizzaDetailsJSONString,
					PizzaDetails.class);

		} catch (Exception e) {

			pizzaDetails
					.setMessage("Something went wrong ... Please contact Admin");

			e.printStackTrace();
		}

		// to trigger notification
		setChanged();

		// Notify all observers
		notifyObservers(pizzaDetails);

	}
}
