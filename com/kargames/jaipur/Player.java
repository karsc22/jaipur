package com.kargames.jaipur;

import java.util.ArrayList;

import com.kargames.jaipur.action.Action;
import com.kargames.jaipur.controller.Controller;
import com.kargames.jaipur.token.SpecialToken;
import com.kargames.jaipur.token.SpecialTokenSet;
import com.kargames.jaipur.token.Token;
import com.kargames.jaipur.token.TokenSet;

public class Player {
	int herd;
	CardSet hand;
	TokenSet tokens;
	SpecialTokenSet specialTokens;
	Controller controller;
	String name;
	int numWins;
	
	public Player(Controller controller, String name) {
		hand = new CardSet();
		tokens = new TokenSet();
		specialTokens = new SpecialTokenSet();
		this.controller = controller;
		this.name = name;
		numWins = 0;
	}
	
	public void giveCard(ResourceType rt) {
		if (rt == ResourceType.CAMEL) {
			herd++;
		} else {
			hand.add(rt);
		}
	}
	
	public void endOfRound() {
		hand.cards.clear();
		herd = 0;
		tokens.clear();
		specialTokens.tokens.clear();
	}
	
	public Action getPlayerAction(Round r) {
		return controller.getAction(r, this);
	}
	
	public int getScore() {
		return tokens.getPointValue() + specialTokens.getPointValue();
	}

	public boolean handIsFull() {
		return hand.cards.size() >= Jaipur.MAX_HAND_SIZE;
	}
	
	public CardSet getHand() {
		return hand;
	}

	public TokenSet getTokens() {
		return tokens;
	}
	
	public void giveToken(Token t) {
		tokens.add(t);
	}
	public void giveSpecialToken(SpecialToken st) {
		specialTokens.tokens.add(st);
	}

	public boolean removeFromHand(ResourceType rt) {
		return hand.remove(rt);
	}

	public void addToHerd(int num) {
		herd += num;
	}

	public int getHerd() {
		return herd;
	}

	public SpecialTokenSet getSpecialTokens() {
		return specialTokens;
	}
}
