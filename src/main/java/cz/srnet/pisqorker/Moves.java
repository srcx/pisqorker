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
	Move move(int x, int y);

	@NonNull
	Move move(@NonNull Player player, int x, int y);

}
