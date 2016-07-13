package com.gregashby.aimsio.ui;

public class MockUI implements IRedrawableUI {

	private boolean isRedrawn = false;
	
	public boolean isRedrawn() {
		return isRedrawn;
	}

	public void setRedrawn(boolean isRedrawn) {
		this.isRedrawn = isRedrawn;
	}

	@Override
	public void redraw(PageLayoutConfig config) {
		isRedrawn = true;
	}

}
