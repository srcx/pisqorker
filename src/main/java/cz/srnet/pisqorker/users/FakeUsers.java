package cz.srnet.pisqorker.users;

import java.util.Objects;
import java.util.Optional;

import org.springframework.lang.NonNull;

public final class FakeUsers implements Users {

	private @NonNull Optional<User> current = Optional.empty();

	public @NonNull FakeUsers _current(@NonNull User user) {
		current = Optional.of(user);
		return this;
	}

	@Override
	@NonNull
	public User current() {
		return Objects.requireNonNull(current.orElseThrow(() -> new UnsupportedOperationException()));
	}

	@Override
	@NonNull
	public Optional<User> user(@NonNull String id) {
		throw new UnsupportedOperationException();
	}

}
