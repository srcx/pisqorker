package cz.srnet.pisqorker.users;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

@Repository
final class HardcodedUsersRepository implements UsersRepository {

	private final @NonNull Map<String, User> users = new HashMap<>();

	public HardcodedUsersRepository() {
		addUser("joe");
		addUser("jane");
	}

	private void addUser(@NonNull String id) {
		users.put(id, new UserImpl(id));
	}

	@Override
	@NonNull
	public Optional<User> user(@NonNull String id) {
		return Objects.requireNonNull(Optional.ofNullable(users.get(id)));
	}

}
