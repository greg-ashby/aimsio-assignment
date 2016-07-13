package com.gregashby.aimsio.database;

import static com.gregashby.aimsio.components.MainUI.logger;

import com.gregashby.aimsio.utils.DateHandler;

public class QueryBuilder {

	private final static String MAX_TIME_IN_DAY = "23:59:59.999999";
	private final static String SELECT_START = "SELECT date_format(entry_date, '";
	private final static String SELECT_END = "') AS 'entry_date', count(*) AS 'count' FROM SIGNALS ";
	private final static String GROUP_START = "GROUP BY date_format(entry_date, '";
	private final static String GROUP_END = "') ORDER BY entry_date;";

	public String buildQuery(ISeriesFilter seriesFilter) {

		String select = buildSelectOrGroupClause(SELECT_START, seriesFilter, SELECT_END);
		String where = buildWhereClause(seriesFilter);
		String group = buildSelectOrGroupClause(GROUP_START, seriesFilter, GROUP_END);

		String query = select + where + group;
		logger.info(query);
		return query;
	}

	private String buildSelectOrGroupClause(String clauseStart, ISeriesFilter seriesFilter, String clauseEnd) {
		String dateResolution = seriesFilter == null ? "" : seriesFilter.getDateResolution();
		String dateFormat = DateHandler.getDateFormatForSql(dateResolution);
		return clauseStart + dateFormat + clauseEnd;
	}

	private String buildWhereClause(ISeriesFilter seriesFilter) {

		StringBuilder whereClause = new StringBuilder();
		String whereDateClause = buildWhereClauseForDates(seriesFilter);
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

	private String buildWhereClauseForDates(ISeriesFilter seriesFilter) {

		String whereDateRange = null;
		if (seriesFilter != null) {
			String fromDate = null;
			if (seriesFilter.getFromDate() != null) {
				fromDate = DateHandler.getDayAsString(seriesFilter.getFromDate());
			}
			String toDate = null;
			if (seriesFilter.getToDate() != null) {
				toDate = DateHandler.getDayAsString(seriesFilter.getToDate());
			}

			if (fromDate != null && toDate != null) {
				whereDateRange = "entry_date BETWEEN '" + fromDate + "' AND '" + toDate + " " + MAX_TIME_IN_DAY + "' ";
			} else if (fromDate != null) {
				whereDateRange = "entry_date >= '" + fromDate + "' ";
			} else if (toDate != null) {
				whereDateRange = "entry_date <= '" + toDate + " " + MAX_TIME_IN_DAY + "' ";
			}
		}
		return whereDateRange;
	}

}