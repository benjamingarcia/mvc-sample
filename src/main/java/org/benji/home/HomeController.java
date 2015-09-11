package org.benji.home;

import java.security.Principal;
import java.util.List;

import org.benji.account.Account;
import org.benji.account.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {

	@Autowired
	private AccountRepository accountRepository;


	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView index(Principal principal) {
		List<Account> accounts = accountRepository.getAccounts();

		ModelAndView model = new ModelAndView(principal != null ? "home/homeSignedIn" : "home/homeNotSignedIn");
		model.addObject("accounts", accounts);
		return model;
	}
}
