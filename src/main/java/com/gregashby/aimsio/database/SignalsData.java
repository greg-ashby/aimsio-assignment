package com.gregashby.aimsio.database;

import static com.gregashby.aimsio.ui.MainUI.logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.gregashby.aimsio.utils.DateHandler;

/**
 * Static class for executing queries against the database. initConnection()
 * needs to be called before executing any other functions.
 */
public class SignalsData {

	private static DataSource dataSource = null;

	/**
	 * Looks up the datasource in the JNDI environment. Only loads once, so if
	 * the datasource connection is changed, please restart the server to
	 * refresh it.
	 * 
	 * @throws NamingException
	 *             - if it can't find the jdbc connection with the excepted name
	 *             context
	 */
	public static void initConnection() throws NamingException {

		if (dataSource == null) {
			try {
				Context context = new InitialContext();
				dataSource = (DataSource) context.lookup("java:comp/env/jdbc/aimsio_assignment_db");
				logger.info("database has been initialized");
			} catch (NamingException ne) {
				logger.error("Unable to initialize database connection");
				ne.printStackTrace();
				throw ne;
			}
		}
	}

	/**
	 * Main function for loading a List of Signal Information.
	 * 
	 * @param seriesFilter
	 *            - any object that implements ISeriesFilter, which has the
	 *            parameters for selecting different data (e.g. date range,
	 *            AssetUN, etc)
	 * 
	 * @return - a (possibly empty) list of SignalInfo for the provided filter
	 * 
	 * @throws SQLException
	 *             - any database issues
	 * @throws ParseException
	 *             - any date format issues
	 * 
	 * @see QueryBuilder
	 */
	public static List<SignalInfo> getSignalInfo(ISeriesFilter seriesFilter) throws SQLException, ParseException {
		List<SignalInfo> signalInfos = new ArrayList<SignalInfo>();
		QueryBuilder queryBuilder = new QueryBuilder();
		String query = queryBuilder.buildQuery(seriesFilter);
		try (Connection conn = dataSource.getConnection();
				PreparedStatement stmt = conn.prepareStatement(query);
				ResultSet rs = stmt.executeQuery();) {
			while (rs.next()) {
				SignalInfo signalInfo = new SignalInfo();
				signalInfo.setEntryDate(
						DateHandler.convertStringToDate(seriesFilter.getDateResolution(), rs.getString("entry_date")));
				signalInfo.setCount(rs.getInt("count"));
				signalInfos.add(signalInfo);
			}
		}
		return signalInfos;
	}

	/**
	 * Gets the maximum date for signal information in the database, so that the
	 * UI filters can have any "To dates" set appropriately. Note that this
	 * wouldn't be a good idea in an actual real time system since you'd just
	 * want "to dates" being the current time, but since the sample data is
	 * static, I didn't want the initial date ranges to be based on the current
	 * time as they'd show no data.
	 * 
	 * @return
	 * @throws SQLException
	 */
	public static Date getMaxDate() throws SQLException {
		try (Connection conn = dataSource.getConnection();
				PreparedStatement stmt = conn.prepareStatement("SELECT max(entry_date) AS 'max_date' FROM SIGNALS;");
				ResultSet rs = stmt.executeQuery();) {
			rs.next();
			return rs.getDate("max_date");
		}
	}

	/**
	 * Gets a list of all AssetUNs in the database, so UIs can pre-populate
	 * option lists correctly.
	 * 
	 * @return
	 * @throws SQLException
	 */
	public static List<String> getAssetUNs() throws SQLException {
		List<String> assertUNs = new ArrayList<String>();
		try (Connection conn = dataSource.getConnection();
				PreparedStatement stmt = conn.prepareStatement("SELECT Distinct AssetUN FROM SIGNALS;");
				ResultSet rs = stmt.executeQuery();) {
			while (rs.next()) {
				assertUNs.add(rs.getString("AssetUN"));
			}
			return assertUNs;
		}
	}

	/**
	 * Gets a list of all statuses in the database, so UIs can pre-populate
	 * option lists correctly.
	 * 
	 * @return
	 * @throws SQLException
	 */
	public static List<String> getStatuses() throws SQLException {
		List<String> statuses = new ArrayList<String>();
		try (Connection conn = dataSource.getConnection();
				PreparedStatement stmt = conn.prepareStatement("SELECT Distinct status FROM SIGNALS;");
				ResultSet rs = stmt.executeQuery();) {
			while (rs.next()) {
				statuses.add(rs.getString("status"));
			}
			return statuses;
		}
	}

}
