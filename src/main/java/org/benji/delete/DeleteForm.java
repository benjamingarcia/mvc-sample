package org.benji.delete;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

/**
 * Created by theoz on 18/09/2015.
 */
public class DeleteForm {


    private static final String NOT_BLANK_MESSAGE = "{notBlank.message}";
    private static final String EMAIL_MESSAGE = "{email.message}";

    @NotBlank(message = DeleteForm.NOT_BLANK_MESSAGE)
    @Email(message = DeleteForm.EMAIL_MESSAGE)
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }
}
