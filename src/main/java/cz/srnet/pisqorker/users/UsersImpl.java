package cz.srnet.pisqorker.users;

import java.util.Objects;
import java.util.Optional;

import org.springframework.lang.NonNull;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
final class UsersImpl implements Users {

	private final @NonNull UsersRepository repository;

	public UsersImpl(@NonNull UsersRepository repository) {
		this.repository = repository;
	}

	@Override
	@NonNull
	public User current() {
		// TODO injected
		String id = SecurityContextHolder.getContext().getAuthentication().getName();
		return Objects.requireNonNull(Optional.ofNullable(id).flatMap(this::user)
				.orElseThrow(() -> new IllegalStateException("Current user is not known: " + id)));
	}

	@Override
	@NonNull
	public Optional<User> user(@NonNull String id) {
		return repository.user(id);
	}

}
