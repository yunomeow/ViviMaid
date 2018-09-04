package com.google.jplurk_oauth.vivi;

import java.util.Random;

public class AntiBlood {
	private Random random;
	private char[] character;
	public AntiBlood(){
		random = new Random(System.currentTimeMillis());
		character = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ01234567890".toCharArray();
	}
	public String getString(){
		int len = random.nextInt()%20;
		String tmp = "";
		for(int i=0;i<len;i++){
			tmp += character[random.nextInt()%62];
		}
		return tmp;
	}
}
