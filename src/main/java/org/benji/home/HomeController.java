package org.benji.home;

import java.security.Principal;

import org.benji.account.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class HomeController {

	@Autowired
	private AccountRepository accountRepository;


	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index(Principal principal, Model model) {

		model.addAttribute("countAccounts", accountRepository.countAccounts());
		return principal != null ? "home/homeSignedIn" : "home/homeNotSignedIn";
	}
}
