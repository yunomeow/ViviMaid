package com.google.jplurk_oauth.vivi;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.jplurk_oauth.Qualifier;
import com.google.jplurk_oauth.module.Responses;
import com.google.jplurk_oauth.skeleton.PlurkOAuth;
import com.google.jplurk_oauth.skeleton.RequestException;

public class Pokemon extends Thread{
	private int plurk_id,owner_id;
	private PlurkOAuth auth;
	boolean isEnding;
	private int[] answer;
	private String antiBlood;
	private AntiBlood random;
	public Pokemon(PlurkOAuth auth,int plurk_id,int owner_id){
		this.auth = auth;
		this.plurk_id = plurk_id;
		this.owner_id = owner_id;
		isEnding = false;
		random = new AntiBlood();
	}
	public void run(){	
		String content;
		JSONObject obj;
		JSONArray array;
		int lastLength=0,id;
		int state=0;
		while(!isEnding){
			try {
				obj = auth.using(Responses.class).get((long)plurk_id);
				array = obj.getJSONArray("responses");
				if(array.length()==lastLength){
					sleep(3000);
					continue;
				}
				id = array.getJSONObject(array.length()-1).getInt("user_id");
				if(id == owner_id){
					content =  array.getJSONObject(array.length()-1).getString("content");
					Pattern pattern = Pattern.compile("²q( *?)[0-9][0-9][0-9][0-9]");
					Matcher matcher = pattern.matcher(content);
					if(state == 0){
						auth.using(Responses.class).responseAdd((long)plurk_id, "®æ¦¡¦³»~QAO",Qualifier.SAYS);
					}
					
				}
				lastLength++;
				sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (RequestException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
