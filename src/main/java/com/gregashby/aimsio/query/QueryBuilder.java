package com.gregashby.aimsio.query;

import java.text.SimpleDateFormat;

import static com.gregashby.aimsio.MainUI.logger;

public class QueryBuilder {

	public final static String WHERE_DATE_FORMAT = "yyyy-MM-dd";

	private final static String DEFAULT_DATE_FORMAT = "%Y-%m-%d";
	private final static String MAX_TIME_IN_DAY = "23:59:59.999999";
	private final static String SELECT_START = "SELECT date_format(entry_date, '";
	private final static String SELECT_END = "') AS 'entry_date', count(*) AS 'count' FROM SIGNALS ";
	private final static String GROUP_START = "GROUP BY date_format(entry_date, '";
	private final static String GROUP_END = "') ORDER BY entry_date;";

	public String buildQuery(IChartFilter chartFilter, ISeriesFilter seriesFilter) {

		String select = SELECT_START + DEFAULT_DATE_FORMAT + SELECT_END;
		String where = buildWhereClause(chartFilter, seriesFilter);
		String group = GROUP_START + DEFAULT_DATE_FORMAT + GROUP_END;

		String query = select + where + group;
		logger.info(query);
		return query;
	}

	private String buildWhereClause(IChartFilter chartFilter, ISeriesFilter seriesFilter) {

		StringBuilder whereClause = new StringBuilder();
		String whereDateClause = buildWhereClauseForDates(chartFilter);
		String whereAssetClause = buildWhereClauseForAssetUN(seriesFilter);
		String whereStatusClause = buildWhereClauseForStatus(seriesFilter);
		
		if (whereDateClause != null) {
			whereClause.append("WHERE ");
			whereClause.append(whereDateClause);
		}

		if (whereAssetClause != null) {
			if (whereClause.length() > 0) {
				whereClause.append(" AND ");
			} else {
				whereClause.append("WHERE ");
			}
			whereClause.append(whereAssetClause);
		}

		if (whereStatusClause != null) {
			if (whereClause.length() > 0) {
				whereClause.append(" AND ");
			} else {
				whereClause.append("WHERE ");
			}
			whereClause.append(whereStatusClause);
		}
		
		return whereClause.toString();
	}

	private String buildWhereClauseForStatus(ISeriesFilter seriesFilter) {
		if (seriesFilter != null && seriesFilter.getStatus() != null && !("ALL".equals(seriesFilter.getStatus()))) {
			return "status = '" + seriesFilter.getStatus() + "' ";
		} else {
			return null;
		}
	}

	private String buildWhereClauseForAssetUN(ISeriesFilter seriesFilter) {
		if (seriesFilter != null && seriesFilter.getAssetUN() != null && !("ALL".equals(seriesFilter.getAssetUN()))) {
			return "AssetUN = '" + seriesFilter.getAssetUN() + "' ";
		} else {
			return null;
		}
	}

	private String buildWhereClauseForDates(IChartFilter chartFilter) {

		String whereDateRange = null;
		if (chartFilter != null) {
			String fromDate = null;
			if (chartFilter.getFromDate() != null) {
				fromDate = new SimpleDateFormat(WHERE_DATE_FORMAT).format(chartFilter.getFromDate());
			}
			String toDate = null;
			if (chartFilter.getToDate() != null) {
				toDate = new SimpleDateFormat(WHERE_DATE_FORMAT).format(chartFilter.getToDate());
			}

			if (fromDate != null && toDate != null) {
				whereDateRange = "entry_date BETWEEN '" + fromDate + "' AND '" + toDate + " " + MAX_TIME_IN_DAY + "' ";
			} else if (fromDate != null) {
				whereDateRange = "entry_date >= '" + fromDate + "' ";
			} else {
				whereDateRange = "entry_date <= '" + toDate + " " + MAX_TIME_IN_DAY + "' ";
			}
		}
		return whereDateRange;
	}

}
