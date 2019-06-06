package com.kargames.jaipur.token;

import java.util.ArrayList;

import com.kargames.jaipur.ResourceType;

public class TokenSet {
	
	ArrayList<Token> tokens;
	
	public TokenSet() {
		tokens = new ArrayList<Token>();
	}
	
	public void add(Token t) {
		if (t != null) {
			tokens.add(t);
		}
	}

	public void remove(Token t) {
		tokens.remove(t);
	}
	
	public Token removeTop(ResourceType rt) {
		for (int i = 0; i < tokens.size(); i++) {
			if (tokens.get(i).type == rt) {
				return tokens.remove(i);
			}
		}
		return null;
	}
	
	public int getValueOf(ResourceType rt) {
		for (int i = 0; i < tokens.size(); i++) {
			if (tokens.get(i).type == rt) {
				return tokens.get(i).value;
			}
		}
		return 0;
	}
	
	public int getPointValue() {
		int sum = 0;
		for (Token t : tokens) {
			sum += t.value;
		}
		return sum;
	}
	
	public void clear() {
		tokens.clear();
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

	public int size() {
		return tokens.size();
	}

	public ArrayList<Token> getTokens() {
		return tokens;
	}

}
