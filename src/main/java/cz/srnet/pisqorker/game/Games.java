package cz.srnet.pisqorker.game;

import org.springframework.lang.NonNull;

public interface Games {

	static @NonNull Games create(@NonNull WinConditionCheckers winConditionCheckers) {
		return new GamesImpl(winConditionCheckers);
	}

	@NonNull
	Game newGame(@NonNull Rules rules);

}
