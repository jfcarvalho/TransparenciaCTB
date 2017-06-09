package com.ctb.contratos.controller;

import java.text.ParseException;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
import com.ctb.contratos.model.Grupo;
import com.ctb.contratos.model.Lancamento;
import com.ctb.contratos.model.Usuario;
import com.ctb.contratos.repository.Contratos;
import com.ctb.contratos.repository.Grupos;
import com.ctb.contratos.repository.Usuarios;
import com.ctb.security.AppUserDetailsService;

@Controller
@RequestMapping("/transparenciactb/usuarios")
public class UsuarioController {
	private static final String CADASTRO_VIEW = "/cadastro/CadastroUsuario"; 
	private static final String USUARIO_VIEW = "/visualizacao/VisualizarUsuario"; 
	@Autowired
	private Usuarios usuarios;
	@Autowired
	private Contratos contratos;
	
	@Autowired
	private Grupos grupos;
	
	
	
	@RequestMapping("/novo")
	public ModelAndView novo()
	{
		ModelAndView mv = new ModelAndView(CADASTRO_VIEW);
		mv.addObject(new Usuario());
		mv.addObject("grupos", grupos.findAll());
		//mv.addObject("todosNiveisUsuario", Nivel.values());
		return mv;
	}
	@RequestMapping(method = RequestMethod.POST)
	public String salvar(@Validated Usuario usuario, RedirectAttributes attributes)
	{
		ModelAndView mv = new ModelAndView(CADASTRO_VIEW);
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		usuario.setPassword(encoder.encode(usuario.getPassword()));
		usuarios.save(usuario);		
		attributes.addFlashAttribute("mensagem", "Usuário salvo com sucesso!");	
		return "redirect:/transparenciactb/usuarios/novo";
	}
	
	@RequestMapping(method= RequestMethod.GET)
	public ModelAndView pesquisar(String busca, String nome, String setor, String matricula) throws ParseException
	{
		ModelAndView mv = new ModelAndView("/pesquisa/PesquisaUsuarios");
		//mv.addObject("usuarios", todosUsuarios);
		
		if(nome != null) {
			if(busca != null && nome.equals("on")) {
				List<Usuario> todosUsuarios = usuarios.findByNomeContaining(busca);
				mv.addObject("buscaUsuarios", todosUsuarios);
				System.out.println(todosUsuarios.size());
				
				return mv;
			}
		}
		else if(setor != null) {
			if(busca != null && setor.equals("on")) {
				List<Usuario> todosUsuarios= usuarios.findBySetorContaining(busca);
				mv.addObject("buscaUsuarios", todosUsuarios);
				return mv;
			}
		}
		else if(matricula != null) {
			if(busca != null && matricula.equals("on")) {
				List<Usuario> todosUsuarios= usuarios.findByMatriculaContaining(busca);
				mv.addObject("buscaUsuarios", todosUsuarios);
				return mv;
			}
		}
		   List<Usuario> todosUsuarios= usuarios.findAll();
		   mv.addObject("buscaUsuarios", todosUsuarios);
	    
    
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
	
	@RequestMapping(value="/remove/{id_usuario}")
	public String excluir(@PathVariable Integer id_usuario, RedirectAttributes attributes)
	{
		Usuario usuario = usuarios.findOne(id_usuario);
		removerGestorOuFiscal(usuario);
		usuarios.delete(id_usuario);
		
		attributes.addFlashAttribute("mensagem", "Usuário excluido com sucesso com sucesso!");	
		//usuarios.delete(id_usuario);
		return "redirect:/transparenciactb/usuarios";	
	
	}
	
	@RequestMapping(value="/visualizar/{id_usuario}")
	public ModelAndView visualizar_usuario(@PathVariable Integer id_usuario, RedirectAttributes attributes)
	{
		Usuario usuario = usuarios.findOne(id_usuario);
		ModelAndView mv = new ModelAndView(USUARIO_VIEW);
		
		mv.addObject("usuario_nome", usuario.getNome());
		mv.addObject("usuario_matricula", usuario.getMatricula());
		mv.addObject("usuario_email", usuario.getEmail());
		mv.addObject("usuario_funcao", usuario.getFuncao());
		mv.addObject("usuario_setor", usuario.getSetor());
		mv.addObject("usuario_telefone", usuario.getTelefone());
		mv.addObject("usuario_contratos_geridos", usuario.getContratosGeridos());
		mv.addObject("usuario_contratos_fiscalizados", usuario.getContratosFiscalizados());
		//usuarios.delete(id_usuario);
		return mv;	
	
	}
	
	@ModelAttribute("todosUsuarios")
	public List<Usuario> todosUsuarios()
	{
		return usuarios.findAll();
	}
	
	public void removerGestorOuFiscal(Usuario usuario)
	{
		List<Contrato> contrats = contratos.findAll();
		Iterator it = contrats.iterator();
		
		
		while(it.hasNext())
		{
			Contrato obj = (Contrato) it.next();
			//System.out.println(obj.getProcesso().getId_processo());
			if(obj.getGestor() != null)
			{
				if(obj.getGestor().getId_usuario() == usuario.getId_usuario())
				{
					obj.setGestor(null);
					contratos.save(obj);
					
				}
			}
			if(obj.getFiscal()!= null) {
				 if(obj.getFiscal().getId_usuario() == usuario.getId_usuario())
				{
					obj.setFiscal(null);
					contratos.save(obj);
				}
			}
		}
	}
	
	@ModelAttribute("permissao")
	public boolean temPermissao() {
		return AppUserDetailsService.cusuario.getAuthorities().toString().contains("ROLE_CADASTRAR_USUARIO");
	}
	
	@ModelAttribute("grupos")
	public List<Grupo> grupos()
	{
		return grupos.findAll();
	}
	
}
