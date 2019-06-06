package com.kargames.jaipur;

public class Out {
	public static final boolean shouldLog = true;
	public static void log(String s) {
		if (shouldLog) {
			System.out.println(s);
		}
	}
	
	public static void log(Object o) {
		if (shouldLog) {
			System.out.println(o);
		}
	}

}
