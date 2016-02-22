package org.benji.removeaccount;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

/**
 * Created by guilhemm on 22/02/2016.
 */
public class RemoveAccountForm {

    private static final String EMAIL_MESSAGE = "{email.message}";
    private static final String NOT_BLANK_MESSAGE = "{notBlank.message}";

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @NotBlank(message = RemoveAccountForm.NOT_BLANK_MESSAGE)
    @Email(message = RemoveAccountForm.EMAIL_MESSAGE)
    private String email;


}
