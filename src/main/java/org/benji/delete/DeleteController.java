package org.benji.delete;

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

import javax.validation.Valid;
import java.security.Principal;

/**
 * Created by theoz on 18/09/2015.
 */
@Controller
public class DeleteController {

    @Autowired
    public AccountRepository accountRepository;

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public String deleteUser(final Principal principal, final Model model) {
        model.addAttribute("enabled", true);
        if (principal != null)
            return "home/homeSignedIn";
        else
            return "home/homeNotSignedIn";
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public String deleteUser(@Valid @ModelAttribute final DeleteForm form, final Errors errors, final Model model, final Principal principal) {
        if (!errors.hasErrors()) {
            if (principal.getName().equals(form.getEmail()))
                MessageHelper.addWarningAttribute(model, "delete.error.selfDelete");
            else {
                final Account account = accountRepository.findByEmail(form.getEmail());
                if (account != null) {
                    if (accountRepository.deleteUserByEmail(form.getEmail()))
                        MessageHelper.addInfoAttribute(model, "delete.success", form.getEmail());
                    else
                        MessageHelper.addErrorAttribute(model, "delete.error.unknown", form.getEmail());
                }
                else
                    MessageHelper.addErrorAttribute(model, "delete.error.notFound", form.getEmail());
            }
        }
        else
            MessageHelper.addErrorAttribute(model, "delete.error", errors.getAllErrors());

        return "home/homeSignedIn";
    }
}
