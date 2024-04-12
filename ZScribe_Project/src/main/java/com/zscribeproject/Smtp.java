package com.zscribeproject;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Smtp {
	Register register = new Register();
	public void sendMail(String recipient,String content) {
		System.out.println("Preparing to send mail....");
		Properties properties = new Properties();
		properties.put("mail.smtp.auth","true");
		properties.put("mail.smtp.starttls.enable","true");
		properties.put("mail.smtp.host","smtp.gmail.com");
		properties.put("mail.smtp.port","587");
		properties.put("mail.smtp.ssl.protocols", "TLSv1.2");
//		properties.put("mail.smtp.ssl.ciphersuites", "TLS_AES_256_GCM_SHA384");

		
		String userName = "murthymadhumathi@gmail.com";
		String password = "xpdr yzpe surv njjc";
	
		Session session = Session.getInstance(properties, new Authenticator() {
			@Override
			protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
				return new javax.mail.PasswordAuthentication(userName, password);
			}
		});
		Message message = prepareMessage(session,userName,recipient,content);
		try {
			Transport.send(message);
			System.out.println("Mail Sent successfully");
		}

		catch (Exception ee) {
			System.out.println("Error! while sending mail...");
			ee.printStackTrace();
		}
		
	}

	private  Message prepareMessage(Session session, String userName, String recipient, String content) {
		Message message = new MimeMessage(session);
		try {
			message.setFrom(new InternetAddress(userName));
			message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
			message.setSubject("---Zscribe---");
			if (message != null) {
				message.setText(content);
			} 
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return message;
		
	}
}