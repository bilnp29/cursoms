package com.miranda.springboot.services;

import org.springframework.mail.SimpleMailMessage;

import com.miranda.springboot.domain.Pedido;

public interface EmailService {

	void sendOrderConfirmationEmail(Pedido obj);
	
	void sendEmail(SimpleMailMessage msg);
}
