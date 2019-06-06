package com.kargames.jaipur.controller;

import java.util.HashMap;

import com.kargames.jaipur.Jaipur;
import com.kargames.jaipur.Out;
import com.kargames.jaipur.Player;
import com.kargames.jaipur.ResourceType;
import com.kargames.jaipur.Round;
import com.kargames.jaipur.action.Action;
import com.kargames.jaipur.action.SellCardsAction;
import com.kargames.jaipur.action.TakeCamelsAction;
import com.kargames.jaipur.action.TakeSingleGood;
import com.kargames.jaipur.token.Token;

public class AiController implements Controller{
	
	boolean printDebug = true;

	double camelValue = 1.5;
	

	public AiController(double camelValue) {
		this.camelValue = camelValue;
	}
	
	public void log(String s) {
		if (printDebug) {
			Out.log(s);
		}
	}
	
	
	@Override
	public Action getAction(Round r, Player p) {
		
		// how many total actions possible (average hand)?
		// 1. 3-4
		// 2. 1
		// 3. to take:
		//		5 choose 1 (5) + 5 choose 2(10) + 5 choose 3(10) + 5 choose 4 (5) + 5 choose 5 (1) = 36
		// to give: (same)
		// assume about 500 combinations
		// 4. 2-3
		
		log("AI hand: (" + p.getHand().getCards().size() + ") " + p.getHand().toString() + ", Herd: " + p.getHerd());
		log("AI tokens: (" + p.getScore() + ") " + p.getTokens());
		log("AI special tokens (" + p.getSpecialTokens().getPointValue() + ") " + p.getSpecialTokens());

		HashMap<ResourceType, Integer> map = p.getHand().getCardsAsMap();
		
		int highestNum = -1;
		ResourceType highest = ResourceType.CAMEL;
		int numCamels = 0;
		for (int i = 0; i < Jaipur.MARKET_SIZE; i++) {
			ResourceType rt = r.getMarket().getCards().get(i);
			if (rt == ResourceType.CAMEL) {
				numCamels++;
			} else {
				int val = r.getTokens().getValueOf(rt);
				if (map.containsKey(rt)) {
					val += map.get(rt)*1;
				}
				if (val > highestNum) {
					highestNum = val;
					highest = rt;
				}
			}
		}
		
		double camelScore = (numCamels * camelValue);
		
		int nearEndOfGame = nearEndOfGame(r);
		int marketValue = (int) ((highestNum > camelScore) ? highestNum : camelScore);
		int shouldSell = shouldSell(r, p, nearEndOfGame,  marketValue);

		Out.log("nearEndOfGame = " + nearEndOfGame);
		Out.log("marketValue = " + marketValue);
		Out.log("shouldSell = " + shouldSell);

		if (p.handIsFull() || shouldSell > 30) {
			// sell the highest number of cards

			ResourceType h = null;
			int hNum = 0;
			for (ResourceType rt : map.keySet()) {
				if (map.get(rt) > hNum) {
					if ( map.get(rt) >= rt.minToSell) {
						hNum = map.get(rt);
						h = rt;
					}
				}
			}
			log("Sell cards action: " + h.name());
			return new SellCardsAction(h);
		}
		
		
		// evaluate the market
		// value = round.getcurrentValue(rt) + numInMyHandBonus
		
		// 3 cards -> 2 pts (+2)
		// 4 cards -> 5 pts (+3)
		// 5 cards -> 9 pts (+4)
		
		// let camel have value 1.5 (to be calculated later)
		//e.g. market has camel(1.5), camel(1.5), diamond(7), spice(3), cloth(5)
		// in hand i have spicex3
		// values will be 1, 1, 7, 4+(5-2) = 7, 5
		
		

		log("CamelScore: " + camelScore);
		log("highest: " + highest.name() + " (" + highestNum + ")");
		if (camelScore > highestNum) {
			return new TakeCamelsAction();
		} else {
			return new TakeSingleGood(highest);
		}
		
	}
	
	public int shouldSell(Round r, Player p, int nearEndOfGame, int marketValue) {
		int result = p.getHand().getCards().size(); // 0 to 7
		result = result * result; // 0 to 49
		result += nearEndOfGame / 5;
		result += (10 - marketValue);
		
		return result;
	}
	
	public int nearEndOfGame(Round r) {
		
		int result = 0;
		
		int numEmpty = ResourceType.values().length;
		HashMap<ResourceType, Integer> map = new HashMap<ResourceType, Integer>();
		for (Token t : r.getTokens().getTokens()) {
			if (!map.containsKey(t.getType())) {
				map.put(t.getType(), 1);
				numEmpty--;
			} else {
				map.put(t.getType(), map.get(t.getType()) + 1);
			}
		}
		
		int numOne = 0;
		for (ResourceType rt: map.keySet()) {
			if (map.get(rt) == 1) {
				numOne++;
			}
		}
		
		numOne++;
		numEmpty++;
		
		result = numOne * numOne + numEmpty * numEmpty * numEmpty; // max about 40
		
		int cards = r.getDeck().getCards().size(); 
		if (cards < 8) {
			result += (8-cards) * (8-cards); // max 49
		}
		
		return result;
	}

}
