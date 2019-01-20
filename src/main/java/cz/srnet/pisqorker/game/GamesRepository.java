package cz.srnet.pisqorker.game;

import java.util.Optional;
import java.util.function.Consumer;

import org.springframework.lang.NonNull;

interface GamesRepository {

	@NonNull
	Game newGame(@NonNull Rules rules, @NonNull Players players, @NonNull Consumer<Piece> moveChecker);

	@NonNull
	Optional<Game> game(@NonNull String id);

}
