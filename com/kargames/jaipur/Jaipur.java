package com.kargames.jaipur;

import com.kargames.jaipur.controller.AiController;
import com.kargames.jaipur.controller.ConsoleController;

public class Jaipur {
	
	public static final int NUM_START_CAMELS = 3;
	public static final int MARKET_SIZE = 5;
	public static final int START_HAND_SIZE = 5;
	public static final int MAX_HAND_SIZE = 7;
	
	
	Player p1;
	Player p2;
	
	int numRounds = 10000;
	
	public static void main(String[] args) {
		new Jaipur();
	}
	

	public Jaipur() {
		p1 = new Player(new ConsoleController(), "Player 1");
		//p1 = new Player(new AiController(1), "Player 1");
		p2 = new Player(new AiController(1), "Player 2");
		Round round = new Round(p1, p2);
		System.out.println("asdf");

		long startTime = System.currentTimeMillis();
		Player loser = p1;
		for (int i = 0; i < numRounds; i++) {
			Player winner = round.playRound(loser);
			if (winner == p1) {
				loser = p2;
			} else {
				loser = p1;
			}
		}
		
		System.out.println(p1.name + " won " + p1.numWins + " times");
		System.out.println(p2.name + " won " + p2.numWins + " times");
		System.out.println("Tied " + (numRounds - p1.numWins - p2.numWins) + " times");
		long totalTime = System.currentTimeMillis() - startTime;
		System.out.println("Did " + numRounds + " rounds in " + totalTime + " ms (" + (1000*numRounds/totalTime) + " rounds/sec)");
		
	}
}

// going first has advantage:
//53-57%, 43-46%, 0.2 -0.6% tie

