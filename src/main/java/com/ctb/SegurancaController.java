package com.ctb;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.*;

@Controller
public class SegurancaController {
	
	/*@GetMapping("/login")*/
	@RequestMapping(value="/login", method=RequestMethod.GET)
	public String login(@AuthenticationPrincipal User user) {
		System.out.println(user);
		if (user != null) {
			return "redirect:/transparenciactb/";
		}
		
		return "login";
	}
	
}
