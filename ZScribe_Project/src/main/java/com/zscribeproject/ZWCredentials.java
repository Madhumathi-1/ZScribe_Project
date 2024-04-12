package com.zscribeproject;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import java.util.Timer;
import org.json.JSONObject;

public class ZWCredentials extends Timer {
    protected static String refreshTkn;
    protected static String accessTkn;
    private String clientID = "1000.NFY4BZ041QWPOAMU4OKKP6MF5VIJ5S";
    private String clientSecret = "a190b24e0e8dfaf36a4febfbdb8555fa3c33d0e40b";
    private String code;

    protected Boolean isFirstToken = false;
	private Scanner scanner;

    protected String getCode() {
        return code;
    }

    private String getClientSecret() {
        return clientSecret;
    }

    private String getClientID() {
        return clientID;
    }

    public void setAccessTkn(String accessToken) {
        ZWCredentials.accessTkn = accessToken;
    }

    private void setRefToken(String refreshToken) {
        ZWCredentials.refreshTkn = refreshToken;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String createAccessToken(String code) {
        String redirectUrl = "http://localhost:8080/ZScribe_Project/WriterCode";
        try {
            URL url = new URL(String.format("https://accounts.zoho.com/oauth/v2/token?client_id=%s&grant_type=authorization_code&client_secret=%s&redirect_uri=%s&code=%s", getClientID(), getClientSecret(), redirectUrl, code));
            HttpURLConnection httpsURLConnection = (HttpURLConnection) url.openConnection();
            httpsURLConnection.setRequestMethod("POST");
            scanner = new Scanner(httpsURLConnection.getInputStream());
            String res = scanner.nextLine();
            System.out.println("response: " + res);

            JSONObject jsonObj = new JSONObject(res);
            setAccessTkn(jsonObj.getString("access_token"));
            setRefToken(jsonObj.getString("refresh_token"));
            System.out.println("Access Token: " + jsonObj.getString("access_token"));
            System.out.println("Refresh Token: " + jsonObj.getString("refresh_token"));
        } catch (Exception ee) {
            System.out.println("Error while creating access token....");
            ee.printStackTrace();
        }
        return accessTkn;
    }
}
