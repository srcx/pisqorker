package cz.srnet.pisqorker.users;

import java.util.Objects;

import org.springframework.lang.NonNull;

import cz.srnet.pisqorker.game.HumanPlayer;

final class UserImpl implements User {

	private final @NonNull String id;

	public UserImpl(@NonNull String id) {
		this.id = id;
	}

	@Override
	@NonNull
	public TransferableUser transferOut() {
		TransferableUser out = new TransferableUser();
		out.setId(id);
		return out;
	}

	@Override
	@NonNull
	public String id() {
		return id;
	}

	@Override
	public boolean canPlayAs(@NonNull HumanPlayer player) {
		return player.user().equals(this);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		UserImpl other = (UserImpl) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return id;
	}

}
