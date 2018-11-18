package cz.srnet.pisqorker;

import java.util.Optional;

import org.springframework.lang.NonNull;

interface NextMoves {

	@NonNull
	Optional<Move> next(@NonNull Move move);

}
