package com.gregashby.aimsio.database;

import static com.gregashby.aimsio.MyUI.logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NameClassPair;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class SignalsData {

	private static DataSource dataSource = null;

	public static void init() throws NamingException, SQLException {
		
		Context context2 = new InitialContext();
		NamingEnumeration<NameClassPair> theEnum = context2.list("java:comp/env");
		while(theEnum.hasMore()){
			NameClassPair nameClassPair = theEnum.nextElement();
			logger.error(nameClassPair.getName());
		}
		theEnum = context2.list("java:comp/env/jdbc");
		while(theEnum.hasMore()){
			NameClassPair nameClassPair = theEnum.nextElement();
			logger.error(nameClassPair.getName());
		}
		if (dataSource == null) {
			try {
				Context context = new InitialContext();
				dataSource = (DataSource) context.lookup("java:comp/env/jdbc/amsio_assignment_db");
				logger.info("database has been initialized");
			} catch (NamingException ne) {
				logger.error("Unable to initialize database connection");
				ne.printStackTrace();
				throw ne;
			}
			
			Connection conn = dataSource.getConnection();
			PreparedStatement stmt = conn.prepareStatement("SELECT * FROM SIGNALS LIMIT 10;");
 
			// A table of data representing a database result set, which is
			// usually generated by executing a statement that queries the
			// database.
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				System.out.println("Comapny: " + rs.getString("AssetUN"));
			}
		}
	}

}
