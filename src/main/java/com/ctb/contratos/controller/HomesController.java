package com.ctb.contratos.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.ctb.contratos.model.Contrato;
import com.ctb.contratos.model.Lancamento;
import com.ctb.contratos.model.Usuario;
import com.ctb.contratos.repository.Contratos;
import com.ctb.contratos.repository.Usuarios;
import com.ctb.security.AppUserDetailsService;

@Controller
@RequestMapping("/transparenciactb/")
public class HomesController {
	private String HOME_VIEW = "/home/PaginaInicial";
	
	@Autowired
	private Usuarios usuarios;
	
	@Autowired
	private Contratos contratos;
	
	@RequestMapping(method=RequestMethod.GET)
	public ModelAndView index()
	{
		ModelAndView mv = new ModelAndView(HOME_VIEW);		
		mv.addObject(new HomesController());
		return mv;
		//	System.out.println(request.getServletContext());
	//return "index";
	}
	
	@ModelAttribute("permissao")
	public boolean temPermissao() {
		return AppUserDetailsService.cusuario.getAuthorities().toString().contains("ROLE_CADASTRAR_CONTRATO");
	}
	
	@ModelAttribute("contratosgeridos")
	public List<Contrato> contratosGeridos()
	{
		List<Contrato> todosContratos = contratos.findAll();
		List<Contrato> contratosGeridos = new ArrayList<Contrato>();
		Usuario gestor = usuarios.findByEmail(AppUserDetailsService.cusuario.getUsername());
		Iterator it = todosContratos.iterator();
	
		while(it.hasNext())
		{
			Contrato obj = (Contrato) it.next();
			
			if(obj.getGestor().getId_usuario() == gestor.getId_usuario()) {
				contratosGeridos.add(obj);
			}
		}
		
	    
		return contratosGeridos;
	
	}
	
	@ModelAttribute("ultimoslancamentos")
	public List<Lancamento> ultimosLancamentos()
	{
		List<Contrato> contratosGeridos = contratosGeridos();
		List<Lancamento> lancamentos = new ArrayList<Lancamento>();
		Iterator it = contratosGeridos.iterator();
	
		while(it.hasNext())
		{
			Contrato obj = (Contrato) it.next();
			List<Lancamento> laux = obj.getLancamentos();
			Iterator it2 = laux.iterator();
			while(it2.hasNext())
			{
				Lancamento l = (Lancamento) it2.next();
				
				lancamentos.add(l);
			}
			
			
		}
		Comparator<Lancamento> cmp = new Comparator<Lancamento>() {
	        public int compare(Lancamento l1, Lancamento l2) {
	          return l2.getData().compareTo(l1.getData());
	        }
	    
		};
		Collections.sort(lancamentos, cmp);
		
		List<Lancamento> novaListaLimitada = new ArrayList<Lancamento>();
		int contador = 0; 
		for (Lancamento l : lancamentos)
		{
			if(contador < 10 ) {
				novaListaLimitada.add(l);
				contador++;
			}
			else {break;}
		}
		
		
		return novaListaLimitada;
	
	}
	
	
	@ModelAttribute("n_contratosgeridos")
	public int ncontratosGeridos()
	{
		List<Contrato> todosContratos = contratos.findAll();
		Usuario gestor = usuarios.findByEmail(AppUserDetailsService.cusuario.getUsername());
		int n_geridos = 0;
		Iterator it = todosContratos.iterator();
	
		while(it.hasNext())
		{
			Contrato obj = (Contrato) it.next();
			
			if(obj.getGestor().getId_usuario() == gestor.getId_usuario()) {
				n_geridos++;
			}
		}
		return n_geridos;
	
	}
	
	@ModelAttribute("gestor")
	public Usuario gestor()
	{
		 Usuario gestor = usuarios.findByEmail(AppUserDetailsService.cusuario.getUsername());
		 return gestor;
	}
	
	
}
