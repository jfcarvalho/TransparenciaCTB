package com.ctb.contratos.controller;

import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ctb.Processo;
import com.ctb.contratos.model.Contratado;
import com.ctb.contratos.model.Usuario;
import com.ctb.contratos.repository.Contratados;
import com.ctb.contratos.repository.Processos;

@Controller
@RequestMapping("/transparenciactb/processos")
public class ProcessoController {
	private static final String CADASTRO_VIEW = "/cadastro/CadastroProcesso";

	@Autowired
	private Processos processos;
	
	@RequestMapping("/novo")
	public ModelAndView novo()
	{
		ModelAndView mv = new ModelAndView(CADASTRO_VIEW);
		mv.addObject(new Processo());
		
		//mv.addObject("todosNiveisUsuario", Nivel.values());
		return mv;
	}
	
	@RequestMapping(method= RequestMethod.GET)
	public ModelAndView pesquisar(String busca, String nome, String setor) throws ParseException
	{
		ModelAndView mv = new ModelAndView("/pesquisa/PesquisaProcessos");
		//mv.addObject("usuarios", todosUsuarios);
    
	return mv;
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String salvar(@Validated Processo processo, RedirectAttributes attributes)
	{
		ModelAndView mv = new ModelAndView(CADASTRO_VIEW);
		processos.save(processo);		
		attributes.addFlashAttribute("mensagem", "Empresa contratada salva com sucesso!");	
		return "redirect:/transparenciactb/processos/novo";
	}
	
	@ModelAttribute("todosProcessos")
	public List<Processo> todosProcessos()
	{
		return processos.findAll();
	}
}
