package com.google.jplurk_oauth.vivi;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.jplurk_oauth.Qualifier;
import com.google.jplurk_oauth.module.Responses;
import com.google.jplurk_oauth.skeleton.PlurkOAuth;
import com.google.jplurk_oauth.skeleton.RequestException;

public class GuessNumber extends Thread{
	private int plurk_id,owner_id;
	private PlurkOAuth auth;
	boolean isEnding;
	private int[] answer;
	private String antiBlood;
	public GuessNumber(PlurkOAuth auth,int plurk_id,int owner_id){
		this.auth = auth;
		this.plurk_id = plurk_id;
		this.owner_id = owner_id;
		isEnding = false;
		genAnswer();
	}
	private void genAnswer(){
		answer = new int[5];
		int[] use = new int[10];
		int cnt,pos;
		Random random = new Random(System.currentTimeMillis());
		for(int i = 0;i < 10;i++)use[i] = 0;
		for(int i = 0;i < 4;i++){
			pos = random.nextInt()%10+1;
			cnt = 0;
			int j=0;
			while(true){
				if(use[j] == 1){j++;if(j==0)j=0;continue;}
				if(use[j] == 0 && cnt > pos){
					answer[i] = j;
					use[j] = 1;
					break;
				}
				j++;if(j == 10)j = 0;
				cnt++;
			}
		}
		System.out.println("Answer is:");
		for(int i=0;i<4;i++)
			System.out.println(answer[i]);
	}
	public void run(){	
		String content;
		JSONObject obj;
		JSONArray array;
		int lastLength=0,id;
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
					Pattern pattern = Pattern.compile("猜( *?)[0-9][0-9][0-9][0-9]");
					Matcher matcher = pattern.matcher(content);
					if(matcher.find()) {
						//auth.using(Responses.class).responseAdd((long)plurk_id, "開始玩囉w",Qualifier.SAYS);
						if(checker(matcher.group())== false){
							auth.using(Responses.class).responseAdd((long)plurk_id, "格式有誤QAO",Qualifier.SAYS);
						}
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
	private boolean checker(String str) throws RequestException{
		String sp[] = str.split("( +?)");
		int[] digit = new int[5];
		int guessNumber;
		int Acount=0,Bcount=0;
		String res;
		guessNumber = Integer.parseInt(sp[1]);
		//System.out.println(guessNumber);
		for(int i=0;i<4;i++){
			digit[i] = guessNumber % 10;
			guessNumber /= 10;
		}
		for(int i=0;i<4;i++){
			System.out.println(digit[i]);
			for(int j=0;j<4;j++){
				if(i == j)continue;
				if(digit[i] == digit[j]){
					return false;
				}
			}
		}
		for(int i=0;i<4;i++){
			for(int j=0;j<4;j++){
				if(i == j && digit[i] == answer[j]){
					Acount++;
				}else if(digit[i] == answer[j]){
					Bcount++;
				}
			}
		}
		System.out.println(Acount+" "+Bcount);
		Random random = new Random(System.currentTimeMillis());
		int ran=random.nextInt()%12;
		antiBlood = "";
		for(int i=0;i<ran;i++){
			antiBlood += "喵";
		}
		if(Acount == 4){
			isEnding = true;
			auth.using(Responses.class).responseAdd((long)plurk_id, "4A0B 恭喜答對囉www",Qualifier.SAYS);
		}else{
			res = Acount + "A" + Bcount + "B" + antiBlood;
			auth.using(Responses.class).responseAdd((long)plurk_id, res ,Qualifier.SAYS);
		}
		
		return true;
	}
}

