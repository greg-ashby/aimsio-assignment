package com.gregashby.aimsio.database;

import com.gregashby.aimsio.chart.SeriesFilter;
import com.gregashby.aimsio.components.IChartFilter;

public class QueryBuilder {

	public String buildQuery(IChartFilter chartFilter, SeriesFilter seriesFilters) {
		return "SELECT date_format(entry_date, '%Y-%m-%d') as 'entry_date', " + ""
				+ "'ALL' as 'signal_name', COUNT(*) as 'count'"
				+ " FROM SIGNALS GROUP BY DATE_FORMAT(entry_date, '%Y-%m-%d')  ORDER BY entry_date;";
	}

}
