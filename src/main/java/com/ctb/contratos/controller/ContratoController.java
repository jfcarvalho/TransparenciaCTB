package com.ctb.contratos.controller;

import java.text.ParseException;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ctb.Processo;
import com.ctb.contratos.model.Contratado;
import com.ctb.contratos.model.Contrato;
import com.ctb.contratos.model.Lancamento;
import com.ctb.contratos.model.Usuario;
import com.ctb.contratos.repository.Contratados;
import com.ctb.contratos.repository.Contratos;
import com.ctb.contratos.repository.Usuarios;

@Controller
@RequestMapping("/transparenciactb/contratos")

public class ContratoController {
	private static final String CADASTRO_VIEW = "/cadastro/CadastroContrato"; 

	@Autowired
	private Usuarios usuarios;
	@Autowired
	private Contratados contratados;
	@Autowired
	private Contratos contratos;

	
	@RequestMapping("/novo")
	public ModelAndView novo()
	{
		ModelAndView mv = new ModelAndView(CADASTRO_VIEW);
		mv.addObject(new Contrato());
		
		//mv.addObject("todosNiveisUsuario", Nivel.values());
		return mv;
	}
	@RequestMapping(method = RequestMethod.POST)
	public String salvar(@Validated Contrato contrato, @RequestParam Integer contrato_id_gestor, @RequestParam Integer contrato_id_fiscal, @RequestParam Integer contrato_id_contrato, RedirectAttributes attributes)
	{
		ModelAndView mv = new ModelAndView(CADASTRO_VIEW);
		Contratado empresa = contratados.findOne(contrato_id_contrato);
		Usuario gestor = usuarios.findOne(contrato_id_gestor);
		Usuario fiscal = usuarios.findOne(contrato_id_fiscal);
		contrato.setContratado(empresa);
		contrato.setFiscal(fiscal);
		contrato.setGestor(gestor);
		
		contratos.save(contrato);		
		attributes.addFlashAttribute("mensagem", "Empresa contratada salva com sucesso!");	
		return "redirect:/transparenciactb/contratos/novo";
	}
	
	@RequestMapping(method= RequestMethod.GET)
	public ModelAndView pesquisar(String busca, String nome, String setor) throws ParseException
	{
		ModelAndView mv = new ModelAndView("/pesquisa/PesquisaContratos");
		//mv.addObject("usuarios", todosUsuarios);
    
	return mv;
	}
	@RequestMapping("{id_contrato}")
	public ModelAndView edicao(@PathVariable("id_contrato") Contrato contrato)
	{
		//System.out.println(">>>>>>> codigo recebido: " + id_usuario);
		//Usuario usuario = usuarios.findOne(id_usuario);
		
		ModelAndView mv = new ModelAndView(CADASTRO_VIEW);
		mv.addObject("su", contrato);
		mv.addObject(contrato);
		return mv;
	}
	/*@RequestMapping(value="{id_contrato}")
	public String excluir(@PathVariable Integer id_contrato, RedirectAttributes attributes)
	{
		Contrato contrato = contratos.findOne(id_contrato);
		attributes.addFlashAttribute("mensagem", "Usuário excluido com sucesso com sucesso!");	
		//usuarios.delete(id_usuario);
		return "redirect:/transparenciactb/usuarios";	
	
	}
	*/
	public void desvincularContrato(Contrato contrato)
	
	{
		if(contrato.getGestor() != null)
		{
			contrato.setGestor(null);
		}
		if (contrato.getFiscal() != null)
		{
			contrato.setFiscal(null);
		}
		if(contrato.getLancamentos() != null)
		{
			
		}
	}
	/*
	public void desvincularLancamentos(List<Lancamento> lancamentos, Integer contrato)
	{
		Iterator it = lancamentos.iterator();
		
		while(it.hasNext())
		{
			Lancamento obj = (Lancamento) it.next();
			//System.out.println(obj.getProcesso().getId_processo());
			if(obj.get) 
			{
				obj.setProcesso(null);
				processos
			}
				
			
		}
	}
	*/
	
	@ModelAttribute("todosGestores")
	public List<Usuario> todosGestores()
	{
		return usuarios.findAll();
	}
	
	@ModelAttribute("todosFiscais")
	public List<Usuario> todosFiscais()
	{
		return usuarios.findAll();
	}
	@ModelAttribute("todosContratados")
	public List<Contratado> todosContratados()
	{
		return contratados.findAll();
	}
	@ModelAttribute("todosContratos")
	public List<Contrato> todosContratos()
	{
		return contratos.findAll();
	}
}
