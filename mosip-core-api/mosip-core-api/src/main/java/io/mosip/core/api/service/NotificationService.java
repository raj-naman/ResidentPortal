package io.mosip.core.api.service;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class NotificationService
{
	private JavaMailSender javaMailSender;
	
	@Autowired
	public NotificationService(JavaMailSender javaMailSender) {
		this.javaMailSender = javaMailSender;
	}
	
	public void sendNotification(long vin,String email) throws MailException{
		SimpleMailMessage mail = new SimpleMailMessage();
		
		mail.setTo(email);
		mail.setFrom("rishabhs201195@gmail.com");
		mail.setSubject("VIN Generated");
		mail.setText("Your Virtual Identity Number is  "+vin+"  which valid for next 24 hours :) ");
		
		javaMailSender.send(mail);
	
	}
	public void sendOTP(long vin,String email,String otp) throws MailException{
		SimpleMailMessage mail = new SimpleMailMessage();
		
		mail.setTo(email);
		mail.setFrom("rishabhs201195@gmail.com");
		mail.setSubject("VIN Generated OTP :: "+otp);
		mail.setText("Your OTP For Virtual Identity Number is  "+otp+"  which valid for next 10 Minutes :) ");
		
		javaMailSender.send(mail);
	
	}


}