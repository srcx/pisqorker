package cz.srnet.pisqorker.game;

import org.springframework.lang.NonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import cz.srnet.pisqorker.rest.TransferableIn;

public final class TransferablePlayers implements TransferableIn<Players> {

	private final @NonNull Player first;
	private final @NonNull Player second;

	@JsonCreator
	public TransferablePlayers(@JsonProperty(value = "first", required = true) @NonNull Player first,
			@JsonProperty(value = "second", required = true) @NonNull Player second) {
		this.first = first;
		this.second = second;
	}

	public @NonNull Player getFirst() {
		return first;
	}

	public @NonNull Player getSecond() {
		return second;
	}

	@Override
	@NonNull
	public Players transferIn() {
		return Players.create(first, second);
	}

}
