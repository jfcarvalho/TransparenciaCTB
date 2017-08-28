package com.ctb;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.ctb.contratos.controller.ContratosInterface;
import com.ctb.contratos.model.Contratado;
import com.ctb.contratos.model.Contrato;
import com.ctb.contratos.model.Lancamento;
import com.ctb.contratos.model.Usuario;
import com.ctb.contratos.repository.Contratados;
import com.ctb.contratos.repository.Contratos;
import com.ctb.contratos.repository.Usuarios;
import com.ctb.contratos.util.ContratosAVencer;
import com.ctb.security.AppUserDetailsService;

@Component
public class Mailer {
	
	@Autowired
	private JavaMailSender mailSender;
	private static Logger logger = LoggerFactory.getLogger(Mailer.class);
	
	@Autowired
	private TemplateEngine thymeleaf;
	
	@Autowired
	private Contratos contratos;
	
	@Autowired
	private Contratados contratados;
	
	@Autowired
	private Usuarios usuarios;
	
	@Async
	public void enviar_vencimento_gestor(List<Contrato> lc, String Email, Integer dias)
	{
		
		
		Context context = new Context();
		context.setVariable("vencimentoXdias", lc);
		context.setVariable("dias", dias);
		String email = thymeleaf.process("mail/ContratosAVencer", context);
		try {
			MimeMessage mimeMessage = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
			helper.setFrom("suporte.ctb1210@ctb.ba.gov.br");
			helper.setTo(Email);
			helper.setSubject("Aviso de Vencimento de Contratos");
			helper.setText(email, true);
			mailSender.send(mimeMessage);
		} catch(MessagingException e) {
			logger.error("Erro enviando e-mail", e);
		}
		thymeleaf.process("mail/ContratosAVencer", context);
	}
	
	@Async
	public void enviar_lancamento_gestor(Lancamento lc, String Email)
	{
		
		//Usuario u = usuarios.findByEmail(AppUserDetailsService.cusuario.getUsername());
		Object usuarioLogado = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Usuario u = new Usuario();
		u = usuarios.findByEmail(((UserDetails)usuarioLogado).getUsername());
		Context context = new Context();
		context.setVariable("lancamento", lc);
		context.setVariable("contrato", lc.getContrato());
		String email = thymeleaf.process("mail/AvisoLancamento", context);
		try {
			MimeMessage mimeMessage = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
			helper.setFrom("suporte.ctb1210@ctb.ba.gov.br");
			helper.setTo(Email);
			helper.setSubject("SGC - Atualização de Contrato");
			helper.setText(email, true);
			mailSender.send(mimeMessage);
		} catch(MessagingException e) {
			logger.error("Erro enviando e-mail", e);
		}
		thymeleaf.process("mail/AvisoLancamento", context);
	}
	
	@Async
	public void enviar_pagamento_contrato(Lancamento lc, Processo p, String Email)
	{
		
	//	Usuario u = usuarios.findByEmail(AppUserDetailsService.cusuario.getUsername());
		Object usuarioLogado = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Usuario u = new Usuario();
		u = usuarios.findByEmail(((UserDetails)usuarioLogado).getUsername());
		Context context = new Context();
		context.setVariable("lancamento", lc);
		context.setVariable("contrato", lc.getContrato());
		context.setVariable("processo", p);
		
		String email = thymeleaf.process("mail/AvisoPagamento", context);
		try {
			MimeMessage mimeMessage = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
			helper.setFrom("suporte.ctb1210@ctb.ba.gov.br");
			helper.setTo(Email);
			helper.setSubject("SGC - Aviso de Pagamento");
			helper.setText(email, true);
			mailSender.send(mimeMessage);
		} catch(MessagingException e) {
			logger.error("Erro enviando e-mail", e);
		}
		thymeleaf.process("mail/AvisoPagamento", context);
	}
	
	
	
public Days numeroDiasVencimentoContrato(Contrato c)
{
	DateTime inicio = new DateTime();
	DateTime fim = new DateTime(c.getData_vencimento());
	Days d = Days.daysBetween(inicio, fim);
	return d;	
}
	
}
