package cz.srnet.pisqorker.rest;

import org.springframework.lang.NonNull;

final class GameNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public GameNotFoundException(@NonNull String id) {
		super(String.format("Game %s cannot be found", id));
	}

}
