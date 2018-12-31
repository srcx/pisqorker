package cz.srnet.pisqorker.game;

import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.lang.NonNull;

interface Moves {

	@NonNull
	Optional<Move> firstMove();

	@NonNull
	Optional<Move> lastMove();

	@NonNull
	Player nextPlayer();

	@NonNull
	MakeMove move();

	@NonNull
	Stream<Move> stream();

}
