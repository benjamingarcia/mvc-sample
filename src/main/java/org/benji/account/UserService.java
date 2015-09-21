package org.benji.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collections;

@Service
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
        final ArrayList authorities = new ArrayList<>();
        authorities.add(createAuthority(account));
		return new User(account.getEmail(), account.getPassword(), authorities);
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
