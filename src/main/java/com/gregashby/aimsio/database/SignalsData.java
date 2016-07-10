package com.gregashby.aimsio.database;

import static com.gregashby.aimsio.MyUI.logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.gregashby.aimsio.chart.SeriesFilter;
import com.gregashby.aimsio.components.IChartFilter;

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
	
	public static List<List<SignalInfo>> getSignalInfoForMultipleSeries(IChartFilter chartFilter,
			List<SeriesFilter> seriesFilters) throws SQLException {
		List<List<SignalInfo>> signalInfoSeries = new ArrayList<List<SignalInfo>>();
		for (SeriesFilter seriesFilter : seriesFilters) {
			signalInfoSeries.add(getSignalInfo(chartFilter, seriesFilter));
		}
		return signalInfoSeries;
	}

	public static List<SignalInfo> getSignalInfo(IChartFilter chartFilter, SeriesFilter seriesFilter)
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
				signalInfo.setSignalName(rs.getString("signal_name"));
				signalInfo.setCount(rs.getInt("count"));
				signalInfos.add(signalInfo);
			}
		}
		return signalInfos;
	}

}
