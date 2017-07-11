package com.ctb.contratos.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ctb.Processo;
import com.ctb.TipoProcesso;
import com.ctb.contratos.model.Contratado;
import com.ctb.contratos.model.Contrato;
import com.ctb.contratos.model.Lancamento;
import com.ctb.contratos.model.Recurso;
import com.ctb.contratos.model.Usuario;
import com.ctb.contratos.repository.Contratados;
import com.ctb.contratos.repository.Lancamentos;
import com.ctb.contratos.repository.Processos;
import com.ctb.contratos.repository.Usuarios;
import com.ctb.security.AppUserDetailsService;

@Controller
@RequestMapping("/transparenciactb/processos")
public class ProcessoController {
	private static final String CADASTRO_VIEW = "/cadastro/CadastroProcesso";
	private static final String PROCESSO_VIEW = "/visualizacao/VisualizarProcesso";

	@Autowired
	private Processos processos;
	
	@Autowired
	private Usuarios usuarios;
	
	@Autowired
	private Lancamentos lancamentos;
	@PersistenceContext
	private EntityManager manager;
	
	
	@RequestMapping("/novo")
	public ModelAndView novo()
	{
		ModelAndView mv = new ModelAndView(CADASTRO_VIEW);
		mv.addObject(new Processo());
		
		//mv.addObject("todosNiveisUsuario", Nivel.values());
		return mv;
	}
	
	@RequestMapping(method= RequestMethod.GET)
	public ModelAndView pesquisar(String busca, String numero, String data, @PageableDefault(size=10) Pageable pageable) throws ParseException
	{
		ModelAndView mv = new ModelAndView("/pesquisa/PesquisaProcessos");
		//mv.addObject("usuarios", todosUsuarios);
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Processo.class);
		int primeiroRegistro = pageable.getPageNumber()*pageable.getPageSize();	
		criteria.setFirstResult(primeiroRegistro);
		criteria.setMaxResults(pageable.getPageSize());
		criteria.addOrder(Order.desc("id_processo"));
		if(numero != null) {
			if(busca != null && numero.equals("on")) {
				//List<Lancamento> todosLancamentos = lancamentosQ.porNota(busca, us.getId_usuario());
				//Usuario us = usuarios.findOne(id_usuario);
				List<Processo> procs = processos.findAll();
				List<Processo> processos_limitados = new ArrayList<Processo>();
					for(Processo p: procs)
					{
						
						if(p.getNumero_processo() != null) {
							if(p.getNumero_processo().contains(busca))
								
							{
								processos_limitados.add(p);
							}
						}
					}
				
				mv.addObject("buscaProcessos", processos_limitados);
				
				
				return mv;
			}
		}
    	
		else if(data != null) {
			if(busca != null && data.equals("on")) {
				List<Processo> procs = processos.findAll();
				List<Processo> processos_limitados = new ArrayList<Processo>();
					for(Processo p: procs)
					{
				
						if(p.getData_abertura() != null) {
							if(p.getData_abertura().toString().contains(busca))
								
							{
								processos_limitados.add(p);
							}
						}
					}
				
				mv.addObject("buscaProcessos", processos_limitados);
				return mv;
			}
		}
		List<Processo> processosOrdenados = criteria.list();
		Page<Processo> procs = new PageImpl<Processo>(processosOrdenados, pageable, processosOrdenados.size());
		
		 mv.addObject("buscaProcessos", procs);
    
	return mv;
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String salvar(@Validated Processo processo, RedirectAttributes attributes)
	{
		ModelAndView mv = new ModelAndView(CADASTRO_VIEW);
		Usuario user = usuarios.findByEmail(AppUserDetailsService.cusuario.getUsername());
		processo.setUsuario(user);
		processos.save(processo);		
		attributes.addFlashAttribute("mensagem", "Processo salvo com sucesso!");	
		return "redirect:/transparenciactb/processos/novo";
	}
	
	@ModelAttribute("todosProcessos")
	public List<Processo> todosProcessos()
	{
		return processos.findAll();
	}
	@RequestMapping("{id_processo}")
	public ModelAndView edicao(@PathVariable("id_processo") Processo processo)
	{
		//System.out.println(">>>>>>> codigo recebido: " + id_usuario);
		//Usuario usuario = usuarios.findOne(id_usuario);
		
		ModelAndView mv = new ModelAndView(CADASTRO_VIEW);
		mv.addObject("processo", processo);
		mv.addObject(processo);
		return mv;
	}
	
	@RequestMapping(value="/remove/{id_processo}"/*, method=RequestMethod.DELETE*/)
	public String excluir(@PathVariable Integer id_processo, RedirectAttributes attributes)
	{
		
		attributes.addFlashAttribute("mensagem", "Processo removido com sucesso com sucesso!");
		
		Lancamento lancamento = buscarLancamentoProcesso(id_processo);
		Processo processo = processos.findOne(id_processo);
		if(lancamento != null) {
			lancamento.setProcesso(null);
			lancamentos.save(lancamento);
		}
		processos.delete(processo);
		
		
		return "redirect:/transparenciactb/processos/";	
	
	}
	
	public Lancamento buscarLancamentoProcesso(Integer numero_processo)
	{
		List<Lancamento> lancamentosABuscar = lancamentos.findAll();
		Iterator it = lancamentosABuscar.iterator();
		
		while(it.hasNext())
		{
			Lancamento obj = (Lancamento) it.next();
			//System.out.println(obj.getProcesso().getId_processo());
			if(obj.getProcesso().getId_processo() == numero_processo)
				return obj;
			
		}
		return null;
	}
	
	@RequestMapping(value="/visualizar/{id_processo}")
	public ModelAndView visualizar_usuario(@PathVariable Integer id_processo, RedirectAttributes attributes)
	{
		Processo processo = processos.findOne(id_processo);
		ModelAndView mv = new ModelAndView(PROCESSO_VIEW);
		
		mv.addObject("processo_numero", processo.getNumero_processo());
		mv.addObject("processo_tipo_processo", processo.getTipo_processo().getTipo());
		mv.addObject("processo_data_abertura", processo.getData_abertura());
		mv.addObject("processo_pago", processo.getPago());
		mv.addObject("processo_data_pagamento", processo.getData_pagamento());
		mv.addObject("processo_numeroci", processo.getNumero_ci());
		mv.addObject("processo_lancamento", processo.getLancamento());
		mv.addObject("processo_contrato", processo.getContrato());
		/*
		mv.addObject("usuario_telefone", usuario.getTelefone());
		mv.addObject("usuario_contratos_geridos", usuario.getContratosGeridos());
		mv.addObject("usuario_contratos_fiscalizados", usuario.getContratosFiscalizados());
		//usuarios.delete(id_usuario);
		*/
		return mv;	
	
	}
	
	
	@ModelAttribute("permissao")
	public boolean temPermissao() {
		return AppUserDetailsService.cusuario.getAuthorities().toString().contains("ROLE_CADASTRAR_CONTRATO");
	}
	
	@ModelAttribute("todosTiposProcessos")
	public List<TipoProcesso> todosTiposProcessos() {
		return Arrays.asList(TipoProcesso.values());
}
}

