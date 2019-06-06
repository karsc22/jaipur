package com.kargames.jaipur;

import com.kargames.jaipur.token.SpecialToken;
import com.kargames.jaipur.token.Token;
import com.kargames.jaipur.token.TokenSet;

public class Round {
	CardSet market;
	CardSet deck;

	TokenSet tokens;
	
	Player p1;
	Player p2;
	
	
	public Round(Player p1, Player p2) {
		this.p1 = p1;
		this.p2 = p2;
	}
	
	public Player playRound(Player firstPlayer) {
		setupTokens();
		setupCards();
		setupPlayers();
		
		boolean done = false;
		Player currentPlayer = firstPlayer;

		while (!done) {
			displayBoard();
			currentPlayer.getPlayerAction(this).applyAction(this, currentPlayer);
			done = checkRoundComplete();
			currentPlayer = (currentPlayer == p1) ? p2 : p1;
		}
		Player winner = getWinner();
		p1.endOfRound();
		p2.endOfRound();
		return winner;
	}
	
	public Player getWinner() {
		if (p1.herd > p2.herd) {
			p1.specialTokens.tokens.add(SpecialToken.CAMEL);
		} else if (p1.herd < p2.herd) {
			p2.specialTokens.tokens.add(SpecialToken.CAMEL);
		}
		Out.log("Player 1:");
		giveSummary(p1);
		Out.log("Player 2:");
		giveSummary(p2);
		Out.log("===============");
		Player winner = null;
		if (p1.getScore() > p2.getScore()) {
			winner = p1;
		} else if (p1.getScore() < p2.getScore()) {
			winner = p2;
		} else {
			int p1t = p1.getTokens().size() + p1.specialTokens.tokens.size();
			int p2t = p2.getTokens().size() + p2.specialTokens.tokens.size();
			if (p1t > p2t) {
				winner = p1;
			} else if (p1t < p2t) {
				winner = p2;
			} else {
				Out.log("It's a tie!");
				return null;
			}
		}
		winner.numWins++;
		Out.log(winner.name + " wins!");
		return winner;
	}
	
	public void giveSummary(Player p ) {
		Out.log("Tokens: (" + p.getScore() + ") " + p.tokens);
		Out.log("Special tokens (" + p.specialTokens.getPointValue() + ") " + p.specialTokens);
		Out.log("Total Score: " + (p.getScore() + p.specialTokens.getPointValue()));
		Out.log("");
	}
	
	public void refillMarket() {
		while (market.cards.size() < Jaipur.MARKET_SIZE && deck.cards.size() > 0) {
			market.add(deck.draw());
		}
	}
	
	public void displayBoard() {
		System.out.println(toString());
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Market:").append(market);

		return sb.toString();
	}
	
	public boolean checkRoundComplete() {
		if (deck.cards.isEmpty()) {
			return true;
		}
		
		return (getNumEmptyPiles() >= 3);
		
	}
	
	public int getNumEmptyPiles() {
		int num = 0;
		for (ResourceType rt : ResourceType.values()) {
			if (rt != ResourceType.CAMEL) {
				if (tokens.getValueOf(rt) == 0) {
					num++;
				}
			}
		}
		return num;
	}
	
	public void setupPlayers() {
		for (int i = 0; i < 5; i++) {
			p1.giveCard(deck.draw());
			p2.giveCard(deck.draw());
		}
	}
	
	
	public void setupCards() {
		deck = new CardSet();
		market = new CardSet();
		for (ResourceType rt : ResourceType.values()) {
			for (int i = 0; i < rt.num; i++) {
				deck.add(rt);
			}
		}
		deck.shuffle();
		
		for (int i = 0; i < Jaipur.NUM_START_CAMELS; i++) {
			market.add(ResourceType.CAMEL);
		}
		refillMarket();
	}
	
	
	/*
diamond	77555
gold	66555
silver	55555
cloth	5332211
spice	5332211
leather	432111111
	 */
	public void setupTokens() {
		if (tokens == null) {
			tokens = new TokenSet();
		} else {
			tokens.clear();
		}
		tokens.add(new Token(ResourceType.DIAMOND, 7));
		tokens.add(new Token(ResourceType.DIAMOND, 7));
		tokens.add(new Token(ResourceType.DIAMOND, 5));
		tokens.add(new Token(ResourceType.DIAMOND, 5));
		tokens.add(new Token(ResourceType.DIAMOND, 5));

		tokens.add(new Token(ResourceType.GOLD, 6));
		tokens.add(new Token(ResourceType.GOLD, 6));
		tokens.add(new Token(ResourceType.GOLD, 5));
		tokens.add(new Token(ResourceType.GOLD, 5));
		tokens.add(new Token(ResourceType.GOLD, 5));

		tokens.add(new Token(ResourceType.SILVER, 5));
		tokens.add(new Token(ResourceType.SILVER, 5));
		tokens.add(new Token(ResourceType.SILVER, 5));
		tokens.add(new Token(ResourceType.SILVER, 5));
		tokens.add(new Token(ResourceType.SILVER, 5));

		tokens.add(new Token(ResourceType.SPICE, 5));
		tokens.add(new Token(ResourceType.SPICE, 3));
		tokens.add(new Token(ResourceType.SPICE, 3));
		tokens.add(new Token(ResourceType.SPICE, 2));
		tokens.add(new Token(ResourceType.SPICE, 2));
		tokens.add(new Token(ResourceType.SPICE, 1));
		tokens.add(new Token(ResourceType.SPICE, 1));
		
		tokens.add(new Token(ResourceType.CLOTH, 5));
		tokens.add(new Token(ResourceType.CLOTH, 3));
		tokens.add(new Token(ResourceType.CLOTH, 3));
		tokens.add(new Token(ResourceType.CLOTH, 2));
		tokens.add(new Token(ResourceType.CLOTH, 2));
		tokens.add(new Token(ResourceType.CLOTH, 1));
		tokens.add(new Token(ResourceType.CLOTH, 1));

		tokens.add(new Token(ResourceType.LEATHER, 4));
		tokens.add(new Token(ResourceType.LEATHER, 3));
		tokens.add(new Token(ResourceType.LEATHER, 2));
		tokens.add(new Token(ResourceType.LEATHER, 1));
		tokens.add(new Token(ResourceType.LEATHER, 1));
		tokens.add(new Token(ResourceType.LEATHER, 1));
		tokens.add(new Token(ResourceType.LEATHER, 1));
		tokens.add(new Token(ResourceType.LEATHER, 1));
		tokens.add(new Token(ResourceType.LEATHER, 1));
	}

	public Token removeTopToken(ResourceType rt) {
		return tokens.removeTop(rt);
	}

	public boolean removeFromMarket(ResourceType rt) {
		return market.remove(rt);
	}

	public String getMarketString() {
		return market.toString();
	}

	public void addToMarket(ResourceType rt) {
		market.add(rt);
	}

	public CardSet getMarket() {
		return market;
	}

	public TokenSet getTokens() {
		return tokens;
	}

	public CardSet getDeck() {
		return deck;
	}
}
