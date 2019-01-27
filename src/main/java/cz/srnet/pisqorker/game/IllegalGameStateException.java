package cz.srnet.pisqorker.game;

import org.springframework.lang.NonNull;

public final class IllegalGameStateException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public IllegalGameStateException(@NonNull String reason) {
		super(reason);
	}

}
