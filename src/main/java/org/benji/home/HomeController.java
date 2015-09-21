package org.benji.home;

import org.benji.account.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;

@Controller
public class HomeController {

	@Autowired
	private AccountRepository accountRepository;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index(final Principal principal, final Model model) {
		model.addAttribute("users", accountRepository.getAllAccounts());
		if (principal != null)
			return  "home/homeSignedIn";
		else
			return "home/homeNotSignedIn";
	}
}
