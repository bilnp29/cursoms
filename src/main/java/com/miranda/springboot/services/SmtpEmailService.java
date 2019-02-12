package com.miranda.springboot.services;

import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

public class SmtpEmailService extends AbstractEmailService{

	private static final Logger LOG = LoggerFactory.getLogger(SmtpEmailService.class);
	
	@Autowired
	private MailSender mailSender;
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	@Override
	public void sendEmail(SimpleMailMessage msg) {
		LOG.info("Simulando o envio de email...");
		mailSender.send(msg);
		LOG.info("Email Enviado...");
	}

	@Override
	public void sendHtmlEmail(MimeMessage msg) {
		LOG.info("Simulando o envio de html...");
		javaMailSender.send(msg);
		LOG.info("Email Enviado...");
	}

}