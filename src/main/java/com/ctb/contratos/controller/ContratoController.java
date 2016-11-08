package com.ctb.contratos.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
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
import com.ctb.contratos.repository.Processos;
import com.ctb.contratos.repository.Usuarios;

@Controller
@RequestMapping("/transparenciactb/contratos")

public class ContratoController {
	private static final String CADASTRO_VIEW = "/cadastro/CadastroContrato"; 
	private static final String LANCAMENTOS_VIEW = "/pesquisa/PesquisaLancamentos";
	private static final String VISUALIZAR_VIEW = "/visualizacao/VisualizarContrato";
	private static final String RESUMO_VIEW = "/visualizacao/ResumoContrato";
	private static final String GERARRESUMO_VIEW = "/visualizacao/GerarResumoContrato";
	public static Integer numero_contrato =0;
	static final ArrayList<AditivoSetting> aditivos = new ArrayList();
	
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
	
	@RequestMapping("/novo")
	public ModelAndView novo()
	{
		ModelAndView mv = new ModelAndView(CADASTRO_VIEW);
		mv.addObject(new Contrato());
		//numero_contrato
		//mv.addObject("todosNiveisUsuario", Nivel.values());
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
	
	public float[] gerarSaldos(List<Lancamento> lancamentos, String anoACalcular, int quantidadeAnos)
	{
		
		//calcularSaldos(lancamentos);
		int i;
		String periodoAComparar;
		float acumuladorValor;
		float acumuladorSaldo;
		float acumuladorAditivo = 0;	
		float vetorSomaSaldos[] = new float[13];

		Comparator<Lancamento> cmp = new Comparator<Lancamento>() {
		        public int compare(Lancamento l1, Lancamento l2) {
		          return l1.getData().compareTo(l2.getData());
		        }
		    };

		Collections.sort(lancamentos, cmp);
		
		//Se ano a calcular for o mesmo que o ano inicial
		
		for(i=1; i < 13; i++)
		{
			
			acumuladorValor =0;
			acumuladorSaldo = 0;
			if(i > 0 && i < 10) {
				periodoAComparar = anoACalcular+ "-0"+ Integer.toString(i);
			}else {periodoAComparar = anoACalcular+ "-"+ Integer.toString(i);}
			Iterator it = lancamentos.iterator();
			while(it.hasNext())
			{
				Lancamento obj = (Lancamento) it.next();
			
				
				if(obj.getData().toString().contains(periodoAComparar))
				{ 
					//System.out.println(obj.getValor());
					
					acumuladorValor += obj.getValor();
					acumuladorSaldo += obj.getSaldo_contrato();
					if(obj.getPossui_aditivo())
					{
						acumuladorAditivo += obj.getValor_aditivo();
						
					}

					
								
				}
				
		}
			
			vetorSomaSaldos[i] = acumuladorValor ;
	}
		AditivoSetting aditivosomado = new AditivoSetting(0);
		aditivosomado.setValor(acumuladorAditivo);
		aditivosomado.setPeriodo(anoACalcular);
		aditivos.add(aditivosomado);
		return vetorSomaSaldos;
	}
	

	
	@RequestMapping(value="/resumo/{id_contrato}", method= RequestMethod.GET)
	public ModelAndView resumo(@PathVariable("id_contrato") Integer Id_contrato, String ano)
	{
		List<Lancamento> lancamentos = contratos.findOne(Id_contrato).getLancamentos();
		ModelAndView mv = new ModelAndView(RESUMO_VIEW);
		Contrato c = contratos.findOne(Id_contrato);
		String primeiro = lancamentos.get(1).getData().toString();
		String ultimo = lancamentos.get(lancamentos.size()-1).getData().toString();
		Calendar cal1 = Calendar.getInstance();
		Calendar cal2 = Calendar.getInstance();
		cal1.setTime(lancamentos.get(0).getData());
		cal2.setTime(lancamentos.get(lancamentos.size()-1).getData());
		int anoprimeiro = cal1.get(Calendar.YEAR);
		int anoultimo = cal2.get(Calendar.YEAR);
		int quantidadeAnos = anoultimo-anoprimeiro;
		float acumuladorValor;
		float acumuladorAditivo = 0;
		float tabelaValores[] = new float[13];
		float tabelaSaldos[][] = new float[14][quantidadeAnos+1];
		float acumuladorSub = 0;
				
		int anoInt = Integer.parseInt(ano);
	//	tabelaValores = gerarSaldos(lancamentos, ano, quantidadeAnos,acumuladorAditivo);
		
		if( anoprimeiro == anoInt)
		{
			tabelaValores = gerarSaldos(lancamentos, ano, quantidadeAnos);
		}
		//System.out.println(acumuladorAditivo);
		tabelaSaldos[0][0] = c.getSaldo_contrato();
		for(int i=1; i < 13; i++)
		{
			acumuladorSub = acumuladorSub+tabelaValores[i]; 
			tabelaSaldos[i][0] = tabelaValores[i];
		}
		
		if(quantidadeAnos > 0) 
		{
		
			tabelaSaldos[0][1] = tabelaSaldos[0][0] - acumuladorSub;
		}
		tabelaSaldos[13][0] = tabelaSaldos[0][0] + aditivos.get(0).getValor() - acumuladorSub;
		
		for(int j=1; j < quantidadeAnos; j++)
		{
			for(int i=0; i < 14; i++)
			{
				if(i == 0)
				{
					tabelaSaldos[i][j] = tabelaSaldos[i][j-1] - tabelaSaldos[13][j-1];
				}
				else if (i ==13) {System.out.println("Calcular o total");}
				else {
					int novoano = Integer.parseInt(ano+1);
					tabelaValores = gerarSaldos(lancamentos, String.valueOf(novoano), quantidadeAnos);}
			}
		}
		System.out.println(tabelaSaldos[0][0]);
		System.out.println(aditivos.get(0).getValor());
		System.out.println(acumuladorSub);
		System.out.println(tabelaSaldos[13][0]);
		
		
		//Funcao Calcular Saldos
		
		
		
		//TODO: Primeiro o algoritmo tem que percorrer todos os lancamentos e fazer uma soma por mes(com aditivo)[FEITO]
		//TODO: Após feito o processamento, alimentamos a tabela de saldos
		//TODO: Exibir informações
		
		//Ordenando dados 
		
		
		
		//Primeiro
		
		
		
	
		
		//Segundo
		/*
		acumuladorValor = 0;
		for(int j=0; j < quantidadeAnos; j++)
		{
			for(int i=0; i < 14; i++)
			{
				if(i ==0 && j==0)
				{
					tabelaSaldos[i][j] = c.getSaldo_contrato(); 
				}
				else if(i ==0 && j != 0)
				{
					tabelaSaldos[i][j] = tabelaSaldos[0][j] - tabelaSaldos[i+1][j-1];
				}
				
				else if(i == 13)
				{
					tabelaSaldos[i][j] = acumuladorValor;
				}
				else {
					tabelaValores = gerarSaldos(lancamentos, ano, quantidadeAnos);
				}
				
			}
		}
		*/
		//Terceiro
		
		
		return mv;
		
	}

	@RequestMapping("/visualizar/{id_contrato}")
	public ModelAndView visualizar(@PathVariable("id_contrato") Integer Id_contrato)
	{
		
		
		ModelAndView mv = new ModelAndView(VISUALIZAR_VIEW);
		Contrato c = contratos.findOne(Id_contrato);
		float totalAditivosPorcentagem;
		float totalAditivos = calcularAditivos(c.getLancamentos());
		
		float valorTotal = calcularValorTotal(c.getLancamentos());
		if(totalAditivos == 0) {
			totalAditivosPorcentagem = c.getSaldo_contrato();
		} else {totalAditivosPorcentagem = totalAditivos;}
		float porcentagemConcluida = (valorTotal/(totalAditivosPorcentagem)*100);

		
		mv.addObject("contrato", c);
		mv.addObject("total_aditivos", totalAditivos);
		mv.addObject("total", valorTotal);
		mv.addObject("totalcomaditivos", c.getValor_contrato() + totalAditivos);
		mv.addObject("saldo_contrato", c.getSaldo_contrato());
		mv.addObject("porcentagem_concluida", porcentagemConcluida);
		mv.addObject("porcentagem_a_concluir", 100 - porcentagemConcluida);
		//	mv.addObject(new Contrato());
		//numero_contrato
		//mv.addObject("todosNiveisUsuario", Nivel.values());
		return mv;
	}


	public float calcularAditivos(List<Lancamento> lancamentos)
	{
		float acumulador = 0;
		Iterator it = lancamentos.iterator();
		
		while(it.hasNext())
		{
			Lancamento obj = (Lancamento) it.next();
			if(obj.getPossui_aditivo())
			{
				acumulador += obj.getValor_aditivo(); 
			}
			
		}
		return acumulador;
	}
	
	public float calcularAditivos(List<Lancamento> lancamentos, String periodo)
	{
		float acumulador = 0;
		Iterator it = lancamentos.iterator();
		
		while(it.hasNext())
		{
			Lancamento obj = (Lancamento) it.next();
			if(obj.getPossui_aditivo() && obj.getData().toString().contains(periodo))
			{
				acumulador += obj.getValor_aditivo(); 
			}
			
		}
		return acumulador;
	}
	
	
	public float calcularValorTotal(List<Lancamento> lancamentos)
	{
		float acumulador = 0;
		Iterator it = lancamentos.iterator();
		
		while(it.hasNext())
		{
			Lancamento obj = (Lancamento) it.next();
		//	System.out.println(obj.getValor());
			acumulador += obj.getValor(); 
			
			
		}
		return acumulador;
	}
			
	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView salvar(@Validated Contrato contrato, @RequestParam Integer contrato_id_gestor, @RequestParam Integer contrato_id_fiscal, @RequestParam Integer contrato_id_contrato, RedirectAttributes attributes, Errors errors)
	{
		ModelAndView mv = new ModelAndView(CADASTRO_VIEW);
		if(errors.hasErrors())
		{
			
			return mv;
		}
		
		Contratado empresa = contratados.findOne(contrato_id_contrato);
		Usuario gestor = usuarios.findOne(contrato_id_gestor);
		Usuario fiscal = usuarios.findOne(contrato_id_fiscal);
		
		
		
		contrato.setContratado(empresa);
		contrato.setFiscal(fiscal);
		contrato.setGestor(gestor);
		contrato.setSaldo_contrato(contrato.getValor_contrato());
		
		contratos.save(contrato);		
		attributes.addFlashAttribute("mensagem", "Contrato salvo com sucesso!");	
		return mv;
	}
	
	@RequestMapping(method= RequestMethod.GET)
	public ModelAndView pesquisar(String busca, String numero, String objeto) throws ParseException
	{
		ModelAndView mv = new ModelAndView("/pesquisa/PesquisaContratos");
		//mv.addObject("usuarios", todosUsuarios);
		
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
		   List<Contrato> todosContratos= contratos.findAll();
		   mv.addObject("buscaContratos", todosContratos);
	    
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
	
	@ModelAttribute("todosGestores")
	public List<Usuario> todosGestores()
	{
		return usuarios.findAll();
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
	
	
}
