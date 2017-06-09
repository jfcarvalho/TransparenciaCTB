package com.ctb.contratos.controller;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.http.HttpServletRequest;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Months;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.ctb.Mailer;
import com.ctb.Processo;
import com.ctb.TipoProcesso;
import com.ctb.contratos.model.Contratado;
import com.ctb.contratos.model.Contrato;
import com.ctb.contratos.model.Lancamento;
import com.ctb.contratos.model.Usuario;
import com.ctb.contratos.repository.Contratados;
import com.ctb.contratos.repository.Contratos;
import com.ctb.contratos.repository.Lancamentos;
import com.ctb.contratos.repository.Processos;
import com.ctb.contratos.repository.Usuarios;
import com.ctb.security.AppUserDetailsService;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.read.biff.BiffException;

@Controller
@RequestMapping("/transparenciactb/")


public class HomesController {
	public static final long TEMPO_MENSAGEM = (1000 * 60*60);
	private String HOME_VIEW = "/home/PaginaInicial";
	static int id_inicial = 984;
	static int id_proc =550;
	@Autowired
	private Usuarios usuarios;
	
	@Autowired
	private Processos processos;
	
	@Autowired
	private Contratos contratos;
	
	@Autowired
	private Lancamentos lancamentos;
	@Autowired
	private Contratados contratadas;
	@Autowired
	private Mailer mailer;
	ModelAndView mv = new ModelAndView(HOME_VIEW);	
	
	@RequestMapping(method=RequestMethod.GET)
	public ModelAndView index() throws BiffException, IOException, ParseException
	{
			
		
		mv.addObject(new HomesController());
		List<Contrato> contratosgeridos = contratosGeridos();
		HashMap<String,String> teste = new HashMap<String,String>();
		Iterator it = contratosgeridos.iterator();
		BigDecimal acumuladoValorJaneiro = new BigDecimal("0");
		BigDecimal acumuladoValorFevereiro = new BigDecimal("0");
		BigDecimal acumuladoValorMarco = new BigDecimal("0");
		BigDecimal acumuladoValorAbril = new BigDecimal("0");
		BigDecimal acumuladoValorMaio = new BigDecimal("0");
		BigDecimal acumuladoValorJunho = new BigDecimal("0");
		BigDecimal acumuladoValorJulho = new BigDecimal("0");
		BigDecimal acumuladoValorAgosto = new BigDecimal("0");
		BigDecimal acumuladoValorSetembro = new BigDecimal("0");
		BigDecimal acumuladoValorOutubro = new BigDecimal("0");
		BigDecimal acumuladoValorNovembro = new BigDecimal("0");
		BigDecimal acumuladoValorDezembro = new BigDecimal("0");
		int  flagmes;
		String periodoAComparar = new String();
		String ano = new String();
		DateTime date = new DateTime();
		ano = Integer.toString(date.getYear());
		teste = contratosVSvalores();
		/*alimentarSistema("C:\\Users\\TECI\\Downloads\\Contratos-SGC\\ALBERONI.xls", 4, 27);
		alimentarSistema("C:\\Users\\TECI\\Downloads\\Contratos-SGC\\BASE.xls", 12, 31);
		alimentarSistema("C:\\Users\\TECI\\Downloads\\Contratos-SGC\\BATUR.xls", 28, 32);
		alimentarSistema("C:\\Users\\TECI\\Downloads\\Contratos-SGC\\BMW.xls", 15, 17);
		alimentarSistema("C:\\Users\\TECI\\Downloads\\Contratos-SGC\\BRASPEB.xls", 1, 68);
		alimentarSistema("C:\\Users\\TECI\\Downloads\\Contratos-SGC\\BRASPECL.xls", 2, 63);
		alimentarSistema("C:\\Users\\TECI\\Downloads\\Contratos-SGC\\CALDAS.xls", 13, 50);
		alimentarSistema("C:\\Users\\TECI\\Downloads\\Contratos-SGC\\ECT.xls", 23, 62);
		alimentarSistema("C:\\Users\\TECI\\Downloads\\Contratos-SGC\\EGBA.xls", 6, 44);
		alimentarSistema("C:\\Users\\TECI\\Downloads\\Contratos-SGC\\ENTEL.xls", 18, 55);
		
	
		alimentarSistema("C:\\Users\\TECI\\Downloads\\Contratos-SGC\\IEL.xls", 20, 72);
		alimentarSistema("C:\\Users\\TECI\\Downloads\\Contratos-SGC\\IPSE.xls", 29, 16);
		alimentarSistema("C:\\Users\\TECI\\Downloads\\Contratos-SGC\\LARCLEAN.xls", 35, 16);
		alimentarSistema("C:\\Users\\TECI\\Downloads\\Contratos-SGC\\LRX.xls", 36, 18);
		alimentarSistema("C:\\Users\\TECI\\Downloads\\Contratos-SGC\\PERFORMANCE.xls", 14, 18);
		alimentarSistema("C:\\Users\\TECI\\Downloads\\Contratos-SGC\\PLUS.xls", 37, 53);
		alimentarSistema("C:\\Users\\TECI\\Downloads\\Contratos-SGC\\PRODEB.xls", 7, 59);
		alimentarSistema("C:\\Users\\TECI\\Downloads\\Contratos-SGC\\PROSEGUR.xls", 30, 59);
		alimentarSistema("C:\\Users\\TECI\\Downloads\\Contratos-SGC\\REALIZA.xls", 27, 54);
		alimentarSistema("C:\\Users\\TECI\\Downloads\\Contratos-SGC\\UNIMED.xls", 26, 38);
		*/
		
		/*alimentarSistema("C:\\Users\\TECI\\Downloads\\Contratos-SGC\\MJR.xls", 2, 28);
		alimentarSistema("C:\\Users\\TECI\\Downloads\\Contratos-SGC\\VIVO.xls", 8, 20);
		alimentarSistema("C:\\Users\\TECI\\Downloads\\Contratos-SGC\\TTC1.xls", 38, 19);
		alimentarSistema("C:\\Users\\TECI\\Downloads\\Contratos-SGC\\TRIVALE.xls", 41, 23);
		alimentarSistema("C:\\Users\\TECI\\Downloads\\Contratos-SGC\\SOS.xls", 32, 26);
		alimentarSistema("C:\\Users\\TECI\\Downloads\\Contratos-SGC\\SOFCON.xls", 31, 17);
		alimentarSistema("C:\\Users\\TECI\\Downloads\\Contratos-SGC\\TELEMARLD.xls", 24, 40);
		alimentarSistema("C:\\Users\\TECI\\Downloads\\Contratos-SGC\\TELEMARREDE.xls", 25, 73);
		alimentarSistema("C:\\Users\\TECI\\Downloads\\Contratos-SGC\\FFGARE.xls", 17, 20);
		*/
		//alimentarSistema("C:\\Users\\TECI\\Downloads\\Contratos-SGC\\FFSCONSTRUCOES.xls", 16, 24);
		//alimentarSistema("C:\\Users\\TECI\\Downloads\\Contratos-SGC\\DUCTOR.xls", 10, 169);
		//alimentarSistema("C:\\Users\\TECI\\Downloads\\Contratos-SGC\\ENGEVIX.xls", 11, 94);
		
		//alimentarSistema("C:\\Users\\TECI\\Downloads\\Contratos-SGC\\GIBBOR.xls", 19, 35);
	//	alimentarSistema("C:\\Users\\TECI\\Downloads\\Contratos-SGC\\FFPASSEIO.xls", 42, 15);
		//alimentarSistema("C:\\Users\\TECI\\Downloads\\Contratos-SGC\\VOLUNTARIAS.xls", 45, 18);
		//alimentarSistema("C:\\Users\\TECI\\Downloads\\Contratos-SGC\\FPMF.xls", 44, 15);
	//alimentarSistema("C:\\Users\\TECI\\Downloads\\Contratos-SGC\\PROJCONSULT.xls", 40, 22);
		
		   System.out.println("inicio");  
	         Timer timer = null;  
	         if (timer == null) {  
	             timer = new Timer();  
	             TimerTask tarefa = new TimerTask() {  
	                 public void run() {  
	                     try {  
	                         System.out.println("Teste Agendador");  
	                        //chamar metodo  
	                     } catch (Exception e) {  
	                         e.printStackTrace();  
	                     }  
	                 }  
	             };  
	             timer.scheduleAtFixedRate(tarefa, TEMPO_MENSAGEM, TEMPO_MENSAGEM);  
	         }
	         
		while(it.hasNext())
		{
			Contrato obj = (Contrato) it.next();
			List<Lancamento> lancamentos = obj.getLancamentos();
			
			for(int i=1; i < 13; i++)
			{
				flagmes = 0;
				if(i > 0 && i < 10) {
					periodoAComparar = "-0"+ Integer.toString(i)+"-";
				}else {periodoAComparar =  "-"+ Integer.toString(i)+"-";}
				Iterator it2 = lancamentos.iterator();
				while(it2.hasNext()) {
				
					Lancamento obj2 = (Lancamento) it2.next();
				
					
					if(obj2.getData().toString().contains(periodoAComparar))
					{ 
						//lagmes = 1;
						switch(i)
						{
							case 1:
								acumuladoValorJaneiro = acumuladoValorJaneiro.add(obj2.getValor());
								break;
							case 2:
								acumuladoValorFevereiro = acumuladoValorFevereiro.add(obj2.getValor());
								break;
							case 3:
								acumuladoValorMarco = acumuladoValorMarco.add(obj2.getValor());
								break;
							case 4:
								acumuladoValorAbril = acumuladoValorAbril.add(obj2.getValor());
								break;
							case 5:
								acumuladoValorMaio = acumuladoValorMaio.add(obj2.getValor());
								break;
							case 6:
								acumuladoValorJunho = acumuladoValorJunho.add(obj2.getValor());
								break;
							case 7:
								acumuladoValorJulho = acumuladoValorJulho.add(obj2.getValor());
								break;
							case 8:
								acumuladoValorAgosto = acumuladoValorAgosto.add(obj2.getValor());
								
								break;
							case 9:
								acumuladoValorSetembro = acumuladoValorSetembro.add(obj2.getValor());
								break;
							case 10:
								acumuladoValorOutubro = acumuladoValorOutubro.add(obj2.getValor());
						
								break;
							case 11:
								acumuladoValorNovembro = acumuladoValorNovembro.add(obj2.getValor());
								break;
							case 12:
								acumuladoValorDezembro = acumuladoValorDezembro.add(obj2.getValor());
								break;
						}				
					}	
				}
		}
	}
		mv.addObject("valorjaneiro", acumuladoValorJaneiro);
		mv.addObject("valorfevereiro", acumuladoValorFevereiro);
		mv.addObject("valormarco", acumuladoValorMarco);
		mv.addObject("valorabril", acumuladoValorAbril);
		mv.addObject("valormaio", acumuladoValorMaio);
		mv.addObject("valorjunho", acumuladoValorJunho);
		mv.addObject("valorjulho", acumuladoValorJulho);
		mv.addObject("valoragosto", acumuladoValorAgosto);
		mv.addObject("valorsetembro", acumuladoValorSetembro);
		mv.addObject("valoroutubro", acumuladoValorOutubro);
		mv.addObject("valornovembro", acumuladoValorNovembro);
		mv.addObject("valordezembro", acumuladoValorDezembro);
		mv.addObject("ntotal_contratos", contratos.count());
		mv.addObject("ntotal_empresas", contratadas.count());
		mv.addObject("ntotal_gestores", usuarios.count());
		mv.addObject("ntotal_lancamentos", lancamentos.count());
		mv.addObject("empresas", teste.toString());
		return mv;
}	

	public void alimentarSistema(String caminhoPlanilha, Integer id_contrato, Integer linhasALer) throws  IOException, BiffException, ParseException
	{
		
		
		  WorkbookSettings workbookSettings = new WorkbookSettings();
		 workbookSettings.setEncoding( "ISO-8859-1" );
		Workbook workbook = Workbook.getWorkbook(new File(caminhoPlanilha), workbookSettings);
		Sheet sheet = workbook.getSheet(0);
		Integer linhas = sheet.getRows();
		Integer linhaAtual = 14;
		Cell c1 = sheet.getCell(0, 14);
		Integer ano = Integer.parseInt(c1.getContents());
		Integer [] diasMeses = gerarVetorDias();
		String dataLancamento = new String();
		Processo p = new Processo();
		Contrato c = contratos.findOne(id_contrato);
		while(linhaAtual < linhasALer)
		{
			Cell clinha = sheet.getCell(2, linhaAtual);
			Cell anoAtual = sheet.getCell(0, linhaAtual);
			Cell medicao = sheet.getCell(1, linhaAtual);
			Cell n_nota_fiscal = sheet.getCell(3, linhaAtual);
			Cell valorContrato = sheet.getCell(6,linhaAtual);
			//Cell numeroAditivo = sheet.getCell(10, linhaAtual);
			//Cell valorAditivo = sheet.getCell(11, linhaAtual);
			Cell numeroAditivo = sheet.getCell(8, linhaAtual);
			Cell valorAditivo = sheet.getCell(9, linhaAtual);
			Cell observacaoContrato = sheet.getCell(10, linhaAtual);
			//Cell observacaoContrato = sheet.getCell(12, linhaAtual);
			//Cell saldoContrato = sheet.getCell(9, linhaAtual);
			Cell saldoContrato = sheet.getCell(7, linhaAtual);
			Cell numeroProcesso = sheet.getCell(4, linhaAtual);
			Cell dataProcesso = sheet.getCell(5, linhaAtual);
			
			
			id_inicial++;
			if(Integer.parseInt(anoAtual.getContents()) != ano)
			{
				diasMeses = gerarVetorDias();
				ano = Integer.parseInt(anoAtual.getContents());
			}
			switch(clinha.getContents().toUpperCase())
			{
				case "JANEIRO":
					diasMeses[1] = diasMeses[1]+1;
					dataLancamento = ano.toString() +"-01-0"+diasMeses[1].toString();
					//System.out.println(dataLancamento);
				break;
				case "FEVEREIRO":
					diasMeses[2] = diasMeses[2]+1;
					dataLancamento = ano.toString() +"-02-0"+diasMeses[2].toString();
					//System.out.println(dataLancamento);
				break;
				case "MARÇO":
					diasMeses[3] = diasMeses[3]+1;
					dataLancamento = ano.toString() +"-03-0"+diasMeses[3].toString();
					//System.out.println(dataLancamento);
				break;
				case "ABRIL":
					diasMeses[4] = diasMeses[4]+1;
					dataLancamento = ano.toString() +"-04-0"+diasMeses[4].toString();
					//System.out.println(dataLancamento);
				break;
				case "MAIO":
					diasMeses[5] = diasMeses[5]+1;
					dataLancamento = ano.toString() +"-05-0"+diasMeses[5].toString();
					//System.out.println(dataLancamento);
				break;
				case "JUNHO":
					diasMeses[6] = diasMeses[6]+1;
					dataLancamento = ano.toString() +"-06-0"+diasMeses[6].toString();
					//System.out.println(dataLancamento);
				break;
				case "JULHO":
					diasMeses[7] = diasMeses[7]+1;
					dataLancamento = ano.toString() +"-07-0"+diasMeses[7].toString();
					//System.out.println(dataLancamento);
				break;
				case "AGOSTO":
					diasMeses[8] = diasMeses[8]+1;
					dataLancamento = ano.toString() +"-08-0"+diasMeses[8].toString();
					//System.out.println(dataLancamento);
				break;
				case "SETEMBRO":
					diasMeses[9] = diasMeses[9]+1;
					dataLancamento = ano.toString() +"-09-0"+diasMeses[9].toString();
					//System.out.println(dataLancamento);
				break;
				case "OUTUBRO":
					diasMeses[10] = diasMeses[10]+1;
					dataLancamento = ano.toString() +"-10-0"+diasMeses[10].toString();
					//System.out.println(dataLancamento);
				break;
				case "NOVEMBRO":
					diasMeses[11] = diasMeses[11]+1;
					dataLancamento = ano.toString() +"-11-0"+diasMeses[11].toString();
					//System.out.println(dataLancamento);
				break;
				case "DEZEMBRO":
					diasMeses[12] = diasMeses[12]+1;
					dataLancamento = ano.toString() +"-12-0"+diasMeses[12].toString();
					//System.out.println(dataLancamento);
				break;
			}
			Lancamento l = new Lancamento();
			
			String Competencia = clinha.getContents()+"/"+anoAtual.getContents();
			l.setId_lancamento(id_inicial);	
			DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			Date date = (Date)formatter.parse(dataLancamento);
			l.setData(date);
			l.setDataliquidacao(null);
			l.setLiquidado(false);
			l.setMeses_prorrogacao(0);
			l.setHora("00:00");
			l.setDoe_aditivo(null);
			l.setCompetencia(Competencia);
			l.setMedicao(medicao.getContents());
			if(!n_nota_fiscal.getContents().equals(""))
			{
				l.setNumero_nota_fiscal(n_nota_fiscal.getContents());
			}
				if(!valorContrato.getContents().equals("") || !valorContrato.getContents().isEmpty()) {
					Double v1 = Double.parseDouble(valorContrato.getContents().replaceAll(",", "."));
					BigDecimal valor = new BigDecimal(v1); //problema aqui
					l.setValor(valor);
				}
				else
				{
					BigDecimal valor = new BigDecimal(BigDecimal.ZERO.toString()); //problema aqui
					l.setValor(valor);
				}
				if(saldoContrato.getContents().length() != 0) {
					Double s1 = Double.parseDouble(saldoContrato.getContents().replaceAll(",", "."));
					BigDecimal saldo = new BigDecimal(s1); //problema aqui
					l.setSaldo_contrato(saldo);
				}
				else
				{
					BigDecimal saldo = new BigDecimal(BigDecimal.ZERO.toString()); //problema aqui
					l.setSaldo_contrato(saldo);
				}
			
			l.setObservacao(observacaoContrato.getContents());
			if(!valorAditivo.getContents().equals("") || !numeroAditivo.getContents().equals("") || numeroAditivo.getContents().length() != 0 || valorAditivo.getContents().length() != 0)
			{
				if(numeroAditivo.getContents().length() != 0) {
					l.setAditivo_n(Integer.parseInt(numeroAditivo.getContents()));
				}
				if(!valorAditivo.getContents().equals("")) {
					
					Double a = Double.parseDouble(valorAditivo.getContents().replaceAll(",", "."));
					BigDecimal aditivo = new BigDecimal(a);
					l.setValor_aditivo(aditivo);
					l.setPossui_aditivo(true);
				}
			}
			else if(valorAditivo.getContents().equals("") && !numeroAditivo.getContents().isEmpty())
			{
				BigDecimal aditivo = new BigDecimal(BigDecimal.ZERO.toString());
				l.setValor_aditivo(aditivo);
				l.setPossui_aditivo(true);
			}
			else {
				l.setPossui_aditivo(false);
			}
			
		if(!numeroProcesso.getContents().contains("[]") && !dataProcesso.getContents().contains("[]")) //Ajeitar aqui!!
		{
			//Processo p = new Processo();
			id_proc++;
			p.setId_processo(id_proc);
			
			p.setNumero_ci("000000");
			p.setPago(true);
			p.setTipo_processo(TipoProcesso.Pagamento);
			p.setNumero_processo(numeroProcesso.getContents());
			if(dataProcesso.getContents().equals(""))
			{
				Date data = (Date)formatter.parse(dataLancamento);
				p.setData_abertura(data);
				p.setData_pagamento(data);

			}
			else
			{
				Date data = (Date)formatter.parse(dataProcesso.getContents());
				p.setData_abertura(data);
				p.setData_pagamento(data);

			}
		
			p.setPago(true);
			

		}
		
			
			Processo paux = processos.findOne(1);
			
		//	processos.save(paux);
		//	l.setProcesso(paux);
			
			
		
			linhaAtual++;
			l.setContrato(c);
			l.setLiquidado(true);
			if(!numeroProcesso.getContents().contains("[]") && !dataProcesso.getContents().contains("[]"))
			{
				processos.save(p);
				lancamentos.save(l);
				l.setProcesso(p);
				p.setLancamento(l);
				processos.save(p);
				lancamentos.save(l);
		}
			else
			{
				lancamentos.save(l);
				l.setProcesso(paux);
				lancamentos.save(l);
			}
	}
	}
	
	public Integer [] gerarVetorDias()
	{
		Integer [] diasMeses = new Integer[13];
		for(int i=0; i < 13; i++) //Setar todos os dias como 0
		{
			diasMeses[i] = 0;
		}
		return diasMeses;
	}

	public HashMap<String, String> contratosVSvalores()
	{
		List<Contrato> listaContratos = contratos.findAll();
		Iterator it = listaContratos.iterator();
		BigDecimal acumuladoValor = new BigDecimal("0"); 
		HashMap<String,String> contratosEvalores = new HashMap<String,String>();
		
		while(it.hasNext())
		{
			Contrato obj = (Contrato) it.next();
			List<Lancamento> lancamentos = obj.getLancamentos();
			acumuladoValor = new BigDecimal("0"); 
			Iterator it2 = lancamentos.iterator();
			
			while(it2.hasNext())
			{
				Lancamento l = (Lancamento) it2.next();
				acumuladoValor = acumuladoValor.add(l.getValor());
			}
			contratosEvalores.put(obj.getContratado().getNome(), acumuladoValor.toString());
		
		}
			return contratosEvalores;
	}
	
	public void zerarAvisos(List<Contrato> c)
	{
		for(Contrato ct:c)
		{
			ct.setAvisos_dias(criar_vetor_booleano());
		}
		contratos.save(c);
		
	}
	
	public Integer checarVetorAvisos(Contrato c)
	{
		int i=0;
		int verdadeiros=0;
		while  (i < c.getAvisos_dias().length)
		{
			if(c.getAvisos_dias()[i] == true)
			{
				verdadeiros++;
				if(i < c.getAvisos_dias().length-1 ) {
					return i+1;
				}
		   }
			i++;
		}
		if(verdadeiros == c.getAvisos_dias().length)
		{
			return -1;
		}
		return i;
	}
	
	public void checarAvisosContratos(List<Usuario> usuarios)
	{
		
		Iterator it = usuarios.iterator();
		
	while(it.hasNext()) 
	{
		Usuario user = (Usuario) it.next();
		List<Contrato> ct_xvencimento90 = new ArrayList<Contrato>();
		List<Contrato> ct_xvencimento85 = new ArrayList<Contrato>();
		List<Contrato> ct_xvencimento80 = new ArrayList<Contrato>();
		List<Contrato> ct_xvencimento75 = new ArrayList<Contrato>();
		List<Contrato> ct_xvencimento70 = new ArrayList<Contrato>();
		List<Contrato> ct_xvencimento65 = new ArrayList<Contrato>();
		List<Contrato> ct_xvencimento60 = new ArrayList<Contrato>();
		List<Contrato> contratos_geridos = user.getContratosGeridos();
		Iterator it2 = contratos_geridos.iterator();
			while(it2.hasNext())
			{
				Contrato contrato = (Contrato) it2.next();
				int diasVencimento =  dias_vencimento(contrato);
				if(contrato.getProcesso() == null && diasVencimento <= 90) //Ou seja, se o processo de renovação ou para elaboração de uma nova licitação não for aberto ainda
				{
					boolean [] avisos_dias = contrato.getAvisos_dias();
					if(diasVencimento <= 90 && diasVencimento > 85 && avisos_dias[0] == false)
					{
						avisos_dias[0] = true;
						contratos.save(contrato);
						ct_xvencimento90.add(contrato);
					}
					else if(diasVencimento <= 85 && diasVencimento > 80 && avisos_dias[1] == false)
					{
						avisos_dias[1] = true;
						contratos.save(contrato);
						ct_xvencimento85.add(contrato);
					}
					else if(diasVencimento <= 80 && diasVencimento > 75 && avisos_dias[0] == false)
					{
						avisos_dias[2] = true;
						contratos.save(contrato);
						ct_xvencimento80.add(contrato);
					}
					else if(diasVencimento <= 75 && diasVencimento > 70 && avisos_dias[0] == false)
					{
						avisos_dias[3] = true;
						contratos.save(contrato);
						ct_xvencimento75.add(contrato);
					}
					else if(diasVencimento <= 70 && diasVencimento > 65 && avisos_dias[0] == false)
					{
						avisos_dias[4] = true;
						contratos.save(contrato);
						ct_xvencimento70.add(contrato);
					}
					else if(diasVencimento <= 65 && diasVencimento > 60 && avisos_dias[0] == false)
					{
						avisos_dias[5] = true;
						contratos.save(contrato);
						ct_xvencimento65.add(contrato);
					}
					else if(diasVencimento <= 60 && diasVencimento > 50 && avisos_dias[0] == false)
					{
						avisos_dias[6] = true;
						contratos.save(contrato);
						ct_xvencimento60.add(contrato);
					}
					
				}
			}
	if(ct_xvencimento90.size() > 0)
	{
		mailer.enviar_vencimento_gestor(ct_xvencimento90, "jfcarvalho@ctb.ba.gov.br", 90);
	}
	if(ct_xvencimento85.size() > 0)
	{
		mailer.enviar_vencimento_gestor(ct_xvencimento85, "jfcarvalho@ctb.ba.gov.br", 85);
	}
	if(ct_xvencimento80.size() > 0)
	{
		mailer.enviar_vencimento_gestor(ct_xvencimento80, "jfcarvalho@ctb.ba.gov.br", 80);
	}
	if(ct_xvencimento75.size() > 0)
	{
		mailer.enviar_vencimento_gestor(ct_xvencimento75, "jfcarvalho@ctb.ba.gov.br", 75);
	}
	if(ct_xvencimento70.size() > 0)
	{
		mailer.enviar_vencimento_gestor(ct_xvencimento70, "jfcarvalho@ctb.ba.gov.br", 70);
	}
	if(ct_xvencimento65.size() > 0)
	{
		mailer.enviar_vencimento_gestor(ct_xvencimento65, "jfcarvalho@ctb.ba.gov.br", 65);
	}
	if(ct_xvencimento60.size() > 0)
	{
		mailer.enviar_vencimento_gestor(ct_xvencimento60, "jfcarvalho@ctb.ba.gov.br", 60);
	}
	}
	
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
	
	
	@ModelAttribute("permissao")
	public boolean temPermissao() {
		return AppUserDetailsService.cusuario.getAuthorities().toString().contains("ROLE_CADASTRAR_CONTRATO");
	}
	
	@ModelAttribute("home_gestor_contratos")
	public boolean homeGestor() {
		return AppUserDetailsService.cusuario.getAuthorities().toString().contains("ROLE_HOME_GESTOR_CONTRATOS");
	}
	
	@ModelAttribute("registrar_processo")
	public boolean homeProcesso() {
		return AppUserDetailsService.cusuario.getAuthorities().toString().contains("ROLE_CADASTRAR_PROCESSO");
	}
	
	
	@ModelAttribute("contratosgeridos")
	public List<Contrato> contratosGeridos()
	{
		List<Contrato> todosContratos = contratos.findAll();
		List<Contrato> contratosGeridos = new ArrayList<Contrato>();
		Usuario gestor = usuarios.findByEmail(AppUserDetailsService.cusuario.getUsername());
		Iterator it = todosContratos.iterator();
	
		while(it.hasNext())
		{
			Contrato obj = (Contrato) it.next();
			
			if(obj.getGestor().getId_usuario() == gestor.getId_usuario()) {
				contratosGeridos.add(obj);
			}
		}
		
	    
		return contratosGeridos;
	
	}
	
	public List<Contrato> contratosGeridos(Integer id_gestor)
	{
		List<Contrato> todosContratos = contratos.findAll();
		List<Contrato> contratosGeridos = new ArrayList<Contrato>();
		Usuario gestor = usuarios.findOne(id_gestor);
		Iterator it = todosContratos.iterator();
	
		while(it.hasNext())
		{
			Contrato obj = (Contrato) it.next();
			
			if(obj.getGestor().getId_usuario() == gestor.getId_usuario()) {
				contratosGeridos.add(obj);
			}
		}
		
	    
		return contratosGeridos;
	
	}
	
	
	
	@ModelAttribute("ultimoslancamentos")
	public List<Lancamento> ultimosLancamentos()
	{
		List<Contrato> contratosGeridos = contratosGeridos();
		List<Lancamento> lancamentos = new ArrayList<Lancamento>();
		Iterator it = contratosGeridos.iterator();
	
		while(it.hasNext())
		{
			Contrato obj = (Contrato) it.next();
			List<Lancamento> laux = obj.getLancamentos();
			Iterator it2 = laux.iterator();
			while(it2.hasNext())
			{
				Lancamento l = (Lancamento) it2.next();
				
				lancamentos.add(l);
			}
			
			
		}
		Comparator<Lancamento> cmp = new Comparator<Lancamento>() {
	        public int compare(Lancamento l1, Lancamento l2) {
	          return l2.getData().compareTo(l1.getData());
	        }
	    
		};
		Collections.sort(lancamentos, cmp);
		
		List<Lancamento> novaListaLimitada = new ArrayList<Lancamento>();
		int contador = 0; 
		for (Lancamento l : lancamentos)
		{
			if(contador < 10 ) {
				novaListaLimitada.add(l);
				contador++;
			}
			else {break;}
		}
		
		
		return novaListaLimitada;
	
	}
	
	
	@ModelAttribute("ultimoslancamentos_todos_contratos")
	public List<Lancamento> ultimosLancamentosTodosContratos()
	{
		List<Lancamento> todos = lancamentos.findAll();
		Comparator<Lancamento> cmp = new Comparator<Lancamento>() {
	        public int compare(Lancamento l1, Lancamento l2) {
	          return l2.getData().compareTo(l1.getData());
	        }
	    
		};
		Collections.sort(todos, cmp);
		
		List<Lancamento> novaListaLimitada = new ArrayList<Lancamento>();
		int contador = 0; 
		for (Lancamento l : todos)
		{
			if(contador < 15 ) {
				novaListaLimitada.add(l);
				contador++;
			}
			else {break;}
		}
		
		
		return novaListaLimitada;
	
	}
	
	@ModelAttribute("ultimoslancamentos_sem_processo")
	public List<Lancamento> ultimosLancamentosSemProcesso()
	{
		List<Lancamento> todos = lancamentos.findAll();
		Comparator<Lancamento> cmp = new Comparator<Lancamento>() {
	        public int compare(Lancamento l1, Lancamento l2) {
	          return l2.getData().compareTo(l1.getData());
	        }
	    
		};
		//Collections.sort(todos, cmp);
		
		List<Lancamento> novaListaLimitada = new ArrayList<Lancamento>(); 
		for (Lancamento l : todos)
		{
			if(l.getProcesso() == null) { //Ou seja, se nao foi registrado ianda nenhum processo de pagamento.
				novaListaLimitada.add(l);
			}	
		}
		
		
		return novaListaLimitada;
	
	}
	
	
	@ModelAttribute("n_contratosgeridos")
	public int ncontratosGeridos()
	{
		List<Contrato> todosContratos = contratos.findAll();
		Usuario gestor = usuarios.findByEmail(AppUserDetailsService.cusuario.getUsername());
		int n_geridos = 0;
		Iterator it = todosContratos.iterator();
	
		while(it.hasNext())
		{
			Contrato obj = (Contrato) it.next();
			
			if(obj.getGestor().getId_usuario() == gestor.getId_usuario()) {
				n_geridos++;
			}
		}
		return n_geridos;
	
	}
	
	@ModelAttribute("gestor")
	public Usuario gestor()
	{
		 Usuario gestor = usuarios.findByEmail(AppUserDetailsService.cusuario.getUsername());
		 return gestor;
	}
	
	@ModelAttribute("vencimento90dias")
	public List<Contrato> vencimento90()
	{
		List<Contrato> contratosgeridos = contratosGeridos();
		List<Contrato> contratosavencer = new ArrayList<Contrato>();
		Iterator it = contratosgeridos.iterator();
		while(it.hasNext())
		{
			Contrato obj = (Contrato) it.next();
			DateTime inicio = new DateTime();
			DateTime fim = new DateTime(obj.getData_vencimento());
			Days d = Days.daysBetween(inicio, fim);
			if(d.getDays() <= 90 && d.getDays() >0)
			{
				contratosavencer.add(obj);
			}
		}
		return contratosavencer;
	}
	
	//Função para retornar todos  os contratos com x dias para o vencimento
	public List<Contrato> vencimentoX(Integer x, Integer Id_gestor)
	{
		List<Contrato> contratosgeridos = contratosGeridos(Id_gestor);
		List<Contrato> contratosavencer = new ArrayList<Contrato>();
		Iterator it = contratosgeridos.iterator();
		while(it.hasNext())
		{
			Contrato obj = (Contrato) it.next();
			DateTime inicio = new DateTime();
			DateTime fim = new DateTime(obj.getData_vencimento());
			Days d = Days.daysBetween(inicio, fim);
			if(d.getDays() <= x && d.getDays() >0)
			{
				contratosavencer.add(obj);
			}
		}
		return contratosavencer;
	}
	
	@ModelAttribute("vencimento90dias_todos")
	public List<Contrato> vencimento90_todos()
	{
		List<Contrato> contratosgeridos = contratos.findAll();
		List<Contrato> contratosavencer = new ArrayList<Contrato>();
		Iterator it = contratosgeridos.iterator();
		while(it.hasNext())
		{
			Contrato obj = (Contrato) it.next();
			DateTime inicio = new DateTime();
			DateTime fim = new DateTime(obj.getData_vencimento());
			Days d = Days.daysBetween(inicio, fim);
			if(d.getDays() <= 90 && d.getDays() >0)
			{
				contratosavencer.add(obj);
			}
		}
		return contratosavencer;
	}
	
	@ModelAttribute("acumladovalor")
	public BigDecimal acumuladovalor()
	{
		List<Contrato> contratosgeridos = contratosGeridos();
		Iterator it = contratosgeridos.iterator();
		BigDecimal acumuladoValor = new BigDecimal("0"); 
		while(it.hasNext())
		{
			Contrato obj = (Contrato) it.next();
			List<Lancamento> lancamentos = obj.getLancamentos();
			
			Iterator it2 = lancamentos.iterator();
			
			while(it2.hasNext())
			{
				Lancamento l = (Lancamento) it2.next();
				acumuladoValor = acumuladoValor.add(l.getValor());
			}
		
		}
		return acumuladoValor;
	}
	
	@ModelAttribute("acumladoaditivo")
	public BigDecimal acumuladoaditivo()
	{
		List<Contrato> contratosgeridos = contratosGeridos();
		Iterator it = contratosgeridos.iterator();
		BigDecimal acumuladoValorAditivo = new BigDecimal("0"); 
		while(it.hasNext())
		{
			Contrato obj = (Contrato) it.next();
			List<Lancamento> lancamentos = obj.getLancamentos();
			
			Iterator it2 = lancamentos.iterator();
			
			while(it2.hasNext())
			{
				Lancamento l = (Lancamento) it2.next();
				if(l.getPossui_aditivo() == true)
				{
					acumuladoValorAditivo = acumuladoValorAditivo.add(l.getValor_aditivo()); 
				}
				//acumuladoValor = acumuladoValor.add(l.getValor());
			}
		
		}
		return acumuladoValorAditivo;
	}
	//Otimizar isso aqui!!! muita função duplicada q pode se transformar em uma fnção só com flag
	@ModelAttribute("lancamentospagos")
	public BigDecimal Lancamentospagos()
	{
		List<Contrato> contratosgeridos = contratosGeridos();
		Iterator it = contratosgeridos.iterator();
		BigDecimal acumuladoPago = new BigDecimal("0"); 
		while(it.hasNext())
		{
			Contrato obj = (Contrato) it.next();
			List<Lancamento> lancamentos = obj.getLancamentos();
			
			Iterator it2 = lancamentos.iterator();
			
			while(it2.hasNext())
			{
				Lancamento l = (Lancamento) it2.next();
				if(l.getProcesso()  !=  null) 
				{
					acumuladoPago = acumuladoPago.add(l.getValor());
				}
				//acumuladoValor = acumuladoValor.add(l.getValor());
			}
		
		}
		return acumuladoPago;
	}

	@ModelAttribute("lancamentosnpagos")
	public BigDecimal LancamentosNaopagos()
	{
		List<Contrato> contratosgeridos = contratosGeridos();
		Iterator it = contratosgeridos.iterator();
		BigDecimal acumuladoNaoPago = new BigDecimal("0"); 
		while(it.hasNext())
		{
			Contrato obj = (Contrato) it.next();
			List<Lancamento> lancamentos = obj.getLancamentos();
			
			Iterator it2 = lancamentos.iterator();
			
			while(it2.hasNext())
			{
				Lancamento l = (Lancamento) it2.next();
				if(l.getProcesso()  ==  null) 
				{
					acumuladoNaoPago = acumuladoNaoPago.add(l.getValor());
				}
				//acumuladoValor = acumuladoValor.add(l.getValor());
			}
		
		}
		return acumuladoNaoPago;
	}
	
	@ModelAttribute("nomes_empresas")
	public List<String> pegarNomeEmpresas()
	{
		List<Contratado> empresas = contratadas.findAll();
		List<String> nomes = new ArrayList<String>();
		Iterator it = empresas.iterator();
		while (it.hasNext())
		{
			Contratado c = (Contratado) it.next();
			nomes.add(c.getNome());
		}
		return nomes;
	}
	public Integer dias_vencimento(Contrato c)
	{
		DateTime inicio = new DateTime();
		DateTime fim = new DateTime(c.getData_vencimento());
		Days d = Days.daysBetween(inicio, fim);
		return d.getDays();
	}
		
}
