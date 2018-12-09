package cz.srnet.pisqorker;

import java.util.Optional;

import org.springframework.lang.NonNull;

public interface Moves {

	@NonNull
	Optional<Move> firstMove();

	@NonNull
	Optional<Move> lastMove();

	@NonNull
	Player nextPlayer();

	@NonNull
	MakeMove move();

}
