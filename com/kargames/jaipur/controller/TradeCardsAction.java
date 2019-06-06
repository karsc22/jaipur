package com.kargames.jaipur.controller;

import com.kargames.jaipur.CardSet;
import com.kargames.jaipur.Player;
import com.kargames.jaipur.ResourceType;
import com.kargames.jaipur.Round;
import com.kargames.jaipur.action.Action;
import com.kargames.jaipur.action.InvalidActionException;

public class TradeCardsAction implements Action {
	
	CardSet cardsToTrade;
	CardSet cardsToTake;
	
	@Override
	public void applyAction(Round r, Player p) {
		
		// assert p.herd + cardsToTrade.size >= cardsToTake.size
		
		for (ResourceType rt : cardsToTake.getCards()) {
			if (!r.removeFromMarket(rt)) {
				throw new InvalidActionException();
			}
			p.giveCard(rt);
		}
		
		for (ResourceType rt : cardsToTrade.getCards()) {
			if (!p.removeFromHand(rt)) {
				throw new InvalidActionException();
			}
			r.addToMarket(rt);
		}
		
		int herdNum = (cardsToTake.getCards().size() - cardsToTrade.getCards().size());
		p.addToHerd(-herdNum);
		for (int i = 0; i < herdNum; i++) {
			r.addToMarket(ResourceType.CAMEL);
		}
		
	}

}
