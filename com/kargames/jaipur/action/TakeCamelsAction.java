package com.kargames.jaipur.action;

import com.kargames.jaipur.Player;
import com.kargames.jaipur.ResourceType;
import com.kargames.jaipur.Round;

public class TakeCamelsAction implements Action {
	
	@Override
	public void applyAction(Round r, Player p) {
		int num = 0;
		while (r.removeFromMarket(ResourceType.CAMEL)) {
			num++;
		}
		p.addToHerd(num);
		r.refillMarket();
	}

}
