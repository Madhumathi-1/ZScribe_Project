package com.zscribeproject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import org.json.JSONArray;
import org.json.JSONObject;

public class GetBlogId {
    public String blogId;
    
    public String getBlogId() {
        try {
            URL url = new URL("https://www.googleapis.com/blogger/v3/users/self/blogs");
            HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url.openConnection();
            httpsURLConnection.setRequestMethod("GET");
            httpsURLConnection.setRequestProperty("Authorization", "Bearer " + BloggerCredentials.accessTkn);

            int responseCode = httpsURLConnection.getResponseCode();
            if (responseCode == 200) { // Successful response
                BufferedReader reader = new BufferedReader(new InputStreamReader(httpsURLConnection.getInputStream()));
                StringBuffer response = new StringBuffer();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();
                
                JSONObject jsonObject = new JSONObject(response.toString());
                JSONArray blogArray = jsonObject.getJSONArray("items");
                if (blogArray.length() > 0) {
                    blogId = blogArray.getJSONObject(0).getString("id");
                    System.out.println("Blog ID: " + blogId);
                } else {
                    System.err.println("No blogs found in the response.");
                }
            } else {
               System.out.println("Error!" + responseCode);
            }
        } catch (Exception ee) {
           System.out.println("Error! while getting code...");
           ee.printStackTrace();
        }
        return blogId;
    }
}