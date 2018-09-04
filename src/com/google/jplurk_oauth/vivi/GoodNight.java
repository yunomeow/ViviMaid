package com.google.jplurk_oauth.vivi;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.jplurk_oauth.Ids;
import com.google.jplurk_oauth.Qualifier;
import com.google.jplurk_oauth.module.Responses;
import com.google.jplurk_oauth.module.Timeline;
import com.google.jplurk_oauth.skeleton.PlurkOAuth;
import com.google.jplurk_oauth.skeleton.RequestException;

public class GoodNight extends Thread{
	private PlurkOAuth auth;
	public GoodNight(PlurkOAuth tauth){
		auth = tauth;
	}
	public void run(){
		int plurk_id;
		String content;
		JSONObject obj;
		Ids ids;
		while(true){
	        try {
	        		obj = auth.using(Timeline.class).getUnreadPlurks().getJSONArray("plurks").getJSONObject(0);
					if(obj == null)continue;
					System.out.println(obj);
	        		plurk_id = obj.getInt("plurk_id");
					content =  obj.getString("content");
					Pattern pattern = Pattern.compile("晚安");
					Matcher matcher = pattern.matcher(content);
					if(matcher.find()) {
						auth.using(Responses.class).responseAdd((long)plurk_id, "主人晚安喔 喵w",Qualifier.SAYS);
					}
					List<Integer> idList = new ArrayList<Integer>();
					idList.add(plurk_id);
					ids = new Ids(idList);
					System.out.println(ids.formatted());
					auth.using(Timeline.class).mutePlurks(ids);

			}catch (RequestException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}catch (JSONException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}
			try {
				sleep(10000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
