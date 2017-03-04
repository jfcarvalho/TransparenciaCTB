package com.ctb.contratos.controller;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.userdetails.UserDetails;
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

import com.ctb.Processo;
import com.ctb.contratos.model.AditivoSetting;
import com.ctb.contratos.model.Contratado;
import com.ctb.contratos.model.Contrato;
import com.ctb.contratos.model.Fonte;
import com.ctb.contratos.model.Lancamento;
import com.ctb.contratos.model.Recurso;
import com.ctb.contratos.model.Uso;
import com.ctb.contratos.model.Usuario;
import com.ctb.contratos.repository.Contratados;
import com.ctb.contratos.repository.Contratos;
import com.ctb.contratos.repository.Lancamentos;
import com.ctb.contratos.repository.Licitacoes;
import com.ctb.contratos.repository.Processos;
import com.ctb.contratos.repository.Usuarios;
import com.ctb.licitacoes.model.Licitacao;
import com.ctb.security.UsuarioSistema;
import com.ctb.security.AppUserDetailsService;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Months;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;


@Controller
@RequestMapping("/transparenciactb/contratos")

public class ContratoController {
	private static final String CADASTRO_VIEW = "/cadastro/CadastroContrato"; 
	private static final String LANCAMENTOS_VIEW = "/pesquisa/PesquisaLancamentos";
	private static final String VISUALIZAR_VIEW = "/visualizacao/VisualizarContrato";
	private static final String RESUMO_VIEW = "/visualizacao/ResumoContrato";
	private static final String GERARRESUMO_VIEW = "/visualizacao/GerarResumoContrato";
	private static final String RELATORIOTCE_VIEW = "/visualizacao/RelatorioTCE";
	private static final String GERARRESUMOCONSIGNADO_VIEW = "/visualizacao/GerarResumoConsignadoContrato";
	private static final String RESUMOCONSIGNADO_VIEW = "/visualizacao/ResumoConsignadoContrato";
	private static final String EDICAOINFORMACOES_VIEW = "/edicao/EdicaoContratoInformacoes";
	private static final String EDICAOGESTORES_VIEW = "/edicao/EditarContratoGestor";
	private static final String EDICAO_VIEW = "/edicao/EdicaoContrato";
	private static final String RENOVACAO_VIEW = "/cadastro/Renovacao";
	public static Integer numero_contrato =0;
	static final ArrayList<AditivoSetting> aditivos = new ArrayList();
	private static final String DASHBOARD_VIEW = "/cabecalho/DashBoard";
    
	@PersistenceContext
	private EntityManager manager;
	
	@Autowired
	private Usuarios usuarios;
	@Autowired
	private Contratados contratados;
	@Autowired
	private Contratos contratos;

	@Autowired
	private Lancamentos lancamentos;
	
	@Autowired
	private Processos processos;
	
	@Autowired
	private Licitacoes licitacoes;
	
	
	@RequestMapping("/novo")
	public ModelAndView novo()
	{
		ModelAndView mv = new ModelAndView(CADASTRO_VIEW);		
		mv.addObject(new Contrato());
		//DateTime dt = new DateTime(); 
		//System.out.println(dt.toString() );
		return mv;
	}
	
	@RequestMapping("/gerar_resumo/{id_contrato}")
	public ModelAndView gerarResumo(@PathVariable("id_contrato") Integer Id_contrato)
	{
		ModelAndView mv = new ModelAndView(GERARRESUMO_VIEW);
		Contrato c = contratos.findOne(Id_contrato);
		mv.addObject("contrato", c);
		return mv;
	}
	
	@RequestMapping("/relatorio_tce")
	public ModelAndView gerarTCE()
	{
		ModelAndView mv = new ModelAndView(RELATORIOTCE_VIEW);
		return mv;
	}
	
	@RequestMapping("/resumo_consignado/")
	public ModelAndView gerarResumoConsignado()
	{
		ModelAndView mv = new ModelAndView(GERARRESUMOCONSIGNADO_VIEW);	
		Map<String, String> contratosEValores = new HashMap<String, String>();
		BigDecimal acumuladoValor;
		BigDecimal acumuladoAditivo;
		
		
		List<Contrato> todosContratos = contratos.findAll();
		Iterator it = todosContratos.iterator();
		
		while(it.hasNext())
		{
			acumuladoValor = new BigDecimal("0.0");
			acumuladoAditivo = new BigDecimal("0.0");
		
			Contrato obj = (Contrato) it.next();
			List<Lancamento> cl = obj.getLancamentos();
			Iterator it2 = todosContratos.iterator();
			while(it2.hasNext())
			{
				Lancamento lanc = (Lancamento) it2.next();
				acumuladoValor.add(lanc.getValor());
				acumuladoAditivo.add(lanc.getValor_aditivo());
		
			}
			contratosEValores.put(obj.getId_contrato().toString(), String.valueOf(acumuladoValor));
			System.out.println(contratosEValores.values());
		    //System.out.println(obj.getValor());
			//acumulador += obj.getValor(); 
			
			
		}
		return mv;
	}

	@RequestMapping("/editar_contrato/{id_contrato}")
	public ModelAndView editar_contrato(@PathVariable("id_contrato") Integer Id_contrato)
	{
		ModelAndView mv = new ModelAndView(EDICAO_VIEW);
		Contrato c = contratos.findOne(Id_contrato);
		mv.addObject("contrato", c);
		return mv;
	}
	
	
	
	@RequestMapping(value="/resumo/{id_contrato}", method= RequestMethod.GET)
	public ModelAndView resumo(@PathVariable("id_contrato") Integer Id_contrato, String ano)
	{
		List<Lancamento> lancamentos = contratos.findOne(Id_contrato).getLancamentos();
		ModelAndView mv = new ModelAndView(RESUMO_VIEW);
		Contrato c = contratos.findOne(Id_contrato);
		Queue<Integer> meses = new LinkedList();
		int i, indiceElemento;
		String periodoAComparar;
		BigDecimal acumuladorValor = new BigDecimal("0");
		BigDecimal acumuladorAditivo = new BigDecimal("0");
		
		
		BigDecimal acumuladorValorGeral = new BigDecimal("0");
		BigDecimal acumuladorSaldoGeral = new BigDecimal("0");
		BigDecimal acumuladorAditivoGeral = new BigDecimal("0");
		
		BigDecimal [] mesesSaldo = new BigDecimal[13];
		BigDecimal [] mesesValores = new BigDecimal[13];
		BigDecimal [] mesesAditivos = new BigDecimal[13];
		List<String> periodosComparados = new ArrayList<String>();
		for(int y=0; y <13; y++)
		{
			mesesSaldo[y] = new BigDecimal("0.0");
		//	mesesValores[y] = new BigDecimal("0");
			mesesAditivos[y] = new BigDecimal("0.0");
		}
		int flagmes = 0;
		int flagoffset = 0;
		
		
		
		Comparator<Lancamento> cmp = new Comparator<Lancamento>() {
	        public int compare(Lancamento l1, Lancamento l2) {
	          return l1.getData().compareTo(l2.getData());
	        }
	    };

	Collections.sort(lancamentos, cmp);
	
	for(i=1; i < 13; i++)
	{
		flagmes = 0;
		acumuladorValor =new BigDecimal("0.0");
		acumuladorAditivo = new BigDecimal("0.0");
		if(i > 0 && i < 10) {
			periodoAComparar = ano+ "-0"+ Integer.toString(i);
		}else {periodoAComparar = ano+ "-"+ Integer.toString(i);}
		Iterator it = lancamentos.iterator();
		while(it.hasNext())
		{
			Lancamento obj = (Lancamento) it.next();
		
			
			if(obj.getData().toString().contains(periodoAComparar))
			{ 
				flagmes = 1;
				acumuladorValor = acumuladorValor.add(obj.getValor());
				acumuladorValorGeral = acumuladorValorGeral.add(acumuladorValor);
				//acumuladorSaldo += obj.getSaldo_contrato();
				if(obj.getPossui_aditivo())
				{
					acumuladorAditivo = acumuladorAditivo.add(obj.getValor_aditivo()) ;
					acumuladorAditivoGeral = acumuladorAditivoGeral.add(acumuladorAditivo);
					
				}				
			}	
		}
		if(flagmes == 1)
		{
			meses.add(i);
		}
		

		mesesValores[i] = acumuladorValor;
		mesesAditivos[i] = acumuladorAditivo;
		
}
		Iterator itfila = meses.iterator();
		
		while (itfila.hasNext()) {
			if(meses.peek() != null ) {
				int mes = meses.peek();
				meses.poll();
			
			if(mes > 0 && mes < 10) {
				periodoAComparar = ano+ "-0"+ Integer.toString(mes);
			}else {periodoAComparar = ano+ "-"+ Integer.toString(mes);}
			
		//	periodosComparados.add(periodoAComparar);
			
		
		Iterator it2 = lancamentos.iterator();
		while (it2.hasNext()) {
			Lancamento obj = (Lancamento) it2.next();
			indiceElemento = lancamentos.indexOf(obj);
			
			Lancamento l;			
			// System.out.println(indiceElemento);
			if(indiceElemento <= lancamentos.size()-1 && obj.getData().toString().contains(ano)) {
				if (indiceElemento == lancamentos.size()-1)
				{	
					 l = lancamentos.get(indiceElemento);
				}
				else { l = lancamentos.get(indiceElemento+1);}
			//	System.out.println(l.getData().toString().contains(periodoAComparar));
			//	System.out.println(periodoAComparar);
			//	System.out.println(l.getData().toString());
			//	System.out.println(lancamentos.indexOf(l) +" " + lancamentos.size());
				// System.out.println(obj.getData().toString() + "---" + l.getData().toString()) ;
				
				if(compararPeriodos(obj.getData().toString(), periodosComparados) == false ) //precisamos colocar mais uma condição aqui nesse IF
				{
					System.out.println(lancamentos.indexOf(obj) +" " + lancamentos.size());
					
					periodosComparados.add(obj.getData().toString());
				}
				
				
				if(l.getData().toString().contains(periodoAComparar) == false && compararPeriodos(l.getData().toString(), periodosComparados) == false || l.getData().toString().equals(obj.getData().toString()))
				{
				/*	if(lancamentos.indexOf(l) == lancamentos.size()-1 && lancamentos.indexOf(obj) != lancamentos.size()-2) { 
						mesesSaldo[mes] = l.getSaldo_contrato();
						break;
					}
					else { */
					System.out.println(obj.getSaldo_contrato());
					System.out.println(mesesAditivos[mes]);	
					mesesSaldo[mes] = obj.getSaldo_contrato() /*+ mesesAditivos[mes] */;
					acumuladorSaldoGeral.add(obj.getSaldo_contrato()) ; 
					break;
						//}
					}
			}
		}
		}
	}
		
		

	
	

				
		mv.addObject("janeiro_saldo", mesesSaldo[1]);		
		mv.addObject("fevereiro_saldo", mesesSaldo[2]);
		mv.addObject("marco_saldo", mesesSaldo[3]);
		mv.addObject("abril_saldo", mesesSaldo[4]);
		mv.addObject("maio_saldo", mesesSaldo[5]);
		mv.addObject("junho_saldo", mesesSaldo[6]);
		mv.addObject("julho_saldo", mesesSaldo[7]);
		mv.addObject("agosto_saldo", mesesSaldo[8]);
		mv.addObject("setembro_saldo", mesesSaldo[9]);
		mv.addObject("outubro_saldo", mesesSaldo[10]);
		mv.addObject("novembro_saldo", mesesSaldo[11]);
		mv.addObject("dezembro_saldo", mesesSaldo[12]);
		
		mv.addObject("janeiro_valor", mesesValores[1]);		
		mv.addObject("fevereiro_valor", mesesValores[2]);
		mv.addObject("marco_valor", mesesValores[3]);
		mv.addObject("abril_valor", mesesValores[4]);
		mv.addObject("maio_valor", mesesValores[5]);
		mv.addObject("junho_valor", mesesValores[6]);
		mv.addObject("julho_valor", mesesValores[7]);
		mv.addObject("agosto_valor", mesesValores[8]);
		mv.addObject("setembro_valor", mesesValores[9]);
		mv.addObject("outubro_valor", mesesValores[10]);
		mv.addObject("novembro_valor", mesesValores[11]);
		mv.addObject("dezembro_valor", mesesValores[12]);
		
		mv.addObject("janeiro_aditivo", mesesAditivos[1]);		
		mv.addObject("fevereiro_aditivo", mesesAditivos[2]);
		mv.addObject("marco_aditivo", mesesAditivos[3]);
		mv.addObject("abril_aditivo", mesesAditivos[4]);
		mv.addObject("maio_aditivo", mesesAditivos[5]);
		mv.addObject("junho_aditivo", mesesAditivos[6]);
		mv.addObject("julho_aditivo", mesesAditivos[7]);
		mv.addObject("agosto_aditivo", mesesAditivos[8]);
		mv.addObject("setembro_aditivo", mesesAditivos[9]);
		mv.addObject("outubro_aditivo", mesesAditivos[10]);
		mv.addObject("novembro_aditivo", mesesAditivos[11]);
		mv.addObject("dezembro_aditivo", mesesAditivos[12]);
		
		mv.addObject("saldoGeral", acumuladorSaldoGeral);
		mv.addObject("valorGeral", acumuladorValorGeral);
		mv.addObject("aditivoGeral", acumuladorAditivoGeral);
		
		
		
		
		
		
		
		
		
		return mv;
		
	}


	public boolean compararPeriodos(String periodoAComparar, List<String> periodos)
	{
		Iterator it = periodos.iterator();
		while(it.hasNext())
		{
			String obj = (String) it.next();
			if(obj.contains(periodoAComparar))
			{
				return true;
			}
		}
		return false;
	}
	
	@RequestMapping("/visualizar/{id_contrato}")
	public ModelAndView visualizar(@PathVariable("id_contrato") Integer Id_contrato)
	{
		
		
		ModelAndView mv = new ModelAndView(VISUALIZAR_VIEW);
		Contrato c = contratos.findOne(Id_contrato);
		List<Lancamento> lancamentos = c.getLancamentos();
		
	    
	    mv.addObject("lancamentosOrdenados", lancamentos);
		BigDecimal totalAditivosPorcentagem;
		BigDecimal totalAditivos = calcularAditivos(c.getLancamentos());
		
		BigDecimal valorTotal = calcularValorTotal(c.getLancamentos());
		
		Comparator<Lancamento> cmp = new Comparator<Lancamento>() {
	        public int compare(Lancamento l1, Lancamento l2) {
	          return l2.getData().compareTo(l1.getData());
	        }
	    };
	    Collections.sort(lancamentos, cmp);
		if(totalAditivos.equals(new BigDecimal("0"))) {
			totalAditivosPorcentagem = new BigDecimal(c.getValor_contrato().toString());
		} else {
			BigDecimal result = totalAditivos.add(c.getValor_contrato());
			totalAditivosPorcentagem = new BigDecimal(result.toString());
			}
		
		BigDecimal cem = new BigDecimal("100");
		BigDecimal multiplicacao = valorTotal.multiply(cem);
		System.out.println(multiplicacao);
		System.out.println(totalAditivosPorcentagem);
		System.out.println(valorTotal);
		BigDecimal divisao = multiplicacao.divide(totalAditivosPorcentagem, RoundingMode.HALF_DOWN);
		BigDecimal porcentagemConcluida = new BigDecimal(divisao.toString());
		
		DateTime inicio = new DateTime();
		DateTime fim = new DateTime(c.getData_vencimento());
		Months m = Months.monthsBetween(inicio, fim);
		Days d = Days.daysBetween(inicio, fim);
		c.setMeses_vencimento(m.getMonths());
		contratos.save(c);
		
	
		mv.addObject("dias", d.getDays() <= 90 && d.getDays() >0);
		mv.addObject("ndias", d.getDays());
		mv.addObject("vencido", d.getDays() < 0);
		mv.addObject("contrato", c);
		mv.addObject("total_aditivos", totalAditivos);
		mv.addObject("total", valorTotal);
		mv.addObject("totalcomaditivos", c.getValor_contrato().add( totalAditivos));
		mv.addObject("saldo_contrato", c.getSaldo_contrato());
		mv.addObject("porcentagem_concluida", porcentagemConcluida);
		mv.addObject("risco", c.getRiscofinanceiro());
		BigDecimal cembigd = new BigDecimal(100);
		BigDecimal result2 = new BigDecimal(porcentagemConcluida.toString());
		mv.addObject("porcentagem_a_concluir", cembigd.subtract(result2));
		//	mv.addObject(new Contrato());
		//numero_contrato
		//mv.addObject("todosNiveisUsuario", Nivel.values());
		return mv;
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
	
	public BigDecimal calcularAditivos(List<Lancamento> lancamentos, String periodo)
	{
		BigDecimal acumulador =  BigDecimal.ZERO;
		Iterator it = lancamentos.iterator();
		
		while(it.hasNext())
		{
			Lancamento obj = (Lancamento) it.next();
			if(obj.getPossui_aditivo() && obj.getData().toString().contains(periodo))
			{
				acumulador = acumulador.add(obj.getValor_aditivo()); 
			}
			
		}
		return acumulador;
	}
	
	
	public BigDecimal calcularValorTotal(List<Lancamento> lancamentos)
	{
		BigDecimal acumulador =  BigDecimal.ZERO;
		Iterator it = lancamentos.iterator();
		
		while(it.hasNext())
		{
			Lancamento obj = (Lancamento) it.next();
		//	System.out.println(obj.getValor());
			acumulador = acumulador.add(obj.getValor()); 
			
			
		}
		return acumulador;
	}
			
	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView salvar(@Validated Contrato contrato, @RequestParam Integer contrato_id_gestor, @RequestParam Integer contrato_id_fiscal, @RequestParam Integer contrato_id_licitacao,@RequestParam Integer contrato_id_contrato, RedirectAttributes attributes, Errors errors)
	{
		ModelAndView mv = new ModelAndView(CADASTRO_VIEW);
		if(errors.hasErrors())
		{
			return mv;
		}
		
		Contratado empresa = contratados.findOne(contrato_id_contrato);
		Usuario gestor = usuarios.findOne(contrato_id_gestor);
		Usuario fiscal = usuarios.findOne(contrato_id_fiscal);
		Licitacao licitacao = licitacoes.findOne(contrato_id_licitacao);
		
		contrato.setContratado(empresa);
		contrato.setFiscal(fiscal);
		contrato.setGestor(gestor);
		contrato.setSaldo_contrato(contrato.getValor_contrato());
		contrato.setAvisos_dias(criar_vetor_booleano());
		contrato.setLicitacao(licitacao);
		
		Date dataAtual = new Date();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.format(dataAtual);
		
		DateTime inicio = new DateTime();
		DateTime fim = new DateTime(contrato.getData_vencimento());
		Months m = Months.monthsBetween(inicio, fim);
		contrato.setMeses_vencimento(m.getMonths());

		
		
		//date = dateFormat.format(date);
		//System.out.println(dateFormat.format(date2.toString()));
		contrato.setUltima_atualizacao(dataAtual);
		
		contratos.save(contrato);		
		//attributes.addFlashAttribute("mensagem", "Contrato salvo com sucesso!");	
		mv.addObject("mensagem", "Contrato salvo com sucesso!");
		return mv;
	}
	
	@RequestMapping(method= RequestMethod.GET)
	public ModelAndView pesquisar(String busca, String numero, String objeto, @PageableDefault(size=5) Pageable pageable) throws ParseException
	{
		
		
		Usuario currentUser = usuarios.findByEmail(AppUserDetailsService.cusuario.getUsername());
		boolean tem_permissao = AppUserDetailsService.cusuario.getAuthorities().toString().contains("ROLE_CADASTRAR_CONTRATO");
		ModelAndView mv = new ModelAndView("/pesquisa/PesquisaContratos");
		//mv.addObject("usuarios", todosUsuarios);
		mv.addObject("tem_permissao", tem_permissao);
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Contrato.class);
		int primeiroRegistro = pageable.getPageNumber()*pageable.getPageSize();
		
		Date dataAtual = new Date();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		
		System.out.println(dateFormat.format(dataAtual));
		System.out.println(dataAtual.toString());
		
		criteria.setFirstResult(primeiroRegistro);
		criteria.setMaxResults(pageable.getPageSize());
		if(numero != null) {
			if(busca != null && numero.equals("on")) {
				List<Contrato> todosContratos = contratos.findByNumeroContaining(busca);
				mv.addObject("buscaContratos", todosContratos);
				System.out.println(todosContratos.size());
				
				return mv;
			}
		}
		else if(objeto != null) {
			if(busca != null && objeto.equals("on")) {
				List<Contrato> todosContratos = contratos.findByObjetoContaining(busca);
				mv.addObject("buscaContratos", todosContratos);
				return mv;
			}
		}

		   
		   if(AppUserDetailsService.cusuario.getAuthorities().toString().contains("ROLE_CADASTRAR_CONTRATO") == false)
		   {
			   
			   List<Contrato> todosContratos = contratos.findAll();
			   List<Contrato> contratosAchados = new ArrayList<Contrato>();
			   
			   Iterator it = todosContratos.iterator();
				while (it.hasNext()) {
					Contrato c = (Contrato) it.next();
					if (c.getGestor().getId_usuario() == currentUser.getId_usuario())
					{
						
						contratosAchados.add(c);
					}
				}  
				mv.addObject("buscaContratos", contratosAchados);
				return mv;
		   }
		   
		//   List<Contrato> todosContratos= contratos.findAll();
		   mv.addObject("buscaContratos", contratos.findAll(pageable));
		   
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
	
	@RequestMapping(value="/remove/{id_contrato}")
	public String excluir(@PathVariable Integer id_contrato, RedirectAttributes attributes)
	{
		Contrato contrato = contratos.findOne(id_contrato);
		attributes.addFlashAttribute("mensagem", "Contrato excluído com sucesso com sucesso!");	
		//usuarios.delete(id_usuario);
		desvincularContrato(contrato);
		contratos.delete(id_contrato);
		return "redirect:/transparenciactb/contratos";	
	
	}
	
	public void desvincularContrato(Contrato contrato)
	
	{
		if(contrato.getGestor() != null)
		{
			contrato.setGestor(null);
			desvincularGestoresContratos(contrato);
		}
		if (contrato.getFiscal() != null)
		{
			contrato.setFiscal(null);
			desvincularFiscaisContratos(contrato);
		}
		if(contrato.getLancamentos() != null)
		{
			desvincularLancamentos(contrato);
			contrato.setLancamentos(null);
			
		}
		
		contratos.save(contrato);
	}
	@RequestMapping("/editar_informacoes/{id_contrato}")
	public ModelAndView edicao1(@PathVariable("id_contrato") Contrato contrato)
	{
		ModelAndView mv = new ModelAndView(EDICAOINFORMACOES_VIEW);
		mv.addObject("su", contrato);
		mv.addObject(contrato);
		return mv;
	}
	
	@RequestMapping(value="/{id_contrato}/salvar1",method = RequestMethod.POST)
	public String salvar1(@Validated Contrato contratoform, Errors errors, RedirectAttributes attributes)
	{
		ModelAndView mv = new ModelAndView(CADASTRO_VIEW);
		if(errors.hasErrors())
		{
			return "CadastroContrato";
		}
	
		Contrato contratobd = contratos.findOne(contratoform.getId_contrato());
		contratobd.setNumero(contratoform.getNumero());
		contratobd.setFonte(contratoform.getFonte());
		contratobd.setObjeto(contratoform.getObjeto());
		contratobd.setRecurso(contratoform.getRecurso());
		contratobd.setValor_contrato(contratoform.getValor_contrato());
		
		contratobd.setSaldo_contrato(contratoform.getValor_contrato());
		contratobd.setUso(contratoform.getUso());
		contratobd.setDuracao_meses(contratoform.getDuracao_meses());
		contratobd.setVencimento_garantia(contratoform.getVencimento_garantia());
		contratobd.setData_assinatura(contratoform.getData_assinatura());
		contratobd.setData_vencimento(contratoform.getData_vencimento());
		contratobd.setNomeResponsavel(contratoform.getNomeResponsavel());
		contratobd.setCpfResponsavel(contratoform.getCpfResponsavel());
		recalcularSaldos(contratobd);
		
		contratos.save(contratobd);
	
	
		attributes.addFlashAttribute("mensagem", "Contrato salvo com sucesso!");	
		return "redirect:/transparenciactb/contratos/novo";
		
	}
	
	@RequestMapping(value="/{id_contrato}/salvar2",method = RequestMethod.POST)
	public String salvar2(Contrato contratoform, @RequestParam Integer contrato_id_gestor,@RequestParam Integer contrato_id_fiscal, Errors errors, RedirectAttributes attributes)
	{
		ModelAndView mv = new ModelAndView(CADASTRO_VIEW);
		Contrato contratobd = contratos.findOne(contratoform.getId_contrato());
		Usuario gestor = usuarios.findOne(contrato_id_gestor);
		Usuario fiscal = usuarios.findOne(contrato_id_fiscal);
		//System.out.println(contratoform.getId_contrato());
		//System.out.println(contratoform.getCpfResponsavel());
			contratobd.setGestor(gestor);
			contratobd.setFiscal(fiscal);
		
		contratos.save(contratobd);
		
		attributes.addFlashAttribute("mensagem", "Contrato salvo com sucesso!");	
		return "redirect:/transparenciactb/contratos/novo";
		
	}
	
		
	@RequestMapping("/editar_gestor/{id_contrato}")
	public ModelAndView edicao2(@PathVariable("id_contrato") Contrato contrato)
	{
		ModelAndView mv = new ModelAndView(EDICAOGESTORES_VIEW);
		mv.addObject("su", contrato);
		mv.addObject(contrato);
		return mv;
	}
	
	@RequestMapping("/gerar_renovacao/{id_contrato}")
	public ModelAndView gerar_renovacao(@PathVariable("id_contrato")Contrato contrato)
	{
		ModelAndView mv = new ModelAndView(RENOVACAO_VIEW);
	   mv.addObject("contrato", contratos.findOne(contrato.getId_contrato()));
	   mv.addObject(contrato);
	   return mv;
	}
	
	@RequestMapping(value= "/renovar/{id_contrato}", method=RequestMethod.POST)
	public ModelAndView renovar(Contrato contrato, @RequestParam("contrato_id_processo" )Integer Id_processo)
	{
		ModelAndView mv = new ModelAndView(RENOVACAO_VIEW);
		Contrato contratobd = contratos.findOne(contrato.getId_contrato());
		Processo p = processos.findOne(Id_processo);
		contratobd.setProcesso(p);
		p.setContrato(contratobd);
		processos.save(p);
		contratos.save(contratobd);
		mv.addObject(contratobd);
		mv.addObject("mensagem", "Processo registrado com sucesso!");
		return mv;
	}

	
	public void desvincularLancamentos(Contrato contrato)
	{
		System.out.println("Entrou aqui nesse método");
		List<Lancamento> contratosLancamentos = contrato.getLancamentos();
		System.out.println(contratosLancamentos);
		//System.out.println(contratosLancamentos.size());
		if(contratosLancamentos != null) {
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
		}
	}
	
	public void desvincularGestoresContratos(Contrato contrato)
	{
		if(contrato.getGestor() != null) {
			Usuario gestor = usuarios.findOne(contrato.getGestor().getId_usuario());
			List<Contrato> contratosGestor = gestor.getContratosGeridos();
			Iterator it = contratosGestor.iterator();
			
			while(it.hasNext())
			{
				Contrato obj = (Contrato) it.next();
				if(obj.getNumero() == contrato.getNumero()) {
					obj.setGestor(null);
					contratos.save(obj);
				}
			}
	}
}
	public void desvincularFiscaisContratos(Contrato contrato)
	{
		if(contrato.getFiscal() != null) {
			Usuario fiscal = usuarios.findOne(contrato.getFiscal().getId_usuario());
			List<Contrato> contratosFiscal= fiscal.getContratosGeridos();
			Iterator it = contratosFiscal.iterator();
			
			while(it.hasNext())
			{
				Contrato obj = (Contrato) it.next();
				if(obj.getNumero() == contrato.getNumero()) {
					obj.setFiscal(null);
					contratos.save(obj);
					}
				}
			}
	}	
	
	
	public Contrato[] maioresValoresContratos()
	{
		List<Contrato> contratosABuscar = contratos.findAll();
		Contrato [] maioresContratos = new Contrato[contratosABuscar.size()];
		Iterator it = contratosABuscar.iterator();
	/*	
		while(it.hasNext())
		{
			Contrato obj = (Contrato) it.next();
			
			if(obj.getGestor().getId_usuario() == id_usuario) {
				contratosDoGestor.add(obj);
			}
		}
		return contratosDoGestor;
	*/
		return null;
	}
	
	
	@ModelAttribute("todosGestores")
	public List<Usuario> todosGestores()
	{
		return usuarios.findAll();
	}
	
	
	@ModelAttribute("todosProcessosRenovacao")
	public List<Processo> todosProcessos()
	{
		List<Processo> processosRenovacao = new ArrayList<Processo>();
		for(Processo p:processos.findAll())
		{
			if((p.getTipo_processo().getTipo().equals("Renovação") || p.getTipo_processo().getTipo().equals("Nova licitação") ) && p.getContrato() == null)
			{
				processosRenovacao.add(p);
			}
		}
		
		return processosRenovacao;
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
	
	@ModelAttribute("todasLicitacoes")
	public List<Licitacao> todasLicitacoes()
	{
		return licitacoes.findAll();
	}
	

	@ModelAttribute("todosUsos")
	public List<Uso> todosUsos() {
		return Arrays.asList(Uso.values());
}
	
	@ModelAttribute("todasFontes")
	public List<Fonte> todosFontes() {
		return Arrays.asList(Fonte.values());
}
	
	@ModelAttribute("todosRecursos")
	public List<Recurso> todosRecursos() {
		return Arrays.asList(Recurso.values());
}
	@ModelAttribute("permissao")
	public boolean temPermissao() {
		return AppUserDetailsService.cusuario.getAuthorities().toString().contains("ROLE_CADASTRAR_CONTRATO");
	}
	



	public boolean[] criar_vetor_booleano()
	{
		boolean[] novo_vetor = new boolean[7];
		for(int i=0; i < novo_vetor.length; i++)
		{
			novo_vetor[i] = false;
		}
		
		return novo_vetor;
	
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
	
	
	
	
}
