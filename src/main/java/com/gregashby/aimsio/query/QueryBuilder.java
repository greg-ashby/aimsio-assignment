package com.gregashby.aimsio.query;

import java.text.SimpleDateFormat;

public class QueryBuilder {

	public final static String WHERE_DATE_FORMAT = "yyyy-MM-dd";

	private final static String DEFAULT_DATE_FORMAT = "%Y-%m-%d";
	private final static String MAX_TIME_IN_DAY = "23:59:59.999999";
	private final static String SELECT_START = "SELECT date_format(entry_date, '";
	private final static String SELECT_END = "') AS 'entry_date', count(*) AS 'count' FROM SIGNALS ";
	private final static String GROUP_START = "GROUP BY date_format(entry_date, '";
	private final static String GROUP_END = "') ORDER BY entry_date;";

	public String buildQuery(IChartFilter chartFilter, ISeriesFilters seriesFilter) {

		String select = SELECT_START + DEFAULT_DATE_FORMAT + SELECT_END;
		String where = buildWhereClause(chartFilter, seriesFilter);
		String group = GROUP_START + DEFAULT_DATE_FORMAT + GROUP_END;

		return select + where + group;
	}

	private String buildWhereClause(IChartFilter chartFilter, ISeriesFilters seriesFilter) {

		String whereDateRange = null;

		if (chartFilter != null) {
			String fromDate = null;
			if(chartFilter.getFromDate() != null) {
				fromDate = new SimpleDateFormat(WHERE_DATE_FORMAT).format(chartFilter.getFromDate());
			}
			String toDate = null;
			if(chartFilter.getToDate() != null) {
				toDate = new SimpleDateFormat(WHERE_DATE_FORMAT).format(chartFilter.getToDate());
			}
			
			if (fromDate != null && toDate != null) {
				whereDateRange = "entry_date BETWEEN '" + fromDate + "' AND '" + toDate + " " + MAX_TIME_IN_DAY +"' ";
			} else if (fromDate != null) { 
				whereDateRange = "entry_date >= '" + fromDate + "' ";
			} else {
				whereDateRange = "entry_date <= '" + toDate + " " + MAX_TIME_IN_DAY +"' ";
			}
		}

		if (whereDateRange != null) {
			return "WHERE " + whereDateRange;
		} else {
			return "";
		}
	}

}
