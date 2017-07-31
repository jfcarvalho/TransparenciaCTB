package com.ctb.contratos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ctb.contratos.repository.ConfAvisoss;
import com.ctb.contratos.repository.Contratados;

@Controller
@RequestMapping("/transparenciactb/contratados")

public class ConfAvisosController {
	
	@Autowired
	private ConfAvisoss cfs;

}
