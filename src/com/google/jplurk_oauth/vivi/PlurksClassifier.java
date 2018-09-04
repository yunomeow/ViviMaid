package com.google.jplurk_oauth.vivi;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.jplurk_oauth.Ids;
import com.google.jplurk_oauth.Offset;
import com.google.jplurk_oauth.Qualifier;
import com.google.jplurk_oauth.module.Polling;
import com.google.jplurk_oauth.module.Responses;
import com.google.jplurk_oauth.module.Timeline;
import com.google.jplurk_oauth.skeleton.PlurkOAuth;
import com.google.jplurk_oauth.skeleton.RequestException;

public class PlurksClassifier extends Thread{
	private PlurkOAuth auth;
	private PlurkChecker pchecker;
	public PlurksClassifier(PlurkOAuth tauth){
		auth = tauth;
		pchecker = new PlurkChecker();
	}
	public void run(){
		int plurk_id,owner_id;
		String content;
		JSONObject obj;
		Ids ids;
		while(true){
	        try {	
	        		JSONArray jarray = auth.using(Polling.class).getPlurks(new Offset(-30000)).getJSONArray("plurks");
	        		for(int i = 0;i < jarray.length();i++){
		        		obj = jarray.getJSONObject(i);
						System.out.println(obj);
		        		plurk_id = obj.getInt("plurk_id");
		        		if(pchecker.check(plurk_id)==true){
		        			System.out.println(true);
		        			continue;
		        		}else{
		        			pchecker.add(plurk_id);
		        		}
		        		owner_id = obj.getInt("owner_id");
						content =  obj.getString("content");
						Pattern pattern = Pattern.compile("玩( *?)猜數字");
						Matcher matcher = pattern.matcher(content);
						if(matcher.find()) {
							auth.using(Responses.class).responseAdd((long)plurk_id, "開始玩囉w",Qualifier.SAYS);
							(new GuessNumber(auth,plurk_id,owner_id)).start();
							
						}
						say(plurk_id,content);
						List<Integer> idList = new ArrayList<Integer>();
						idList.add(plurk_id);
						ids = new Ids(idList);
						auth.using(Timeline.class).mutePlurks(ids);
	        		}
			}catch (RequestException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	private void say(long plurk_id,String content) throws RequestException{
		Pattern pattern = Pattern.compile("晚安");
		Matcher matcher = pattern.matcher(content);
		if(matcher.find()) {
			auth.using(Responses.class).responseAdd((long)plurk_id, "主人晚安w",Qualifier.SAYS);
			
		}
		pattern = Pattern.compile("午安");
		matcher = pattern.matcher(content);
		if(matcher.find()) {
			auth.using(Responses.class).responseAdd((long)plurk_id, "主人午安w",Qualifier.SAYS);
			
		}
		pattern = Pattern.compile("早安");
		matcher = pattern.matcher(content);
		if(matcher.find()) {
			auth.using(Responses.class).responseAdd((long)plurk_id, "主人早安w",Qualifier.SAYS);
			
		}
		pattern = Pattern.compile("QQ");
		matcher = pattern.matcher(content);
		if(matcher.find()) {
			auth.using(Responses.class).responseAdd((long)plurk_id, "[拍拍]",Qualifier.SAYS);
			
		}
	}
}
