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

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/readusername")
	public String readUserNameUsingTelephoneNumber(@QueryParam("telephonenumber") String telephoneNumber) throws Exception {

		String username = sqlQueryFetchData.getUserNameFromTelephoneNumber(telephoneNumber);
		return username;

	}

}
