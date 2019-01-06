package cz.srnet.pisqorker.game;

import java.util.Optional;

import org.springframework.lang.NonNull;

public interface Games {

	@NonNull
	Game newGame(@NonNull Rules rules);

	@NonNull
	Optional<Game> game(@NonNull String id);

}
