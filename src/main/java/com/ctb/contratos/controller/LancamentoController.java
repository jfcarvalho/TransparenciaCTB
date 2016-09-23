package com.ctb.contratos.controller;

import java.text.ParseException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.ctb.contratos.model.Contratado;
import com.ctb.contratos.model.Contrato;
import com.ctb.contratos.model.Fonte;
import com.ctb.contratos.model.Lancamento;
import com.ctb.contratos.model.TipoAditivo;
import com.ctb.contratos.repository.Contratados;
import com.ctb.contratos.repository.Contratos;
import com.ctb.contratos.repository.Lancamentos;
import com.ctb.contratos.repository.Processos;

@Controller
@RequestMapping("/transparenciactb/lancamentos")

public class LancamentoController {
	

	private static final String CADASTRO_VIEW = "/cadastro/CadastroLancamento"; 
	private static final String LANCAMENTOS_VIEW = "/pesquisa/PesquisaLancamentos";
	
	
	@Autowired
	private Lancamentos lancamentos;
	@Autowired
	private Processos processos;
	@Autowired
	private Contratos contratos;
	
	
	@RequestMapping("/novo/{id_contrato}")
	public ModelAndView novo(@PathVariable("id_contrato") Integer Id_contrato)
	{
		ModelAndView mv = new ModelAndView(CADASTRO_VIEW);
		mv.addObject(new Lancamento());
		Contrato c = contratos.findOne(Id_contrato);
		mv.addObject("contrato", c);
		return mv;
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String salvar(@Validated Lancamento lancamento, @RequestParam Integer lancamento_id_processo, @RequestParam Integer lancamento_id_contrato, RedirectAttributes attributes)
	{
		ModelAndView mv = new ModelAndView(CADASTRO_VIEW);
		if(lancamento_id_processo != null)
		{
			Processo processo = processos.findOne(lancamento_id_processo);
			lancamento.setProcesso(processo);
		}
		//System.out.println(lancamento_id_contrato);
	
		if(lancamento_id_contrato != null)
		{
			Contrato contrato = contratos.findOne(lancamento_id_contrato);
			//contrato.setSaldo_contrato(contrato.getSaldo_contrato() - lancamento.getValor());
			lancamento.setContrato(contrato);
			contratos.save(contrato);
		}
	
		lancamentos.save(lancamento);		
		attributes.addFlashAttribute("mensagem", "Lancamento salvo com sucesso!");	
		return "redirect:/transparenciactb/lancamentos/pesquisar/"+ lancamento_id_contrato;
	}

	
	@ModelAttribute("todosProcessos")
	public List<Processo> todosProcessos()
	{
		return processos.findAll();
	}
	
	
	
	@RequestMapping(value="/pesquisar/{id_contrato}")
	public ModelAndView pesquisar(@PathVariable("id_contrato") Integer Id_contrato, String busca, String nome, String setor) throws ParseException
	{
		ModelAndView mv = new ModelAndView("/pesquisa/PesquisaLancamentos");
		
		Contrato c = contratos.findOne(Id_contrato);
		List<Lancamento> lancamentos = c.getLancamentos();
		mv.addObject("todosLancamentos", lancamentos);
		mv.addObject("contrato", c);
		
		//Iterator it = lancamentos.iterator();
		/*
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
	return mv;
	}
	
	@RequestMapping("/{id_contrato}/{id_lancamento}")
	public ModelAndView edicao(@PathVariable("id_lancamento") Lancamento lancamento, @PathVariable("id_contrato") Contrato contrato)
	{
		
		
		ModelAndView mv = new ModelAndView(CADASTRO_VIEW);
		mv.addObject("lancamento", lancamento);
		mv.addObject(lancamento);
		mv.addObject("contrato", contrato);
		return mv;
	}
	
	@ModelAttribute("todosContratos")
	public List<Contrato> todosContratos()
	{
		return contratos.findAll();
	}
	
	@RequestMapping(value="/remove/{id_lancamento}")
	public String excluir(@PathVariable Integer id_lancamento, RedirectAttributes attributes)
	{
		Lancamento lancamento = lancamentos.findOne(id_lancamento);
		attributes.addFlashAttribute("mensagem", "Contrato excluído com sucesso com sucesso!");	
		//usuarios.delete(id_usuario);
		desvincularLancamento(lancamento);
		lancamentos.delete(id_lancamento);
		return "redirect:/transparenciactb/lancamentos";	
	
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

@ModelAttribute("todosTipos")
public List<TipoAditivo> todosAditivos() {
	return Arrays.asList(TipoAditivo.values());
}

}
