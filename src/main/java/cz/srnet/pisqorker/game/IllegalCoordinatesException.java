package cz.srnet.pisqorker.game;

import org.springframework.lang.NonNull;

public final class IllegalCoordinatesException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public IllegalCoordinatesException(@NonNull Coordinates xy, @NonNull String reason) {
		super(String.format("Cannot move to %s, because %s", xy, reason));
	}

}
