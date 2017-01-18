package com.ctb.contratos.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

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
import com.ctb.contratos.model.Contratado;
import com.ctb.contratos.model.Contrato;
import com.ctb.contratos.model.Lancamento;
import com.ctb.contratos.model.Usuario;
import com.ctb.contratos.repository.Contratados;
import com.ctb.contratos.repository.Contratos;
import com.ctb.contratos.repository.Lancamentos;
import com.ctb.contratos.repository.Usuarios;
import com.ctb.security.AppUserDetailsService;

@Controller
@RequestMapping("/transparenciactb/")


public class HomesController {
	private String HOME_VIEW = "/home/PaginaInicial";
	
	@Autowired
	private Usuarios usuarios;
	
	@Autowired
	private Contratos contratos;
	
	@Autowired
	private Lancamentos lancamentos;
	@Autowired
	private Contratados contratadas;
	@Autowired
	private Mailer mailer;
	
	@RequestMapping(method=RequestMethod.GET)
	public ModelAndView index()
	{
		ModelAndView mv = new ModelAndView(HOME_VIEW);		
		//checarAvisosContratos(contratos.findAll());
		mv.addObject(new HomesController());
		List<Contrato> contratosgeridos = contratosGeridos();
		HashMap<String,String> teste = new HashMap<String,String>();
		Iterator it = contratosgeridos.iterator();
		//atualizarContratos();
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
	
		//mailer.enviar_vencimento_gestor(vencimento90_todos());
		//mailer.pegarContrato();
		
		
		
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
								System.out.println("Valor de AGosto: " + acumuladoValorAgosto);
								break;
							case 9:
								acumuladoValorSetembro = acumuladoValorSetembro.add(obj2.getValor());
								break;
							case 10:
								acumuladoValorOutubro = acumuladoValorOutubro.add(obj2.getValor());
								System.out.println("Valor de Outubro: " + acumuladoValorOutubro);
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
		//System.out.println(teste.toString());
		mv.addObject("empresas", teste.toString());
		return mv;
}	
	public void atualizarContratos()
	{
		List<Contrato> todos = contratos.findAll();
		for(Contrato contrato: todos)
		{
			contrato.setAvisos_dias(criar_vetor_booleano());
		}
		contratos.save(todos);
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
	
	public void checarAvisosContratos(List<Usuario> users)
	{
		for(Usuario user: users) {
			List<Contrato> cts = user.getContratosGeridos();
			List<Contrato> ct_xvencimento90 = new ArrayList<Contrato>();
			List<Contrato> ct_xvencimento85 = new ArrayList<Contrato>();
			List<Contrato> ct_xvencimento80 = new ArrayList<Contrato>();
			List<Contrato> ct_xvencimento75 = new ArrayList<Contrato>();
			List<Contrato> ct_xvencimento70 = new ArrayList<Contrato>();
			List<Contrato> ct_xvencimento65 = new ArrayList<Contrato>();
			List<Contrato> ct_xvencimento60 = new ArrayList<Contrato>();
			if(ct_xvencimento90.size() > 0)
			{
				
			}
			if(ct_xvencimento85.size() > 0)
			{
				
			}
			if(ct_xvencimento80.size() > 0)
			{
				
			}
			if(ct_xvencimento75.size() > 0)
			{
				
			}
			if(ct_xvencimento70.size() > 0)
			{
				
			}
			if(ct_xvencimento65.size() > 0)
			{
				
			}
			if(ct_xvencimento60.size() > 0)
			{
				
			}
			
			for(Contrato aux: cts)
			{
				if(aux.getProcesso() == null && dias_vencimento(aux) <= 90) //Ou seja, se o processo de renovação ou para elaboração de uma nova licitação não for aberto ainda
				{
					Integer dias = checarVetorAvisos(aux);
				//	boolean [] avisos_dias = criar_vetor_booleano();
					switch (dias)
					{
						
						case 1:
							//Enviar mensagem com 85 dias
					//		List<Contrato> ct_xvencimento85 = vencimentoX(85, aux.getGestor().getId_usuario());
							ct_xvencimento85 = vencimentoX(85, aux.getGestor().getId_usuario());
						//	if(ct_xvencimento85.size() > 0 ) {
							//	mailer.enviar_vencimento_gestor(ct_xvencimento85, aux.getGestor().getEmail(), 85);
							//	avisos_dias[1] = true;
						//		aux.setAvisos_dias(avisos_dias);
						//		contratos.save(ct_xvencimento85);
						//	}
						break;
						
						case 2:
							ct_xvencimento80 = vencimentoX(80, aux.getGestor().getId_usuario());
							/*if(ct_xvencimento80.size() > 0 ) {
								mailer.enviar_vencimento_gestor(ct_xvencimento80, aux.getGestor().getEmail(), 80);
								avisos_dias[2] = true;
								aux.setAvisos_dias(avisos_dias);
								contratos.save(ct_xvencimento80);
							}*/
							//Enviar mensagem com 80 dias
						break;
						
						case 3:
							//Enviar mensagem com 75 dias
						    ct_xvencimento75 = vencimentoX(75, aux.getGestor().getId_usuario());
							/*if(ct_xvencimento75.size() > 0 ) {
								mailer.enviar_vencimento_gestor(ct_xvencimento75, aux.getGestor().getEmail(), 75);
								avisos_dias[3] = true;
								aux.setAvisos_dias(avisos_dias);
								contratos.save(ct_xvencimento75);
							}
							*/
						break;
						
						case 4:
							//Enviar mensagem com 70
							ct_xvencimento70 = vencimentoX(70, aux.getGestor().getId_usuario());
							/*if(ct_xvencimento70.size() > 0 ) {
								mailer.enviar_vencimento_gestor(ct_xvencimento70, aux.getGestor().getEmail(), 70);
								avisos_dias[4] = true;
								aux.setAvisos_dias(avisos_dias);
								contratos.save(ct_xvencimento70);
							}
							*/
						break;
						
						case 5:
							//Enviar mensagem com 65 dias
							
							ct_xvencimento65 = vencimentoX(65, aux.getGestor().getId_usuario());
							/*if(ct_xvencimento65.size() > 0 ) {
								mailer.enviar_vencimento_gestor(ct_xvencimento65, aux.getGestor().getEmail(), 65);
								avisos_dias[5] = true;
								aux.setAvisos_dias(avisos_dias);
								contratos.save(ct_xvencimento65);
							}*/
						break;
						
						case 6:
							//Enviar mensagem com 60 dias
							
								ct_xvencimento60 = vencimentoX(60, aux.getGestor().getId_usuario());
								/*if(ct_xvencimento60.size() > 0 ) {
									mailer.enviar_vencimento_gestor(ct_xvencimento60, aux.getGestor().getEmail(), 60);
									avisos_dias[6] = true;
									aux.setAvisos_dias(avisos_dias);
									contratos.save(ct_xvencimento60);
							}
							*/
							break;
						
						case 7:
							//Enviar mensagem com 90 dias
							ct_xvencimento90 = vencimentoX(90, aux.getGestor().getId_usuario());
							/*if(ct_xvencimento90.size() > 0 ) {
								mailer.enviar_vencimento_gestor(ct_xvencimento90, aux.getGestor().getEmail(), 90);
								avisos_dias[0] = true;
								aux.setAvisos_dias(avisos_dias);
								contratos.save(ct_xvencimento90);
							}
							*/
							break;
						}
					}
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
