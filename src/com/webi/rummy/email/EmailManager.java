package com.webi.rummy.email;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.webi.games.rummy.entity.MailManagerEntity;

public class EmailManager {
	
	public static void sendGameInviteEmail(String inviterEmailId, String[] inviteesMailIds, long gameId , final MailManagerEntity mailManagerEntity)
	{
		//Test environment related logic
		 Properties props = new Properties();
		 props.put("mail.smtp.starttls.enable", "true");
	        props.put("mail.smtp.auth", "true");
	        props.put("mail.smtp.host", "smtp.gmail.com");
	        props.put("mail.smtp.port", "587");
	     Session session = Session.getInstance(props,  new javax.mail.Authenticator() {
	            protected PasswordAuthentication getPasswordAuthentication() {
	            	
	            	//Get UserName and Password from DB to hide it when we upload the code to GITHUB
	            	//For testing you can use your gmailId and password here
	                return new PasswordAuthentication(mailManagerEntity.getUserId(), mailManagerEntity.getPassword());
	            }
	          });

		 Message msg = new MimeMessage(session);
		 
		 String GAME_INVITE_MAIL_BODY = "Welcome To The Fun  World\n\n\n.\t You have been invited to play Rummy Card Game by "+inviterEmailId+".\n"+
				 	"Please launch http://localhost:8080/games/rummy/home to view the invitation.\n";
		 
		 try {
			msg.setFrom(new InternetAddress(inviterEmailId, "Webi.com Admin"));
			boolean validEmailExists = false;
			InternetAddress[] inviteesInternetAddress = new InternetAddress[inviteesMailIds.length]; 
			for ( int index=0; index < inviteesMailIds.length ; index++) {
				if ( inviteesMailIds[index].contains("@gmail.com") == true ) {
					validEmailExists = true;
					inviteesInternetAddress[index] = new InternetAddress(inviteesMailIds[index], "Hello Friend");
				}
			}
			if ( validEmailExists == false ) {
				//No need to send mail
				return ;
			}
			
			msg.addRecipients(Message.RecipientType.TO,inviteesInternetAddress);
			
	         msg.setSubject("Rummy Card Game Invitation");
	         msg.setText(GAME_INVITE_MAIL_BODY);
	         Transport.send(msg);
		 } catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (MessagingException e) {
				e.printStackTrace();
			}
	}
}
