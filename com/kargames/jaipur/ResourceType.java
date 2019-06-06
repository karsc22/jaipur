package com.kargames.jaipur;

/*
 * 6 x diamonds
6 x gold
6 x silver
8 x cloth
8 x spice
10 x leather
11 x camels
 */
public enum ResourceType {
	DIAMOND('D', 6, 2),
	GOLD('G', 6, 2),
	SILVER('S', 6, 2),
	CLOTH('C', 8, 0),
	SPICE('P', 8, 0),
	LEATHER('L', 10, 0),
	CAMEL('A', 8, 0);	//11, but 3 are in market to begin
	
	public int num = 0;
	public int minToSell = 0;
	public char letter;
	
	private ResourceType(char letter, int numCards, int minToSell) {
		this.letter = letter;
		this.num = numCards;
		this.minToSell = minToSell;
	}
}
