package com.me.FinalProject.Util;


import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.JOptionPane;

import com.me.FinalProject.Model.UserProfile;

public class SendMail {

	public void send(UserProfile user, String otp) {
		final String gmailu = "manaranbank1@gmail.com";
		final String gmailp = "Iamthebos1s1";
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
		javax.mail.Session session = javax.mail.Session.getDefaultInstance(
				props, new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(gmailu, gmailp);
					}
				});
		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("manaranbank1@gmail.com"));
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(user.getEmail()));
			message.setSubject("One Time Password - Manaran Bank");
			message.setText("Hi " + user.getFirstName() + " "
					+ user.getLastName() + ", Your OTP is: "
					+ otp);
			Transport.send(message);
		} catch (MessagingException e) {
			// throw new RuntimeException(e);
			JOptionPane.showMessageDialog(null, "cant process the request");
		}
	}

}
