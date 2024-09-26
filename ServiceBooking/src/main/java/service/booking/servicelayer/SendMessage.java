package service.booking.servicelayer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import service.booking.Registration.UserEmailDto;

public class SendMessage {

	@Autowired
	private JavaMailSenderImpl javaMailSenderImpl;
	
	public void send(UserEmailDto user)
	{
		SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
		
		simpleMailMessage.setTo(user.getTo());
		simpleMailMessage.setSubject(user.getSubject());
		simpleMailMessage.setText(user.getBody());
		System.out.println("Sent");
		
		javaMailSenderImpl.send(simpleMailMessage);
		
	}

}
