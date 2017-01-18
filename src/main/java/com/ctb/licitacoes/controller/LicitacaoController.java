package com.ctb.licitacoes.controller;

import java.text.ParseException;
import java.util.Arrays;
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
import com.ctb.contratos.model.Contrato;
import com.ctb.contratos.repository.Licitacoes;
import com.ctb.contratos.repository.Usuarios;
import com.ctb.licitacoes.model.Licitacao;
import com.ctb.licitacoes.model.Modalidade;

@Controller
@RequestMapping("/transparenciactb/licitacoes")

public class LicitacaoController {

	private static final String LICITACAO_VIEW = "/cadastro/CadastroLicitacao"; 
	@Autowired
	private Licitacoes licitacoes;
	
	@RequestMapping("/novo")
	public ModelAndView novo()
	{
		ModelAndView mv = new ModelAndView(LICITACAO_VIEW);		
		mv.addObject(new Licitacao());
		return mv;
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String salvar(Licitacao licitacao, RedirectAttributes attributes)
	{
		ModelAndView mv = new ModelAndView(LICITACAO_VIEW);
		licitacoes.save(licitacao);		
		attributes.addFlashAttribute("mensagem", "Licitação salva com sucesso!");	
		return "redirect:/transparenciactb/licitacoes/novo";
	}
	
	@RequestMapping(method= RequestMethod.GET)
	public ModelAndView pesquisar(String busca, String nome, String setor) throws ParseException
	{
		ModelAndView mv = new ModelAndView("/pesquisa/PesquisaLicitacoes");
	//	mv.addObject("licitacoes", todasLicitacoes);
  
	return mv;
	}

	@ModelAttribute("todasLicitacoes")
	public List<Licitacao> todosLicitacoes()
	{
		return licitacoes.findAll();
	}
	
	@ModelAttribute("todasModalidades")
	public List<Modalidade> todasModalidades() {
		return Arrays.asList(Modalidade.values());
}
	
}
