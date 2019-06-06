package com.kargames.jaipur.token;

import com.kargames.jaipur.ResourceType;

public class Token {
	int value;
	ResourceType type;
	
	public Token(ResourceType resType, int val) {
		value = val;
		type = resType;
	}

	@Override
	public boolean equals(Object o) {
		if (o == null || !(o instanceof Token)) {
			return false;
		}
		Token t = (Token) o;
		return t.value == value && t.type == type;
	}
	
	public String toString() {
		return type.name() + ":" + value;
	}
	
	public ResourceType getType() {
		return type;
	}
	
	public int getValue() {
		return value;
	}
}
