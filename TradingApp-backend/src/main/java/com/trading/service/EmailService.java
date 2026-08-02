package com.trading.service;

import org.springframework.mail.MailException;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {
	
	private JavaMailSender javaMailSender;
	
	public void sendVerificationOtpEmail(String email,String otp) {
		
		
		try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            helper.setTo(email);
            helper.setSubject("Verification OTP");
            helper.setText(
                    "<h2>Your Verification OTP</h2>"
                    + "<p>Your OTP is: <b>" + otp + "</b></p>"
                    + "<p>This OTP is valid for 5 minutes.</p>",
                    true
            );

            javaMailSender.send(mimeMessage);

        } catch (MessagingException | MailException e) {
            throw new MailSendException("Failed to send email: " + e.getMessage());
        }
				
	}
}
