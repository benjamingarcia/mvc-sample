package org.benji.account;

import javax.persistence.*;
import javax.inject.Inject;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;

@Repository
@Transactional(readOnly = false)
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

	public void deleteByEmail(String email)
	{
		Query q = entityManager.createQuery("DELETE FROM Account WHERE email = :email");
		q.setParameter("email", email);
		q.executeUpdate();
	}

	public int countAccounts()
	{
		return ((Long)entityManager.createQuery("SELECT COUNT(*) FROM Account a").getSingleResult()).intValue();



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
}
