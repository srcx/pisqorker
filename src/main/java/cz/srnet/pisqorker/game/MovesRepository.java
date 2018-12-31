package cz.srnet.pisqorker.game;

import java.util.Optional;

import org.springframework.lang.NonNull;

interface MovesRepository extends Moves {

	@NonNull
	Optional<Move> previousMove(@NonNull Move move);

	@NonNull
	Optional<Move> nextMove(@NonNull Move move);

}
