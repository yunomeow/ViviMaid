package com.google.jplurk_oauth.vivi;

import java.util.HashSet;

public class PlurkChecker {
	private HashSet<Long> set;
	public PlurkChecker(){
		set = new HashSet<Long>(); 
	}
	public boolean check(long in){
		return set.contains(in);
	}
	public void clear(){
		set.clear();
	}
	public void add(long in){
		set.add(in);
	}
}
