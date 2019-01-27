package cz.srnet.pisqorker.users;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

import org.springframework.lang.NonNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
final class UsersRepositoryUserDetailsService implements UserDetailsService {

	private final @NonNull UsersRepository repository;

	public UsersRepositoryUserDetailsService(@NonNull UsersRepository repository) {
		this.repository = repository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return Optional.ofNullable(username).flatMap(repository::user).map(this::wrapUser)
				.orElseThrow(() -> new UsernameNotFoundException("Cannot find user " + username));
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
				return user.id();
			}

			@Override
			public Collection<? extends GrantedAuthority> getAuthorities() {
				return Collections.emptyList();
			}
		};
	}

}
