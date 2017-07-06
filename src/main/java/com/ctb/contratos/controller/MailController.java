package com.ctb.contratos.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ctb.Processo;

@Controller
@RequestMapping("/transparenciactb/mail")
public class MailController {
	private String MAIL_VIEW = "/mail/ContratosAVencer";
	
	@RequestMapping("/template")
	public ModelAndView novo()
	{
		ModelAndView mv = new ModelAndView(MAIL_VIEW);
		mv.addObject(new MailController());
		
		//mv.addObject("todosNiveisUsuario", Nivel.values());
		return mv;
	}
	

}
