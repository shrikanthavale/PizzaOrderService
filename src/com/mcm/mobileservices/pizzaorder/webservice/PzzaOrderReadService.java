/**
 * 
 */
package com.mcm.mobileservices.pizzaorder.webservice;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.mcm.mobileservices.pizzaorder.database.SQLQueryFetchData;

/**
 * @author Shrikant Havale
 * 
 */

@Path("/pizzaorderread")
public class PzzaOrderReadService {

	private SQLQueryFetchData sqlQueryFetchData = new SQLQueryFetchData();
	
	
	/**
	 * Web service accepts the telephone number and retrieves user name from database.
	 * 
	 * @param telephoneNumber - 10 digit telephone number
	 * @return user name as string
	 * @throws Exception - User not found exception is thrown if telephone number is not present in database.
	 * 
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/readusername")
	public String readUserNameUsingTelephoneNumber(@QueryParam("telephonenumber") String telephoneNumber) throws Exception {

		// get the user name from telephone number
		String username = sqlQueryFetchData.getUserNameFromTelephoneNumber(telephoneNumber);
		
		// return the user name
		return username;

	}

}
