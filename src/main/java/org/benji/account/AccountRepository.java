package org.benji.account;

import javax.persistence.*;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class AccountRepository {
    
    @PersistenceContext
    private EntityManager entityManager;
    
    @Inject
    private PasswordEncoder passwordEncoder;

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Transactional
    public Account save(Account account) {
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        entityManager.persist(account);
        return account;
    }
    
    public Account findByEmail(String email) {
        try {
            return entityManager.createNamedQuery(Account.FIND_BY_EMAIL, Account.class)
                    .setParameter("email", email)
                    .getSingleResult();
        } catch (PersistenceException e) {
            LOGGER.error("Can't create account", e);
        }
        return null;
    }

    public List<Account> getAccounts(){
        try {
            return entityManager.createQuery("select a from Account a", Account.class).getResultList();
        } catch (PersistenceException e) {
            LOGGER.error("Can't get account list", e);
        }
        return new ArrayList<>(0);
    }

    @Transactional
    public void delete(String email) {
        try {
            entityManager.createQuery("delete from Account a where a.email = :email").setParameter("email", email).executeUpdate();
        } catch (PersistenceException e) {
            LOGGER.error("Can't delete account", e);
        }
    }
}
