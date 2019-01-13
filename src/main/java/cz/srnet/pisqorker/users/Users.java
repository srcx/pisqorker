package cz.srnet.pisqorker.users;

import java.util.Optional;

import org.springframework.lang.NonNull;

public interface Users {

	@NonNull
	User current();

	@NonNull
	Optional<User> user(@NonNull String id);

}
