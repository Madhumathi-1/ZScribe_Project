package com.zscribeproject;

import java.io.InputStreamReader;
import java.net.URL;
import java.util.Scanner;
import java.util.Timer;

import javax.net.ssl.HttpsURLConnection;

import org.json.JSONObject;

public class BloggerCredentials extends Timer {
    private static String refreshTkn;
    protected static String accessTkn;
    private String clientID = "687327551965-odbgqkv4i26bcnk6jelojuvliqc677d6.apps.googleusercontent.com";
    private String clientSecret = "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";
    private String code;
    protected Boolean isFirstToken = false;
    private Scanner scanner;

    // Getter methods
    protected String getCode() {
        return code;
    }

    private String getClientSecret() {
        return clientSecret;
    }

    private String getClientID() {
        return clientID;
    }

    // Setter methods
    public void setAccessTkn(String accessToken) {
        BloggerCredentials.accessTkn = accessToken;
    }

    private void setRefToken(String refreshToken) {
        BloggerCredentials.refreshTkn = refreshToken;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void createAccessToken() {
        String redirectUrl = "http://localhost:8080/ZScribe_Project/GetBloggerCode";
        try {
            URL url = new URL("https://oauth2.googleapis.com/token?client_id=" + getClientID()
                    + "&grant_type=authorization_code&client_secret=" + getClientSecret() + "&redirect_uri="
                    + redirectUrl + "&code=" + getCode());
            HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url.openConnection();
            httpsURLConnection.setRequestMethod("POST");
            httpsURLConnection.setUseCaches(false);
            httpsURLConnection.setRequestProperty("Content-Length", "0");
            httpsURLConnection.setRequestProperty("User-Agent", "PostmanRuntime/7.31.1");
            httpsURLConnection.setRequestProperty("Connection", "keep-alive");
            httpsURLConnection.setDoOutput(true);

            scanner = new Scanner(new InputStreamReader(httpsURLConnection.getInputStream()));

            StringBuilder credentials = new StringBuilder();
            while (scanner.hasNext()) {
                credentials.append(scanner.next().trim());
            }

            System.out.println("Credentials: " + credentials);
            JSONObject jsonObj = new JSONObject(credentials.toString());
            System.out.println(jsonObj);
            setAccessTkn(jsonObj.getString("access_token"));
            setRefToken(jsonObj.getString("refresh_token"));
            System.out.println(accessTkn);
            System.out.println(refreshTkn);
        } catch (Exception ee) {
            System.out.println("Error while creating access token...");
            ee.printStackTrace();
        }
    }
}

//Formatted the code properly with indentation.
//Moved getter and setter methods together for better readability.
//Renamed setRef_token method to follow Java naming conventions (setRefToken).
//Used StringBuilder instead of concatenating strings inside a loop.
//Added comments for better understanding.
//Removed unnecessary commented-out code.
