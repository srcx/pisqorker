package cz.srnet.pisqorker.rest;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import cz.srnet.pisqorker.users.User;
import cz.srnet.pisqorker.users.Users;

@Component
final class TokenAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

	private final @NonNull Users users;

	public TokenAuthenticationProvider(@NonNull Users users) {
		this.users = users;
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
		return Optional.ofNullable(token).map(String::valueOf).flatMap(users::user).map(this::wrapUser).orElseThrow(
				() -> new UsernameNotFoundException("Cannot find user with authentication token: " + token));
	}

	private @NonNull UserDetails wrapUser(@NonNull User user) {
		return new UserDetails() {

			private static final long serialVersionUID = 1L;

			@Override
			public boolean isEnabled() {
				return true;
			}

			@Override
			public boolean isCredentialsNonExpired() {
				return true;
			}

			@Override
			public boolean isAccountNonLocked() {
				return true;
			}

			@Override
			public boolean isAccountNonExpired() {
				return true;
			}

			@Override
			public String getUsername() {
				return user.id();
			}

			@Override
			public String getPassword() {
				return "NO_PASSWORD";
			}

			@Override
			public Collection<? extends GrantedAuthority> getAuthorities() {
				return Collections.emptyList();
			}
		};
	}

}
