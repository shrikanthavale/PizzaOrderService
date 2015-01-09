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
	public String readListGames(@QueryParam("telephonenumber") String telephoneNumber) throws Exception {

		String username = sqlQueryFetchData.getUserNameFromTelephoneNumber(telephoneNumber);
		return username;

	}

	/*
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/readgame")
	public String readGame(@QueryParam("GameName") String gameName,
			@QueryParam("EmailID") String emailID) {

		List<ImageDetails> gameDetails = sqlQueryReadData.getGame(
				gameName, emailID);

		Gson gson = new Gson();
		String gsonString = gson.toJson(gameDetails);

		return gsonString;

	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/readgamescores")
	public String readGameScores(@QueryParam("GameID") String gameID,
			@QueryParam("EmailID") String emailID,
			@QueryParam("DifficultyLevel") String difficultyLevel) {

		List<GameScores> gameScores = sqlQueryReadData.getGameScores(gameID,
				emailID, difficultyLevel);

		Gson gson = new Gson();
		String gsonString = gson.toJson(gameScores);

		return gsonString;

	}

*/}
