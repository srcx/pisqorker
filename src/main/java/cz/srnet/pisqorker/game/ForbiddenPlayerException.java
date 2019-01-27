package cz.srnet.pisqorker.game;

import org.springframework.lang.NonNull;

public class ForbiddenPlayerException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ForbiddenPlayerException(@NonNull Player player) {
		super(String.format("Forbidden to play as %s", player));
	}

	public ForbiddenPlayerException(@NonNull Players players) {
		super(String.format("Forbidden to play game with players %s", players));
	}

}
