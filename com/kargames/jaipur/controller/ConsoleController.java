package com.kargames.jaipur.controller;

import java.util.HashMap;
import java.util.Scanner;

import com.kargames.jaipur.CardSet;
import com.kargames.jaipur.Jaipur;
import com.kargames.jaipur.Out;
import com.kargames.jaipur.Player;
import com.kargames.jaipur.ResourceType;
import com.kargames.jaipur.Round;
import com.kargames.jaipur.action.Action;
import com.kargames.jaipur.action.SellCardsAction;
import com.kargames.jaipur.action.TakeCamelsAction;
import com.kargames.jaipur.action.TakeSingleGood;

public class ConsoleController implements Controller{
	

	Scanner scanner = new Scanner(System. in); 
	
	/*
	 * 1. pick 1 card from market - data: 1 RT. TakeSingleGood
	 * 2. take camels
	 * 2. trade x cards from hand and y cards from herd to get x+y cards from market - data - cardset + number , cardset.  TakeSeveralGoods
	 * 3. sell x cards of 1 type - data: rt, num	SellCards
	 * 4. take camels	TakeCamels
	 */
	@Override
	public Action getAction(Round r, Player p) {
		
		while (true) {
			
			Out.log("Your hand: (" + p.getHand().getCards().size() + ") " + p.getHand() + ", Herd: " + p.getHerd());
			Out.log("Your tokens: (" + p.getScore() + ") " + p.getTokens());
			Out.log("Your special tokens (" + p.getSpecialTokens().getPointValue() + ") " + p.getSpecialTokens());
			
			Out.log("Select an option: (1-5)");
			Out.log("1. Pick 1 card from market");
			Out.log("2. Take camels");
			Out.log("3. Trade your cards for market cards");
			Out.log("4. Sell cards from your hand");
			Out.log("5. View the status of the board");
			
			String input = scanner. nextLine();
			
			switch (input) {
			case "1":
				if (p.handIsFull()) { 
					Out.log("Your hand is full - you cannot draw any more cards");
					break;
				}
				return getSingleGoodAction(r);				
			case "2":
				return new TakeCamelsAction();
			case "3":
				return getTradeCardsAction(r, p);
			case "4":
				SellCardsAction action = getSellCardsAction(r, p);
				if (action != null) {
					return action;
				}
				break;
			case "5":
				Out.log("Remaining tokens:");
				Out.log(r.getTokens());

				Out.log("Number of empty token piles: " + r.getNumEmptyPiles());
				Out.log("Cards remaining: " + r.getDeck().getCards().size());
				break;
			case "z":
				Out.log("Welcome to the bonus menu! Here is the deck:");
				Out.log(r.getDeck());

				break;
			default:
				Out.log("Invalid input: " + input);
				break;
				
			}
		}
	}
	

	
	public TakeSingleGood getSingleGoodAction(Round r) {
		String input;
		while (true) {
			Out.log("Select a card from the market (1-5)");
			input = scanner. nextLine();
			int num = 0;
			try {
				num = Integer.parseInt(input);
			} catch (NumberFormatException e) {
				// do nothing
			}
			
			if (num >= 1 && num <= 5) {
				return new TakeSingleGood(r.getMarket().getCards().get(num-1));
			}
			
			Out.log("Invalid input: " + input);
		}			
	}
	
	
	public TradeCardsAction getTradeCardsAction(Round r, Player p) {
		String input = null;
		boolean done = false;
		CardSet fromMarket = new CardSet();
		CardSet fromHand = new CardSet();
		while (!done) {
			Out.log("Select cards from the market (1-5).  Enter 0 when finished.");
			input = scanner. nextLine();
			int num = -1;
			try {
				num = Integer.parseInt(input);
			} catch (NumberFormatException e) {	/* do nothing */ }
			
			if (num >= 1 && num <= 5) {
				fromMarket.add(r.getMarket().getCards().get(num-1));
			} else if (num == 0) {
				if (fromMarket.getCards().size() > p.getHand().getCards().size() + p.getHerd()) {
					Out.log("You don't have enough cards to trade, please start again");
					fromMarket.getCards().clear();
				} else {
					done = true;
				}
			} else {
				Out.log("Invalid input: " + input);
			}
		}
		done = false;
		while (!done) {
			Out.log("Select cards from your hand (1-" + p.getHand().getCards().size() +  ").  Enter 0 when finished.");
			input = scanner. nextLine();
			int num = -1;
			try {
				num = Integer.parseInt(input);
			} catch (NumberFormatException e) {	/* do nothing */ }
			
			if (num >= 1 && num <= 5) {
				fromHand.add(p.getHand().getCards().get(num-1));
			} else if (num == 0) {
				// check if it's valid
				if (fromMarket.getCards().size() > fromHand.getCards().size() + p.getHerd()) {
					Out.log("Not enough cards - please select more");
				} else if (p.getHand().getCards().size() + fromMarket.getCards().size() - fromHand.getCards().size() > Jaipur.MAX_HAND_SIZE) {
					Out.log("This would result in having too many cards in your hand.  Please select more to trade");
				} else {
					done = true;
				}
			} else {
				Out.log("Invalid input: " + input);
			}
		}
		
		TradeCardsAction action = new TradeCardsAction();
		action.cardsToTake = fromMarket;
		action.cardsToTrade = fromHand;
		
		return action;
	}
	
	
	public SellCardsAction getSellCardsAction(Round r, Player p) {
		while (true) {
			Out.log("Select a card type to sell:");
			
			HashMap<ResourceType, Integer> map = p.getHand().getCardsAsMap();
			
			int i = 1;
			HashMap<Integer, ResourceType> map2 = new HashMap<Integer, ResourceType>();
			for (ResourceType rt : map.keySet()) {
				if (map.get(rt) >= rt.minToSell) {
					Out.log(i + ". " + rt.name() + " (" + map.get(rt) + ")");
					map2.put(i, rt);
					i++;
				}
			}
			
			if (map2.isEmpty()) {
				Out.log("You don't have any cards that you can sell");
				return null;
			}
			
			String input = scanner. nextLine();
			int num = -1;
			try {
				num = Integer.parseInt(input);
			} catch (NumberFormatException e) {	/* do nothing */ }
	
			if (map2.containsKey(num)) {
				return new SellCardsAction(map2.get(num));
			}
			
			Out.log("Invalid input: " + input);
		}
			
	}
	
}
