package cz.srnet.pisqorker.users;

import java.util.Optional;

import org.springframework.lang.NonNull;

interface UsersRepository {

	@NonNull
	Optional<User> user(@NonNull String id);

}
