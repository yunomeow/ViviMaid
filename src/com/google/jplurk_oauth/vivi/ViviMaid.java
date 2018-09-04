package com.google.jplurk_oauth.vivi;
import java.util.Properties;

import org.json.JSONException;

import com.google.jplurk_oauth.Offset;
import com.google.jplurk_oauth.module.Polling;
import com.google.jplurk_oauth.skeleton.PlurkOAuth;
import com.google.jplurk_oauth.skeleton.RequestException;
public class ViviMaid {
    public static void main(String[] args) throws RequestException, JSONException {
        Properties prop = System.getProperties();
        
        /* create oauth config */
        PlurkOAuth auth = new PlurkOAuth(
                "", "", 
                "", "");
        

        Thread thread = new PlurksClassifier(auth);
        thread.start();
    }
}
