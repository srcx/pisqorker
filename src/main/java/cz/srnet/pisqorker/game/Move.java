package cz.srnet.pisqorker.game;

import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.lang.NonNull;

interface Move {

	int turn();

	@NonNull
	GameState state();

	@NonNull
	Player player();

	@NonNull
	Coordinates xy();

	@NonNull
	Optional<Move> previous();

	@NonNull
	Optional<Move> next();

	@NonNull
	Stream<Move> previousStream();

	@NonNull
	MakeMove move();

}
