package org.benji.home;

import org.benji.account.Account;
import org.benji.account.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;
import java.util.List;

@Controller
public class HomeController {

	@Autowired
	private AccountRepository accountRepository;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index(Principal principal, Model model) {
		if (principal != null)
			return  "home/homeSignedIn";
		else
			return "home/homeNotSignedIn";
	}

	@ModelAttribute("users")
	public List<Account> getAllUsers() {
		return accountRepository.getAllAccounts();
	}
}
