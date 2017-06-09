package com.ctb.contratos.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
import com.ctb.security.AppUserDetailsService;

@Controller
@RequestMapping("/transparenciactb/contratados")

public class ContratadoController {
	private static final String CADASTRO_VIEW = "/cadastro/CadastroContratado"; 
	private static final String CONTRATADO_VIEW = "/visualizacao/VisualizarContratado"; 

	@Autowired
	private Contratados contratados;
	
	@Autowired
	private Contratos contratos;
	
	@RequestMapping("/novo")
	public ModelAndView novo()
	{
		ModelAndView mv = new ModelAndView(CADASTRO_VIEW);
		mv.addObject(new Contratado());
		
		//mv.addObject("todosNiveisUsuario", Nivel.values());
		return mv;
	}
	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView salvar(@Validated Contratado contratado, RedirectAttributes attributes, Errors errors)
	{
		ModelAndView mv = new ModelAndView(CADASTRO_VIEW);
		if(errors.hasErrors())
		{
			return mv;
		}
		contratados.save(contratado);		
		mv.addObject("mensagem", "Empresa contratada salva com sucesso!");	
		return mv;
	}
	
	@RequestMapping(method= RequestMethod.GET)
	public ModelAndView pesquisar(String busca, String nome, String cnpj) throws ParseException
	{
		ModelAndView mv = new ModelAndView("/pesquisa/PesquisaContratados");
		 
		if(nome != null) {
			if(busca != null && nome.equals("on")) {
				List<Contratado> todosContratados = contratados.findByNomeContaining(busca);
				mv.addObject("buscaContratados", todosContratados);
			
				
				return mv;
			}
		}
		else if(cnpj != null) {
			if(busca != null && cnpj.equals("on")) {
				List<Contratado> todosContratados = contratados.findByNomeContaining(busca);
				mv.addObject("buscaContratados", todosContratados);
				return mv;
			}
		}
		 //  List<Contrato> tc = contratos.findAll();
		 
		   mv.addObject("buscaContratados", contratados.findAll());
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
	@RequestMapping(value="/remove/{id_contratado}"/*, method=RequestMethod.DELETE*/)
	public String excluir(@PathVariable Integer id_contratado, RedirectAttributes attributes)
	{
		
		attributes.addFlashAttribute("mensagem", "Contratado removido com sucesso com sucesso!");
		Contratado contratado = contratados.findOne(id_contratado);
		contratado.setContratos(desvincularContratos(contratado.getContratos()));
		contratados.delete(contratado);
		//usuarios.delete(id_usuario);
		return "redirect:/transparenciactb/contratados/";	
	
	}
	
	
	@ModelAttribute("todosContratados")
	public List<Contratado> todosContratados()
	{
		return contratados.findAll();
	}
	

	public List<Contrato> desvincularContratos(List<Contrato> contratosADesvincular)
	{
		List<Contrato> listaNova = new ArrayList<Contrato>();
		Iterator it = contratosADesvincular.iterator();
		
		while(it.hasNext())
		{
			Contrato obj = (Contrato) it.next();
			obj.setContratado(null);
			contratos.save(obj);
			listaNova.add(obj);
		}
		
		return listaNova;
	}

	@RequestMapping(value="/visualizar/{id_contratado}")
	public ModelAndView visualizar_usuario(@PathVariable Integer id_contratado, RedirectAttributes attributes)
	{
		Contratado contratado = contratados.findOne(id_contratado);
		ModelAndView mv = new ModelAndView(CONTRATADO_VIEW);
		
		mv.addObject("contratado_nome", contratado.getNome());
		mv.addObject("contratado_razao", contratado.getNome());
		mv.addObject("contratado_cnpj", contratado.getCnpj());
		mv.addObject("contratado_contratos", contratado.getContratos());
		mv.addObject("contratado_q_contratos", contratado.getContratos().size());
		mv.addObject("contratado_q_lancamentos", quantidadeLancamentos(contratado));
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
	
	public Integer quantidadeLancamentos (Contratado contratado)
	{
		Integer nlancs = 0;
		for(Contrato c: contratado.getContratos())
		{
			for(Lancamento l: c.getLancamentos())
			{
				nlancs++;
			}
		}
		return nlancs;
	}
	
}
