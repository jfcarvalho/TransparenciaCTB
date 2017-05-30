package com.ctb.contratos.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.ctb.DocumentoDTO;
import com.ctb.DocumentoStorageRunnable;
import com.ctb.contratos.model.Contrato;

@RestController
@RequestMapping("/transparenciactb/documentos")

public class DocumentosController {

	private static final String CADASTRO_VIEW = "/cadastro/CadastroDocumento";
	
	@RequestMapping("/novo")
	public ModelAndView novo()
	{
		ModelAndView mv = new ModelAndView(CADASTRO_VIEW);		
		mv.addObject(new Contrato());
		//DateTime dt = new DateTime(); 
		//System.out.println(dt.toString() );
		return mv;
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public DeferredResult<DocumentoDTO> upload(@RequestParam("files[]")MultipartFile[] files)
	{
		DeferredResult<DocumentoDTO> resultado = new DeferredResult<>();
		
		Thread thread = new Thread(new DocumentoStorageRunnable(files, resultado));
		thread.start();
		
		return resultado;
	}
}
