package com.ctb.contratos.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;

public class HomesController {
	@RequestMapping("/transparenciactb")
	public String index(HttpServletRequest request)
	{
		System.out.println(request.getServletContext());
		return "index";
	}
}
