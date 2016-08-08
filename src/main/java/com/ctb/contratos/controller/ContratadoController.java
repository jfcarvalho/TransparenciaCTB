package com.ctb.contratos.controller;

import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ctb.contratos.model.Contratado;
import com.ctb.contratos.model.Contrato;
import com.ctb.contratos.repository.Contratados;
import com.ctb.contratos.repository.Usuarios;

@Controller
@RequestMapping("/transparenciactb/contratados")

public class ContratadoController {
	private static final String CADASTRO_VIEW = "/cadastro/CadastroContratado"; 

	@Autowired
	private Contratados contratados;
	
	@RequestMapping("/novo")
	public ModelAndView novo()
	{
		ModelAndView mv = new ModelAndView(CADASTRO_VIEW);
		mv.addObject(new Contratado());
		
		//mv.addObject("todosNiveisUsuario", Nivel.values());
		return mv;
	}
	@RequestMapping(method = RequestMethod.POST)
	public String salvar(@Validated Contratado contratado, RedirectAttributes attributes)
	{
		ModelAndView mv = new ModelAndView(CADASTRO_VIEW);
		contratados.save(contratado);		
		attributes.addFlashAttribute("mensagem", "Empresa contratada salva com sucesso!");	
		return "redirect:/transparenciactb/contratados/novo";
	}
	
	@RequestMapping(method= RequestMethod.GET)
	public ModelAndView pesquisar(String busca, String nome, String setor) throws ParseException
	{
		ModelAndView mv = new ModelAndView("/pesquisa/PesquisaContratados");
		//mv.addObject("usuarios", todosUsuarios);
    
	return mv;
	}
	@RequestMapping("{id_contratado}")
	public ModelAndView edicao(@PathVariable("id_contratado") Contratado contratado)
	{
		//System.out.println(">>>>>>> codigo recebido: " + id_usuario);
		//Usuario usuario = usuarios.findOne(id_usuario);
		
		ModelAndView mv = new ModelAndView(CADASTRO_VIEW);
		mv.addObject("su", contratado);
		mv.addObject(contratado);
		return mv;
	}
	@RequestMapping(value="{id_usuario}", method=RequestMethod.DELETE)
	public String excluir(@PathVariable Integer id_usuario, RedirectAttributes attributes)
	{
		attributes.addFlashAttribute("mensagem", "Usuário excluido com sucesso com sucesso!");	
		//usuarios.delete(id_usuario);
		return "redirect:/transparenciactb/usuarios";	
	
	}
	
	@ModelAttribute("todosContratados")
	public List<Contratado> todosContratados()
	{
		return contratados.findAll();
	}
}
