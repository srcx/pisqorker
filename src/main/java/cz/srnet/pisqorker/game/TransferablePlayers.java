package cz.srnet.pisqorker.game;

import java.util.Objects;

import org.springframework.lang.NonNull;

import cz.srnet.pisqorker.rest.TransferableIn;

public final class TransferablePlayers implements TransferableIn<Players> {

	private Player first;
	private Player second;

	public Player getFirst() {
		return first;
	}

	public void setFirst(Player first) {
		this.first = first;
	}

	public Player getSecond() {
		return second;
	}

	public void setSecond(Player second) {
		this.second = second;
	}

	@Override
	@NonNull
	public Players transferIn() {
		// TODO better validation
		return Players.create(Objects.requireNonNull(first), Objects.requireNonNull(second));
	}

}
