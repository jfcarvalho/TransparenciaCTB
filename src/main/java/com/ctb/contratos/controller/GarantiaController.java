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
import com.ctb.contratos.model.Garantia;
import com.ctb.contratos.model.Lancamento;
import com.ctb.contratos.model.Recurso;
import com.ctb.contratos.model.Usuario;
import com.ctb.contratos.repository.Contratados;
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
	private Lancamentos lancamentos;
	
	@PersistenceContext
	private EntityManager manager;
	
	

	@RequestMapping("/novo/{id_lancamento}")
	public ModelAndView novo(@PathVariable("id_lancamento") Integer id_lancamento)
	{
		ModelAndView mv = new ModelAndView(CADASTRO_VIEW);
		mv.addObject(new Garantia());
		Lancamento l = lancamentos.findOne(id_lancamento);
		mv.addObject("lancamento", l);
		//mv.addObject("todosNiveisUsuario", Nivel.values());
		return mv;
	}
	
	/*
	@RequestMapping(method= RequestMethod.GET)
	public ModelAndView pesquisar(String busca, String nome, String setor) throws ParseException
	{
		ModelAndView mv = new ModelAndView("/pesquisa/PesquisaGarantias");
		//mv.addObject("usuarios", todosUsuarios);
    
	return mv;
	}
	*/
	@RequestMapping(value="/pesquisar/{id_lancamento}")
	public ModelAndView pesquisar(@PathVariable("id_lancamento") Integer Id_lancamento, String busca, String nome, String setor, @PageableDefault(size=15) Pageable pageable) throws ParseException
	{
		ModelAndView mv = new ModelAndView("/pesquisa/PesquisaGarantias");
		Lancamento l = lancamentos.findOne(Id_lancamento);
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Garantia.class);
		int paginaAtual = pageable.getPageNumber();
		int totalRegistrosPorPagina = pageable.getPageSize();
		int primeiroRegistro = paginaAtual * totalRegistrosPorPagina;
		
		
		criteria.setFirstResult(primeiroRegistro);
        criteria.setMaxResults(totalRegistrosPorPagina);
    	criteria.add(Restrictions.eq("lancamento", l));
        
        Comparator<Garantia> cmp = new Comparator<Garantia>() {
	        public int compare(Garantia g1, Garantia g2 ) {
	          return g2.getId_garantia().compareTo(g1.getId_garantia());
	        }
	    };
	   
    	
    	List<Garantia> garantiasOrdenadas = criteria.list();
	    garantiasOrdenadas.sort(cmp);
		//criteria.add(Restrictions.eq("contrato", c));
		
		//List<Garantia> garantia= c.getLancamentos();
		Page<Garantia> pags = new PageImpl<Garantia>(garantiasOrdenadas, pageable, garantiasOrdenadas.size());
	
		//Page<Lancamento> lancamentos = criteria.list();
		//c.getLancamentos();
		mv.addObject("todosLancamentos",pags);
		mv.addObject("lancamento", l);
		return mv;
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String salvar(@Validated Garantia garantia, RedirectAttributes attributes)
	{
		ModelAndView mv = new ModelAndView(CADASTRO_VIEW);
		garantias.save(garantia);		
		attributes.addFlashAttribute("mensagem", "Garantia salva com sucesso!");	
		return "redirect:/transparenciactb/garantias/novo";
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
		return AppUserDetailsService.cusuario.getAuthorities().toString().contains("ROLE_CADASTRAR_CONTRATO");
	}
	
}

