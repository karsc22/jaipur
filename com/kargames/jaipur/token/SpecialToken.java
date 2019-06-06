package com.kargames.jaipur.token;

public enum SpecialToken {
	CAMEL(5, 5),
	BONUS3(1,3),
	BONUS4(4,6),
	BONUS5(8,10);
	
	private int minBonus;
	private int maxBonus;
	
	
	private SpecialToken(int min, int max) {
		minBonus = min;
		maxBonus = max;
	}
	
	public int getValue() {
		// TODO fix this, it just uses the average
		return (minBonus+maxBonus) / 2;
	}

}
