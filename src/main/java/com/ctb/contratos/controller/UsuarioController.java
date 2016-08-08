package com.ctb.contratos.controller;

import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ctb.contratos.model.Contrato;
import com.ctb.contratos.model.Usuario;
import com.ctb.contratos.repository.Usuarios;

@Controller
@RequestMapping("/transparenciactb/usuarios")
public class UsuarioController {
	private static final String CADASTRO_VIEW = "/cadastro/CadastroUsuario"; 
	@Autowired
	private Usuarios usuarios;
	
	@RequestMapping("/novo")
	public ModelAndView novo()
	{
		ModelAndView mv = new ModelAndView(CADASTRO_VIEW);
		mv.addObject(new Usuario());
		
		//mv.addObject("todosNiveisUsuario", Nivel.values());
		return mv;
	}
	@RequestMapping(method = RequestMethod.POST)
	public String salvar(@Validated Usuario usuario, RedirectAttributes attributes)
	{
		ModelAndView mv = new ModelAndView(CADASTRO_VIEW);
		usuarios.save(usuario);		
		attributes.addFlashAttribute("mensagem", "Usuário salvo com sucesso!");	
		return "redirect:/transparenciactb/usuarios/novo";
	}
	
	@RequestMapping(method= RequestMethod.GET)
	public ModelAndView pesquisar(String busca, String nome, String setor) throws ParseException
	{
		ModelAndView mv = new ModelAndView("/pesquisa/PesquisaUsuarios");
		//mv.addObject("usuarios", todosUsuarios);
    
	return mv;
	}
	@RequestMapping("{id_usuario}")
	public ModelAndView edicao(@PathVariable("id_usuario") Usuario usuario)
	{
		//System.out.println(">>>>>>> codigo recebido: " + id_usuario);
		//Usuario usuario = usuarios.findOne(id_usuario);
		
		ModelAndView mv = new ModelAndView(CADASTRO_VIEW);
		mv.addObject("su", usuario);
		mv.addObject(usuario);
		return mv;
	}
	@RequestMapping(value="{id_usuario}", method=RequestMethod.DELETE)
	public String excluir(@PathVariable Integer id_usuario, RedirectAttributes attributes)
	{
		attributes.addFlashAttribute("mensagem", "Usuário excluido com sucesso com sucesso!");	
		//usuarios.delete(id_usuario);
		return "redirect:/transparenciactb/usuarios";	
	
	}
	@ModelAttribute("todosUsuarios")
	public List<Usuario> todosUsuarios()
	{
		return usuarios.findAll();
	}
	
}
