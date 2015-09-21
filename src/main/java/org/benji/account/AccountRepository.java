package org.benji.account;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class AccountRepository {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Inject
	private PasswordEncoder passwordEncoder;
	
	@Transactional
	public Account save(Account account) {
		account.setPassword(passwordEncoder.encode(account.getPassword()));
		entityManager.persist(account);
		return account;
	}

    @Transactional
    public boolean deleteUserByEmail(final String email) {
        final Account account = findByEmail(email);
        if (account != null) {
            entityManager.remove(findByEmail(email));
            return true;
        }
        else
            return false;
    }
	
	public Account findByEmail(String email) {
		try {
			return entityManager.createNamedQuery(Account.FIND_BY_EMAIL, Account.class)
					.setParameter("email", email)
					.getSingleResult();
		} catch (PersistenceException e) {
			return null;
		}
	}

	public List<Account> getAllAccounts() {
		try {
			return entityManager.createNamedQuery(Account.FIND_ALL_USERS, Account.class).getResultList();
		} catch (PersistenceException e) {
			return null;
		}
	}


}
