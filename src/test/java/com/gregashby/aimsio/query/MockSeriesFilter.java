package com.gregashby.aimsio.query;

public class MockSeriesFilter implements ISeriesFilter {
	
	private String assetUN = null;
	private String status = null;
	public String getAssetUN() {
		return assetUN;
	}
	public void setAssetUN(String assetUN) {
		this.assetUN = assetUN;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	

}
