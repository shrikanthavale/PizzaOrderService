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

/*	public List<SalesManagementQuestionOptions> getSaleManagementQueestionOptions() {
		List<SalesManagementQuestionOptions> salesManagementQuestionOptionsList = new ArrayList<SalesManagementQuestionOptions>();
		try {
			Connection sqlConnection = SQLConnectionDatabase.getConnection();
			PreparedStatement preparedStatement = sqlConnection
					.prepareStatement("SELECT CASE_STUDY_NODE, QUESTION_OPTION_NUMBER,"
							+ " QUESTION_OPTION_DESCRIPTION, QUESTION_OPTION_EVALUATION, "
							+ " QUESTION_OPTION_MONEY_ASSOCIATED FROM salesmanagement_question_options");

			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {

				SalesManagementQuestionOptions salesManagementQuestionOptions = new SalesManagementQuestionOptions();
				salesManagementQuestionOptions.setCaseStudyNode(resultSet
						.getString("CASE_STUDY_NODE"));
				salesManagementQuestionOptions
						.setCaseStudyOptionNumber(resultSet
								.getInt("QUESTION_OPTION_NUMBER"));
				salesManagementQuestionOptions
						.setQuestionOptionDescription(resultSet
								.getString("QUESTION_OPTION_DESCRIPTION"));
				salesManagementQuestionOptions
						.setQuestionOptionEvaluation(resultSet
								.getString("QUESTION_OPTION_EVALUATION"));
				salesManagementQuestionOptions
						.setQuestionOptionMoneyAssoicated(resultSet
								.getInt("QUESTION_OPTION_MONEY_ASSOCIATED"));

				salesManagementQuestionOptionsList
						.add(salesManagementQuestionOptions);
			}
			sqlConnection.close();

		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return salesManagementQuestionOptionsList;
	}

	public boolean updateNodeQuestionDetails(
			SalesManagementQuestion salesManagementQuestion) {

		try {
			Connection sqlConnection = SQLConnectionDatabase.getConnection();
			PreparedStatement preparedStatement = sqlConnection
					.prepareStatement("UPDATE salesmanagement_question SET CASE_STUDY_TITLE = ?, CASE_STUDY_ORGANIZATION = ?, CASE_STUDY_DESCRIPTION = ?"
							+ " WHERE CASE_STUDY_NODE = ?");

			preparedStatement.setString(1,
					salesManagementQuestion.getCaseStudyTitle());
			preparedStatement.setString(2,
					salesManagementQuestion.getCaseStudyOrganization());
			preparedStatement.setString(3,
					salesManagementQuestion.getCaseStudyDescription());
			preparedStatement.setString(4,
					salesManagementQuestion.getCaseStudyNode());

			preparedStatement.executeUpdate();
			sqlConnection.close();
		} catch (Exception exception) {
			exception.printStackTrace();
			return false;
		}

		return true;
	}

	public boolean updateNodeQuestionOptionDetails(
			List<SalesManagementQuestionOptions> salesManagementQuestionOptions) {

		try {

			Connection sqlConnection = SQLConnectionDatabase.getConnection();
			PreparedStatement preparedStatement = sqlConnection
					.prepareStatement("UPDATE salesmanagement_question_options SET "
							+ " QUESTION_OPTION_DESCRIPTION = ?, QUESTION_OPTION_EVALUATION = ?, "
							+ " QUESTION_OPTION_MONEY_ASSOCIATED = ? WHERE CASE_STUDY_NODE = ? AND QUESTION_OPTION_NUMBER = ?");

			for (SalesManagementQuestionOptions temp : salesManagementQuestionOptions) {

				preparedStatement.setString(1,
						temp.getQuestionOptionDescription());
				preparedStatement.setString(2,
						temp.getQuestionOptionEvaluation());
				preparedStatement.setInt(3,
						temp.getQuestionOptionMoneyAssoicated());
				preparedStatement.setString(4, temp.getCaseStudyNode());
				preparedStatement.setInt(5, temp.getCaseStudyOptionNumber());
				preparedStatement.executeUpdate();

			}

			sqlConnection.close();

		} catch (Exception exception) {

			exception.printStackTrace();
			return false;
		}

		return true;
	}
*/}
