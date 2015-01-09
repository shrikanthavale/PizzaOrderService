/**
 * 
 */
package com.mcm.mobileservices.pizzaorder.webservice;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * @author Shrikant Havale
 * 
 *         This class represent the starting point for the web service Memory
 *         Game for writing some data into database. It has several methods
 *         exposed, few of them are writing scores, inserting dummy data,
 *         deleting dummy data etc.
 * 
 */

@Path("/pizzaorderwrite")
public class PizzaOrderWriteService {

	/**
	 * SQL query write data instance
	 */
	//private SQLQueryWriteData sqlQueryWriteData = new SQLQueryWriteData();

	@POST
	@Produces({ MediaType.APPLICATION_JSON })
	@Path("/writeusername")
	public String inserNewUser(@FormParam("Username") String userName,
			@FormParam("EmailID") String userEmailID) {

		// create user details object
//		UserDetails userDetails = null;
//		Gson gson = new Gson();
//
//		// call the database layer
//		userDetails = sqlQueryWriteData.insertNewUser(userName, userEmailID);

		// convert to JSON and return
		//return gson.toJson(userDetails);
		
		return "Shrikant Havale";
	}

/*	@POST
	@Produces({ MediaType.APPLICATION_JSON })
	@Path("/insertscores")
	public String insertScores(@FormParam("GameName") String gameName,
			@FormParam("EmailID") String userEmailID,
			@FormParam("DifficultyLevel") String difficultyLevel,
			@FormParam("ScoreMove") String scoreMove,
			@FormParam("ScoreTimerSeconds") String scoreTimerSeconds,
			@FormParam("CustomPredefineGame") String customPredefinedGame) {

		// create game score details object
		GameScores gameScores = null;
		Gson gson = new Gson();

		// call the database layer
		gameScores = sqlQueryWriteData.insertScores(userEmailID, gameName,
				difficultyLevel, scoreMove, scoreTimerSeconds,
				customPredefinedGame);

		// convert to JSON and return
		return gson.toJson(gameScores);
	}

	@POST
	@Produces({ MediaType.APPLICATION_JSON })
	@Path("/insertnewgame")
	public String insertNewGame(
			@FormParam("NewGame") String gameDetailsJsonString,
			@FormParam("GameImages") String listGameImagesJsonString) {

		// create game details object and list of images
		GameDetails gameDetails = null;
		List<ImageDetails> listImages = null;

		Gson gson = new Gson();

		// get the game details object from JSON string
		gameDetails = gson.fromJson(gameDetailsJsonString, GameDetails.class);

		// get the images list
		listImages = Arrays.asList(gson.fromJson(listGameImagesJsonString,
				ImageDetails[].class));
		gameDetails = sqlQueryWriteData.insertNewGame(gameDetails,
				listImages);

		// convert to gson and return
		return gson.toJson(gameDetails);
	}*/
}