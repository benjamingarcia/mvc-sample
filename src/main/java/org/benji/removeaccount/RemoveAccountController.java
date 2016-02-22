package org.benji.removeaccount;

import org.benji.account.Account;
import org.benji.account.AccountRepository;
import org.benji.support.web.MessageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

/**
 * Created by guilhemm on 22/02/2016.
 */
@Controller
public class RemoveAccountController {

    public static final String REMOVE_ACCOUNT_VIEW = "removeaccount/removeaccount";
    public static final String REMOVE_ACCOUNT_DONE_REDIRECTION = "/removeaccount";

    @Autowired
    private AccountRepository accountRepository;

    @RequestMapping(value = "removeaccount", method = RequestMethod.GET)
    public String removeAccount(Model model)
    {
        model.addAttribute(new RemoveAccountForm());
        return REMOVE_ACCOUNT_VIEW;
    }

    @RequestMapping(value="removeaccount", method = RequestMethod.POST)
    public String removeAccount(@Valid @ModelAttribute RemoveAccountForm removeAccountForm, Errors errors, RedirectAttributes ra)
    {
        if(errors.hasErrors()) {
            return REMOVE_ACCOUNT_VIEW;
        }
        accountRepository.deleteByEmail(removeAccountForm.getEmail());
        MessageHelper.addSuccessAttribute(ra, "removeAccount.success");
        ra.addAttribute("removedAccount", removeAccountForm.getEmail());
        return "redirect:"+REMOVE_ACCOUNT_DONE_REDIRECTION;
    }
}

