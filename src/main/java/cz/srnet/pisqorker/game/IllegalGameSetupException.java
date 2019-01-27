package cz.srnet.pisqorker.game;

import org.springframework.lang.NonNull;

public final class IllegalGameSetupException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public IllegalGameSetupException(@NonNull String reason) {
		super(reason);
	}

}
