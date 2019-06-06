package com.kargames.jaipur.action;

import com.kargames.jaipur.Player;
import com.kargames.jaipur.ResourceType;
import com.kargames.jaipur.Round;

public class TakeSingleGood implements Action {

	ResourceType typeToTake;
	
	public TakeSingleGood(ResourceType rt) {
		typeToTake = rt;
	}

	@Override
	public void applyAction(Round r, Player p) {
		if (r.removeFromMarket(typeToTake)) {
			p.giveCard(typeToTake);
			r.refillMarket();
		} else {
			System.out.println("ERROR - " + typeToTake.name() + " is not in the market");
			System.out.println("market: " + r.getMarketString());
			System.exit(1);
		}
	}

}
