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

import com.ctb.Datasheet;
import com.ctb.Processo;
import com.ctb.contratos.model.AditivoSetting;
import com.ctb.contratos.model.AnoResumo;
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

import jxl.write.WriteException;

import com.ctb.security.AppUserDetailsService;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
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
	private static final String RESUMOCINCO_VIEW = "/visualizacao/ResumoContratoCincoAnos";
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
	
	Queue<Integer> meses = new LinkedList();
	
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
	public ModelAndView gerarTCE() throws WriteException
	{
		ModelAndView mv = new ModelAndView(RELATORIOTCE_VIEW);
	     List<Contrato> ct = contratos.findAll();
	     List<Contrato> ctx = new ArrayList<Contrato>();
	     Datasheet ds = new Datasheet();
	     ds.readExcel();
	     int linhaPlanilha = 5;
	     int colunaPlanilha = 0;
	     
	     for(Contrato c:ct)
	     {
	    	 String [] data = c.getData_vencimento().toString().split("-");
	    	 if(Integer.parseInt(data[0]) >= 2016)
	    	 {
	    		 ctx.add(c);
	    	 }
	     }
	     List<PrestacaoContas> prestacao = new ArrayList<PrestacaoContas>();
	   // int linhaAtual = 5;
	    for (Contrato c:ctx) {
	    	PrestacaoContas pc = new PrestacaoContas();
	    	pc.setCuo("26402");
	    	ds.setValueIntoCell("Planilha1", colunaPlanilha++,linhaPlanilha, pc.getCuo());
	    	pc.setNuo("CTB");
	    	ds.setValueIntoCell("Planilha1", colunaPlanilha++,linhaPlanilha, pc.getNuo());
	    	pc.setCug("1");
	    	ds.setValueIntoCell("Planilha1", colunaPlanilha++,linhaPlanilha, pc.getCug());
	    	pc.setNug("CTB");
	    	ds.setValueIntoCell("Planilha1", colunaPlanilha++,linhaPlanilha, pc.getNuo());
	    	pc.setContratado(c.getContratado().getNome());
	    	ds.setValueIntoCell("Planilha1", colunaPlanilha++ ,linhaPlanilha, pc.getContratado());
	    	pc.setCnpj(c.getContratado().getCnpj());
	    	ds.setValueIntoCell("Planilha1", colunaPlanilha++,linhaPlanilha, pc.getCnpj());
		    pc.setCpf(c.getCpfResponsavel());
		    ds.setValueIntoCell("Planilha1", colunaPlanilha++,linhaPlanilha,  pc.getCpf());
		    pc.setNumero(c.getNumero());
		    ds.setValueIntoCell("Planilha1", colunaPlanilha++,linhaPlanilha, pc.getNumero());
		    pc.setObjeto(c.getObjeto());
		    ds.setValueIntoCell("Planilha1", colunaPlanilha++,linhaPlanilha, pc.getObjeto());
		    pc.setDoe(c.getDoe());
		    if(pc.getDoe() != null)
		    {
		    	ds.setValueIntoCell("Planilha1", linhaPlanilha, colunaPlanilha++, pc.getDoe().toString());
		    }
		    else
		    {
		    	ds.setValueIntoCell("Planilha1", linhaPlanilha, colunaPlanilha++, "--------");
		    }
		   // ds.setValueIntoCell("Planilha1", linhaPlanilha, colunaPlanilha++, pc.getDoe().toString());
		    if(c.getLicitacao() != null) {
			    pc.setLicitacao_modalidade(c.getLicitacao().getModalidade().getDescricao());
			    ds.setValueIntoCell("Planilha1", colunaPlanilha++,linhaPlanilha, pc.getLicitacao_modalidade());
			    pc.setLicitacao_numero(c.getLicitacao().getNumero());
			    ds.setValueIntoCell("Planilha1", colunaPlanilha++,linhaPlanilha, pc.getLicitacao_numero());
		    }
		    else
		    {
		    	 ds.setValueIntoCell("Planilha1", colunaPlanilha++,linhaPlanilha,"--------");
		    	 ds.setValueIntoCell("Planilha1", colunaPlanilha++,linhaPlanilha, "--------");
		    }
		    pc.setContrato_vigencia(c.getData_assinatura());
		    ds.setValueIntoCell("Planilha1", colunaPlanilha++,linhaPlanilha, pc.getContrato_vigencia().toString());
		    pc.setContrato_fim(c.getData_vencimento());
		    ds.setValueIntoCell("Planilha1", colunaPlanilha++,linhaPlanilha, pc.getContrato_fim().toString());
		   pc.setValor(c.getValor_contrato());
		   ds.setValueIntoCell("Planilha1", colunaPlanilha++ ,linhaPlanilha, pc.getValor().toString());
		   

		 		 

		    BigDecimal valorTotalAditivos = totalValoresAditivos(c);
		    valorTotalAditivos = valorTotalAditivos.add(c.getValor_contrato());
		 
		    pc.setValor_com_aditivo(valorTotalAditivos);
		    ds.setValueIntoCell("Planilha1", colunaPlanilha++ ,linhaPlanilha, pc.getValor_com_aditivo().toString());
		     Integer qAditivos = totalAditivos(c);
		     pc.setN_aditivos(qAditivos);
		     ds.setValueIntoCell("Planilha1", colunaPlanilha++ ,linhaPlanilha, pc.getN_aditivos().toString());
		    
		     BigDecimal pagoExercicio = totalPagoExercicio(c, "2016"); 
		    pc.setPagoExercicio(pagoExercicio);
		    ds.setValueIntoCell("Planilha1", colunaPlanilha++ ,linhaPlanilha, pc.getPagoExercicio().toString());
		     
		     BigDecimal pagoAcumulado = totalPagoAcumulado(c);
		     pc.setPagoAcumulado(pagoAcumulado);
		     ds.setValueIntoCell("Planilha1", colunaPlanilha++ ,linhaPlanilha, pc.getPagoAcumulado().toString());
		     prestacao.add(pc);
		     linhaPlanilha++;
		     colunaPlanilha = 0;
	    }
	    mv.addObject("todos", prestacao); 
	    ds.closeFile();
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
		boolean primeiroAno = false;
		boolean primeiraVez = true;
		
		
		if(lancamentos.get(0).getCompetencia().contains(ano))
		{
			primeiroAno = true;
		}
	
			
	
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
			String dia = obj.getData().toString().split("-")[2];
			String dt_1 = transformar_data(obj.getCompetencia())+"-"+dia;
			
			if(dt_1.contains(periodoAComparar)) //mudar aqui tb
			{ 
				flagmes = 1;
				acumuladorValor = acumuladorValor.add(obj.getValor());
				//acumuladorValorGeral = acumuladorValorGeral.add(acumuladorValor);
				
				if(obj.getPossui_aditivo())
				{
					acumuladorAditivo = acumuladorAditivo.add(obj.getValor_aditivo()) ;
					acumuladorAditivoGeral = acumuladorAditivoGeral.add(acumuladorAditivo);
					
				}
				
				
			}	
		}
		primeiraVez = false;
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
			
			//String dt_l = transformar_data(obj.getData().toString());
			String dia2 = obj.getData().toString().split("-")[2];
			String dt_obj = transformar_data(obj.getCompetencia())+ "-" + dia2;
			
			
			
			
			// System.out.println(indiceElemento);
			if(indiceElemento <= lancamentos.size()-1 && dt_obj.contains(ano)) { //precisa mudar aqui
				if (indiceElemento == lancamentos.size()-1)
				{	
					 l = lancamentos.get(indiceElemento);
				}
				else { l = lancamentos.get(indiceElemento+1);}
				String dia = l.getData().toString().split("-")[2];
				String dt_l = transformar_data(l.getCompetencia())+ "-"+dia;
				
		
				
				if(compararPeriodos(dt_obj, periodosComparados) == false ) //mudar o getData para outra coisa
				{
					
					
					periodosComparados.add(dt_obj); //mudar aqui tb
				}
				
				
				if(dt_l.contains(periodoAComparar) == false && compararPeriodos(dt_l, periodosComparados) == false || dt_l.equals(dt_obj)) //mudar aqui tb
				{
			
					mesesSaldo[mes] = obj.getSaldo_contrato() /*+ mesesAditivos[mes] */;
					acumuladorSaldoGeral.add(obj.getSaldo_contrato()) ; 
					break;
						
					}
			}
		}
		}
	}
		BigDecimal valorAno = new BigDecimal("0");
		BigDecimal valorContrato = new BigDecimal("0");
		BigDecimal valorAditivoAno = new BigDecimal("0");
		BigDecimal valorAditivoContrato = new BigDecimal("0");
		BigDecimal saldoAno = new BigDecimal("0");
		BigDecimal saldoContrato = new BigDecimal("0");
		List<Lancamento> lancamentos_verificados = new ArrayList<Lancamento>();
		
		//Calculo dos valores do ano e do contrato
		for(Lancamento l: lancamentos)
		{
			String [] comp = l.getCompetencia().split("/");
			Integer anoAVerificar = Integer.valueOf(comp[1]);
			
			if(l.getCompetencia().contains(ano))
			{
				valorAno = valorAno.add(l.getValor());
				if(l.getPossui_aditivo() == true && l.getValor_aditivo() != null)
				{
					valorAditivoAno = valorAditivoAno.add(l.getValor_aditivo());
					valorAditivoContrato = valorAditivoContrato.add(l.getValor_aditivo());
				}
				valorContrato = valorContrato.add(l.getValor());
				lancamentos_verificados.add(l);	
				
				if(lancamentos.indexOf(l) == lancamentos.size()-1) //se ele e o ultimo elemento da lista
				{
					
					String [] lcomp1 = lancamentos_verificados.get(0).getCompetencia().split("/");
					saldoAno = l.getSaldo_contrato();
					if(lcomp1[1].equals(ano))
					{
						saldoContrato = saldoAno;
					}
					
					else{
						saldoContrato = saldoContrato.add(saldoAno);
					}
					
				}
			}
			else if(anoAVerificar < Integer.parseInt(ano))
			{
				lancamentos_verificados.add(l);
				valorContrato = valorContrato.add(l.getValor());
				if(l.getPossui_aditivo() == true && l.getValor_aditivo() != null)
				{
					valorAditivoContrato = valorAditivoContrato.add(l.getValor_aditivo());
				}
			}
			else if(anoAVerificar > Integer.parseInt(ano))
			{
				
				
					if(lancamentos.size() > 1)
					{
						
						saldoAno = saldoAno.add(lancamentos_verificados.get(lancamentos_verificados.size()-1).getSaldo_contrato());		
						String [] lcomp2 = lancamentos_verificados.get(0).getCompetencia().split("/");
						if(lcomp2[1].equals(ano))
						{
							saldoContrato = saldoAno;
						}
						
						else{
							saldoContrato = saldoContrato.add(saldoAno);
					}
						
						
					
					}
					
					break;
				
				
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
		mv.addObject("valorGeralAno", valorAno);
		mv.addObject("valorGeralContrato", valorContrato);
		mv.addObject("valorAditivoAno", valorAditivoAno);
		mv.addObject("valorAditivoContrato", valorAditivoContrato);
		mv.addObject("saldoAno", saldoAno);
		mv.addObject("saldoContrato", saldoContrato);
		
		
		
		
		
		
		
		
		
		
		return mv;
	}
	
	public String transformar_data(String data_a_transformar)
	{
		String [] data_l = data_a_transformar.split("/");
		String data_transformada = data_l[1];
		
		switch(data_l[0].toUpperCase())
		{
			case "JANEIRO":
				data_transformada =  data_transformada+'-'+"01";
			break;
			
			case "FEVEREIRO":
				data_transformada =  data_transformada+'-'+"02";
			break;
			
			case "MARÇO":
				data_transformada =  data_transformada+'-'+"03";
			break;
			
			case "ABRIL":
				data_transformada =  data_transformada+'-'+"04";
			break;
			
			case "MAIO":
				data_transformada =  data_transformada+'-'+"05";
			break;
			
			case "JUNHO":
				data_transformada =  data_transformada+'-'+"06";
			break;
			
			case "JULHO":
				data_transformada =  data_transformada+'-'+"07";
			break;
			
			case "AGOSTO":
				data_transformada =  data_transformada+'-'+"08";
			break;
			
			case "SETEMBRO":
				data_transformada =  data_transformada+'-'+"09";
				break;
			case "OUTUBRO":
				data_transformada =  data_transformada+'-'+"10";
			break;
			
			case "NOVEMBRO":
				data_transformada =  data_transformada+'-'+"11";
			break;
			
			case "DEZEMBRO":
				data_transformada =  data_transformada+'-'+"12";
			break;
		}
		return data_transformada;
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
			if(obj.getValor() != null) {
				acumulador = acumulador.add(obj.getValor()); 
			}
			
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
	public ModelAndView pesquisar(String busca, String numero, String objeto, String nome_empresa, @PageableDefault(size=5) Pageable pageable) throws ParseException
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
		
		Comparator<Contrato> cmp = new Comparator<Contrato>() {
	        public int compare(Contrato c1, Contrato c2) {
	          return c2.getUltima_atualizacao().compareTo(c1.getUltima_atualizacao());
	        }
	    };
		
		criteria.setFirstResult(primeiroRegistro);
		criteria.setMaxResults(pageable.getPageSize());
		criteria.addOrder(Order.desc("ultima_atualizacao"));
		if(numero != null && AppUserDetailsService.cusuario.getAuthorities().toString().contains("ROLE_CADASTRAR_CONTRATO") == true) {
			if(busca != null && numero.equals("on")) {
				List<Contrato> todosContratos = contratos.findByNumeroContaining(busca);
				mv.addObject("buscaContratos", todosContratos);
			
				
				return mv;
			}
		}
		else if(objeto != null && AppUserDetailsService.cusuario.getAuthorities().toString().contains("ROLE_CADASTRAR_CONTRATO") == true) {
			if(busca != null && objeto.equals("on")) {
				List<Contrato> todosContratos = contratos.findByObjetoContaining(busca);
				mv.addObject("buscaContratos", todosContratos);
				return mv;
			}
		}
		else if(nome_empresa != null && AppUserDetailsService.cusuario.getAuthorities().toString().contains("ROLE_CADASTRAR_CONTRATO") == true)
		{
			if(busca != null && nome_empresa.equals("on"))
			{
				List<Contrato> todosCEmpresa = todosContratosEmpresa(contratos.findAll(), busca);
				mv.addObject("buscaContratos", todosCEmpresa);
				return mv;
			}
		}

		   
		   if(AppUserDetailsService.cusuario.getAuthorities().toString().contains("ROLE_CADASTRAR_CONTRATO") == false) //Ou seja, se for um gestor comum
		   {
			   
			   List<Contrato> contratosUsuario = currentUser.getContratosGeridos();
			   contratosUsuario.sort(cmp);
			   
			   
			   mv.addObject("buscaContratos", contratosUsuario);
			   return mv;
		   }
		   
		  List<Contrato> tc= criteria.list();
		  mv.addObject("buscaContratos", tc); 
		  return mv;
    
	}
	
	@RequestMapping("/vencidos")
	public ModelAndView pesquisar_vencidos(String busca, String numero, String objeto, String nome_empresa, @PageableDefault(size=15) Pageable pageable) throws ParseException
	{
		
		
		Usuario currentUser = usuarios.findByEmail(AppUserDetailsService.cusuario.getUsername());
		boolean tem_permissao = AppUserDetailsService.cusuario.getAuthorities().toString().contains("ROLE_CADASTRAR_CONTRATO");
		ModelAndView mv = new ModelAndView("/pesquisa/PesquisaContratosVencidos");
		//mv.addObject("usuarios", todosUsuarios);
		mv.addObject("tem_permissao", tem_permissao);
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Contrato.class);
		
		int primeiroRegistro = pageable.getPageNumber()*pageable.getPageSize();
		
		Date dataAtual = new Date();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		
		Comparator<Contrato> cmp = new Comparator<Contrato>() {
	        public int compare(Contrato c1, Contrato c2) {
	          return c2.getUltima_atualizacao().compareTo(c1.getUltima_atualizacao());
	        }
	    };
		
		criteria.setFirstResult(primeiroRegistro);
		criteria.setMaxResults(pageable.getPageSize());
		criteria.addOrder(Order.desc("ultima_atualizacao"));
		if(numero != null && AppUserDetailsService.cusuario.getAuthorities().toString().contains("ROLE_CADASTRAR_CONTRATO") == true) {
			if(busca != null && numero.equals("on")) {
				List<Contrato> todosContratos = todosContratosVencidos(contratos.findByNumeroContaining(busca));
				mv.addObject("buscaContratos", todosContratos);
			
				
				return mv;
			}
		}
		else if(objeto != null && AppUserDetailsService.cusuario.getAuthorities().toString().contains("ROLE_CADASTRAR_CONTRATO") == true) {
			if(busca != null && objeto.equals("on")) {
				List<Contrato> todosContratos = todosContratosVencidos(contratos.findByObjetoContaining(busca));
				mv.addObject("buscaContratos", todosContratos);
				return mv;
			}
		}
		
		else if(nome_empresa != null && AppUserDetailsService.cusuario.getAuthorities().toString().contains("ROLE_CADASTRAR_CONTRATO") == true)
		{
			if(busca != null && nome_empresa.equals("on"))
			{
				List<Contrato> todosCEmpresa = todosContratosEmpresa(todosContratosVencidos(contratos.findAll()), busca);
				mv.addObject("buscaContratos", todosCEmpresa);
				return mv;
			}
		}

		   
		   if(AppUserDetailsService.cusuario.getAuthorities().toString().contains("ROLE_CADASTRAR_CONTRATO") == false) //Ou seja, se for um gestor comum
		   {
			   
			 
			   List<Contrato> contratosUsuario = todosContratosVencidos(currentUser.getContratosGeridos());
			   contratosUsuario.sort(cmp);
			   mv.addObject("buscaContratos", contratosUsuario);
			   return mv;
		   }
		   
		  List<Contrato> tc= todosContratosVencidos(criteria.list());
		  mv.addObject("buscaContratos", tc);
		   
			return mv;
    
	}
	
	@RequestMapping("/vigentes")
	public ModelAndView pesquisar_vigentes(String busca, String numero, String objeto, String nome_empresa, @PageableDefault(size=15) Pageable pageable) throws ParseException
	{
		
		
		Usuario currentUser = usuarios.findByEmail(AppUserDetailsService.cusuario.getUsername());
		boolean tem_permissao = AppUserDetailsService.cusuario.getAuthorities().toString().contains("ROLE_CADASTRAR_CONTRATO");
		ModelAndView mv = new ModelAndView("/pesquisa/PesquisaContratosVigente");
		//mv.addObject("usuarios", todosUsuarios);
		mv.addObject("tem_permissao", tem_permissao);
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Contrato.class);
		
		int primeiroRegistro = pageable.getPageNumber()*pageable.getPageSize();
		
		Date dataAtual = new Date();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		
		Comparator<Contrato> cmp = new Comparator<Contrato>() {
	        public int compare(Contrato c1, Contrato c2) {
	          return c2.getUltima_atualizacao().compareTo(c1.getUltima_atualizacao());
	        }
	    };
		
		criteria.setFirstResult(primeiroRegistro);
		criteria.setMaxResults(pageable.getPageSize());
		criteria.addOrder(Order.desc("ultima_atualizacao"));
		if(numero != null) {
			if(busca != null && numero.equals("on")) {
				List<Contrato> todosContratos = todosContratosVigente(contratos.findByNumeroContaining(busca));
				mv.addObject("buscaContratos", todosContratos);
			
				
				return mv;
			}
		}
		else if(objeto != null) {
			if(busca != null && objeto.equals("on")) {
				List<Contrato> todosContratos = todosContratosVigente(contratos.findByObjetoContaining(busca));
				mv.addObject("buscaContratos", todosContratos);
				return mv;
			}
		}
		
		else if(nome_empresa != null && AppUserDetailsService.cusuario.getAuthorities().toString().contains("ROLE_CADASTRAR_CONTRATO") == true)
		{
			if(busca != null && nome_empresa.equals("on"))
			{
				List<Contrato> todosCEmpresa = todosContratosEmpresa(todosContratosVigente(contratos.findAll()), busca);
				mv.addObject("buscaContratos", todosCEmpresa);
				return mv;
			}
		}

		   
		   if(AppUserDetailsService.cusuario.getAuthorities().toString().contains("ROLE_CADASTRAR_CONTRATO") == false) //Ou seja, se for um gestor comum
		   {
			   
			 
			   List<Contrato> contratosUsuario = todosContratosVigente(currentUser.getContratosGeridos());
			   contratosUsuario.sort(cmp);
			   mv.addObject("buscaContratos", contratosUsuario);
			   return mv;
		   }
		   
		  List<Contrato> tc= todosContratosVigente(criteria.list());
		  mv.addObject("buscaContratos", tc);
		   
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
	public String salvar2(Contrato contratoform, @RequestParam Integer contrato_id_gestor,@RequestParam Integer contrato_id_fiscal, @RequestParam Integer contrato_id_contrato,Errors errors, RedirectAttributes attributes)
	{
		ModelAndView mv = new ModelAndView(CADASTRO_VIEW);
		Contrato contratobd = contratos.findOne(contratoform.getId_contrato());
		Usuario gestor = usuarios.findOne(contrato_id_gestor);
		Usuario fiscal = usuarios.findOne(contrato_id_fiscal);
		Contratado empresa = contratados.findOne(contrato_id_contrato);
		//System.out.println(contratoform.getId_contrato());
		//System.out.println(contratoform.getCpfResponsavel());
			contratobd.setGestor(gestor);
			contratobd.setFiscal(fiscal);
			contratobd.setContratado(empresa);
		
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
	
	@RequestMapping("/resumo_cincoultimos/{id_contrato}")
	public ModelAndView gerar_resumo_5ultimos(@PathVariable("id_contrato") Integer contrato)
	{
		ModelAndView mv = new ModelAndView(RESUMOCINCO_VIEW);
		Contrato c = contratos.findOne(contrato);
		//Inicialização dos vetores
		
		//Ano Atual
		
		BigDecimal [] valores = new BigDecimal[13];
		BigDecimal [] aditivos = new BigDecimal[13];
		BigDecimal [] saldos = new BigDecimal[13];
		
		//Ano Atual -1
		
		BigDecimal [] valores1 = new BigDecimal[13];
		BigDecimal [] aditivos1 = new BigDecimal[13];
		BigDecimal [] saldos1 = new BigDecimal[13];
		
		//Ano Atual -2
		BigDecimal [] valores2 = new BigDecimal[13];
		BigDecimal [] aditivos2 = new BigDecimal[13];
		BigDecimal [] saldos2 = new BigDecimal[13];
		
		//Ano Atual -3
		BigDecimal [] valores3 = new BigDecimal[13];
		BigDecimal [] aditivos3 = new BigDecimal[13];
		BigDecimal [] saldos3 = new BigDecimal[13];
		
		//Ano Atual -4
		
		BigDecimal [] valores4 = new BigDecimal[13];
		BigDecimal [] aditivos4 = new BigDecimal[13];
		BigDecimal [] saldos4 = new BigDecimal[13];
		
		
		
		
		
		for(int j=0; j < 13; j++)
		{
			valores[j] = new BigDecimal(BigDecimal.ZERO.toString());
			aditivos[j] = new BigDecimal(BigDecimal.ZERO.toString());
			saldos[j] = new BigDecimal(BigDecimal.ZERO.toString());
			
			valores1[j] = new BigDecimal(BigDecimal.ZERO.toString());
			aditivos1[j] = new BigDecimal(BigDecimal.ZERO.toString());
			saldos1[j] = new BigDecimal(BigDecimal.ZERO.toString());

			valores2[j] = new BigDecimal(BigDecimal.ZERO.toString());
			aditivos2[j] = new BigDecimal(BigDecimal.ZERO.toString());
			saldos2[j] = new BigDecimal(BigDecimal.ZERO.toString());
			
			valores3[j] = new BigDecimal(BigDecimal.ZERO.toString());
			aditivos3[j] = new BigDecimal(BigDecimal.ZERO.toString());
			saldos3[j] = new BigDecimal(BigDecimal.ZERO.toString());
			
			valores4[j] = new BigDecimal(BigDecimal.ZERO.toString());
			aditivos4[j] = new BigDecimal(BigDecimal.ZERO.toString());
			saldos4[j] = new BigDecimal(BigDecimal.ZERO.toString());
		}
		
		AnoResumo [] resumos = new AnoResumo[5];
/*
		for(int i=0; i < 5; i++)
		{
			resumos[i].setAditivos(aditivos);
			resumos[i].setValores(valores);
			resumos[i].setSaldos(saldos);
		}
	*/	
		//Fim da inicialização dos vetores
		
		Comparator<Lancamento> cmp = new Comparator<Lancamento>() {
	        public int compare(Lancamento l1, Lancamento l2) {
	          return l1.getData().compareTo(l2.getData());
	        }
	    };
	    
	    List<Lancamento> lancs = c.getLancamentos();
	    Collections.sort(lancs, cmp);
	    
	    DateTime ano = new DateTime();
	    Integer anoInteiro = ano.getYear();
	    Integer anoInteiromenos1 = anoInteiro-1;
	    Integer anoInteiromenos2 = anoInteiro-2;
	    Integer anoInteiromenos3 = anoInteiro-3;
	    Integer anoInteiromenos4 = anoInteiro-4;
	    
	    String anoAtual = anoInteiro.toString();
	    String anoAtualMenos1 = anoInteiromenos1.toString();
	    String anoAtualMenos2 = anoInteiromenos2.toString();
	    String anoAtualMenos3 = anoInteiromenos3.toString();
	    String anoAtualMenos4 = anoInteiromenos4.toString();
		
	    valores = gerarValoresResumo(anoAtual, lancs);
		aditivos = gerarAditivosResumo(anoAtual, lancs);
		saldos = gerarSaldosResumo(anoAtual, lancs);
		
	    valores1 = gerarValoresResumo(anoAtualMenos1, lancs);
	    aditivos1 = gerarAditivosResumo(anoAtualMenos1, lancs);
	    saldos1 = gerarSaldosResumo(anoAtualMenos1, lancs);
	    
	    valores2 = gerarValoresResumo(anoAtualMenos2, lancs);
	    aditivos2 = gerarAditivosResumo(anoAtualMenos2, lancs);
	    saldos2 = gerarSaldosResumo(anoAtualMenos2, lancs);
	    
	    valores3 = gerarValoresResumo(anoAtualMenos3, lancs);
	    aditivos3 = gerarAditivosResumo(anoAtualMenos3, lancs);
	    saldos3 = gerarSaldosResumo(anoAtualMenos3, lancs);
	    
	    valores4 = gerarValoresResumo(anoAtualMenos4, lancs);
	    aditivos4 = gerarAditivosResumo(anoAtualMenos4, lancs);
	    saldos4 = gerarSaldosResumo(anoAtualMenos4, lancs);
	    
	    mv.addObject("atual", anoAtual);
	    mv.addObject("atualm1", anoAtualMenos1);
	    mv.addObject("atualm2", anoAtualMenos2);
	    mv.addObject("atualm3", anoAtualMenos3);
	    mv.addObject("atualm4", anoAtualMenos4);
	    
		
		mv.addObject("janeiro_valor_atual", valores[1]);
		mv.addObject("janeiro_aditivo_atual", aditivos[1]);
		mv.addObject("janeiro_saldo_atual", saldos[1]);
		
		mv.addObject("fevereiro_valor_atual", valores[2]);
		mv.addObject("fevereiro_aditivo_atual", aditivos[2]);
		mv.addObject("fevereiro_saldo_atual", saldos[2]);
		
		mv.addObject("marco_valor_atual", valores[3]);
		mv.addObject("marco_aditivo_atual", aditivos[3]);
		mv.addObject("marco_saldo_atual", saldos[3]);
		
		mv.addObject("abril_valor_atual", valores[4]);
		mv.addObject("abril_aditivo_atual", aditivos[4]);
		mv.addObject("abril_saldo_atual", saldos[4]);
		
		mv.addObject("maio_valor_atual", valores[5]);
		mv.addObject("maio_aditivo_atual", aditivos[5]);
		mv.addObject("maio_saldo_atual", saldos[5]);
		
		mv.addObject("junho_valor_atual", valores[6]);
		mv.addObject("junho_aditivo_atual", aditivos[6]);
		mv.addObject("junho_saldo_atual", saldos[6]);
		
		mv.addObject("julho_valor_atual", valores[7]);
		mv.addObject("julho_aditivo_atual", aditivos[7]);
		mv.addObject("julho_saldo_atual", saldos[7]);
		
		mv.addObject("agosto_valor_atual", valores[8]);
		mv.addObject("agosto_aditivo_atual", aditivos[8]);
		mv.addObject("agosto_saldo_atual", saldos[8]);
		
		mv.addObject("setembro_valor_atual", valores[9]);
		mv.addObject("setembro_aditivo_atual", aditivos[9]);
		mv.addObject("setembro_saldo_atual", saldos[9]);
		
		mv.addObject("outubro_valor_atual", valores[10]);
		mv.addObject("outubro_aditivo_atual", aditivos[10]);
		mv.addObject("outubro_saldo_atual", saldos[10]);
		
		mv.addObject("novembro_valor_atual", valores[11]);
		mv.addObject("novembro_aditivo_atual", aditivos[11]);
		mv.addObject("novembro_saldo_atual", saldos[11]);
		
		mv.addObject("dezembro_valor_atual", valores[12]);
		mv.addObject("dezembro_aditivo_atual", aditivos[12]);
		mv.addObject("dezembro_saldo_atual", saldos[12]);
		
		mv.addObject("janeiro_valor_atual1", valores1[1]);
		mv.addObject("janeiro_aditivo_atual1", aditivos1[1]);
		mv.addObject("janeiro_saldo_atual1", saldos1[1]);
		
		mv.addObject("fevereiro_valor_atual1", valores1[2]);
		mv.addObject("fevereiro_aditivo_atual1", aditivos1[2]);
		mv.addObject("fevereiro_saldo_atual1", saldos1[2]);
		
		mv.addObject("marco_valor_atual1", valores1[3]);
		mv.addObject("marco_aditivo_atual1", aditivos1[3]);
		mv.addObject("marco_saldo_atual1", saldos1[3]);
		
		mv.addObject("abril_valor_atual1", valores1[4]);
		mv.addObject("abril_aditivo_atual1", aditivos1[4]);
		mv.addObject("abril_saldo_atual1", saldos1[4]);
		
		mv.addObject("maio_valor_atual1", valores1[5]);
		mv.addObject("maio_aditivo_atual1", aditivos1[5]);
		mv.addObject("maio_saldo_atual1", saldos1[5]);
		
		mv.addObject("junho_valor_atual1", valores1[6]);
		mv.addObject("junho_aditivo_atual1", aditivos1[6]);
		mv.addObject("junho_saldo_atual1", saldos1[6]);
		
		mv.addObject("julho_valor_atual1", valores1[7]);
		mv.addObject("julho_aditivo_atual1", aditivos1[7]);
		mv.addObject("julho_saldo_atual1", saldos1[7]);
		
		mv.addObject("agosto_valor_atual1", valores1[8]);
		mv.addObject("agosto_aditivo_atual1", aditivos1[8]);
		mv.addObject("agosto_saldo_atual1", saldos1[8]);
		
		mv.addObject("setembro_valor_atual1", valores1[9]);
		mv.addObject("setembro_aditivo_atual1", aditivos1[9]);
		mv.addObject("setembro_saldo_atual1", saldos1[9]);
		
		mv.addObject("outubro_valor_atual1", valores1[10]);
		mv.addObject("outubro_aditivo_atual1", aditivos1[10]);
		mv.addObject("outubro_saldo_atual1", saldos1[10]);
		
		mv.addObject("novembro_valor_atual1", valores1[11]);
		mv.addObject("novembro_aditivo_atual1", aditivos1[11]);
		mv.addObject("novembro_saldo_atual1", saldos1[11]);
		
		mv.addObject("dezembro_valor_atual1", valores1[12]);
		mv.addObject("dezembro_aditivo_atual1", aditivos1[12]);
		mv.addObject("dezembro_saldo_atual1", saldos1[12]);
		
		mv.addObject("janeiro_valor_atual2", valores2[1]);
		mv.addObject("janeiro_aditivo_atual2", aditivos2[1]);
		mv.addObject("janeiro_saldo_atual2", saldos2[1]);
		
		mv.addObject("fevereiro_valor_atual2", valores2[2]);
		mv.addObject("fevereiro_aditivo_atual2", aditivos2[2]);
		mv.addObject("fevereiro_saldo_atual2", saldos2[2]);
		
		mv.addObject("marco_valor_atual2", valores2[3]);
		mv.addObject("marco_aditivo_atual2", aditivos2[3]);
		mv.addObject("marco_saldo_atual2", saldos2[3]);
		
		mv.addObject("abril_valor_atual2", valores2[4]);
		mv.addObject("abril_aditivo_atual2", aditivos2[4]);
		mv.addObject("abril_saldo_atual2", saldos2[4]);
		
		mv.addObject("maio_valor_atual2", valores2[5]);
		mv.addObject("maio_aditivo_atual2", aditivos2[5]);
		mv.addObject("maio_saldo_atual2", saldos2[5]);
		
		mv.addObject("junho_valor_atual2", valores2[6]);
		mv.addObject("junho_aditivo_atual2", aditivos2[6]);
		mv.addObject("junho_saldo_atual2", saldos2[6]);
		
		mv.addObject("julho_valor_atual2", valores2[7]);
		mv.addObject("julho_aditivo_atual2", aditivos2[7]);
		mv.addObject("julho_saldo_atual2", saldos2[7]);
		
		mv.addObject("agosto_valor_atual2", valores2[8]);
		mv.addObject("agosto_aditivo_atual2", aditivos2[8]);
		mv.addObject("agosto_saldo_atual2", saldos2[8]);
		
		mv.addObject("setembro_valor_atual2", valores2[9]);
		mv.addObject("setembro_aditivo_atual2", aditivos2[9]);
		mv.addObject("setembro_saldo_atual2", saldos2[9]);
		
		mv.addObject("outubro_valor_atual2", valores2[10]);
		mv.addObject("outubro_aditivo_atual2", aditivos2[10]);
		mv.addObject("outubro_saldo_atual2", saldos2[10]);
		
		mv.addObject("novembro_valor_atual2", valores2[11]);
		mv.addObject("novembro_aditivo_atual2", aditivos2[11]);
		mv.addObject("novembro_saldo_atual2", saldos2[11]);
		
		mv.addObject("dezembro_valor_atual2", valores2[12]);
		mv.addObject("dezembro_aditivo_atual2", aditivos2[12]);
		mv.addObject("dezembro_saldo_atual2", saldos2[12]);
		
		mv.addObject("janeiro_valor_atual3", valores3[1]);
		mv.addObject("janeiro_aditivo_atual3", aditivos3[1]);
		mv.addObject("janeiro_saldo_atual3", saldos3[1]);
		
		mv.addObject("fevereiro_valor_atual3", valores3[2]);
		mv.addObject("fevereiro_aditivo_atual3", aditivos3[2]);
		mv.addObject("fevereiro_saldo_atual3", saldos3[2]);
		
		mv.addObject("marco_valor_atual3", valores3[3]);
		mv.addObject("marco_aditivo_atual3", aditivos3[3]);
		mv.addObject("marco_saldo_atual3", saldos3[3]);
		
		mv.addObject("abril_valor_atual3", valores3[4]);
		mv.addObject("abril_aditivo_atual3", aditivos3[4]);
		mv.addObject("abril_saldo_atual3", saldos3[4]);
		
		mv.addObject("maio_valor_atual3", valores3[5]);
		mv.addObject("maio_aditivo_atual3", aditivos3[5]);
		mv.addObject("maio_saldo_atual3", saldos3[5]);
		
		mv.addObject("junho_valor_atual3", valores3[6]);
		mv.addObject("junho_aditivo_atual3", aditivos3[6]);
		mv.addObject("junho_saldo_atual3", saldos3[6]);
		
		mv.addObject("julho_valor_atual3", valores3[7]);
		mv.addObject("julho_aditivo_atual3", aditivos3[7]);
		mv.addObject("julho_saldo_atual3", saldos3[7]);
		
		mv.addObject("agosto_valor_atual3", valores3[8]);
		mv.addObject("agosto_aditivo_atual3", aditivos3[8]);
		mv.addObject("agosto_saldo_atual3", saldos3[8]);
		
		mv.addObject("setembro_valor_atual3", valores3[9]);
		mv.addObject("setembro_aditivo_atual3", aditivos3[9]);
		mv.addObject("setembro_saldo_atual3", saldos3[9]);
		
		mv.addObject("outubro_valor_atual3", valores3[10]);
		mv.addObject("outubro_aditivo_atual3", aditivos3[10]);
		mv.addObject("outubro_saldo_atual3", saldos3[10]);
		
		mv.addObject("novembro_valor_atual3", valores3[11]);
		mv.addObject("novembro_aditivo_atual3", aditivos3[11]);
		mv.addObject("novembro_saldo_atual3", saldos3[11]);
		
		mv.addObject("dezembro_valor_atual3", valores3[12]);
		mv.addObject("dezembro_aditivo_atual3", aditivos3[12]);
		mv.addObject("dezembro_saldo_atual3", saldos3[12]);
		
		mv.addObject("janeiro_valor_atual4", valores4[1]);
		mv.addObject("janeiro_aditivo_atual4", aditivos4[1]);
		mv.addObject("janeiro_saldo_atual4", saldos4[1]);
		
		mv.addObject("fevereiro_valor_atual4", valores4[2]);
		mv.addObject("fevereiro_aditivo_atual4", aditivos4[2]);
		mv.addObject("fevereiro_saldo_atual4", saldos4[2]);
		
		mv.addObject("marco_valor_atual4", valores4[3]);
		mv.addObject("marco_aditivo_atual4", aditivos4[3]);
		mv.addObject("marco_saldo_atual4", saldos4[3]);
		
		mv.addObject("abril_valor_atual4", valores4[4]);
		mv.addObject("abril_aditivo_atual4", aditivos4[4]);
		mv.addObject("abril_saldo_atual4", saldos4[4]);
		
		mv.addObject("maio_valor_atual4", valores4[5]);
		mv.addObject("maio_aditivo_atual4", aditivos4[5]);
		mv.addObject("maio_saldo_atual4", saldos4[5]);
		
		mv.addObject("junho_valor_atual4", valores4[6]);
		mv.addObject("junho_aditivo_atual4", aditivos4[6]);
		mv.addObject("junho_saldo_atual4", saldos4[6]);
		
		mv.addObject("julho_valor_atual4", valores4[7]);
		mv.addObject("julho_aditivo_atual4", aditivos4[7]);
		mv.addObject("julho_saldo_atual4", saldos4[7]);
		
		mv.addObject("agosto_valor_atual4", valores4[8]);
		mv.addObject("agosto_aditivo_atual1", aditivos4[8]);
		mv.addObject("agosto_saldo_atual4", saldos4[8]);
		
		mv.addObject("setembro_valor_atual4", valores4[9]);
		mv.addObject("setembro_aditivo_atual4", aditivos4[9]);
		mv.addObject("setembro_saldo_atual4", saldos4[9]);
		
		mv.addObject("outubro_valor_atual4", valores4[10]);
		mv.addObject("outubro_aditivo_atual4", aditivos4[10]);
		mv.addObject("outubro_saldo_atual4", saldos4[10]);
		
		mv.addObject("novembro_valor_atual4", valores4[11]);
		mv.addObject("novembro_aditivo_atual4", aditivos4[11]);
		mv.addObject("novembro_saldo_atual4", saldos4[11]);
		
		mv.addObject("dezembro_valor_atual4", valores4[12]);
		mv.addObject("dezembro_aditivo_atual4", aditivos4[12]);
		mv.addObject("dezembro_saldo_atual4", saldos4[12]);
		
		
		return mv;
	}
	
	public BigDecimal [] gerarValoresResumo(String ano, List<Lancamento> lancs)
	{
		int flagmes;
		BigDecimal acumuladorValor;
		BigDecimal acumuladorValorGeral;
		BigDecimal [] valores = new BigDecimal [13];
		String periodoAComparar;
		
		int indiceElemento;
		
		
		for(int i=1; i < 13; i++)
		{
			flagmes = 0;
			acumuladorValor =new BigDecimal("0.0");
		
			if(i > 0 && i < 10) {
				periodoAComparar = ano+ "-0"+ Integer.toString(i);
			}else {periodoAComparar = ano+ "-"+ Integer.toString(i);}
			Iterator it = lancs.iterator();
			while(it.hasNext())
			{
				Lancamento obj = (Lancamento) it.next();
				String dt_1 = transformar_data(obj.getCompetencia());
				
				if(dt_1.contains(periodoAComparar))
				{ 
					flagmes = 1;
					acumuladorValor = acumuladorValor.add(obj.getValor());
					
					//acumuladorSaldo += obj.getSaldo_contrato();				
				}	
			}
			if(flagmes == 1)
			{
				meses.add(i);
			}
			valores[i] = acumuladorValor;
			
			
	}
		
		return valores;
	}
	
	public BigDecimal [] gerarSaldosResumo(String ano, List<Lancamento> lanc)
	{
		BigDecimal [] mesesSaldo = new BigDecimal[13];
		List<String> periodosComparados = new ArrayList<String>();
		
		String periodoAComparar;
		Iterator itfila = meses.iterator();
		Integer indiceElemento = 0;
		
		BigDecimal acumuladorSaldoGeral = new BigDecimal("0");
	
		
		while (itfila.hasNext()) {
			if(meses.peek() != null ) {
				int mes = meses.peek();
				meses.poll();
			
			if(mes > 0 && mes < 10) {
				periodoAComparar = ano+ "-0"+ Integer.toString(mes);
			}else {periodoAComparar = ano+ "-"+ Integer.toString(mes);}
			
		//	periodosComparados.add(periodoAComparar);
			
		
		Iterator it2 = lanc.iterator();
		while (it2.hasNext()) {
			Lancamento obj = (Lancamento) it2.next();
			indiceElemento = lanc.indexOf(obj);
			
			Lancamento l;	
			String dt_obj = transformar_data(obj.getCompetencia());
			
			// System.out.println(indiceElemento);
			if(indiceElemento <= lanc.size()-1 && dt_obj.contains(ano)) {
				if (indiceElemento == lanc.size()-1)
				{	
					 l = lanc.get(indiceElemento);
				}
				else { l = lanc.get(indiceElemento+1);}
				String dt_l = transformar_data(l.getCompetencia());
			//	System.out.println(l.getData().toString().contains(periodoAComparar));
			//	System.out.println(periodoAComparar);
			//	System.out.println(l.getData().toString());
			//	System.out.println(lancamentos.indexOf(l) +" " + lancamentos.size());
				// System.out.println(obj.getData().toString() + "---" + l.getData().toString()) ;
				
				if(compararPeriodos(dt_obj, periodosComparados) == false ) //precisamos colocar mais uma condição aqui nesse IF
				{
					
					
					periodosComparados.add(dt_obj);
				}
				
				
				if(dt_l.contains(periodoAComparar) == false && compararPeriodos(dt_l, periodosComparados) == false || dt_l.equals(dt_obj))
				{
				/*	if(lancamentos.indexOf(l) == lancamentos.size()-1 && lancamentos.indexOf(obj) != lancamentos.size()-2) { 
						mesesSaldo[mes] = l.getSaldo_contrato();
						break;
					}
					else { */
					
					mesesSaldo[mes] = obj.getSaldo_contrato() /*+ mesesAditivos[mes] */;
					acumuladorSaldoGeral.add(obj.getSaldo_contrato()) ; 
					break;
						//}
					}
			}
		}
		}
			 
	}
		return mesesSaldo;
}
	
	public BigDecimal [] gerarAditivosResumo(String ano, List<Lancamento> lancamentos)
	{
	

		Queue<Integer> meses = new LinkedList();
		int i, indiceElemento;
		String periodoAComparar;

		BigDecimal acumuladorAditivo = new BigDecimal("0");
	
		BigDecimal acumuladorAditivoGeral = new BigDecimal("0");
		
		BigDecimal [] mesesSaldo = new BigDecimal[13];
		BigDecimal [] mesesValores = new BigDecimal[13];
		BigDecimal [] mesesAditivos = new BigDecimal[13];
		List<String> periodosComparados = new ArrayList<String>();
		for(int y=0; y <13; y++)
		{
			mesesAditivos[y] = new BigDecimal("0.0");
		}
		int flagmes = 0;
		int flagoffset = 0;
		
	
	for(i=1; i < 13; i++)
	{
	
		flagmes = 0;
		
		acumuladorAditivo = new BigDecimal("0.0");
		if(i > 0 && i < 10) {
			periodoAComparar = ano+ "-0"+ Integer.toString(i);
		}else {periodoAComparar = ano+ "-"+ Integer.toString(i);}
		Iterator it = lancamentos.iterator();
		while(it.hasNext())
		{
			Lancamento obj = (Lancamento) it.next();
			String dt_1 = transformar_data(obj.getCompetencia());
			
			
			if(dt_1.contains(periodoAComparar))
			{ 
				flagmes = 1;
				if(obj.getPossui_aditivo())
				{
					acumuladorAditivo = acumuladorAditivo.add(obj.getValor_aditivo()) ;
					acumuladorAditivoGeral = acumuladorAditivoGeral.add(acumuladorAditivo);
					
				}				
			}	
		}
	/*	if(flagmes == 1)
		{
			meses.add(i);
		}
		*/
		mesesAditivos[i] = acumuladorAditivo;
		
}
	return mesesAditivos;
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
			if(processos.findAll() != null) {
			if((p.getTipo_processo().getTipo().equals("Renovação") || p.getTipo_processo().getTipo().equals("Nova licitação") ) && p.getContrato() == null)
			{
				processosRenovacao.add(p);
			}
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
	
	public List<Contrato> todosContratosVencidos(List<Contrato> c)
	{
		List<Contrato> contratos_vencidos = new ArrayList<Contrato>();
		for(Contrato contrato : c)
		{
			DateTime data_vencimento = new DateTime(contrato.getData_vencimento());
			DateTime data_atual = new DateTime();
			Days d = Days.daysBetween(data_atual, data_vencimento);
			
			if(d.getDays() <= 0)
			{
				contratos_vencidos.add(contrato);
			}
			
		}
		return contratos_vencidos;
	}
	
	public List<Contrato> todosContratosVigente(List<Contrato> c)
	{
		List<Contrato> contratos_vigentes = new ArrayList<Contrato>();
		for(Contrato contrato : c)
		{
			DateTime data_vencimento = new DateTime(contrato.getData_vencimento());
			DateTime data_atual = new DateTime();
			Days d = Days.daysBetween(data_atual, data_vencimento);
			
			if(d.getDays() > 0)
			{
				contratos_vigentes.add(contrato);
			}
			
		}
		return contratos_vigentes;
	}
	
	public List<Contrato> todosContratosEmpresa(List<Contrato> c, String Empresa)
	{
		List<Contrato> contratos_empresa = new ArrayList<Contrato>();
		for(Contrato contrato :c)
		{
			if(contrato.getContratado().getNome().contains(Empresa))
			{
				contratos_empresa.add(contrato);
			}
		}
		return contratos_empresa;
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
	
	public Integer totalAditivos(Contrato contrato)
	{
		Integer nAditivos = 0;
			for(Lancamento l:contrato.getLancamentos())
			{
				if(l.getPossui_aditivo() == true || l.getAditivo_n() != null || l.getValor_aditivo() != null)
				{
					nAditivos++;
				}
			}
		
		return nAditivos;
	}

	public BigDecimal totalValoresAditivos(List<Contrato> contratos)
	{
		BigDecimal totalValor = new BigDecimal(BigDecimal.ZERO.toString());
		for(Contrato c:contratos)
		{
			for(Lancamento l:c.getLancamentos())
			{
				if(l.getPossui_aditivo() == true || l.getAditivo_n() != null || l.getValor_aditivo() != null)
				{
				
					totalValor = totalValor.add(l.getValor_aditivo());
				}
			}
		}
		return totalValor;
	}

	public BigDecimal totalValoresAditivos(Contrato contrato)
	{
		BigDecimal totalValor = new BigDecimal(BigDecimal.ZERO.toString());
		for(Lancamento l:contrato.getLancamentos())
			{
				if((l.getPossui_aditivo() == true || l.getAditivo_n() != null) && l.getValor_aditivo() != null)
				{
					totalValor = totalValor.add(l.getValor_aditivo());
				}
			}
		
		return totalValor;
	}

	public BigDecimal totalPagoExercicio(Contrato contrato, String ano)
	{
		BigDecimal acumulado = new BigDecimal(BigDecimal.ZERO.toString());
		for(Lancamento l:contrato.getLancamentos())
		{
			if(l.getData().toString().contains(ano))
			{
				acumulado = acumulado.add(l.getValor());
			}
		}
		return acumulado;
	}

	public BigDecimal totalPagoAcumulado(Contrato contrato)
	{
		BigDecimal acumulado = new BigDecimal(BigDecimal.ZERO.toString());
		for(Lancamento l:contrato.getLancamentos())
		{
				acumulado = acumulado.add(l.getValor());
		}
		return acumulado;
	}
	
	
	
	
	
}
