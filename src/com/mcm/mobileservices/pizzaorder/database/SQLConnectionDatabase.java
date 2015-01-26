/**
 * 
 */
package com.mcm.mobileservices.pizzaorder.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author Shrikant Havale
 * 
 */
public class SQLConnectionDatabase {

	public static Connection getConnection() throws Exception {

		try {

			// String connectionURL =
			// "jdbc:mysql://127.4.82.130:3306/PizzaOrder";
			String connectionURL = "jdbc:mysql://127.0.0.1:3306/PizzaOrder";
			Connection connection = null;
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			connection = DriverManager.getConnection(connectionURL,
					"admin3QzRbR8", "XeMReZYM3c16");
			return connection;
		} catch (SQLException e) {
			throw e;
		} catch (Exception e) {
			throw e;
		}
	}

	public static void main(String[] args) throws Exception {
	}

}
