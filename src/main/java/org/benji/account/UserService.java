package org.benji.account;

import java.util.Collections;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.*;

public class UserService implements UserDetailsService {
	
	@Autowired
	private AccountRepository accountRepository;
	
	@PostConstruct	
	protected void initialize() {
		accountRepository.save(new Account("user", "demo", "ROLE_USER"));
		accountRepository.save(new Account("CentauriTiki@harakirimail.com", "CentauriTiki", "ROLE_ADMIN"));
		accountRepository.save(new Account("DoroEosaurus@harakirimail.com", "DoroEosaurus", "ROLE_USER"));
		accountRepository.save(new Account("WillingIsenstein@harakirimail.com", "WillingIsenstein", "ROLE_USER"));
		accountRepository.save(new Account("HudibrasticAgcy@harakirimail.com", "HudibrasticAgcy", "ROLE_USER"));
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Account account = accountRepository.findByEmail(username);
		if(account == null) {
			throw new UsernameNotFoundException("user not found");
		}
		return null;
	}
	
	public void signin(Account account) {
		SecurityContextHolder.getContext().setAuthentication(authenticate(account));
	}
	
	private Authentication authenticate(Account account) {
		return new UsernamePasswordAuthenticationToken(account.getEmail(), account.getPassword(), Collections.singleton(createAuthority(account)));
	}


	private GrantedAuthority createAuthority(Account account) {
		return new SimpleGrantedAuthority(account.getRole());
	}

}
