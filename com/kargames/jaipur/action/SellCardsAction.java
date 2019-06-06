package com.kargames.jaipur.action;

import com.kargames.jaipur.Player;
import com.kargames.jaipur.ResourceType;
import com.kargames.jaipur.Round;
import com.kargames.jaipur.token.SpecialToken;

public class SellCardsAction implements Action {
	
	public ResourceType resourceToSell;
	
	public SellCardsAction(ResourceType rt) {
		resourceToSell = rt;
	}

	@Override
	public void applyAction(Round r, Player p) {
		int numCardsSold = 0;
		while (p.removeFromHand(resourceToSell)) {
			p.giveToken(r.removeTopToken(resourceToSell));
			numCardsSold++;
			
		}
		
		if (numCardsSold == 3) {
			p.giveSpecialToken(SpecialToken.BONUS3);
		} else if (numCardsSold == 4) {
			p.giveSpecialToken(SpecialToken.BONUS4);
		} else if (numCardsSold >= 5) {
			p.giveSpecialToken(SpecialToken.BONUS5);
		}

	}

}
