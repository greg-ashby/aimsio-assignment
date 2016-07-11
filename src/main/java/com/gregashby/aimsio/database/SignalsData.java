package com.gregashby.aimsio.database;

import static com.gregashby.aimsio.MyUI.logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.gregashby.aimsio.query.IChartFilter;
import com.gregashby.aimsio.query.ISeriesFilters;
import com.gregashby.aimsio.query.QueryBuilder;

public class SignalsData {

	private static DataSource dataSource = null;

	public static void initConnection() throws NamingException {

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
		}
	}

	public static List<SignalInfo> getSignalInfo(IChartFilter chartFilter, ISeriesFilters seriesFilter)
			throws SQLException {
		List<SignalInfo> signalInfos = new ArrayList<SignalInfo>();
		QueryBuilder queryBuilder = new QueryBuilder();
		String query = queryBuilder.buildQuery(chartFilter, seriesFilter);
		try (Connection conn = dataSource.getConnection();
				PreparedStatement stmt = conn.prepareStatement(query);
				ResultSet rs = stmt.executeQuery();) {
			while (rs.next()) {
				SignalInfo signalInfo = new SignalInfo();
				signalInfo.setEntryDate(rs.getDate("entry_date"));
				signalInfo.setCount(rs.getInt("count"));
				signalInfos.add(signalInfo);
			}
		}
		return signalInfos;
	}

	public static Date getMaxDate() throws SQLException {
		try (Connection conn = dataSource.getConnection();
				PreparedStatement stmt = conn.prepareStatement("SELECT max(entry_date) AS 'max_date' FROM SIGNALS;");
				ResultSet rs = stmt.executeQuery();) {
			rs.next();
			return rs.getDate("max_date");
		}
	}

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
