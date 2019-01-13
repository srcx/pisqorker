package cz.srnet.pisqorker.users;

import java.util.Objects;

import org.springframework.lang.NonNull;

import cz.srnet.pisqorker.rest.TransferableIn;

public final class TransferableUser implements TransferableIn<User> {

	private String id;

	private transient Users users;

	public void injectUsers(Users users) {
		this.users = users;
	}

	@Override
	@NonNull
	public User transferIn() {
		// TODO better validation
		return Objects.requireNonNull(users.user(Objects.requireNonNull(id)).get());
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
