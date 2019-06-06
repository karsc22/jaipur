package com.kargames.jaipur.token;

import java.util.ArrayList;

public class SpecialTokenSet {
	public ArrayList<SpecialToken> tokens;
	
	public SpecialTokenSet() {
		tokens = new ArrayList<SpecialToken>();
	}

	public int getPointValue() {
		int sum = 0;
		for (SpecialToken t : tokens) {
			sum += t.getValue();
		}
		return sum;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		for (int i  = 0; i < tokens.size(); i++) {			
			sb.append(tokens.get(i).toString());
			if (i < tokens.size() - 1) {
				sb.append(",");
			}
		}
		sb.append("]");
		return sb.toString();
	}
}
