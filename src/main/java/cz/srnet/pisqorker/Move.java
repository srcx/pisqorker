package cz.srnet.pisqorker;

import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.lang.NonNull;

public interface Move {

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
	Move move(@NonNull Coordinates xy);

	@NonNull
	Move move(int x, int y);

	@NonNull
	Player nextPlayer();

	@NonNull
	Move move(@NonNull Player player, @NonNull Coordinates xy);

	@NonNull
	Move move(@NonNull Player player, int x, int y);

	@NonNull
	Stream<Move> previousStream();

}
