package org.benji.delete;

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
 * Created by benjaming on 11/09/2015.
 */
@Controller
public class DeleteController {

    private static final String DELETE_VIEW_NAME = "delete/delete";

    @Autowired
    private AccountRepository accountRepository;

    @RequestMapping(value = "delete")
    public String signup(Model model) {
        model.addAttribute(new DeleteForm());
        return DELETE_VIEW_NAME;
    }

    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public String signup(@Valid @ModelAttribute DeleteForm deleteForm, Errors errors, RedirectAttributes ra) {
        if (errors.hasErrors()) {
            return DELETE_VIEW_NAME;
        }

        accountRepository.delete(deleteForm.getEmail());
        // see /WEB-INF/i18n/messages.properties and /WEB-INF/views/homeSignedIn.html
        MessageHelper.addSuccessAttribute(ra, "delete.success");
        return "redirect:/";
    }
}
