package com.ctb;

import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.*;

@Controller
public class SegurancaController {
	
	//@GetMapping("/login")
	@RequestMapping(value="/login", method=RequestMethod.GET)
	public String login()
	{
		return "login/Login";
	}
}
