package cz.srnet.pisqorker.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import cz.srnet.pisqorker.rest.TransferableIn;

public final class TransferableUser implements TransferableIn<User> {

	@Autowired
	private @NonNull Users users;

	private final @NonNull String id;

	@JsonCreator
	public TransferableUser(@JsonProperty(value = "id", required = true) @NonNull String id) {
		this.id = id;
	}

	@Override
	@NonNull
	public User transferIn() {
		return users.user(id).get();
	}

	public @NonNull String getId() {
		return id;
	}

}
