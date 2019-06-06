package com.kargames.jaipur.action;

import com.kargames.jaipur.Player;
import com.kargames.jaipur.Round;

/* 
 * action can be one of:
 * 1. pick 1 card from market - data: 1 RT. TakeSingleGood
 * 2. trade x cards from hand and y cards from herd to get x+y cards from market - data - cardset + number , cardset.  TakeSeveralGoods
 * 3. sell x cards of 1 type - data: rt, num	SellCards
 * 4. take camels	TakeCamels
 * 
 * 
 */

public interface Action {
	
	public void applyAction(Round r, Player p);
}
