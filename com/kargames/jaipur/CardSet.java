package com.kargames.jaipur;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class CardSet {

	ArrayList<ResourceType> cards;
	
	public CardSet() {
		cards = new ArrayList<ResourceType>();
	}
	
	public void add(ResourceType t) {
		cards.add(t);
	}
	
	/** 
	 * Return true if the remove was successful
	 * 
	 * @param t
	 * @return
	 */
	public boolean remove(ResourceType t) {
		return cards.remove(t);
	}
	
	public ResourceType draw() {
		return cards.remove(cards.size()-1);
	}
	
	public void shuffle() {
		
		for (int i = 0; i < cards.size(); i++) {
			int swapPos = (int) (Math.random() * (cards.size() - 1));
			ResourceType temp = cards.get(i);
			cards.set(i, cards.get(swapPos));
			cards.set(swapPos, temp);
		}
		
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		for (int i  = 0; i < cards.size(); i++) {
			sb.append(cards.get(i).name());
			if (i < cards.size() - 1) {
				sb.append(", ");
			}
		}
		sb.append("]");
		return sb.toString();
	}

	public HashMap<ResourceType, Integer> getCardsAsMap() {
		HashMap<ResourceType, Integer> map = new HashMap<ResourceType, Integer>();
		for (ResourceType rt : cards) { 
			if (map.containsKey(rt)) {
				map.put(rt, map.get(rt) + 1);
			} else {
				map.put(rt, 1);
			}
		}
		return map;
	}

	public ArrayList<ResourceType> getCards() {
		return cards;
	}
}
