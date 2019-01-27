package cz.srnet.pisqorker.rest;

import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

@Component
final class TokenAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

	private final @NonNull UserDetailsService userDetailsService;

	public TokenAuthenticationProvider(@NonNull UserDetailsService userDetails) {
		this.userDetailsService = userDetails;
	}

	@Override
	protected void additionalAuthenticationChecks(UserDetails userDetails,
			UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
		// void
	}

	@Override
	protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication)
			throws AuthenticationException {
		final Object token = authentication.getCredentials();
		// TODO actual token support
		return userDetailsService.loadUserByUsername(String.valueOf(token));
	}

}
