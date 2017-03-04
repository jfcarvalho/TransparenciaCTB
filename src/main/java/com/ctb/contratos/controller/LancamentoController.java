package com.ctb.contratos.controller;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Months;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ctb.Mailer;
import com.ctb.Processo;
import com.ctb.contratos.model.Contratado;
import com.ctb.contratos.model.Contrato;
import com.ctb.contratos.model.Fonte;
import com.ctb.contratos.model.Lancamento;
import com.ctb.contratos.model.TipoAditivo;
import com.ctb.contratos.repository.Contratados;
import com.ctb.contratos.repository.Contratos;
import com.ctb.contratos.repository.Lancamentos;
import com.ctb.contratos.repository.Processos;
import com.ctb.security.AppUserDetailsService;

@Controller
@RequestMapping("/transparenciactb/lancamentos")

public class LancamentoController {
	

	private static final String CADASTRO_VIEW = "/cadastro/CadastroLancamento"; 
	private static final String LANCAMENTOS_VIEW = "/pesquisa/PesquisaLancamentos";
	private static final String PAGAMENTO_VIEW="/cadastro/Pagamento";
	private static final String EDICAO_VIEW="/edicao/EdicaoLancamento";
	
	
	@PersistenceContext
	private EntityManager manager;
	
	@Autowired
	private Lancamentos lancamentos;
	@Autowired
	private Processos processos;
	@Autowired
	private Contratos contratos;
	@Autowired
	private Mailer mailer;
	
	
	@RequestMapping("/novo/{id_contrato}")
	public ModelAndView novo(@PathVariable("id_contrato") Integer Id_contrato)
	{
		ModelAndView mv = new ModelAndView(CADASTRO_VIEW);
		mv.addObject(new Lancamento());
		Contrato c = contratos.findOne(Id_contrato);
		mv.addObject("contrato", c);
		return mv;
	}
	@RequestMapping("/gerar_pagamento/{id_lancamento}")
	public ModelAndView gerar_pagamento(@PathVariable("id_lancamento")Integer Id_lancamento)
	{
		ModelAndView mv = new ModelAndView(PAGAMENTO_VIEW);
	   mv.addObject("lancamento", lancamentos.findOne(Id_lancamento));
	   return mv;	
	}
	
	public void checaGastoContrato(Contrato contrato, BigDecimal gastomedio, BigDecimal valorgasto)
	{
		DateTime inicio = new DateTime();
		DateTime fim = new DateTime(contrato.getData_vencimento());
		Days d = Days.daysBetween(inicio, fim);
		Integer dias  = d.getDays();
		BigDecimal valorEstimado = new BigDecimal("0");
		if(dias <= 90 && contrato.getRiscofinanceiro() == false)
		{
			Months m = Months.monthsBetween(inicio, fim);
			BigDecimal meses = new BigDecimal (Integer.toString(m.getMonths()));
			
			//BigDecimal totalAditivos = new BigDecimal("0");
			//totalAditivos = calcularAditivos(contrato.getLancamentos());
			//totalAditivos = totalAditivos.add(contrato.getValor_contrato());
			valorEstimado = contrato.getSaldo_contrato().divide(meses);
			int comp = valorgasto.compareTo(gastomedio);
			int comp2 = valorgasto.compareTo(valorEstimado);
			if(comp == 1 && comp2 ==1) //Ou seja se o valor gasto estiver acima do valor médio e for maior que o valorEstimado
			{
				contrato.setRiscofinanceiro(true);
			}
		}
	}
	
	public BigDecimal calcularAditivos(List<Lancamento> lancamentos)
	{
		BigDecimal acumulador =  BigDecimal.ZERO;
		Iterator it = lancamentos.iterator();
		
		while(it.hasNext())
		{
			Lancamento obj = (Lancamento) it.next();
			if(obj.getPossui_aditivo())
			{
				acumulador = acumulador.add(obj.getValor_aditivo()); 
			}
			
		}
		return acumulador;
	}
	
	@RequestMapping(value= "/pagar/{id_lancamento}", method=RequestMethod.POST)
	public ModelAndView pagar(Lancamento lancamento, @RequestParam("lancamento_id_processo" )Integer Id_processo)
	{
		ModelAndView mv = new ModelAndView(PAGAMENTO_VIEW);
		Lancamento lancamentobd = lancamentos.findOne(lancamento.getId_lancamento());
		//mv.addObject("lancamento", lancamentobd);
		lancamento.setAditivo_n(lancamentobd.getAditivo_n());
		lancamento.setContrato(lancamentobd.getContrato());
		lancamento.setData(lancamentobd.getData());
		lancamento.setMeses_prorrogacao(lancamentobd.getMeses_prorrogacao());
		lancamento.setNumero_nota_fiscal(lancamentobd.getNumero_nota_fiscal());
		lancamento.setObservacao(lancamentobd.getObservacao());
		lancamento.setPossui_aditivo(lancamentobd.getPossui_aditivo());
		lancamento.setSaldo_contrato(lancamentobd.getSaldo_contrato());
		lancamento.setHora(lancamentobd.getHora());
		lancamento.setMedicao(lancamentobd.getMedicao());
		lancamento.setTipoAditivo(lancamentobd.getTipoAditivo());
		lancamento.setValor(lancamentobd.getValor());
		lancamento.setValor_aditivo(lancamentobd.getValor_aditivo());
		lancamento.setDoe_aditivo(lancamentobd.getDoe_aditivo());
		Processo p = processos.findOne(Id_processo);
		lancamento.setProcesso(p);
		p.setLancamento(lancamento);
		processos.save(p);
	lancamento.setLiquidado(true);

		lancamentos.save(lancamento);
		mv.addObject("mensagem", "Pagamento registrado com sucesso!");
		return mv;
	}
	

	
	@RequestMapping(method = RequestMethod.POST)
	public String salvar(Lancamento lancamento, @RequestParam Integer lancamento_id_contrato, RedirectAttributes attributes)
	{
		ModelAndView mv = new ModelAndView(CADASTRO_VIEW);
		Contrato c = contratos.findOne(lancamento_id_contrato);
		lancamento.setLiquidado(false);
		
	
		if(lancamento_id_contrato != null)
		{
			Contrato contrato = contratos.findOne(lancamento_id_contrato);
			//contrato.setSaldo_contrato(contrato.getSaldo_contrato() - lancamento.getValor());
			lancamento.setContrato(contrato);
			contratos.save(contrato);
		}
		if(lancamento.getPossui_aditivo() && lancamento.getMeses_prorrogacao() != null)
		{
			Calendar cal = Calendar.getInstance();
			cal.setTime(lancamento.getContrato().getData_vencimento());
			cal.add(Calendar.MONTH, lancamento.getMeses_prorrogacao());
	
			
			
			c.setDuracao_meses(c.getDuracao_meses()+ lancamento.getMeses_prorrogacao());
			c.setMeses_vencimento(c.getMeses_vencimento()+ lancamento.getMeses_prorrogacao());
			c.setData_vencimento(cal.getTime());
			
		}
		BigDecimal resultop1 = new BigDecimal(c.getSaldo_contrato().toString());
		BigDecimal resultop2 = new BigDecimal("0");
		if(lancamento.getValor_aditivo() != null)
		{
			resultop2 = new BigDecimal(lancamento.getValor_aditivo().toString());
		}
		BigDecimal resultop3 = new BigDecimal(lancamento.getValor().toString());
		BigDecimal  resultop4 = resultop1.add(resultop2);
		BigDecimal resultop5 = resultop4.subtract(resultop3);
		lancamento.setSaldo_contrato(resultop5);
		c.setSaldo_contrato(resultop5 );
		
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		Date hora = Calendar.getInstance().getTime(); // Ou qualquer outra forma que tem
		String dataFormatada = sdf.format(hora);
		lancamento.setHora(dataFormatada);
	//	System.out.println(gastoMedio(c));
		mailer.enviar_lancamento_gestor(lancamento,"jfcarvalho@ctb.ba.gov.br");
		checaGastoContrato(c, gastoMedio(c), lancamento.getValor());
		contratos.save(c);
		lancamentos.save(lancamento);		
		attributes.addFlashAttribute("mensagem", "Lancamento salvo com sucesso!");	
		return "redirect:/transparenciactb/lancamentos/pesquisar/" + lancamento.getContrato().getId_contrato();
	}

	
	@ModelAttribute("todosProcessos")
	public List<Processo> todosProcessos()
	{
		List <Processo> p = processos.findAll();
		List <Processo> np = new ArrayList<Processo>();
		for(Processo proc:p)
		{
			if(proc.getTipo_processo().getTipo().equals("Pagamento") && proc.getLancamento() == null)
			{
				np.add(proc);
			}
		}
		return np;
	}
	
	public BigDecimal gastoMedio(Contrato contrato)
	{
		BigDecimal valor = new BigDecimal("0");
		Integer quantidadeLancamentos = 0;
		
		for(Lancamento l:contrato.getLancamentos())
			{
				valor = valor.add(l.getValor());
				quantidadeLancamentos++;
			}
		
	if(contrato.getLancamentos().size() > 0) {
		BigDecimal divisor = new BigDecimal(quantidadeLancamentos.toString());
		valor = valor.divide(divisor, RoundingMode.HALF_UP);
		return valor;
	}
	return BigDecimal.ZERO;
	}
	
	
	
	@RequestMapping(value="/pesquisar/{id_contrato}")
	public ModelAndView pesquisar(@PathVariable("id_contrato") Integer Id_contrato, String busca, String nome, String setor, @PageableDefault(size=5) Pageable pageable) throws ParseException
	{
		ModelAndView mv = new ModelAndView("/pesquisa/PesquisaLancamentos");
		Contrato c = contratos.findOne(Id_contrato);
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Lancamento.class);
		int paginaAtual = pageable.getPageNumber();
		int totalRegistrosPorPagina = pageable.getPageSize();
		int primeiroRegistro = paginaAtual * totalRegistrosPorPagina;
		
		
		criteria.setFirstResult(primeiroRegistro);
        criteria.setMaxResults(totalRegistrosPorPagina);
    	criteria.add(Restrictions.eq("contrato", c));
        
        Comparator<Lancamento> cmp = new Comparator<Lancamento>() {
	        public int compare(Lancamento l1, Lancamento l2) {
	          return l2.getData().compareTo(l1.getData());
	        }
	    };
	    
	    Comparator<Lancamento> cmp2 = new Comparator<Lancamento>() {
	        public int compare(Lancamento l1, Lancamento l2) {
	          return l1.getId_lancamento().compareTo(l2.getId_lancamento());
	        }
	    };
	    /*
    	Comparator<Lancamento> cmp = new Comparator<Lancamento>() {
	        public int compare(Lancamento l1, Lancamento l2) {
	          return l2.getId_lancamento().compareTo(l1.getId_lancamento());
	        }
	    };
	    */
	    List<Lancamento> lancamentosOrdenados = criteria.list();
	   // lancamentosOrdenados.sort(cmp2);
	    //Collections.sort(lancamentosOrdenados);
	    Collections.reverse(lancamentosOrdenados);
	    //lancamentosOrdenados.sort(cmp);
	   
		//criteria.add(Restrictions.eq("contrato", c));
		
		//List<Lancamento> lanc= c.getLancamentos();
		Page<Lancamento> pags = new PageImpl<Lancamento>(lancamentosOrdenados, pageable, lancamentosOrdenados.size());
	
		//Page<Lancamento> lancamentos = criteria.list();
		//c.getLancamentos();
		mv.addObject("todosLancamentos",pags);
		mv.addObject("contrato", c);
		return mv;
	}
	
	@RequestMapping("/gerar_edicao_lancamento/{id_lancamento}")
	public ModelAndView gerar_edicao(@PathVariable("id_lancamento") Lancamento lancamento)
	{
		ModelAndView mv = new ModelAndView(EDICAO_VIEW);
		mv.addObject(new LancamentoController());
		Lancamento teste = lancamentos.findOne(lancamento.getId_lancamento());
		Contrato c = contratos.findOne(teste.getContrato().getId_contrato());
		lancamento.setContrato(c);
		mv.addObject(lancamento);
		return mv;
		
	}
	

	@RequestMapping(value = "/editar_lancamento/{id_lancamento}", method=RequestMethod.POST)
	public ModelAndView edicao(Lancamento lancamento, Contrato contrato)
	{
		ModelAndView mv = new ModelAndView(EDICAO_VIEW);
		Lancamento lancbd = lancamentos.findOne(lancamento.getId_lancamento());
		lancbd.setAditivo_n(lancamento.getAditivo_n());
		lancbd.setData(lancamento.getData());
		lancbd.setDataliquidacao(lancamento.getDataliquidacao());
		lancbd.setLiquidado(lancamento.getLiquidado());
		lancbd.setMeses_prorrogacao(lancamento.getMeses_prorrogacao());
		lancbd.setNumero_nota_fiscal(lancamento.getNumero_nota_fiscal());
		lancbd.setObservacao(lancamento.getObservacao());
		lancbd.setPossui_aditivo(lancamento.getPossui_aditivo());
		lancbd.setObservacao(lancamento.getObservacao());
		lancbd.setTipoAditivo(lancamento.getTipoAditivo());
		lancbd.setValor_aditivo(lancamento.getValor_aditivo());
		lancbd.setValor(lancamento.getValor());
		lancbd.setDoe_aditivo(lancamento.getDoe_aditivo());
		lancamentos.save(lancbd);
		recalcularSaldos(lancbd.getContrato());
		mv.addObject("mensagem", "Lançamento editado com sucesso");
		return mv;
	}
	
	@ModelAttribute("todosContratos")
	public List<Contrato> todosContratos()
	{
		return contratos.findAll();
	}
	public void zerarLancamentos(List<Lancamento> l)
	{
		Iterator it = l.iterator();
		
		while(it.hasNext())
		{
			Lancamento lanc = (Lancamento) it.next();
			lanc.setSaldo_contrato(BigDecimal.ZERO);
		}
		lancamentos.save(l);
	}
	
	public void recalcularSaldos(Contrato c)
	{
		List<Lancamento> lanc = c.getLancamentos();
		zerarLancamentos(lanc);
		c.setSaldo_contrato(c.getValor_contrato());
		BigDecimal saldo_corrente = new BigDecimal(c.getValor_contrato().toString());
		for(Lancamento aux:lanc)
		{
			saldo_corrente = saldo_corrente.subtract(aux.getValor());
			if(aux.getPossui_aditivo())
			{
				saldo_corrente = saldo_corrente.add(aux.getValor_aditivo());
			}
			aux.setSaldo_contrato(saldo_corrente);
		
		}
		lancamentos.save(lanc);
		c.setSaldo_contrato(saldo_corrente);
		contratos.save(c);
	}
	
	@RequestMapping(value="/remove/{id_lancamento}")
	public String excluir(@PathVariable Integer id_lancamento, RedirectAttributes attributes)
	{
		Lancamento lancamento = lancamentos.findOne(id_lancamento);
		//attributes.addFlashAttribute("mensagem", "Contrato excluído com sucesso com sucesso!");	
		Contrato c = contratos.findOne(lancamento.getContrato().getId_contrato());
		BigDecimal resultop1 = new BigDecimal(c.getSaldo_contrato().toString());
		BigDecimal resultop2 = new BigDecimal(lancamento.getValor().toString());
		BigDecimal resultop3 = new BigDecimal(lancamento.getValor_aditivo().toString());
		c.setSaldo_contrato(resultop1.add(resultop2));
		if(lancamento.getPossui_aditivo() == true)
		{
			
			c.setSaldo_contrato(resultop1.subtract(resultop3));
			//c.setSaldo_contrato(c.getSaldo_contrato() - lancamento.getValor_aditivo());
			
		}
		contratos.save(c);
		//usuarios.delete(id_usuario);
		//desvincularLancamento(lancamento);
		lancamentos.delete(id_lancamento);
		return "redirect:/transparenciactb/lancamentos/pesquisar/"+ lancamento.getContrato().getId_contrato();	
	
	}
	
public void desvincularLancamento(Lancamento lancamento)
	
	{
	
	if(lancamento.getContrato() != null)
	{
		desvincularContratoLancamento(lancamento.getContrato());
	}
	
	
	
	/*
	Iterator it = contratosLancamentos.iterator();
	
	while(it.hasNext())
	{
		Lancamento obj = (Lancamento) it.next();
		System.out.println("Entrou aqui nesse laço");
		System.out.println(obj.getContrato().getNumero());
		if(obj.getContrato().getNumero() == contrato.getNumero()) {
			obj.setContrato(null);
			lancamentos.save(obj);
			}
		}
		*/
		
	}

public void desvincularContratoLancamento(Contrato contrato)
{
	List<Lancamento> contratosLancamentos = contrato.getLancamentos();
	//System.out.println(contratosLancamentos.size());
	if(contratosLancamentos != null) {
		Iterator it = contratosLancamentos.iterator();
	
	while(it.hasNext())
	{
		Lancamento obj = (Lancamento) it.next();
		if(obj.getContrato().getNumero() == contrato.getNumero()) {
			obj.setContrato(null);
			lancamentos.save(obj);
			}
		}
	}
	
}

@ModelAttribute("todosTipos")
public List<TipoAditivo> todosAditivos() {
	return Arrays.asList(TipoAditivo.values());
}

private Long total(Criteria criteria) {
	criteria.setProjection(Projections.rowCount());
	return (Long) criteria.uniqueResult();
}

@ModelAttribute("permissao")
public boolean temPermissao() {
	return AppUserDetailsService.cusuario.getAuthorities().toString().contains("ROLE_CADASTRAR_CONTRATO");
}

}
