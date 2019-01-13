package cz.srnet.pisqorker.users;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

@Service
final class HardcodedUsers implements Users {

	private final @NonNull Map<String, User> users = new HashMap<>();

	public HardcodedUsers() {
		users.put("joe", new UserImpl("joe"));
	}

	@Override
	@NonNull
	public User current() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	@NonNull
	public Optional<User> user(@NonNull String id) {
		return Objects.requireNonNull(Optional.ofNullable(users.get(id)));
	}

}
