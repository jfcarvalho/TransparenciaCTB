package com.ctb.contratos.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
import com.ctb.TipoProcesso;
import com.ctb.contratos.model.Contratado;
import com.ctb.contratos.model.Contrato;
import com.ctb.contratos.model.Garantia;
import com.ctb.contratos.model.Lancamento;
import com.ctb.contratos.model.Recurso;
import com.ctb.contratos.model.Usuario;
import com.ctb.contratos.repository.Contratados;
import com.ctb.contratos.repository.Contratos;
import com.ctb.contratos.repository.Garantias;
import com.ctb.contratos.repository.Lancamentos;
import com.ctb.contratos.repository.Processos;
import com.ctb.security.AppUserDetailsService;

@Controller
@RequestMapping("/transparenciactb/garantias")
public class GarantiaController {
	private static final String CADASTRO_VIEW = "/cadastro/CadastroGarantia";

	@Autowired
	private Garantias garantias;
	
	@Autowired
	private Contratos contratos;
	
	@PersistenceContext
	private EntityManager manager;
	
	

	@RequestMapping("/novo")
	public ModelAndView novo()
	{
		ModelAndView mv = new ModelAndView(CADASTRO_VIEW);
		mv.addObject(new Garantia());
		//mv.addObject("todosNiveisUsuario", Nivel.values());
		return mv;
	}
	
	@RequestMapping(method= RequestMethod.GET)
	public ModelAndView pesquisar(String busca, String nome, String setor) throws ParseException
	{
		ModelAndView mv = new ModelAndView("/pesquisa/PesquisaGarantias");
		mv.addObject("garantias", garantias.findAll());
		return mv;
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String salvar(Garantia garantia, @RequestParam("contrato") Integer Id_contrato, RedirectAttributes attributes)
	{
		ModelAndView mv = new ModelAndView(CADASTRO_VIEW);
		if(Id_contrato != null) {
			Contrato c = contratos.findOne(Id_contrato);
			garantia.setContrato(c);
		}
		garantias.save(garantia);		
		attributes.addFlashAttribute("mensagem", "Garantia salva com sucesso!");	
		return "redirect:/transparenciactb/garantias";
	}
	
	@ModelAttribute("todasGarantias")
	public List<Garantia> todasGarantias()
	{
		return garantias.findAll();
	}
	@RequestMapping("{id_processo}")
	public ModelAndView edicao(@PathVariable("id_garantia") Garantia garantia)
	{
		//System.out.println(">>>>>>> codigo recebido: " + id_usuario);
		//Usuario usuario = usuarios.findOne(id_usuario);
		
		ModelAndView mv = new ModelAndView(CADASTRO_VIEW);
		mv.addObject("garantia", garantia);
		mv.addObject(garantia);
		return mv;
	}
	
		@ModelAttribute("permissao")
	public boolean temPermissao() {
			Object usuarioLogado = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			return ((UserDetails)usuarioLogado).getAuthorities().toString().contains("ROLE_CADASTRAR_CONTRATO");
	}
		
		@ModelAttribute("todosContratos")
		public List<Contrato> todosContratos() {
			return contratos.findAll();
	}
	
}

